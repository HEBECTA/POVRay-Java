/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
/**
 *
 * @author gugu
 */
public class DisplayImage extends JPanel{
    
    JLabel lbl;
    ImageIcon icon;
    BufferedImage img;
    File file;
    String imagePath;
    int input = -1;
    
    public DisplayImage(int input) throws IOException{
        
        this.input = input;
    }
    
    public DisplayImage(String imagePath) throws IOException{
        
        this.imagePath = imagePath;
        this.file = new File(imagePath);
        
        if (!file.exists())
        {
            System.out.println(imagePath + " doens't exist");
            file = null;
        }
        
        else
        {
            this.img = ImageIO.read(this.file);
            this.icon = new ImageIcon(img);
            this.lbl = new JLabel();
            this.lbl.setIcon(this.icon);
            this.add(this.lbl);
            this.setVisible(true);
        }
    }
    
    public void setImage(String imagePath) throws IOException{
        
        this.imagePath = imagePath;
        this.file = new File(imagePath);
        
        if (!file.exists())
        {
            System.out.println(imagePath + " doens't exist");
            file = null;
        }
        
        else
        {
            this.img = ImageIO.read(this.file);
            this.icon = new ImageIcon(img);
            this.lbl = new JLabel();
            this.lbl.setIcon(this.icon);
            this.add(this.lbl);
            this.setVisible(true);
        }
    }
    
    public void Refresh()throws IOException {
        
        if (input == 1){
            
            this.file = Main.firstMethod1File;
        }
        
        else if (input == 2){
            
            this.file = Main.firstMethod2File;
        }
        
        else if (input == 3){
            
            this.file = Main.secondMethodFile;
        }
        
        if (file != null){
            
            this.file = new File(imagePath);
            if (!file.exists())
                return;
            
            this.img = ImageIO.read(this.file);
            this.icon = new ImageIcon(this.img);
            this.lbl.setIcon(icon);
            //this.add(this.lbl);
            //this.setVisible(true);
        }
        else
            return;
        
        
        //this.lbl.setIcon(this.icon);
        //this.add(this.lbl);
        //this.setVisible(true);

    }

    @Override
    public void repaint() {
        try {
            Refresh();
            revalidate();
            //if (file != null)
                //System.out.println(this.getName() + " " + file.getName());
            super.repaint(); //To change body of generated methods, choose Tools | Templates.

        } catch (IOException ex) {
            Logger.getLogger(DisplayImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
