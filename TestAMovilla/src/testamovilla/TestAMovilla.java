/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testamovilla;


/**
 *
 * @author movillaf
 */
public class TestAMovilla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException{
		
		SplashWindow splash = new SplashWindow();
			
		/*
		try {
			String content = readFile("pokeDB.txt", StandardCharsets.UTF_8);
			System.out.println(content);
			System.out.println(content[1]);
		} catch (IOException ex) {
			Logger.getLogger(TestAMovilla.class.getName()).log(Level.SEVERE, null, ex);
		}
		*/
		
		
		//ManejadorPokemon manejadorPokemon = new ManejadorPokemon();
		Thread.sleep(5000);
		//GameWindow gameWindow = new GameWindow();
		splash.setVisible(false);
		splash = null;
    }
    /*
	static String readFile(String path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	*/
}