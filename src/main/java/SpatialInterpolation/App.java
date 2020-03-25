package SpatialInterpolation;

import SpatialInterpolation.controller.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            Controller controller = new Controller("C:\\Users\\Xps 9560\\Documents\\PPD\\SpatialInterpolation\\src\\main\\java\\SpatialInterpolation\\input.txt", "output.png");
            controller.createTemperatureMap();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
