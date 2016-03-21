package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by sathaye on 3/11/16.
 */
public class MLFQFCFS {
    private int totalTime = 0;
    private Queue<Job> runningQueue = new LinkedList<>();
    private ArrayList<Progress> processExecutionTrail = new ArrayList<>();

    private List<Job>[] multiLevelQueues = new List[3];
    private int quantum =3;
    private List<Job> doneQueue = new LinkedList<>();
    private int[] BT;
    private int WT[] = new int[5];
    private int TAT[] = new int[5];
    private int RT[] = new int[5];
    private int[] AT;
    private int[] priority;
    private int[] BT2;
    private int[] IOTime;
    private int[] IOAT = new int[5];
    boolean[] visited = new boolean[5];
    int LastIOTime = 0;

    public MLFQFCFS(){
        for(int i=0;i<3;i++)
            multiLevelQueues[i] = new LinkedList<>();
    }

    public void populateQueue(int[] arrivalTime,int[] burstTime,int[] priority){
        int processID=0;
        List<Job> presortedJobs = new LinkedList<>();

        for(int i=0;i<5;i++){
            Job j = new Job(arrivalTime[i],burstTime[i]);
            j.setPriority(priority[i]);
            presortedJobs.add(j);
        }
        presortedJobs = sortAccordingToArrivalTime(presortedJobs);

        for(Job j: presortedJobs){
            if(j.getPriority() == 0)
                multiLevelQueues[0].add(new Job(j.getArrivalTime(),j.getBurstTime(),processID));
            else if(j.getPriority() == 1)
                multiLevelQueues[1].add(new Job(j.getArrivalTime(),j.getBurstTime(),processID));
            else if(j.getPriority() == 2)
                multiLevelQueues[2].add(new Job(j.getArrivalTime(),j.getBurstTime(),processID));
            processID++;
        }


//        for(int i=0;i<5;i++){
//            Job j = new Job(arrivalTime[i],burstTime[i]);
//            j.setPriority(priority[i]);
//            presortedJobs.put(arrivalTime[i],j);
//        }
//        for(Map.Entry sortjobs: presortedJobs.entrySet()){
//            Job j = (Job)sortjobs.getValue();
//            j.setProcessID(processID);
//            processID++;
//        }
//
//        for(int i=0;i<3;i++)
//            multiLevelTreeMap[i] = new TreeMap<>();
//
//        for(Map.Entry jobs : presortedJobs.entrySet()){
//            Job j = (Job) jobs.getValue();
//            if(j.getPriority() == 0)
//                multiLevelTreeMap[0].put(j.getArrivalTime(),j);
//            else if(j.getPriority() == 1)
//                multiLevelTreeMap[1].put(j.getArrivalTime(),j);
//            else if(j.getPriority() == 2)
//                multiLevelTreeMap[2].put(j.getArrivalTime(),j);
//
//        }
//
//        for(int i=0;i<3;i++){
//            for(Map.Entry e:multiLevelTreeMap[i].entrySet()){
//                //multiLevelQueues[i].add((Job)e.getValue());
//                multiLevelQueues[i].add((Job)e.getValue());
//                processID++;
//            }
//        }
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
                    if(totalTime < multiLevelQueues[i].get(0).getArrivalTime()){
                        totalTime++;
                        break;
                    }
                    if(i==1){
                        if(!multiLevelQueues[i-1].isEmpty()){
                            if(totalTime >= multiLevelQueues[i-1].get(0).getArrivalTime()) {
                                i = -1;
                                globalTimeslice = 1;
                                break;
                            }
                        }
                    }
                    if(i==2){
                        if(!multiLevelQueues[i-2].isEmpty()) {
                            if (totalTime >= multiLevelQueues[i - 2].get(0).getArrivalTime()) {
                                i = -1;
                                globalTimeslice = 1;
                                break;
                            }
                        }
                        if(!multiLevelQueues[i-1].isEmpty()) {
                            if (totalTime >= multiLevelQueues[i - 1].get(0).getArrivalTime()) {
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
                    Job currentJob = multiLevelQueues[i].get(0);
                    if (totalTime >= currentJob.getArrivalTime()) {
                        currentJob = multiLevelQueues[i].remove(0);
                        processExecutionTrail.add(new Progress(currentJob.getProcessID(),
                                totalTime, currentJob.getBurstTime()));
                        currentJob.setWaitTime(totalTime - currentJob.getArrivalTime());
                        currentJob.setResponseTime(totalTime - currentJob.getOriginalArrivalTime());
                        totalTime += currentJob.getBurstTime();
                        currentJob.setTurnAroundTime(totalTime - currentJob.getOriginalArrivalTime());
                        doneQueue.add(currentJob);
                        runningQueue.remove(currentJob);
                        int pid = currentJob.getProcessID();
                        if(!visited[pid]) {
                            IOAT[pid] = totalTime;
                            if (totalTime < LastIOTime) {
                                LastIOTime += IOTime[currentJob.getProcessID()];
                            } else {
                                LastIOTime = totalTime + IOTime[currentJob.getProcessID()];
                            }
                            if(currentJob.getPriority() == 0){
                                multiLevelQueues[0].add(new Job(LastIOTime,BT2[pid],pid));
                                multiLevelQueues[0] = sortAccordingToArrivalTime(multiLevelQueues[0]);
                            }
                            else if(currentJob.getPriority() == 1){
                                multiLevelQueues[1].add(new Job(LastIOTime,BT2[pid],pid));
                                multiLevelQueues[1] = sortAccordingToArrivalTime(multiLevelQueues[1]);
                            }
                            else if(currentJob.getPriority() == 2) {
                                multiLevelQueues[2].add(new Job(LastIOTime, BT2[pid], pid));
                                multiLevelQueues[2] = sortAccordingToArrivalTime(multiLevelQueues[2]);
                            }
                        }
                        visited[pid] = true;
//                        if (currentJob.getBurstTime() > quantum) {
//                            processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,quantum));
//                            currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
//                            if(currentJob.getArrivalTime() == currentJob.getOriginalArrivalTime())
//                                currentJob.setResponseTime(totalTime - currentJob.getOriginalArrivalTime());
//                            currentJob.setBurstTime(currentJob.getBurstTime() - quantum);
//                            currentJob.setArrivalTime(totalTime + quantum);
//                            totalTime += quantum;
//                            if(i!=2) {
//                                multiLevelQueues[i + 1].add(currentJob);
//                                multiLevelQueues[i+1] = sortAccordingToArrivalTime(multiLevelQueues[i+1]);
//                            }
//                            else{
//                                multiLevelQueues[i].add(currentJob);
//                                multiLevelQueues[i] = sortAccordingToArrivalTime(multiLevelQueues[i]);
//                            }
//
//                        } else {
//                            processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,currentJob.getBurstTime()));
//                            currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
//                            if(currentJob.getArrivalTime() == currentJob.getOriginalArrivalTime())
//                                currentJob.setResponseTime(totalTime - currentJob.getOriginalArrivalTime());
//                            totalTime += currentJob.getBurstTime();
//                            currentJob.setTurnAroundTime(totalTime - currentJob.getOriginalArrivalTime());
//                            doneQueue.add(currentJob);
//                        }
//                        globalTimeslice = (globalTimeslice + 1) % 3;
                    }
                else {
                        totalTime++;
                        globalTimeslice = (globalTimeslice + 1) % 3;
                    }

                }
            }
        }
        for(Job j:doneQueue){
            this.WT[j.getProcessID()] = j.getWaitTime();
            this.TAT[j.getProcessID()] = j.getTurnAroundTime();
            this.RT[j.getProcessID()] = j.getResponseTime();
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

    public List<Job> sortAccordingToArrivalTime(List<Job> arrivalTimeList){
        List<Job> sortedArrivalTimeList = new LinkedList<>();
        Job tempJob=null;
        while(!arrivalTimeList.isEmpty()){
            int minValue = Integer.MAX_VALUE;
            for(Job j:arrivalTimeList){
                if(minValue > j.getArrivalTime()){
                    minValue  = j.getArrivalTime();
                    tempJob = j;
                }
            }
            arrivalTimeList.remove(tempJob);
            sortedArrivalTimeList.add(tempJob);
        }
        return sortedArrivalTimeList;
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

    public int[] getTAT() {
        return TAT;
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

    public int[] getRT() {
        return RT;
    }

    public void setRT(int[] RT) {
        this.RT = RT;
    }

    public void setBurstTime2(int[] burstTime2) {
        this.BT2 = burstTime2;
    }

    public void setIOTime(int[] IOTime) {
        this.IOTime = IOTime;
    }
}
