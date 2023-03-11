/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Graphics.FigureData;
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
    
    public void generateFigurePixelsScene(String inputLocation, String outputLocation, LinkedList<LinkedList<LinkedList<Point>>> list, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<LinkedList<LinkedList<Point>>> rowsIt = list.iterator();
            
            int x = 0;
            int y = 0;
            
            while ( rowsIt.hasNext() ){
                
                ++y;
                
                LinkedList<LinkedList<Point>> rows = rowsIt.next();

                Iterator<LinkedList<Point>> rowLinesIt = rows.iterator();

                while ( rowLinesIt.hasNext() ){
                    
                    LinkedList<Point> rowLines = rowLinesIt.next();
                    
                    Iterator<Point> linePixelsIt = rowLines.iterator();
                    
                    while ( linePixelsIt.hasNext() ){
                        
                        ++x;

                        Point point = linePixelsIt.next();
                        
                        if ( y % 2 == 0 && x % 2 == 0){
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                            myWriter.write("pigment{Red}}\n");
                        }
                        
                        else if (y % 2 == 1 && x % 2 == 0){
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                            myWriter.write("pigment{Green}}\n");
                        }
                        
                        else if (y % 2 == 0 && x % 2 == 1){
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                            myWriter.write("pigment{Blue}}\n");
                        }
                        
                        else {
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                            myWriter.write("pigment{Yellow}}\n");
                        }
                    }
                }
            
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generatePixelsScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    /*
    public void generateFlatTrianglesScene(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            Iterator<LinkedList<Triangle>> rowIt = figureData.flatTriangles.iterator();
        
            myWriter.write("\nmesh\n{\n");
            
            while ( rowIt.hasNext() ){

                LinkedList<Triangle> row = rowIt.next();

                Iterator<Triangle> triangleIt = row.iterator();

                while ( triangleIt.hasNext() ){

                    Triangle triangle = triangleIt.next();
                    
                    myWriter.write("triangle {");

                    myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                    myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                    myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
                }
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFinalScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateInflatedTrianglesScene(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            Iterator<Triangle> it = figureData.inflatedTriangles.iterator();
        
            myWriter.write("\nmesh\n{\n");
            
            while ( it.hasNext() ){

                Triangle triangle = it.next();
                    
                myWriter.write("triangle {");

                myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
                
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFinalScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    */
    private void executePovRay(String inputLocation, String outputLocation){
        
            try {
                System.out.println("Opening povray");
                Runtime runTime = Runtime.getRuntime();
                System.out.println("Read from " + inputLocation + " write to " + outputLocation);
                Process process = runTime.exec("povray +I" + inputLocation + " +V +W1280 +H720 +O" + outputLocation);
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
