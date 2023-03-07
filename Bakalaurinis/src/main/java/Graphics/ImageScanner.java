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
    private BufferedImage img;
    private BufferedImage paintedImage;
    
    private FigureData figureData;

    //LinkedList<LinkedList<Triangle>> triangles;

    //LinkedList<Triangle> trianglesTest;
    //LinkedList<LinkedList<Point>> pixels;

    private int ObjContourColor;
    private int ObjInnerColor;
    private int ObjEmptyColor;

    Point midPoint;
    public int width;
    public int height;
    
    private long lastModified;

    public ImageScanner(File file) {

        imageFile = file;

        // black
        ObjContourColor = 0xff000000;
        // green
        ObjInnerColor = 0xff22b14c;
        // white
        ObjEmptyColor = 0xffffffff;

        refreshImage();
    }
    
    public FigureData getFigureData(int triangleSize){
        
        figureData = new FigureData();
        
        figureData.inflatedTriangles = null;
        figureData.height = height;
        figureData.width = width;
        figureData.midPoint = midPoint;
        figureData.flatTriangles = getTriangulatedObject(triangleSize);
        figureData.contourPixels = getContourPixels();
        figureData.figureAreaPixels = getFigureAreaPixels();
        
        return figureData;
    }
    
    public boolean imageUpdated(){
        
        return lastModified != imageFile.lastModified();
    }
    
    public boolean refreshImage(){
        
        try {
            
            if (imageFile.exists()){
                
                paintedImage = ImageIO.read(imageFile);
                img = ImageIO.read(imageFile);
            }
           
            calculateMidPoint();
        
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
    
    private void calculateMidPoint(){
        
        int maxY = -1, minY = img.getHeight() - 1, maxX = -1, minX = img.getWidth() - 1;

            for (int y = 0; y < img.getHeight(); ++y) {

                for (int x = 0; x < img.getWidth(); ++x) {

                    int pixelColor = img.getRGB(x, y);

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

            midPoint = new Point(minY + (maxY - minY) / 2, minX + (maxX - minX) / 2);
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

                    Triangle tTopRight = triangulateTopRight(y, x, triangleSize);
                    Triangle tTopLeft = triangulateTopLeft(y, x, triangleSize);
                    Triangle tBotRight = triangulateBotRight(y, x, triangleSize);
                    Triangle tBotLeft = triangulateBotLeft(y, x, triangleSize);

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

    public LinkedList<LinkedList<Point>> getContourPixels() {
                   
        LinkedList<LinkedList<Point>> pixels = new LinkedList();
        LinkedList<Point> row = new LinkedList();
        
        try {

            img.flush();
            img = ImageIO.read(imageFile);

            boolean coloredPixelSequence = false;

            for (int y = 0; y < img.getHeight(); ++y) {

                for (int x = 0; x < img.getWidth(); ++x) {

                    int pixelColor = img.getRGB(x, y);

                    if (pixelColor == ObjInnerColor || pixelColor == ObjContourColor) {

                        coloredPixelSequence = true;

                        row.add(new Point(-(y - midPoint.y), x - midPoint.x));
                    } else if (coloredPixelSequence) {

                        coloredPixelSequence = false;
                        pixels.add(row);
                        row = new LinkedList();
                    }
                }

                if (!row.isEmpty()) {

                    pixels.add(row);
                    row = new LinkedList();
                }

                coloredPixelSequence = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("getContourPixels Exception");
        }

        return pixels;
    }
    
    public LinkedList<LinkedList<Point>> getFigureAreaPixels(){
        
        LinkedList<LinkedList<Point>> pixels = new LinkedList();
        LinkedList<Point> row = new LinkedList();
        
        try {

            boolean coloredPixelSequence = false;

            for (int y = 0; y < paintedImage.getHeight(); ++y) {

                for (int x = 0; x < paintedImage.getWidth(); ++x) {

                    int pixelColor = paintedImage.getRGB(x, y);

                    if (pixelColor == ObjInnerColor || pixelColor == ObjContourColor) {

                        coloredPixelSequence = true;

                        row.add(new Point(-(y - midPoint.y), x - midPoint.x));
                    } else if (coloredPixelSequence) {

                        coloredPixelSequence = false;
                        pixels.add(row);
                        row = new LinkedList();
                    }
                }

                if (!row.isEmpty()) {

                    pixels.add(row);
                    row = new LinkedList();
                }

                coloredPixelSequence = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("getFigureAreaPixels Exception");
        }
        
        return pixels;
    }

    private Triangle triangulateTopRight(int yy, int xx, int dimension) {

        Triangle triangle = null;

        int i = 0;

        boolean topRightCorner = true;

        outerloop:
        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx + i; x < xx + dimension; ++x) {

                if (paintedImage.getRGB(x, y) != ObjInnerColor) {

                    topRightCorner = false;
                    break outerloop;
                }
            }

            ++i;
        }

        if (topRightCorner) {

            triangle = new Triangle(new Point(yy - midPoint.y, xx - midPoint.x), new Point(yy - midPoint.y, xx + dimension - midPoint.x),
                    new Point(yy + dimension - midPoint.y, xx + dimension - midPoint.x));

            if (!triangle.topRight()) {

                System.out.println("Error, triangle not top right");
            }
        }

        return triangle;
    }

    private Triangle triangulateTopLeft(int yy, int xx, int dimension) {

        Triangle triangle = null;

        int i = 0;

        boolean topLeftCorner = true;

        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx; x < xx + dimension - i; ++x) {

                if (paintedImage.getRGB(x, y) != ObjInnerColor) {

                    topLeftCorner = false;
                    break;
                }
            }

            ++i;
        }

        if (topLeftCorner) {

            //triangle = new Triangle(new Point(yy-midPoint.y, xx-midPoint.x), new Point(yy-midPoint.y, xx+dimension-midPoint.x),
            //new Point(yy+dimension-midPoint.y, xx-midPoint.x));
            triangle = new Triangle(new Point(yy - midPoint.y, xx - midPoint.x), new Point(yy - midPoint.y, xx + dimension - midPoint.x),
                    new Point(yy + dimension - midPoint.y, xx - midPoint.x));

            if (!triangle.topLeft()) {

                System.out.println("Error, triangle not top left");
            }
        }

        return triangle;
    }

    private Triangle triangulateBotRight(int yy, int xx, int dimension) {

        Triangle triangle = null;

        int i = dimension - 2;

        boolean botRightCorner = true;

        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx + dimension - 1; x > xx + i; --x) {

                if (paintedImage.getRGB(x, y) != ObjInnerColor) {

                    botRightCorner = false;
                    break;
                }
            }

            --i;
        }

        if (botRightCorner) {

            triangle = new Triangle(new Point(yy - midPoint.y, xx + dimension - midPoint.x), new Point(yy + dimension - midPoint.y, xx - midPoint.x),
                    new Point(yy + dimension - midPoint.y, xx + dimension - midPoint.x));

            if (!triangle.botRight()) {

                System.out.println("Error, triangle not bot right");
            }
        }

        return triangle;
    }

    private Triangle triangulateBotLeft(int yy, int xx, int dimension) {

        Triangle triangle = null;

        int i = 1;

        boolean botLeftCorner = true;

        outerloop:
        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx; x < xx + i; ++x) {

                if (paintedImage.getRGB(x, y) != ObjInnerColor) {

                    botLeftCorner = false;
                    break outerloop;
                }
            }

            ++i;
        }

        if (botLeftCorner) {

            triangle = new Triangle(new Point(yy - midPoint.y, xx - midPoint.x), new Point(yy + dimension - midPoint.y, xx - midPoint.x),
                    new Point(yy + dimension - midPoint.y, xx + dimension - midPoint.x));

            if (!triangle.botLeft()) {

                System.out.println("Error, triangle not bot left");
            }
        }

        return triangle;
    }

    public void exportPaintedFigure(String location) {

        try {

            File outputfile = new File(location);
            ImageIO.write(paintedImage, "png", outputfile);
        } catch (Exception e) {

            System.out.println("exportPaintedFigure exception");
        }
    }
    
    public void printTriangles(LinkedList<LinkedList<Triangle>> triangles){
        
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
