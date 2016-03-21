package sample;
import java.util.*;

/**
 * Created by sathaye on 3/6/16.
 */

public class Multilevelfeedback {
    private int totalTime = 0;
    private Queue<Job> runningQueue = new LinkedList<>();
    private ArrayList<Progress> processExecutionTrail = new ArrayList<>();
    private Queue<Job>[] multiLevelQueues = new Queue[3];
    private int quantum =3;
    private Queue<Job> doneQueue = new LinkedList<>();
    private int[] BT;
    private int WT[] = new int[5];
    private int[] AT;
    private int[] priority;

    public Multilevelfeedback(){
        for(int i=0;i<3;i++)
            multiLevelQueues[i] = new LinkedList<>();
    }

    public void populateQueue(int[] arrivalTime,int[] burstTime,int[] priority){
        int processID=0;
        TreeMap<Integer,Job>[] multiLevelTreeMap = new TreeMap[3];
        TreeMap<Integer, Job> presortedJobs = new TreeMap<>();
        for(int i=0;i<5;i++){
            Job j = new Job(arrivalTime[i],burstTime[i]);
            j.setPriority(priority[i]);
            presortedJobs.put(arrivalTime[i],j);
        }
        for(Map.Entry sortjobs: presortedJobs.entrySet()){
            Job j = (Job)sortjobs.getValue();
            j.setProcessID(processID);
            processID++;
        }

        for(int i=0;i<3;i++)
            multiLevelTreeMap[i] = new TreeMap<>();

        for(Map.Entry jobs : presortedJobs.entrySet()){
            Job j = (Job) jobs.getValue();
            if(j.getPriority() == 0)
                multiLevelTreeMap[0].put(j.getArrivalTime(),j);
            else if(j.getPriority() == 1)
                multiLevelTreeMap[1].put(j.getArrivalTime(),j);
            else if(j.getPriority() == 2)
                multiLevelTreeMap[2].put(j.getArrivalTime(),j);

        }

        for(int i=0;i<3;i++){
            for(Map.Entry e:multiLevelTreeMap[i].entrySet()){
                //multiLevelQueues[i].add((Job)e.getValue());
                multiLevelQueues[i].add((Job)e.getValue());
                processID++;
            }
        }
    }

    public void calculateTimes(){
        populateQueue(this.AT,this.BT,this.priority);
        int globalTimeslice=1;
        while(true) {
            if (donewithProcesses())
                break;
            for (int i = 0; i < 3; i++) {
//                if (globalTimeslice == 0) {
//                    globalTimeslice+=(globalTimeslice+1)%2;
//                }
                globalTimeslice = 1;
                while (!multiLevelQueues[i].isEmpty()) {
                    if(i==1){
                        if(!multiLevelQueues[i-1].isEmpty()){
                            if(totalTime >= multiLevelQueues[i-1].peek().getArrivalTime()) {
                                i = -1;
                                globalTimeslice = 1;
                                break;
                            }
                        }
                    }
                    if(i==2){
                        if(!multiLevelQueues[i-2].isEmpty()) {
                            if (totalTime >= multiLevelQueues[i - 2].peek().getArrivalTime()) {
                                i = -1;
                                globalTimeslice = 1;
                                break;
                            }
                        }
                        if(!multiLevelQueues[i-1].isEmpty()) {
                            if (totalTime >= multiLevelQueues[i - 1].peek().getArrivalTime()) {
                                i = 0;
                                globalTimeslice = 1;
                                break;
                            }
                        }
                    }
                    System.out.println("Inside each queue");
                    if (globalTimeslice == 0) {
                        globalTimeslice+=(globalTimeslice+1)%2;
                        break;
                    }
                    Job currentJob = multiLevelQueues[i].peek();
                    if (totalTime >= currentJob.getArrivalTime()) {
                        currentJob = multiLevelQueues[i].remove();
                        if (currentJob.getBurstTime() > quantum) {
                            processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,quantum));
                            currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
                            currentJob.setBurstTime(currentJob.getBurstTime() - quantum);
                            currentJob.setArrivalTime(totalTime + quantum);
                            totalTime += quantum;
                            if(i!=2) {
                                multiLevelQueues[i + 1].add(currentJob);
                            }
                            else
                                multiLevelQueues[i].add(currentJob);

                        } else {
                            processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,currentJob.getBurstTime()));
                            currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
                            totalTime += currentJob.getBurstTime();
                            doneQueue.add(currentJob);
                        }
                        globalTimeslice = (globalTimeslice + 1) % 3;
                    } else {
                        totalTime++;
                        globalTimeslice = (globalTimeslice + 1) % 3;
                    }

                }
            }
        }
        for(Job j:doneQueue){
            this.WT[j.getProcessID()] = j.getWaitTime();
//            System.out.println("In done queq");
//            System.out.println(j.getOriginalArrivalTime()+"\t"+j.getWaitTime());
        }
//        System.out.println("===================================");
//        for(Progress p : processExecutionTrail){
//            System.out.println(p.getProcessID()+"\t"+p.getStartTime());
//        }
        System.out.println("MLFQ Progress");
        for(Progress p: processExecutionTrail){
            System.out.println(p.getProcessID()+"\t"+p.getStartTime());
        }
        System.out.println("=====================================");

    }
    public ArrayList<Progress> getProcessExecutionTrail(){
        return processExecutionTrail;
    }
    private int getProcessIndex(int originalArrivalTime){
        int index = -1;
        for(int i=0;i<5;i++){
            if(AT[i] == originalArrivalTime)
                return i;
        }
        return index;
    }

    private boolean donewithProcesses() {
        if(multiLevelQueues[0].isEmpty() && multiLevelQueues[1].isEmpty() && multiLevelQueues[2].isEmpty()){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Multilevelfeedback mlfq = new Multilevelfeedback();
        int[] AT = {1,2,3,4,5};
        int[] BT = {6,4,2,4,4};
        int[] P =  {1,2,0,0,2};
        mlfq.populateQueue(AT,BT,P);
        mlfq.calculateTimes();
    }

    public int[] getWaitingTime() {
        return WT;
    }

    public void setBurstTime(int[] burstTime) {
        this.BT = burstTime;
    }

    public void setArrivalTime(int[] arrivalTime) {
        this.AT = arrivalTime;
    }

    public void setPriority(int[] priority) {
        this.priority = priority;
    }
}