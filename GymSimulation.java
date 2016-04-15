import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

//import org.apache.commons.math3.distribution.ExponentialDistribution;
//import org.apache.commons.math3.distribution.NormalDistribution;
//import java.util.Random;

public class GymSimulation {

    static double arrivalRate = 19.78569777;
    
    static boolean debug = true;//Prints out all events and doest let user choose options
    
    static ArrayList<Event> events = new ArrayList<>();//times of all the upcoming events     
    static double currentTime = 0;//current program time (hours)
    static double endTime = 0;//time program will end
    
    static Queue<Member> line = new LinkedList();//first class check in line    
    
    static double powerRacks = 0;//number of power racks in the gym
    static double freePowerRacks = 0;//number of open power racks
    
    static ArrayList<Double> waitTimes = new ArrayList<>();
    static ArrayList<Double> liftTimes = new ArrayList<>();
    static int numMembers = 0;
    static int benchMembers = 0;
    static double lineCount = 0;
    static double lineLength = 0;
    satic char choice = '';
    
    public static void main(String[] args) {
        System.out.println("Start Simulation");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        
        if(!debug){
            System.out.println("How long should program be run");
            endTime = reader.nextInt(); 
            
            System.out.println("Should we used bench/squat spefic racks? (y/n)");
            choice = reader.nextChar();
            
            System.out.println("How many Power Racks should there be?");
            powerRacks = reader.nextInt();
        }else{
            endTime = 24;
            powerRacks = 10;
        }
        
        freePowerRacks = powerRacks;//all power racks start out empty
        
        //generate first gym member
        if(choice == 'y'){
            double arrivalTime = exponential(arrivalRate);
            Event newEvent = new Event(arrivalTime,4);
            events.add(newEvent);      
        }else{
            double arrivalTime = exponential(arrivalRate);
            Event newEvent = new Event(arrivalTime,1);
            events.add(newEvent);                 
        }        
        
        //Run Simulation
        //All events are in an array with the next event sorted to the begining
        while(currentTime<endTime){
            Collections.sort(events);
            currentTime = events.get(0).getTime();
            if(events.get(0).getEventType()==1){
                memberArrival();
            }else if(events.get(0).getEventType()==2){
                memberExit(events.get(0).getMember());
            }else if(events.get(0).getEventType()==3){
                results();
            }else if(events.get(0).getEventType()==4){
                choiceArrival();
            }else if(events.get(0).getEventType()==5){
                benchExit();
            }else if(events.get(0).getEventType()==6){
                squatExit();
            }
            events.remove(0);                 
        }
        
        //Print results
        results();
        
    }

    private static void choiceArrival() {
        if(debug) System.out.println(currentTime + ": Member arrives at gym");
        
        //create member that arrived now
        Member arrivedMember = new Member(currentTime);
        arrivedMember.setUsedBench(usedBench());    
        
        if(arrivedMember.getUsedBench()){
            benchArrival(arrivedMember);
        }else{
            squatArrival(arrivedMember);
        }
        
        //track average length of line
        lineLength += line.size();
        lineCount ++;
        
        //after member has arrived figure out the time for next arrival
        double nextArrivalTime = currentTime+exponential(arrivalRate);
        Event newEvent = new Event(nextArrivalTime,4);
        events.add(newEvent);
        
    }

    private static void memberArrival() {
        if(debug) System.out.println(currentTime + ": Member arrives at gym");
        
        //create member that arrived now
        Member arrivedMember = new Member(currentTime);
        arrivedMember.setUsedBench(usedBench());    
        
        //track average length of line
        lineLength += line.size();
        lineCount ++;
        
        //If member can walk up and start a power rack right away
        if(line.isEmpty() && freePowerRacks>0){
            freePowerRacks--;//Power rack is now taken up
            arrivedMember.setStartTime(currentTime);//member starts now
            double liftTime = .5;//TODO: calculate lift time
            Event newEvent = new Event(currentTime+liftTime,2,arrivedMember);//member will exit after done lifting
            events.add(newEvent);            
        }else{//has to wait in line
            line.add(arrivedMember);
        }
        
        //after member has arrived figure out the time for next arrival
        double nextArrivalTime = currentTime+exponential(arrivalRate);
        Event newEvent = new Event(nextArrivalTime,1);
        events.add(newEvent);
    }

    private static void benchArrival(Member arrivingMember) {
        if(benchLine.isEmpty() && freeBench>0){
            freeBench--;//Power rack is now taken up
            arrivedMember.setStartTime(currentTime);//member starts now
            double liftTime = .5;//TODO: calculate lift time
            Event newEvent = new Event(currentTime+liftTime,5,arrivedMember);//member will exit after done lifting
            events.add(newEvent);            
        }else{//has to wait in line
            benchLine.add(arrivedMember);
        }        
    }

    private static void squatArrival(Member arrivingMember) {
        if(squatLine.isEmpty() && freeSquat>0){
            freeBench--;//Power rack is now taken up
            arrivedMember.setStartTime(currentTime);//member starts now
            double liftTime = .5;//TODO: calculate lift time
            Event newEvent = new Event(currentTime+liftTime,6,arrivedMember);//member will exit after done lifting
            events.add(newEvent);            
        }else{//has to wait in line
            squatLine.add(arrivedMember);
        }              
    }

    private static void memberExit(Member exitingMember) {
        exitingMember.setEndTime(currentTime);
        liftTimes.add(exitingMember.getLiftTime());
        waitTimes.add(exitingMember.getWaitTime());
        numMembers++;
        if(exitingMember.usedBench()){
            benchMembers++;
        }
        
        if(line.isEmpty()){
            freePowerRacks++;
        }else{
            Member nextMember = line.poll();
            double liftTime = .5;
            nextMember.setStartTime(currentTime);
            Event newEvent = new Event(currentTime+liftTime,2,nextMember);//member will exit after done lifting
            events.add(newEvent); 
        }
    }  
    
    private static void benchExit(Member exitingMember) {
        exitingMember.setEndTime(currentTime);
        liftTimes.add(exitingMember.getLiftTime());
        waitTimes.add(exitingMember.getWaitTime());
        numMembers++;
        benchMembers++;
        
        if(line.isEmpty()){
            freeBench++;
        }else{
            Member nextMember = benchLine.poll();
            double liftTime = .5;
            nextMember.setStartTime(currentTime);
            Event newEvent = new Event(currentTime+liftTime,5,nextMember);//member will exit after done lifting
            events.add(newEvent); 
        }
    }      
     
    private static void squatExit(Member exitingMember) {
        exitingMember.setEndTime(currentTime);
        liftTimes.add(exitingMember.getLiftTime());
        waitTimes.add(exitingMember.getWaitTime());
        numMembers++;
        squatMembers++;
        
        if(line.isEmpty()){
            freeSquat++;
        }else{
            Member nextMember = squatLine.poll();
            double liftTime = .5;
            nextMember.setStartTime(currentTime);
            Event newEvent = new Event(currentTime+liftTime,6,nextMember);//member will exit after done lifting
            events.add(newEvent); 
        }
    }  
    //print out the results
    //to print out average line length divide lineLength/lineCount
    public static void results(){   
        double avg_wait_time;
        double avg_lift_time;
        double avg_line_length;
        int num_benched;
        double percent_benched;

        for (int i = 0; i < waitTimes.length; i++){
            avg_wait_time += waitTimes[i];
        }

        for (int i = 0; i < liftTimes.length; i++){
            avg_lift_time += liftTimes[i];
        }

        for (int i = 0; i < waitTimes.length; i++){ //use same array, want to generate same # of bench probabilities as ppl in this array
            if (usedBench()){
                num_benched++;
            }
        }

        

        avg_wait_time /= waitTimes.length;
        avg_lift_time /= liftTimes.length;
        avg_line_length = lineLength / lineCount;
        percent_benched = num_benched / waitTimes.length;



        System.out.println("Total Simulation runtime: " + endTime);
        System.out.println("Number of Power Racks: " + powerRacks);

        System.out.println("Average Wait Time: " + avg_wait_time);
        System.out.println("Average Lift Time: " + avg_lift_time);
        System.out.println("Average Line Length: " + avg_line_length);
        System.out.println("% of Bench users: " + percent_benched);
    }
    
    //returns the arrival time of the next geometric distribution
    public static double exponential(double lambda) {
        Random rand = new Random();
        return -1*Math.log(rand.nextDouble())/lambda;
    }

    //returns a number from the geometric distribution with probability p
    public static int geometric(double p){
        Random rand = new Random();
        return (int)Math.ceil(Math.log(1-rand.nextDouble()) / Math.log(1-p));
    }
    //to use, call: normal(0.4080,0.235017207);
    public static double normal(double mean,double SD) {
        Random rand = new Random();
        double number = rand.nextGaussian()*SD+mean;
        if (number<0)return normal(mean,SD);
        return number;
    }
    //use this to not need to input mean/SD
    public static double getNormal() {
        return normal(0.4080,0.235017207);
    }
    
    public static boolean usedBench(){
            Random rand = new Random();
            double benchRate = 0.5909; //value of people using benches divided by total
            double value = rand.nextDouble();return value>=benchRate;
    }
    
    
}



