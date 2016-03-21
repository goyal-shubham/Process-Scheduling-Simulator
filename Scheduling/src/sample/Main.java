package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;


/*
TODO: FIX FCFS
 */

public class Main extends Application {

    Group rootGroup;

    Car fcfsBlock;
    Car rrBlock;
    Car sjfBlock;
    Car mlfqBlock;
    Car MLFQSJFBlock;

    int maxWidth = 600;
    int maxHeight = 600;
    int FCFSburstTime = 21;

    public static enum direction {

        left, right, up, down
    };


    ArrayList<Progress> processExecutionFCFS;
    ArrayList<Progress> processExecutionRR;
    ArrayList<Progress> processExecutionSJF;
    ArrayList<Progress> processExecutionMLFQ;
    ArrayList<Progress> processExecutionMLFQSJF;

    FCFS fcfs;// = new FCFS();
    SJF sjf;// = new SJF();
    RR rr;// = new RR();
    MLFQ mlfq;// = new MLFQ();
    Controller c;// = new Controller();
    MLFQSJF mlfqsjf;// = new MLFQSJF();



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Visual Tool");
        Scene scene = new Scene(root,800,800);

        TextField process1BurstTime = (TextField) scene.lookup("#process1BurstTime");
        TextField process2BurstTime = (TextField) scene.lookup("#process2BurstTime");
        TextField process3BurstTime = (TextField) scene.lookup("#process3BurstTime");
        TextField process4BurstTime = (TextField) scene.lookup("#process4BurstTime");
        TextField process5BurstTime = (TextField) scene.lookup("#process5BurstTime");


        TextField process1BurstTime2 = (TextField) scene.lookup("#process1BurstTime2");
        TextField process2BurstTime2 = (TextField) scene.lookup("#process2BurstTime2");
        TextField process3BurstTime2 = (TextField) scene.lookup("#process3BurstTime2");
        TextField process4BurstTime2 = (TextField) scene.lookup("#process4BurstTime2");
        TextField process5BurstTime2 = (TextField) scene.lookup("#process5BurstTime2");


        TextField process1IOtime = (TextField) scene.lookup("#process1IOtime");
        TextField process2IOtime = (TextField) scene.lookup("#process2IOtime");
        TextField process3IOtime = (TextField) scene.lookup("#process3IOtime");
        TextField process4IOtime = (TextField) scene.lookup("#process4IOtime");
        TextField process5IOtime = (TextField) scene.lookup("#process5IOtime");


        //process1BurstTime.setText("101");

        TextField process1ArrivalTime = (TextField) scene.lookup("#process1ArrivalTime");
        TextField process2ArrivalTime = (TextField) scene.lookup("#process2ArrivalTime");
        TextField process3ArrivalTime = (TextField) scene.lookup("#process3ArrivalTime");
        TextField process4ArrivalTime = (TextField) scene.lookup("#process4ArrivalTime");
        TextField process5ArrivalTime = (TextField) scene.lookup("#process5ArrivalTime");


        TextField process1Priority = (TextField) scene.lookup("#process1Priority");
        TextField process2Priority = (TextField) scene.lookup("#process2Priority");
        TextField process3Priority = (TextField) scene.lookup("#process3Priority");
        TextField process4Priority = (TextField) scene.lookup("#process4Priority");
        TextField process5Priority = (TextField) scene.lookup("#process5Priority");

        //process1ArrivalTime.setText("100");
//        FCFS fcfs = new FCFS();
//        SJF sjf = new SJF();
//        RR rr = new RR();
//        MLFQ mlfq = new MLFQ();
//        Controller c = new Controller();
//        MLFQSJF mlfqsjf = new MLFQSJF();


        Button calculate = (Button) scene.lookup("#Submit");
        Button showWaitingTimes = (Button) scene.lookup("#getWaitingTimes");
        Button showTurnAroundTimes = (Button) scene.lookup("#getTurnAroundTimes");
        Button showStaticGanntChart = (Button) scene.lookup("#getStaticGanntChart");
        Button showDynamicGanntChart = (Button) scene.lookup("#getDynamicGanntChart");
        Button showResponseTime = (Button) scene.lookup("#getResponseTime");

        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                fcfs = new FCFS();
                sjf = new SJF();
                rr = new RR();
                mlfq = new MLFQ();
                c = new Controller();
                mlfqsjf = new MLFQSJF();


                int[] burstTime = {
                        Integer.parseInt(process1BurstTime.getText()),
                        Integer.parseInt(process2BurstTime.getText()),
                        Integer.parseInt(process3BurstTime.getText()),
                        Integer.parseInt(process4BurstTime.getText()),
                        Integer.parseInt(process5BurstTime.getText())
                };

                int[] burstTime2 = {
                        Integer.parseInt(process1BurstTime2.getText()),
                        Integer.parseInt(process2BurstTime2.getText()),
                        Integer.parseInt(process3BurstTime2.getText()),
                        Integer.parseInt(process4BurstTime2.getText()),
                        Integer.parseInt(process5BurstTime2.getText())
                };

                int[] IOTime = {
                        Integer.parseInt(process1IOtime.getText()),
                        Integer.parseInt(process2IOtime.getText()),
                        Integer.parseInt(process3IOtime.getText()),
                        Integer.parseInt(process4IOtime.getText()),
                        Integer.parseInt(process5IOtime.getText())
                };

                int[] arrivalTime = {
                        Integer.parseInt(process1ArrivalTime.getText()),
                        Integer.parseInt(process2ArrivalTime.getText()),
                        Integer.parseInt(process3ArrivalTime.getText()),
                        Integer.parseInt(process4ArrivalTime.getText()),
                        Integer.parseInt(process5ArrivalTime.getText())
                };

                int[] priority = {
                        Integer.parseInt(process1Priority.getText()),
                        Integer.parseInt(process2Priority.getText()),
                        Integer.parseInt(process3Priority.getText()),
                        Integer.parseInt(process4Priority.getText()),
                        Integer.parseInt(process5Priority.getText())
                };

                sjf.setBurstTime(burstTime);
                sjf.setBurstTime2(burstTime2);
                sjf.setIOTime(IOTime);
                sjf.setArrivalTime(arrivalTime);

                fcfs.setBurstTime2(burstTime2);
                fcfs.setIOTime(IOTime);
                fcfs.setBurstTime(burstTime);
                fcfs.setArrivalTime(arrivalTime);

                mlfq.setBurstTime2(burstTime2);
                mlfq.setIOTime(IOTime);
                mlfq.setBurstTime(burstTime);
                mlfq.setArrivalTime(arrivalTime);
                mlfq.setPriority(priority);


                mlfqsjf.setBurstTime2(burstTime2);
                mlfqsjf.setIOTime(IOTime);
                mlfqsjf.setBurstTime(burstTime);
                mlfqsjf.setArrivalTime(arrivalTime);
                mlfqsjf.setPriority(priority);


                rr.setBurstTime2(burstTime2);
                rr.setIOTime(IOTime);
                rr.setBurstTime(burstTime);
                rr.setArrivalTime(arrivalTime);

                try {
                    fcfs.calculateTimes();
                    rr.calculateTimes();
                    mlfq.calculateTimes();
                    sjf.calculateTimes();
                    mlfqsjf.calculateTimes();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        showWaitingTimes.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {

                                   int[] waitingTimeSJF = sjf.getWaitingTime();
                                   int[] waitingTimeFCFS = fcfs.getWaitingTime();
                                   int[] waitingTimeRR = rr.getWaitingTime();
                                   int[] waitingTimeMLFQ = mlfq.getWaitingTime();
                                   int[] waitingTimeMLFQSJF = mlfqsjf.getWaitingTime();

                                   c.displayChart(waitingTimeFCFS, waitingTimeSJF, waitingTimeRR, waitingTimeMLFQ,waitingTimeMLFQSJF,"Waiting Time");
                                   c.displayAverageTimes(waitingTimeFCFS, waitingTimeSJF, waitingTimeRR, waitingTimeMLFQ, waitingTimeMLFQSJF, "Average Waiting Time");

                               }
                           }
        );

        showTurnAroundTimes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int[] tatSJF = sjf.getTAT();
                int[] tatFCFS = fcfs.getTAT();
                int[] tatRR = rr.getTAT();
                int[] tatMLFQ = mlfq.getTAT();
                int[] tatMLFQSJF = mlfqsjf.getTAT();
                c.displayChart(tatFCFS, tatSJF, tatRR, tatMLFQ,tatMLFQSJF,"Turn Around Time");
                c.displayAverageTimes(tatFCFS, tatSJF, tatRR, tatMLFQ, tatMLFQSJF, "Average Turn Around Time");
            }
        });

        showStaticGanntChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                processExecutionFCFS = fcfs.getProcessExecutionTrail();
                processExecutionRR = rr.getProcessExecutionTrail();
                processExecutionSJF = sjf.getProcessExecutionTrail();
                processExecutionMLFQ = mlfq.getProcessExecutionTrail();
                processExecutionMLFQSJF = mlfqsjf.getProcessExecutionTrail();
                c.displayGanttChart(processExecutionFCFS, processExecutionRR,
                        processExecutionSJF, processExecutionMLFQ,processExecutionMLFQSJF);
            }
        });

        showDynamicGanntChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rootGroup = new Group();
                Rectangle fcfsSide = new Rectangle(0, 190, 20, 20);
                Rectangle rrSide = new Rectangle(0, 290, 20, 20);
                Rectangle sjfSide = new Rectangle(0, 390, 20, 20);
                Rectangle mlfqSide = new Rectangle(0, 490, 20, 20);
                Rectangle MLFQSJFSide = new Rectangle(0, 590, 20, 20);



                Path path = new Path();
                path.getElements().add(new MoveTo(100.0f, 0.0f));
                path.getElements().addAll(new VLineTo(700.0f));
                //path.getElements().add(new MoveTo(5000.0f, 0.0f));
                Path path1 = new Path();
                path1.getElements().add(new MoveTo());
                path1.getElements().add(new MoveTo(100.0f, 700.0f));
                path1.getElements().addAll(new HLineTo(1200.0f));
                int x = 100;
                int y = 20;
                for(int i = 0 ; i < 35 ; i++){
                    Line line1 = new Line(x,y,1220,y);
                    line1.setOpacity(10);
                    line1.getStrokeDashArray().addAll(3d);
                    rootGroup.getChildren().addAll(line1);
                    y += 20;
                }
                y = 0;
                x = 100;
                for(int i = 0 ; i <= 60 ; i++){
                    Line line1 = new Line(x,y,x,700);
                    line1.setOpacity(10);
                    line1.getStrokeDashArray().addAll(3d);
                    rootGroup.getChildren().addAll(line1);
                    x += 20;
                }
                //path.getElements().addAll(new HLineTo(5000.0f));
                rootGroup.getChildren().addAll(path1);
                rootGroup.getChildren().addAll(path);
                //rootGroup.getChildren().add(leftSide);
                int start = 100;
                for(int  i = 1 ; i <= 60 ; i ++){
                    Text text1 = new Text();
                    text1.setText("  " + i);
                    //   t.setFill(Color.BROWN);
                    text1.setX(start);
                    text1.setY(710);
                    start += 20;
                    rootGroup.getChildren().addAll(text1);
                }

                Text fcfsText = new Text();
                fcfsText.setText("FCFS");
                fcfsText.setX(20);
                fcfsText.setY(240);

                Text sjfText = new Text();
                sjfText.setText("SJF");
                sjfText.setX(20);
                sjfText.setY(340);

                Text rrText = new Text();
                rrText.setText("RR");
                rrText.setX(20);
                rrText.setY(440);

                Text mlfqText = new Text();
                mlfqText.setText("MLFQ");
                mlfqText.setX(20);
                mlfqText.setY(540);

                Text MLFQSJFText = new Text();
                MLFQSJFText.setText("MLFQSJF");
                MLFQSJFText.setX(10);
                MLFQSJFText.setY(640);


                rootGroup.getChildren().addAll(fcfsText,sjfText,rrText,mlfqText,MLFQSJFText);
                Scene scene = new Scene(rootGroup, maxWidth, maxHeight);
                //Rectangle leftSide = new Rectangle(0, 200 - 10, 20, 20);
                //leftSide.setFill(Color.WHEAT);

                processExecutionFCFS = fcfs.getProcessExecutionTrail();
                processExecutionRR = rr.getProcessExecutionTrail();
                processExecutionSJF = sjf.getProcessExecutionTrail();
                processExecutionMLFQ = mlfq.getProcessExecutionTrail();
                processExecutionMLFQSJF = mlfqsjf.getProcessExecutionTrail();

                fcfsBlock = new Car(fcfsSide, direction.right);
                rrBlock = new Car(rrSide, direction.right);
                sjfBlock = new Car(sjfSide, direction.right);
                mlfqBlock = new Car(mlfqSide, direction.right);
                MLFQSJFBlock = new Car(MLFQSJFSide,direction.right);

                //rootGroup.getChildren().addAll(fcfsSide, rrSide, sjfSide, mlfqSide,MLFQSJFSide);

                Stage dynamicGannt = new Stage();
                dynamicGannt.setTitle("Dynamic Chart");
                dynamicGannt.setScene(scene);
                dynamicGannt.show();

                Thread fcfsThread = new Thread(new DrawRunnableFCFS());
                Thread rrThread = new Thread(new DrawRunnableRR());
                Thread sjfThread = new Thread(new DrawRunnableSJF());
                Thread mlfqThread = new Thread(new DrawRunnableMLFQ());
                Thread MLFQSJFThread = new Thread(new DrawRunnableMLFQSJF());

                fcfsThread.start();
                rrThread.start();
                sjfThread.start();
                mlfqThread.start();
                MLFQSJFThread.start();

            }
        });

        showResponseTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int[] responseTimeSJF = sjf.getRT();
                int[] responseTimeFCFS = fcfs.getRT();
                int[] responseTimeRR = rr.getRT();
                int[] responseTimeMLFQ = mlfq.getRT();
                int[] responseTimeMLFQSJF = mlfqsjf.getRT();
                c.displayChart(responseTimeFCFS, responseTimeSJF, responseTimeRR, responseTimeMLFQ,responseTimeMLFQSJF, "Response Time");
                c.displayAverageTimes(responseTimeFCFS, responseTimeSJF, responseTimeRR, responseTimeMLFQ,responseTimeMLFQSJF, "Average Response Time");
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }


//    public void moveCars(Group root,int processID) {
//        //processExecutionFCFS
//
//
//        fcfsBlock.move(root, processID);
//        sjfBlock.move(root, processID);
//        rrBlock.move(root, processID);
//        mlfqBlock.move(root, processID);
//        //carFromRight.move();
//
//    }

private class DrawRunnableFCFS implements Runnable {
    int i=-1;
    int time = 0;

    @Override
    public void run() {
        try {
            for(Progress prr : processExecutionFCFS){
                if(time < prr.getStartTime()) {
                    for(int i = 0; i < prr.getStartTime()- time; i++){
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                fcfsBlock.move(rootGroup, 10);
                                //moveCars(rootGroup,prr.getProcessID());
                            }
                        });
                    }
                }

                for (i = 0; i < prr.getBurstTime(); i++) {
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                fcfsBlock.move(rootGroup, prr.getProcessID());
                                //moveCars(rootGroup,prr.getProcessID());
                            }
                        });
                    }


                time = prr.getStartTime() + prr.getBurstTime();
            }

        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }

}

    private class DrawRunnableRR implements Runnable {
        int i=-1;
        @Override
        public void run() {
            try {
                int time = 0;
                for(Progress prr : processExecutionRR){
                    if(time < prr.getStartTime()) {
                        for(int i = 0; i < prr.getStartTime()- time; i++){
                            Thread.sleep(300);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    rrBlock.move(rootGroup, 10);
                                    //moveCars(rootGroup,prr.getProcessID());
                                }
                            });
                        }
                    }
                    for(i = 0;i<prr.getBurstTime();i++){
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                rrBlock.move(rootGroup, prr.getProcessID());

                            }
                        });
                    }
                    time = prr.getBurstTime() + prr.getStartTime();

                }
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }

    }

    private class DrawRunnableSJF implements Runnable {
        int i=-1;
        @Override
        public void run() {
            try {
                int time = 0;

                for(Progress prr : processExecutionSJF){
                    if(time < prr.getStartTime()) {
                        for(int i = 0; i < prr.getStartTime()- time; i++){
                            Thread.sleep(300);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    sjfBlock.move(rootGroup, 10);
                                    //moveCars(rootGroup,prr.getProcessID());
                                }
                            });
                        }
                    }
                    for(i = 0;i<prr.getBurstTime();i++){
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                sjfBlock.move(rootGroup, prr.getProcessID());
                            }
                        });
                    }
                    time = prr.getBurstTime() + prr.getStartTime();

                }

            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }

    }

    private class DrawRunnableMLFQ implements Runnable {
        int i=-1;
        int time = 0;
        @Override
        public void run() {
            try {
                for(Progress prr : processExecutionMLFQ){
                    if(time < prr.getStartTime()) {
                        for(int i = 0; i < prr.getStartTime()- time; i++){
                            Thread.sleep(300);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    mlfqBlock.move(rootGroup, 10);
                                    //moveCars(rootGroup,prr.getProcessID());
                                }
                            });
                        }
                    }
                    for(i = 0;i<prr.getBurstTime();i++){
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mlfqBlock.move(rootGroup, prr.getProcessID());
                            }
                        });
                    }
                    time = prr.getBurstTime() + prr.getStartTime();

                }

            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }

    }
    private class DrawRunnableMLFQSJF implements Runnable {
        int time = 0;
        @Override
        public void run() {
            try {
                for(Progress prr : processExecutionMLFQSJF){
                    if(time < prr.getStartTime()) {
                        for(int i = 0; i < prr.getStartTime()- time; i++){
                            Thread.sleep(300);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    MLFQSJFBlock.move(rootGroup, 10);
                                    //moveCars(rootGroup,prr.getProcessID());
                                }
                            });
                        }
                    }
                    for(int i = 0;i<prr.getBurstTime();i++){
                        Thread.sleep(300);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MLFQSJFBlock.move(rootGroup, prr.getProcessID());
                                //moveCars(rootGroup,prr.getProcessID());
                            }
                        });
                    }
                    time = prr.getBurstTime() + prr.getStartTime();
                }

            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
