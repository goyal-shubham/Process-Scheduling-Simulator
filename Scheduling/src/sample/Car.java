//package sample;
//
//import javafx.scene.Group;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//import java.util.ArrayList;
//
///**
// * Created by sathaye on 3/4/16.
// */
//public class Car {
//
//    private Rectangle rect;
//    private Main.direction direction;
//    private int previous;
//
//    public Car(Rectangle r, Main.direction d) {
//        this.rect = r;
//        this.direction = d;
//    }
//
//    public void move(Group root,int processID) {
//        double xPos;
//        double yPos;
//        switch (direction) {
//            case left:
//                // make sure x is a positive nr between 0 and 600
//                xPos = (rect.getX() - 1) % 600;
//                if (xPos < 0) {
//                    xPos = 600;
//                }
//                rect.setX(xPos);
//                System.out.println(String.format("move left, x: %1.0f", xPos));
//                break;
//            case right:
////                xPos = (rect.getX() + 20) % 600;
////                rect.setX(xPos);
////                System.out.println(String.format("move right, x: %1.0f", xPos));
//
//                Rectangle leftSide = new Rectangle(previous+rect.getWidth(), 200 - 10, 20, 20);
//                previous+=rect.getWidth();
//                if(processID == 0)
//                    leftSide.setFill(Color.RED);
//                if(processID == 1)
//                    leftSide.setFill(Color.GREY);
//                if(processID == 2)
//                    leftSide.setFill(Color.BLUE);
//                if(processID == 3)
//                    leftSide.setFill(Color.BEIGE);
//                if(processID == 4)
//                    leftSide.setFill(Color.VIOLET);
//                root.getChildren().add(leftSide);
//                break;
//            case up:
//                yPos = (rect.getY() - 1) % 600;
//                rect.setY(yPos);
//                break;
//            case down:
//                yPos = (rect.getY() + 1) % 600;
//                rect.setY(yPos);
//                break;
//            default:
//                throw new AssertionError(direction.name());
//
//        }
//    }
//}
package sample;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ArrayList;
/**
 * Created by sathaye on 3/4/16.
 */
public class Car {
    private Rectangle rect;
    private Main.direction direction;
    private int previous = 80;
    public Car(Rectangle r, Main.direction d) {
        this.rect = r;
        this.direction = d;
    }
    public void move(Group root,int processID) {
        double xPos;
        double yPos;
        switch (direction) {
            case left:
                // make sure x is a positive nr between 0 and 600
                xPos = (rect.getX() - 1) % 600;
                if (xPos < 0) {
                    xPos = 600;
                }
                rect.setX(xPos);
                System.out.println(String.format("move left, x: %1.0f", xPos));
                break;
            case right:
//                xPos = (rect.getX() + 20) % 600;
//                rect.setX(xPos);
//                System.out.println(String.format("move right, x: %1.0f", xPos));
                Text t = new Text();
                Rectangle leftSide = new Rectangle(previous+rect.getWidth(), rect.getY(), 20, 80);
                double i = previous+rect.getWidth();
                previous+=rect.getWidth();
                if(processID == 0) {
                    t.setText("  " + (processID + 1));
                    //  t.setFill(Color.ALICEBLUE);
                    t.setX(i);
                    t.setY(rect.getY() - 10);
                    //leftSide.setFill(Color.ALICEBLUE);
                    leftSide.setFill(Color.rgb(128,0,0,0.7));
//                    leftSide.setOpacity(1);
//                    leftSide.setAccessibleText("0");
//                    leftSide.setFill(Color.RED);
                }
                if(processID == 1) {
                    t.setText("  " + (processID + 1));
                    //   t.setFill(Color.GREY);
                    t.setX(i);
                    t.setY(rect.getY() - 10);
                    //leftSide.setFill(Color.GREY);
                    leftSide.setFill(Color.rgb(128,128,128,0.7));
                }
                if(processID == 2){
                    t.setText("  " + (processID + 1));
                    //   t.setFill(Color.BROWN);
                    t.setX(i);
                    t.setY(rect.getY() - 10);
                    //leftSide.setFill(Color.BROWN);
                    leftSide.setFill(Color.rgb(0,0,128,0.7));
                }
                if(processID == 3)
                {
                    t.setText("  " + (processID + 1));
                    //  t.setFill(Color.PINK);
                    t.setX(i);
                    t.setY(rect.getY() - 10);
                    //leftSide.setFill(Color.PINK);
                    leftSide.setFill(Color.rgb(0,128,128,0.7));
                }
                if(processID == 4){
                    t.setText("  " + (processID + 1));
                    //  t.setFill(Color.VIOLET);
                    t.setX(i);
                    t.setY(rect.getY() - 10);
                    //leftSide.setFill(Color.VIOLET);
                    leftSide.setFill(Color.rgb(128,0,128,0.7));
                }
                if(processID == 10){
                    leftSide.setFill(Color.rgb(255,255,255,0.0));
                }
                root.getChildren().add(t);
                root.getChildren().add(leftSide);
                break;
            case up:
                yPos = (rect.getY() - 1) % 600;
                rect.setY(yPos);
                break;
            case down:
                yPos = (rect.getY() + 1) % 600;
                rect.setY(yPos);
                break;
            default:
                throw new AssertionError(direction.name());
        }
    }
}