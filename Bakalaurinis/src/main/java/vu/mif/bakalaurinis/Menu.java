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
    
    JMenuItem newMenuItem;
    JMenuItem openMenuItem;
    JMenuItem saveMenuItem;
    JMenuItem cutMenuItem;
    JMenuItem copyMenuItem;
    
    FileOperation fileHandler;
    
    Menu(Main mainWindow, FileOperation fileHandler){
        
        this.mainWindow = mainWindow;
        
        this.fileHandler = fileHandler;
        
        fileMenu = new JMenu("File");
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
        
        newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");
        //createMenuItem(fileNew,KeyEvent.VK_N,fileMenu,KeyEvent.VK_N,this);

        openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");
        
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");
        
        
        cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");
        
        copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");
        
        
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        
        this.add(fileMenu);
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
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String cmdText = e.getActionCommand();
        
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
            
            while (scanner.hasNextLine()){
                txt.loadText(scanner.nextLine());
            }
            }
            catch ( Exception ex) {}
            
            mainWindow.tabbedPane.setSelectedComponent(txt);
        }
        
    }
    
    
    /*
    abstract class MenuItemListener implements ActionListener {
        public void actionPerformed(Action e){
            System.out.println(e.getActionCommand() + "JMenuItem clicked.");
        }
    }*/


   
}


