/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb30.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ImagePanel extends JPanel implements MouseListener {

    private BufferedImage image = null;
    private boolean resize = true;

    public ImagePanel(BufferedImage image) {
        super();
        this.image = image;
        //updateSize();
        //this.setBorder(BorderFactory.createLineBorder(Color.black));
       
    }



    /**
     * Return image
     * @return contained image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set new image.
     * @param img Buffered image for update;
     */
    public void setImage(BufferedImage img) {
        this.image = img;
        this.repaint();
        //updateSize();
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension dm = this.getSize();
        //this.setBackground(Color.BLACK);
        g2.setColor(Color.black);
        if (image != null) {
            if (resize) {
                Image dimg;
                if (image.getWidth() > image.getHeight()) {
                    dimg = image.getScaledInstance(dm.width, -1, Image.SCALE_SMOOTH);
                } else {
                    dimg = image.getScaledInstance(-1, dm.height, Image.SCALE_SMOOTH);
                }
                int x = (dm.width - dimg.getWidth(this)) / 2;
                int y = (dm.height - dimg.getHeight(this)) / 2;
                g2.drawImage(dimg, x, y, null);
            } else {
                g2.drawImage(image, 0, 0, null);
                
            }

        } else {
            g2.drawString("No photo included", 10, dm.height / 2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (image != null) {
            JFrame f = new JFrame();
            ImagePanel im = new ImagePanel(image);
            im.setResize(false);
            f.add(im);
            f.setPreferredSize(new Dimension(image.getWidth()+20, image.getHeight() +20));
            f.pack();
            f.setVisible(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
