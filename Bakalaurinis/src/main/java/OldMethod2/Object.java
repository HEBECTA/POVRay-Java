/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OldMethod2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author gugu
 */
public class Object {
    
    String cameraSettings;
    String lightSettings;
    String floorSettings;
    String transformationSettings;
    
    LinkedList<LinkedList<Triangle>> triangles;
    
    LinkedList<Triangle> finalTriangles;
   
    Matrix matrix;
    
    int width;
    
    public Object(LinkedList<LinkedList<Triangle>> triangles, int width){
        
        this.triangles = triangles;
        
        this.matrix = new Matrix();
        
        this.width = width;
    }
    /*
    public Object(LinkedList<Triangle> triangles){
        
        this.trianglesTest = triangles;
        
        this.matrix = new Matrix();
    }*/
    
    public void inflate(float dept){
        
        finalTriangles = new LinkedList();
        
        //System.out.println("inflate function");
        
        Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        
        while ( rowIt.hasNext() ){

            LinkedList<Triangle> row = rowIt.next();

            Iterator<Triangle> triangleIt = row.iterator();
            
            Triangle firstTriangle;
            Triangle lastTriangle;
            
            if ( row.size() > 0 ){
                
                firstTriangle = row.getFirst();
                lastTriangle = row.getLast();
            }
            
            else {
                
                //System.out.println("no triangles in the linkedList row");
                continue;
            }
       
            if ( firstTriangle == lastTriangle){
                
                if ( firstTriangle.topLeft() || firstTriangle.topRight() ){
                    
                    
                }
                
                else if (firstTriangle.botLeft() || firstTriangle.botRight() ){
                    
                }
                
                else {
                    
                    //System.out.println("Object inflate error, can't define single triangle type");
                    break;
                }
            }
            
            else {
                
                float upperRowLength;
                float bottomRowLength;
                
                float topLeftStartPoint;
                float bottomLeftStartPoint;
                
                if ( firstTriangle.topRight() && lastTriangle.botLeft() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                else if ( firstTriangle.topRight() && lastTriangle.botRight() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                else if ( firstTriangle.topRight() && lastTriangle.topLeft() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                else if ( firstTriangle.topRight() && firstTriangle.topRight() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                
                
                else if ( firstTriangle.topLeft() && lastTriangle.botRight() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x; // ?????????????????????????????????????????
                }
                else if ( firstTriangle.topLeft() && lastTriangle.botLeft() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                else if ( firstTriangle.topLeft() && lastTriangle.topRight() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                else if ( firstTriangle.topLeft() && firstTriangle.topLeft() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p3.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p3.x;
                }
                
                
                else if ( firstTriangle.botRight() && lastTriangle.topLeft() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botRight() && lastTriangle.topRight() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botRight() && lastTriangle.botLeft() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botRight() && firstTriangle.botRight() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                
                
                else if ( firstTriangle.botLeft() && lastTriangle.topRight() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botLeft() && lastTriangle.topLeft() ){
                    
                    upperRowLength = lastTriangle.p2.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botLeft() && lastTriangle.botRight() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                else if ( firstTriangle.botLeft() && firstTriangle.botLeft() ){
                    
                    upperRowLength = lastTriangle.p1.x - firstTriangle.p1.x; 
                    bottomRowLength = lastTriangle.p3.x - firstTriangle.p2.x;
                    topLeftStartPoint = firstTriangle.p1.x;
                    bottomLeftStartPoint = firstTriangle.p2.x;
                }
                
                else{
                    
                    //System.out.println("Object inflate error, can't define triangle type");
                    break;
                }
                
                float localUpperDept = (upperRowLength * dept) / width;
                float localBottomDept = (bottomRowLength * dept) / width;
                
                //System.out.println("upperRowLength  " + upperRowLength);
                //System.out.println("bottomRowLength  " + bottomRowLength);
                //System.out.println("localUpperDept  " + localUpperDept);
                //System.out.println("localBottomDept  " + localBottomDept);
                
                float scaleUpper = (float)Math.PI / upperRowLength;
                float scaleBottom = (float)Math.PI / bottomRowLength;

                while ( triangleIt.hasNext() ){

                    Triangle triangle = triangleIt.next();

                    if ( triangle.topLeft() ){
                       
                        //float p1z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p2z = (float)Math.sin( (triangle.p2.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p3z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom) * localBottomDept;
                        triangle.p1.z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p2.z = (float)Math.sin( (triangle.p2.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p3.z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom) * localBottomDept;
                        
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, triangle.p3.z)));
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, -triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, -triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, -triangle.p3.z)));
                        
                        
                        //System.out.println("top left triangle = ");
                        //System.out.println("top row");
                        //System.out.println("distance " + (triangle.p1.x - topLeftStartPoint));
                        //System.out.println(triangle.p1.y + " " + triangle.p1.z);
                        //System.out.println("distance " + (triangle.p2.x - topLeftStartPoint));
                        //System.out.println(triangle.p2.y + " " + triangle.p2.z);
                        //System.out.println("bot row");
                        //System.out.println(triangle.p3.z);
                    }
                    
                    else if ( triangle.topRight() ){
                        //System.out.println("botLeft " + triangle.p1.x + " - " + topLeftStartPoint + " * " + scaleUpper + " = " + (triangle.p1.x - topLeftStartPoint) * scaleUpper);
                        //float p1z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p2z = (float)Math.sin( (triangle.p2.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p3z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        triangle.p1.z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p2.z = (float)Math.sin( (triangle.p2.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p3.z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, triangle.p3.z)));
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, -triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, -triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, -triangle.p3.z)));
                        //System.out.println("top right triangle = ");
                        //System.out.println("top row");
                        //System.out.println("distance " + (triangle.p1.x - topLeftStartPoint));
                        //System.out.println(triangle.p1.y + " " + triangle.p1.z);
                        //System.out.println("distance " + (triangle.p2.x - topLeftStartPoint));
                        //System.out.println(triangle.p2.y + " " + triangle.p2.z);
                        //System.out.println("bot row");
                        //System.out.println(triangle.p3.z);
                    }
                    
                    else if ( triangle.botLeft() ){
                        
                       //System.out.println("botLeft " + triangle.p1.x + " - " + topLeftStartPoint + " * " + scaleUpper + " = " + (triangle.p1.x - topLeftStartPoint) * scaleUpper);
                        
                        //float p1z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p2z = (float)Math.sin( (triangle.p2.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        //float p3z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        triangle.p1.z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p2.z = (float)Math.sin( (triangle.p2.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        triangle.p3.z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, triangle.p3.z)));
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, -triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, -triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, -triangle.p3.z)));
                        //System.out.println("bot left triangle = ");
                        //System.out.println("top row");
                        //System.out.println("distance " + (triangle.p1.x - topLeftStartPoint));
                        //System.out.println(triangle.p1.y + " " + triangle.p1.z);
                        //System.out.println("bot row");
                        //System.out.println(triangle.p2.z);
                        //System.out.println(triangle.p3.z);
                    }
                    
                    else if ( triangle.botRight() ){
                        //System.out.println("botLeft " + triangle.p1.x + " - " + topLeftStartPoint + " * " + scaleUpper + " = " + (triangle.p1.x - topLeftStartPoint) * scaleUpper);
                        //float p1z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        //float p2z = (float)Math.sin( (triangle.p2.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        //float p3z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        triangle.p1.z = (float)Math.sin( (triangle.p1.x - topLeftStartPoint) * scaleUpper ) * localUpperDept;
                        triangle.p2.z = (float)Math.sin( (triangle.p2.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        triangle.p3.z = (float)Math.sin( (triangle.p3.x - bottomLeftStartPoint) * scaleBottom ) * localBottomDept;
                        
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x,triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, triangle.p3.z)));
                        finalTriangles.add(new Triangle(new Point(triangle.p1.y, triangle.p1.x, -triangle.p1.z), new Point(triangle.p2.y, triangle.p2.x, -triangle.p2.z), 
                            new Point(triangle.p3.y, triangle.p3.x, -triangle.p3.z)));
                        //System.out.println("bot right triangle = ");
                        //System.out.println("top row");
                        //System.out.println("distance " + (triangle.p1.x - topLeftStartPoint));
                        //System.out.println(triangle.p1.y + " " + triangle.p1.z);
                        //System.out.println("bot row");
                        //System.out.println(triangle.p2.z);
                        //System.out.println(triangle.p3.z);
                    }
                }
            }
            
            //System.out.println();
        }
    }
    
    public void exportPovRay(String inputLocation, String outputLocation){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write("camera{ location  <0,0, -300>\n  angle 40\nright     x*image_width/image_height\nlook_at   <0,0,0>\n}");   
 

            myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}");
            myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");          

            myWriter.write("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}");

            myWriter.write("#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
            
            Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        
            myWriter.write("mesh\n{");
            
            while ( rowIt.hasNext() ){

                LinkedList<Triangle> row = rowIt.next();

                Iterator<Triangle> triangleIt = row.iterator();

                while ( triangleIt.hasNext() ){

                    Triangle triangle = triangleIt.next();
                    
                    myWriter.write("triangle{");

                    myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                    myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                    myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
                    
                    //System.out.println("Triangle");
                    //System.out.println("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                    //System.out.println("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                    //System.out.println("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
                    
                }
            }
            
            myWriter.write("texture{ pigment { color rgb<0, 1, 0>}}");

            myWriter.write("\n}");
            
            myWriter.close();

        }
        catch ( Exception e){
            
            System.out.println("Exception file export, Object class, exportPovRay function");
            
            e.printStackTrace();
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void exportPovRayTest(String inputLocation, String outputLocation, float radius){
        
        try {

            FileWriter myWriter = new FileWriter(inputLocation);

            
            //myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n\n\n"); 
            //myWriter.write("camera{ location  <0,0, -300>\n          angle 40\n          right     x*image_width/image_height\n          look_at   <0,0,0>\n}\n\n");   
 

            //myWriter.write("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}\n");
            //myWriter.write("light_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}\n\n");          

            //myWriter.write("#declare Floor_Texture =\n      texture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\n      texture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\n      texture {\n         pigment { P_WoodGrain12B color_map { M_Wood18B }}\n         finish { reflection 0.25 }\n}\n\n");

            //myWriter.write("#declare Floor =\nplane { y, 0\n     texture { Floor_Texture\n           scale 0.5\n           rotate y*90\n           rotate <10, 0, 15>\n           translate z*4\n     }\n}\n");
            
            myWriter.write("#include \"colors.inc\"\n#include \"textures.inc\"\n#include \"shapes.inc\"\n#include \"metals.inc\"\n#include \"glass.inc\"\n#include \"woods.inc\"\n"); 
            myWriter.write(cameraSettings);
 
            myWriter.write(lightSettings);
            myWriter.write(floorSettings);          

            myWriter.write("\nmesh\n{\n");
            
            Iterator<Triangle> rowIt = finalTriangles.iterator();
        
            //myWriter.write("mesh\n{");
    
            while ( rowIt.hasNext() ){

                    Triangle triangle = rowIt.next();
                    myWriter.write("triangle{");

                    myWriter.write("<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, ");
                    myWriter.write("<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, ");
                    myWriter.write("<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">}\n");
            }
            
            myWriter.write("texture{ pigment { color rgb<0, 1, 0>}}");
            
            myWriter.write("\nscale <3, 3, 3>\n");
            
            myWriter.write("\n"+transformationSettings+"\n");  
            
            myWriter.write("\n}\n");  

            myWriter.write("\n");

            //myWriter.write("\n}");
            /*
            rowIt = finalTriangles.iterator();
            
            while ( rowIt.hasNext() ){

                    Triangle triangle = rowIt.next();
                    myWriter.write("sphere{<"+triangle.p1.x+", "+triangle.p1.y+", "+triangle.p1.z+">, " + radius + "\npigment {Red}\n}");
                    myWriter.write("sphere{<"+triangle.p2.x+", "+triangle.p2.y+", "+triangle.p2.z+">, " + radius + "\npigment {Red}\n}");
                    myWriter.write("sphere{<"+triangle.p3.x+", "+triangle.p3.y+", "+triangle.p3.z+">, " + radius + "\npigment {Red}\n}");
            }
           
            myWriter.write("\n");
            */
            myWriter.close();

        }
        catch ( Exception e){
            
            System.out.println("Exception file export, Object class, exportPovRay function");
            
            e.printStackTrace();
        }
        
        executePovRay(inputLocation, outputLocation);
    }
    
    public void printTriangles(){
        
        Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        
        while ( rowIt.hasNext() ){
            
            LinkedList<Triangle> row = rowIt.next();
            
            Iterator<Triangle> triangleIt = row.iterator();
            
            while ( triangleIt.hasNext() ){
                
                Triangle triangle = triangleIt.next();
                
                triangle.print();
            }
        }
    }
    
    public void printTrianglesTest(){
        
        Iterator<Triangle> triangleIt = finalTriangles.iterator();
        
        while ( triangleIt.hasNext() ){
            
            Triangle triangle = triangleIt.next();
            
            triangle.print();
            System.out.println();
        }
    }
    
    public void scale(float y, float x, float z){
        
        Matrix scaleM = new Matrix();
        
        scaleM.scale(y, x, z);
        
        Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        
        while ( rowIt.hasNext() ){
            
            LinkedList<Triangle> row = rowIt.next();
            
            Iterator<Triangle> triangleIt = row.iterator();
            
            while ( triangleIt.hasNext() ){
                
                Triangle triangle = triangleIt.next();
                
                scaleM.printMatrix();
                
                triangle.p1.matrixMult(scaleM);
                triangle.p2.matrixMult(scaleM);
                triangle.p3.matrixMult(scaleM);
            }
        }
    }
    
    public void transformation(Matrix m){
        
        //matrix.multiply(m);
        
        Iterator<LinkedList<Triangle>> rowIt = triangles.iterator();
        
        while ( rowIt.hasNext() ){
            
            LinkedList<Triangle> row = rowIt.next();
            
            Iterator<Triangle> triangleIt = row.iterator();
            
            while ( triangleIt.hasNext() ){
                
                Triangle triangle = triangleIt.next();
                
                triangle.p1.matrixMult(m);
                triangle.p2.matrixMult(m);
                triangle.p3.matrixMult(m);
            }
        }
    }
    
    public void transformationTest(Matrix m){
        
        //matrix.multiply(m);
        
        Iterator<Triangle> triangleIt = finalTriangles.iterator();
        
        while ( triangleIt.hasNext() ){
            
            Triangle triangle = triangleIt.next();
            
            triangle.p1.matrixMult(m);
            triangle.p2.matrixMult(m);
            triangle.p3.matrixMult(m);
        }
    }
    
    private float topDistanceBetweenTriangles(Triangle t1, Triangle t2){
        
        
        
        return 0;
    }
    
    private float botDistanceBetweenTriangles(Triangle t1, Triangle t2){
        
        return 0;
    }
    
    private void executePovRay(String inputLocation, String outputLocation){
        
            try {
                System.out.println("Opening povray");
                Runtime runTime = Runtime.getRuntime();
                System.out.println("Read from " + inputLocation + " write to " + outputLocation);
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
    
    public void setSceneSettings(String camera, String light, String floor, String transformation){
        
        cameraSettings = camera;
        lightSettings = light;
        floorSettings = floor;
        transformationSettings = transformation;
    }
}
