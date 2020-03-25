package SpatialInterpolation.algorithm;

import SpatialInterpolation.utils.Key;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Xps 9560
 */
public class SequentialAlgorithm implements InterpolationAlgorithm {

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1));
    }

    private static double singlePixelInterpolation(HashMap<Key, Double> obs, int x, int y){
        Key check = new Key(x,y);
        if(obs.containsKey(check)){
            return obs.get(check);
        }

        double distanceSum=0;
        double currentWeight;
        double weightSum=0;

        for (Map.Entry<Key,Double> obsPoint : obs.entrySet() ) {
            Key key = obsPoint.getKey();
            Double val = obsPoint.getValue();

            currentWeight= Math.pow(distance(key.getX(), key.getY(), x, y), 2);
            distanceSum+= val/currentWeight;
            weightSum+= 1/currentWeight;
        }

        return distanceSum/weightSum;
    }

    @Override
    public Double[][] interpolation(Double[][] pixelMatrix, int width, int height, HashMap<Key, Double> obs) {
        Key currKey;
        for (int i=0;i<height;i++){
            for (int j=0; j<width; j++){
                currKey = new Key(i,j );
                if(!obs.containsKey(currKey)){
                    pixelMatrix[i][j] = singlePixelInterpolation(obs, i, j);

                }
            }
        }
        return pixelMatrix;
    }
}
