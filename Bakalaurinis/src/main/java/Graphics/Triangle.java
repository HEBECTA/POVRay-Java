/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Graphics.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author GUGU
 */
public class Triangle {
    
    public Point p1, p2, p3;
    
    int r, g, b;
    
    public Triangle(Point p1, Point p2, Point p3){
        
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        
        r = 1;
        g = 1;
        b = 1;
    }
    
    public Triangle(Triangle t){
         
        this.p1 = new Point(t.p1);
        this.p2 = new Point(t.p2);
        this.p3 = new Point(t.p3);
        
        r = 1;
        g = 1;
        b = 1;
    }
    
    public void print(){
        
        System.out.println("p1 y " + p1.y + ", x " + p1.x + ", z " + p1.z + ", \np2 y " + p2.y + ", x " + p2.x + ", z " + p2.z + ", \np3 y " + p3.y + ", x " + p3.x + ", z " + p3.z);
    }
    
    public void setColor(int r, int g, int b){
        
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /*
    *******
     ******
      *****
       ****
        ***
         **
          *
    */
    public boolean topRight(){
        
        if ( p1.y == p2.y && p2.x == p3.x )
            return true;
        
        return false;   
    }
    
    /*
    *******
    ******
    *****
    ****
    ***
    **
    *
    */
    public boolean topLeft(){
        
        if ( p1.y == p2.y && p1.x == p3.x )
            return true;
        
        return false;   
    }
    
    public boolean botRight(){
        
        if ( p2.y == p3.y && p1.x == p3.x )
            return true;
        
        return false;   
    }
    
    public boolean botLeft(){
        
        if ( p2.y == p3.y && p1.x == p2.x )
            return true;
        
        return false;   
    }
    
    public void mirrorYfrom(){
        
        p1.y = -p1.y;
        p2.y = -p2.y;
        p3.y = -p3.y;
    }
    
    public static Triangle triangulateTopRight(int yy, int xx, int dimension, BufferedImage image, int color) {

        Triangle triangle = null;

        int i = 0;

        boolean topRightCorner = true;

        outerloop:
        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx + i; x < xx + dimension; ++x) {

                if (image.getRGB(x, y) != color) {

                    topRightCorner = false;
                    break outerloop;
                }
            }

            ++i;
        }

        if (topRightCorner) {

            triangle = new Triangle(new Point(yy, xx), new Point(yy, xx + dimension),
                    new Point(yy + dimension, xx + dimension));

            if (!triangle.topRight()) {

                System.out.println("Error, triangle not top right");
            }
        }

        return triangle;
    }
    
    public static Triangle triangulateTopLeft(int yy, int xx, int dimension, BufferedImage image, int color) {

        Triangle triangle = null;

        int i = 0;

        boolean topLeftCorner = true;

        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx; x < xx + dimension - i; ++x) {

                if (image.getRGB(x, y) != color) {

                    topLeftCorner = false;
                    break;
                }
            }

            ++i;
        }

        if (topLeftCorner) {

            triangle = new Triangle(new Point(yy, xx), new Point(yy, xx + dimension),
                    new Point(yy + dimension, xx));

            if (!triangle.topLeft()) {

                System.out.println("Error, triangle not top left");
            }
        }

        return triangle;
    }
    
    public static Triangle triangulateBotRight(int yy, int xx, int dimension, BufferedImage image, int color) {

        Triangle triangle = null;

        int i = dimension - 2;

        boolean botRightCorner = true;

        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx + dimension - 1; x > xx + i; --x) {

                if (image.getRGB(x, y) != color) {

                    botRightCorner = false;
                    break;
                }
            }

            --i;
        }

        if (botRightCorner) {

            triangle = new Triangle(new Point(yy, xx + dimension), new Point(yy + dimension, xx),
                    new Point(yy + dimension, xx + dimension));

            if (!triangle.botRight()) {

                System.out.println("Error, triangle not bot right");
            }
        }

        return triangle;
    }

    public static Triangle triangulateBotLeft(int yy, int xx, int dimension, BufferedImage image, int color) {

        Triangle triangle = null;

        int i = 1;

        boolean botLeftCorner = true;

        outerloop:
        for (int y = yy; y < yy + dimension; ++y) {

            for (int x = xx; x < xx + i; ++x) {

                if (image.getRGB(x, y) != color) {

                    botLeftCorner = false;
                    break outerloop;
                }
            }

            ++i;
        }

        if (botLeftCorner) {

            triangle = new Triangle(new Point(yy, xx), new Point(yy + dimension, xx),
                    new Point(yy + dimension, xx + dimension));

            if (!triangle.botLeft()) {

                System.out.println("Error, triangle not bot left");
            }
        }

        return triangle;
    }
}

