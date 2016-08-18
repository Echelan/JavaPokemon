/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonviolet;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.*;
/*
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
*/
import java.util.Random;
import javax.swing.*;
//import javax.swing.GroupLayout.ParallelGroup;


/**
 *
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {

    /**
     * @param args the command line arguments
     */
    
    public Pokemon enemy;
    final JTextField PokeInfo;
    final JButton spawnPokemans;
    
    void performactionthing(ActionEvent evt){
        Random rnd = new Random();
        enemy = new Pokemon(rnd.nextInt(151));
    }
    
    public GameWindow(){
    //  final public Canvas gameCanvas;
    //final JButton spawnPokemans;
        final GroupLayout winLayout;
        final JFrame gameFrame;
        final Dimension dims1 = new Dimension(600,500);
        
       // new JFrame();
        setLayout(null);
        setVisible(true);
        setSize(dims1);
        setTitle("Pokemon Violet");
        setResizable(false);
        setLocationRelativeTo(null);
	/*	
        gameFrame = new JFrame();
       
        
        gameFrame.setVisible(true);
        gameFrame.setSize(dims1);
        gameFrame.setTitle("Pokemon Violet");
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
       */
     //   gameCanvas = new Canvas();
     //   gameCanvas.setBackground(Color.green);
     //   gameFrame.getContentPane().add(gameCanvas);
     
        spawnPokemans = new JButton();
     //   spawnPokemans.setBackground(Color.red);
     //   spawnPokemans.setForeground(Color.white);
     //   spawnPokemans.setToolTipText("Undalo que no muerde");
       // spawnPokemans.setSize(20, 10);
        //spawnPokemans.setBounds(10, 20, 30, 40);
        spawnPokemans.setText("Spawn");
        spawnPokemans.addActionListener(this);
      //  spawnPokemans.setLocation(100, 200);
        //spawnPokemans.setBounds()
        spawnPokemans.setBounds(100, 200, 150, 60);
      //  spawnPokemans.setSize(40,10);
        //spawnPokemans.setLocation(1000, 1000);
        add(spawnPokemans);
        
        PokeInfo = new JTextField();
        PokeInfo.setBounds(40,50,150,60);
       // PokeInfo.isVisible(true);
        add(PokeInfo);
        
        
    //    gameFrame.add(spawnPokemans);
        
        /*
        winLayout=new GroupLayout(gameFrame.getContentPane());
        gameFrame.getContentPane().setLayout(winLayout);
        ParallelGroup winGroup = winLayout.createParallelGroup();
        
        winGroup.addComponent(spawnPokemans);
        */
       // gameFrame.getContentPane().add(spawnPokemans);
        
        /*
        winLayout.setHorizontalGroup(
            winLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, winLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spawnPokemans, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            //    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            //    .addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
             //   .addGap(18, 18, 18)
                    .addGap(18, 18, 18)
                    .addComponent(PokeInfo,80,80,120)
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
                        .addComponent(PokeInfo,10,10,30)
                 //       .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
              //  .addContainerGap(266, Short.MAX_VALUE)
                )
           // )
        );
        */
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
        
    }

    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
		
		
		//ManejadorPokemon manejadorPokemon = new ManejadorPokemon();
		Thread.sleep(100);
                
		GameWindow gameWindow=new GameWindow();
                
		splash.setVisible(false);
		splash = null;
       //         JButton.addActionListener(JButton instance);
        //        gameWindow.spawnPokemans.addActionListener(new ActionListener() { getEnemy });
     //   gameWindow.spawnPokemans.addActionListener(new ActionListener() { 
            /*
            void actionPerformed1(ActionEvent evt) {
                Random rnd = new Random();

               enemy = new Pokemon(rnd.nextInt(151));
            }
            */
      //      performactionthing
     //  });
                //gameWindow.spawnPokemans.setAction(getEnemy);
    }
    
 
    //void actionPerformed1(ActionEvent evt) {
  //      getEnemy();
  //  }
    
 //  void getEnemy(){
            /*
        try {
                String content = readFile("pokeDB.txt", StandardCharsets.UTF_8);
        //	System.out.println(content);
                String[] parts = content.split("(?<=},)");
         //       String part1 = parts[0]; // 004
        //        String part2 = parts[1]; // 034556
         //   System.out.println(parts[0]);
        //    System.out.println("--");                    
         //   System.out.println(parts[1]);

        } catch (IOException ex) {
                Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
      //   Random rnd = new Random();
         
    //    enemy = new Pokemon(rnd.nextInt(151));
  //  }
    
    /*
	static String readFile(String path, Charset encoding) throws IOException{
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
	}
      */  
	@Override
        public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
        }
        
        @Override
        public void windowOpened(WindowEvent e) {
        }
        
        
        @Override
        public void windowActivated(WindowEvent e) {
        }
        
        
        @Override
        public void windowIconified(WindowEvent e) {
        }
        
        
        @Override
        public void windowDeiconified(WindowEvent e) {
        }
        
        
        @Override
        public void windowDeactivated(WindowEvent e) {
        }
        
        
        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Random rnd = new Random();
            enemy = new Pokemon(rnd.nextInt(151));
    //    enemy = new Pokemon(121);
        //    this.PokeInfo.setText(enemy.speciesName);
        }
        
}

/*
REFS:

import java.awt.*;
import java.awt.event.*;

public class AL extends Frame implements WindowListener,ActionListener {
        TextField text = new TextField(20);
        Button b;
        private int numClicks = 0;

        public static void main(String[] args) {
                AL myWindow = new AL("My first window");
                myWindow.setSize(350,100);
                myWindow.setVisible(true);
        }

        public AL(String title) {

                super(title);
                setLayout(new FlowLayout());
                addWindowListener(this);
                b = new Button("Click me");
                add(b);
                add(text);
                b.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
                numClicks++;
                text.setText("Button Clicked " + numClicks + " times");
        }

        public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
        }

        public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}

}



*/