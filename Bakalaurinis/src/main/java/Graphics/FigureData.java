/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import static Graphics.ImageScanner.objInnerColor;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class FigureData {
    
    public Point[][] grid;
    
    public LinkedList<LinkedList<LinkedList<Point>>> contourPixels;
    public LinkedList<LinkedList<LinkedList<Point>>> figureAreaPixels;
    public LinkedList<Point> figureAreaPixels2;
    public LinkedList<Point> inflatedFigureAreaPixels2;
    //public LinkedList<Point> figureAreaPixels3;
    //public LinkedList<LinkedList<LinkedList<Point>>> figureAreaPixelsRef;
    public LinkedList<Point> temp;
    
    //public LinkedList<LinkedList<Point>> flatAreaPixelsRef;
    //public LinkedList<LinkedList<Triangle>> flatTriangles;
    
    public LinkedList<Triangle> inflatedTriangles;
    public LinkedList<LinkedList<LinkedList<Point>>> inflatedFigureAreaPixels;
    
    public Point midPoint;
    public int width;
    public int height;
    
    public void translateFigureData(int base_y, int base_x, int base_z){
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
                
                //grid[y][x].y = (-grid[y][x].y + midPoint.y) + base_y;
                grid[y][x].y = (grid[y][x].y + base_y) - midPoint.y;
                grid[y][x].x = (grid[y][x].x + base_x) - midPoint.x;
                grid[y][x].z = grid[y][x].z + base_z;
            }
        }
        //translateTriangles(base_y, base_x, base_z);
    }
    
    public void translateFigureData(){
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x< width; ++x){
                
                grid[y][x].y -= height / 2; 
                grid[y][x].x -= width / 2; 
            }
        }
        
        Iterator<Triangle> trianglesIt = inflatedTriangles.iterator();
        LinkedList<Triangle> tList = new LinkedList<>();
        
        while (trianglesIt.hasNext()){
            
            Triangle triangle = trianglesIt.next();
            
            if (triangle != null){
                
                if (triangle.p1 != null && triangle.p2 != null && triangle.p3 != null){
                    
                    tList.add(triangle.getInvertedTriangle());
                    tList.add(triangle);
                }
            } 
        }
        
        //inflatedTriangles.addAll(tList);
        
        inflatedTriangles = tList;
        /*
        Iterator<Point> pointsIt = temp.iterator();
        
        while (pointsIt.hasNext()){
            
            Point p = pointsIt.next();
            
            p.y -= height / 2; 
            
            p.x -= width / 2; 
        }
             */   
    }
    
    /*
    private void translateTriangles(int y, int x, int z){
        
        Iterator<Triangle> trianglesIt = inflatedTriangles.iterator();
        
        while (trianglesIt.hasNext()){
            
            Triangle triangle = trianglesIt.next();
            
            Point p1 = triangle.p1;
            Point p2 = triangle.p2;
            Point p3 = triangle.p3;
            
            p1.y = (-p1.y + midPoint.y) + y;
            p1.x = (p1.x - midPoint.x) + x;
            p1.z = (p1.z - midPoint.z) + z;
            
            p2.y = (-p2.y + midPoint.y) + y;
            p2.x = (p2.x - midPoint.x) + x;
            p2.z = (p2.z - midPoint.z) + z;
            
            p3.y = (-p3.y + midPoint.y) + y;
            p3.x = (p3.x - midPoint.x) + x;
            p3.z = (p3.z - midPoint.z) + z;
        }
    }
    */
    public void printInfo(){
        
        System.out.println("height " + height + ", width " + width);
        
        for (int y = 0; y < height; ++y){
         
            for (int x = 0; x < width; ++x){
                
                System.out.println("Grid y " + y + ", x " + x + "; Point y " + grid[y][x].y + ", x " + grid[y][x].x + ", z " + grid[y][x].z + ", color " + grid[y][x].color);
            }
        }
    }
    
    public void flipPixels(){
        
        for (int y = 0; y < height / 2; ++y){
            
            for (int x = 0; x < width; ++x){

                Point temp = grid[y][x];
                grid[y][x] = grid[height - (y + 1)][x];
                grid[y][x].color = grid[height - (y + 1)][x].color;
                grid[height - (y + 1)][x] = temp;
                grid[height - (y + 1)][x].color = temp.color;
            }
        }
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){

                grid[y][x].y = y;
                grid[y][x].x = x;
            }
        }
    }
    
    public void printPixelsCount(){
        
        int count = 0;
        
        for (int y = 0; y < height; ++y){
         
            for (int x = 0; x < width; ++x){
                
                if (grid[y][x].color == ImageScanner.objInnerColor)
                    ++count;
            }
        }
        
        System.out.println("Pixels count: " + count);
    }
}
