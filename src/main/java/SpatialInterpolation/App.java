package SpatialInterpolation;

import SpatialInterpolation.algorithm.InterpolationAlgorithm;
import SpatialInterpolation.algorithm.ParallelAlgorithm;
import SpatialInterpolation.algorithm.SequentialAlgorithm;
import SpatialInterpolation.controller.Controller;

import java.util.Arrays;

import static java.lang.System.nanoTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            InterpolationAlgorithm sequential = new SequentialAlgorithm();
            InterpolationAlgorithm parallel = new ParallelAlgorithm();

            Controller controllerSeq = new Controller(sequential,"C:\\Users\\Xps 9560\\Documents\\PPD\\SpatialInterpolation\\src\\main\\java\\SpatialInterpolation\\input.txt", "outputSeq.png");
            Controller controllerPar = new Controller(parallel,"C:\\Users\\Xps 9560\\Documents\\PPD\\SpatialInterpolation\\src\\main\\java\\SpatialInterpolation\\input.txt", "outputPar.png");

            float start, end, total;

            start =  nanoTime() / 1000000;
            controllerSeq.createTemperatureMap();
            end =  nanoTime() / 1000000;
            total = (end - start);
            System.out.println("Sequential total: "+ total +"ms");

            start =  nanoTime() / 1000000;
            controllerPar.createTemperatureMap();
            end =  nanoTime() / 1000000;
            total = (end - start);
            System.out.println("Parallel total: "+ total +"ms");

            if(controllerPar.validate()){
                System.out.println("Same result from both approaches.");
            }else{
                System.out.println("The result is different.");
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
