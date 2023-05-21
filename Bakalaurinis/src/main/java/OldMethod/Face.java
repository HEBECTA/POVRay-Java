/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OldMethod;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class Face {
    
    public LinkedList<Point> points;
    
    public Face(){
        
        this.points = new LinkedList<Point>();
    }
    
    public Face(Face f){
        
        this.points = f.points;
    }
    
    public Face(Point p1, Point p2, Point p3){
        
        this.points = new LinkedList<Point>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
    }
     
    public void addPoint(Point p){
        
        points.add(p);
    }
    
    public void clear(){
        
        points.clear();
    }
    
    public int size(){
        
        return points.size();
    }
    
    public boolean triangleUp(){
        
        if ( points.size() == 3 ){
            
            Iterator<Point> it = points.iterator();
            
            Point p1 = it.next();
            Point p2 = it.next();
            
            if ( p1.y != p2.y )
                return true;
        }

        return false;
    }
    
    public boolean triangleDown(){
        
        if ( points.size() == 3 ){
            
            Iterator<Point> it = points.iterator();
            
            Point p1 = it.next();
            Point p2 = it.next();
            
            if ( p1.y == p2.y )
                return true;
        }

        return false;
    }
    
    public boolean trapeze(){
        
        if ( points.size() == 4 )
            return true;
        
        return false;
    }
    
    
}

