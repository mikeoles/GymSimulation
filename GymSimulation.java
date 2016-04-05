package GymSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GymSimulation {

    static boolean debug = true;
    
    static ArrayList<Event> events = new ArrayList<>();//times of all the upcoming events     
    static double currentTime = 0;
    static double endTime = 0;
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        if(!debug){
            System.out.println("How long should program be run");
            endTime = reader.nextInt(); 
        }else{
            endTime = 24;
        }
        
        //generate first gym member
        double arrivalTime = 0;
        //arrivalTime = currentTime+exponential(40);
        Event newEvent = new Event(arrivalTime,2);
        events.add(newEvent);
        
        
        //Run Simulation
        //All events are in an array with the next event sorted to the begining
        while(currentTime<endTime){
            Collections.sort(events);
            currentTime = events.get(0).getTime();
            if(events.get(0).getEventType()==1){
                memberArrival();
            }else if(events.get(0).getEventType()==2){
                memberExit();
            }else if(events.get(0).getEventType()==3){
                results();
            }
            events.remove(0);                 
        }
        
        //Print results
        results();
        
    }

    private static void memberArrival() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void memberExit() {
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
}

