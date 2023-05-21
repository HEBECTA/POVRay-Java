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
public class Engine2 {
    
    FigureData figureData;
    
    LinkedList<LinkedList<Point>> terrainLines;
    LinkedList<LinkedList<Point>> jointLines;
    
    LinkedList<JointLine> jointLinesData;
    
    float maxLineLen = 0;
    
    int intervalBetweenPoints = 5;
    int lineIterationInterval = 17;
    
    LinkedList<LinkedList<Segment>> segmentsLines;
    LinkedList<LinkedList<Block>> jointBlockLines;
    LinkedList<LinkedList<Block>> terrainBlockLines;
    boolean []terrainIsCircle;
    
    LinkedList<Triangle> triangulatedJointBlocks;
    LinkedList<Triangle> triangulatedTerrainBlocks;
    
    boolean linesIntersect = false;
    
    public void inflateFigure2(float dept){
       
        generateBlocks();
        
        System.out.println("Longest line " + maxLineLen);
        
        Iterator<LinkedList<Block>> jointBlockLinesIt = jointBlockLines.iterator();
        
        System.out.println("jointBlockLines size " + jointBlockLines.size());
        
        while (jointBlockLinesIt.hasNext()){
            
            LinkedList<Block> blockLine = jointBlockLinesIt.next();
            Iterator<Block> blocksIt = blockLine.iterator();
                
            while (blocksIt.hasNext()){
                
                Block block = blocksIt.next();
                
                block.firstSeg.middPoint.z = (block.firstSeg.len * dept) / maxLineLen;
                block.secondSeg.middPoint.z = (block.secondSeg.len * dept) / maxLineLen;
            }
        }
        
        Iterator<LinkedList<Block>> terrainBlockLinesIt = terrainBlockLines.iterator();
        
        System.out.println("terrainBlockLines size " + terrainBlockLines.size());
        
        int i = 0;
        while (terrainBlockLinesIt.hasNext()){
            
            LinkedList<Block> blockLine = terrainBlockLinesIt.next();
            Iterator<Block> blocksIt = blockLine.iterator();
            
            System.out.println("blockLine size " + blockLine.size());
             
            while (blocksIt.hasNext()){
                
                Block block = blocksIt.next();
                
                if (block.secondSeg == null ){
                    
                    System.out.println("block == null");
                }
                
                block.firstSeg.middPoint.z = (block.firstSeg.len * dept) / maxLineLen;
                block.secondSeg.middPoint.z = (block.secondSeg.len * dept) / maxLineLen;
            }
            /*
            if (!terrainIsCircle[i] && blockLine.size() > 1){
                
                blockLine.getFirst().firstSeg.middPoint.z = 0;
                blockLine.getLast().secondSeg.middPoint.z = 0;
            }*/
            
            ++i;
        }
        
        System.out.println("after" );
        
        triangulateJointBlocks();
        
        triangulateTerrainBlocks();
        
        figureData.inflatedTriangles = new LinkedList<>();
        
        figureData.inflatedTriangles.addAll(triangulatedJointBlocks);
        figureData.inflatedTriangles.addAll(triangulatedTerrainBlocks);
        
        loadData();
    }
    
    private void triangulateJointBlocks(){
        
        triangulatedJointBlocks = new LinkedList<>();
        
        Iterator<LinkedList<Block>> blockLinesIt = jointBlockLines.iterator();
        
        while (blockLinesIt.hasNext()){
            
            LinkedList<Block> blockLine = blockLinesIt.next();
            Iterator<Block> blockIt = blockLine.iterator();

            while(blockIt.hasNext()) {
                
                Block block = blockIt.next();
                
                LinkedList<Triangle> triangles = block.triangulateBlock();
                
                triangulatedJointBlocks.addAll(triangles);
            } 
        }
    }
    
    private void triangulateTerrainBlocks(){
        
        triangulatedTerrainBlocks = new LinkedList<>();
        
        Iterator<LinkedList<Block>> blockLinesIt = terrainBlockLines.iterator();
        
        int i = 0;
        while (blockLinesIt.hasNext()){
            
            LinkedList<Block> blockLine = blockLinesIt.next();
            Iterator<Block> blockIt = blockLine.iterator();
            
            while(blockIt.hasNext()) {
                
                Block block = blockIt.next();
                
                LinkedList<Triangle> triangles = block.triangulateBlock();
                
                triangulatedTerrainBlocks.addAll(triangles);
            }
            
            if (terrainIsCircle[i] && blockLine.size() > 2){

                Block first = blockLine.getFirst();
                Block last = blockLine.getLast();
                
                triangulatedTerrainBlocks.addAll(Block.triangulateBetweenBlocks(first, last));
            }
            
            ++i;
        }
    }

    public void inflateFigure(float dept){
       
        generateBlocks();
        
        System.out.println("Longest line " + maxLineLen);
        
        Iterator<LinkedList<Segment>> segLinesIt = segmentsLines.iterator();
        
        int i = 0;
        while (segLinesIt.hasNext()){
            
            LinkedList<Segment> segmentsLine = segLinesIt.next();
            Iterator<Segment> segmentsIt = segmentsLine.iterator();
            
            System.out.println("seg size " + segmentsLine.size());
              
            while (segmentsIt.hasNext()){
                
                Segment segment = segmentsIt.next();
                
                segment.middPoint.z = (segment.len * dept) / maxLineLen;
            }
            
            if (!terrainIsCircle[i] && segmentsLine.size() > 1){
                
                segmentsLine.getFirst().middPoint.z = 0;
                segmentsLine.getLast().middPoint.z = 0;
            }
            
            ++i;
        }
        
        triangulate();
        
        loadData();
    }
    
    private void triangulate(){
        
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
    
    private void generateBlocks(){
        
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
        
        jointLines = returnJointTerrainLines(terrainLines);
       
        jointBlockLines = generateJointBlockLines();
        
        
        Iterator<JointLine> dataIt = jointLinesData.iterator();
        
        while (dataIt.hasNext()){
            
            JointLine data = dataIt.next();
            
            //System.out.println("blocks size " + data.blocks.size());
            //System.out.println("line size " + data.line.size());
        }
        
        
        terrainBlockLines = generateTerrainBlockLines(); 
        /*
        Iterator<LinkedList<Point>> linesIt = terrainLines.iterator();
        segmentsLines = new LinkedList<>();
        while (linesIt.hasNext()){
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
            LinkedList<Segment> segments = new LinkedList<>();
            for (int i = 0; i < points.length - accuracy; i += accuracy2){    
                Point p1 = points[i];
                Point p2 = points[i + accuracy];
                Segment seg = generateSegment(p1, p2);
                if (seg != null)
                    segments.add(seg);
            }
            segmentsLines.add(segments);
        }
        */
    }
    
    private LinkedList<LinkedList<Block>> generateJointBlockLines(){
        
        LinkedList<LinkedList<Block>> blockLines = new LinkedList<>();
        
        Iterator<LinkedList<Point>> linesIt = jointLines.iterator();
          
        jointLinesData = new LinkedList<>();
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
            
            LinkedList<Block> blockLine = new LinkedList<>(); 
            
            Segment segPrev = null;

            for (int i = 0; i < points.length - intervalBetweenPoints; i += lineIterationInterval){   
                
                Point p1 = points[i];
                Point p2 = points[i + intervalBetweenPoints];
                
                Segment segCurr = generateSegment(p1, p2);

                if (segPrev == null){
                    
                    segPrev = segCurr;
                    continue;
                }
                
                if (segCurr == null)
                    continue;

                Block block = grenerateBlock(segPrev, segCurr);

                if (block != null){
                    
                    block.jointBlock = true;
                    blockLine.add(block);
                }

                segPrev = segCurr;
            }
            
            blockLines.add(blockLine);
            jointLinesData.add(new JointLine(pointsList, blockLine));
        }
        
        return blockLines;
    }
    
    private LinkedList<LinkedList<Block>> generateTerrainBlockLines(){
        
        LinkedList<LinkedList<Block>> blockLines = new LinkedList<>();
        
        Iterator<LinkedList<Point>> linesIt = terrainLines.iterator();
        
        while (linesIt.hasNext()){
            
            LinkedList<Point> pointsList = linesIt.next();
            Point[] points = new Point[pointsList.size()];
            pointsList.toArray(points);
            
            LinkedList<Block> blockLine = new LinkedList<>(); 
            
            Segment segPrev = null;
            
            boolean foundJointLine = false;
            
            Block block = null;
            
            Point prevP1 = null;
            Point prevP2 = null;
            
            for (int i = 0; i < points.length - intervalBetweenPoints; i += lineIterationInterval){   
                
                Point p1 = points[i];
                Point p2 = points[i + intervalBetweenPoints];

                if (p1.commonTop || p2.commonTop){
                    //System.out.println("block lines size " + blockLines.size() + ", i " + i);
                    if (!foundJointLine)
                        addConnectingFrontBlock(blockLine, block, p1, p2);
                        
                    foundJointLine = true;
                    prevP1 = p1;
                    prevP2 = p2;
                    continue;
                }
                    
                Segment segCurr = generateSegment(p1, p2);

                if (segPrev == null){
                    
                    segPrev = segCurr;
                    continue;
                }
                
                if (segCurr == null)
                    continue;

                block = grenerateBlock(segPrev, segCurr);

                if (block != null) {
                    //System.out.println("__ block lines size " + blockLines.size() + ", i " + i);
                    if (foundJointLine)
                        addConnectingBackBlock(blockLine, block, prevP1, prevP2);
                        
                    blockLine.add(block);
                }
               
                segPrev = segCurr;
                prevP1 = p1;
                prevP2 = p2;
                foundJointLine = false;
            }
             blockLines.add(blockLine);
        }
        
        return blockLines;
    }
    
    private void addConnectingFrontBlock(LinkedList<Block> blockLine, Block block, Point p1, Point p2){
        
        Iterator<JointLine> dataIt = jointLinesData.iterator();
        
        while (dataIt.hasNext()){
            
            JointLine data = dataIt.next();
            
            if (!data.line.contains(p1) && !data.line.contains(p2))
                continue;
            
            int index = data.line.indexOf(p1);
            if (index == -1)
                index = data.line.indexOf(p2);

            Block firstBlock;
            
            if ((data.line.size() / 2) > index)
                firstBlock = data.blocks.getFirst();
            
            else 
                firstBlock = data.blocks.getLast();

            blockLine.add(new Block(block.secondSeg, firstBlock.firstSeg));
           
            break;
        }
    }
    
    private void addConnectingBackBlock(LinkedList<Block> blockLine, Block block, Point p1, Point p2){
        
        Iterator<JointLine> dataIt = jointLinesData.iterator();
        
        while (dataIt.hasNext()){
            
            JointLine data = dataIt.next();
            
            if (!data.line.contains(p1) && !data.line.contains(p2))
                continue;
            
            int index = data.line.indexOf(p1);
            if (index == -1)
                index = data.line.indexOf(p2);
            
            Block lastBlock;
            
            if ((data.line.size() / 2) > index)
                lastBlock = data.blocks.getFirst();
               
            else   
                lastBlock = data.blocks.getLast();

            blockLine.add(new Block(lastBlock.secondSeg, block.firstSeg));
            
            break;
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
    
    private Block grenerateBlock(Segment s1, Segment s2){
        
        return new Block(s1, s2);
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
                    
                    System.out.println("y " + y + ", x " + x);
                    System.out.println("is circle");
                    figureData.grid[y][x].hillStartingPoint = true;
                    startPoints.add(figureData.grid[y][x]);
                }
                
                else{
                    
                    System.out.println("y " + y + ", x " + x);
                    System.out.println("is not circle");
                    Point p = returnStartPoint(figureData.grid[y][x]);
                    if (p == null)
                        System.out.println("Point is null !!! findTerrainStartingPoints");
                    p.hillStartingPoint = true;
                    startPoints.add(returnStartPoint(figureData.grid[y][x]));
                }
            }
        }
        
        return startPoints;
    }
    
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

            if (point == null)
                System.out.println("point == null"); 
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
             System.out.println("pointTouchesContour true y " + p.y + ", x " + p.x);
            return false;

        }
        System.out.println("pointTouchesContour false y " + p.y + ", x " + p.x);
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
                
                figureData.figureAreaPixels2.add(s.middPoint);
                
                if (s.negPoint!= null)
                    figureData.figureAreaPixels2.add(s.negPoint);
                if (s.posPoint!= null)
                    figureData.figureAreaPixels2.add(s.posPoint);
            }
        }
        */
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
        }
    }
}
