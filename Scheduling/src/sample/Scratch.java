package sample;

import java.util.ArrayList;

/**
 * Created by sathaye on 3/9/16.
 */
public class Scratch {
    public static void main(String[] args){
        ArrayList<Job> checkIndexes = new ArrayList<>();
        checkIndexes.add(new Job(10,4,0));
        checkIndexes.add(new Job(5,2,1));
        checkIndexes.add(new Job(6,5,2));
        checkIndexes.add(new Job(7,6,3));
        checkIndexes.add(new Job(8,7,4));
        checkIndexes.add(new Job(9,8,5));
        checkIndexes.add(new Job(11,9,6));

        Job removedJob = checkIndexes.get(0);
        System.out.println(checkIndexes.get(0).getArrivalTime());
        checkIndexes.remove(removedJob);
        System.out.println(checkIndexes.get(0).getArrivalTime());


    }
}
