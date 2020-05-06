package SpatialInterpolation.algorithm;

import SpatialInterpolation.utils.Key;
import SpatialInterpolation.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static SpatialInterpolation.utils.ComputationHelper.singlePixelInterpolation;

/**
 * @author Xps 9560
 */
public class InterpolationThread extends Thread {
    private List<Pair<Integer,Pair<Integer,Integer> >> workingPoints;
    Double[][] pixelMatrix;
    HashMap<Key, Double> obs;

    public InterpolationThread(Double[][] pixelMatrix, HashMap<Key, Double> obs){
        this.pixelMatrix = pixelMatrix;
        this.obs = obs;
        this.workingPoints = new ArrayList<>();
    }

    public void addWorkingPoint(int line, int colStart, int colEnd){
        Pair<Integer, Integer> colIndexes = new Pair<>(colStart, colEnd);
        Pair<Integer,Pair<Integer,Integer>> wp = new Pair<>(line, colIndexes);
        workingPoints.add(wp);
    }

    @Override
    public void run(){
        Key currKey;
        for(Pair<Integer,Pair<Integer,Integer>> wp : this.workingPoints){
            int line = wp.getFirstValue();
            int colStart = wp.getSecondValue().getFirstValue();
            int colEnd = wp.getSecondValue().getSecondValue();
            for(int col = colStart;col<colEnd;col++){
                currKey = new Key(line,col);
                if(!obs.containsKey(currKey)){
                    pixelMatrix[line][col] = singlePixelInterpolation(obs, line, col);

                }
            }
        }
    }
}
