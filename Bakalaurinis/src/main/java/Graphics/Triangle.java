/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Graphics.Point;

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
    
    public boolean topRight(){
        
        if ( p1.y == p2.y && p2.x == p3.x )
            return true;
        
        return false;   
    }
    
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
}

