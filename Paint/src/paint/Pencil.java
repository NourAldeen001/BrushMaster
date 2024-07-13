/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;

/**
 *
 * @author xps
 */
public class Pencil implements Shape {

    private ArrayList<Point> points;
    
    private Color color;

    public Pencil(Color color) {
        this.color = color;
        points = new ArrayList<>();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void addPoint(int x, int y){
        points.add(new Point(x, y));
    }
    
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(2));
        for(int i = 1; i < points.size(); i++){
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public int calculateArea() {
        int sum = 0;
        for(int i = 1; i < points.size(); i++){
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            sum += ( Math.sqrt( Math.pow(Math.abs(p2.x - p1.x), 2) + Math.pow(Math.abs(p2.y - p1.y), 2) ) );
        }
        return sum;
    }
    
}
