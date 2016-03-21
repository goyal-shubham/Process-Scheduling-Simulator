package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by sathaye on 3/11/16.
 */
public class MLFQSJF {
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
    private int[] burstTime2;
    private int[] IOTime;
    private int[] IOAT = new int[5];
    boolean[] visited = new boolean[5];
    int LastIOTime = 0;

    public MLFQSJF(){
        for(int i=0;i<3;i++)
            multiLevelQueues[i] = new LinkedList<>();
    }

    public void populateQueue(int[] arrivalTime,int[] burstTime,int[] priority){
        int processID=0;
        List<Job> presortedJobs = new LinkedList<>();

        for(int i=0;i<5;i++){
            Job j = new Job(arrivalTime[i],burstTime[i], i);
            j.setPriority(priority[i]);
            presortedJobs.add(j);
        }
        presortedJobs = sortAccordingToArrivalTime(presortedJobs);

        for(Job j: presortedJobs){
            if(j.getPriority() == 0)
                multiLevelQueues[0].add(j);
            else if(j.getPriority() == 1)
                multiLevelQueues[1].add(j);
            else
                multiLevelQueues[2].add(j);
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
        while(!donewithProcesses()) {

            for (int i = 0; i < 3; i++) {

                while(!multiLevelQueues[i].isEmpty()){

                    Job currentshortestJob = findMinJob((multiLevelQueues[i]));
                    if (currentshortestJob != null) {

                        multiLevelQueues[i].remove(currentshortestJob);
                        if (currentshortestJob.getBurstTime() > quantum) {
                            processExecutionTrail.add(new Progress(currentshortestJob.getProcessID(), totalTime, quantum));
                            currentshortestJob.setWaitTime(currentshortestJob.getWaitTime() + totalTime - currentshortestJob.getArrivalTime());
                            if(currentshortestJob.getVisited() != 1){
                                RT[currentshortestJob.getProcessID()] = totalTime - currentshortestJob.getOriginalArrivalTime();
                                currentshortestJob.setVisited(1);
                            }
                            currentshortestJob.setBurstTime(currentshortestJob.getBurstTime() - quantum);
                            currentshortestJob.setArrivalTime(totalTime + quantum);
                            totalTime += quantum;
                            if (i != 2) {
                                multiLevelQueues[i + 1].add(currentshortestJob);
                                multiLevelQueues[i + 1] = sortAccordingToArrivalTime(multiLevelQueues[i + 1]);
                            } else {
                                multiLevelQueues[i].add(currentshortestJob);
                                multiLevelQueues[i] = sortAccordingToArrivalTime(multiLevelQueues[i]);
                            }
                        } else {
                            processExecutionTrail.add(new Progress(currentshortestJob.getProcessID(), totalTime, currentshortestJob.getBurstTime()));
                            currentshortestJob.setWaitTime(currentshortestJob.getWaitTime() + totalTime - currentshortestJob.getArrivalTime());
                            int tempTime = totalTime;
                            if(currentshortestJob.getVisited() != 1){
                                RT[currentshortestJob.getProcessID()] = totalTime - currentshortestJob.getOriginalArrivalTime();
                                currentshortestJob.setVisited(1);
                            }
                            totalTime += currentshortestJob.getBurstTime();
                            if(currentshortestJob.getVisited() == 1)
                                TAT[currentshortestJob.getProcessID()] = totalTime - AT[currentshortestJob.getProcessID()];
                            doneQueue.add(currentshortestJob);
                            int pid = currentshortestJob.getProcessID();
                            if(!visited[pid]) {
                                currentshortestJob.setResponseTime(tempTime - currentshortestJob.getOriginalArrivalTime());
                                IOAT[pid] = totalTime;
                                if (totalTime < LastIOTime) {
                                    LastIOTime += IOTime[currentshortestJob.getProcessID()];
                                } else {
                                    LastIOTime = totalTime + IOTime[currentshortestJob.getProcessID()];
                                }
                                Job reentry = new Job(LastIOTime,burstTime2[pid],pid);
                                reentry.setVisited(1);
                                multiLevelQueues[i].add(reentry);
                                multiLevelQueues[i] = sortAccordingToArrivalTime(multiLevelQueues[i]);
                            }
                            visited[pid] = true;
                        }

                        if (i == 1) {
                            if (!multiLevelQueues[i - 1].isEmpty()) {
                                if (totalTime >= multiLevelQueues[i - 1].get(0).getArrivalTime()) {
                                    i = -1;
                                    break;
                                }
                            }
                        }
                        if (i == 2) {
                            if (!multiLevelQueues[i - 2].isEmpty()) {
                                if (totalTime >= multiLevelQueues[i - 2].get(0).getArrivalTime()) {
                                    i = -1;
                                    break;
                                }
                            }
                            if (!multiLevelQueues[i - 1].isEmpty()) {
                                if (totalTime >= multiLevelQueues[i - 1].get(0).getArrivalTime()) {
                                    i = 0;
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }


                }
            }
            totalTime++;
        }
//                while (!multiLevelQueues[i].isEmpty()) {
//                    if(totalTime < multiLevelQueues[i].get(0).getArrivalTime()){
//                        //totalTime++;
//                        break;
//                    }
//                    System.out.println("Inside each queue");
//                    Job currentJob = multiLevelQueues[i].get(0);
//                    if (totalTime >= currentJob.getArrivalTime()) {
//                        Job currentshortestJob = findMinJob((multiLevelQueues[i]));
//                        if (currentshortestJob!= null && totalTime >= currentshortestJob.getArrivalTime()) {
//                            processExecutionTrail.add(new Progress(currentshortestJob.getProcessID(),
//                                    totalTime, currentshortestJob.getBurstTime()));
//                            currentshortestJob.setWaitTime(totalTime - currentshortestJob.getArrivalTime());
//                            currentshortestJob.setResponseTime(totalTime - currentshortestJob.getOriginalArrivalTime());
//                            totalTime += currentshortestJob.getBurstTime();
//                            doneQueue.add(currentshortestJob);
//                            currentshortestJob.setTurnAroundTime(totalTime - currentshortestJob.getOriginalArrivalTime());
//                            multiLevelQueues[i].remove(currentshortestJob);
//                        }
//                        else if(totalTime >= currentJob.getArrivalTime()){
//                            processExecutionTrail.add(new Progress(currentJob.getProcessID(),
//                                    totalTime, currentJob.getBurstTime()));
//                            currentJob.setWaitTime(totalTime - currentJob.getArrivalTime());
//                            currentJob.setResponseTime(totalTime - currentJob.getOriginalArrivalTime());
//                            totalTime += currentJob.getBurstTime();
//                            currentJob.setTurnAroundTime(totalTime - currentJob.getOriginalArrivalTime());
//                            doneQueue.add(currentJob);
//                            multiLevelQueues[i].remove(currentJob);
//                        }
////                        else{
////                            totalTime++;
////                        }
//                    }
////                    else {
////                     totalTime++;
////                    }
//
//                }
//
//            }
//            totalTime++;
//        }
        for(Job j:doneQueue){
            this.WT[j.getProcessID()] = j.getWaitTime();
//
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


    private Job findMinJob(List<Job> shortestJobList){
        int minValue = Integer.MAX_VALUE;
        Job sJ = null;
        for(Job shortest :shortestJobList){
            if(minValue >=shortest.getBurstTime() && totalTime >= shortest.getArrivalTime()){
                sJ = shortest;
                minValue = shortest.getBurstTime();
            }
        }
        return sJ;
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
        this.burstTime2 = burstTime2;
    }

    public void setIOTime(int[] IOTime) {
        this.IOTime = IOTime;
    }
}
