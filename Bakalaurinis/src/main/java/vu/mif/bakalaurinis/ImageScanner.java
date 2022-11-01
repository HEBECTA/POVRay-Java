/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vu.mif.bakalaurinis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author GUGU
 */
public class ImageScanner {
    
    private FileOperation fileHandler;  
    private BufferedImage img;
    
    LinkedList<LinkedList<Triangle>> triangles;
    
    LinkedList<Triangle> trianglesTest;
    LinkedList<LinkedList<Point>> pixels;
    
    private int ObjContourColor;
    private int ObjInnerColor;
    
    Point midPoint;
    int width;
    int height;
    
    public ImageScanner(File file){
        
        fileHandler = new FileOperation("/home/gugu/stuff/povray");
        
        System.out.println(file.getAbsolutePath()); 
  
        // black
        ObjContourColor = 0xff000000;
        // green
        ObjInnerColor = 0xff22b14c;

        try {
            
            img = ImageIO.read(file);
            
            int maxY = -1, minY = img.getHeight()-1, maxX = -1, minX = img.getWidth()-1;
            
            for (int y = 0; y < img.getHeight(); ++y ){
                    
                for (int x = 0; x < img.getWidth(); ++x ){
                    
                    int pixelColor = img.getRGB(x, y);
                    
                    if ( pixelColor == ObjContourColor ){
                        
                        if ( maxY < y )
                            maxY = y;
                        
                        if ( minY > y )
                            minY = y;
                        
                        if ( maxX < x )
                            maxX = x;
                        
                        if ( minX > x )
                            minX = x;
                    }
                    
                    if ( pixelColor == ObjInnerColor ){
                        
                        if ( y - 1 >= 0 )
                            fillObject(y-1, x);
                        
                        else if (y+1<img.getHeight())
                            fillObject(y-1, x);
                        
                        else if (x-1<0)
                            fillObject(y, x-1);
                        
                        else if (x+1<img.getWidth())
                            fillObject(y, x+1);
                        
                        else
                            System.out.println("Image constructor, nothing to fill !");
                        
                        break;
                    }
                }
            }
            
            midPoint = new Point(minY+(maxY - minY)/2, minX+(maxX - minX)/2);
            width = maxX-minX;
            height = maxY-minY;
            //File outputfile = new File("image.png");
            //ImageIO.write(img, "png", outputfile);    
        }
        catch(Exception e){
            
            e.printStackTrace();
            
            System.out.println("Image constructor exception");
        }
    }
    
    private void fillObject(int y, int x){
        
        //System.out.println("fillObject " + y + ", " + x);
    
        int pixelColor = img.getRGB(x, y);
        
        if ( pixelColor != ObjContourColor && pixelColor != ObjInnerColor) { 
            
            img.setRGB(x, y, ObjInnerColor);
            //System.out.println("empty pixel " + y + ", " + x);
        }

        else 
            return;
      
        if ( y-1 >= 0 ){
            
            fillObject(y-1, x);
        }
        
        if ( y+1 < img.getHeight() ){
            
            fillObject(y+1, x);
        }
        
        if ( x-1 >= 0){
            
            fillObject(y, x-1);
        }
        
        if ( x+1 < img.getWidth() ){
            
            fillObject(y, x+1);
        }
    }
    
    // check boundaries
    // triangleSize => opposite and adjacent side size in pixels
    public LinkedList<LinkedList<Triangle>> getTriangulatedObject(int triangleSize){
        
        triangles = new LinkedList<>();
        LinkedList<Triangle> row;// = new LinkedList();

        int i = triangleSize - 1;
        //int i = triangleSize;
        
        boolean coloredPixelSequence = false;
 
        for (int y = 0; y < img.getHeight(); y+= i ){
            
            row = new LinkedList();
            
            for (int x = 0; x < img.getWidth(); x += i){
                
                int pixelColor = img.getRGB(x, y);
                
                if ( pixelColor == ObjInnerColor || pixelColor == ObjContourColor){
                    
                    coloredPixelSequence = true;
                    
                    Triangle tTopRight = triangulateTopRight(y, x, triangleSize);
                    Triangle tTopLeft = triangulateTopLeft(y, x, triangleSize);
                    Triangle tBotRight = triangulateBotRight(y, x, triangleSize);
                    Triangle tBotLeft = triangulateBotLeft(y, x, triangleSize);
                    
                    if ( tTopRight != null || tBotLeft != null ){
                        
                        if ( tBotLeft != null )
                            row.add(tBotLeft);

                        if ( tTopRight != null )
                            row.add(tTopRight);
                    }
               
                    else if ( tTopLeft != null || tBotRight != null ){
                        
                        if ( tTopLeft != null )
                            row.add(tTopLeft);
                            
                        if ( tBotRight != null )
                            row.add(tBotRight);  
                    }
                    
                    else {
                        
                        coloredPixelSequence = false;
                        
                        if ( row.size() > 0 ){
                            
                            triangles.add(row);
                            row = new LinkedList();
                        }
                    }
                }
           
                else if ( coloredPixelSequence ){
                    
                    coloredPixelSequence = false;
                    triangles.add(row);
                    row = new LinkedList();
                }
            }

            if ( coloredPixelSequence )
                triangles.add(row);
 
            coloredPixelSequence = false;  
        }
        
        return triangles;
    }
    
    public LinkedList<LinkedList<Point>> getFigurePixels(){
        
        pixels = new LinkedList();
        LinkedList<Point> row = new LinkedList();
        
        boolean coloredPixelSequence = false;

        for (int y = 0; y < img.getHeight(); ++y ){
            
            for (int x = 0; x < img.getWidth(); ++x){
                
                int pixelColor = img.getRGB(x, y);
                
                if ( pixelColor == ObjInnerColor || pixelColor == ObjContourColor){
                    
                    coloredPixelSequence = true;
                    
                    row.add(new Point(y-midPoint.y ,x-midPoint.x)); 
                }
                
                else if ( coloredPixelSequence ){
                    
                    coloredPixelSequence = false;
                    pixels.add(row);
                    row = new LinkedList();
                }
            }
            
            if ( !row.isEmpty() ){
                
                pixels.add(row);
                row = new LinkedList();
            }
            
            coloredPixelSequence = false; 
        }
        
        return pixels;
    }
    
    private Triangle triangulateTopRight(int yy, int xx, int dimension){
        
        Triangle triangle = null;
        
        int i = 0;
        
        boolean topRightCorner = true;
        
        outerloop:
        for (int y = yy; y < yy + dimension; ++y){
         
            for (int x = xx+i; x < xx + dimension; ++x){
                
                if ( img.getRGB(x, y) != ObjInnerColor ){
                    
                    topRightCorner = false;
                    break outerloop;
                }
            }

            ++i;
        }
        
        if ( topRightCorner ){
            
            triangle = new Triangle(new Point(yy-midPoint.y, xx-midPoint.x), new Point(yy-midPoint.y, xx+dimension-midPoint.x),
                    new Point(yy+dimension-midPoint.y, xx+dimension-midPoint.x));
            
            if ( !triangle.topRight()){
             
                System.out.println("Error, triangle not top right");
            }
        }
        
        return triangle;
    }
    
    private Triangle triangulateTopLeft(int yy, int xx, int dimension){
        
        Triangle triangle = null;
        
        int i = 0;
        
        boolean topLeftCorner = true;
        
        for (int y = yy; y < yy + dimension; ++y){
         
            for (int x = xx; x < xx + dimension-i; ++x){
                
                if ( img.getRGB(x, y) != ObjInnerColor ){
                    
                    topLeftCorner = false;
                    break;
                }
            }
            
            ++i;
        }
        
        if ( topLeftCorner ){
            
            //triangle = new Triangle(new Point(yy-midPoint.y, xx-midPoint.x), new Point(yy-midPoint.y, xx+dimension-midPoint.x),
                    //new Point(yy+dimension-midPoint.y, xx-midPoint.x));
                    
            triangle = new Triangle(new Point(yy-midPoint.y, xx-midPoint.x), new Point(yy-midPoint.y, xx+dimension-midPoint.x),
                new Point(yy+dimension-midPoint.y, xx-midPoint.x));
            
            if ( !triangle.topLeft()){
             
                System.out.println("Error, triangle not top left");
            }
        }
        
        return triangle;
    }
    
    private Triangle triangulateBotRight(int yy, int xx, int dimension){
        
        Triangle triangle = null;
        
        int i = dimension-2;
        
        boolean botRightCorner = true;
        
        for (int y = yy; y < yy + dimension; ++y){
         
            for (int x = xx + dimension-1; x > xx + i ; --x){
                
                if ( img.getRGB(x, y) != ObjInnerColor ){
                    
                    botRightCorner = false;
                    break;
                }
            }
            
            --i;
        }
        
        if ( botRightCorner ){
            
            triangle = new Triangle(new Point(yy-midPoint.y, xx+dimension-midPoint.x), new Point(yy+dimension-midPoint.y, xx-midPoint.x),
                    new Point(yy+dimension-midPoint.y, xx+dimension-midPoint.x));
            
            if ( !triangle.botRight() ){
             
                System.out.println("Error, triangle not bot right");
            }
        }
        
        
        
        return triangle;
    }
    
    private Triangle triangulateBotLeft(int yy, int xx, int dimension){

        Triangle triangle = null;
        
        int i = 1;
        
        boolean botLeftCorner = true;
        
        outerloop:
        for (int y = yy; y < yy + dimension; ++y){
         
            for (int x = xx; x < xx+i; ++x){
                
                if ( img.getRGB(x, y) != ObjInnerColor ){

                    botLeftCorner = false;
                    break outerloop;
                }
            }
            
            ++i;
        }
        
        if ( botLeftCorner ){
            
            triangle = new Triangle(new Point(yy-midPoint.y, xx-midPoint.x), new Point(yy+dimension-midPoint.y, xx-midPoint.x),
                    new Point(yy+dimension-midPoint.y, xx+dimension-midPoint.x));
            
            if ( !triangle.botLeft()){
             
                System.out.println("Error, triangle not bot left");
            }
        }
        
        return triangle;
    }  
    
    public void exportFillPovRay(String location, float radius){
        
        try {

            FileWriter myWriter = new FileWriter(location);

            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write("camera{ location  <0,0,300>\n  angle 40\nright     x*image_width/image_height\nlook_at   <0,0,0>\n}");   
 

            myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}");
            myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");          

            myWriter.write("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}");

            myWriter.write("#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
            
            for (int y = 0; y < img.getHeight(); ++y ){
            
                for (int x = 0; x < img.getWidth(); ++x){

                    if ( img.getRGB(x, y) == ObjInnerColor){
                        
                        float yy = (midPoint.y-y);
                        float xx = (midPoint.x-x);
                        
                        if ( y % 2 == 0 && x % 2 == 0){
                            
                            myWriter.write("sphere{<"+yy+","+xx+",0>,"+radius);
                            myWriter.write("pigment{Red}}\n");
                        }
                        
                        else if (y % 2 == 1 && x % 2 == 0){
                            
                            myWriter.write("sphere{<"+yy+","+xx+",0>,"+radius);
                            myWriter.write("pigment{Green}}\n");
                        }
                        
                        else if (y % 2 == 0 && x % 2 == 1){
                            
                            myWriter.write("sphere{<"+yy+","+xx+",0>,"+radius);
                            myWriter.write("pigment{Blue}}\n");
                        }
                        
                        else {
                            
                            myWriter.write("sphere{<"+yy+","+xx+",0>,"+radius);
                            myWriter.write("pigment{Yellow}}\n");
                        }
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
    
    public void exportPaintedFigure(String location){
        
        try {
            
            File outputfile = new File(location);
            ImageIO.write(img, "png", outputfile);
        }
        catch ( Exception e){
            
            System.out.println("exportPaintedFigure exception");
        }
    }
}
