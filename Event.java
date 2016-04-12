public class Event implements Comparable<Event> {
    private Double time;
    private int eventType;
    private Member member;
    
    public Event(double time, int eventType) {
        this.time = time;
        this.eventType = eventType;
        this.member = null;
    }
    
    public Event(double time, int eventType, Member member) {
        this.time = time;
        this.eventType = eventType;
        this.member = member;
    }    

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public double getTime() {
        return time;
    }

    public int getEventType() {
        return eventType;
    }  
    
    public void setTime(double time) {
        this.time = time;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    @Override
    public int compareTo(Event o) {
      return (this.time).compareTo(o.getTime());
    }
    
    
}
