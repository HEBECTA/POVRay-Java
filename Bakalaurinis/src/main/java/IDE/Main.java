/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

/**
 *
 * @author GUGU
 */
import Engine.SceneGenerator;
import Graphics.ImageScanner;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;



public class Main {
     
    private JFrame frame;
    private JMenuBar menu;
    private JToolBar toolBar;
    public JTabbedPane tabbedPane;
    
    FileOperation fileHandler;
    
    private final String inputImage = "/home/gugu/Pictures/bak/input.jpg";
    private final String pixelsImage = "/home/gugu/Pictures/bak/pixels.png";
    private final String finalImage = "/home/gugu/Pictures/bak/final.png";
    
    private final String pixelsImagePovCode = "/home/gugu/Pictures/bak/pixels.pov";
    //private final String finalImagePovCode = "/home/gugu/Pictures/bak/final.pov";
    
    JPanel povRaySettingsTab;
    JPanel paintImageTab;
    JPanel pixelsImageTab;
    JPanel finalImageTab;
    
    ImageScanner imageScanner;
    SceneGenerator generator;
    
    Main() throws IOException{
        
        frame = new JFrame("Pov Ray");
        
        File imageFile = new File(inputImage);
        imageScanner = new ImageScanner(imageFile);
        
        generator = new SceneGenerator();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 650);
        frame.setTitle("POV Ray");
        frame.setLocationRelativeTo(null);
        
        fileHandler = new FileOperation("/home/gugu/stuff/povray");
        
        
        menu = new Menu(this, fileHandler);
        toolBar = new Toolbar(this, fileHandler);
        
        
        povRaySettingsTab = new PovRaySettings();
        paintImageTab = new DisplayImage(inputImage);
        pixelsImageTab = new DisplayImage(pixelsImage);
        finalImageTab = new DisplayImage(finalImage);
        tabbedPane = new JTabbedPane();
        
        tabbedPane.add("Pov Ray scene settings", povRaySettingsTab);
        tabbedPane.add("preview painted image", paintImageTab);
        tabbedPane.add("preview generated pixels", pixelsImageTab);
        tabbedPane.add("preview final generated object", finalImageTab);
        
        frame.setJMenuBar(menu);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(tabbedPane);
        
        ImageIcon img = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/Povray.png");
       
        frame.setIconImage(img.getImage());
        frame.setVisible(true);
    }
    
    public static void main(String[] args){

        try {
            new Main();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void createWindow(){
        
        //button = new JButton("CLick me");
        //button.addActionListener(this);
        //label = new JLabel("zero");
        //panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        //panel.setLayout(new GridLayout(0, 1));
        //panel.add(button);
        //panel.add(label);
        //frame.add(panel, BorderLayout.CENTER);
    }

    
    public void refreshTabs(){
        
        try {
            
            String cameraSettings = PovRaySettings.cameraText.getText();
            String floorSettings = PovRaySettings.floorText.getText();
            String lightSettings = PovRaySettings.lightText.getText();
            String transformationSettings = PovRaySettings.transformationText.getText();
            
            if (imageScanner.imageUpdated()){
                
                generator.setPixels(imageScanner.getFigurePixels());
            }
            
            generator.setCameraSettings(cameraSettings);
            generator.setLightSettings(lightSettings);
            generator.setFloorSettings(floorSettings);
            generator.setTransformationSettings(transformationSettings);
            generator.generatePixelsScene(pixelsImagePovCode, pixelsImage, 0.5f);
            
            paintImageTab.repaint();
            pixelsImageTab.repaint();
            //finalImageTab.repaint();
            
        } catch (Exception e){
            
        }
    }
}
