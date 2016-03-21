//package sample;
//
///**
// * Created by sathaye on 3/4/16.
// */
//public class SRTF {
//
//    private int AT[],BT[];
//    private int WT[] = new int[5];
//    private int TAT[] = new int[5];
//
//    public void setBurstTime(int[] timeArray) {
//        this.BT = timeArray;
//    }
//
//    public void setArrivalTime(int[] arrivalTime) {
//        this.AT = arrivalTime;
//    }
//
//
//        //Calculation of Total Time and Initialization of Time Chart array
//        public void calculatTimes(){
//        int total_time = 0;
//        for (int i = 0; i < 5; i++) {
//            total_time += BT[i];
//        }
//        int time_chart[] = new int[total_time];
//
//        for (int i = 0; i < total_time; i++) {
//            //Selection of shortest process which has arrived
//            int sel_proc = 0;
//            int min = 99999;
//            for (int j = 0; j < 5; j++) {
//                if (AT[0] <= i)//Condition to check if Process has arrived
//                {
//                    if (BT[j] < min && BT[j] != 0) {
//                        min = BT[j];
//                        sel_proc = j;
//                    }
//                }
//            }
//
//            //Assign selected process to current time in the Chart
//            time_chart[i] = sel_proc;
//
//            //Decrement Remaining Time of selected process by 1 since it has been assigned the CPU for 1 unit of time
//            BT[sel_proc]--;
//
//            //WT and TT Calculation
//            for (int j = 0; j < 5; j++) {
//                if (AT[j] <= i) {
//                    if (BT[j] != 0) {
//                        TAT[j]++;//If process has arrived and it has not already completed execution its TT is incremented by 1
//                        if (j != sel_proc)//If the process has not been currently assigned the CPU and has arrived its WT is incremented by 1
//                            WT[j]++;
//                    } else if (j == sel_proc)//This is a special case in which the process has been assigned CPU and has completed its execution
//                        TAT[j]++;
//                }
//            }
//
//            //Printing the Time Chart
//            if (i != 0) {
//                if (sel_proc != time_chart[i - 1])
//                //If the CPU has been assigned to a different Process we need to print the current value of time and the name of
//                //the new Process
//                {
//                    System.out.print("--" + i + "--P" + sel_proc);
//                }
//            } else//If the current time is 0 i.e the printing has just started we need to print the name of the First selected Process
//                System.out.print(i + "--P" + sel_proc);
//            if (i == total_time - 1)//All the process names have been printed now we have to print the time at which execution ends
//                System.out.print("--" + (i + 1));
//
//        }
//
//        //Printing the WT and TT for each Process
//        System.out.println("P\t WT \t TT ");
//        for(int i = 1; i <= ; i++)
//        {
//            System.out.printf("%d\t%2dms\t%2dms",i,WT[i],TAT[i]);
//            System.out.println();
//        }
//
//        System.out.println();
//
//        //Printing the average WT & TT
//        float WAT = 0, TT = 0;
//        for (int i = 1; i <= n; i++) {
//            WAT += WT[i];
//            TT += TAT[i];
//        }
//        WAT /= 5;
//        TT /= 5;
//        System.out.println("The Average WT is: " + WAT + "ms");
//        System.out.println("The Average TT is: " + TT + "ms");
//    }
//    }
//}
