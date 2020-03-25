package SpatialInterpolation.algorithm;

import SpatialInterpolation.utils.Key;

import java.util.HashMap;

public interface InterpolationAlgorithm {
    Double[][] interpolation(Double[][] pixelMatrix, int width, int height, HashMap<Key, Double> obs);
}
