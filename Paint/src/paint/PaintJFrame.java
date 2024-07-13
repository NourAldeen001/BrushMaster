/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
/**
 *
 * @author xps
 */
public class PaintJFrame extends JFrame {
    
    public static Color Color_Selected = Color.BLACK;
    
    public static String Shape_Selected = "LINE";
    
    public static boolean Is_Dotted = false;
    
    public static boolean Is_Outline = true;
    
    Shape currentShape;
    
    ArrayList<Shape> shapesStore = new ArrayList<>();
    
    int x1, y1, x2, y2;
    
    
    /**
     * Creates new form PaintJFrame
     */
    public PaintJFrame() {
        initComponents();
        
        
        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                switch (Shape_Selected) {
                    case "PENCIL":
                        if(currentShape == null){
                            currentShape = new Pencil(Color_Selected);
                        }   ((Pencil) currentShape).addPoint(x2, y2);
                        break;
                    case "ERASER":
                        eraserAction(x2, y2);
                        break;
                    default:
                        updateCurrentShape(); 
                        break;
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                getCursorLocation();
            }
        
        });
        
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                 if(Shape_Selected.equals("PENCIL")){
                    if(currentShape == null){
                        currentShape = new Pencil(Color_Selected);
                    }
                    ((Pencil) currentShape).addPoint(x1, y1);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                
                if(!"ERASER".equals(Shape_Selected)){
                    if("PENCIL".equals(Shape_Selected)){
                        shapesStore.add(currentShape);
                    }
                    else{
                        createShape();  
                    }
                    currentShape = null;
                }
                repaint();
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }

            
            
        });
        
        // Handling Picking The Colors
        
        BlackBtn.addActionListener((ActionEvent e) -> {
            Color_Selected = Color.BLACK;
            repaint();
        });
        
        RedBtn.addActionListener((ActionEvent e) -> {
            Color_Selected = Color.RED;
            repaint();
        });
        
        GreenBtn.addActionListener((ActionEvent e) -> {
            Color_Selected = Color.GREEN;
            repaint();
        });
        
        BlueBtn.addActionListener((ActionEvent e) -> {
            Color_Selected = Color.BLUE;
            repaint();
        });
        
        
        
        //Handling Picking The Shape
        
        LineBtn.addActionListener((ActionEvent e) -> { Shape_Selected = "LINE"; });
        
        RectangleBtn.addActionListener((ActionEvent e) -> { Shape_Selected = "RECT"; });
        
        OvalBtn.addActionListener((ActionEvent e) -> { Shape_Selected = "OVAL"; });
       
        // Eraser Button
        
        EraserBtn.addActionListener((e) -> { Shape_Selected = "ERASER"; });
        
        // Pencil Button 
        
        PencilBtn.addActionListener((e) -> { Shape_Selected = "PENCIL"; });
        
        // Dotted or not Shape ??
        
        SolidCheckBox.addActionListener((e) -> { Is_Outline = !SolidCheckBox.isSelected(); });
        
        // Solid or not Shape ??
        
        DottedCheckBox.addActionListener((e) -> { Is_Dotted = DottedCheckBox.isSelected(); });
        
        
        // Undo Button
        
        UndoBtn.addActionListener((e) -> {
            if(!shapesStore.isEmpty()){
                shapesStore.remove(shapesStore.size() - 1);
                repaint();
            }
        });
        
        
        // Clear Button
        
        ClearBtn.addActionListener((e) -> {
            shapesStore.clear();
            repaint();
        });
        
        
        
    }

    private void createShape() {
        
        Shape shape = null;
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        
        switch(Shape_Selected){
            case "LINE":
                shape = new Line(x1, y1, x2, y2, Color_Selected, Is_Dotted);
                break;
            case "RECT":               
                shape = new Rectangle(startX, startY, width, height, Color_Selected, Is_Dotted, Is_Outline);
                break;
            case "OVAL":
                shape = new Oval(startX, startY, width, height, Color_Selected, Is_Dotted, Is_Outline);
                break;
        }
        if(shape != null){
            shapesStore.add(shape);
        }
    }    
    
    private void updateCurrentShape(){
        
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        
        switch(Shape_Selected){
            case "LINE":
                currentShape = new Line(x1, y1, x2, y2, Color_Selected, Is_Dotted);
                break;
            case "RECT":               
                currentShape = new Rectangle(startX, startY, width, height, Color_Selected, Is_Dotted, Is_Outline);
                break;
            case "OVAL":
                currentShape = new Oval(startX, startY, width, height, Color_Selected, Is_Dotted, Is_Outline);
                break;
        } 
    }    
    
    private void eraserAction(int x, int y) {
        int eraserSize = 40;
        shapesStore.add(new Rectangle(x - eraserSize/2, y - eraserSize/2, eraserSize, eraserSize, Color.WHITE, false, false));
    }
    
    private void getCursorLocation(){
        Point cursorPointLocation = MouseInfo.getPointerInfo().getLocation();
        Thread th = new Thread(() -> {
                    CrusorPositionLabel.setText("( "  + cursorPointLocation.x + " , " + cursorPointLocation.y + " )");
                });
                th.start();
    }
    


    
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D graphics2d = (Graphics2D) g;
       
        
        for(Shape shape : shapesStore){
            shape.draw(graphics2d);
        }
        
        if(currentShape != null){
            currentShape.draw(graphics2d);
        }
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HomePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ToolbarPanel = new javax.swing.JPanel();
        functionsLabel = new javax.swing.JLabel();
        ClearBtn = new javax.swing.JButton();
        UndoBtn = new javax.swing.JButton();
        PaintModeLabel = new javax.swing.JLabel();
        LineBtn = new javax.swing.JButton();
        RectangleBtn = new javax.swing.JButton();
        OvalBtn = new javax.swing.JButton();
        PencilBtn = new javax.swing.JButton();
        EraserBtn = new javax.swing.JButton();
        SolidCheckBox = new javax.swing.JCheckBox();
        DottedCheckBox = new javax.swing.JCheckBox();
        ColorsLabel = new javax.swing.JLabel();
        BlackBtn = new javax.swing.JButton();
        RedBtn = new javax.swing.JButton();
        GreenBtn = new javax.swing.JButton();
        BlueBtn = new javax.swing.JButton();
        CrusorPositionLabel = new javax.swing.JLabel();
        IconCrusorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));
        HomePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        HomePanel.setPreferredSize(new java.awt.Dimension(1042, 491));

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(0, 487, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ToolbarPanel.setBackground(new java.awt.Color(255, 255, 255));
        ToolbarPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        functionsLabel.setText("Functions : ");

        ClearBtn.setText("Clear");
        ClearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBtnActionPerformed(evt);
            }
        });

        UndoBtn.setText("Undo");
        UndoBtn.setActionCommand("UndoBtn");
        UndoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoBtnActionPerformed(evt);
            }
        });

        PaintModeLabel.setText("Paint Mode : ");

        LineBtn.setText("Line");
        LineBtn.setActionCommand("LineBtn");

        RectangleBtn.setText("Rectangle");
        RectangleBtn.setActionCommand("RectangleBtn");

        OvalBtn.setText("Oval");
        OvalBtn.setActionCommand("OvalBtn");

        PencilBtn.setText("Pencil");
        PencilBtn.setActionCommand("PencilBtn");

        EraserBtn.setText("Eraser");
        EraserBtn.setActionCommand("EraserBtn");

        SolidCheckBox.setText("Soild");
        SolidCheckBox.setActionCommand("SolidCheckBox");
        SolidCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolidCheckBoxActionPerformed(evt);
            }
        });

        DottedCheckBox.setText("Dotted");
        DottedCheckBox.setActionCommand("DottedCheckBox");

        ColorsLabel.setText("Colors : ");

        BlackBtn.setBackground(new java.awt.Color(0, 0, 0));
        BlackBtn.setForeground(new java.awt.Color(255, 255, 255));
        BlackBtn.setText("Black");
        BlackBtn.setActionCommand("BlackBtn");

        RedBtn.setBackground(new java.awt.Color(255, 0, 0));
        RedBtn.setText("Red");
        RedBtn.setActionCommand("RedBtn");

        GreenBtn.setBackground(new java.awt.Color(51, 255, 0));
        GreenBtn.setText("Green");
        GreenBtn.setActionCommand("GreenBtn");

        BlueBtn.setBackground(new java.awt.Color(0, 0, 255));
        BlueBtn.setText("Blue");
        BlueBtn.setActionCommand("BlueBtn");

        javax.swing.GroupLayout ToolbarPanelLayout = new javax.swing.GroupLayout(ToolbarPanel);
        ToolbarPanel.setLayout(ToolbarPanelLayout);
        ToolbarPanelLayout.setHorizontalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(functionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UndoBtn)
                .addGap(18, 18, 18)
                .addComponent(PaintModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RectangleBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OvalBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PencilBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EraserBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SolidCheckBox)
                .addGap(10, 10, 10)
                .addComponent(DottedCheckBox)
                .addGap(18, 18, 18)
                .addComponent(ColorsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BlackBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RedBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GreenBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BlueBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ToolbarPanelLayout.setVerticalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(functionsLabel)
                    .addComponent(ClearBtn)
                    .addComponent(UndoBtn)
                    .addComponent(PaintModeLabel)
                    .addComponent(LineBtn)
                    .addComponent(RectangleBtn)
                    .addComponent(OvalBtn)
                    .addComponent(PencilBtn)
                    .addComponent(EraserBtn)
                    .addComponent(SolidCheckBox)
                    .addComponent(DottedCheckBox)
                    .addComponent(ColorsLabel)
                    .addComponent(BlackBtn)
                    .addComponent(RedBtn)
                    .addComponent(GreenBtn)
                    .addComponent(BlueBtn))
                .addGap(16, 16, 16))
        );

        CrusorPositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CrusorPositionLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        IconCrusorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IconCrusorLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/targets.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ToolbarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(IconCrusorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CrusorPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(ToolbarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CrusorPositionLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IconCrusorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        HomePanel.getAccessibleContext().setAccessibleName("");
        HomePanel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SolidCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolidCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SolidCheckBoxActionPerformed

    private void UndoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UndoBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_ClearBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaintJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaintJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaintJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaintJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaintJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BlackBtn;
    private javax.swing.JButton BlueBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JLabel ColorsLabel;
    private javax.swing.JLabel CrusorPositionLabel;
    private javax.swing.JCheckBox DottedCheckBox;
    private javax.swing.JButton EraserBtn;
    private javax.swing.JButton GreenBtn;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JLabel IconCrusorLabel;
    private javax.swing.JButton LineBtn;
    private javax.swing.JButton OvalBtn;
    private javax.swing.JLabel PaintModeLabel;
    private javax.swing.JButton PencilBtn;
    private javax.swing.JButton RectangleBtn;
    private javax.swing.JButton RedBtn;
    private javax.swing.JCheckBox SolidCheckBox;
    private javax.swing.JPanel ToolbarPanel;
    private javax.swing.JButton UndoBtn;
    private javax.swing.JLabel functionsLabel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}