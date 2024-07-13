/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author xps
 */
public class Oval implements Shape {
    
    private int x, y;
    
    private int width;
    
    private int height;

    private Color color;
    
    private boolean isDotted;
    
    private boolean isOutline;
    
    public Oval(int x, int y, int width, int height, Color color, boolean isDotted, boolean isOutline) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isDotted = isDotted;
        this.isOutline = isOutline;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isIsDotted() {
        return isDotted;
    }

    public void setIsDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }
    
    public boolean isIsOutline() {
        return isOutline;
    }

    public void setIsOutline(boolean isOutline) {
        this.isOutline = isOutline;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        
        graphics2D.setColor(color);
        
        if(isDotted){
            float[] dottedPattern = new float[]{10, 10};
            graphics2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, dottedPattern, 0));
        }
        else {
            graphics2D.setStroke(new BasicStroke(2));
        }
        
       if(isOutline){
            graphics2D.drawOval(x, y, width, height);
        }
        else {
            graphics2D.fillOval(x, y, width, height);
        }

    }
    
    @Override
    public int calculateArea() {
        return (int) (Math.PI * width * height);
    }
    
}
