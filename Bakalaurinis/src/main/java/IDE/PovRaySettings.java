/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;


import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author gugu
 */
public class PovRaySettings extends JPanel {
    
    JLabel cameraLocationLabel;
    JLabel cameraAngleLabel;
    JLabel cameraLookAtLabel;
    JLabel lightLabel;
    JLabel floorLabel;
    
    public static JTextField cameraLocationText;
    public static JTextField cameraAngleText;
    public static JTextField cameraLookAtText;
    public static JTextField lightText;
    public static JTextField floorText;
    
    public PovRaySettings(){
        
        
        this.cameraLocationLabel = new JLabel("Camera location");
        //this.cameraAngleLabel = new JLabel("Camera angle");
        //this.cameraLookAtLabel = new JLabel("Camera lookt at");
        
        this.lightLabel = new JLabel("Light");
        this.floorLabel = new JLabel("Floor");
        
        this.cameraLocationText = new JTextField(16);
        //this.cameraAngleText = new JTextField(16);
        //this.cameraLookAtText = new JTextField(16);
        this.lightText = new JTextField(16);
        this.floorText = new JTextField(16);
        
        //this.cameraText.setBounds(100, 50, 120, 30);
        //this.lightText.setBounds(20, 10, 12, 3);
        //this.floorText.setBounds(100, 50, 120, 30);
        //this.text.setSize(30, 15);
        
        setLayout(new GridLayout(0, 1));
        
        
        this.add(cameraLocationLabel);
        this.add(cameraLocationText);
        //this.add(cameraAngleLabel);
        //this.add(cameraAngleText);
        //this.add(cameraLookAtLabel);
        //this.add(cameraLookAtText);
        
        this.add(lightLabel);
        this.add(lightText);
        
        this.add(floorLabel);
        this.add(floorText);
    }
    
}
