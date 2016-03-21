package sample;

/**
 * Created by sathaye on 3/8/16.
 */
public class Progress {
    private int processID;
    private int startTime;
    private int burstTime;
    private String processIDString;


    public Progress(int processID,int startTime,int burstTime){
        this.processID = processID;
        this.startTime = startTime;
        this.burstTime = burstTime;
        if(this.processID == 0){
            this.processIDString = "one";
        }
        else if(this.processID == 1){
            this.processIDString = "two";
        }
        else if(this.processID == 2){
            this.processIDString = "three";
        }
        else if(this.processID == 3)
            this.processIDString = "four";
        else
            this.processIDString = "five";
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public String getProcessIDString() {
        return processIDString;
    }
}
