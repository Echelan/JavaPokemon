/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

/*
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
*/
//import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JButton;
//import java.awt.Color;
import javax.swing.JFrame;


/**
 *p
 * 
 * @author movillaf
 */
public class GameWindow {

    final private JFrame gameFrame;
    final JButton spawnPokemans;
    final private GroupLayout winLayout;
  //  final public Canvas gameCanvas;
    
    public GameWindow() {
        gameFrame = new JFrame();
        Dimension dims1 = new Dimension(600,500);
        
        gameFrame.setVisible(true);
        gameFrame.setSize(dims1);
        gameFrame.setTitle("Pokemon Violet");
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
       
     //   gameCanvas = new Canvas();
     //   gameCanvas.setBackground(Color.green);
     //   gameFrame.getContentPane().add(gameCanvas);
        spawnPokemans = new JButton();
        spawnPokemans.setBackground(Color.red);
        spawnPokemans.setForeground(Color.white);
        //spawnPokemans.set
        spawnPokemans.setToolTipText("Undalo que no muerde");
        
        
        winLayout=new GroupLayout(gameFrame.getContentPane());
        gameFrame.getContentPane().setLayout(winLayout);
        
        winLayout.setHorizontalGroup(
            winLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, winLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spawnPokemans, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            //    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            //    .addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
             //   .addGap(18, 18, 18)
             //   .addComponent(jButton1)
                .addContainerGap())
        );
        winLayout.setVerticalGroup(
            winLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(winLayout.createSequentialGroup()
                .addContainerGap()
            //    .addGroup(winLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                //    .addComponent(spawnPokemans, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                 //  .addGroup(winLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(spawnPokemans)
                 //       .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
              //  .addContainerGap(266, Short.MAX_VALUE)
                )
           // )
        );
        /*
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(266, Short.MAX_VALUE))
        );
        */
       // spawnPokemans.setSize(20, 10);
        //spawnPokemans.setBounds(10, 20, 30, 40);
        spawnPokemans.setText("Spawn");
        gameFrame.getContentPane().add(spawnPokemans);
        
    }
	
	//public void paint (Graphics g)
	//{
		//g.drawRect(100, 100, 50, 50);
	//}
	
}
