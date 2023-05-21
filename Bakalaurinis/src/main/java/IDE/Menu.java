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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author gugu
 */
public class Menu extends JMenuBar implements ActionListener {
    
    Main mainWindow;
    
    JMenu fileMenu;
    /*
    JMenu editMenu;
    JMenu searchMenu;
    JMenu textMenu;
    JMenu editorMenu;
    JMenu insertMenu;
    JMenu renderMenu;
    JMenu optionsMenu;
    JMenu toolsMenu;
    JMenu windowMenu;
    JMenu helpMenu;
    */
    
    JMenuItem firstMethodImg1;
    JMenuItem firstMethodImg2;
    JMenuItem secondMethodImg;

    /*
    JMenuItem cutMenuItem;
    JMenuItem copyMenuItem;
    */
    FileOperation fileHandler;
    
    Menu(Main mainWindow, FileOperation fileHandler){
        
        this.mainWindow = mainWindow;
        
        this.fileHandler = fileHandler;
        
        fileMenu = new JMenu("Load Images");
        /*
        editMenu = new JMenu("Edit");
        searchMenu = new JMenu("Search");
        textMenu = new JMenu("Text");
        editorMenu = new JMenu("Editor");
        insertMenu = new JMenu("Insert");
        renderMenu = new JMenu("Render");
        optionsMenu = new JMenu("Options");
        toolsMenu = new JMenu("Tools");
        windowMenu = new JMenu("Window");
        helpMenu = new JMenu("Help");
        */
        firstMethodImg1 = new JMenuItem("First method1 image");
        firstMethodImg1.setMnemonic(KeyEvent.VK_N);
        firstMethodImg1.setActionCommand("img1");
        //createMenuItem(fileNew,KeyEvent.VK_N,fileMenu,KeyEvent.VK_N,this);

        firstMethodImg2 = new JMenuItem("First method2 image");
        firstMethodImg2.setActionCommand("img2");
        
        secondMethodImg = new JMenuItem("Second method image");
        secondMethodImg.setActionCommand("img3");
        
        /*
        cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");
        
        copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");
        */
        
        fileMenu.add(firstMethodImg1);
        fileMenu.add(firstMethodImg2);
        fileMenu.add(secondMethodImg);
        /*
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        */
        firstMethodImg1.addActionListener(this);
        firstMethodImg2.addActionListener(this);
        secondMethodImg.addActionListener(this);
        /*
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        */
        this.add(fileMenu);
        /*
        this.add(editMenu);
        this.add(searchMenu);
        this.add(textMenu);
        this.add(editorMenu);
        this.add(insertMenu);
        this.add(renderMenu);
        this.add(optionsMenu);
        this.add(toolsMenu);
        this.add(windowMenu);
        this.add(helpMenu);
        */
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String cmdText = e.getActionCommand();
        
        if (cmdText.equals("img1")){
            
            //fileHandler.newFile();
            
            //mainWindow.tabbedPane.setTitleAt(0, "Untitled !");
            File file = fileHandler.openFile();
            
            Main.firstMethod1File = file;
            
        }
        
        else if (cmdText.equals("img2")) {
            
            File file = fileHandler.openFile();
            
            Main.firstMethod2File = file;
            /*
            mainWindow.tabbedPane.setTitleAt(mainWindow.tabbedPane.getSelectedIndex(), file.getName());
            
            CodeArea txt = (CodeArea)mainWindow.tabbedPane.getSelectedComponent();
            
            try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()){
                txt.loadText(scanner.nextLine());
            }
            }
            catch (Exception ex) {}
            
            mainWindow.tabbedPane.setSelectedComponent(txt);*/
        }
        
        else if (cmdText.equals("img3")) {
            
            File file = fileHandler.openFile();
            
            Main.secondMethodFile = file;
        }
        
        
    }
    
    
    /*
    abstract class MenuItemListener implements ActionListener {
        public void actionPerformed(Action e){
            System.out.println(e.getActionCommand() + "JMenuItem clicked.");
        }
    }*/


   
}


