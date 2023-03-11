/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class FigureData {
    
    public LinkedList<LinkedList<LinkedList<Point>>> contourPixels;
    public LinkedList<LinkedList<LinkedList<Point>>> flatAreaPixels;
    //public LinkedList<LinkedList<Triangle>> flatTriangles;
    
    //public LinkedList<Triangle> inflatedTriangles;
    public LinkedList<LinkedList<LinkedList<Point>>> inflatedFigureAreaPixels;
    
    public Point midPoint;
    public int width;
    public int height;
    
    public void translateFigureData(int y, int x, int z){
        
        translatePixels(0, 0, 0, contourPixels);
        translatePixels(0, 0, 0, flatAreaPixels);
        //translatePixels(0, 0, 0, inflatedFigureAreaPixels);
        
        //translateInflatedTriangles(y, x, z);
        //translateFlatTriangles(y, x, z);
        
    }
    
    private void translatePixels(int y, int x, int z, LinkedList<LinkedList<LinkedList<Point>>> list){
        
        Iterator<LinkedList<LinkedList<Point>>> rowsIt = list.iterator();
        
        while ( rowsIt.hasNext() ){
        
            LinkedList<LinkedList<Point>> rows = rowsIt.next();
            Iterator<LinkedList<Point>> rowLinesIt = rows.iterator();

            while ( rowLinesIt.hasNext() ){
                    
                LinkedList<Point> rowLines = rowLinesIt.next();
                    
                Iterator<Point> linePixelsIt = rowLines.iterator();
                    
                while ( linePixelsIt.hasNext() ){
                        
                    Point point = linePixelsIt.next();
                    
                    point.y = (point.y + midPoint.y) + y;
                    point.x = (point.x - midPoint.x) + x;
                    point.z = (point.z - midPoint.z) + z;
                }
            }
        }
    }
    
    public static void printPixels(LinkedList<LinkedList<LinkedList<Point>>> list){
        
        Iterator<LinkedList<LinkedList<Point>>> rowsIt = list.iterator();
            
        while ( rowsIt.hasNext() ){

            LinkedList<LinkedList<Point>> rows = rowsIt.next();

            Iterator<LinkedList<Point>> rowLinesIt = rows.iterator();
            
            System.out.println("Row");

            while ( rowLinesIt.hasNext() ){
                    
                LinkedList<Point> rowLines = rowLinesIt.next();
                    
                Iterator<Point> linePixelsIt = rowLines.iterator();
                
                System.out.println("Line");
                    
                while ( linePixelsIt.hasNext() ){
                       
                    Point point = linePixelsIt.next();
                        
                    System.out.println("Pixel y " + point.y + ", x " + point.x + ", z " + point.z);
                }
                
                System.out.println("");
            }
            
            System.out.println("");
        }
    }
    /*
    private void translateInflatedTriangles(int y, int x, int z){
        
        Iterator<Triangle> it = inflatedTriangles.iterator();
        
        while ( it.hasNext() ){

                Triangle triangle = it.next();
                   
                Point p1 = triangle.p1;
                Point p2 = triangle.p2;
                Point p3 = triangle.p3;
                
                //p1.y = p1.y + p1.y;
                p1.x = (p1.x - midPoint.x) + x;
                p1.z = (p1.z - midPoint.z) + z;
                
                //p2.y = -(p2.y - midPoint.y) + y;
                p2.x = (p2.x - midPoint.x) + x;
                p2.z = (p2.z - midPoint.z) + z;
                
                //p3.y = -(p3.y - midPoint.y) + y;
                p3.x = (p3.x - midPoint.x) + x;
                p3.z = (p3.z - midPoint.z) + z;
            }
    }
    
    private void translateFlatTriangles(int y, int x, int z){
     
        Iterator<LinkedList<Triangle>> rowIt = flatTriangles.iterator();
        
        while ( rowIt.hasNext() ){

                LinkedList<Triangle> row = rowIt.next();

                Iterator<Triangle> triangleIt = row.iterator();

                while ( triangleIt.hasNext() ){

                    Triangle triangle = triangleIt.next();
                    
                    Point p1 = triangle.p1;
                    Point p2 = triangle.p2;
                    Point p3 = triangle.p3;

                    p1.y = (p1.y - midPoint.y) + y;
                    p1.x = (p1.x - midPoint.x) + x;
                    p1.z = (p1.z - midPoint.z) + z;

                    p2.y = (p2.y - midPoint.y) + y;
                    p2.x = (p2.x - midPoint.x) + x;
                    p2.z = (p2.z - midPoint.z) + z;

                    p3.y = (p3.y - midPoint.y) + y;
                    p3.x = (p3.x - midPoint.x) + x;
                    p3.z = (p3.z - midPoint.z) + z;
                }
            }
    }*/
}
