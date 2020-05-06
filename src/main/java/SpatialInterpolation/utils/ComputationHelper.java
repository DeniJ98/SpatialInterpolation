package SpatialInterpolation.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xps 9560
 */
public class ComputationHelper {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1));
    }

    public static double singlePixelInterpolation(HashMap<Key, Double> obs, int x, int y){
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

}
