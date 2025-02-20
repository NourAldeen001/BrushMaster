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
public class Line implements Shape {
    
    private int x1, y1;
    
    private int x2, y2;
    
    private Color color;
    
    private boolean isDotted;

    public Line(int x1, int y1, int x2, int y2, Color color, boolean isDotted) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.isDotted = isDotted;
    }
    

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
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
        graphics2D.drawLine(x1, y1, x2, y2);
        
    }
    
    @Override
    public int calculateArea() {
        return (int) ( Math.sqrt( Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2) ) );
    }
    
}
