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
public class FigureData {
    
    public LinkedList<LinkedList<Triangle>> triangles;
    public LinkedList<LinkedList<Point>> contourPixels;
    public LinkedList<LinkedList<Point>> figureAreaPixels;
    
    public Point midPoint;
    public int width;
    public int height;
}
