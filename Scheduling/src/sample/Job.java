package sample;

/**
 * Created by sathaye on 3/6/16.
 */
class Job{
    private int priority;
    private int processID;
    private int originalArrivalTime;
    private int originalBurstTime;
    private int arrivalTime;
    private int burstTime;
    private int waitTime;
    private int turnAroundTime;
    private int responseTime;
    private int visited =0;

    public Job(int arrivalTime, int burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalArrivalTime = arrivalTime;
        this.visited = 0;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Job(){}
    public Job(int arrivalTime,int burstTime,int processID){
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalArrivalTime = arrivalTime;
        this.originalBurstTime = burstTime;
        this.processID = processID;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getOriginalArrivalTime() {
        return originalArrivalTime;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }
}
