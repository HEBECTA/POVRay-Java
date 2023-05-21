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
public class Block {
    
    
    Segment firstSeg;
    Segment secondSeg;
    
    boolean jointBlock;
    boolean triangulated;
    
    LinkedList<Triangle> triangles;
    
    public Block(Segment s1, Segment s2){
        
        firstSeg = s1;
        secondSeg = s2;
    }
    
    public LinkedList<Triangle> triangulateBlock(){
        
        triangles = new LinkedList<>();
         
        Triangle triangle;
        if (firstSeg.negPoint != null && secondSeg.negPoint != null){
                    
            triangle = new Triangle(firstSeg.middPoint, firstSeg.negPoint, secondSeg.negPoint);
            triangles.add(triangle);
        }
                
        if (secondSeg.negPoint != null){
                    
            triangle = new Triangle(firstSeg.middPoint, secondSeg.middPoint, secondSeg.negPoint);
            triangles.add(triangle);       
        }
                
        if (firstSeg.posPoint != null && secondSeg.posPoint != null){
                    
            triangle = new Triangle(firstSeg.middPoint, firstSeg.posPoint, secondSeg.posPoint);
            triangles.add(triangle); 
        }
                
        if (secondSeg.posPoint != null){
                    
            triangle = new Triangle(firstSeg.middPoint, secondSeg.middPoint, secondSeg.posPoint);
            triangles.add(triangle);
        }
        
        return triangles;
    }
    
    public static LinkedList<Triangle> triangulateBetweenBlocks(Block b1, Block b2){
        
        Block block = new Block(b1.secondSeg, b2.firstSeg);
        
        return block.triangulateBlock();
    }
}
