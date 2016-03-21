package sample;
import java.util.*;
public class SJF {
    private int processCount = 0;
    private int totalTime = 0;
    private int AT[], BT[];
    private int WT[] = new int[5];
    private int TAT[] = new int[5];
    private int RT[] = new int[5];
    private ArrayList<Progress> processExecutionTrail = new ArrayList<>();
    private TreeMap<Integer,Integer> shortestJob = new TreeMap<>();
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

    public void setBurstTime(int[] timeArray) {
        this.BT = timeArray;
    }

    public void setArrivalTime(int[] arrivalTime) {
        this.AT = arrivalTime;
    }

    public void calculateTimes() {
        for (int i = 0; i < 5; i++) {

            sortList.add(new Job(AT[i],BT[i], i));

            //sortList = sortAccordingToArrivalTime(sortList);

//            shortestJob.put(this.BT[i], this.AT[i]);
//            earliestJob.put(this.AT[i], this.BT[i]);
        }
        sortList = sortAccordingToArrivalTime(sortList);
//        int processID = 0;
//        for (Map.Entry entry : earliestJob.entrySet()) {
//            runningQueue.add(new Job((Integer) entry.getKey(), (Integer) entry.getValue(), processID));
//            processID++;
//        }

        for(Job j : sortList){
            runningQueue.add(j);
        }

//        for(Map.Entry entry:shortestJob.entrySet()) {
//            shorterQueue.add(new Job((Integer)entry.getValue(),(Integer)entry.getKey(),processID));
//            processID++;
//        }
        int runningQueueIndex = 0;
        while (!runningQueue.isEmpty()) {
            Job currentEarliestJob = runningQueue.get(runningQueueIndex);
            Job currentshortestJob = findMinJob(runningQueue);
            if (currentshortestJob!= null && totalTime >= currentshortestJob.getArrivalTime()) {
                processExecutionTrail.add(new Progress(currentshortestJob.getProcessID(),
                        totalTime, currentshortestJob.getBurstTime()));
                currentshortestJob.setWaitTime(totalTime - currentshortestJob.getArrivalTime());
                if(currentshortestJob.getVisited() != 1)
                    RT[currentshortestJob.getProcessID()] = totalTime - currentshortestJob.getOriginalArrivalTime();
                totalTime += currentshortestJob.getBurstTime();
                if(currentshortestJob.getVisited() == 1)
                    TAT[currentshortestJob.getProcessID()] = totalTime - AT[currentshortestJob.getProcessID()];
                doneQueue.add(currentshortestJob);
                currentshortestJob.setTurnAroundTime(totalTime - currentshortestJob.getOriginalArrivalTime());
                runningQueue.remove(currentshortestJob);
                int pid = currentshortestJob.getProcessID();
                if(!visited[pid]) {
                    IOAT[pid] = totalTime;
                    if (totalTime < LastIOTime) {
                        LastIOTime += IOTime[currentshortestJob.getProcessID()];
                    } else {
                        LastIOTime = totalTime + IOTime[currentshortestJob.getProcessID()];
                    }
                    Job reentry = new Job(LastIOTime, BT2[pid],pid);
                    reentry.setVisited(1);
                    runningQueue.add(reentry);
                    runningQueue = sortAccordingToArrivalTime(runningQueue);
                }
                visited[pid] = true;
            }
            else if(totalTime >= currentEarliestJob.getArrivalTime()){
                processExecutionTrail.add(new Progress(currentEarliestJob.getProcessID(),
                        totalTime, currentEarliestJob.getBurstTime()));
                currentEarliestJob.setWaitTime(totalTime - currentEarliestJob.getArrivalTime());
                if(currentEarliestJob.getVisited() != 1)
                    RT[currentshortestJob.getProcessID()] = totalTime - currentshortestJob.getOriginalArrivalTime();

                totalTime += currentEarliestJob.getBurstTime();
                if(currentEarliestJob.getVisited() == 1)
                    TAT[currentEarliestJob.getProcessID()] = totalTime - AT[currentEarliestJob.getProcessID()];

                doneQueue.add(currentEarliestJob);
                runningQueue.remove(currentEarliestJob);
                int pid = currentEarliestJob.getProcessID();
                if(!visited[pid]) {
                    IOAT[pid] = totalTime;
                    if (totalTime < LastIOTime) {
                        LastIOTime += IOTime[currentEarliestJob.getProcessID()];
                    } else {
                        LastIOTime = totalTime + IOTime[currentEarliestJob.getProcessID()];
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
//        while (true) {
//            if (processCount > 5)
//                break;
//            int shortestJobIndex = findMin(BT);
//            int earliestJobIndex = findMin(AT);
//
//
//            //System.out.println(shortestJobIndex +"\t"+earliestJobIndex);
//            Scanner scanner = new Scanner(System.in);
////            scanner.nextInt();
//            if(totalTime >=AT[shortestJobIndex]){
//                processExecutionTrail.add(new Progress(,totalTime,quantum));
//                WT[shortestJobIndex] = totalTime - AT[shortestJobIndex];
//                TAT[shortestJobIndex] = WT[shortestJobIndex] + BT[shortestJobIndex];
//                totalTime+=BT[shortestJobIndex];
//                BT[shortestJobIndex] = Integer.MAX_VALUE;
//                AT[shortestJobIndex] = Integer.MAX_VALUE;
//                processCount++;
//
//            }
//            else if(totalTime >=AT[earliestJobIndex]){
//                WT[earliestJobIndex] = totalTime - AT[earliestJobIndex];
//                TAT[earliestJobIndex] = WT[earliestJobIndex] + BT[earliestJobIndex];
//                totalTime+=BT[earliestJobIndex];
//                BT[earliestJobIndex] = Integer.MAX_VALUE;
//                AT[earliestJobIndex] = Integer.MAX_VALUE;
//                processCount++;
//            }
//            else{
//                totalTime++;
//            }
//        }
//        for(int i:WT){
//            System.out.print(i+",");
//        }
        }
        for (Job j : doneQueue) {
            this.WT[j.getProcessID()] += j.getWaitTime();
//            this.TAT[j.getProcessID()] = j.getTurnAroundTime();
//            this.RT[j.getProcessID()] = j.getResponseTime();
            //this.WT[getProcessIndex(j.getOriginalArrivalTime())] = j.getWaitTime();
//            System.out.println(j.getOriginalArrivalTime() + "\t" + j.getWaitTime());
        }
//        for(Progress p : processExecutionTrail){
//            System.out.println(p.getProcessID());
//        }
        System.out.println("SJF Progress");
        for(Progress p: processExecutionTrail){
            System.out.println(p.getProcessID()+"\t"+p.getStartTime());
        }
        System.out.println("=====================================");

    }
//for queue

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


//    private int findMin(int[] bt) {
//        int minValue = Integer.MAX_VALUE;
//        int index=-1;
//        for (int i = 0; i < 5; i++) {
//            if (bt[i] < minValue) {
//                minValue = bt[i];
//                index = i;
//            }
//        }
//        return index;
//    }

    public int[] getWaitingTime() {
        return WT;
    }

    public int[] getTAT() {
        return TAT;
    }

    public ArrayList<Progress> getProcessExecutionTrail() {
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

    public static void main(String[] args){
        SJF sjf = new SJF();
        int[] AT = {4,1,2,3,5};
        int[] BT = {3,1,5,2,1};
        sjf.setArrivalTime(AT);
        sjf.setBurstTime(BT);
        sjf.calculateTimes();
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