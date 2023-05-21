/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;


import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.util.Iterator;

/**
 *
 * @author GUGU
 */
public class ImageScanner {

    private File imageFile;
    private BufferedImage originalImage;
    private BufferedImage paintedImage;
    private BufferedImage levelImage;
    
    private FigureData figureData;

    private Point[][] grid;

    //black
    public static final int objContourColor = 0xff000000;
    public static final int objTempContourColor = 0xff111111;
    //green
    //public static final int objInnerColor = 0xff22b14c; // 34 177 76
    public static final int objInnerColor = 0xff00ff00;
    // white
    public static final int objEmptyColor = 0xffffffff;
    
    // red
    public static final int objHillTopColor = 0xffff0000;
    // blue
    public static final int objHillBotColor = 0xff0000ff;
    // yellow
    //public static final int objInflatedColor = 0xffffff00;
    
    int maxY, minY;
    int maxX, minX;
    public int width;
    public int height;
    /*
    public ImageScanner(File file) {

        imageFile = file;
    }
*/
    public void setFile(File file){
        
        imageFile = file;
    }
    
    public FigureData getFigureData(){
        
        figureData = new FigureData();
        
        figureData.height = height;
        figureData.width = width;
        figureData.midPoint = getMidPoint();
        figureData.grid = grid;
        
        figureData.figureAreaPixels = getFigureAreaPixels();

        return figureData;
    }
    
    private Point[][] convertToGrid(){
        
        Point[][] grid = new Point[height][width];
        
        System.out.println("height " + height + ", width " + width);
        System.out.println("diagonal " + (int) Math.round(Math.sqrt(height * height + width * width)));
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
                
                int pixelColor = originalImage.getRGB(minX + x, minY + y);
                
                //grid[y][x] = new Point(height - 1 - y, x , 0, pixelColor);
                grid[y][x] = new Point(y, x , 0, pixelColor);
                
                switch (pixelColor) {
                    case objContourColor:
                        grid[y][x].contour = true;
                        break;
                    case objHillTopColor:
                        grid[y][x].commonTop = true;
                        break;
                    case objHillBotColor:
                        grid[y][x].bottom = true;
                        break;
                    case objEmptyColor:
                        break;
                    default:
                        grid[y][x].top = true;
                        break;
                }
                
                if (grid[y][x].top || grid[y][x].bottom || grid[y][x].commonTop)
                    grid[y][x].topColor = pixelColor;
            }
        }
        
        /*
        // flip grid
        
        for (int y = 0; y < height / 2; ++y){
            
            for (int x = 0; x < width; ++x){

                Point temp = grid[y][x];
                grid[y][x] = grid[height - (y + 1)][x];
                grid[y][x].color = grid[height - (y + 1)][x].color;
                grid[height - (y + 1)][x] = temp;
                grid[height - (y + 1)][x].color = temp.color;
            }
        }
        */
        
        // translate to 0 0 0
        /*
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){

                grid[y][x].y -= height / 2; 
                grid[y][x].x -= width / 2; 
            }
        }
        */
        return grid;
    }
    
    public LinkedList<LinkedList<LinkedList<Point>>> getFigureAreaPixels(){
        
        LinkedList<LinkedList<LinkedList<Point>>> pixels = new LinkedList<>();
        LinkedList<LinkedList<Point>> linesList = new LinkedList<>();
        LinkedList<Point> linePixelsList = new LinkedList<>();
        
        boolean coloredPixelSequence = false;
        int maxMissingPixels = 5;
        
        for (int y = 0; y < height; ++y) {

                for (int x = 0; x < width; ++x) {

                    if (grid[y][x].color == objInnerColor) {

                        coloredPixelSequence = true;
                        linePixelsList.add(grid[y][x]);
                    }
                    else if (coloredPixelSequence) {

                        boolean spaceGap = true;
                        // missing pixels
                        if (x + maxMissingPixels < width){
                            for (int xx = x + 1; xx < x + maxMissingPixels; ++xx){
                            
                                if (grid[y][xx].color == objInnerColor) 
                                    spaceGap = false;
                            }
                        }
                        
                        if (spaceGap){
                            
                            coloredPixelSequence = false;
                            linesList.add(linePixelsList);
                            linePixelsList = new LinkedList();
                        }
                        // continue as grid[y][x].color == objColor
                        else {
                            
                            coloredPixelSequence = true;
                            linePixelsList.add(grid[y][x]);
                        }
                    }
                }

                if (!linePixelsList.isEmpty()) {

                    linesList.add(linePixelsList);
                    linePixelsList = new LinkedList();
                }
                
                if (!linesList.isEmpty()) {

                    pixels.add(linesList);
                    linesList = new LinkedList();
                }

                coloredPixelSequence = false;
            }
        
        return pixels;
    }
   
    public void exportPaintedFigure(String location) {

        try {

            File outputfile = new File(location);
            
            paintedImage = new BufferedImage(width, height, TYPE_INT_ARGB);
            for(int y = 0; y < height; ++y){
                
                for (int x = 0; x < width; ++x){
                    
                    //paintedImage.setRGB(x, height - 1 - y, grid[y][x].color);
                    paintedImage.setRGB(x, y, grid[y][x].color);
                }
            }
            
            ImageIO.write(paintedImage, "png", outputfile);
        } catch (Exception e) {

            System.out.println("exportPaintedFigure exception");
        }
    }

    public void exportFigureLevels(String location){
        
        try {

            File outputfile = new File(location);
            
            levelImage = new BufferedImage(width, height, TYPE_INT_ARGB);
            for(int y = 0; y < height; ++y){
                
                for (int x = 0; x < width; ++x){
                    
                    if (grid[y][x].commonTop)
                        levelImage.setRGB(x, y, objHillTopColor);
                    
                    else if (grid[y][x].top)
                        levelImage.setRGB(x, y, 0xff00ff);
                    
                    else if (grid[y][x].bottom)
                        levelImage.setRGB(x,  y, objHillBotColor);
                    
                    else if (grid[y][x].contour)
                        levelImage.setRGB(x, y, objContourColor);
                    
                    else
                        levelImage.setRGB(x, y, objInnerColor);
                }
            }
            
            ImageIO.write(levelImage, "png", outputfile);
        } catch (Exception e) {

            System.out.println("exportPaintedFigure exception");
        }
    }
    
    public boolean processImage(){
        
        try {
            
            if (!imageFile.exists()) return false;
                
            originalImage = ImageIO.read(imageFile);
            
            calculateDimensions();
            
            grid = convertToGrid();
            
            fillFigure();

            shrinkLines();
            
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("processImage Exception");
        }
        
        return true;
    }

    public Point getMidPoint(){
        
        return new Point(height / 2, width / 2);
    }
    
    private void calculateDimensions(){
        
        maxY = -1;
        minY = originalImage.getHeight() - 1;
        maxX = -1;
        minX = originalImage.getWidth() - 1;

            for (int y = 0; y < originalImage.getHeight(); ++y) {

                for (int x = 0; x < originalImage.getWidth(); ++x) {

                    int pixelColor = originalImage.getRGB(x, y);

                    if (pixelColor == objContourColor || pixelColor == objHillTopColor || pixelColor == objHillBotColor) {

                        if (maxY < y) {
                            maxY = y;
                        }

                        if (minY > y) {
                            minY = y;
                        }

                        if (maxX < x) {
                            maxX = x;
                        }

                        if (minX > x) {
                            minX = x;
                        }
                    }
                }
            }

        width = maxX - minX + 1;
        height = maxY - minY + 1;
    }
    
    private void fillFigure(){
        
        changeTopColorInContour();
       

        Point innerColor = findInnerColor();
        
        //System.out.println("y " + (int)innerColor.y + ", x " + (int)innerColor.x);
        
        fillObject((int)innerColor.y, (int)innerColor.x, objInnerColor);
        
        restoreContourColor();
        
        //this.exportPaintedFigure("/home/gugu/Pictures/bak/paintedImage.png");
    }
    
    private Point findInnerColor(){
        
        for (int y = 1; y  < height - 1; ++y){
            
            for (int x = 1; x < width - 1; ++x){
                
                //String.format("0x%08X", grid[y][x].color);
                
                if (hillTopColor(grid[y][x].color)){
                    
                    return new Point(y, x);
                }
            }
        }
        
        return null;
    }
    
    private void fillObject(int y, int x, int color) {

        int pixelColor = grid[y][x].color;
        
        if (pixelColor != objContourColor && pixelColor != objTempContourColor && pixelColor != color) {

            grid[y][x].color = color;
        } else return;

        if (y - 1 >= 0) {

            fillObject(y - 1, x, color);
        }

        if (y + 1 < height) {

            fillObject(y + 1, x, color);
        }

        if (x - 1 >= 0) {

            fillObject(y, x - 1, color);
        }

        if (x + 1 < width) {

            fillObject(y, x + 1, color);
        }
    }

    private void changeTopColorInContour(){
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
                
                if (!ImageScanner.hillTopColor(grid[y][x].color))
                    continue;
                
                if (y - 1 >= 0){
                    
                    if (grid[y-1][x].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (y + 1 < height){
                    
                    if (grid[y+1][x].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (x + 1 < width){
                    
                    if (grid[y][x + 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (x - 1 >= 0){
                    
                    if (grid[y][x - 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                // ***************************************************************************
                
                if (y - 1 >= 0 && x - 1 >= 0){
                    
                    if (grid[y - 1][x - 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (y - 1 >= 0 && x + 1 < width){
                    
                    if (grid[y - 1][x + 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (y + 1 < height && x - 1 >= 0){
                    
                    if (grid[y + 1][x - 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
                
                if (y + 1 < height && x + 1 < width){
                    
                    if (grid[y + 1][x + 1].color == objContourColor){
                        
                        grid[y][x].color = objTempContourColor;
                        continue;
                    }
                }
            }
        }
    }
    
    public void restoreContourColor(){
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
                
                if (grid[y][x].color == objTempContourColor){
                 
                    
                    if (grid[y][x].top || grid[y][x].bottom || grid[y][x].commonTop)
                        grid[y][x].color = grid[y][x].topColor;
                         
                    else
                        grid[y][x].color = objContourColor;  
                }
            }
        }
    }
    
    private void shrinkLines(){
        
        // shrink black
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].color != objContourColor && grid[y][x].color != objHillTopColor && grid[y][x].color != objHillBotColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (grid[y + 1][x].color != objEmptyColor && grid[y - 1][x].color != objEmptyColor
                        && grid[y][x + 1].color != objEmptyColor && grid[y][x - 1].color != objEmptyColor)
                {
                    if (grid[y][x].color == objContourColor){
                        
                        grid[y][x].top = false;
                        grid[y][x].bottom = false;
                        grid[y][x].commonTop = false;
                        grid[y][x].contour = false;
                    }
                    grid[y][x].color = objInnerColor;
                }
                
                else
                    grid[y][x].color = objContourColor;
            }
        }
        
        
        
        // check corners
        for (int y = 0; y < height; ++y){
            
            if (grid[y][0].color == objHillTopColor || grid[y][0].color == objHillBotColor){
                
                grid[y][0].color = objContourColor;
                grid[y][0].top = false;
                grid[y][0].bottom = false;
            }

            if (grid[y][width - 1].color == objHillTopColor || grid[y][width - 1].color == objHillBotColor){
                
                grid[y][width - 1].color = objContourColor;
                grid[y][width - 1].top = false;
                grid[y][width - 1].bottom = false;
            }
        }
        
        for (int x = 0; x < width; ++x){
            
            if (grid[0][x].color == objHillTopColor || grid[0][x].color == objHillBotColor){
                
                grid[0][x].color = objContourColor;
                grid[0][x].top = false;
                grid[0][x].bottom = false;
            }
            
            if (grid[height - 1][x].color == objHillTopColor || grid[height - 1][x].color == objHillBotColor){
                
                grid[height - 1][x].color = objContourColor;
                grid[height - 1][x].top = false;
                grid[height - 1][x].bottom = false;
            }
        }
        
        // remove unnecessary contour pixels
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].color != objContourColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (grid[y+1][x].color == objContourColor && grid[y][x+1].color == objContourColor )
                    grid[y][x].color = objEmptyColor;
                
                else if (grid[y+1][x].color == objContourColor && grid[y][x-1].color == objContourColor)
                    grid[y][x].color = objEmptyColor;
                
                else if (grid[y-1][x].color == objContourColor && grid[y][x+1].color == objContourColor)
                    grid[y][x].color = objEmptyColor;
                
                else if (grid[y-1][x].color == objContourColor && grid[y][x-1].color == objContourColor)
                    grid[y][x].color = objEmptyColor;
            }
        }
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].color != objInnerColor)
                    continue;
                
                if (grid[y][x].top || grid[y][x].commonTop)
                    grid[y][x].color = grid[y][x].topColor;
                
                else if (grid[y][x].bottom)
                    grid[y][x].color = objHillBotColor;
            }
        }
        
        
        // remove unnecessary hill top pixels
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (!hillTopColor(grid[y][x].color) )
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (hillTopColor(grid[y+1][x].color) && hillTopColor(grid[y][x+1].color)){
                    
                    grid[y][x].color = objInnerColor;
                    grid[y][x].top = false;
                }
                
                else if (hillTopColor(grid[y+1][x].color) && hillTopColor(grid[y][x-1].color)){
                    
                    grid[y][x].color = objInnerColor;
                    grid[y][x].top = false;
                }
                
                else if (hillTopColor(grid[y-1][x].color) && hillTopColor(grid[y][x+1].color)){
                    
                    grid[y][x].color = objInnerColor;
                    grid[y][x].top = false;
                }
                
                else if (hillTopColor(grid[y-1][x].color) && hillTopColor(grid[y][x-1].color)){
                    
                    grid[y][x].color = objInnerColor;
                    grid[y][x].top = false;
                }
            }
        }
        
        
        
        // add additional hill bot pixels
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].color ==  objHillBotColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (grid[y+1][x].color == objHillBotColor && grid[y][x+1].color == objHillBotColor && grid[y+1][x+1].color != objHillBotColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].bottom = true;
                }
                
                else if (grid[y+1][x].color == objHillBotColor && grid[y][x-1].color == objHillBotColor && grid[y+1][x-1].color != objHillBotColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].bottom = true;
                }
                
                else if (grid[y-1][x].color == objHillBotColor && grid[y][x+1].color == objHillBotColor && grid[y-1][x+1].color != objHillBotColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].bottom = true;
                }
                
                else if (grid[y-1][x].color == objHillBotColor && grid[y][x-1].color == objHillBotColor && grid[y-1][x-1].color != objHillBotColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].bottom = true;
                }
            }
        }
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].bottom)
                    grid[y][x].color = objHillBotColor;
            }
        }
        
        // add additional contour pixels
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].color ==  objContourColor)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (grid[y+1][x].color == objContourColor && grid[y][x+1].color == objContourColor && grid[y+1][x+1].color != objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].contour = true;
                }
                
                else if (grid[y+1][x].color == objContourColor && grid[y][x-1].color == objContourColor && grid[y+1][x-1].color != objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].contour = true;
                }
                
                else if (grid[y-1][x].color == objContourColor && grid[y][x+1].color == objContourColor && grid[y-1][x+1].color != objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].contour = true;
                }
                
                else if (grid[y-1][x].color == objContourColor && grid[y][x-1].color == objContourColor && grid[y-1][x-1].color != objContourColor){
                    
                    //grid[y][x].color = objHillBotColor;
                    grid[y][x].contour = true;
                }
            }
        }
        
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (grid[y][x].contour)
                    grid[y][x].color = objContourColor;
            }
        }
        
        /*
        for (int y = 0; y < height; ++y){
            
            for (int x = 0; x < width; ++x){
            
                if (!grid[y][x].top)
                    continue;
                
                if (y - 1 < 0 || y + 1 >= height || x - 1 < 0 || x + 1 >= width)
                    continue;
                
                if (grid[y + 1][x].top && grid[y - 1][x].top 
                        && grid[y][x + 1].top && grid[y][x - 1].top )
                {
                    grid[y][x].top = false;
                }
            }
        }
        */
    }
    
    public static boolean hillTopColor(int color){
        
        if (color == objContourColor || color == objEmptyColor || color == objHillTopColor
            || color == objHillBotColor || color == objInnerColor || color == objTempContourColor)
            return false;
        
        return true;
    }
    
    public static boolean hillTopColorCom(int color){
        
        if (color == objContourColor || color == objEmptyColor
            || color == objHillBotColor || color == objInnerColor || color == objTempContourColor)
            return false;
        
        return true;
    }
}
