/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


/**
 *
 * @author movillaf
 */
public class TestAMovilla {

    /**
     * @param args the command line arguments
     */
    public Pokemon enemy;
    
    
    
    void performactionthing(ActionEvent evt){
        
         Random rnd = new Random();
         
        enemy = new Pokemon(rnd.nextInt(151));
    }
    
    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
			
		
		
		
		
		//ManejadorPokemon manejadorPokemon = new ManejadorPokemon();
		Thread.sleep(5000);
		GameWindow gameWindow = new GameWindow();
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
    
    void actionPerformed1(ActionEvent evt) {
        getEnemy();
    }
    
   void getEnemy(){
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
                Logger.getLogger(TestAMovilla.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
         Random rnd = new Random();
         
        enemy = new Pokemon(rnd.nextInt(151));
    }
    
    
	static String readFile(String path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
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