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
import Graphics.Engine;
import Graphics.FigureData;
import Graphics.ImageScanner;
import Graphics.Object3D;
import static IDE.Toolbar.triangulationPrecision;
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
    
    static File firstMethod1File = null;
    static File firstMethod2File = null;
    static File secondMethodFile = null;
    
    
    private String paintedImage; // = "/home/gugu/Pictures/bak/paintedImage.png";
    //private final String gridImage = "/home/gugu/Pictures/bak/gridImage.png";
    
    //private final String inputImage = "/home/gugu/Pictures/bak/geras.png";
    
    //private final String contourImage = "/home/gugu/Pictures/bak/contour.png";
    private String flatAreaPixelsImage; // = "/home/gugu/Pictures/bak/flatAreaPixels.png";
    private String inflatedAreaPixelsImage; // = "/home/gugu/Pictures/bak/inflatedAreaPixels.png";
    //private final String flatTriangulatedImage = "/home/gugu/Pictures/bak/flatTriangulated.png";
    private String inflatedTriangulatedImage; // = "/home/gugu/Pictures/bak/inflatedTriangulated.png";
   // private final String gridColorImage = "/home/gugu/Pictures/bak/gridColor.png";
    static String firstMethodImage; // = "/home/gugu/Pictures/bak/firstMethod.png";
   // private final String flatTrianglesImage = "/home/gugu/Pictures/bak/flatTriangles.png";
    
    //private final String pixelsImagePovCode = "/home/gugu/Pictures/bak/contour.pov";
    private String flatAreaPixelsPovCode;// = "/home/gugu/Pictures/bak/flatAreaPixels.pov";
    private String inflatedAreaPixelsPovCode;// = "/home/gugu/Pictures/bak/inflatedAreaPixels.pov";
    //private final String filledImagePovCode = "/home/gugu/Pictures/bak/flatTriangulated.pov";
    private String inflatedImagePovCode;// = "/home/gugu/Pictures/bak/inflatedTriangulated.pov";
    //private final String gridColorImagePovCode = "/home/gugu/Pictures/bak/gridColor.pov";
    //private final String firstMethodImagePovCode = "/home/gugu/Pictures/bak/firstMethod.pov";
    //private final String flatTrianglesPovCode = "/home/gugu/Pictures/bak/flatTriangles.pov";
    
    JPanel povRaySettingsTab;
    JPanel inputImageTab;
    JPanel paintedImageTab;
    //JPanel contourImageTab;
    JPanel flatAreaPixelsImageTab;
    JPanel inflatedAreaPixelsImageTab;
    //JPanel flatTriangulatedImageTab;
    JPanel inflatedTriangulatedImageTab;
    //JPanel gridColorImageTab;
    JPanel firstMethodImageTab;
    
    ImageScanner imageScanner;
    SceneGenerator sceneWriter;
    //Object3D generator3D;
    Engine generator3D;
    
    String inputImagesBasePath;
    static String outputImagesBasePath;
    static String povRayCodeBasePath;
    
    Main() throws IOException{
        
        inputImagesBasePath = "../data/input-images/";
        outputImagesBasePath = "../data/output-images/";
        povRayCodeBasePath = "../data/generated-pov-ray/";
        
        paintedImage = outputImagesBasePath + "secondMethodPaintedImage.png";
        flatAreaPixelsImage = outputImagesBasePath + "flatAreaPixels.png";
        inflatedAreaPixelsImage = outputImagesBasePath + "inflatedAreaPixels.png";
        inflatedTriangulatedImage = outputImagesBasePath + "inflatedTrianglesPixels.png";
        firstMethodImage = outputImagesBasePath + "firstMethodImage.png";
        
        flatAreaPixelsPovCode = povRayCodeBasePath + "flatAreaPixels.pov";
        inflatedAreaPixelsPovCode = povRayCodeBasePath + "inflatedAreaPixels.pov";
        inflatedImagePovCode = povRayCodeBasePath + "inflatedTrianglesPixels.pov";
        
        frame = new JFrame("Pov Ray");
        
        //File imageFile = new File(inputImage);
        imageScanner = new ImageScanner();
        
        sceneWriter = new SceneGenerator();
        //generator3D = new Object3D();
        
        generator3D = new Engine();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 900);
        frame.setTitle("Figure inflation");
        frame.setLocationRelativeTo(null);
        
        fileHandler = new FileOperation(inputImagesBasePath);
        
        
        menu = new Menu(this, fileHandler);
        toolBar = new Toolbar(this, fileHandler);
        
        
        povRaySettingsTab = new PovRaySettings();
        //inputImageTab = new DisplayImage(inputImage);
        //inputImageTab = new DisplayImage(true);
        paintedImageTab = new DisplayImage(paintedImage);
        //contourImageTab = new DisplayImage(contourImage);
        flatAreaPixelsImageTab = new DisplayImage(flatAreaPixelsImage);
        inflatedAreaPixelsImageTab = new DisplayImage(inflatedAreaPixelsImage);
        //flatTriangulatedImageTab = new DisplayImage(flatTriangulatedImage);
        inflatedTriangulatedImageTab = new DisplayImage(inflatedTriangulatedImage);
        //gridColorImageTab = new DisplayImage(gridColorImage);
        firstMethodImageTab = new DisplayImage(firstMethodImage);
        tabbedPane = new JTabbedPane();
        
        tabbedPane.add("Pov-Ray scene settings", povRaySettingsTab);
        //tabbedPane.add("preview input image", inputImageTab);
        tabbedPane.add("Preview painted image", paintedImageTab);
        //tabbedPane.add("preview contour pixels", contourImageTab);
        //tabbedPane.add("preview color grid", gridColorImageTab);
        //tabbedPane.add("preview point grid", gridPointImageTab);
        tabbedPane.add("Preview flat area pixels", flatAreaPixelsImageTab);
        tabbedPane.add("Preview inflated area pixels", inflatedAreaPixelsImageTab);
        //tabbedPane.add("preview flat triangulated object", flatTriangulatedImageTab);
        tabbedPane.add("Preview inflated triangulated object", inflatedTriangulatedImageTab);
        
        tabbedPane.add("First method", firstMethodImageTab);
        
        
        frame.setJMenuBar(menu);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(tabbedPane);
        
        ImageIcon img = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/show.png");
       
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
            
            String inflationDept = Toolbar.inflationDept.getText();
            String iterationSize = Toolbar.topHillIteration.getText();
            String pointsPrecision = Toolbar.topHillPrecision.getText();
            String triangulationPrecision = Toolbar.triangulationPrecision.getText();
            
            if (secondMethodFile == null)
                return;
            
            imageScanner.setFile(secondMethodFile);

            if (!imageScanner.processImage()){
                    
                System.out.println("imageScanner.processImage failed !");
                return;
            }
            imageScanner.exportPaintedFigure(paintedImage);
            //imageScanner.exportFigureLevels(gridImage);
            
            
            
            FigureData data = imageScanner.getFigureData();
            
            data.flipPixels();
            //data.printPixelsCount();

            
            
            generator3D.setFigureData(data);
            int inflationDepth = Integer.parseInt(inflationDept);
            int iterationInterval = Integer.parseInt(iterationSize);
            int pointsPrecisionDistance = Integer.parseInt(pointsPrecision);
            int triangulationPrecisionDistance = Integer.parseInt(triangulationPrecision);
            
            //generator3D.InflatePixelsList(inflationDepth);
            //int triangulationDimension = 10;
            //generator3D.triangulateFigure(triangulationDimension);
            
            generator3D.inflateFigure((float)inflationDepth, iterationInterval, pointsPrecisionDistance, triangulationPrecisionDistance);
            //generator3D.exportImage(paintedImage);
            //generator3D.triangulateFigureOld();
            //generator3D.getFigurePixels();
            //generator3D.getFigureTriangles();

            //data.printInfo();
            
            //data.translateFigureData(0, 0, 0);
            
            sceneWriter.setCameraSettings(cameraSettings);
            sceneWriter.setLightSettings(lightSettings);
            sceneWriter.setFloorSettings(floorSettings);
            sceneWriter.setTransformationSettings(transformationSettings);
            
            
            data.translateFigureData();
            sceneWriter.setFigureData(data);
            
            //sceneWriter.generateFigureContourPixelsScene(pixelsImagePovCode, contourImage, 0.5f);
            
            
            sceneWriter.generateFigurePixels2Scene(flatAreaPixelsPovCode, flatAreaPixelsImage, 0.5f);
            sceneWriter.generateFigureInflatedPixelsScene(inflatedAreaPixelsPovCode, inflatedAreaPixelsImage, 0.5f);
            sceneWriter.generateFigureTrianglesScene(inflatedImagePovCode, inflatedTriangulatedImage);
            
            //sceneWriter.generateTempScene(pixelsImagePovCode, contourImage, 0.5f);
            
            //sceneWriter.generateTempScene(flatAreaPixelsPovCode, flatAreaPixelsImage, 0.5f);
            
            
            //sceneWriter.generateTempScene(flatTrianglesPovCode, flatTrianglesImage, inflationDepth);
            //sceneWriter.generateGridColorPreviewScene(gridColorImagePovCode, gridColorImage, 0.5f);
            //sceneWriter.generateGridPointPreviewScene(gridPointImagePovCode, gridPointImage, 0.5f);
            //sceneWriter.generateFlatTrianglesScene(flatTrianglesPovCode, flatTrianglesImage);

            
            //inputImageTab.repaint();
            paintedImageTab.repaint();
            //contourImageTab.repaint();
            flatAreaPixelsImageTab.repaint();
            inflatedAreaPixelsImageTab.repaint();
            //flatTriangulatedImageTab.repaint();
            inflatedTriangulatedImageTab.repaint();
            //gridColorImageTab.repaint();
            firstMethodImageTab.repaint();
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Main: refreshTabs exception");
        }
    }
}
