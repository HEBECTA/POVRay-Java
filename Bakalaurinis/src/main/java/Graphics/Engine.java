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
public class Engine {
    
    FigureData figureData;
    
    LinkedList<LinkedList<Point>> terrainLines;
    LinkedList<LinkedList<Point>> jointLines;
    
    LinkedList<JointLine> jointLinesData;
    
    float maxLineLen = 0;
    
    int intervalBetweenPoints = 5; //5
    int lineIterationInterval = 17; // 17
    
    int triangulationInterval = 5;
    
    LinkedList<LinkedList<Segment>> segmentsLines;
    LinkedList<LinkedList<Block>> jointBlockLines;
    LinkedList<LinkedList<Block>> terrainBlockLines;
    boolean []terrainIsCircle;
    
    LinkedList<Triangle> triangulatedJointBlocks;
    LinkedList<Triangle> triangulatedTerrainBlocks;
    
    boolean linesIntersect = false;
    
    public void inflateFigure(float dept, int iterationInterval, int pointsDistance, int triangulationDistance){
       
        intervalBetweenPoints = pointsDistance;
        lineIterationInterval = iterationInterval;
        triangulationInterval = triangulationDistance;
        
        generateVectors();
        
        //System.out.println("Longest line " + maxLineLen);
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        int i = 0;
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            //System.out.println("seg size " + segmentsLine.size());
              
            while (segmentsIt.hasNext()){
                
                Segment segment = segmentsIt.next();
                
                segment.middPoint.z = (segment.len * dept) / maxLineLen;
                
                inflateSegment(segment);
            }
            
            if (!terrainIsCircle[i] && segmentsLine.size() > 1){
                
                nullifySegment(segmentsLine.getFirst());
                nullifySegment(segmentsLine.getLast());
                
                //segmentsLine.getFirst().middPoint.z = 0;
                //segmentsLine.getLast().middPoint.z = 0;
            }
            
            ++i;
        }
        //loadData();
        //return;
        //System.out.println("before triangulation");
        
        triangulate2();
        
        loadData();
    }
    
    private void inflateSegment(Segment seg){
        
        if (seg.negPoint != null){
            
            Point[] points = new Point[seg.negativeLine.size()];
            seg.negativeLine.toArray(points);
            
            if (seg.negativeLine.size() > triangulationInterval){
                
                float lineNmbDots = seg.negativeLine.size() / triangulationInterval;
                
                seg.inflatedNegLine = new LinkedList<>();
                
                float scale = (float)(Math.PI / 2) / seg.negativeLine.size();
                
                seg.inflatedNegLine.add(seg.middPoint);
                
                int inc = (int)lineNmbDots;
                for (int i = 0; i < seg.negativeLine.size(); i += inc){
                    
                    if (i == 0){
                        
                        seg.inflatedNegLine.add(points[i]);
                        continue;
                    }
                    
                    points[i].z = (float)(Math.round((Math.cos(i * scale) * seg.middPoint.z) * 100000d) / 100000d);
                    seg.inflatedNegLine.add(points[i]);
                }
                
                Point lastPoint = seg.inflatedNegLine.getLast();
                
                if (lastPoint.z != 0){
                    seg.negPoint.z = 0;
                    seg.inflatedNegLine.add(seg.negPoint);
                }
                    //lastPoint.z = 0;
                /*
                if (lastPoint != seg.negPoint)
                    seg.inflatedNegLine.add(seg.negPoint);
                */
                //if (lineNmbDots % 1 != 0)
                    //seg.inflatedNegLine.add(seg.negPoint);
            }
        }
        
        if (seg.posPoint != null){
            
            Point[] points = new Point[seg.positiveLine.size()];
            seg.positiveLine.toArray(points);
            
            if (seg.positiveLine.size() > triangulationInterval){
                
                float lineNmbDots = seg.positiveLine.size() / triangulationInterval;
                
                seg.inflatedPosLine = new LinkedList<>();
                
                float scale = (float)(Math.PI / 2) / seg.positiveLine.size();
                
                seg.inflatedPosLine.add(seg.middPoint);
                
                int inc = (int)lineNmbDots;
                for (int i = 0; i < seg.positiveLine.size(); i += inc){
                    
                    if (i == 0){
                        
                        seg.inflatedPosLine.add(points[i]);
                        continue;
                    }
                    
                    points[i].z = (float)(Math.round((Math.cos(i * scale) * seg.middPoint.z) * 100000d) / 100000d);
                    seg.inflatedPosLine.add(points[i]);
                }
                
                Point lastPoint = seg.inflatedPosLine.getLast();
                
                if (lastPoint.z != 0){
                    seg.posPoint.z = 0;
                    seg.inflatedPosLine.add(seg.posPoint);
                }
                    //lastPoint.z = 0;
                /*
                if (lastPoint != seg.posPoint)
                    seg.inflatedPosLine.add(seg.posPoint);
                */
                //if (lineNmbDots % 1 != 0)
                    //seg.inflatedPosLine.add(seg.posPoint);
            }
        }
    }
    
    private void nullifySegment(Segment seg){
        
        if (seg.inflatedNegLine != null){
            
            Iterator<Point> pointsIt = seg.inflatedNegLine.iterator();
            
            while(pointsIt.hasNext()){
                
                Point p = pointsIt.next();
                
                p.z = 0;
            }
        }
        
        if (seg.inflatedPosLine != null){
            
            Iterator<Point> pointsIt = seg.inflatedPosLine.iterator();
            
            while(pointsIt.hasNext()){
                
                Point p = pointsIt.next();
                
                p.z = 0;
            }
        }
        
        seg.middPoint.z = 0;
    }
    
    private void triangulate2(){
        
        figureData.inflatedTriangles = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        int i = 0;
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            //System.out.println("triangulate seg size " + segmentsLine.size());

            Segment segmentPrev;
            
            if (!segmentsIt.hasNext())
                continue;
            
            segmentPrev = segmentsIt.next();
            Segment segment = null;
            
            //System.out.println("pirmas seg " + segmentsIt.hasNext());
            
            while(segmentsIt.hasNext()) {
                
                segment = segmentsIt.next();

                triangulateSegs(segmentPrev, segment);
                
                segmentPrev = segment;
            } 
            
            if (terrainIsCircle[i] && segmentsLine.size() > 2){

                Segment first = segmentsLine.getFirst();
                Segment last = segmentsLine.getLast();
                
                triangulateSegs(first, last);
            }
            
            ++i;
        }
    }
    
    private void triangulateSegs(Segment s1, Segment s2){
        
        if (s1.negPoint != null && s2.negPoint != null){
            
            if (s1.inflatedNegLine != null && s2.inflatedNegLine != null){
               
                triangulateLines(s1.inflatedNegLine, s2.inflatedNegLine);
            }
            
            else if (s1.inflatedNegLine != null){
                
                triangulateLinePoint(s1.inflatedNegLine, s2, false);
            }
            
            else if (s2.inflatedNegLine != null){
                
                triangulateLinePoint(s2.inflatedNegLine, s1, false);
            }
            
            else {
                
                triangulatePoints(s1, s2, false);
            }
        } 
        
        if (s1.posPoint != null && s2.posPoint != null){
            
            if (s1.inflatedPosLine != null && s2.inflatedPosLine != null){
               
                triangulateLines(s1.inflatedPosLine, s2.inflatedPosLine);
            }   
            
            else if (s1.inflatedPosLine != null){
                
                triangulateLinePoint(s1.inflatedPosLine, s2, true);
            }
            
            else if (s2.inflatedPosLine != null){
                
                triangulateLinePoint(s2.inflatedPosLine, s1, true);
            }
            
            else{
                
                triangulatePoints(s1, s2, true);
            }
        }
    }
    
    private void triangulateLines(LinkedList<Point> line1, LinkedList<Point> line2){
        
        //System.out.println("line1 size " + line1.size() + ", line2 size " + line2.size());
        
        LinkedList<Point> shorterLine = line1;
        LinkedList<Point> longerLine = line2;

        if (line1.size() > line2.size()) {

            shorterLine = line2;
            longerLine = line1;
        }

        Iterator<Point> shortIt = shorterLine.iterator();
        Iterator<Point> longerIt = longerLine.iterator();

        int trianglesPerVertex = longerLine.size() / shorterLine.size();
        
        Point prevLongPoint = null;
        Point longPoint = null;

        Point prevShortPoint = null;
        Point shortPoint = null;
        /*
        if (longerIt.hasNext())
            prevLongPoint = longerIt.next();
        
        if (shortIt.hasNext())
            prevShortPoint = shortIt.next();
*/
        int triangulationCount = 0;
        while (shortIt.hasNext()) {

            if (prevShortPoint == null) {
                prevShortPoint = shortIt.next();
                continue;
            }

            outerLoop:
            while (longerIt.hasNext()) {

                if (trianglesPerVertex <= triangulationCount) {

                    triangulationCount = 0;
                    break outerLoop;
                }

                longPoint = longerIt.next();

                if (prevLongPoint == null) {

                    prevLongPoint = longPoint;
                    continue;
                }

                Triangle triangle = new Triangle(shortPoint, prevLongPoint, longPoint);
                figureData.inflatedTriangles.add(triangle);

                prevLongPoint = longPoint;
                ++triangulationCount;
            }

            if (shortIt.hasNext()) {

                shortPoint = shortIt.next();

                Triangle triangle = new Triangle(prevShortPoint, shortPoint, prevLongPoint);
                figureData.inflatedTriangles.add(triangle);

                prevShortPoint = shortPoint;
            }
            /*
            else {
                
                Triangle triangle = new Triangle(shortPoint, prevLongPoint, longPoint);
                figureData.inflatedTriangles.add(triangle);
            }*/
        }
    }
    
    private void triangulateLinePoint(LinkedList<Point> line, Segment seg, boolean pos){
        
        //System.out.println("line size " + line.size() + " seg");
        
        Iterator<Point> pointsIt = line.iterator();
        
        int trianglePerVertex = line.size() / 2;
        
        Point secondPoint = seg.negPoint;

        if (pos)
            secondPoint = seg.posPoint;
        
        Point prevPoint = null;
        Point currPoint = null;
        
        int trianglesCount = 0;
        while (pointsIt.hasNext()){
            
            if (trianglesCount >= trianglePerVertex){
                
                trianglesCount = 0;
                break;
            }
            
            currPoint = pointsIt.next();
            
            if (prevPoint == null){
                
                prevPoint = currPoint;
                continue;
            }
            
            Triangle triangle = new Triangle(seg.middPoint, prevPoint, currPoint);
            ++trianglesCount;
            figureData.inflatedTriangles.add(triangle);
        }
        
        figureData.inflatedTriangles.add(new Triangle(seg.middPoint, secondPoint, prevPoint));
        
        while (pointsIt.hasNext()){
            
            currPoint = pointsIt.next();
            
            Triangle triangle = new Triangle(secondPoint, prevPoint, currPoint);
            figureData.inflatedTriangles.add(triangle);
        }
    }
    
    private void triangulatePoints(Segment seg1, Segment seg2, boolean pos){
        
        //System.out.println("seg1  seg2");
        
        if (pos){
            
            figureData.inflatedTriangles.add(new Triangle(seg1.posPoint, seg2.posPoint, seg1.middPoint));
            figureData.inflatedTriangles.add(new Triangle(seg1.middPoint, seg2.middPoint, seg2.posPoint));
        }
        
        else {
            
            figureData.inflatedTriangles.add(new Triangle(seg1.negPoint, seg2.negPoint, seg1.middPoint));
            figureData.inflatedTriangles.add(new Triangle(seg1.middPoint, seg2.middPoint, seg2.negPoint));
        }
    }
    /*
    private void triangulate(){
        
        figureData.inflatedTriangles = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        int i = 0;
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            //System.out.println("triangulate seg size " + segmentsLine.size());

            Segment segmentPrev;
            
            if (!segmentsIt.hasNext())
                continue;
            
            segmentPrev = segmentsIt.next();
            Segment segment = null;
            
            //System.out.println("pirmas seg " + segmentsIt.hasNext());
            
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
                   // figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                    
                }
                
                if (segment.posPoint != null){
                    
                    triangle = new Triangle(segmentPrev.middPoint, segment.middPoint, segment.posPoint);
                    figureData.inflatedTriangles.add(triangle);
                    //figureData.inflatedTriangles.add(triangle.getInvertedTriangle());
                }
                
                segmentPrev = segment;
            } 
            
            if (terrainIsCircle[i] && segmentsLine.size() > 2){

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
            }
            
            ++i;
        }
    }
    */
    private void generateVectors(){
        
        LinkedList<Point> terrainStartPoints = findTerrainStartingPoints();
        terrainLines = new LinkedList<>();
        
        terrainIsCircle = new boolean[terrainStartPoints.size()];
        
        Iterator<Point> it = terrainStartPoints.iterator();
        
        int k = 0;
        while (it.hasNext()){
            
            Point startPoint = it.next();
            
            LinkedList<Point> line = generateLine(startPoint);
            
            terrainLines.add(line);
            
            if (lineIsCircle(line.getFirst()))
                terrainIsCircle[k] = true;
            
            else
                terrainIsCircle[k] = false;
            
            ++k;
        }
        
        Iterator<LinkedList<Point>> linesIt = terrainLines.iterator();
        
        segmentsLines = new LinkedList<>();
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
            
            LinkedList<Segment> segments = new LinkedList<>();
            
            for (int i = 0; i < points.length - intervalBetweenPoints; i += lineIterationInterval){    
                
                Point p1 = points[i];
                Point p2 = points[i + intervalBetweenPoints];
                Segment seg = generateSegment(p1, p2);
                if (seg != null)
                    segments.add(seg);
            }
            
            segmentsLines.add(segments);
        }
        
    }
    
    /*
    private void referenceToJointBlock(LinkedList<Block> blockLine, Point p1, Point p2){
        
        Iterator<JointLine> dataIt = jointLinesData.iterator();
        
        while (dataIt.hasNext()){
            
            JointLine data = dataIt.next();
            
            if (!data.line.contains(p1) && !data.line.contains(p2))
                continue;
            
            Iterator<Block> blocksIt = data.blocks.iterator();
            
            while (blocksIt.hasNext()){
                
                Block block = blocksIt.next();
                
                blockLine.add(block);
            }
            
            break;
        }
    }*/

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

        if (segment.len > maxLineLen)
            maxLineLen = segment.len;
        
        return segment;
    }
    
    
    private LinkedList<Point> findTerrainStartingPoints(){
        
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
                    
                    //System.out.println("y " + y + ", x " + x);
                    //System.out.println("is circle");
                    figureData.grid[y][x].hillStartingPoint = true;
                    startPoints.add(figureData.grid[y][x]);
                }
                
                else{
                    
                    //System.out.println("y " + y + ", x " + x);
                    //System.out.println("is not circle");
                    Point p = returnStartPoint(figureData.grid[y][x]);
                    //if (p == null)
                        //System.out.println("Point is null !!! findTerrainStartingPoints");
                    p.hillStartingPoint = true;
                    startPoints.add(returnStartPoint(figureData.grid[y][x]));
                }
            }
        }
        
        return startPoints;
    }
    /*
    private LinkedList<LinkedList<Point>> returnJointTerrainLines(LinkedList<LinkedList<Point>> terrainLines){
        
        LinkedList<LinkedList<Point>> jointTerrainLines = new LinkedList<>();
        LinkedList<Point> jointTerrainLine = new LinkedList<>();
        
        Iterator<LinkedList<Point>> linesIt = terrainLines.iterator();
        
        boolean foundJointLine = false;
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> line = linesIt.next();
            Iterator<Point> pointsIt = line.iterator();
            
            while (pointsIt.hasNext()){
                
                Point p = pointsIt.next();
                
                //System.out.println("color " + String.format("0x%08X", p.color));
                
                if (!p.commonTop){
                    
                    if (foundJointLine){
                        
                        jointTerrainLines.add(jointTerrainLine);
                        jointTerrainLine = new LinkedList<>();
                        foundJointLine = false;
                    }
                    
                    continue;
                }
                
                //System.out.println("found joint");
                if (!p.tempMarked){
                    
                    foundJointLine = true;
                    p.tempMarked = true;
                    jointTerrainLine.add(p);
                }

            }
            
            if (foundJointLine){
                
                jointTerrainLines.add(jointTerrainLine);
                jointTerrainLine = new LinkedList<>();
                foundJointLine = false;
            }
        }
        
        restoreLinePointStates();
        
        return jointTerrainLines;
    }
  */
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
    
    private boolean listContainsColor(LinkedList<Point> list, int color){
 
        Iterator<Point> it = list.iterator();

        while (it.hasNext()){
 
            Point point = it.next();

            //if (point == null)
                //System.out.println("point == null"); 
            if (point.color == color){
                        
               return true;
            }
        }

        return false;
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
             //System.out.println("pointTouchesContour true y " + p.y + ", x " + p.x);
            return false;

        }
        //System.out.println("pointTouchesContour false y " + p.y + ", x " + p.x);
        return true;
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
    
    private void restoreLinePointStates(){
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                //if (figureData.grid[y][x].tempMarked)
                figureData.grid[y][x].tempMarked = false;
            }
        }
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
        //float lineLength2 = 15;
        
        if (opposite)
            lineLength *= -1;
        
        //if (opposite)
            //lineLength2 *= -1;
        
        int perpendicularPointX = (int)Math.round(midX + normalizedX * lineLength);
        int perpendicularPointY = (int)Math.round(midY + normalizedY * lineLength);
        
        //int perpendicularPointX2 = (int)Math.round(midX + normalizedX * lineLength2);
        //int perpendicularPointY2 = (int)Math.round(midY + normalizedY * lineLength2);
        
        //System.out.println("Mid point y " + midY + ", x " + midX);
        //System.out.println("Second point y " + perpendicularPointY + ", x " + perpendicularPointX);
        
        figureData.grid[(int)Math.round(midY)][(int)Math.round(midX)].midPoint = true;
        //figureData.grid[perpendicularPointY2][perpendicularPointX2].marked4 = true;
        p1.linePoint = true;
        p2.linePoint = true;
        return bresenhamAlgorithm(midY, midX, perpendicularPointY, perpendicularPointX);
    }
    
    private LinkedList<Point> bresenhamAlgorithm(float y1, float x1, float y2, float x2){
        
        LinkedList<Point> line = new LinkedList<>();

        int dx, dy, err, sx, sy;
        
        dx = (int)Math.round(Math.abs(x2 - x1));
        dy = (int)Math.round(Math.abs(y2 - y1));
        
        sx = x1 < x2 ? 1 : -1;
        sy = y1 < y2 ? 1 : -1;
        
        err = dx - dy;
        
        int xx = (int)Math.round(x1);
        int yy = (int)Math.round(y1);
        
        line.add(figureData.grid[yy][xx]);
        
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
    
    private boolean lineEnd(Point p){
        
        if (p.bottom || p.color == ImageScanner.objContourColor)
            return true;
        
        return false;
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
    
    private float distance(float y1, float x1, float y2, float x2){
        
        float dy = y1 - y2;
        float dx = x1 - x2;
        
        return (float)Math.sqrt(dx * dx + dy * dy);
    }
    
    public void setFigureData(FigureData data){
        
        figureData = data;
    }
    
    private void loadData(){
        
        figureData.figureAreaPixels2 = new LinkedList<>();
        
        for (int y = 0; y < figureData.height; ++y){
            
            for (int x = 0; x < figureData.width; ++x){
                
                if (figureData.grid[y][x].color != ImageScanner.objEmptyColor)
                    figureData.figureAreaPixels2.add(figureData.grid[y][x]);
            }
        }
        
        figureData.inflatedFigureAreaPixels2 = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segmentsIt = segmentsLines.iterator();
        //System.out.println("vectors num " + inflationVectors.size());
        while (segmentsIt.hasNext()){
            
            LinkedList<Segment> segs = segmentsIt.next();
            Iterator<Segment> segsIt = segs.iterator();
            //System.out.println("vector size " + points.size());
            while (segsIt.hasNext()){
                
                Segment s = segsIt.next();
                
                //figureData.inflatedFigureAreaPixels2.add(s.middPoint);
                
                if (s.inflatedNegLine != null)
                    figureData.inflatedFigureAreaPixels2.addAll(s.inflatedNegLine);
                if (s.inflatedPosLine!= null)
                    figureData.inflatedFigureAreaPixels2.addAll(s.inflatedPosLine);
            }
        }
        
        
        
        /*
        figureData.temp = new LinkedList<>();
        
        Iterator<LinkedList<Segment>> segmentsIt = segmentsLines.iterator();
        //System.out.println("vectors num " + inflationVectors.size());
        while (segmentsIt.hasNext()){
            
            LinkedList<Segment> segs = segmentsIt.next();
            Iterator<Segment> segsIt = segs.iterator();
            //System.out.println("vector size " + points.size());
            while (segsIt.hasNext()){
                
                Segment s = segsIt.next();
                
                figureData.temp.add(s.middPoint);
                
                if (s.inflatedNegLine != null)
                    figureData.temp.addAll(s.inflatedNegLine);
                if (s.inflatedPosLine!= null)
                    figureData.temp.addAll(s.inflatedPosLine);
            }
        }
        */
        
        /*
        figureData.temp = new LinkedList<>();
        
        System.out.println("jointLines size " + jointLines.size());
        
        Iterator<LinkedList<Point>> linesIt = jointLines.iterator();
        //System.out.println("vectors num " + inflationVectors.size());
        while (linesIt.hasNext()){
            
            LinkedList<Point> lines = linesIt.next();
            Iterator<Point> lineIt = lines.iterator();
            //System.out.println("vector size " + points.size());
            
            System.out.println("jointLine size " + lines.size());
            while (lineIt.hasNext()){
                
                Point p = lineIt.next();
                
                figureData.temp.add(p);
            }
        }*/
    }
}
