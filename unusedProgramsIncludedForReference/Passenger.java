/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Benjamin
 */
public class Passenger {
    private double arrivalTime;
    private double checkInStartTime;
    private double checkOutTime;
    private String type;
    //private int index;
    private int numberOfBags;
    private double checkingInTime;
    private double flightTime;
    private double securityTime;//time spent in security
    private double securityStartTime;//time started to be security screened
    private double gateTime;//arrival at gate
    private double printTime;
    private double bagTime;
    private double problemTime;
    private boolean missed=false;
    
    /*public void Clone(Passenger passenger) {
        setArrivalTime(passenger.getArrivalTime());
        setStartTime(passenger.getStartTime());
        setCheckOutTime(passenger.getCheckOutTime());
        setType(passenger.getType());
        //private int index;
        setNumberOfBags(passenger.getNumberOfBags());
        setCheckInTime(passenger.getCheckInTime());
    }*/
    
    /**
     * @return the arrivalTime
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the time spent waiting in the check-in line
     */
    public double getCheckInWaitTime() {
        return checkInStartTime-arrivalTime;
    }    
    
    /**
     * @return the checkInStartTime
     */
    public double getCheckInStartTime() {
        return checkInStartTime;
    }

    /**
     * @param checkInStartTime the checkInStartTime to set
     */
    public void setCheckInStartTime(double checkInStartTime) {
        this.checkInStartTime = checkInStartTime;
    }

    /**
     * @return the checkOutTime
     */
    public double getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * @param checkOutTime the checkOutTime to set
     */
    public void setCheckOutTime(double checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    /**
     * @return the type
     */
    public String getFlightType() {
        return getType();
    }

    /**
     * @param type the type to set
     */
    public void setFlightType(String type) {
        this.setType(type);
    }

    /**
     * @return the numberOfBags
     */
    public int getNumberOfBags() {
        return numberOfBags;
    }

    /**
     * @param numberOfBags the numberOfBags to set
     */
    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    /**
     * @return the checkingInTime
     */
    public double getCheckingInTime() {
        return checkingInTime;
    }

    /**
     * @param checkingInTime the checkingInTime to set
     */
    public void setCheckingInTime(double checkingInTime) {
        this.checkingInTime = checkingInTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    public String toString() {
        if(this==null){
            return "null";
        }else {
            String returnString="";
            returnString=returnString+
                    "Arrival Time: Day "+(int)((arrivalTime/24)+1)+", ";
            returnString=returnString+
                    (int)(arrivalTime%24)+":";
            if((int)((arrivalTime%1)*60)<10)returnString=returnString+"0";
            returnString=returnString+
                    (int)((arrivalTime%1)*60)+" and "+
                    ((((arrivalTime%1)*60)%1)*60)+" seconds ";
            if(!type.equalsIgnoreCase("commute")){
                if(isMissed())returnString=returnString+"\nThe airplane couldn't be boarded in time :(";
                else returnString=returnString+"\nThe Flight Wasn't Missed";
            }
            returnString=returnString+
                    "\nCheck-in Start Time: Day "+(int)((checkInStartTime/24)+1)+", ";
            returnString=returnString+
                    (int)(checkInStartTime%24)+":";
            if((int)((checkInStartTime%1)*60)<10)returnString=returnString+"0";
            returnString=returnString+
                    (int)((checkInStartTime%1)*60)+" and "+
                    ((((checkInStartTime%1)*60)%1)*60)+" seconds "+
                    "\nTime spent checking-in: ";
            if((int)checkingInTime>0)returnString=returnString+
                    (int)checkingInTime+" hours ";
            if((int)((checkingInTime%1)*60)>0)returnString=returnString+
                    (int)((checkingInTime%1)*60)+" minutes ";
            returnString=returnString+
                    ((((checkingInTime%1)*60)%1)*60)+" seconds ";
            returnString=returnString+
                    "\nTime spent printing: ";
            if((int)printTime>0)returnString=returnString+
                    (int)printTime+" hours ";
            if((int)((printTime%1)*60)>0)returnString=returnString+
                    (int)((printTime%1)*60)+" minutes ";
            returnString=returnString+
                    ((((printTime%1)*60)%1)*60)+" seconds ";
                        returnString=returnString+
                    "\nTime spent bag checking: ";
            if((int)bagTime>0)returnString=returnString+
                    (int)bagTime+" hours ";
            if((int)((bagTime%1)*60)>0)returnString=returnString+
                    (int)((bagTime%1)*60)+" minutes ";
            returnString=returnString+
                    ((((bagTime%1)*60)%1)*60)+" seconds ";
            
            returnString=returnString+
                    "\nProblem Time: ";
            if((int)problemTime>0)returnString=returnString+
                    (int)problemTime+" hours ";
            if((int)((problemTime%1)*60)>0)returnString=returnString+
                    (int)((problemTime%1)*60)+" minutes ";
            returnString=returnString+
                    ((((problemTime%1)*60)%1)*60)+" seconds ";
            
            
            returnString=returnString+
                    "\nCheck-out Time: Day "+(int)((checkOutTime/24)+1)+", ";
            returnString=returnString+
                    (int)(checkOutTime%24)+":";
            if((int)((checkOutTime%1)*60)<10)returnString=returnString+"0";
            returnString=returnString+
                    (int)((checkOutTime%1)*60)+" and "+
                    ((((checkOutTime%1)*60)%1)*60)+" seconds "+
                    "\nFlight Type: "+type+
                    "\nBag Count: "+numberOfBags+
                    "\nTime spent in Security: ";
            if((int)securityTime>0)returnString=returnString+
                    (int)securityTime+" hours ";
            if((int)((securityTime%1)*60)>0)returnString=returnString+
                    (int)((securityTime%1)*60)+" minutes ";
            returnString=returnString+
                    ((((securityTime%1)*60)%1)*60)+" seconds ";
            
            returnString=returnString+
                    "\nArrival at Gate: Day "+(int)((gateTime/24)+1)+", ";
            returnString=returnString+
                    (int)(gateTime%24)+":";
            if((int)((gateTime%1)*60)<10)returnString=returnString+"0";
            returnString=returnString+
                    (int)((gateTime%1)*60)+" and "+
                    ((((gateTime%1)*60)%1)*60)+" seconds ";
            if (getFlightTime()!=-1){
                returnString=returnString+
                    "\nFlight Time: Day "+(int)((flightTime/24)+1)+", ";
                returnString=returnString+
                        (int)(flightTime%24)+":";
                if((int)((flightTime%1)*60)<10)returnString=returnString+"0";
                returnString=returnString+
                        (int)((flightTime%1)*60)+" and "+
                        ((((flightTime%1)*60)%1)*60)+" seconds ";
            }
            if(getCheckInWaitTime()>0){
                returnString=returnString+
                    "\nTime spent waiting in check-in line ";
                if((int)getCheckInWaitTime()>0)returnString=returnString+
                        (int)getCheckInWaitTime()+" hours ";
                if((int)((getCheckInWaitTime()%1)*60)>0)returnString=returnString+
                        (int)((getCheckInWaitTime()%1)*60)+" minutes ";
                returnString=returnString+
                        ((((getCheckInWaitTime()%1)*60)%1)*60)+" seconds ";
            }
            if(getSecurityWaitTime()>0){
                returnString=returnString+
                    "\nTime spent waiting in security line ";
                if((int)getSecurityWaitTime()>0)returnString=returnString+
                        (int)getSecurityWaitTime()+" hours ";
                if((int)((getSecurityWaitTime()%1)*60)>0)returnString=returnString+
                        (int)((getSecurityWaitTime()%1)*60)+" minutes ";
                returnString=returnString+
                        ((((getSecurityWaitTime()%1)*60)%1)*60)+" seconds ";
            }
            if(type.equalsIgnoreCase("commute")){
                returnString=returnString+
                    "\nTime spent waiting at gate:";
                if((int)getGateWaitingTime()>0)returnString=returnString+
                        (int)getGateWaitingTime()+" hours ";
                if((int)((getGateWaitingTime()%1)*60)>0)returnString=returnString+
                        (int)((getGateWaitingTime()%1)*60)+" minutes ";
                returnString=returnString+
                        ((((getGateWaitingTime()%1)*60)%1)*60)+" seconds ";
            }
            return returnString;
        }
                
                
    }

    /**
     * @return the flightTime
     */
    public double getFlightTime() {
        return flightTime;
    }

    /**
     * @param flightTime the flightTime to set
     */
    public void setFlightTime(double flightTime) {
        this.flightTime = flightTime;
    }
    
    /**
     * @return the time spent in the security line
     */
    public double getSecurityWaitTime() {
        return securityStartTime-checkOutTime;
    }   
    
    /**
     * @return the securityTime
     */
    public double getSecurityTime() {
        return securityTime;
    }

    /**
     * @param securityTime the securityTime to set
     */
    public void setSecurityTime(double securityTime) {
        this.securityTime = securityTime;
    }

    /**
     * @return the gateTime
     */
    public double getGateTime() {
        return gateTime;
    }

    /**
     * @param gateTime the gateTime to set
     */
    public void setGateTime(double gateTime) {
        this.gateTime = gateTime;
    }

    /**
     * @return the securityStartTime
     */
    public double getSecurityStartTime() {
        return securityStartTime;
    }

    /**
     * @param securityStartTime the securityStartTime to set
     */
    public void setSecurityStartTime(double securityStartTime) {
        this.securityStartTime = securityStartTime;
    }
    
    /**
     * @return the securityStartTime
     */
    public double getGateWaitingTime() {
        return flightTime-gateTime;
    }

    /**
     * @return the printTime
     */
    public double getPrintTime() {
        return printTime;
    }

    /**
     * @param printTime the printTime to set
     */
    public void setPrintTime(double printTime) {
        this.printTime = printTime;
    }

    /**
     * @return the bagTime
     */
    public double getBagTime() {
        return bagTime;
    }

    /**
     * @param bagTime the bagTime to set
     */
    public void setBagTime(double bagTime) {
        this.bagTime = bagTime;
    }

    /**
     * @return the problemTime
     */
    public double getProblemTime() {
        return problemTime;
    }

    /**
     * @param problemTime the problemTime to set
     */
    public void setProblemTime(double problemTime) {
        this.problemTime = problemTime;
    }

    /**
     * @return the missed
     */
    public boolean isMissed() {
        return missed;
    }

    /**
     * @param missed the missed to set
     */
    public void setMissed(boolean missed) {
        this.missed = missed;
    }
}
