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
public class Point {
    
    public Point(Point p){
        
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
    
    public Point(float x, float y){
            
            this.x = x;
            this.y = y;
            this.z = 0;
        }
    
    public Point(float x, float y, float z){
            
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        //public boolean first;
        //public boolean last;
        public int row;
        public float x;
        public float y;
        public float z;
}
