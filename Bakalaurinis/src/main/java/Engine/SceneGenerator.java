/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Graphics.FigureData;
import Graphics.ImageScanner;
import static Graphics.ImageScanner.objInnerColor;
import Graphics.Point;
import Graphics.Triangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author gugu
 */
public class SceneGenerator {
    
    private String cameraSettings;
    private String lightSettings;
    private String floorSettings;
    private String transformationsSettings;
    
    private FigureData figureData;
    
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
    
    public void setFigureData(FigureData data){
     
        figureData = data;
    }
    
    public void generateFigurePixelsScene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<LinkedList<LinkedList<Point>>> rowsIt = figureData.figureAreaPixels.iterator();
            
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
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                            myWriter.write("pigment{Red}}\n");
                        }
                        
                        else if (y % 2 == 1 && x % 2 == 0){
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                            myWriter.write("pigment{Green}}\n");
                        }
                        
                        else if (y % 2 == 0 && x % 2 == 1){
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                            myWriter.write("pigment{Blue}}\n");
                        }
                        
                        else {
                            
                            myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
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
            
            System.out.println("Exception generateFigurePixelsScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFigurePixels2Scene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {
            
            File file = new File(inputLocation);

            FileWriter myWriter = new FileWriter(file);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<Point> rowsIt = figureData.figureAreaPixels2.iterator();
            
            int x = 0;
            int y = 1;
            
            while ( rowsIt.hasNext() ){
                
                ++y;
                ++x;
                
                Point point = rowsIt.next();

                if ( point.contour ){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                    myWriter.write("pigment{Red}}\n");
                }
                        
                if (point.commonTop){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                    myWriter.write("pigment{Red}}\n");
                }
                        
                if (point.color == ImageScanner.objInnerColor){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                    myWriter.write("pigment{Green}}\n");
                }
                
                if (point.segNEg){
                    
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius*9);
                    myWriter.write("pigment{Blue}}\n");
                }
                
                if (point.segPOs){
                    
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius*9);
                    myWriter.write("pigment{Cyan}}\n");
                }
                
                if (point.perpendicularLine){

                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius*2);
                    myWriter.write("pigment{Magenta}}\n");
                }
                
                if (point.midPoint){

                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius*9);
                    myWriter.write("pigment{Yellow}}\n");
                }
                    /*    
                else {
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + 0 + " >,"+pixelRadius);
                    myWriter.write("pigment{Yellow}}\n");
                }*/
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generatePixelsScene2 myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFigurePixels3Scene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<Point> rowsIt = figureData.figureAreaPixels2.iterator();
            
            for (int y = 0; y < figureData.height; ++y){
                
                for (int x = 0; x < figureData.width; ++x){
                    
                    Point p = figureData.grid[y][x];
                    
                    //if (p.color == ImageScanner.objEmptyColor)
                        //continue;
                    
                    if (p.color == ImageScanner.objContourColor){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius * 3);
                        myWriter.write("pigment{Red}}\n");
                    }
                    
                    if (p.color == ImageScanner.objInnerColor){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius);
                        myWriter.write("pigment{Green}}\n");
                    }
                    
                    if (p.perpendicularLine && p.linePoint){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius*6);
                        myWriter.write("pigment{Magenta}}\n");
                    }
                    /*
                    if (p.linePoint){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius * 5);
                        myWriter.write("pigment{Blue}}\n");
                    }
                    */
                    if (p.perpendicularLine){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius*2);
                        myWriter.write("pigment{Magenta}}\n");
                    }
                    
                    if (p.midPoint){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius*6);
                        myWriter.write("pigment{Cyan}}\n");
                    }
                    
                    if (p.marked4){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius*7);
                        myWriter.write("pigment{White}}\n");
                    }
                    
                    if (!p.linePoint && ImageScanner.hillTopColor(p.color)){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius);
                        myWriter.write("pigment{White}}\n");
                    }
                    
                    if (!p.linePoint) {
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius);
                        myWriter.write("pigment{Yellow}}\n");
                    }
                    
                    if (p.color == ImageScanner.objHillBotColor){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius * 3);
                        myWriter.write("pigment{White}}\n");
                    }
                    
                    if (p.segPOs){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius * 7);
                        myWriter.write("pigment{Blue}}\n");
                    }
                    
                    if (p.segNEg){
                        
                        myWriter.write("sphere{< " + p.x + ", " + p.y + ", " + 0 + " >,"+pixelRadius * 7);
                        myWriter.write("pigment{White}}\n");
                    }
                }
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFigurePixels3Scene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateTempScene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<Point> rowsIt = figureData.temp.iterator();
            
            while ( rowsIt.hasNext() ){

                Point point = rowsIt.next();
     
                myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius*4);
                myWriter.write("pigment{Yellow}}\n");
                 
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateTempScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFigureInflatedPixelsScene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {
            
            File file = new File(inputLocation);

            FileWriter myWriter = new FileWriter(file);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<Point> pointsIt = figureData.inflatedFigureAreaPixels2.iterator();
            
            int x = 0;
            int y = 0;
            
            while ( pointsIt.hasNext() ){
                 
                Point point = pointsIt.next();
                                  
                myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius*3);
                myWriter.write("pigment{Red}}\n");   
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFigureInflatedPixelsScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFigureInflatedPixels2Scene(String inputLocation, String outputLocation, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nunion{\n");  
            
            Iterator<Point> pointsIt = figureData.figureAreaPixels2.iterator();
            
            int x = 1;
            int y = 0;
            
            while ( pointsIt.hasNext() ){
                
                ++y;
                ++x;
                
                Point point = pointsIt.next();

                if ( point.midPoint){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                    myWriter.write("pigment{Red}}\n");
                }
                        
                else if (point.segNEg){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                    myWriter.write("pigment{Green}}\n");
                }
                        
                else if (point.segPOs){
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                    myWriter.write("pigment{Blue}}\n");
                }
                        
                else {
                            
                    myWriter.write("sphere{< " + point.x + ", " + point.y + ", " + point.z + " >,"+pixelRadius);
                    myWriter.write("pigment{Yellow}}\n");
                }
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFigureInflatedPixels2Scene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFigureTrianglesScene(String inputLocation, String outputLocation){
        
        try {
            
            File file = new File(inputLocation);
            
            FileWriter myWriter = new FileWriter(file);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nmesh\n{\n"); 
            
            //System.out.println("Triangles num " + figureData.inflatedTriangles.size());
            
            Iterator<Triangle> trianglesIt = figureData.inflatedTriangles.iterator();
            
            while ( trianglesIt.hasNext() ){
                
                Triangle triangle = trianglesIt.next();
                
                myWriter.write("triangle {");

                    myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                    myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                    myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFigureTrianglesScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void generateFlatTrianglesScene(String inputLocation, String outputLocation){
        
        try {
            
            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nmesh\n{\n"); 
            
            Iterator<Triangle> trianglesIt = figureData.inflatedTriangles.iterator();
            
            while ( trianglesIt.hasNext() ){
                
                Triangle triangle = trianglesIt.next();
                
                myWriter.write("triangle {");

                    myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", 0>, ");
                    myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", 0>, ");
                    myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", 0>}\n");
            }
            
            myWriter.write("\n"+transformationsSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception generateFlatTrianglesScene myWriter");
            
            e.printStackTrace();
            
            return;
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    private void executePovRay(String inputLocation, String outputLocation){
        
        File file1 = new File(inputLocation);
        inputLocation = file1.getAbsolutePath();
        
        File file2 = new File(outputLocation);
        outputLocation = file2.getAbsolutePath();
        
        // Create a Path object from the input path
        Path path = Paths.get(outputLocation);

        // Resolve the path to the final path
        Path resolvedPath = path.normalize();

        // Get the final path as a string
        outputLocation = resolvedPath.toString();
        
        path = Paths.get(inputLocation);

        // Resolve the path to the final path
        resolvedPath = path.normalize();

        // Get the final path as a string
        inputLocation = resolvedPath.toString();
        
        System.out.println(outputLocation);
        
            try {
                System.out.println("Opening povray");
                Runtime runTime = Runtime.getRuntime();
                System.out.println("Read from " + inputLocation + " write to " + outputLocation);
                System.out.println("Command " + "povray +I" + inputLocation + " +V +W1280 +H720 +O" + outputLocation);
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
