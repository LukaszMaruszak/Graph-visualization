import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

public class Malowanko extends JPanel {

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		try {
			if (Zmienne.go == true) {
				Graf graf = new Graf();
				
				
				if (Zmienne.etykietyWierzcholkow == true) {
					graf.drawEtykietyWierzcholkow(g);
				}

				if (Zmienne.nieSkierowana == true) {
					graf.drawEdge(g);
				}

				if (Zmienne.skierowana == true) {
					graf.drawSkierowane(g);
				}
				
				if (Zmienne.etykietyKrawedzi == true) {
					graf.drawEtykietyKrawdzi(g);
				}


				graf.draw(g);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
