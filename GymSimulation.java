package gymsimulation;

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
    
    public static void main(String[] args) {
        System.out.println("Start Simulation");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        
        if(!debug){
            System.out.println("How long should program be run");
            endTime = reader.nextInt(); 
            
            System.out.println("How many Power Racks should there be?");
            powerRacks = reader.nextInt();
        }else{
            endTime = 24;
            powerRacks = 10;
        }
        
        freePowerRacks = powerRacks;//all power racks start out empty
        
        //generate first gym member
        double arrivalTime = exponential(arrivalRate);
        Event newEvent = new Event(arrivalTime,1);
        events.add(newEvent);
        
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
            }
            events.remove(0);                 
        }
        
        //Print results
        results();
        
    }

    private static void memberArrival() {
        if(debug) System.out.println(currentTime + ": Member arrives at gym");
        
        //create member that arrived now
        Member arrivedMember = new Member(currentTime);
		arrivedMember.setUsedBench(usedBench());
        
        //If member can walk up and start a power rack right away
        if(line.isEmpty() && freePowerRacks>0){
            freePowerRacks--;//Power rack is now taken up
            arrivedMember.setStartTime(currentTime);//member starts now
            double liftTime = 0;//TODO: calculate lift time
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

    private static void memberExit(Member exitingMember) {
        throw new UnsupportedOperationException("Not supported yet.");
    }  
 
    //print out the results 
    public static void results(){   
                         
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
	public static boolean usesBench(){
		Random rand = new Random();
		double benchRate = 0.5909; //value of people using benches divided by total
		double value = rand.nextDouble
		if(value>=benchRate){
			return true;
		}
		else{
			return false;
		}
	}
}


