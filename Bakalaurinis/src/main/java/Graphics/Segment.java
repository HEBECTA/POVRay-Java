/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class Segment {
    
    boolean isCircle;
    
    Point middPoint;
    
    Point posPoint;
    Point negPoint;
    
    LinkedList<Point> positiveLine;
    LinkedList<Point> negativeLine;
    
    LinkedList<Point> inflatedPosLine;
    LinkedList<Point> inflatedNegLine;
    
    float len;
    
    public Segment(){
        
        middPoint = null;
        posPoint = null;
        negPoint = null;
        positiveLine = null;
        negativeLine = null;
        
        inflatedPosLine = null;
        inflatedNegLine = null;
        
        len = 0;
    }
    
}
