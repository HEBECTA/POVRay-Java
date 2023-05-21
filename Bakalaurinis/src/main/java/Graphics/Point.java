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
public class Point {
    
    public float y, x, z;
    public int color;
    public int topColor;
    public boolean top;
    public boolean commonTop;
    public boolean bottom;
    public boolean contour;
    
    public boolean linePoint;
    public boolean perpendicularLine;
    public boolean midPoint;
    public boolean marked4;
    public boolean tempMarked;
    
    public boolean segPOs;
    public boolean segNEg;
    
    public boolean hillStartingPoint;
    
    public Point(Point p){
     
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        
        this.color = 0;
    }
    
    public Point(float y, float x){
        
        this(y, x, 0);
    }
    
    public Point(float y, float x, float z){
        
        this.y = y;
        this.x = x;
        this.z = z;
    }
    
    public Point(float y, float x, int color){
        
        this(y, x, 0, color);
    }
    
    public Point(float y, float x, float z, int color){
        
        this.y = y;
        this.x = x;
        this.z = z;
        
        this.color = color;
        
        this.bottom = false;
        this.top = false;
    }
    
    public void matrixMult(Matrix m){
                 
        x = m.M[0][0] * x + m.M[0][1] * y + m.M[0][2] * z;
        y = m.M[1][0] * x + m.M[1][1] * y + m.M[1][2] * z;
        z = m.M[2][0] * x + m.M[2][1] * y + m.M[2][2] * z;
    }
}
