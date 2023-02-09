/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

/**
 *
 * @author GUGU
 */
public class Point {
    
    public float y, x, z;
    
    public Point(float y, float x){
        
        this.y = y;
        this.x = x;
        this.z = 0;
    }
    
    public Point(float y, float x, float z){
        
        this.y = y;
        this.x = x;
        this.z = z;
    }
    
    public void matrixMult(Matrix m){
                 
        x = m.M[0][0] * x + m.M[0][1] * y + m.M[0][2] * z;
        y = m.M[1][0] * x + m.M[1][1] * y + m.M[1][2] * z;
        z = m.M[2][0] * x + m.M[2][1] * y + m.M[2][2] * z;
    }
}
