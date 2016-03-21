package sample;
import java.util.*;

/**
 * Created by sathaye on 3/5/16.
 */
public class RR{
    private int processCount = 0;
    private int totalTime = 0;
    private int AT[], BT[],BTcopy[];
    private int WT[] = new int[5];
    private int TAT[] = new int[5];
    private TreeMap<Integer,Integer> jobs = new TreeMap<>();
    private List<Job> runningQueue = new LinkedList<>();
    private Queue<Job> doneQueue = new LinkedList<>();
    private ArrayList<Progress> processExecutionTrail = new ArrayList<>();
    private List<Job> sortList = new LinkedList<>();
    private int[] RT = new int[5];
    private int[] burstTime2;
    private int[] IOTime;
    private int[] IOAT = new int[5];
    boolean[] visited = new boolean[5];
    int LastIOTime = 0;

    public void setBurstTime(int[] timeArray) {
        this.BT = timeArray;
        this.BTcopy = timeArray;
    }

    public void setArrivalTime(int[] arrivalTime) {
        this.AT = arrivalTime;
    }

    public void calculateTimes() {
        for(int i=0;i<5;i++){
        //    jobs.put(this.AT[i],this.BT[i]);
            sortList.add(new Job(AT[i],BT[i]));
            //sortList = sortAccordingToArrivalTime(sortList);

        }
        sortList = sortAccordingToArrivalTime(sortList);
//        for(Map.Entry entry:jobs.entrySet()){
//            runningQueue.add(new Job((Integer)entry.getKey(),(Integer)entry.getValue(),processID));
//            processID++;
//        }

        int processID = 0;
        for(Job j : sortList){
            runningQueue.add(new Job(j.getArrivalTime(),j.getBurstTime(),processID));
            processID++;
        }

        int quantum = 3;
        //int maxValue = this.AT[findMax(AT)];

        while(!runningQueue.isEmpty()){
            Job currentJob =runningQueue.get(0);
            if(totalTime >= currentJob.getArrivalTime()){
//
                System.out.println("======================QUANTUM============");
                quantum = findNewMedian(runningQueue);
                System.out.println(quantum);

                currentJob = runningQueue.remove(0);
                if(currentJob.getBurstTime() > quantum){
                    processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,quantum));
                    currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
                    if(currentJob.getVisited() != 1){
                        RT[currentJob.getProcessID()] = totalTime - currentJob.getOriginalArrivalTime();
                        currentJob.setVisited(1);
                    }
                    currentJob.setBurstTime(currentJob.getBurstTime() - quantum);
                    currentJob.setArrivalTime(totalTime + quantum);
                    totalTime += quantum;
                    runningQueue.add(currentJob);
                    runningQueue = sortAccordingToArrivalTime(runningQueue);
                }
                else{
                    processExecutionTrail.add(new Progress(currentJob.getProcessID(),totalTime,currentJob.getBurstTime()));
                    currentJob.setWaitTime(currentJob.getWaitTime() + totalTime - currentJob.getArrivalTime());
                    int tempTime = totalTime;
                    if(currentJob.getVisited() != 1){
                        RT[currentJob.getProcessID()] = totalTime - currentJob.getOriginalArrivalTime();
                        currentJob.setVisited(1);
                    }
                    totalTime += currentJob.getBurstTime();
                    if(currentJob.getVisited() == 1)
                        TAT[currentJob.getProcessID()] = totalTime - AT[currentJob.getProcessID()];
                    doneQueue.add(currentJob);
                    int pid = currentJob.getProcessID();
                    if(!visited[pid]) {
                        currentJob.setResponseTime(tempTime - currentJob.getOriginalArrivalTime());
                        IOAT[pid] = totalTime;
                        if (totalTime < LastIOTime) {
                            LastIOTime += IOTime[currentJob.getProcessID()];
                        } else {
                            LastIOTime = totalTime + IOTime[currentJob.getProcessID()];
                        }
                        Job reentry = new Job(LastIOTime, burstTime2[pid],pid );
                        reentry.setVisited(1);

                        runningQueue.add(reentry);
                        runningQueue = sortAccordingToArrivalTime(runningQueue);
                    }

                    visited[pid] = true;
                }
            }else {
                totalTime++;
            }
        }
        for(Job j:doneQueue){
            this.WT[j.getProcessID()] = j.getWaitTime();
//            this.TAT[j.getProcessID()] = j.getTurnAroundTime();
//            this.RT[j.getProcessID()] = j.getResponseTime();
//            System.out.println(j.getOriginalArrivalTime()+"\t"+j.getWaitTime());
        }
//        for(int i=0;i<5;i++){
//            System.out.println(this.AT[i]+"\t"+this.WT[i]);
//        }
        System.out.println("RR Progress");
        for(Progress p: processExecutionTrail){
            System.out.println(p.getProcessID()+"\t"+p.getStartTime());
        }
        System.out.println("=====================================");

    }

    private int findNewMedian(List<Job> runningQueue) {
        int newQuantum=3;
        int count=0;
        int tempBurstTime=0;
        for(Job j : runningQueue){
            if(totalTime >= j.getArrivalTime()){
                tempBurstTime+=j.getBurstTime();
                count++;
            }
        }
        if(count != 1){
            newQuantum = tempBurstTime/count;
        }
        return newQuantum;
    }

    private int getProcessIndex(int originalArrivalTime){
        int index = -1;
        for(int i=0;i<5;i++){
            if(AT[i] == originalArrivalTime)
                return index = i;
        }
        return index;
    }

    private int findMax(int[] bt) {
        int maxValue = Integer.MIN_VALUE;
        int index=-1;
        for (int i = 0; i < 5; i++) {
            if (bt[i] > maxValue) {
                maxValue = bt[i];
                index = i;
            }
        }
        return index;
    }


    private int findMin(int[] bt) {
        int minValue = Integer.MAX_VALUE;
        int index=-1;
        for (int i = 0; i < 5; i++) {
            if (bt[i] < minValue) {
                minValue = bt[i];
                index = i;
            }
        }
        return index;
    }

    public ArrayList<Progress> getProcessExecutionTrail(){
        return processExecutionTrail;
    }
//    public static void main(String[] args){
//        RR rr = new RR();
//        int[] AT = {1,2,3,4,5};
//        int[] BT = {1,6,2,3,5};
//        rr.setArrivalTime(AT);
//        rr.setBurstTime(BT);
//        rr.calculateTimes();
//    }

    public int[] getWaitingTime() {
        return this.WT;
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
    public int[] getTAT() {
        return TAT;
    }
    public int[] getRT() {
        return RT;
    }

    public void setRT(int[] RT) {
        this.RT = RT;
    }

    public void setBurstTime2(int[] burstTime2) {
        this.burstTime2 = burstTime2;
    }

    public void setIOTime(int[] IOTime) {
        this.IOTime = IOTime;
    }
}