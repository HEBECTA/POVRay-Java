/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vu.mif.bakalaurinis;

/**
 *
 * @author GUGU
 */
import java.awt.FlowLayout;
import java.awt.Graphics2D;
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
import javax.swing.JPanel;
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
    JButton saveButton;
    JButton closeButton;
    JButton queueButton;
    JButton showButton;
    JButton iniButton;
    JButton sel_runButton;
    JButton runButton;
    JButton pauseButton;
    JButton trayButton;
    
    ImageIcon newIcon;
    ImageIcon openIcon;
    ImageIcon floppyIcon;
    ImageIcon closeIcon;
    ImageIcon queueIcon;
    ImageIcon showIcon;
    ImageIcon iniIcon;
    ImageIcon sel_runIcon;
    ImageIcon runIcon;
    ImageIcon pauseIcon;
    ImageIcon trayIcon;
    
    Main mainWindow;
    FileOperation fileHandler;        
    
    Toolbar(Main mainWindow, FileOperation fileHandler){
        
        this.mainWindow = mainWindow;
        this.fileHandler = fileHandler;
        
        int dimensions = 24;
        
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        newIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/file.png");  
        openIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/open.png");
        floppyIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/floppy.png");
        closeIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/close.png");
        queueIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/queue.png");
        showIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/show.png");
        iniIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/init.png");
        sel_runIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/sourcefile.bmp");
        runIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/run.png");
        pauseIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/pause.jpg");
        trayIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/tray.png");
                                    
        newButton = new JButton(new ImageIcon(getScaledImage(newIcon.getImage(), dimensions, dimensions)));
        openButton = new JButton(new ImageIcon(getScaledImage(openIcon.getImage(), dimensions, dimensions)));
        saveButton = new JButton(new ImageIcon(getScaledImage(floppyIcon.getImage(), dimensions, dimensions)));
        closeButton = new JButton(new ImageIcon(getScaledImage(closeIcon.getImage(), dimensions, dimensions)));
        queueButton = new JButton(new ImageIcon(getScaledImage(queueIcon.getImage(), dimensions, dimensions)));
        showButton = new JButton(new ImageIcon(getScaledImage(showIcon.getImage(), dimensions, dimensions)));
        iniButton = new JButton(new ImageIcon(getScaledImage(iniIcon.getImage(), dimensions, dimensions)));
        sel_runButton = new JButton(new ImageIcon(getScaledImage(sel_runIcon.getImage(), dimensions, dimensions)));
        runButton = new JButton(new ImageIcon(getScaledImage(runIcon.getImage(), dimensions, dimensions)));
        pauseButton = new JButton(new ImageIcon(getScaledImage(pauseIcon.getImage(), dimensions, dimensions)));
        trayButton = new JButton(new ImageIcon(getScaledImage(trayIcon.getImage(), dimensions, dimensions)));
      
        newButton.setText("New");
        openButton.setText("Open");
        saveButton.setText("Save");
        closeButton.setText("Close");
        queueButton.setText("Queue");
        showButton.setText("Show");       
        iniButton.setText("Ini");      
        sel_runButton.setText("Sel_Run");        
        runButton.setText("Run");        
        pauseButton.setText("Pause");        
        trayButton.setText("Tray");
                
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
    
        openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        openButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        closeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        queueButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        queueButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        showButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        showButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        iniButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        iniButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        sel_runButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        sel_runButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        runButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        runButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        pauseButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        pauseButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        trayButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        trayButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        newButton.addActionListener(this);
        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        queueButton.addActionListener(this);
        showButton.addActionListener(this);
        iniButton.addActionListener(this);
        sel_runButton.addActionListener(this);
        runButton.addActionListener(this);
        pauseButton.addActionListener(this);
        trayButton.addActionListener(this);
        
        panel.add(newButton);
        panel.add(openButton);
        panel.add(saveButton);
        panel.add(closeButton);
        panel.add(queueButton);
        panel.add(showButton);
        panel.add(iniButton);
        panel.add(sel_runButton);
        panel.add(runButton);
        panel.add(pauseButton);
        panel.add(trayButton);
        
       
        
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
            
            fileHandler.newFile();
            
            mainWindow.tabbedPane.setTitleAt(0, "Untitled !");
            
            
        }
        
        else if (cmdText.equals("Open")) {
            
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
            
            mainWindow.tabbedPane.setSelectedComponent(txt);
        }
        
        else if ( cmdText.equals("Save") ){
            
            CodeArea txt = (CodeArea)mainWindow.tabbedPane.getSelectedComponent();
            
            fileHandler.saveFile(txt.getName(), txt.textArea.getText());
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
        
        else if ( cmdText.equals("Queue") ){
           
        }
        else if ( cmdText.equals("Ini") ){
            
         
        }
        
        else if ( cmdText.equals("Sel_Run") ){
            
        }
        
        else if ( cmdText.equals("Run") ){
            
         
            
        }
        
        else if ( cmdText.equals("Pause") ){
            
            

        }
        
        else if ( cmdText.equals("Tray") ){
            
            JFileChooser f = new JFileChooser("C:\\Users\\GUGU\\Pictures\\_kursinis.png");
            
            f.showOpenDialog(null);
            
            File file = f.getSelectedFile();
            
            try {
            
                ImageScanner image = new ImageScanner(file);
                
                image.exportFillPovRay("fill.txt", 0.7f);
                
                image.exportPaintedFigure("painted.png");
                
                Object3D obj = new Object3D(image.getTriangulatedObject(5), image.width);
                
                
                Matrix transformationMatrix = new Matrix();
                
                obj.inflate((image.height + image.width)/2);
                
                //transformationMatrix.scale(0.25f, 0.25f, 0.25f);
                transformationMatrix.rotateX((float)Math.toRadians(180));
                //transformationMatrix.rotateY((float)Math.toRadians(90));
                 
                
                
                //obj.transformation(transformationMatrix);
                //obj.transformationTest(transformationMatrix);
                
                //obj.printTriangles();
                
                obj.exportPovRay("Flat.txt");
                obj.exportPovRayTest("FlatTest.txt", 0.5f);
                
                obj.exportTriangulated("triangles.txt", 0.2f);
                
                //obj.printTrianglesTest();
                
                //Object obj2 = new Object(image.getFigurePixels(), image.width, image.width);
                
                //obj2.transformation(transformationMatrix);
                
                //obj2.exportPovRay("new.txt");
               
            }
            catch(Exception e){
                
                System.out.println("Toolbar Init Scanner exception");
                
                e.printStackTrace();
            }
        }
        
    }
}
