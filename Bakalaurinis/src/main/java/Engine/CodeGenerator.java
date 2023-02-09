/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Graphics.Point;
import Graphics.Triangle;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class CodeGenerator {
    
    private String cameraSettings;
    private LinkedList<LinkedList<Point>> pixels;
    
    public void setPixels(LinkedList<LinkedList<Point>> pixels){
        
        this.pixels = pixels;
    }
    
    public void setCameraSettings(String settings){
        
        cameraSettings = settings;
    }
    
    public void setFloorSettings(String settings){
        
    }
    
    public void setLightSettings(String settings){
        
    }
    
    public void generatePixelsScene(String location, float pixelRadius){
        
        try {

            FileWriter myWriter = new FileWriter(location);

            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 

            myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}");
            myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");          

            myWriter.write("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}");

            myWriter.write("#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
            
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
                            
                            myWriter.write("sphere{<"+point.y+","+point.x+",0>,"+pixelRadius);
                            myWriter.write("pigment{Red}}\n");
                        }
                        
                        else if (y % 2 == 1 && x % 2 == 0){
                            
                            myWriter.write("sphere{<"+point.y+","+point.x+",0>,"+pixelRadius);
                            myWriter.write("pigment{Green}}\n");
                        }
                        
                        else if (y % 2 == 0 && x % 2 == 1){
                            
                            myWriter.write("sphere{<"+point.y+","+point.x+",0>,"+pixelRadius);
                            myWriter.write("pigment{Blue}}\n");
                        }
                        
                        else {
                            
                            myWriter.write("sphere{<"+point.y+","+point.x+",0>,"+pixelRadius);
                            myWriter.write("pigment{Yellow}}\n");
                        }
                }
            
            }

            myWriter.write("\n");
            
            myWriter.close();

        }
        catch ( Exception e){
            
            System.out.println("Exception file export, Object class, exportPovRay function");
            
            e.printStackTrace();
        }
    }
}
