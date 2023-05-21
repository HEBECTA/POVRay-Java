/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OldMethod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author gugu
 */
public class Scanner {
    
    //FileOperation2 fileHandler;  
    
    LinkedList<Point> points;
    
    //LinkedList<LinkedList<LinkedList<Point>>> linedUpPoints;
    LinkedList<Face> faces;
    
    int colorMark;
    
    BufferedImage img;
    
    public Point maxTop;
    public Point maxRight;
    public Point minBot;
    public Point minLeft;
    
    public Scanner(File file){
        
        //fileHandler = new FileOperation2("/home/gugu/stuff/povray");
        
        points = new LinkedList<Point>();
        
        //linedUpPoints = new LinkedList<LinkedList<LinkedList<Point>>>();
        
        faces = new LinkedList<Face>();
        
        //File file = fileHandler.openFile();
            
        //System.out.println(file.getAbsolutePath()); 
            
        maxTop = new Point(0, 0);
        maxRight = new Point(0, 0);
        minBot = new Point(0, 0);
        minLeft = new Point(0, 0);
            
        colorMark = 0xff000000;
            
        try {
                        
            img = ImageIO.read(file);

            LinkedList<Integer> colors = new LinkedList<Integer>();
                
            LinkedList<Integer> numb = new LinkedList<Integer>();
                
            for (int y = 0; y < img.getHeight(); ++y ){
                    
                for (int x = 0; x < img.getWidth(); ++x ){
                        
                    int color = img.getRGB(x, y);
                        
                    if ( !colors.contains(color)){
                            
                        if (color == colorMark ){
                                
                            maxTop = new Point(x, y);
                                
                            minBot = new Point(x, y);
                                
                            maxRight = new Point(x, y);
                                        
                            minLeft = new Point(img.getWidth(), y);
                        }
                            
                        colors.add(color);
                            
                        numb.add(1);
                    }
                        
                    else{
                            
                        if (color == colorMark && y > minBot.y ){
                                
                            minBot = new Point(x, y); // ???
                        }
                            
                        if (color == colorMark && x > maxRight.x ){
                                
                            maxRight = new Point(x, y); // ???
                        }
                            
                        if (color == colorMark && x < minLeft.x ){
                                
                             minLeft = new Point(x, y); // ???
                        }
                            
                        int number = numb.get(colors.indexOf(color));
                            
                        numb.set(colors.indexOf(color), ++number);
                    }   
                    //System.out.println("y: " + y + ", x: " + x + ", color: " + color);
                }
            }
                 /*
            int i = 0;
                
            for (int color : colors){
                    
                if ( numb.get(i) > 100 ){
    
                    System.out.println(Integer.toHexString(color));
 
                    System.out.println(numb.get(i));
                }

                ++i;
            }*/
/*
            System.out.println("top x: " + maxTop.x + " , y: " + maxTop.y);
  
            System.out.println("min x: " + minBot.x + " , y: " + minBot.y);
 
            System.out.println("left x: " + minLeft.x + " , y: " + minLeft.y);

            System.out.println("right x: " + maxRight.x + " , y: " + maxRight.y);
            */
            // TODO for fragments
            
            for (int y = (int)maxTop.y; y < minBot.y; y += (minBot.y - maxTop.y) / 30 ){
                    
                for (int x = (int)minLeft.x; x < maxRight.x; x += (maxRight.x - minLeft.x) / 50 ){

                    if ( img.getRGB(x, y) == colorMark ){
                           
                        points.add(new Point(x/10, (img.getHeight() - y)/10));
                    }           
                }
            }
        }
        
        catch(Exception e){
            
            System.out.println("Scanner constructor exception !");
        }
    }
    
    public void printPoints(){
        
        Iterator<Face> faceIt = faces.iterator();
        
        while ( faceIt.hasNext() ){
            
            Face face = faceIt.next();
            Iterator<Point> pointIt = face.points.iterator();
            
            while (pointIt.hasNext()){
                
                Point p = pointIt.next();
                
                System.out.print("Scanned Point x: " + p.x + ", y " + p.y);
                System.out.print(" ;; ");
            }
            System.out.println("");
        }
        
        System.out.println("After scanner");
    }
    
    // TODO scan img and make new points to base...
    public void scanImage2(int detail){
        
        LinkedList<Point> linePoints = new LinkedList<Point>();
        
        int details = (int)(minBot.y - maxTop.y) / detail;
        
        for (int y = (int)maxTop.y + details; y < minBot.y; y+= details){
            
            linePoints = scanLine(y);

            Iterator<Point> lineIterator = linePoints.iterator();
    
            Face face = new Face();
            
            while (lineIterator.hasNext()){
                
                Point p = lineIterator.next();
                
                face.addPoint(p);
            }
            
            faces.add(face);
        }
    }
    
    public void scanImage(){
        
        LinkedList<Point> linePoints = new LinkedList<Point>();
        
        LinkedList<Point> prevLinePoints = new LinkedList<Point>();
        
        int details = (int)(minBot.y - maxTop.y) / 30;
        
        prevLinePoints = scanLine((int)maxTop.y);
        
        //Iterator<LinkedList<LinkedList<Point>>> i = linedUpPoints.iterator();
        
        for (int y = (int)maxTop.y + details; y < minBot.y; y+= details){
            
            linePoints = scanLine(y);

            Iterator<Point> lineIterator = linePoints.iterator();
            Iterator<Point> prevLineIterator = prevLinePoints.iterator();
            
            Face face = new Face();
            
            // Triangle UP
            if ( prevLinePoints.size() == 1 ){
                
                Point p = prevLineIterator.next();
                face.addPoint(p);
                p = lineIterator.next(); 
                face.addPoint(p);
                p = lineIterator.next(); 
                face.addPoint(p);
            }
            
            else {
                
                // Triangle Down
                if ( linePoints.size() == 1){
                    
                    Point p = prevLineIterator.next();
                    face.addPoint(p);
                    p = prevLineIterator.next();
                    face.addPoint(p);
                    p = lineIterator.next(); 
                    face.addPoint(p);
                }
                
                // Trapeze
                else {
                    
                    Point p = prevLineIterator.next();
                    face.addPoint(p);
                    p = prevLineIterator.next();
                    face.addPoint(p);
                    p = lineIterator.next(); 
                    face.addPoint(p);
                    p = lineIterator.next(); 
                    face.addPoint(p);
                }
            }
            
            faces.add(face);
            //System.out.println("Inside scanner face before clear " + face.size());
            //face.clear();
            //System.out.println("Inside scanner face after clear " + faces.getLast().size());
            
            prevLinePoints = linePoints;
            
            //System.out.println("Inside scanner faces size " + faces.size());
        }
    }
    
    private boolean pointsConnected(Point top, Point bot){
        
        float mostLeftX = top.x;
        float mostRightX = top.x;
        
        int tolerance = 3;
        
        // find most left point
        for ( int x = (int)mostLeftX; img.getRGB(x, (int)top.y) == colorMark; --x){
                
            if ( top.y == bot.y && x == bot.x){
                    
                return true;
            }
             
            mostLeftX = x;
        }
        
        // find most right point
        for ( int x = (int)mostRightX; img.getRGB(x, (int)top.y) == colorMark; ++x){
                
            if ( top.y == bot.y && x == bot.x){
                    
                return true;
            }
             
            mostRightX = x;
        }
        
        float y = top.y+1;
        
        int newMostLeftX, newMostRightX;
        
        boolean rowBelowColorMark = false;
        
        while ( y <= bot.y ){
                    
            for (int i = (int)mostLeftX; i <= mostRightX; ++i){
                
                //System.out.println("Tikrinu ar atitinka spalva y: " + y + ", x: " + i);

                if ( img.getRGB(i, (int)y) == colorMark ){
                    
                    //System.out.println("y: " + y + ", x: " + i + " Atitiko");
                    
                    rowBelowColorMark = true;
                    
                    newMostLeftX = i;
                    newMostRightX = i;
                    
                    // find most left point
                    for (int x = newMostLeftX; img.getRGB(x, (int)y) == colorMark; --x){
                        
                        if ( y == bot.y && x == bot.x ){
                    
                            return true;
                        }
                        
                        newMostLeftX = x;
                    } 
                    
                    // find most right point
                    for (int x = newMostRightX; img.getRGB(x, (int)y) == colorMark; ++x){
                        
                        if ( y == bot.y && x == bot.x ){
                    
                            return true;
                        }
                        
                        newMostRightX = x;
                    }
                    
                    // check if the line has a branch
                    if (mostRightX > newMostRightX ){
                        
                        for (int k = (int)mostRightX; k > newMostRightX; --k){
                            
                            if ( img.getRGB(k, (int)y) == colorMark ){
                                
                                if ( pointsConnected(new Point( k, (int)y) , bot) )
                                    return true;

                            }
                        }
                    }
                    
                    
                    //else{
                        
                        i = (int)mostRightX+10;
                        
                        mostLeftX = newMostLeftX;
                        
                        mostRightX = newMostRightX;
                    //}
                }
                
                
            }
          
            
            
            if ( !rowBelowColorMark )
                return false;
            
            rowBelowColorMark = false;
            
            ++y;
        }
        /*
        boolean checkRight = true;
        for (int y = top.y - 1; y <= bot.y; --y){
            
            for ( int x = mostLeftX; x > x - tolerance; --x){
                
                if ( img.getRGB(x, y) == colorMark ){
                    
                    for ( int _x = x; img.getRGB(x, y) == colorMark; --_x){
                        
                        if ( y == bot.y && _x == bot.x){
                    
                            return true;
                        }
                        
                        mostLeftX = x;
                    }
                    
                    checkRight = false;
                }
            }
            
            if ( checkRight ){
            
                for ( int x = mostLeftX; x < x + tolerance; ++x){

                    if ( img.getRGB(x, y) == colorMark ){
                        
                        if ( y == bot.y && x == bot.x){
                    
                            return true;
                        }

                        mostLeftX = x;
                        
                        x += tolerance;
                    }
                }
            }
            
            checkRight = true;
        }
        */
        return false;
    }
    
    private LinkedList<Point> scanLine(int y){
        
        LinkedList<Point> answ = new LinkedList<Point>();
        
        boolean pointFound = false;
        
        for (int i = (int)minLeft.x; i <= maxRight.x; ++i){
            
            if ( img.getRGB(i, y) == colorMark ){
                
                if (!pointFound){
                    
                    if ( answ.isEmpty() ){
                        
                        answ.add(new Point(i, y));

                        pointFound = true;
                    }
                    
                    // add comment (salia pikseliai vienas kito is tos pacios linijos)
                    else if ( answ.getLast().x + 5 < i ){
                    
                        answ.add(new Point(i, y));

                        pointFound = true;
                    }
                }
            }
            
            else{
                /*
                if ( !answ.isEmpty()){
                
                    if (pointFound && answ.getLast().x +10 < i){
                        
                        answ.add(new Point(i, y));
                    }
                }
             */
                pointFound = false;
            }
        }
        
        return answ;
    }
    
    public LinkedList<Point> getPoints(){
        
        return points;
    }
    
    public LinkedList<Face> getFaces(){
     
        return faces;
    }
    
    public float getMaxRight(){
        
        return maxRight.x;
    }
    
    public float getMinLeft(){
        
        return minLeft.x;
    }
    
    public float getMaxTop(){
        
        return maxTop.y;
    }
    
    public float getMinBot(){
        
        return minBot.y;
    }
    
    public float getWidth(){
        
        return maxRight.x - minLeft.x;
    }
    
    public float getHeight(){
        
        return minBot.y - maxTop.y;
    }
    
    public Point getMidPoint(){
        
        return new Point(minLeft.x+((maxRight.x-minLeft.x)/2), maxTop.y+((minBot.y-maxTop.y)/2), 0);
    }
}

