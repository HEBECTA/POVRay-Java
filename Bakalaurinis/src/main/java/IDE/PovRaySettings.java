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
import javax.swing.JTextField;

/**
 *
 * @author gugu
 */
public class PovRaySettings extends JPanel {
    
    JLabel cameraLabel;
    JLabel lightLabel;
    JLabel floorLabel;
    
    public static JTextArea cameraText;
    public static JTextArea lightText;
    public static JTextArea floorText;
    
    JPanel cameraPanel;
    JPanel lightPanel;
    JPanel floorPanel;
      
    public PovRaySettings(){
        
        
        this.cameraLabel = new JLabel("Camera location");
        this.lightLabel = new JLabel("Light");
        this.floorLabel = new JLabel("Floor");
        
        this.cameraText = new JTextArea();
        this.lightText = new JTextArea();
        this.floorText = new JTextArea();
        
        cameraText.setText("camera");
        lightText.setText("light");
        floorText.setText("floor");
        
        
        setLayout(new GridLayout(1, 3, 20, 20));
        
        
        cameraPanel = new JPanel();
        lightPanel = new JPanel();
        floorPanel = new JPanel();
        
        cameraPanel.setLayout(new BorderLayout());
        lightPanel.setLayout(new BorderLayout());
        floorPanel.setLayout(new BorderLayout());
        
        cameraPanel.add(cameraLabel, BorderLayout.NORTH);
        cameraPanel.add(cameraText);
        lightPanel.add(lightLabel, BorderLayout.NORTH);
        lightPanel.add(lightText);
        floorPanel.add(floorLabel, BorderLayout.NORTH);
        floorPanel.add(floorText);
        
        this.add(cameraPanel);
        this.add(lightPanel);
        this.add(floorPanel);

    }
    
}
