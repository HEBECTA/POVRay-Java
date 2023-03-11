/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author gugu
 */
public class PovRaySettings extends JPanel {
    
    JLabel cameraLabel;
    JLabel lightLabel;
    JLabel floorLabel;
    JLabel transformationLabel;
    
    public static JTextArea cameraText;
    public static JTextArea lightText;
    public static JTextArea floorText;
    public static JTextArea transformationText;
    
    JPanel cameraPanel;
    JPanel lightPanel;
    JPanel floorPanel;
    JPanel transformationPanel;
      
    public PovRaySettings(){
        
        
        this.cameraLabel = new JLabel("Camera location");
        this.lightLabel = new JLabel("Light");
        this.floorLabel = new JLabel("Floor");
        this.transformationLabel = new JLabel("Transformation");
        
        this.cameraText = new JTextArea();
        this.lightText = new JTextArea();
        this.floorText = new JTextArea();
        this.transformationText = new JTextArea();
        
        cameraText.setText("camera{ location  <0,0, -800>\n  angle 40\nright     x*image_width/image_height\nlook_at   <0,0,0>\n}");
        lightText.setText("light_source {<-140,200, 300> rgb <1.0, 1.0, 0.95>*1.5}\nlight_source {< 140,200,-300> rgb <0.9, 0.9, 1.00>*0.9 shadowless}");
        floorText.setText("#declare Floor_Texture =\ntexture { pigment { P_WoodGrain18A color_map { M_Wood18A }}}\ntexture { pigment { P_WoodGrain12A color_map { M_Wood18B }}}\ntexture {\npigment { P_WoodGrain12B color_map { M_Wood18B }}\nfinish { reflection 0.25 }\n}\n#declare Floor =\nplane { y,0\ntexture { Floor_Texture\nscale 0.5\nrotate y*90\nrotate <10, 0, 15>\ntranslate z*4\n}}");
        transformationText.setText("texture {\n" +
        "      pigment { color rgb<0.9, 0.9, 0.9> }\n" +
        "      finish { ambient 0.2 diffuse 0.7 }\n" +
        "    }\n" +
        "rotate <0, 0, 0>");

        setLayout(new GridLayout(1, 4, 20, 20));
        
        
        cameraPanel = new JPanel();
        lightPanel = new JPanel();
        floorPanel = new JPanel();
        transformationPanel = new JPanel();
        
        cameraPanel.setLayout(new BorderLayout());
        lightPanel.setLayout(new BorderLayout());
        floorPanel.setLayout(new BorderLayout());
        transformationPanel.setLayout(new BorderLayout());
        
        cameraPanel.add(cameraLabel, BorderLayout.NORTH);
        cameraPanel.add(cameraText);
        lightPanel.add(lightLabel, BorderLayout.NORTH);
        lightPanel.add(lightText);
        floorPanel.add(floorLabel, BorderLayout.NORTH);
        floorPanel.add(floorText);
        transformationPanel.add(transformationLabel, BorderLayout.NORTH);
        transformationPanel.add(transformationText);
        
        this.add(cameraPanel);
        this.add(lightPanel);
        this.add(floorPanel);
        this.add(transformationPanel);
    }
    
}
