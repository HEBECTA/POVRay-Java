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
public class JointLine {
    
    LinkedList<Point> line;
    LinkedList<Block> blocks;
    
    public JointLine(){
        
        line = null;
        blocks = null;
    }
    
    public JointLine(LinkedList<Point> lines, LinkedList<Block> blocks){
        
        this.line = lines;
        this.blocks = blocks;
    }
}
