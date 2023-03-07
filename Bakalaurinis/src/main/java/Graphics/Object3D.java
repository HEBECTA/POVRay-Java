/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Graphics.Triangle;
import Graphics.Point;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author GUGU
 */
public class Object3D {
    
    private FigureData figureData;
    
    //private LinkedList<LinkedList<Point>> pixels;
    private LinkedList<LinkedList<Triangle>> triangles;
    
    LinkedList<Triangle> finalTriangles;
   
    Matrix matrix;
    
    public int width;
    
    public Object3D(){
        
    }
    
    public Object3D(LinkedList<LinkedList<Triangle>> triangles, int width){
        
        this.triangles = triangles;
        
        this.matrix = new Matrix();
        
        this.width = width;
    }
    
    public Object3D(LinkedList<LinkedList<Point>> points, float dept, int width){
            
        triangles = new LinkedList<>();
        LinkedList<Triangle> rowTriangles = new LinkedList();
        this.matrix = new Matrix();
        this.width = width;
        
        Iterator<LinkedList<Point>> rowIt = points.iterator();
        LinkedList<Point> firstRow;
        
        while (rowIt.hasNext() ){
            
            firstRow = rowIt.next();

            Iterator<Point> pointsIt = firstRow.iterator();
            
            LinkedList<Triangle> trianglesRow = new LinkedList();
            
            float rowLength = 1f;
            float mostLeftX = 0f;
            
            if ( firstRow.size() > 1 ){
                
                rowLength = firstRow.getLast().x - firstRow.getFirst().x;
                mostLeftX = firstRow.getFirst().x;
            }
           
            float rowDept = (rowLength * dept) / width;
            float scale = (float)Math.PI / rowLength;
            
            while ( pointsIt.hasNext() ){
                
                Point point = pointsIt.next();

                float z = (float)Math.sin( (point.x - mostLeftX) * scale ) * rowDept;
                
                Triangle t1 = new Triangle(new Point(point.y, point.x, z), new Point(point.y, point.x+1, z), new Point(point.y+1, point.x, z));
                Triangle t2 = new Triangle(new Point(point.y, point.x+1, z), new Point(point.y+1, point.x, z), new Point(point.y+1, point.x+1, z));
                
                trianglesRow.add(t1);
                trianglesRow.add(t2);
            }
            
            triangles.add(trianglesRow);
        }
    }
    
    public void setFigureData(FigureData figureData){
        
        //this.figureData = figureData;
        width = figureData.width;
        //pixels = figureData.figureAreaPixels;
        triangles = copyLinkedList(figureData.flatTriangles);  
    }
    
    public LinkedList<Triangle> getInflatedFigure(float dept){
        
        finalTriangles = new LinkedList();
        
        System.out.println("inflate function");
        
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
                
                System.out.println("no triangles in the linkedList row");
                continue;
            }
       
            if ( firstTriangle == lastTriangle){
                
                if ( firstTriangle.topLeft() || firstTriangle.topRight() ){
                    
                    
                }
                
                else if (firstTriangle.botLeft() || firstTriangle.botRight() ){
                    
                }
                
                else {
                    
                    System.out.println("Object inflate error, can't define single triangle type");
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
                    
                    System.out.println("Object inflate error, can't define triangle type");
                    break;
                }
                
                float localUpperDept = (upperRowLength * dept) / width;
                float localBottomDept = (bottomRowLength * dept) / width;
                
                System.out.println("upperRowLength  " + upperRowLength);
                System.out.println("bottomRowLength  " + bottomRowLength);
                System.out.println("localUpperDept  " + localUpperDept);
                System.out.println("localBottomDept  " + localBottomDept);
                
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
            
            System.out.println();
        }
        
        return finalTriangles;
    }
    
    private LinkedList<LinkedList<Triangle>> copyLinkedList(LinkedList<LinkedList<Triangle>> list){
        
        LinkedList<LinkedList<Triangle>> retList = new LinkedList<LinkedList<Triangle>>();
        
        Iterator<LinkedList<Triangle>> rowIt = list.iterator();
        
            while ( rowIt.hasNext() ){
                
                LinkedList<Triangle> retRow = new LinkedList<Triangle>();

                LinkedList<Triangle> row = rowIt.next();

                Iterator<Triangle> triangleIt = row.iterator();
                //outerloop:
                while ( triangleIt.hasNext() ){

                    Triangle triangle = triangleIt.next();
                    
                    retRow.add(new Triangle(triangle));
                }
                
                retList.add(retRow);
            }
            
        return retList;
    }
    /*
    public void setFigureAreaPixels(LinkedList<LinkedList<Point>> pixels){
        
        this.pixels = pixels;
    }
    
    public void setFlatFigureTriangles(LinkedList<LinkedList<Triangle>> triangles){
        
        this.triangles = triangles;
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
*/
}
