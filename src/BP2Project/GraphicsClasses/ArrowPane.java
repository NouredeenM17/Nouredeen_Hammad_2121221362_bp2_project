
package BP2Project.GraphicsClasses;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 * @author Administrator
 */
public class ArrowPane extends javax.swing.JPanel {

    public ArrowPane() {
        initComponents();
    }
    
    //paints 2 arrows
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8.0f));
        g2d.draw(new Line2D.Double(10, 10, 50, 50));
        g2d.draw(new Line2D.Double(50, 50, 90, 10));

        g2d.draw(new Line2D.Double(120, 10, 160, 50));
        g2d.draw(new Line2D.Double(160, 50, 200, 10));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
