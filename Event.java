/**
 *
 * @author Benjamin
 * 
 */
public class Member {
    private double arrivalTime;
    private double startTime;
    private double endTime;
    private boolean usedBench;
    private int liftType;	
    
    public Member(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }    

    /**
     * @return the time spent waiting
     */
    public double getWaitTime() {
        return startTime-arrivalTime;
    }
    /**
     * @return the time spent lifting
     */
    public double getLiftTime() {
        return endTime-startTime;
    }
    
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
     * @return the time started lifting
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the lifting startTime to set
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getLiftType() {
        return liftType;
    }

    /**
     * @param startTime the lifting startTime to set
     */
    public void setLiftType(int liftType) {
        this.liftType = liftType;
    }

    /**
     * @return the time ended lifting
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the lifting endTime to set
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
	public boolean getUsedBench(){
		return usedBench;
	}
	public void setUsedBench(boolean usedBench){
		this.usedBench = usedBench;
	}

}
