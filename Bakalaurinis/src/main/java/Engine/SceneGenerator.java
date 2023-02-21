/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Graphics.Point;
import Graphics.Triangle;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class SceneGenerator {
    
    private String cameraSettings;
    private String lightSettings;
    private String floorSettings;
    private String transformationsSettings;
    
    private LinkedList<LinkedList<Point>> pixels;
    
    public void setPixels(LinkedList<LinkedList<Point>> pixels){
        
        this.pixels = pixels;
    }
    
    public void setCameraSettings(String settings){
        
        cameraSettings = settings;
    }
    
    public void setFloorSettings(String settings){
        
        lightSettings = settings;
    }
    
    public void setLightSettings(String settings){
        
        floorSettings = settings;
    }
    
    public void setTransformationSettings(String settings){
        transformationsSettings = settings;
    }
    
    public void generatePixelsScene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<LinkedList<Point>> rowIt = pixels.iterator();
            
            int x = 0;
            int y = 0;
            
            while ( rowIt.hasNext() ){
                
                ++y;
                
                LinkedList<Point> row = rowIt.next();

                Iterator<Point> pointIt = row.iterator();

                while ( pointIt.hasNext() ){
                    
                    ++x;

                    Point point = pointIt.next();
                    
                    if ( y % 2 == 0 && x % 2 == 0){
                            
                            myWriter.write("sphere{<"+point.x+","+point.y+",0>,"+pixelRadius);
                            myWriter.write("pigment{Red}}\n");
                        }
                        
                        else if (y % 2 == 1 && x % 2 == 0){
                            
                            myWriter.write("sphere{<"+point.x+","+point.y+",0>,"+pixelRadius);
                            myWriter.write("pigment{Green}}\n");
                        }
                        
                        else if (y % 2 == 0 && x % 2 == 1){
                            
                            myWriter.write("sphere{<"+point.x+","+point.y+",0>,"+pixelRadius);
                            myWriter.write("pigment{Blue}}\n");
                        }
                        
                        else {
                            
                            myWriter.write("sphere{<"+point.x+","+point.y+",0>,"+pixelRadius);
                            myWriter.write("pigment{Yellow}}\n");
                        }
                }
            
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception file export, Object class, exportPovRay function");
            
            e.printStackTrace();
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    private void executePovRay(String inputLocation, String outputLocation){
        
            try {
                System.out.println("Opening povray");
                Runtime runTime = Runtime.getRuntime();
                System.out.println("Read from " + inputLocation + " write to " + outputLocation);
                Process process = runTime.exec("povray +I" + inputLocation + " +V +W600 +H480 +O" + outputLocation);
                synchronized (process){ 
                    try {
                        //Thread.sleep(6000);
                        process.waitFor();
                        System.out.println(process.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Closing povray");
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        
    }
}
