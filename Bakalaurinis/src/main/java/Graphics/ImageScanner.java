/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Graphics.Triangle;
import Graphics.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import IDE.FileOperation;
import java.util.Iterator;

/**
 *
 * @author GUGU
 */
public class ImageScanner {

    //private FileOperation fileHandler; 
    private File imageFile;
    private BufferedImage originalImage;
    private BufferedImage paintedImage;
    
    private FigureData figureData;

    //LinkedList<LinkedList<Triangle>> triangles;

    //LinkedList<Triangle> trianglesTest;
    //LinkedList<LinkedList<Point>> pixels;

    private int ObjContourColor;
    private int ObjInnerColor;
    private int ObjEmptyColor;

    int maxY, minY;
    int maxX, minX;
    public int width;
    public int height;
    
    private long lastModified;

    public ImageScanner(File file) {

        imageFile = file;

        // black
        ObjContourColor = 0xff000000;
        // green
        ObjInnerColor = 0xff22b14c; // 34 177 76
        // white
        ObjEmptyColor = 0xffffffff;

        refreshImage();
    }
    
    public FigureData getFigureData(int triangleSize){
        
        figureData = new FigureData();
        
        figureData.height = height;
        figureData.width = width;
        figureData.midPoint = getMidPoint();
        figureData.contourPixels = getContourPixels();
        figureData.flatAreaPixels = getFlatAreaPixels();
        //figureData.flatTriangles = getTriangulatedObject(triangleSize);
        
        return figureData;
    }
    
    public boolean imageUpdated(){
        
        return lastModified != imageFile.lastModified();
    }
    
    public boolean refreshImage(){
        
        try {
            
            if (imageFile.exists()){
                
                paintedImage = ImageIO.read(imageFile);
                originalImage = ImageIO.read(imageFile);
            }
           
            calculateDimensions();
        
            Point innerPixel = findColor(0, 0, ObjInnerColor);
            if (innerPixel == null)
                return false;

            innerPixel = findColor((int)innerPixel.y, (int)innerPixel.x, ObjEmptyColor);
            if (innerPixel == null)
                return false;

            fillObject((int)innerPixel.y, (int)innerPixel.x);
            
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("refreshImage Exception");
        }
        
        return true;
    }
    
    public Point getMidPoint(){
        
        return new Point(minY + (maxY - minY) / 2, minX + (maxX - minX) / 2);
    }
    
    public LinkedList<LinkedList<LinkedList<Point>>> getContourPixels() {
                   
        LinkedList<LinkedList<LinkedList<Point>>> pixels = new LinkedList<>();
        LinkedList<LinkedList<Point>> linesList = new LinkedList<>();
        LinkedList<Point> linePixelsList = new LinkedList<>();
        
        boolean coloredPixelSequence = false;
        
        for (int y = 0; y < originalImage.getHeight(); ++y) {

                for (int x = 0; x < originalImage.getWidth(); ++x) {

                    int pixelColor = originalImage.getRGB(x, y);

                    if (pixelColor == ObjInnerColor || pixelColor == ObjContourColor) {

                        coloredPixelSequence = true;
                        linePixelsList.add(new Point(-y, x));
                    }
                    else if (coloredPixelSequence) {

                        coloredPixelSequence = false;
                        linesList.add(linePixelsList);
                        linePixelsList = new LinkedList();
                    }
                }

                if (!linePixelsList.isEmpty()) {

                    linesList.add(linePixelsList);
                    linePixelsList = new LinkedList();
                }
                
                if (!linesList.isEmpty()) {

                    pixels.add(linesList);
                    linesList = new LinkedList();
                }

                coloredPixelSequence = false;
            }
        
        return pixels;
    }
    
    public LinkedList<LinkedList<LinkedList<Point>>> getFlatAreaPixels(){
        
        LinkedList<LinkedList<LinkedList<Point>>> pixels = new LinkedList<>();
        LinkedList<LinkedList<Point>> linesList = new LinkedList<>();
        LinkedList<Point> linePixelsList = new LinkedList<>();
        
        boolean coloredPixelSequence = false;
        
        for (int y = 0; y < paintedImage.getHeight(); ++y) {

                for (int x = 0; x < paintedImage.getWidth(); ++x) {

                    int pixelColor = paintedImage.getRGB(x, y);

                    if (pixelColor == ObjInnerColor || pixelColor == ObjContourColor) {

                        coloredPixelSequence = true;
                        linePixelsList.add(new Point(-y, x));
                    }
                    else if (coloredPixelSequence) {

                        coloredPixelSequence = false;
                        linesList.add(linePixelsList);
                        linePixelsList = new LinkedList();
                    }
                }

                if (!linePixelsList.isEmpty()) {

                    linesList.add(linePixelsList);
                    linePixelsList = new LinkedList();
                }
                
                if (!linesList.isEmpty()) {

                    pixels.add(linesList);
                    linesList = new LinkedList();
                }

                coloredPixelSequence = false;
            }
        
        return pixels;
    }
    
    /*
    // check boundaries
    // triangleSize => opposite and adjacent side size in pixels
    public LinkedList<LinkedList<Triangle>> getTriangulatedObject(int triangleSize) {
        
        LinkedList<LinkedList<Triangle>> triangles = new LinkedList<>();;
        LinkedList<Triangle> row;
        
        int i = triangleSize - 1;
        //int i = triangleSize;

        boolean coloredPixelSequence = false;

        for (int y = 0; y < paintedImage.getHeight(); y += i) {

            row = new LinkedList();

            for (int x = 0; x < paintedImage.getWidth(); x += i) {

                int pixelColor = paintedImage.getRGB(x, y);

                if (pixelColor == ObjInnerColor || pixelColor == ObjContourColor) {
                    
                    //System.out.println("traingulating pixel y " + y + " x " + x);

                    coloredPixelSequence = true;

                    Triangle tTopRight = Triangle.triangulateTopRight(y, x, triangleSize, paintedImage, ObjInnerColor);
                    Triangle tTopLeft = Triangle.triangulateTopLeft(y, x, triangleSize, paintedImage, ObjInnerColor);
                    Triangle tBotRight = Triangle.triangulateBotRight(y, x, triangleSize, paintedImage, ObjInnerColor);
                    Triangle tBotLeft = Triangle.triangulateBotLeft(y, x, triangleSize, paintedImage, ObjInnerColor);

                    if (tTopRight != null || tBotLeft != null) {

                        if (tBotLeft != null) {
                            tBotLeft.mirrorYfrom();
                            row.add(tBotLeft);
                        }

                        if (tTopRight != null) {
                            tTopRight.mirrorYfrom();
                            row.add(tTopRight);
                        }
                    } else if (tTopLeft != null || tBotRight != null) {

                        if (tTopLeft != null) {
                            tTopLeft.mirrorYfrom();
                            row.add(tTopLeft);
                        }

                        if (tBotRight != null) {
                            tBotRight.mirrorYfrom();
                            row.add(tBotRight);
                        }
                    } else {

                        coloredPixelSequence = false;

                        if (row.size() > 0) {

                            triangles.add(row);
                            row = new LinkedList();
                        }
                    }
                } else if (coloredPixelSequence) {

                    coloredPixelSequence = false;
                    triangles.add(row);
                    row = new LinkedList();
                }
                //System.out.println("y " + y + "row size " + row.size());
            }

            if (coloredPixelSequence) {
                triangles.add(row);
            }

            coloredPixelSequence = false;
        }

        return triangles;
    }
    */
    public void exportPaintedFigure(String location) {

        try {

            File outputfile = new File(location);
            ImageIO.write(paintedImage, "png", outputfile);
        } catch (Exception e) {

            System.out.println("exportPaintedFigure exception");
        }
    }
    
    private void calculateDimensions(){
        
        maxY = -1;
        minY = originalImage.getHeight() - 1;
        maxX = -1;
        minX = originalImage.getWidth() - 1;

            for (int y = 0; y < originalImage.getHeight(); ++y) {

                for (int x = 0; x < originalImage.getWidth(); ++x) {

                    int pixelColor = originalImage.getRGB(x, y);

                    if (pixelColor == ObjContourColor) {

                        if (maxY < y) {
                            maxY = y;
                        }

                        if (minY > y) {
                            minY = y;
                        }

                        if (maxX < x) {
                            maxX = x;
                        }

                        if (minX > x) {
                            minX = x;
                        }
                    }
                }
            }

        width = maxX - minX;
        height = maxY - minY;
    }
    
    private Point findColor(int base_y, int base_x, int color){
        
        for (int y = base_y; y < paintedImage.getHeight(); ++y){
            
            for (int x = base_x; x < paintedImage.getWidth(); ++x){
                
                if (paintedImage.getRGB(x, y) == color)
                    return new Point(y, x);
            }
        }
        
        return null;
    }
    
    private void fillObject(int y, int x) {
        
        int pixelColor = paintedImage.getRGB(x, y);

        if (pixelColor != ObjContourColor && pixelColor != ObjInnerColor) {

            paintedImage.setRGB(x, y, ObjInnerColor);
            //System.out.println("empty pixel " + y + ", " + x);
        } else return;
        

        if (y - 1 >= 0) {

            fillObject(y - 1, x);
        }

        if (y + 1 < paintedImage.getHeight()) {

            fillObject(y + 1, x);
        }

        if (x - 1 >= 0) {

            fillObject(y, x - 1);
        }

        if (x + 1 < paintedImage.getWidth()) {

            fillObject(y, x + 1);
        }
    }
    
    public static void printTriangles(LinkedList<LinkedList<Triangle>> triangles){
        
        int i = 0;
        int k = 0;
        
        Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        outerloop:
        while ( rowIt.hasNext() ){

            LinkedList<Triangle> row = rowIt.next();

            Iterator<Triangle> triangleIt = row.iterator();
            //outerloop:
            while ( triangleIt.hasNext() ){

                Triangle triangle = triangleIt.next();
                    
                System.out.println("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                System.out.println("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                System.out.println("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
                ++k;
                if (k > 10)
                    break outerloop;
                        
            }
            ++i;
            if ( i > 25)
                return;
        }
    }
}
