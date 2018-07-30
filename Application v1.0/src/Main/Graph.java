/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Graph extends javax.swing.JFrame {

    private List points = new ArrayList();

    public Graph() {

        super("Graph");
        int padding = 70;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < Main.driverAge.size(); i++) {
            points.add(new Point2D.Double(Main.driverAge.get(i) * 5, Main.numCas.get(i) * 20));
        }
    
        points.add(new Point2D.Double(0, 0));
        JPanel panel = new JPanel() {
            
            
            
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.drawString("driverAge", 300, 555);
                g2d.drawString("numCas", 20, 420);

                AffineTransform tform = AffineTransform.getTranslateInstance(0, getHeight());
                tform.scale(1, -1);

                g2d.drawString("99", 560, 555);
                g2d.drawString("10", 50, 280);
                g2d.drawString("8", 50, 330);
                g2d.drawString("0", 60, 540);
                g2d.drawString("0", 70, 555);

                g2d.setTransform(tform);

                g2d.drawRect(padding, padding, getWidth() - 2 * padding, getHeight() - 5 * padding);

                g2d.setColor(Color.RED);
                for (Iterator i = points.iterator(); i.hasNext();) {
                    Point2D.Double pt = (Point2D.Double) i.next();

                    Ellipse2D dot = new Ellipse2D.Double(pt.x + padding, pt.y + padding, 3, 3);
                    g2d.fill(dot);
                }
                g2d.dispose();
            }
        };
        
     

        setContentPane(panel);
        setLocation(300, 200);
        setSize(500 + 2 * padding, 500 + 2 * padding);
        setResizable(false);
        setVisible(true);
        
    JButton b1 = new JButton();   
    b1 = new JButton("Go back to Main ");
    add(b1);
    b1.addActionListener(e -> this.dispose());

        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

