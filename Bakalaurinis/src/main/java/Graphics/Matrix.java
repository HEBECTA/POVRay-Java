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
public class Matrix {
    
    static int dimension = 4;
    public float M[][];
    
    public Matrix(){
        
        M = new float[dimension][dimension];
        
        M[0][0] = 1;
        M[1][1] = 1;
        M[2][2] = 1;
        M[3][3] = 1;
    }
    
    // zero matrix, dump function argument
    private Matrix(int digit){
        
        M = new float[dimension][dimension];
    }

    public void multiply(Matrix m){

        Matrix answ = new Matrix(0);
        
        for (int y = 0; y < dimension; ++y){
            
            for (int x = 0; x < dimension; ++x){

                for (int i = 0; i < dimension; ++i){

                    answ.M[y][x] += M[y][i] * m.M[i][x];
                }
            }
        }

        M = answ.M;
    }
    
    public void translate(int x, int y, int z){
        
        Matrix tmp = new Matrix();
        tmp.M[0][3] = x;
        tmp.M[1][3] = y;
        tmp.M[2][3] = z;
        
        tmp.multiply(tmp);
    }
    
    public void scale(float x, float y, float z){
        
        Matrix tmp = new Matrix();
        tmp.M[0][0] = x;
        tmp.M[1][1] = y;
        tmp.M[2][2] = z;
        
        this.multiply(tmp);
    }
    
    public void rotateX(double radians){
        
        double sinL = Math.sin(radians);
        double cosL = Math.cos(radians);
        
        Matrix tmp = new Matrix();
        tmp.M[1][1] = (float)cosL;
        tmp.M[1][2] = (float)-sinL;
        tmp.M[2][1] = (float)sinL;
        tmp.M[2][2] = (float)cosL;

        this.multiply(tmp);
    }
    
    public void rotateY(double radians){
        
        double sinL = Math.sin(radians);
        double cosL = Math.cos(radians);
        
        Matrix tmp = new Matrix();
        tmp.M[0][0] = (float)cosL;
        tmp.M[0][2] = (float)sinL;
        tmp.M[2][0] = (float)-sinL;
        tmp.M[2][2] = (float)cosL;
        
        this.multiply(tmp);
    }
    
    public void rotateZ(double radians){
        
        double sinL = Math.sin(radians);
        double cosL = Math.cos(radians);
        
        Matrix tmp = new Matrix();
        tmp.M[0][0] = (float)cosL;
        tmp.M[0][1] = (float)-sinL;
        tmp.M[1][0] = (float)sinL;
        tmp.M[1][1] = (float)cosL;
        
        this.multiply(tmp);
    }
    
    public void printMatrix(){
        
        System.out.println("Matrix:");
        
        for (int y = 0; y < dimension; ++y){
            
            for (int x = 0; x < dimension; ++x){
                
                System.out.print(" " + M[y][x]);
            }
            
            System.out.println("");
        }
    } 
}

