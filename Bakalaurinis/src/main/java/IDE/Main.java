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
    
    private final String inputImage = "/home/gugu/Pictures/bak/input.png";
    
    private final String contourImage = "/home/gugu/Pictures/bak/contour.png";
    private final String paintedImage = "/home/gugu/Pictures/bak/paintedImage.png";
    private final String flatAreaPixelsImage = "/home/gugu/Pictures/bak/flatAreaPixels.png";
    private final String flatTriangulatedImage = "/home/gugu/Pictures/bak/flatTriangulated.png";
    private final String inflatedTriangulatedImage = "/home/gugu/Pictures/bak/inflatedTriangulated.png";
    
    private final String pixelsImagePovCode = "/home/gugu/Pictures/bak/contour.pov";
    private final String flatAreaPixelsPovCode = "/home/gugu/Pictures/bak/flatAreaPixels.pov";
    private final String filledImagePovCode = "/home/gugu/Pictures/bak/flatTriangulated.pov";
    private final String inflatedImagePovCode = "/home/gugu/Pictures/bak/inflatedTriangulated.pov";
    
    JPanel povRaySettingsTab;
    JPanel inputImageTab;
    JPanel paintedImageTab;
    JPanel contourImageTab;
    JPanel flatAreaPixelsImageTab;
    JPanel flatTriangulatedImageTab;
    JPanel inflatedTriangulatedImageTab;
    
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
        frame.setSize(1440, 900);
        frame.setTitle("POV Ray");
        frame.setLocationRelativeTo(null);
        
        fileHandler = new FileOperation("/home/gugu/stuff/povray");
        
        
        menu = new Menu(this, fileHandler);
        toolBar = new Toolbar(this, fileHandler);
        
        
        povRaySettingsTab = new PovRaySettings();
        inputImageTab = new DisplayImage(inputImage);
        paintedImageTab = new DisplayImage(paintedImage);
        contourImageTab = new DisplayImage(contourImage);
        flatAreaPixelsImageTab = new DisplayImage(flatAreaPixelsImage);
        flatTriangulatedImageTab = new DisplayImage(flatTriangulatedImage);
        inflatedTriangulatedImageTab = new DisplayImage(inflatedTriangulatedImage);
        tabbedPane = new JTabbedPane();
        
        tabbedPane.add("Pov Ray scene settings", povRaySettingsTab);
        tabbedPane.add("preview input image", inputImageTab);
        tabbedPane.add("preview painted image", paintedImageTab);
        tabbedPane.add("preview contour pixels", contourImageTab);
        tabbedPane.add("preview flat area pixels", flatAreaPixelsImageTab);
        tabbedPane.add("preview flat triangulated object", flatTriangulatedImageTab);
        tabbedPane.add("preview inflated triangulated object", inflatedTriangulatedImageTab);
        
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
            
            
            int traingleSize = 10;
            int inflationDepth = 80;
            data = imageScanner.getFigureData(traingleSize);
 
            generator3D.setFigureData(data);
            //data.inflatedTriangles = generator3D.getInflatedFigure(inflationDepth);
            data.inflatedFigureAreaPixels = generator3D.getInflatedPixelsList(inflationDepth);
            data.translateFigureData(0, 0, 0);
            
            sceneWriter.setFigureData(data);
            sceneWriter.setCameraSettings(cameraSettings);
            sceneWriter.setLightSettings(lightSettings);
            sceneWriter.setFloorSettings(floorSettings);
            sceneWriter.setTransformationSettings(transformationSettings);
            
            sceneWriter.generateFigureContourScene(pixelsImagePovCode, contourImage, 0.5f);
            sceneWriter.generateFigureAreaPixelsScene(flatAreaPixelsPovCode, flatAreaPixelsImage, 0.5f);
            //sceneWriter.generateFlatTrianglesScene(filledImagePovCode, flatTriangulatedImage);
            //sceneWriter.generateInflatedTrianglesScene(inflatedImagePovCode, inflatedTriangulatedImage);
            
            inputImageTab.repaint();
            paintedImageTab.repaint();
            contourImageTab.repaint();
            flatAreaPixelsImageTab.repaint();
            //flatTriangulatedImageTab.repaint();
            //inflatedTriangulatedImageTab.repaint();
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Main: refreshTabs exception");
        }
    }
}
