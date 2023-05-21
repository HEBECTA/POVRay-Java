/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import static Graphics.ImageScanner.objHillBotColor;
import static Graphics.ImageScanner.objInnerColor;
import Graphics.Triangle;
import Graphics.Point;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author GUGU
 */
public class Object3D {
    
    FigureData figureData;
    
    LinkedList<LinkedList<Point>> inflationVectors;
    LinkedList<LinkedList<Point>> hillTopLines;
    float maxLineLen;
    
    LinkedList<LinkedList<Point>> hillTopLines3;
    LinkedList<LinkedList<Point>> inflationPoints3;
    LinkedList<LinkedList<LinkedList<Point>>> inflationVectors3;
    LinkedList<LinkedList<LinkedList<Point>>> inflationVectors33;
    float maxLineLen3 = 0;
    //int iter = 0;
    
    LinkedList<LinkedList<Segment>> segmentsLines;
    boolean []segmentIsCircle;
    
    boolean linesIntersect = false;
    
    Point[][] pp;
    
    public Object3D(){
        
    }
    
    public void setFigureData(FigureData data){
        
        figureData = data;
    }
    
    public void inflateFigure3(float dept){
       
        generateVectors3();
        
        System.out.println("Longest line " + maxLineLen3);
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            System.out.println("seg size " + segmentsLine.size());
              
            while (segmentsIt.hasNext()){
                
                Segment segment = segmentsIt.next();
                
                segment.middPoint.z = (segment.len * dept) / maxLineLen3;
            }

            
            if (segmentsLine.size() > 1){
                
                segmentsLine.getFirst().middPoint.z = 0;
                segmentsLine.getLast().middPoint.z = 0;
            }
        }
        
        triangulate33();
        
        getFigurePixels3();
    }
    
    private void triangulate33(){
        
        figureData.inflatedTriangles = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        int i = 0;
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            System.out.println("triangulate seg size " + segmentsLine.size());

            Segment segmentPrev;
            
            if (!segmentsIt.hasNext())
                continue;
            
            segmentPrev = segmentsIt.next();
            Segment segment = null;
            
            System.out.println("pirmas seg " + segmentsIt.hasNext());
            
            while(segmentsIt.hasNext()) {
                
                segment = segmentsIt.next();
                         
                Triangle triangle;

                if (segmentPrev.negPoint != null && segment.negPoint != null){
                    
                    triangle = new Triangle(segmentPrev.middPoint, segmentPrev.negPoint, segment.negPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                if (segment.negPoint != null){
                    
                    triangle = new Triangle(segmentPrev.middPoint, segment.middPoint, segment.negPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                if (segmentPrev.posPoint != null && segment.posPoint != null){
                    
                    triangle = new Triangle(segmentPrev.middPoint, segmentPrev.posPoint, segment.posPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                    
                }
                
                if (segment.posPoint != null){
                    
                    triangle = new Triangle(segmentPrev.middPoint, segment.middPoint, segment.posPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                segmentPrev = segment;
            } 
            /*
            if (segmentIsCircle[i] && segmentsLine.size() > 2){

                Segment first = segmentsLine.getFirst();
                Segment last = segmentsLine.getLast();
                
                Triangle triangle;
                if (first.negPoint != null && last.negPoint != null){
                    
                    triangle = new Triangle(first.middPoint, first.negPoint, last.negPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                if (last.negPoint != null){
                    
                    triangle = new Triangle(first.middPoint, last.middPoint, last.negPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                if (first.posPoint != null && last.posPoint != null){
                    
                    triangle = new Triangle(first.middPoint, first.posPoint, last.posPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                    
                }
                
                if (last.posPoint != null){
                    
                    triangle = new Triangle(first.middPoint, last.middPoint, last.posPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
            }*/
            
            ++i;
        }
    }
    
    private void triangulate3(){
        
        Iterator<LinkedList<LinkedList<Point>>> segment3It = inflationVectors3.iterator();
        Iterator<LinkedList<LinkedList<Point>>> segment33It = inflationVectors33.iterator();
        
        while (segment3It.hasNext()){
            
            LinkedList<LinkedList<Point>> lines3 = segment3It.next();
            
            Iterator<LinkedList<Point>> lines3It = lines3.iterator();
            
            Point p1 = null;
            Point p2 = null;
            Point p3 = null;
            Point p4 = null;
            
            if (lines3It.hasNext()){
                
                LinkedList<Point> points3 = lines3It.next();
                p1 = points3.getFirst();
                p2 = points3.getLast();
            }
            
            if (lines3It.hasNext()){
                
                LinkedList<Point> points3 = lines3It.next();
                p3 = points3.getFirst();
                p4 = points3.getLast();
            }
            
            figureData.inflatedTriangles.add(new Triangle(p1, p2, p4));
            figureData.inflatedTriangles.add(new Triangle(p1, p3, p4));
        }
        
        while (segment33It.hasNext()){
            
            LinkedList<LinkedList<Point>> lines33 = segment33It.next();
          
            Iterator<LinkedList<Point>> lines33It = lines33.iterator();
            
            Point p1 = null;
            Point p2 = null;
            Point p3 = null;
            Point p4 = null;
            
            if (lines33It.hasNext()){
                
                LinkedList<Point> points3 = lines33It.next();
                p1 = points3.getFirst();
                p2 = points3.getLast();
            }
            
            if (lines33It.hasNext()){
                
                LinkedList<Point> points3 = lines33It.next();
                p3 = points3.getFirst();
                p4 = points3.getLast();
            }
            
            figureData.inflatedTriangles.add(new Triangle(p1, p2, p4));
            figureData.inflatedTriangles.add(new Triangle(p1, p3, p4));
        }
    }
    
    private void generateVectors3(){
        
        LinkedList<Point> startPoints = findTopHillStartingPoints();
        
        //inflationPoints3 = new LinkedList<>();
        
        //inflationVectors3 = new LinkedList<>();
        //inflationVectors33 = new LinkedList<>();
        
        hillTopLines3 = new LinkedList<>();
        
        segmentIsCircle = new boolean[startPoints.size()];
        
        Iterator<Point> it = startPoints.iterator();
        
        int k = 0;
        while (it.hasNext()){
            
            Point startPoint = it.next();
            
            LinkedList<Point> line = generateLine(startPoint);
            
            hillTopLines3.add(line);
            
            if (lineIsCircle(line.getFirst()))
                segmentIsCircle[k] = true;
            
            else
                segmentIsCircle[k] = false;
            
            ++k;
        }
        
        Iterator<LinkedList<Point>> linesIt = hillTopLines3.iterator();
        
        segmentsLines = new LinkedList<>();
        
        while (linesIt.hasNext()){
 
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
           
            int accuracy = 5;
            
            //LinkedList<LinkedList<Point>> segment3 = new LinkedList<>();
            //LinkedList<LinkedList<Point>> segment33 = new LinkedList<>();
            //LinkedList<Point> segmentPoints = new LinkedList<>();
            
            LinkedList<Segment> segments = new LinkedList<>();

            for (int i = 0; i < points.length - accuracy; i += 17){
                
                Point p1 = points[i];
                Point p2 = points[i + accuracy];
                
                Segment seg = generateSegment(p1, p2);
                
                if (seg == null){
                    
                    System.out.println("seg == null");
                }
                
                else
                    segments.add(seg);

                //float midX = (p2.x + p1.x) / 2;
                //float midY = (p2.y + p1.y) / 2;
                
                //segmentPoints.add(figureData.grid[(int)Math.round(midY)][(int)Math.round(midX)]);
                //segment3.add(generatePerpendicularLine(p1, p2, true));
                //segment33.add(generatePerpendicularLine(p1, p2, false));
            }
            
            segmentsLines.add(segments);
            
            //inflationVectors3.add(segment3);
            //inflationVectors33.add(segment33);
            //inflationPoints3.add(segmentPoints);
        }
    }
    
    private Segment generateSegment(Point p1, Point p2){
        
        Segment segment = new Segment();
        
        float midX = (p2.x + p1.x) / 2;
        float midY = (p2.y + p1.y) / 2;
        
        segment.middPoint = figureData.grid[(int)Math.round(midY)][(int)Math.round(midX)];
        segment.middPoint.midPoint = true;
        
        segment.negativeLine = generatePerpendicularLine(p1, p2, false);
        segment.positiveLine = generatePerpendicularLine(p1, p2, true);
        
        if (linesIntersect){
            
            linesIntersect = false;
            return null;
        }
        
        if (segment.negativeLine.size() > 1){
            
            segment.negPoint = segment.negativeLine.getLast();
            segment.negPoint.segNEg = true;
        }
        
        if (segment.positiveLine.size() > 1){
            
            segment.posPoint = segment.positiveLine.getLast();
            segment.posPoint.segPOs = true;
        }
        
        if (segment.posPoint != null && segment.negPoint != null)
            segment.len = distance(segment.negPoint.y, segment.negPoint.x, segment.posPoint.y, segment.posPoint.x);
        
        else if (segment.posPoint != null )
            segment.len = distance(midY, midX, segment.posPoint.y, segment.posPoint.x);
        
        else
            segment.len = distance(segment.negPoint.y, segment.negPoint.x, midY, midX);

        if (segment.len > maxLineLen3)
            maxLineLen3 = segment.len;
        
        return segment;
    }
    
    private float returnLongestLineLen3(){
        
        Iterator<LinkedList<LinkedList<Point>>> segment3It = inflationVectors3.iterator();
        Iterator<LinkedList<LinkedList<Point>>> segment33It = inflationVectors33.iterator();
        
        float maxLen = 0;
        
        while (segment3It.hasNext()){
            
            LinkedList<LinkedList<Point>> lines3 = segment3It.next();
            LinkedList<LinkedList<Point>> lines33 = segment33It.next();
            
            Iterator<LinkedList<Point>> lines3It = lines3.iterator();
            Iterator<LinkedList<Point>> lines33It = lines33.iterator();
            
            while (lines3It.hasNext()){
                
                LinkedList<Point> points3 = lines3It.next();
                LinkedList<Point> points33 = lines33It.next();
                
                float len3 = 0;
                float len33 = 0;
                
                Point p1 = null;
                Point p2 = null;
                
                if ( points3.size() > 1 ){
                    
                    p1 = points3.getFirst();
                    p2 = points3.getLast();
                    len3 = distance(p1.y, p1.x, p2.y, p2.x);
                }
                
                if (points33.size() > 1){
                    
                    p1 = points33.getFirst();
                    p2 = points33.getLast();
                    len33 = distance(p1.y, p1.x, p2.y, p2.x);
                }
                
                if (len3 + len33 > maxLen){
                    
                    maxLen = len3 + len33;
                    System.out.println("maxLen " + maxLen);
                    System.out.println("p y " + p1.y + ", x " + p1.x);
                    System.out.println("p y " + p2.y + ", x " + p2.x);
                    //System.out.println("p y " + p2.y + ", x " + p2.x);
                }
                  
                
                if (p1 != null)
                    p1.z = len3 + len33;
            }
        }
        
        return maxLen;
    }
    
    public void inflateFigure2(float dept){
        
        generateVectors();
        
        maxLineLen = returnLongestLineLen();
        
        updateTopHillColor();
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (figureData.grid[y][x].color != ImageScanner.objInnerColor)
                    continue;
                
                figureData.grid[y][x].z = findInflationValue2(figureData.grid[y][x], dept);
            }
        }
        
        triangulateFigureOld();
        getFigurePixels();
        getFigureTriangles();
    }
    
    public void inflateFigure(float dept){
        
        generateVectors();
        
        maxLineLen = returnLongestLineLen();
        
        inflateVectors(dept);
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (figureData.grid[y][x].color != ImageScanner.objInnerColor)
                    continue;
                
                if (figureData.grid[y][x].z > 0)
                    continue;
                
                figureData.grid[y][x].z = findInflationValue(figureData.grid[y][x]);
            }
        }
        
        triangulateFigureOld();
        getFigurePixels();
        getFigureTriangles();
    }
    
    private float findInflationValue2(Point p, float dept){
        
        float minDist = (float)Math.sqrt(figureData.height * figureData.height + figureData.width * figureData.width);
        float lineDist = 0;
        
        Iterator<LinkedList<Point>> linesIt = hillTopLines.iterator();
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> points = linesIt.next();
            
            Iterator<Point> pointsIt = points.iterator();
            
            while (pointsIt.hasNext()){
                
                Point linePoint = pointsIt.next();
                
                float dist = distanceToLinePoint(p, linePoint);
                
                if (dist < minDist){
                    
                    minDist = dist;
                    lineDist = fullLineDistance(p, linePoint);
                }
            }
        }
        
        float inflationDept = (lineDist * dept) / maxLineLen;
        float scale = (float)(Math.PI / 2) / lineDist;
        
        return (float)(Math.round((Math.cos( minDist * scale ) * inflationDept) * 100000d) / 100000d);
    }
    
    private float fullLineDistance(Point innerPoint, Point linePoint){
         
        float diagnol = (float)Math.sqrt(figureData.height * figureData.height + figureData.width * figureData.width);
        
        float dy = linePoint.y - innerPoint.y;
        float dx = linePoint.x - innerPoint.x;
        
        float magnitude = (float)Math.sqrt(dy*dy + dx*dx);
        float normalizedX = dx / magnitude;
        float normalizedY = dy / magnitude;
        
        if (linePoint.x < innerPoint.x)
            diagnol *= -1;
        
        float distPointX = linePoint.x + normalizedX * diagnol;
        float distPointY = linePoint.y + normalizedY * diagnol;
        
        Point endPoint = bresenhamAlgorithmEndPoint(linePoint.y, linePoint.x, distPointY, distPointX);
         
        return distance(linePoint.y, linePoint.x, endPoint.y, endPoint.x);
    }
    
    private float distanceToLinePoint(Point innerPoint, Point linePoint){

        if (!bresenhamAlgorithmLineConnectable(innerPoint.y, innerPoint.x, linePoint.y, linePoint.x, linePoint.color))
            return 1000000;
        
        return distance(innerPoint.y, innerPoint.x, linePoint.y, linePoint.x);
    }

    private float findInflationValue(Point p){
        
        LinkedList<Point> nearestVector;
        float nearestDist = (float) Math.sqrt(figureData.height * figureData.height + figureData.width * figureData.width);
        float nearestZ = 0;
        
        Iterator<LinkedList<Point>> linesIt = inflationVectors.iterator();
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> points = linesIt.next();
            
            Iterator<Point> pointsIt = points.iterator();
            
            while (pointsIt.hasNext()){
                
                Point point = pointsIt.next();
                /*
                float dx = point.x - p.x;
                float dy = point.y - p.y;
                
                float dist = (float)Math.sqrt(dx*dx+dy*dy);
                */
                float dist = distance(point.y, point.x, p.y, p.x);
                
                if (dist < nearestDist){
                    
                    nearestDist = dist;
                    nearestVector = points;
                    nearestZ = point.z;
                }
            }
        }
        
        return nearestZ;
    }
    
    private void inflateVectors(float dept){
        
        Iterator<LinkedList<Point>> vectorsIt = inflationVectors.iterator();
        
        while (vectorsIt.hasNext()){
            
            LinkedList<Point> line = vectorsIt.next();
            Iterator<Point> pointsIt = line.iterator();
            
            if (line.size() < 2)
                continue;
            
            float dx = line.getLast().x - line.getFirst().x;
            float dy = line.getLast().y - line.getFirst().y;
            
            int rowLength = (int)Math.round(Math.sqrt(dx * dx + dy * dy));
            float inflationDept = (rowLength * dept) / maxLineLen;
            float scale = (float)(Math.PI / 2) / rowLength;
            
            while (pointsIt.hasNext()){
                
                Point point = pointsIt.next();
                
                int currDx = (int)Math.round(point.x - line.getFirst().x);
                int currDy = (int)Math.round(point.y - line.getFirst().y);
                int currDist = (int)Math.round(Math.sqrt(currDx * currDx + currDy * currDy));
                
                float z = (float)(Math.round((Math.cos( currDist * scale ) * inflationDept) * 100000d) / 100000d);
                
                if (point.z > 0){
                    
                    point.z += z;
                    point.z /= 2;
                }
                
                else
                    point.z = z;
            }
        }
    }
    
    private void generateVectors(){
        
        LinkedList<Point> startPoints = findTopHillStartingPoints();
        
        inflationVectors = new LinkedList<>();
        
        hillTopLines = new LinkedList<>();
        
        Iterator<Point> it = startPoints.iterator();
        
        while (it.hasNext()){
            
            Point startPoint = it.next();
            
            hillTopLines.add(generateLine(startPoint));
        }
        
        Iterator<LinkedList<Point>> linesIt = hillTopLines.iterator();
        
        while (linesIt.hasNext()){
 
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
           
            int accuracy = 4;

            for (int i = 0; i < points.length - accuracy; i += 15){
                
                Point p1 = points[i];
                Point p2 = points[i + accuracy];

                inflationVectors.add(generatePerpendicularLine(p1, p2, true));
                inflationVectors.add(generatePerpendicularLine(p1, p2, false));
            }
        }
    }
    
    private float returnLongestLineLen(){
        
        Iterator<LinkedList<Point>> vectorsIt = hillTopLines.iterator();
        
        float maxLen = 0;
        
        while (vectorsIt.hasNext()){
            
            LinkedList<Point> line = vectorsIt.next();
            
            Point p1 = line.getFirst();
            Point p2 = line.getLast();
            
            float dx = p1.x - p2.x;
            float dy = p1.y - p2.y;
            
            float len = (float)Math.sqrt(dx * dx + dy * dy);
            
            if (len > maxLen)
                maxLen = len;
        }
        
        return maxLen;
    }
    
    private LinkedList<Point> generatePerpendicularLine(Point p1, Point p2, boolean opposite){
        
        float x1 = p1.x;
        float x2 = p2.x;
        
        float y1 = p1.y;
        float y2 = p2.y;
        
        float dx = x2 - x1;
        float dy = y2 - y1;
        
        float perpendicularX = -dy;
        float perpendicularY = dx;
        
        double len = Math.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);
        double normalizedX = perpendicularX / len;
        double normalizedY = perpendicularY / len;
        
        //float quantizedX = (int) Math.round(normalizedX);
        //float quantizedY = (int) Math.round(normalizedY);
        
        float midX = (x2 + x1) / 2;
        float midY = (y2 + y1) / 2;
        
        float lineLength = (float)Math.sqrt(figureData.height * figureData.height + figureData.width * figureData.width);
        float lineLength2 = 15;
        
        if (opposite)
            lineLength *= -1;
        
        if (opposite)
            lineLength2 *= -1;
        
        int perpendicularPointX = (int)Math.round(midX + normalizedX * lineLength);
        int perpendicularPointY = (int)Math.round(midY + normalizedY * lineLength);
        
        int perpendicularPointX2 = (int)Math.round(midX + normalizedX * lineLength2);
        int perpendicularPointY2 = (int)Math.round(midY + normalizedY * lineLength2);
        
        //System.out.println("Mid point y " + midY + ", x " + midX);
        //System.out.println("Second point y " + perpendicularPointY + ", x " + perpendicularPointX);
        
        figureData.grid[(int)Math.round(midY)][(int)Math.round(midX)].midPoint = true;
        //figureData.grid[perpendicularPointY2][perpendicularPointX2].marked4 = true;
        p1.linePoint = true;
        p2.linePoint = true;
        return bresenhamAlgorithmNew(midY, midX, perpendicularPointY, perpendicularPointX);
    }
    
    private Point bresenhamAlgorithmEndPoint(float y1, float x1, float y2, float x2){
        //++iter;
        int dx, dy, err, sx, sy;
        
        dx = (int)Math.round(Math.abs(x2 - x1));
        dy = (int)Math.round(Math.abs(y2 - y1));
        
        sx = x1 < x2 ? 1 : -1;
        sy = y1 < y2 ? 1 : -1;
        
        err = dx - dy;
        
        int xx = 0;
        int yy = 0;
        
        while (x1 != x2 || y1 != y2){
            
            int err2 = 2 * err;
            
            if (err2 > -dy){
                
                err -= dy;
                x1 += sx;
            }
            
            if (err2 < dx){
                
                err+= dx;
                y1 += sy;
            }
            
            xx = (int)Math.round(x1);
            yy = (int)Math.round(y1);
            
            if ( yy < 0 || yy >= figureData.height || xx < 0 || xx >= figureData.width )
                break;
            
            if (lineEnd2(figureData.grid[yy][xx]))
                break;
            
            //if (iter == 50){
                
               //igureData.grid[yy][xx].marked4 = true;
           // }
        }
        
        if (yy < 0)
            yy = 0;
        
        if (yy >= figureData.height)
            yy = figureData.height - 1;
        
        if (xx < 0)
            xx = 0;
        
        if (xx >= figureData.width)
            xx = figureData.width - 1;
        
        //if (iter == 50){
                
             // iter = 0;  
        //}
        
        //System.out.println("yy " + yy + ", x " + xx);
        //System.out.println("height " + figureData.height + ", width " + figureData.width);
           
        return figureData.grid[yy][xx];
    }
    
    private boolean bresenhamAlgorithmLineConnectable(float y1, float x1, float y2, float x2, int color){
        
        int dx, dy, err, sx, sy;
        
        dx = (int)Math.round(Math.abs(x2 - x1));
        dy = (int)Math.round(Math.abs(y2 - y1));
        
        sx = x1 < x2 ? 1 : -1;
        sy = y1 < y2 ? 1 : -1;
        
        err = dx - dy;
        
        while (x1 != x2 || y1 != y2){
            
            int err2 = 2 * err;
            
            if (err2 > -dy){
                
                err -= dy;
                x1 += sx;
            }
            
            if (err2 < dx){
                
                err+= dx;
                y1 += sy;
            }
            
            int xx = (int)Math.round(x1);
            int yy = (int)Math.round(y1);
            
            if ( !(yy >= 0 && yy < figureData.height && xx >= 0 && xx < figureData.width) )
                return false;
            
            if (figureData.grid[yy][xx].color == ImageScanner.objContourColor ||
                    figureData.grid[yy][xx].color == ImageScanner.objHillBotColor)
                return false;
            
            if (figureData.grid[yy][xx].color != ImageScanner.objInnerColor && figureData.grid[yy][xx].color != color)
                return false;
        }
            
        return true;
    }
    
    private LinkedList<Point> bresenhamAlgorithmNew(float y1, float x1, float y2, float x2){
        
        LinkedList<Point> line = new LinkedList<>();

        int dx, dy, err, sx, sy;
        
        dx = (int)Math.round(Math.abs(x2 - x1));
        dy = (int)Math.round(Math.abs(y2 - y1));
        
        sx = x1 < x2 ? 1 : -1;
        sy = y1 < y2 ? 1 : -1;
        
        err = dx - dy;
        
        while (x1 != x2 || y1 != y2){
            
            int err2 = 2 * err;
            
            if (err2 > -dy){
                
                err -= dy;
                x1 += sx;
            }
            
            if (err2 < dx){
                
                err+= dx;
                y1 += sy;
            }
            
            int xx = (int)Math.round(x1);
            int yy = (int)Math.round(y1);
            
            if ( !(yy >= 0 && yy < figureData.height && xx >= 0 && xx < figureData.width) )
                break;
            
            if (lineEnd(figureData.grid[yy][xx]))
                break;
            
            line.add(figureData.grid[yy][xx]);
            
            if (figureData.grid[yy][xx].perpendicularLine){
                
                linesIntersect = true;
            }
            
            figureData.grid[yy][xx].perpendicularLine = true;
            
        }
            
        return line;
    }
    
    private LinkedList<Point> bresenhamAlgorithm(float y1, float x1, float y2, float x2){
        
        LinkedList<Point> line = new LinkedList<>();
        /*
        if (x1 > x2){
            
            float tmp = x1;
            x1 = x2;
            x2 = tmp;
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        */
        int dx, dy, p, x, y;
        
        dx = (int)Math.round(x2 - x1);
        dy = (int)Math.round(y2 - y1);
        
        x = (int)Math.round(x1);
        y = (int)Math.round(y1);
        
        line.add(figureData.grid[y][x]);
        figureData.grid[y][x].perpendicularLine = true;
        
        p = 2 * dy - dx;
        
        while (x < x2){
            
            if (p >= 0){
                
                y = y + 1;
                p = p + 2 * dy - 2 * dx;
            }
            
            else {
                
                p = p + 2 * dy;
            }
            
            x = x + 1;
            
            if ( !(y >= 0 && y < figureData.height && x >= 0 && x < figureData.width) )
                break;
            
            if (lineEnd(figureData.grid[y][x]))
                break;
            
            line.add(figureData.grid[y][x]);
            
            figureData.grid[y][x].perpendicularLine = true;
            //System.out.println("Line point y " + y + ", x " + x);
        }
        
        //figureData.grid[y][x].marked = true;
                
        return line;
    }
    
    private boolean lineEnd(Point p){
        
        if (p.bottom || p.color == ImageScanner.objContourColor)
            return true;
        
        return false;
    }
    
    
    private boolean lineEnd2(Point p){
        
        int y = (int)p.y;
        int x = (int)p.x;
        
        if (p.bottom || p.color == ImageScanner.objContourColor)
            return true;
        
        if (figureData.grid[y][x].color == ImageScanner.objEmptyColor)
            return true;
        
        return false;
    }
    
    private LinkedList<Point> generateLine(Point p){

        LinkedList<Point> line = new LinkedList<>();
        
        line.add(p);
        
        p.tempMarked = true;
        int color = p.color;
        
        while (returnUnivistedNeighbour(p, color) != null){
            
            p = returnUnivistedNeighbour(p, color);
            
            line.add(p);
        
            p.tempMarked = true;
        }
        
        restoreLinePointStates();

        return line;
    }
    
    private Point returnNextLinePoint(Point currentPoint, int indexFromCurrent){
        
        currentPoint.tempMarked = true;
        Point nextPoint = currentPoint;

        for (int i = 0; i < indexFromCurrent; ++i){
            
            if (figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x].linePoint){
                
                nextPoint = figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x];
            }

            if (figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x + 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x + 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x + 1];
            }

            if (figureData.grid[(int)nextPoint.y][(int)nextPoint.x + 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y][(int)nextPoint.x + 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y][(int)nextPoint.x + 1];
            }
            
            if (figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x + 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x + 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x + 1];
            }
            
            if (figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x];
            }
            
            if (figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x - 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x - 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y - 1][(int)nextPoint.x - 1];
            }
            
            if (figureData.grid[(int)nextPoint.y][(int)nextPoint.x - 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y][(int)nextPoint.x - 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y][(int)nextPoint.x - 1];
            }
            
            if (figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x - 1].color == ImageScanner.objHillTopColor && !figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x - 1].linePoint){

                nextPoint = figureData.grid[(int)nextPoint.y + 1][(int)nextPoint.x - 1];
            }
            
            nextPoint.tempMarked = true;
        }
        
        return nextPoint;
    }
    
    private LinkedList<Point> findTopHillStartingPoints(){
        
        LinkedList<Point> startPoints = new LinkedList<>();
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                int color = figureData.grid[y][x].color;
                
                if (!ImageScanner.hillTopColor(color))
                    continue;
                
                //System.out.println("hill top color " + String.format("0x%08X", color));

                if (listContainsColor(startPoints, color))
                    continue;
                
                if (lineIsCircle(figureData.grid[y][x])){
                    
                    System.out.println("y " + y + ", x " + x);
                    System.out.println("is circle");
                    startPoints.add(figureData.grid[y][x]);
                }
                
                else{
                    
                    System.out.println("y " + y + ", x " + x);
                    System.out.println("is not circle");
                    startPoints.add(returnStartPoint(figureData.grid[y][x]));
                    
                }
            }
        }
        
        return startPoints;
    }
    
    private Point returnStartPoint(Point p){
        
        p.tempMarked = true;
        int color = p.color;
        
        while (returnUnivistedNeighbour(p, color) != null){
            
            p = returnUnivistedNeighbour(p, color);
            
            p.tempMarked = true;
        }
        
        restoreLinePointStates();
        
        if (pointTouchesContour(p))
            return p;
        
        return null;
    }
    
    private boolean lineIsCircle(Point p){
        
        p.tempMarked = true;
        int color = p.color;
        
        while (returnUnivistedNeighbour(p, color) != null){
            
            p = returnUnivistedNeighbour(p, color);
            //System.out.println("returnUnivistedNeighbour " + "y " + p.y + ", x " + p.x + ", color " + String.format("0x%08X", p.color));
            p.tempMarked = true;
        }
        
        restoreLinePointStates();
        
        if (pointTouchesContour(p)){
             System.out.println("pointTouchesContour true y " + p.y + ", x " + p.x);
            return false;

        }
        System.out.println("pointTouchesContour false y " + p.y + ", x " + p.x);
        return true;
    }
    
    private void restoreLinePointStates(){
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                //if (figureData.grid[y][x].tempMarked)
                figureData.grid[y][x].tempMarked = false;
            }
        }
    }
    
    private boolean pointTouchesContour(Point p){
       
        int y = (int)p.y;
        int x = (int)p.x;
        
        if (y + 1 < figureData.height)
            if (figureData.grid[y + 1][x].color == ImageScanner.objContourColor)
                return true;
        
        if (y - 1 >= 0)
            if (figureData.grid[y - 1][x].color == ImageScanner.objContourColor)
                return true;
        
        if (x + 1 < figureData.width)
            if (figureData.grid[y][x + 1].color == ImageScanner.objContourColor)
                return true;
        
        if (x - 1 >= 0)
            if (figureData.grid[y][x - 1].color == ImageScanner.objContourColor)
                return true;
        
        if (y + 1 < figureData.height && x + 1 < figureData.width)
            if (figureData.grid[y + 1][x + 1].color == ImageScanner.objContourColor)
                return true;
        
        if (y + 1 < figureData.height && x - 1 >= 0)
            if (figureData.grid[y + 1][x - 1].color == ImageScanner.objContourColor)
                return true;
            
        if (y - 1 >= 0 && x + 1 < figureData.width)
            if (figureData.grid[y - 1][x + 1].color == ImageScanner.objContourColor)
                return true;
        
        if (y - 1 >= 0 && x - 1 >= 0)
            if (figureData.grid[y - 1][x - 1].color == ImageScanner.objContourColor)
                return true;
        
        return false;
    }
    
    private Point returnUnivistedNeighbour(Point p, int color){

        int y = (int)p.y;
        int x = (int)p.x;
        
        Point neighbour = null;
        if (y + 1 < figureData.height){
            
            neighbour = figureData.grid[y + 1][x];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (y - 1 >= 0){
            
            neighbour = figureData.grid[y - 1][x];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if ( x + 1 < figureData.width){
            
            neighbour = figureData.grid[y][x + 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (x - 1 >= 0){
            
            neighbour = figureData.grid[y][x - 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (y + 1 < figureData.height && x + 1 < figureData.width){
            
            neighbour = figureData.grid[y + 1][x + 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (y + 1 < figureData.height && x - 1 >= 0){
            
            neighbour = figureData.grid[y + 1][x - 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (y - 1 >= 0 && x + 1 < figureData.width){
            
            neighbour = figureData.grid[y - 1][x + 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        if (y - 1 >= 0 && x - 1 >= 0){
            
            neighbour = figureData.grid[y - 1][x - 1];
            if ((neighbour.commonTop || neighbour.top) && !neighbour.tempMarked && (neighbour.color == color || neighbour.color == ImageScanner.objHillTopColor))
                return neighbour;
        }
        
        return null;
    }
    
    private boolean listContainsColor(LinkedList<Point> list, int color){
 
        Iterator<Point> it = list.iterator();

        while (it.hasNext()){
 
            Point point = it.next();

            if (point == null)
                System.out.println("point == null"); 
            if (point.color == color){
                        
               return true;
            }
        }

        return false;
    }
    
    private boolean neighbourColor(int y, int x, int color){
        
        if ( y + 1 < figureData.height){
            
            if (figureData.grid[y + 1][x].color == color){
                
                return true;
            }
        }
        
        if ( y - 1 >= 0 ){
            
            if ( figureData.grid[y-1][x].color == color){
                
                return true;
            }
        }
        
        if ( x + 1 < figureData.width){
        
            if (figureData.grid[y][x+1].color == color){
                
                return true;
            }
        }
        
        if (x - 1 >= 0){
            
            if (figureData.grid[y][x-1].color == color){
                
                return true;
            }
        }
        
        return false;
    }
    
    private boolean topHillColor(Point p){
        
        if (p.color != ImageScanner.objContourColor && p.color != ImageScanner.objEmptyColor
            && p.color != ImageScanner.objInnerColor && p.color != ImageScanner.objHillBotColor)
            return true;
        
        return false;
    }
    
    public void getFigurePixels(){
        
        figureData.figureAreaPixels2 = new LinkedList<>();
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (figureData.grid[y][x].color != ImageScanner.objEmptyColor)
                    figureData.figureAreaPixels2.add(figureData.grid[y][x]);
            }
        }
        
        figureData.temp = new LinkedList<>();
        
        Iterator<LinkedList<Point>> vectorsIt = inflationVectors.iterator();
        //System.out.println("vectors num " + inflationVectors.size());
        while (vectorsIt.hasNext()){
            
            LinkedList<Point> points = vectorsIt.next();
            Iterator<Point> pointsIt = points.iterator();
            //System.out.println("vector size " + points.size());
            while (pointsIt.hasNext()){
                
                Point point = pointsIt.next();
                
                figureData.temp.add(point);
            }
        }
    }
    
    public void getFigurePixels3(){
        /*
        figureData.figureAreaPixels2 = new LinkedList<>();
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (figureData.grid[y][x].color != ImageScanner.objEmptyColor)
                    figureData.figureAreaPixels2.add(figureData.grid[y][x]);
            }
        }
        */
        figureData.figureAreaPixels2 = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segmentsIt = segmentsLines.iterator();
        //System.out.println("vectors num " + inflationVectors.size());
        while (segmentsIt.hasNext()){
            
            LinkedList<Segment> segs = segmentsIt.next();
            Iterator<Segment> segsIt = segs.iterator();
            //System.out.println("vector size " + points.size());
            while (segsIt.hasNext()){
                
                Segment s = segsIt.next();
                
                figureData.figureAreaPixels2.add(s.middPoint);
                
                if (s.negPoint!= null)
                    figureData.figureAreaPixels2.add(s.negPoint);
                if (s.posPoint!= null)
                    figureData.figureAreaPixels2.add(s.posPoint);
            }
        }
    }
    
    public void getFigureTriangles(){
        
        Iterator<Triangle> trianglesIt = figureData.inflatedTriangles.iterator();
        
        while (trianglesIt.hasNext() ){
            
            Triangle triangle = trianglesIt.next();
            
            Point p1 = triangle.p1;
            Point p2 = triangle.p2;
            Point p3 = triangle.p3;
            
            p1.x -= figureData.width / 2;
            p1.y -= figureData.height / 2;
            
            p2.x -= figureData.width / 2;
            p2.y -= figureData.height / 2;
            
            p3.x -= figureData.width / 2;
            p3.y -= figureData.height / 2;
        }
    }
    

    public void InflatePixelsList(float dept){
    
        LinkedList<LinkedList<LinkedList<Point>>> inflatedPixels = new LinkedList<>();
        
        Iterator<LinkedList<LinkedList<Point>>> rowsIt = figureData.figureAreaPixels.iterator();
        
        while ( rowsIt.hasNext() ){
        
            LinkedList<LinkedList<Point>> inflatedRows = new LinkedList<>();

            //LinkedList<LinkedList<Point>> rows = rowsIt.next();
            Iterator<LinkedList<Point>> rowLinesIt = rowsIt.next().iterator();
            
            while ( rowLinesIt.hasNext() ){
                    
                    LinkedList<Point> rowLines = rowLinesIt.next();
                    Iterator<Point> linePixelsIt = rowLines.iterator();
                    
                    //if (pointsRowList.size() < 3) // no inflation for contour points
                    //continue;
            
                    Point firstPoint = rowLines.getFirst();
                    Point lastPoint = rowLines.getLast();

                    float rowLength = lastPoint.x - firstPoint.x;

                    float inflationDept = (rowLength * dept) / figureData.width;
                    float scale = (float)Math.PI / rowLength;

                    LinkedList<Point> inflatedRow = new LinkedList<>();
                    
                    while (linePixelsIt.hasNext()){
                        
                        Point point = linePixelsIt.next();
                
                        point.z = (float)(Math.round((Math.sin( (point.x - firstPoint.x) * scale ) * inflationDept) * 100000d) / 100000d);
                        //float inflatedZ = (float)(Math.round((Math.sin( (point.x - firstPoint.x) * scale ) * inflationDept) * 100000d) / 100000d);
                        //System.out.println("z " + inflatedZ);
                        //inflatedRow.add(new Point(point.y, point.x, inflatedZ));
                        inflatedRow.add(point);
                    }
                    
                    inflatedRows.add(inflatedRow);
            }
            
            inflatedPixels.add(inflatedRows);
        }
        
        figureData.inflatedFigureAreaPixels = inflatedPixels;
    }
   
    /*
    public void triangulateFigure(int dimension){
        
        LinkedList<Triangle> triangles = new LinkedList<>();
 
        for (int y = figureData.height-1; y > 0; --y){
            
            boolean triangulated = false;
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (y - dimension < 0 || x + dimension >= figureData.width)
                        continue;
                
                Point pixel = figureData.grid[y][x];
                
                if (pixel.color == ImageScanner.objInnerColor){
                    
                    Triangle tTopRight = Triangle.triangulateTopRight(y, x, dimension, figureData.grid, ImageScanner.objInnerColor);
                    Triangle tTopLeft = Triangle.triangulateTopLeft(y, x, dimension, figureData.grid, ImageScanner.objInnerColor);
                    Triangle tBotRight = Triangle.triangulateBotRight(y, x, dimension, figureData.grid, ImageScanner.objInnerColor);
                    Triangle tBotLeft = Triangle.triangulateBotLeft(y, x, dimension, figureData.grid, ImageScanner.objInnerColor);
                    
                    if (tTopRight != null || tBotLeft != null) {

                        if (tBotLeft != null) {
                            triangles.add(tBotLeft);
                            //triangles.add(tBotLeft.getInvertedTriangle());
                        }

                        if (tTopRight != null) {
                            triangles.add(tTopRight);
                            //triangles.add(tTopRight.getInvertedTriangle());
                        }
                        
                        x += dimension - 1;
                        triangulated = true;
                    } else if (tTopLeft != null || tBotRight != null) {

                        if (tTopLeft != null) {
                            triangles.add(tTopLeft);
                            //triangles.add(tTopLeft.getInvertedTriangle());
                        }

                        if (tBotRight != null) {
                            triangles.add(tBotRight);
                            //triangles.add(tBotRight.getInvertedTriangle());
                        }
                        
                        triangulated = true;
                        x += dimension - 1;
                    } 
                }
            }
            
            if (triangulated)
                y -= dimension - 1;
        }
        
        figureData.inflatedTriangles = triangles;
    }
    */
    public void triangulateFigureOld(){
        
        LinkedList<Triangle> triangles = new LinkedList<>();
        
        for (int y = figureData.height - 1; y > 0; --y){
         
            for (int x = 0; x < figureData.width; ++x){
                
                boolean bot = false;
                boolean right = false;
                boolean botRight = false;
                
                if (figureData.grid[y][x].color != ImageScanner.objEmptyColor){
                    
                    if (y - 1 > 0){
                        
                        if (figureData.grid[y-1][x].color != ImageScanner.objEmptyColor)
                            bot = true;
                    }
                    
                    if (x + 1 < figureData.width){
                        
                        if (figureData.grid[y][x+1].color != ImageScanner.objEmptyColor)
                            right = true;
                    }
                    
                    if (y - 1 > 0 && x + 1 < figureData.width){
                        
                        if (figureData.grid[y-1][x+1].color != ImageScanner.objEmptyColor)
                            botRight = true;
                    }
                    
                    if (bot && botRight){
                        
                        Point p1 = new Point(figureData.grid[y][x].y, figureData.grid[y][x].x, figureData.grid[y][x].z);
                        Point p2 = new Point(figureData.grid[y-1][x].y, figureData.grid[y-1][x].x, figureData.grid[y-1][x].z);
                        Point p4 = new Point(figureData.grid[y-1][x+1].y, figureData.grid[y-1][x+1].x, figureData.grid[y-1][x+1].z);
                        
                        //Point p1 = new Point(y, x, figureData.grid[y][x].z);
                        //Point p2 = new Point(y - 1, x, figureData.grid[y-1][x].z);
                        //Point p4 = new Point(y - 1, x + 1, figureData.grid[y-1][x+1].z);
                        Triangle triangle = new Triangle(p1, p2, p4);
                        triangles.add(triangle);
                        //triangles.add(triangle.getInvertedTriangle());
                    }
                    
                    if (right && botRight){
                        
                        Point p1 = new Point(figureData.grid[y][x].y, figureData.grid[y][x].x, figureData.grid[y][x].z);
                        Point p3 = new Point(figureData.grid[y][x+1].y, figureData.grid[y][x+1].x, figureData.grid[y][x+1].z);
                        Point p4 = new Point(figureData.grid[y-1][x+1].y, figureData.grid[y-1][x+1].x, figureData.grid[y-1][x+1].z);
                    
                        //Point p1 = new Point(y, x, figureData.grid[y][x].z);
                        //Point p3 = new Point(y, x + 1, figureData.grid[y][x+1].z);
                        //Point p4 = new Point(y - 1, x + 1, figureData.grid[y-1][x+1].z);
                        Triangle triangle = new Triangle(p1, p3, p4);
                        triangles.add(triangle);
                        //triangles.add(triangle.getInvertedTriangle());
                    }
                }
            }
        }
        
        figureData.inflatedTriangles = triangles;
    }
    
    private float distance(float y1, float x1, float y2, float x2){
        
        float dy = y1 - y2;
        float dx = x1 - x2;
        
        return (float)Math.sqrt(dx * dx + dy * dy);
    }
    
    private void updateTopHillColor(){
        
        // add additional hill bot pixels
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
            
                if (figureData.grid[y][x].color ==  ImageScanner.objHillTopColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= figureData.height || x - 1 < 0 || x + 1 >= figureData.width)
                    continue;
                
                if (ImageScanner.hillTopColor(figureData.grid[y+1][x].color) && ImageScanner.hillTopColor(figureData.grid[y][x+1].color) && !ImageScanner.hillTopColor(figureData.grid[y+1][x+1].color)){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].top = true;
                }
                
                else if (ImageScanner.hillTopColor(figureData.grid[y+1][x].color) && ImageScanner.hillTopColor(figureData.grid[y][x-1].color) && !ImageScanner.hillTopColor(figureData.grid[y+1][x-1].color)){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].top = true;
                }
                
                else if (ImageScanner.hillTopColor(figureData.grid[y-1][x].color) && ImageScanner.hillTopColor(figureData.grid[y][x+1].color) && !ImageScanner.hillTopColor(figureData.grid[y-1][x+1].color)){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].top = true;
                }
                
                else if (ImageScanner.hillTopColor(figureData.grid[y-1][x].color) && ImageScanner.hillTopColor(figureData.grid[y][x-1].color) && !ImageScanner.hillTopColor(figureData.grid[y-1][x-1].color)){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].top = true;
                }
            }
        }
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
            
                if (figureData.grid[y][x].top)
                    figureData.grid[y][x].color = ImageScanner.objHillTopColor;
            }
        }
    }
    
    private void updateContourColor(){
        
        // add additional contour pixels
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
            
                if (figureData.grid[y][x].color ==  ImageScanner.objContourColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= figureData.height || x - 1 < 0 || x + 1 >= figureData.width)
                    continue;
                
                if (figureData.grid[y+1][x].color == ImageScanner.objContourColor && figureData.grid[y][x+1].color == ImageScanner.objContourColor && figureData.grid[y+1][x+1].color != ImageScanner.objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].contour = true;
                }
                
                else if (figureData.grid[y+1][x].color == ImageScanner.objContourColor && figureData.grid[y][x-1].color == ImageScanner.objContourColor && figureData.grid[y+1][x-1].color != ImageScanner.objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].contour = true;
                }
                
                else if (figureData.grid[y-1][x].color == ImageScanner.objContourColor && figureData.grid[y][x+1].color == ImageScanner.objContourColor && figureData.grid[y-1][x+1].color != ImageScanner.objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].contour = true;
                }
                
                else if (figureData.grid[y-1][x].color == ImageScanner.objContourColor && figureData.grid[y][x-1].color == ImageScanner.objContourColor && figureData.grid[y-1][x-1].color != ImageScanner.objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    figureData.grid[y][x].contour = true;
                }
            }
        }
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
            
                if (figureData.grid[y][x].contour)
                    figureData.grid[y][x].color = ImageScanner.objContourColor;
            }
        }
    }
    
    public void exportImage(String Path){
        
        BufferedImage paintedImage;
        
        try {

            File outputfile = new File(Path);
            
            paintedImage = new BufferedImage(figureData.width, figureData.height, TYPE_INT_ARGB);
            for(int y = 0; y < figureData.height; ++y){
                
                for (int x = 0; x < figureData.width; ++x){
                    
                    //paintedImage.setRGB(x, height - 1 - y, grid[y][x].color);
                    paintedImage.setRGB(x, y, figureData.grid[y][x].color);
                }
            }
            
            ImageIO.write(paintedImage, "png", outputfile);
        } catch (Exception e) {

            System.out.println("exportPaintedFigure exception");
        }
    }
}
