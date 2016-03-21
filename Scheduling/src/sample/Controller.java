package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    public void update() throws IOException {
        Parent root2 = FXMLLoader.load(Main.class.getResource("newwindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Window");
        Scene scene2 = new Scene(root2,700,400);
        Rectangle rightSide = new Rectangle(600, 250 + 10, -20, -20);
        rightSide.setFill(Color.BLUE);
        stage.setScene(scene2);
        stage.show();

    }


    public void drawRectangle(){

    }



    public void displayChart(int[] waitingTimeFCFS, int[] waitingTimeSJF, int[] waitingTimeRR,
                             int[] waitingTimeMLFQ,int[] waitingTimeMLFQFCFS,String property) {
        Stage stageChart = new Stage();
        stageChart.setTitle(property);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
        barChart.setTitle("Process Summary");
        xAxis.setLabel("Processes");
        yAxis.setLabel("Time");

        XYChart.Series FCFS = new XYChart.Series();
        FCFS.setName("FCFS");
        FCFS.getData().add(new XYChart.Data("Process1",waitingTimeFCFS[0]));
        FCFS.getData().add(new XYChart.Data("Process2",waitingTimeFCFS[1]));
        FCFS.getData().add(new XYChart.Data("Process3",waitingTimeFCFS[2]));
        FCFS.getData().add(new XYChart.Data("Process4",waitingTimeFCFS[3]));
        FCFS.getData().add(new XYChart.Data("Process5",waitingTimeFCFS[4]));


        XYChart.Series SJF = new XYChart.Series();
        SJF.setName("SJF");
        SJF.getData().add(new XYChart.Data("Process1",waitingTimeSJF[0]));
        SJF.getData().add(new XYChart.Data("Process2",waitingTimeSJF[1]));
        SJF.getData().add(new XYChart.Data("Process3",waitingTimeSJF[2]));
        SJF.getData().add(new XYChart.Data("Process4",waitingTimeSJF[3]));
        SJF.getData().add(new XYChart.Data("Process5",waitingTimeSJF[4]));
//

        XYChart.Series RR = new XYChart.Series();
        RR.setName("RR");
        RR.getData().add(new XYChart.Data("Process1",waitingTimeRR[0]));
        RR.getData().add(new XYChart.Data("Process2",waitingTimeRR[1]));
        RR.getData().add(new XYChart.Data("Process3",waitingTimeRR[2]));
        RR.getData().add(new XYChart.Data("Process4",waitingTimeRR[3]));
        RR.getData().add(new XYChart.Data("Process5",waitingTimeRR[4]));

        XYChart.Series MLFQ = new XYChart.Series();
        MLFQ.setName("MLFQ");
        MLFQ.getData().add(new XYChart.Data("Process1",waitingTimeMLFQ[0]));
        MLFQ.getData().add(new XYChart.Data("Process2",waitingTimeMLFQ[1]));
        MLFQ.getData().add(new XYChart.Data("Process3",waitingTimeMLFQ[2]));
        MLFQ.getData().add(new XYChart.Data("Process4",waitingTimeMLFQ[3]));
        MLFQ.getData().add(new XYChart.Data("Process5",waitingTimeMLFQ[4]));


        XYChart.Series MLFQFCFS = new XYChart.Series();
        MLFQFCFS.setName("MLFQSJF");
        MLFQFCFS.getData().add(new XYChart.Data("Process1",waitingTimeMLFQFCFS[0]));
        MLFQFCFS.getData().add(new XYChart.Data("Process2",waitingTimeMLFQFCFS[1]));
        MLFQFCFS.getData().add(new XYChart.Data("Process3",waitingTimeMLFQFCFS[2]));
        MLFQFCFS.getData().add(new XYChart.Data("Process4",waitingTimeMLFQFCFS[3]));
        MLFQFCFS.getData().add(new XYChart.Data("Process5",waitingTimeMLFQFCFS[4]));


        Scene scene = new Scene(barChart,800,600);
        barChart.getData().addAll(FCFS,SJF,RR,MLFQ,MLFQFCFS);
        stageChart.setScene(scene);
        stageChart.show();
    }
    public void displayGanttChart(ArrayList<Progress> ganttFCFS,ArrayList<Progress> ganttRR,
                                  ArrayList<Progress> ganttSJF,ArrayList<Progress> ganttMLFQ,
                                    ArrayList<Progress> ganttMLFQSJF){
        Stage stage = new Stage();
        stage.setTitle("Gantt Chart Sample");

        String[] machines = new String[] { "Multilevel Feedback with SJF","Multilevel Feedback","Round Robin","SJF","FCFS"};

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        //xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));

        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        String machine;

        XYChart.Series series1 = new XYChart.Series();
        for(Progress progress:ganttFCFS){
            System.out.println(progress.getStartTime()+"\t"+progress.getBurstTime());
            series1.getData().add(new XYChart.Data(progress.getStartTime(),"FCFS", new GanttChart.ExtraData(progress.getBurstTime(),progress.getProcessIDString())));
        }
        //series1.getData().add(new XYChart.Data(1, "FCFS", new GanttChart.ExtraData( 3, "one")));
//        series1.getData().add(new XYChart.Data(4, "FCFS", new GanttChart.ExtraData( 2, "two")));
//        series1.getData().add(new XYChart.Data(6, "FCFS", new GanttChart.ExtraData( 1, "three")));
//        series1.getData().add(new XYChart.Data(7, "FCFS", new GanttChart.ExtraData( 2, "four")));
//        series1.getData().add(new XYChart.Data(9, "FCFS", new GanttChart.ExtraData( 1, "five")));
        XYChart.Series series2 = new XYChart.Series();
        for(Progress progress:ganttRR){
            System.out.println(progress.getStartTime()+"\t"+progress.getBurstTime());
            series2.getData().add(new XYChart.Data(progress.getStartTime(),"Round Robin", new GanttChart.ExtraData(progress.getBurstTime(),progress.getProcessIDString())));
        }

//        machine = machines[1];
//        XYChart.Series series2 = new XYChart.Series();
//        series2.getData().add(new XYChart.Data(3, "Round Robin", new GanttChart.ExtraData( 1, "status-green")));
//        series2.getData().add(new XYChart.Data(2, "Round Robin", new GanttChart.ExtraData( 1, "status-blue")));
//        series2.getData().add(new XYChart.Data(1, "Round Robin", new GanttChart.ExtraData( 1, "status-red")));

        XYChart.Series series3 = new XYChart.Series();
        for(Progress progress:ganttSJF){
            System.out.println(progress.getStartTime()+"\t"+progress.getBurstTime());
            series3.getData().add(new XYChart.Data(progress.getStartTime(),"SJF", new GanttChart.ExtraData(progress.getBurstTime(),progress.getProcessIDString())));
        }

//        machine = machines[2];
//        XYChart.Series series3 = new XYChart.Series();
//        series3.getData().add(new XYChart.Data(0, "SJF", new GanttChart.ExtraData( 1, "status-blue")));
//        series3.getData().add(new XYChart.Data(1, "SJF", new GanttChart.ExtraData( 1, "status-red")));
//        series3.getData().add(new XYChart.Data(3, "SJF", new GanttChart.ExtraData( 1, "status-green")));
//
        XYChart.Series series4 = new XYChart.Series();
        for(Progress progress:ganttMLFQ){
            System.out.println(progress.getStartTime()+"\t"+progress.getBurstTime());
            series4.getData().add(new XYChart.Data(progress.getStartTime(),"Multilevel Feedback", new GanttChart.ExtraData(progress.getBurstTime(),progress.getProcessIDString())));
        }

        XYChart.Series series5 = new XYChart.Series();
        for(Progress progress:ganttMLFQSJF){
            System.out.println(progress.getStartTime()+"\t"+progress.getBurstTime());
            series5.getData().add(new XYChart.Data(progress.getStartTime(),"Multilevel Feedback with SJF", new GanttChart.ExtraData(progress.getBurstTime(),progress.getProcessIDString())));
        }

        chart.getData().addAll(series1,series2,series3,series4,series5);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        Scene scene  = new Scene(chart,620,350);
        stage.setScene(scene);
        stage.show();
    }

    public void displayAverageTimes(int[] waitingTimeFCFS, int[] waitingTimeSJF, int[] waitingTimeRR, int[] waitingTimeMLFQ, int[] waitingTimeMLFQSJF, String s) {
        Stage stageChart = new Stage();
        stageChart.setTitle(s);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
        //barChart.wid
        barChart.setTitle("Process Summary");
        xAxis.setLabel("Processes");
        yAxis.setLabel("Time");

        XYChart.Series TotalWaitingTime = new XYChart.Series();
        int totalWaitingTimeFCFS=0;
        int totalWaitingTimeSJF=0;
        int totalWaitingTimeRR=0;
        int totalWaitingTimeMLFQSJF=0;
        int totalWaitingTimeMLFQ=0;
        for(int i=0;i<5;i++) {
            totalWaitingTimeFCFS +=waitingTimeFCFS[i];
            totalWaitingTimeSJF +=waitingTimeSJF[i];
            totalWaitingTimeRR +=waitingTimeRR[i];
            totalWaitingTimeMLFQ += waitingTimeMLFQ[i];
            totalWaitingTimeMLFQSJF +=waitingTimeMLFQSJF[i];
        }
        //FCFS.setName("FCFS");
        TotalWaitingTime.getData().add(new XYChart.Data("MLFQ",totalWaitingTimeMLFQ/5));
        TotalWaitingTime.getData().add(new XYChart.Data("MLFQSJF",totalWaitingTimeMLFQSJF/5));
        TotalWaitingTime.getData().add(new XYChart.Data("RR",totalWaitingTimeRR/5));
        TotalWaitingTime.getData().add(new XYChart.Data("SJF",totalWaitingTimeSJF/5));
        TotalWaitingTime.getData().add(new XYChart.Data("FCFS",totalWaitingTimeFCFS/5));

        Scene scene = new Scene(barChart,800,600);
        barChart.getData().add(TotalWaitingTime);
        stageChart.setScene(scene);
        stageChart.show();

    }
}
