/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

/**
 *
 * @author GUGU
 */
public class Triangle {
    
    public Point p1, p2, p3;
    
    int r, g, b;
    
    public Triangle(Point p1, Point p2, Point p3){
        
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        
        r = 1;
        g = 1;
        b = 1;
    }
    
    public Triangle(Triangle t){
         
        this.p1 = new Point(t.p1);
        this.p2 = new Point(t.p2);
        this.p3 = new Point(t.p3);
        
        r = 1;
        g = 1;
        b = 1;
    }
    
    public void print(){
        
        System.out.println("p1 y " + p1.y + ", x " + p1.x + ", z " + p1.z + ", \np2 y " + p2.y + ", x " + p2.x + ", z " + p2.z + ", \np3 y " + p3.y + ", x " + p3.x + ", z " + p3.z);
    }
    
    public void setColor(int r, int g, int b){
        
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /*
    *******
     ******
      *****
       ****
        ***
         **
          *
    */
    public boolean topRight(){
        
        if ( p1.y == p2.y && p2.x == p3.x )
            return true;
        
        return false;   
    }
    
    /*
    *******
    ******
    *****
    ****
    ***
    **
    *
    */
    public boolean topLeft(){
        
        if ( p1.y == p2.y && p1.x == p3.x )
            return true;
        
        return false;   
    }
    
    public boolean botRight(){
        
        if ( p2.y == p3.y && p1.x == p3.x )
            return true;
        
        return false;   
    }
    
    public boolean botLeft(){
        
        if ( p2.y == p3.y && p1.x == p2.x )
            return true;
        
        return false;   
    }
    
    public void mirrorYfrom(){
        
        p1.y = -p1.y;
        p2.y = -p2.y;
        p3.y = -p3.y;
    }
    
    public Triangle getInvertedTriangle(){
     
        Triangle invertedTriangle = new Triangle(this);
        
        invertedTriangle.p1.z = -invertedTriangle.p1.z;
        invertedTriangle.p2.z = -invertedTriangle.p2.z;
        invertedTriangle.p3.z = -invertedTriangle.p3.z;
        
        return invertedTriangle;
    }
    
    public static Triangle triangulateTopRight(int yy, int xx, int dimension, Point[][] grid, int color) {

        Triangle triangle = null;

        for (int i = 0; i < dimension; ++i){
            
            for (int k = i; k < dimension; ++k){
                
                if (grid[yy - i][xx + k].color != color)
                    return null;
            }
        }

        Point p1 = new Point(grid[yy][xx].y, grid[yy][xx].x, grid[yy][xx].z);
        Point p2 = new Point(grid[yy][xx+dimension].y, grid[yy][xx+dimension].x, grid[yy][xx+dimension].z);
        Point p3 = new Point(grid[yy-dimension][xx+dimension].y, grid[yy-dimension][xx+dimension].x, grid[yy-dimension][xx+dimension].z);
            
        triangle = new Triangle(p1, p2, p3);
            
        if (!triangle.topRight()) {

            System.out.println("Error, triangle not top right");
        }
        
        return triangle;
    }
    
    public static Triangle triangulateTopLeft(int yy, int xx, int dimension, Point[][] grid, int color) {

        Triangle triangle = null;

        for (int i = 0; i < dimension; ++i) {

            for (int k = 0; k < dimension - i; ++k) {

                if (grid[yy - i][xx + k].color != color)
                    return null;
            }
        }

        Point p1 = new Point(grid[yy][xx].y, grid[yy][xx].x, grid[yy][xx].z);
        Point p2 = new Point(grid[yy][xx+dimension].y, grid[yy][xx+dimension].x, grid[yy][xx+dimension].z);
        Point p3 = new Point(grid[yy-dimension][xx].y, grid[yy-dimension][xx].x, grid[yy-dimension][xx].z);
            
        triangle = new Triangle(p1, p2, p3);
            
        if (!triangle.topLeft()) {

            System.out.println("Error, triangle not top left");
        }
        
        return triangle;
    }
    
    public static Triangle triangulateBotRight(int yy, int xx, int dimension, Point[][] grid, int color) {

        Triangle triangle = null;

        for (int i = 0; i < dimension; ++i) {

            for (int k = 0; k < i + 1; ++k) {

                if (grid[yy - i][xx + dimension - 1 - k].color != color)
                    return null;
            }

        }
                    
        Point p1 = new Point(grid[yy][xx + dimension].y, grid[yy][xx + dimension].x, grid[yy][xx + dimension].z);
        Point p2 = new Point(grid[yy-dimension][xx].y, grid[yy-dimension][xx].x, grid[yy-dimension][xx].z);
        Point p3 = new Point(grid[yy-dimension][xx+dimension].y, grid[yy-dimension][xx+dimension].x, grid[yy-dimension][xx+dimension].z);
            
        triangle = new Triangle(p1, p2, p3);
                    
        if (!triangle.botRight()) {

            System.out.println("Error, triangle not bot right");
        }
        
        return triangle;
    }

    public static Triangle triangulateBotLeft(int yy, int xx, int dimension, Point[][] grid, int color) {

        Triangle triangle = null;

        for (int i = 0; i < dimension; ++i) {

            for (int k = 0; k < i + 1; ++k) {

                if (grid[yy - i][xx + k].color != color)
                    return null;
            }
        }

        Point p1 = new Point(grid[yy][xx].y, grid[yy][xx].x, grid[yy][xx].z);
        Point p2 = new Point(grid[yy-dimension][xx].y, grid[yy-dimension][xx].x, grid[yy-dimension][xx].z);
        Point p3 = new Point(grid[yy-dimension][xx+dimension].y, grid[yy-dimension][xx+dimension].x, grid[yy-dimension][xx+dimension].z);
            
        triangle = new Triangle(p1, p2, p3);
                    
        if (!triangle.botLeft()) {

            System.out.println("Error, triangle not bot left");
        }
        
        return triangle;
    }
}

