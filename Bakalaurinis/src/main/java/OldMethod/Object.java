/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OldMethod;

/**
 *
 * @author gugu
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;



public class Object {
    
    String cameraSettings;
    String lightSettings;
    String floorSettings;
    String transformationSettings;
    
    private Point midPoint;
    
    private LinkedList<Point> points;
    
    private LinkedList<LinkedList<Point>> experiment;
    
    private LinkedList<Face> faces;
    
    // processed faces
    private LinkedList<Face> procFaces;
    
    float maxWidth;
    float maxHeight;
    
    int maxIssiputimas;
    
    int detail;
   /*
    public Object(){
        
        this.points = new LinkedList<>();
        this.procFaces = new LinkedList<Face>();
        this.maxIssiputimas = 5;
    }
     */
    public Object(LinkedList<Face> f, Point midPoint, float w, float h, int detail, int inflationDept){
        
        //faces = new LinkedList<Face>();
        
        this.faces = f;
        this.maxHeight = h;
        this.maxWidth = w;
        this.procFaces = new LinkedList<Face>();
        this.maxIssiputimas = inflationDept;  // 20
        this.points = new LinkedList<Point>();
        this.detail = detail;
        this.experiment = new LinkedList<LinkedList<Point>>();
        
        this.midPoint = midPoint;
    }
    
    public void printPoints(){
        
        Iterator<Point> it = points.iterator();
        
        System.out.println("Points " + points.size());
        
        while( it.hasNext() ){
            
            Point p = it.next();
            
            System.out.println("Point x " + p.x + ", y "+ p.y + ", z " + p.z);
        }
    }
    
    public void printProcFaces(){
        
        Iterator<Face> it = procFaces.iterator();
        
        System.out.println("Proc Faces size : " + procFaces.size());
        
        while(it.hasNext()){
            
            Face f = it.next();
            
            Iterator<Point> pIt = f.points.iterator();
            
            System.out.println("Face:");
            
            while (pIt.hasNext()){
                
                Point p = pIt.next();
                
                System.out.println("Point: x:"+p.x+", y:"+p.y+", z:"+p.z);
            }
            
            System.out.println("");
        }
    }
    
    public void printFaces(){
        
        Iterator<Face> it = faces.iterator();
        
        System.out.println("Faces size : " + faces.size());
        
        while(it.hasNext()){
            
            Face f = it.next();
            
            Iterator<Point> pIt = f.points.iterator();
            
            System.out.println("Face:");
            
            while (pIt.hasNext()){
                
                Point p = pIt.next();
                
                System.out.println("Point: x:"+p.x+", y:"+p.y+", z:"+p.z);
            }
            
            System.out.println("");
        }
    }
    
    public void printMidPoint(){
        
        System.out.println("Mid Point:");
        System.out.println("x : " + midPoint.x + ", y : " + midPoint.y + ", z = " + midPoint.z);
    }
    
    public void detailedFaces3(){
        
        procFaces = new LinkedList<Face>();
        
        Iterator<Face> itFaces = faces.iterator();
        
        Face firstLine;
        Face secondLine; 
        
        Point p1; 
        Point p2;
        Point p3;
        Point p4;
        
        Iterator<Point> itPoint;
        
        if ( itFaces.hasNext() ){
            
            firstLine = itFaces.next();
        }
        
        else {
            
            //System.out.println("No points for triangalation !");
            
            return;
        }
        
        if ( itFaces.hasNext() ){
            
            secondLine = itFaces.next();
        }
        
        else {
            
            //System.out.println("No enough points for triangalation !");
            
            return;
        }
        
        if ( firstLine.size() == 1 && secondLine.size() == 2 ){
            
            p1 = firstLine.points.getFirst();
            p2 = secondLine.points.getFirst();
            p3 = secondLine.points.getLast();
            /*
            System.out.println("TOP");
            System.out.println("Point x " + p1.x + " ,y " + p1.y);
            System.out.println("BOT");
            System.out.println("Point x " + p2.x + " ,y " + p2.y + " Point 2 x " + p3.x + " ,y " + p3.y);
            System.out.println("");*/
            
            triangulate(p1, p2, p3);
        }
        
        else if ( firstLine.size() == 2 && secondLine.size() == 1 ){
            
            p1 = firstLine.points.getFirst();
            p2 = firstLine.points.getFirst();
            p3 = secondLine.points.getLast();
            /*
            System.out.println("TOP");
            System.out.println("Point x " + p1.x + " ,y " + p1.y + " Point 2 x " + p2.x + " ,y " + p2.y);
            System.out.println("BOT");
            System.out.println("Point x " + p3.x + " ,y " + p3.y);
            System.out.println("");*/
            
            triangulate(p1, p2, p3);
        }
        
        else if ( firstLine.size() == 2 && secondLine.size() == 2 ){
            
            p1 = firstLine.points.getFirst();
            p2 = firstLine.points.getLast();
            p3 = secondLine.points.getFirst();
            p4 = secondLine.points.getLast();
            /*
            System.out.println("TOP");
            System.out.println("Point x " + p1.x + " ,y " + p1.y+ " Point 2 x " + p2.x + " ,y " + p2.y);
            System.out.println("BOT");
            System.out.println("Point x " + p3.x + " ,y " + p3.y + " Point 2 x " + p4.x + " ,y " + p4.y);
            System.out.println("");*/
            
            triangulate(p1, p2, p3, p4);
        }
  
        else {
            
            //System.out.println("Wrong number of points for triangalation !");
            
            return ;
        }
         
        while ( itFaces.hasNext() ){
            
            firstLine = new Face(secondLine);
            secondLine = itFaces.next();
            
            if ( firstLine.size() == 1 && secondLine.size() == 2 ){
            
                p1 = firstLine.points.getFirst();
                p2 = secondLine.points.getFirst();
                p3 = secondLine.points.getLast();
                /*
                System.out.println("TOP");
                System.out.println("Point x " + p1.x + " ,y " + p1.y);
                System.out.println("BOT");
                System.out.println("Point x " + p2.x + " ,y " + p2.y);
                System.out.println("BOT");
                System.out.println("Point x " + p3.x + " ,y " + p3.y);
                System.out.println("");
                */
                triangulate(p1, p2, p3);
            }

            else if ( firstLine.size() == 2 && secondLine.size() == 1 ){

                p1 = firstLine.points.getFirst();
                p2 = firstLine.points.getFirst();
                p3 = secondLine.points.getLast();
                /*
                System.out.println("TOP");
                System.out.println("Point x " + p1.x + " ,y " + p1.y);
                System.out.println("TOP");
                System.out.println("Point x " + p2.x + " ,y " + p2.y);
                System.out.println("BOT");
                System.out.println("Point x " + p3.x + " ,y " + p3.y);
                System.out.println("");
                */
                triangulate(p1, p2, p3);
            }

            else if ( firstLine.size() == 2 && secondLine.size() == 2 ){

                p1 = firstLine.points.getFirst();
                p2 = firstLine.points.getLast();
                p3 = secondLine.points.getFirst();
                p4 = secondLine.points.getLast();
                /*
                System.out.println("TOP");
                System.out.println("Point x " + p1.x + " ,y " + p1.y);
                System.out.println("TOP");
                System.out.println("Point x " + p2.x + " ,y " + p2.y);
                System.out.println("BOT");
                System.out.println("Point x " + p3.x + " ,y " + p3.y);
                System.out.println("BOT");
                System.out.println("Point x " + p4.x + " ,y " + p4.y);
                System.out.println("");
                */
                triangulate(p1, p2, p3, p4);
            }

            else {

                //System.out.println("Wrong number of points for triangalation !");
            }
        }
}
    
    // ?????????
    private void triangulate(Point p1, Point p2, Point p3) {
        
        Point singlePoint = p1;
        Point leftPoint = p2;
        Point rightPoint = p3;/*
        System.out.println("");
        System.out.println("");
        System.out.println("Triangulate 3");*/
        if ( p1.y == p2.y ){
            
            singlePoint = p3;
            leftPoint = p1;
            rightPoint = p2;
        }
            
        int dist = (int)(rightPoint.x - leftPoint.x);
            
        float localRadius = ((maxIssiputimas * dist) / maxWidth);
            
        int nmbDots = dist / detail;
        int cornerPadding = (dist - (nmbDots * detail)) / 2;
            
        float degDetail = (float)180 / dist;
                
        Point TriangLeftPoint = new Point(leftPoint.x, leftPoint.y, 0);
        float rad = (float)Math.toRadians( ((leftPoint.x+cornerPadding)-leftPoint.x)*degDetail ) ;
        Point TriangRightPoint = new Point( leftPoint.x+cornerPadding, leftPoint.y, (float)Math.sin(rad)*localRadius);
                
        if (cornerPadding == 0)
            nmbDots -=1;
        
        for (int i = 0; i < nmbDots+1; ++i){
            
            Face frontFace = new Face(singlePoint, new Point(TriangLeftPoint), new Point(TriangRightPoint));
            Face rearFace = new Face(singlePoint, new Point(TriangLeftPoint.x, TriangLeftPoint.y, -TriangLeftPoint.z),
                    new Point(TriangRightPoint.x, TriangRightPoint.y, -TriangRightPoint.z));
        
            procFaces.add(frontFace);
            procFaces.add(rearFace);
            /*
            System.out.println("Single point x:"+singlePoint.x+", y: "+singlePoint.y+", z: "+singlePoint.z);
            System.out.println("Left point x:"+TriangLeftPoint.x+", y: "+TriangLeftPoint.y+", z: "+TriangLeftPoint.z);
            System.out.println("Right point x:"+TriangRightPoint.x+", y: "+TriangRightPoint.y+", z: "+TriangRightPoint.z);
            
            System.out.println("");
                  */    
            TriangLeftPoint = new Point(TriangRightPoint);
            TriangRightPoint.x += detail;
            rad = (float)Math.toRadians((TriangRightPoint.x-leftPoint.x)*degDetail);
            TriangRightPoint.z = (float)(Math.sin(rad)*localRadius);  
        }
        
        procFaces.add(new Face(singlePoint, new Point(TriangRightPoint), new Point(rightPoint)));
        procFaces.add(new Face(singlePoint, new Point(TriangRightPoint.x, TriangRightPoint.y, -TriangRightPoint.z),
                    new Point(rightPoint)));
   /*
        System.out.println("Single point x:"+singlePoint.x+", y: "+singlePoint.y+", z: "+singlePoint.z);
        System.out.println("Left point x:"+TriangRightPoint.x+", y: "+TriangRightPoint.y+", z: "+TriangRightPoint.z);
        System.out.println("Right point x:"+rightPoint.x+", y: "+rightPoint.y+", z: "+rightPoint.z);
       
        System.out.println("");*/
    }
    
    private void triangulate(Point p1, Point p2, Point p3, Point p4){
        /*
        System.out.println("");
        System.out.println("");
        System.out.println("Triangulate 4");
        */
        Point longLeftPoint = p1;
        Point longRightPoint = p2;
        Point shortLeftPoint = p3;
        Point shortRightPoint = p4;
        
        int longDist = (int)(p2.x - p1.x);
        int shortDist = (int)(p4.x - p3.x);
        
        if ( shortDist > longDist ){
            
            longLeftPoint = p3;
            longRightPoint = p4;
            shortLeftPoint = p1;
            shortRightPoint = p2;
            
            int temp = longDist;
            longDist = shortDist;
            shortDist = temp;
        }
   
        int longNmbDots = longDist / detail;
        int shortNmbDots = shortDist / detail;
        
        // ????????????????????????
        if ( longNmbDots < 2 && shortNmbDots < 2){
            
            Face face = new Face(longLeftPoint, longRightPoint, shortLeftPoint);
            procFaces.add(face);
            face = new Face(shortLeftPoint, shortRightPoint, longRightPoint);
            procFaces.add(face);
            return;
        }
        
        float longLocalRadius = ((maxIssiputimas * longDist) / maxWidth);
        float shortLocalRadius = ((maxIssiputimas * shortDist) / maxWidth);
        
        int longCornerPadding = (longDist - (longNmbDots * detail)) / 2;
        int shortCornerPadding = (shortDist - (shortNmbDots * detail)) / 2;
            
        float longDegDetail = (float)180 / longDist;
        float shortDegDetail = (float)180 / shortDist;
                
        Point shortTmpLeftPoint = new Point(shortLeftPoint.x, shortLeftPoint.y, 0);
        float shortTmpRadius = (float)Math.toRadians( ((shortLeftPoint.x+shortCornerPadding)-shortLeftPoint.x)*shortDegDetail );
        Point shortTmpRightPoint = new Point( shortLeftPoint.x+shortCornerPadding, shortLeftPoint.y, (float)Math.sin(shortTmpRadius)*shortLocalRadius);
                
        Point longTmpLeftPoint = new Point(longLeftPoint.x, longLeftPoint.y, 0);
        float longTmpRadius = (float)Math.toRadians( ((longLeftPoint.x+longCornerPadding)-longLeftPoint.x)*longDegDetail );
        Point longTmpRightPoint = new Point(longLeftPoint.x + longCornerPadding, longLeftPoint.y, 0);
        
        // ????????????????????????????????
        if (longCornerPadding == 0){
            
            longNmbDots -=1;
            
            longTmpRightPoint = new Point(longLeftPoint.x + detail, longLeftPoint.y, 0);
        }
        
        if (shortCornerPadding == 0){
            
            shortNmbDots -=1;
            
            shortTmpRightPoint = new Point( shortLeftPoint.x+detail, shortLeftPoint.y, (float)Math.sin(shortTmpRadius)*shortLocalRadius);
        }
        
        // Triangulation
        int dotsPerVertex = longNmbDots / shortNmbDots;
        int extraDots = longNmbDots % shortNmbDots;
        /*
        System.out.println("Short nmb " + shortNmbDots);
        System.out.println("Long nmb " + longNmbDots);
        System.out.println("dotsPerVertex " + dotsPerVertex);
        System.out.println("extraDots " + extraDots);
        
        System.out.println("short point left x: " + shortLeftPoint.x + " , right x: " + shortRightPoint.x);
        System.out.println("long point left x: " + longLeftPoint.x + " , right x: " + longRightPoint.x);
        */
        for (int shrt = 0; shrt < shortNmbDots+1; ++shrt){
            
            int tmpDotsPerVertex = dotsPerVertex;

            if ( extraDots > 0 ){
                
                ++tmpDotsPerVertex;
                --extraDots;
            }
            
            //System.out.println("Single point x:"+shortTmpLeftPoint.x);
            
            Point opositeShortLeftPoint = new Point(shortTmpLeftPoint.x, shortTmpLeftPoint.y, -shortTmpLeftPoint.z);

            // FIRST STEP
            for (int lng = 0; lng < tmpDotsPerVertex; ++lng){
                
                Point opositeLongLeftPoint = new Point(longTmpLeftPoint.x, longTmpLeftPoint.y, -longTmpLeftPoint.z);
                Point opositeLongRightPoint = new Point(longTmpRightPoint.x, longTmpRightPoint.y, -longTmpRightPoint.z);
                
                Face frontFace = new Face(longTmpLeftPoint, longTmpRightPoint, shortTmpLeftPoint);
                Face rearFace = new Face(opositeLongLeftPoint, opositeLongRightPoint, opositeShortLeftPoint);
        
                procFaces.add(frontFace);
                procFaces.add(rearFace);
                
                //System.out.println("Right point x:"+longTmpRightPoint.x);

                longTmpLeftPoint = new Point(longTmpRightPoint);
                longTmpRightPoint.x += detail;
                longTmpRadius = (float)Math.toRadians((longTmpRightPoint.x-longLeftPoint.x)*longDegDetail);
                longTmpRightPoint.z = (float)(Math.sin(longTmpRadius)*longLocalRadius);
            }

            // THIRD STEP (connect segements)
            Point opositeShortRightPoint = new Point(shortTmpRightPoint.x, shortTmpRightPoint.y, -shortTmpRightPoint.z);
                
            Face frontFace = new Face(shortTmpLeftPoint, shortTmpRightPoint, longTmpLeftPoint);
            Face rearFace = new Face(opositeShortLeftPoint, opositeShortRightPoint,
                new Point(longTmpLeftPoint.x, longTmpLeftPoint.y, -longTmpLeftPoint.z));
        
            procFaces.add(frontFace);
            procFaces.add(rearFace);
            /*
            System.out.println("");
            
            System.out.println("Pertvara");
            
            System.out.println("short left point x: "+shortTmpLeftPoint.x+", right point x: "+shortTmpRightPoint.x);

            System.out.println("");*/

            shortTmpLeftPoint = new Point(shortTmpRightPoint);
            shortTmpRightPoint.x += detail;
            shortTmpRadius = (float)Math.toRadians((shortTmpRightPoint.x-shortLeftPoint.x)*shortDegDetail);
            shortTmpRightPoint.z = (float)(Math.sin(shortTmpRadius)*shortLocalRadius);
        }
        
        // Last ????????
        
        //System.out.println("");
        
        procFaces.add(new Face(shortRightPoint, longTmpRightPoint, longRightPoint)); 
        procFaces.add(new Face(shortRightPoint, new Point(longTmpRightPoint.x, longTmpRightPoint.y, -longTmpRightPoint.z),
                    longRightPoint));
        /*
        System.out.println("Single point x:"+shortRightPoint.x+", y: "+shortRightPoint.y+", z: "+shortRightPoint.z);
        System.out.println("Left point x:"+longTmpRightPoint.x+", y: "+longTmpRightPoint.y+", z: "+longTmpRightPoint.z);
        System.out.println("Right point x:"+longRightPoint.x+", y: "+longRightPoint.y+", z: "+longRightPoint.z);

        System.out.println("");*/
    }
    
    public void export(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            //myWriter.write(String.valueOf(points.size()) + ",\n");
            //myWriter.write(String.valueOf(procFaces.size()*3) + ",\n");
            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nmesh\n{\n"); 
            
            //myWriter.write("mesh\n{");
            
            Iterator<Face> facesIt = procFaces.iterator();
            
            while (facesIt.hasNext()){
                
                Face f = facesIt.next();
                Iterator<Point> pointsIt = f.points.iterator();
                
                myWriter.write("triangle{");
                
                Point p;
                
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    myWriter.write("<"+String.valueOf((p.x/10) )+", "+(double)(((midPoint.y-p.y+midPoint.y)/10) )+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                }
                
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    myWriter.write("<"+String.valueOf((p.x/10) )+", "+(double)(((midPoint.y-p.y+midPoint.y)/10) )+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                }
                
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    myWriter.write("<"+String.valueOf((p.x/10) )+", "+(double)(((midPoint.y-p.y+midPoint.y)/10) )+", "+String.valueOf(Math.floor(p.z*100)/100)+">}\n");
                }
            }
            
            //myWriter.write("\n}");
            myWriter.write("\nscale <4, 4, 4>\n");
            myWriter.write("\n"+transformationSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception file export");
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void exportPovRay(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write("camera{ location  <0,0,-150>\n  angle 40\nright     x*image_width/image_height\nlook_at   <0,0,0>\n}");   
 

            myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}");
            myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");          

            myWriter.write("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map exportMiddlePoint{ M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}");

            myWriter.write("#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
            
            myWriter.write("mesh\n{");
            
            Iterator<Face> facesIt = procFaces.iterator();
            
            while (facesIt.hasNext()){
               
                Face f = facesIt.next();
                Iterator<Point> pointsIt = f.points.iterator();
                
                myWriter.write("triangle{");
                
                Point p;
                      
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    //System.out.println("<"+String.valueOf(p.x/10)+", "+(double)(maxHeight-p.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                    
                    myWriter.write("<"+String.valueOf(p.x/10)+", "+(double)(midPoint.y-p.y+midPoint.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                }
                
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    //System.out.println("<"+String.valueOf(p.x/10)+", "+(double)(maxHeight-p.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                    
                    myWriter.write("<"+String.valueOf(p.x/10)+", "+(double)(midPoint.y-p.y+midPoint.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">, ");
                }
                
                if ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                    //System.out.println("<"+String.valueOf(p.x/10)+", "+(double)(maxHeight-p.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">}\n");
                    
                    myWriter.write("<"+String.valueOf(p.x/10)+", "+(double)(midPoint.y-p.y+midPoint.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">}\n");
                }
            }
            
             myWriter.write("texture{ pigment { color rgb<0, 1, 0>}}");
            
            myWriter.write("\n}");
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception file export");
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void rotateAbsoluteXaxis(int degree){
        
        Iterator<Face> faceIt = procFaces.iterator();
        
        while ( faceIt.hasNext() ){
            
            Face face = faceIt.next();
            
            Iterator<Point> points = face.points.iterator();
            
            while ( points.hasNext()){
                
                Point point = points.next();
                
                float sinY = (float)(midPoint.y - point.y);
                float cosZ = (float)(midPoint.z - point.z);
                
                if (point.z > 0){
                    
                    float ZDegree = (float)Math.acos(1f/cosZ);
                    cosZ = (float)Math.cos(ZDegree+degree)*cosZ;
                }
                
        
                if ( sinY > 0 ){
                        
                    double YDegree = Math.asin(1f/sinY);
                        
                    sinY = (float)Math.sin(YDegree+degree)*sinY;
                }
                
            }
        }
    }
    
    public void rotateAbsoluteYaxis(int degree){
    
       // x
    }
    
    public void rotateAbsoluteZaxis(int degree){
    
       // x y
    }
    
    public void setPosition(int x, int y, int z){
        
        Iterator<Face> faceIt = faces.iterator();
        
        while (faceIt.hasNext()){
            
            Face face = faceIt.next();
            
            Iterator<Point> points = face.points.iterator();
            
            while ( points.hasNext()){
                
                Point point = points.next();
                
                int Xdiff = (int)(midPoint.x - point.x);
                int Ydiff = (int)(midPoint.y - point.y);
                int Zdiff = (int)(midPoint.z - point.z);
                
                point.x = x - Xdiff;
                point.y = y - Ydiff;
                point.z = z - Zdiff;
            }
        }
        
        midPoint.x = x;
        midPoint.y = y;
        midPoint.z = z;
    }
    
    public void exportPerimeter(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write("camera{ location  <0,0,-150>\n  angle 40\nright     x*image_width/image_height\nlook_at   <0,0,0>\n}");   
 

            myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}");
            myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");          

            myWriter.write("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}");

            myWriter.write("#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
           
            Iterator<Face> facesIt = faces.iterator();
            
            while (facesIt.hasNext()){
                
                Face f = facesIt.next();
                Iterator<Point> pointsIt = f.points.iterator();
               
                Point p;
                
                while ( pointsIt.hasNext() ){
                    
                    p = pointsIt.next();
                    
                  
                    System.out.println("Export x : " + p.x/10 + ", y : " + (double)(midPoint.y-p.y+midPoint.y)/10 + ", z : " + Math.floor(p.z*100)/100);
                    myWriter.write("sphere {\n<"+String.valueOf(p.x/10)+", "+(double)(midPoint.y-p.y+midPoint.y)/10+", "+String.valueOf(Math.floor(p.z*100)/100)+">, 0.5\npigment{Green}\n}");
                }
            }
            
            myWriter.close();
        }
        catch ( Exception e){
            
            System.out.println("Exception file export");
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void exportSphere(String location){
        
        Iterator<Point> it = points.iterator();
        
         try {
                
            FileWriter myWriter = new FileWriter(location);
        
            while ( it.hasNext() ){

                Point p = it.next();

                myWriter.write("sphere {\n");
                if ( p.z < 0){
                    myWriter.write("<"+(double)p.x/10+","+(double)(maxHeight-p.y)/10+","+(float)p.z+">, ." + 2 + "\npigment {Green}\n}\n");
                }
                
                else
                    myWriter.write("<"+(double)p.x/10+","+(double)(maxHeight-p.y)/10+","+(float)p.z+">, ." + 2 + "\npigment {Blue}\n}\n");
                //myWriter.write("<"+(double)p.x/10+","+(double)p.z/10+","+(double)(maxHeight-p.y)/10+">, ." + 2 + "\npigment {Blue}\n}\n");
            }
            
            myWriter.close();
        }
        catch ( Exception e){
                
            System.out.println("Export Sphere Exception");
        }
    }
    
    public void exportMiddlePoint(String inputLocation, String outputLocation, Point left, Point right, Point top, Point bot){
        
        try {
                
            FileWriter myWriter = new FileWriter(inputLocation);
        
            myWriter.write((double)(right.x-left.x)/10+" "+(double)(top.y-bot.y)/10);
            
            myWriter.close();
        }
        catch ( Exception e){
                
            System.out.println("Export Sphere Exception");
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    private void executePovRay(String inputLocation, String outputLocation){
        /*
        File file1 = new File(inputLocation);
        inputLocation = file1.getAbsolutePath();
        
        File file2 = new File(outputLocation);
        outputLocation = file2.getAbsolutePath();
        
        // Create a Path object from the input path
        Path path = Paths.get(outputLocation);

        // Resolve the path to the final path
        Path resolvedPath = path.normalize();

        // Get the final path as a string
        outputLocation = resolvedPath.toString();
        
        path = Paths.get(inputLocation);

        // Resolve the path to the final path
        resolvedPath = path.normalize();

        // Get the final path as a string
        inputLocation = resolvedPath.toString();
        */
            try {
                System.out.println("Opening povray");
                Runtime runTime = Runtime.getRuntime();
                System.out.println("Read from " + inputLocation + " write to " + outputLocation);
                System.out.println("Command " + "povray +I" + inputLocation + " +V +W1280 +H720 +O" + outputLocation);
                Process process = runTime.exec("povray +I" + inputLocation + " +V +W1280 +H720 +O" + outputLocation);
                synchronized (process){ 
                    try {
                        //Thread.sleep(6000);
                        process.waitFor();
                        System.out.println(process.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Closing povray");
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        
    }
    
    public void setSettings(String camera, String light, String floor, String transformation){
        
        cameraSettings = camera;
        lightSettings = light;
        floorSettings = floor;
        transformationSettings = transformation;
    
    }
}

