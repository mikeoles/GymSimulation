/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benjamin
 */
public class PassengerList {
    private int N;               // number of elements on queue
    private int passedSize;
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    private Node current;
    private double idleTime;

    /**
     * @return the idleTime
     */
    public double getIdle() {
        return idleTime;
    }

    /**
     * @param idleTime the idleTime to set
     */
    public void addIdle(double idleTime) {
        this.idleTime = idleTime+this.idleTime;
    }
    
    private static class Node{
        private Passenger passenger;
        private Node next;
        private Node previous;
        
    }
    public PassengerList() {
        first = null;
        last  = null;
        //current=null;
        N = 0;
        passedSize=0;
    }
    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;     
    }
    public void add(double time,String flightType,int bagCount,double flightTime){
        Node oldLast=last;
        last=new Node();
        last.passenger=new Passenger();
        last.passenger.setArrivalTime(time);
        last.passenger.setNumberOfBags(bagCount);
        last.next = null;
        last.previous=oldLast;
        last.passenger.setFlightType(flightType);
        last.passenger.setFlightTime(flightTime);
        N++;
        if (isEmpty()){
            first=last;
            current=first;
        }else{
            oldLast.next=last;
        }
        
    }
    public void add(Passenger passenger){
        Node oldLast=last;
        last=new Node();
        last.passenger=passenger;
        //last.passenger=passenger;
        last.next = null;
        last.previous=oldLast;
        //last.passenger.setFlightTime(0);
        N++;
        if (isEmpty()){
            //current=last;
            first=last;
            current=first;
        }else{
            oldLast.next=last;
        }
        
    }
    public void sortedAdd(double time,String flightType,int bagCount,double flightTime){
        
        add(time,flightType,bagCount,flightTime);
        Node curr=last;
        while(curr.previous!=null){
            if(curr.passenger.getArrivalTime()<curr.previous.passenger.getArrivalTime()){
                //Node newLast=last.previous;
                //Node next=last.next;
                //newLast.next=next;
                //last.previous=newLast.previous;
                //newLast.previous=last;
                //last.next=newLast;
                Passenger temp=curr.previous.passenger;
                curr.previous.passenger=curr.passenger;
                curr.passenger=temp;
                curr=curr.previous;
            }else break;
        }
        
    }
    public void sortedAdd(Passenger passenger){
        add(passenger);
        Node curr=last;
        while(curr.previous!=null){
            if(curr.passenger.getArrivalTime()<curr.previous.passenger.getArrivalTime()){
                //Node newLast=last.previous;
                //Node next=last.next;
                //newLast.next=next;
                //last.previous=newLast.previous;
                //newLast.previous=last;
                //last.next=newLast;
                Passenger temp=curr.previous.passenger;
                curr.previous.passenger=curr.passenger;
                curr.passenger=temp;
                curr=curr.previous;
            }else break;
        }
        
    }
    public void sortedMerge(PassengerList passengers){
        Node curr=passengers.first;
        while(curr!=null){
            sortedAdd(curr.passenger);
            curr=curr.next;
        }
    }
    /**
     * sets initial time to look at
     */
    public void setClock(double hoursPassed) {
        current=first;
        passedSize=0;
        while (current.passenger.getArrivalTime()<hoursPassed){
            current=current.next;
            passedSize++;
            if(current==null)break;
        }
    }
    public double getArrivalTime() {
        return current.passenger.getArrivalTime();     
    }
    public void nextArrival() {
        if (current==null){
            //System.out.println("\n\nno new arrival\n\n");
            return;
        }
        current=current.next;
        //passedSize++;
    }
    /*public void setFlightType(String flightType){
        current.type=flightType;
    }*/
    public String currentFlight() {
        return current.passenger.getFlightType();
    }
    public Passenger currentPassenger() {
        if (current==null)return null;
        return current.passenger;
    }
    public Passenger lastPassenger() {
        if (last==null)return null;
        return last.passenger;
    }
    
    /**
     * gets sizeRemaining after clock set
     */
    public int sizeRemaining() {
        return N-passedSize;
    }
    public int sizeAfter(double startTime) {
        setClock(startTime);
        return N-passedSize;
    }
    public int sizeBetween(double startTime,double endTime) {
        setClock(startTime);
        Node current=this.current;
        int remainingSize=0;
        while (current.passenger.getArrivalTime()<endTime){
            current=current.next;
            remainingSize++;
            if(current==null)break;
        }
        return remainingSize;
    }
    /*public String toString() {
        if(this==null){
            return "null";
        }else {
            String returnString="";
            
            return returnString;
        }
        
    }*/
}
