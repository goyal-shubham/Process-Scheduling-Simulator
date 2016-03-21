package sample;

/**
 * Created by sathaye on 3/4/16.
 */

import java.io.*;
import java.util.*;

public class FCFS
{
    private int totalTime = 0;
    private int AT[],BT[];
    private int WT[] = new int[5];
    private int TAT[] = new int[5];
    private int RT[] = new int[5];
    private ArrayList<Progress> processExecutionTrail = new ArrayList<>();
    private TreeMap<Integer,Integer> earliestJob = new TreeMap<>();
    private List<Job> runningQueue = new LinkedList<>();
    private Queue<Job> shorterQueue = new LinkedList<>();
    private Queue<Job> doneQueue = new LinkedList<>();
    private List<Job> sortList = new LinkedList<>();
    private int[] BT2;
    private int[] IOTime;
    private int[] IOAT = new int[5];
    boolean[] visited = new boolean[5];
    int LastIOTime = 0;

    public int[] getWaitingTime(){
        return WT;
    }

    public int[] getTAT(){
        return TAT;
    }

    public void setBurstTime(int [] burstTime){
        this.BT = burstTime;
    }

    public void setArrivalTime(int[] arrivalTime){
        this.AT = arrivalTime;
    }

    public void calculateTimes() throws Exception
    {
        for (int i = 0; i < 5; i++) {

            sortList.add(new Job(AT[i],BT[i], i));

            //earliestJob.put(this.AT[i], this.BT[i]);
        }
        sortList = sortAccordingToArrivalTime(sortList);
        int processID = 0;
        for(Job j : sortList){
            runningQueue.add(j);
            processID++;
        }
//        for (Map.Entry entry : earliestJob.entrySet()) {
//            runningQueue.add(new Job((Integer) entry.getKey(), (Integer) entry.getValue(), processID));
//            processID++;
//        }
        while(!runningQueue.isEmpty()){
            if(totalTime >= runningQueue.get(0).getArrivalTime()){
                Job currentJob = runningQueue.get(0);
                processExecutionTrail.add(new Progress(currentJob.getProcessID(),
                        totalTime,currentJob.getBurstTime()));
                currentJob.setWaitTime(totalTime - currentJob.getArrivalTime());
                if(currentJob.getVisited() != 1)
                    RT[currentJob.getProcessID()] = totalTime - currentJob.getOriginalArrivalTime();
                totalTime += currentJob.getBurstTime();
                if(currentJob.getVisited() == 1)
                    TAT[currentJob.getProcessID()] = totalTime - AT[currentJob.getProcessID()];
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
                    Job reentry = new Job(LastIOTime, BT2[pid],pid );
                    reentry.setVisited(1);
                    runningQueue.add(reentry);
                    runningQueue = sortAccordingToArrivalTime(runningQueue);
                }
                visited[pid] = true;
            }
            else{
                totalTime++;
            }
        }
        System.out.println("FCFS Progress");
        for(Progress p: processExecutionTrail){
            System.out.println(p.getProcessID()+"\t"+p.getStartTime());
        }
        System.out.println("=====================================");
//
//        float AWT=0;
//        float ATAT=0;
//        this.WT[0]=0;
//        for(int i=0;i<5;i++){
//            processExecutionTrail.add(new Progress(i,AT[i],BT[i]));
//        }
//        for(int i=1;i<5;i++)
//        {
//            WT[i]=WT[i-1]+BT[i-1]+AT[i-1];
//            WT[i]=WT[i]-AT[i];
//        }
//        for(int i=0;i<5;i++)
//        {
//            TAT[i]=WT[i]+BT[i];
//            AWT=AWT+WT[i];
//        }
//        System.out.println("  PROCESS   BT      WT      TAT     ");
//        for(int i=0;i<5;i++)
//        {
//            System.out.println("    "+ i + "       "+BT[i]+"       "+WT[i]+"       "+TAT[i]);
//        }
//        AWT=AWT/5;
//        System.out.println("***********************************************");
//        System.out.println("Avg waiting time="+AWT+"\n***********************************************");
//
//        for(int i=0;i<5;i++)
//        {
//            TAT[i]=WT[i]+BT[i];
//            ATAT=ATAT+TAT[i];
//        }
//
//        ATAT=ATAT/5;
//        System.out.println("***********************************************");
//        System.out.println("Avg turn around time="+ATAT+"\n***********************************************");
        for(Job j: doneQueue){
            this.WT[j.getProcessID()] += j.getWaitTime();
//            this.TAT[j.getProcessID()] = j.getTurnAroundTime();
//            this.RT[j.getProcessID()] = j.getResponseTime();
        }
   }
    public ArrayList<Progress> getProcessExecutionTrail(){
        return processExecutionTrail;
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