package SpatialInterpolation.algorithm;

import SpatialInterpolation.utils.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.nanoTime;

/**
 * @author Xps 9560
 */
public class ParallelAlgorithm implements InterpolationAlgorithm{
    private int THREADS_NUMBER = 10;

    @Override
    public Double[][] interpolation(Double[][] pixelMatrix, int width, int height, HashMap<Key, Double> obs) throws InterruptedException {

        List<InterpolationThread> interpolationThreads = new ArrayList<>();

        for(int i=0; i<THREADS_NUMBER; i++){
            InterpolationThread  interpolationThread = new InterpolationThread(pixelMatrix, obs);
            interpolationThreads.add(interpolationThread);
        }

        int mline = 0;
        int tnumber = 0;

        if(width >= 1.5*height){
            float rate = width/height;
            int colEnd;
            while(mline < height){
                for(int colStart = 0; colStart< width; colStart += width/rate){
                    if(width-colStart < width/rate){
                        colEnd = width;
                    }else{
                        colEnd = (int) (colStart+width/rate);
                    }
                    interpolationThreads.get(tnumber).addWorkingPoint(mline, colStart, colEnd);

                    tnumber +=1;
                    if(tnumber == interpolationThreads.size()){
                        tnumber=0;
                    }
                }

                mline +=1;
            }
        }else{
            while(mline < height){
                interpolationThreads.get(tnumber).addWorkingPoint(mline, 0, width);
                mline +=1;
                tnumber +=1;
                if(tnumber == interpolationThreads.size()){
                    tnumber=0;
                }
            }
        }



        for (int i =0; i<THREADS_NUMBER; i++){
            interpolationThreads.get(i).run();
        }

        for (int i =0; i<THREADS_NUMBER; i++){
            interpolationThreads.get(i).join();
        }


        return pixelMatrix;
    }
}
