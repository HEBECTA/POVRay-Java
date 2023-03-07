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
import Graphics.FigureData;
import Graphics.ImageScanner;
import Graphics.Object3D;
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
    
    private final String inputImage = "/home/gugu/Pictures/bak/kursinis.png";
    private final String pixelsImage = "/home/gugu/Pictures/bak/pixels.png";
    private final String paintedImage = "/home/gugu/Pictures/bak/paintedImage.png";
    private final String filledImage = "/home/gugu/Pictures/bak/filled.png";
    private final String inflatedImage = "/home/gugu/Pictures/bak/inflated.png";
    
    private final String pixelsImagePovCode = "/home/gugu/Pictures/bak/pixels.pov";
    private final String filledImagePovCode = "/home/gugu/Pictures/bak/filled.pov";
    private final String inflatedImagePovCode = "/home/gugu/Pictures/bak/inflated.pov";
    
    JPanel povRaySettingsTab;
    JPanel paintImageTab;
    JPanel paintedImageTab;
    JPanel pixelsImageTab;
    JPanel filledImageTab;
    JPanel inflatedImageTab;
    
    ImageScanner imageScanner;
    SceneGenerator sceneWriter;
    Object3D generator3D;
    
    Main() throws IOException{
        
        frame = new JFrame("Pov Ray");
        
        File imageFile = new File(inputImage);
        imageScanner = new ImageScanner(imageFile);
        
        sceneWriter = new SceneGenerator();
        generator3D = new Object3D();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 650);
        frame.setTitle("POV Ray");
        frame.setLocationRelativeTo(null);
        
        fileHandler = new FileOperation("/home/gugu/stuff/povray");
        
        
        menu = new Menu(this, fileHandler);
        toolBar = new Toolbar(this, fileHandler);
        
        
        povRaySettingsTab = new PovRaySettings();
        paintImageTab = new DisplayImage(inputImage);
        paintedImageTab = new DisplayImage(paintedImage);
        pixelsImageTab = new DisplayImage(pixelsImage);
        filledImageTab = new DisplayImage(filledImage);
        inflatedImageTab = new DisplayImage(inflatedImage);
        tabbedPane = new JTabbedPane();
        
        tabbedPane.add("Pov Ray scene settings", povRaySettingsTab);
        tabbedPane.add("preview paint image", paintImageTab);
        tabbedPane.add("preview painted image", paintedImageTab);
        tabbedPane.add("preview generated pixels", pixelsImageTab);
        tabbedPane.add("preview filled object", filledImageTab);
        tabbedPane.add("preview inflated object", inflatedImageTab);
        
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
            
            FigureData data = new FigureData();
            
            String cameraSettings = PovRaySettings.cameraText.getText();
            String floorSettings = PovRaySettings.floorText.getText();
            String lightSettings = PovRaySettings.lightText.getText();
            String transformationSettings = PovRaySettings.transformationText.getText();
                   
            if (!imageScanner.refreshImage()){
                    
                System.out.println("imageScanner.refreshImage failed !");
                return;
            }
            imageScanner.exportPaintedFigure(paintedImage);
            int traingleSize = 2;
            int inflationDepth = 30;
            data = imageScanner.getFigureData(traingleSize);
            
            imageScanner.printTriangles(data.flatTriangles);
            
            generator3D.setFigureData(data);
            data.inflatedTriangles = generator3D.getInflatedFigure(inflationDepth);
            imageScanner.printTriangles(data.flatTriangles);
            sceneWriter.setFigureData(data);
            
            sceneWriter.setCameraSettings(cameraSettings);
            sceneWriter.setLightSettings(lightSettings);
            sceneWriter.setFloorSettings(floorSettings);
            sceneWriter.setTransformationSettings(transformationSettings);
            sceneWriter.generatePixelsScene(pixelsImagePovCode, pixelsImage, 0.5f);
            sceneWriter.generateFlatFigureScene(filledImagePovCode, filledImage);
            sceneWriter.generateInflatedFigureScene(inflatedImagePovCode, inflatedImage);
            
            System.out.println("Tas pats triangles");
            imageScanner.printTriangles(sceneWriter.figureData.flatTriangles);
            
            paintImageTab.repaint();
            paintedImageTab.repaint();
            pixelsImageTab.repaint();
            filledImageTab.repaint();
            inflatedImageTab.repaint();
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Main: refreshTabs exception");
        }
    }
}
