package SpatialInterpolation.controller;

import SpatialInterpolation.algorithm.InterpolationAlgorithm;
import SpatialInterpolation.algorithm.SequentialAlgorithm;
import SpatialInterpolation.utils.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xps 9560
 */
public class Controller {
    private InterpolationAlgorithm interpolationAlgorithm;
    private String inputFilename;
    private String outputFilename;
    private Double[][] pixelMatrix;
    private HashMap<Key, Double> observationPoints;
    private int w, h;

    public Controller(String inputFilename, String outputFilename) throws IOException {
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
        this.observationPoints = new HashMap<>();
        readInputData();
        constructPixelMatrix();
        interpolationAlgorithm = new SequentialAlgorithm();
    }

    private void constructPixelMatrix(){
        pixelMatrix = new Double[this.h][this.w];

        for(Map.Entry<Key,Double> obsPoint:observationPoints.entrySet()){
            Key key = obsPoint.getKey();
            Double val = obsPoint.getValue();

            pixelMatrix[key.getX()][key.getY()] = val;
        }

    }

    private void readInputData() throws IOException {
        File file = new File(inputFilename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String[] arrOfStr;
        line=br.readLine();
        w = Integer.parseInt(line);
        line=br.readLine();
        h=Integer.parseInt(line);
        while ((line = br.readLine()) != null){
            arrOfStr = line.split(";");
            Key key = new Key(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]));
            Double value = Double.parseDouble(arrOfStr[2]);
            observationPoints.put(key, value);
//            System.out.println(line);
        }

    }

    public void createTemperatureMap() throws IOException {
        BufferedImage tempMap = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
        pixelMatrix = interpolationAlgorithm.interpolation(pixelMatrix, w, h, observationPoints);
        for (int i=0; i<this.h;i++){
            for (int j=0;j<this.w;j++){
                Float hue = generateHue(pixelMatrix[i][j]);
                Color pixelColor =  Color.getHSBColor(hue, 1F,1F);
                tempMap.setRGB(i,j ,pixelColor.getRGB());
            }
        }
        File outputFile = new File(outputFilename);
        ImageIO.write(tempMap, "png", outputFile);
    }

    private float generateHue(Double pixelValue){
        if(pixelValue<0) {
            return normalizeNegativeHueValue(pixelValue);
        }
        return normalizePositiveHueValue(pixelValue);
    }

    float normalizePositiveHueValue(Double pixelValue){
        Float hue = 150 - pixelValue.floatValue()*3;
        return  hue/300;
    }

    float normalizeNegativeHueValue(Double pixelValue) {
        Float hue = 150 + (-1)*pixelValue.floatValue()*3;
        return hue/300;
    }
}

