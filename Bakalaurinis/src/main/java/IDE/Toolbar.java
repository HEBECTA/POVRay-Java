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
import Graphics.Matrix;
import Graphics.ImageScanner;
import Graphics.Object3D;
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
    
    JButton povRaySceneSettingsBtn;
    JButton paintedImgBtn;
    JButton showPixelsBtn;
    JButton showFinalBtn;
    JButton refreshBtn;
    
    ImageIcon newIcon;
    ImageIcon openIcon;
    ImageIcon floppyIcon;
    ImageIcon closeIcon;
    
    ImageIcon povRaySceneSettingsIcon;
    ImageIcon paintedImgIcon;
    ImageIcon showPixelsIcon;
    ImageIcon showFinalIcon;
    ImageIcon refreshIcon;

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
        
        povRaySceneSettingsIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/queue.png");
        paintedImgIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/show.png");
        showPixelsIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/sourcefile.bmp");
        showFinalIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/run.png");
        refreshIcon = new ImageIcon("/home/gugu/NetBeansProjects/swing/src/main/java/resources/pause.jpg");
                                    
        newButton = new JButton(new ImageIcon(getScaledImage(newIcon.getImage(), dimensions, dimensions)));
        openButton = new JButton(new ImageIcon(getScaledImage(openIcon.getImage(), dimensions, dimensions)));
        saveButton = new JButton(new ImageIcon(getScaledImage(floppyIcon.getImage(), dimensions, dimensions)));
        closeButton = new JButton(new ImageIcon(getScaledImage(closeIcon.getImage(), dimensions, dimensions)));
        
        povRaySceneSettingsBtn = new JButton(new ImageIcon(getScaledImage(povRaySceneSettingsIcon.getImage(), dimensions, dimensions)));
        paintedImgBtn = new JButton(new ImageIcon(getScaledImage(paintedImgIcon.getImage(), dimensions, dimensions)));
        showPixelsBtn = new JButton(new ImageIcon(getScaledImage(showPixelsIcon.getImage(), dimensions, dimensions)));
        showFinalBtn = new JButton(new ImageIcon(getScaledImage(showFinalIcon.getImage(), dimensions, dimensions)));
        refreshBtn = new JButton(new ImageIcon(getScaledImage(refreshIcon.getImage(), dimensions, dimensions)));
      
        newButton.setText("New");
        openButton.setText("Open");
        saveButton.setText("Save");
        closeButton.setText("Close");
        
        povRaySceneSettingsBtn.setText("scene");
        paintedImgBtn.setText("image");       
        showPixelsBtn.setText("pixels");      
        showFinalBtn.setText("final");        
        refreshBtn.setText("refresh");
                
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
    
        openButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        openButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        closeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        povRaySceneSettingsBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        povRaySceneSettingsBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        paintedImgBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        paintedImgBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        showPixelsBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        showPixelsBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        showFinalBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        showFinalBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        refreshBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        refreshBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        newButton.addActionListener(this);
        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        
        povRaySceneSettingsBtn.addActionListener(this);
        paintedImgBtn.addActionListener(this);
        showPixelsBtn.addActionListener(this);
        showFinalBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        
        panel.add(newButton);
        panel.add(openButton);
        panel.add(saveButton);
        panel.add(closeButton);
      
        panel.add(povRaySceneSettingsBtn);
        panel.add(paintedImgBtn);
        panel.add(showPixelsBtn);
        panel.add(showFinalBtn);
        panel.add(refreshBtn);
        
       
        
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
        
        else if ( cmdText.equals("scene") ){
           
        }
        
        else if ( cmdText.equals("image") ){

            
        }
        
        else if ( cmdText.equals("pixels") ){

               
        }
        
        else if ( cmdText.equals("final") ){
            
            
            JFileChooser f = new JFileChooser("C:\\Users\\GUGU\\Pictures\\_kursinis.png");
            
            f.showOpenDialog(null);
            
            File file = f.getSelectedFile();
            
            try {
            
                ImageScanner image = new ImageScanner(file);
                
                image.exportFillPovRay("fill.txt", 0.7f);
                
                image.exportPaintedFigure("painted.png");


               
            }
            catch(Exception e){
                
                System.out.println("Toolbar Init Scanner exception");
                
                e.printStackTrace();
            }
            
        }
        
        else if ( cmdText.equals("refresh") ){
            
            mainWindow.refreshTabs();
            
        }
    }
}
