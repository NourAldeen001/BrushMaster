/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.Graphics2D;

/**
 *
 * @author xps
 */
public interface Shape {
    void draw(Graphics2D graphics2D);
    int calculateArea();
}
