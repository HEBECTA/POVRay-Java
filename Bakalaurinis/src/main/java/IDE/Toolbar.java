/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import OldMethod.Object;
import OldMethod2.ImageScanner;
//import OldMethod2.Object;

/**
 *
 * @author GUGU
 */
import Graphics.Matrix;
//import Graphics.ImageScanner;
import Graphics.Object3D;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 *
 * @author gugu
 */
public class Toolbar extends JToolBar implements ActionListener {

    JPanel panel;
    
    JButton newButton;
    JButton openButton;
    //JButton saveButton;
    //JButton closeButton;
    
    //JButton povRaySceneSettingsBtn;
    //JButton paintedImgBtn;
    JButton firstAlg2;
    JButton firstAlg1;
    JButton secondAlg;
    
    ImageIcon refreshIcon;
    /*
    ImageIcon openIcon;
    ImageIcon floppyIcon;
    ImageIcon closeIcon;
    
    ImageIcon povRaySceneSettingsIcon;
    ImageIcon paintedImgIcon;
    ImageIcon showPixelsIcon;
    ImageIcon showFinalIcon;
    ImageIcon refreshIcon;
    */
    JLabel inflationLabel;
    public static JTextField inflationDept;
    
    JLabel reliefIterationLabel;
    public static JTextField topHillIteration;
    
    JLabel linePrecisionLabel;
    public static JTextField topHillPrecision;

    JLabel triangulationPrecisionLabel;
    public static JTextField triangulationPrecision;
    
    Main mainWindow;
    FileOperation fileHandler;        
    
    //JPanel algorithmSettings;
    
    Toolbar(Main mainWindow, FileOperation fileHandler){
        
        this.mainWindow = mainWindow;
        this.fileHandler = fileHandler;
        
        int dimensions = 24;
        
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        //GridLayout layout = new GridLayout(2, 3);
        

        
        //algorithmSettings = new JPanel(new GridLayout(0, 2));
        
        refreshIcon = new ImageIcon("../src/main/java/icons/refresh.png");  

                                    
        //newButton = new JButton(new ImageIcon(getScaledImage(newIcon.getImage(), dimensions, dimensions)));
        //openButton = new JButton(new ImageIcon(getScaledImage(openIcon.getImage(), dimensions, dimensions)));
        //saveButton = new JButton(new ImageIcon(getScaledImage(floppyIcon.getImage(), dimensions, dimensions)));
        //closeButton = new JButton(new ImageIcon(getScaledImage(closeIcon.getImage(), dimensions, dimensions)));
        
        inflationLabel = new JLabel("Inflation dept");
        inflationDept = new JTextField(4);
        inflationDept.setText("40");
        
        reliefIterationLabel = new JLabel("Relief iteration interval");
        topHillIteration = new JTextField(4);
        topHillIteration.setText("17");
        
        linePrecisionLabel = new JLabel("Line generating two points precision distance");
        topHillPrecision = new JTextField(4);
        topHillPrecision.setText("5");
        
        triangulationPrecisionLabel = new JLabel("Triangulation precision");
        triangulationPrecision = new JTextField(4);
        triangulationPrecision.setText("5");
        
        
        //povRaySceneSettingsBtn = new JButton(new ImageIcon(getScaledImage(povRaySceneSettingsIcon.getImage(), dimensions, dimensions)));
        //paintedImgBtn = new JButton(new ImageIcon(getScaledImage(paintedImgIcon.getImage(), dimensions, dimensions)));
        firstAlg2 = new JButton(new ImageIcon(getScaledImage(refreshIcon.getImage(), dimensions, dimensions)));
        firstAlg1 = new JButton(new ImageIcon(getScaledImage(refreshIcon.getImage(), dimensions, dimensions)));
        secondAlg = new JButton(new ImageIcon(getScaledImage(refreshIcon.getImage(), dimensions, dimensions)));
      
        //newButton.setText("New");
        //openButton.setText("Open");
        //saveButton.setText("Save");
        //closeButton.setText("Close");
        
        //povRaySceneSettingsBtn.setText("scene");
        //paintedImgBtn.setText("image");       
        firstAlg2.setText("first method 2");      
        firstAlg1.setText("first method 1");        
        secondAlg.setText("second method");
                
        //newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        //newButton.setHorizontalTextPosition(SwingConstants.CENTER);
    
        //openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        //openButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        //saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        //saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        //closeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        //closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        /*
        povRaySceneSettingsBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        povRaySceneSettingsBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        paintedImgBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        paintedImgBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        */
        firstAlg2.setVerticalTextPosition(SwingConstants.BOTTOM);
        firstAlg2.setHorizontalTextPosition(SwingConstants.CENTER);
       
        firstAlg1.setVerticalTextPosition(SwingConstants.BOTTOM);
        firstAlg1.setHorizontalTextPosition(SwingConstants.CENTER);

        secondAlg.setVerticalTextPosition(SwingConstants.BOTTOM);
        secondAlg.setHorizontalTextPosition(SwingConstants.CENTER);
        
        //newButton.addActionListener(this);
        //openButton.addActionListener(this);
        //saveButton.addActionListener(this);
        //closeButton.addActionListener(this);
        
        //povRaySceneSettingsBtn.addActionListener(this);
        //paintedImgBtn.addActionListener(this);
        firstAlg2.addActionListener(this);
        firstAlg1.addActionListener(this);
        secondAlg.addActionListener(this);
        
        //panel.add(newButton);
        //panel.add(openButton);
        //panel.add(saveButton);
        //panel.add(closeButton);
      
        //panel.add(povRaySceneSettingsBtn);
        //panel.add(paintedImgBtn);
        /*
        algorithmSettings.add(inflationLabel);
        algorithmSettings.add(inflationDept);
        
        algorithmSettings.add(reliefIterationLabel);
        algorithmSettings.add(topHillIteration);
        
        algorithmSettings.add(linePrecisionLabel);
        algorithmSettings.add(topHillPrecision);
        
        algorithmSettings.add(triangulationPrecisionLabel);
        algorithmSettings.add(triangulationPrecision);
*/
        
        panel.add(firstAlg1);
        panel.add(firstAlg2);
        panel.add(secondAlg);
        
        //panel.add(algorithmSettings);
        
        
        panel.add(inflationLabel);
        panel.add(inflationDept);
        
        panel.add(reliefIterationLabel);
        panel.add(topHillIteration);
        
        panel.add(linePrecisionLabel);
        panel.add(topHillPrecision);
        
        panel.add(triangulationPrecisionLabel);
        panel.add(triangulationPrecision);
        
       
        
        this.add(panel);
        this.setFloatable(false);
    }
    
    private Image getScaledImage(Image srcImg, int w, int h){
        
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        String cmdText = ae.getActionCommand();
        
        if (cmdText.equals("New")){
            /*
            fileHandler.newFile();
            
            mainWindow.tabbedPane.setTitleAt(0, "Untitled !");*/
        }
        
        else if (cmdText.equals("Open")) {
            /*
            File file = fileHandler.openFile();
            
            mainWindow.tabbedPane.setTitleAt(mainWindow.tabbedPane.getSelectedIndex(), file.getName());
            
            CodeArea txt = (CodeArea)mainWindow.tabbedPane.getSelectedComponent();
            
            try {
            Scanner scanner = new Scanner(file);
            
            txt.setAbsolutePath(file.getAbsolutePath());
            
            while (scanner.hasNextLine()){
                txt.loadText(scanner.nextLine());
                //txt.loadText("\n");
            }
            }
            catch ( Exception ex) {}
            
            mainWindow.tabbedPane.setSelectedComponent(txt);*/
        }
        
        else if ( cmdText.equals("Save") ){
            
            //CodeArea txt = (CodeArea)mainWindow.tabbedPane.getSelectedComponent();
            
            //fileHandler.saveFile(txt.getName(), txt.textArea.getText());
            /*
            try {
                
                
            
                File file = new File(txt.getName());

                FileWriter fileWriter = new FileWriter(file);

                fileWriter.write(txt.textArea.getText());
            }
            
            catch ( Exception e){}*/
            
            
        }
        
        else if ( cmdText.equals("Close") ){
            
        }
        
        else if ( cmdText.equals("scene") ){
           
        }
        
        else if ( cmdText.equals("first method 2") ){
            
            String cameraSettings = PovRaySettings.cameraText.getText();
            String floorSettings = PovRaySettings.floorText.getText();
            String lightSettings = PovRaySettings.lightText.getText();
            String transformationSettings = PovRaySettings.transformationText.getText();
            
            //JFileChooser f = new JFileChooser("C:\\Users\\GUGU\\Pictures\\_kursinis.png");
            
            //f.showOpenDialog(null);
            
            //File file = f.getSelectedFile();
            
            if (Main.firstMethod2File == null)
                return;
            
            File file = Main.firstMethod2File;
            
            try {
            
                OldMethod2.ImageScanner image = new ImageScanner(file);
                
                //*****image.exportFillPovRay("/home/gugu/Pictures/bak/firstMethod2Fill.pov", "/home/gugu/Pictures/bak/firstMethod2Fill.png", 0.7f);
                
                OldMethod2.Object obj = new OldMethod2.Object(image.getTriangulatedObject(10), image.width);
                
                OldMethod2.Matrix transformationMatrix = new OldMethod2.Matrix();
                
                obj.setSceneSettings(cameraSettings, lightSettings, floorSettings, transformationSettings);
                
                String inflationDept = Toolbar.inflationDept.getText();
                int inflationDepth = Integer.parseInt(inflationDept);
                
                obj.inflate((float)inflationDepth);
                
                transformationMatrix.scale(0.25f, 0.25f, 0.25f);
                transformationMatrix.rotateX((float)Math.toRadians(180));
                //transformationMatrix.rotateY((float)Math.toRadians(90));
                 
                
                
                obj.transformation(transformationMatrix);
                obj.transformationTest(transformationMatrix);
                
                //obj.printTriangles();
                
                //*****obj.exportPovRay("/home/gugu/Pictures/bak/firstMethod2Export.pov", "/home/gugu/Pictures/bak/firstMethod2Export.png");
                obj.exportPovRayTest(Main.povRayCodeBasePath + "firstMethod2.pov", Main.firstMethodImage, 0.5f);
                
                //obj.printTrianglesTest();
               
            }
            catch(Exception e){
                
                System.out.println("Toolbar Init Scanner exception");
                
                e.printStackTrace();
            }
        }
        
        else if ( cmdText.equals("first method 1") ){
            
            String cameraSettings = PovRaySettings.cameraText.getText();
            String floorSettings = PovRaySettings.floorText.getText();
            String lightSettings = PovRaySettings.lightText.getText();
            String transformationSettings = PovRaySettings.transformationText.getText();

            //JFileChooser f = new JFileChooser("C:\\Users\\GUGU\\Pictures\\_kursinis.png");
               
            //f.showOpenDialog(null);
            
            //File file = f.getSelectedFile();
            
            if (Main.firstMethod1File == null)
                return;
            
            File file = Main.firstMethod1File;
            
            try {
            
                OldMethod.Scanner scanner = new OldMethod.Scanner(file);
                
                scanner.scanImage2(30);
                
                //scanner.printPoints();
                
                //System.out.println("Scanner faces size " + scanner.getFaces().size());
                
                String inflationDept = Toolbar.inflationDept.getText();
                int inflationDepth = Integer.parseInt(inflationDept);
               
                OldMethod.Object obj = new OldMethod.Object(scanner.getFaces(), scanner.getMidPoint(), scanner.getWidth(), scanner.getHeight(), 2, inflationDepth);
                
                obj.setSettings(cameraSettings, lightSettings, floorSettings, transformationSettings);

                //obj.printMidPoint();
                
                //obj.printFaces();
                
                obj.detailedFaces3();
                
                //obj.setPosition(0, 0, 0); 
                
                obj.export(Main.povRayCodeBasePath + "firstMethod1.pov", Main.firstMethodImage);

                //obj.setPosition((int)(scanner.getMidPoint().x+100), (int)(scanner.getMidPoint().y+100), (int)(scanner.getMidPoint().z+100));
                
                //obj.setPosition(0, 0, 0); 
                
                //obj.detailedFaces3();
                
                //*****obj.exportPovRay("/home/gugu/Pictures/bak/firstMethod.pov", "/home/gugu/Pictures/bak/firstMethod123.png");
                
                //obj.export("translatedObject.txt");
                
                //System.out.println("After position changed");
                
                //obj.printFaces();
                
                //obj.exportSphere("taskai.txt");
                
                // ******obj.exportMiddlePoint("/home/gugu/Pictures/bak/firstMethodMiddlePoint.pov", "/home/gugu/Pictures/bak/firstMethodMiddlePoint.png", scanner.minLeft, scanner.maxRight, scanner.maxTop, scanner.minBot);
                
                //******obj.exportPerimeter("/home/gugu/Pictures/bak/firstMethodPerimiter.pov", "/home/gugu/Pictures/bak/firstMethodPerimeter.png");
                
                
            }
            catch(Exception e){
                
                System.out.println("Toolbar Init Scanner exception");
                
                //e.printStackTrace();
            }
               
               
        }
        
        else if ( cmdText.equals("rand") ){
            
            /*
            JFileChooser f = new JFileChooser("C:\\Users\\GUGU\\Pictures\\_kursinis.png");
            
            f.showOpenDialog(null);
            
            File file = f.getSelectedFile();
            
            try {
            
                ImageScanner image = new ImageScanner(file);
                
                image.exportFillPovRay("fill.txt", 0.7f);
                
                //image.exportPaintedFigure("painted.png");


               
            }
            catch(Exception e){
                
                System.out.println("Toolbar Init Scanner exception");
                
                e.printStackTrace();
            }
            */
        }
        
        else if ( cmdText.equals("second method") ){
            
            mainWindow.refreshTabs();
        }
    }
}
