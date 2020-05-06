package SpatialInterpolation.algorithm;

import SpatialInterpolation.utils.Key;

import java.util.HashMap;
import java.util.Map;

import static SpatialInterpolation.utils.ComputationHelper.singlePixelInterpolation;
import static java.lang.System.nanoTime;


/**
 * @author Xps 9560
 */
public class SequentialAlgorithm implements InterpolationAlgorithm {

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
