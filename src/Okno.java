import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame implements ActionListener {
	protected JCheckBox skierowany;
	protected JCheckBox nieSkierowany;
	protected JCheckBox etykietyW;
	protected JCheckBox etykietyK;
	protected JButton go;

	Okno() {
		JPanel panel1 = new JPanel();
		Zmienne.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Zmienne.frame.setVisible(true);
		Zmienne.frame.setSize(800, 800);
		Zmienne.frame.setResizable(false);
		// frame.setLayout(new FlowLayout());

		skierowany = new JCheckBox("Graf Skierowny");
		skierowany.addActionListener(this);
		panel1.add(skierowany);

		nieSkierowany = new JCheckBox("Graf NieSkierowny");
		nieSkierowany.addActionListener(this);
		panel1.add(nieSkierowany);

		etykietyW = new JCheckBox("Etykiety Wierzcho³ków");
		etykietyW.addActionListener(this);
		panel1.add(etykietyW);

		etykietyK = new JCheckBox("Etykiety Krawêdzi");
		etykietyK.addActionListener(this);
		panel1.add(etykietyK);

		go = new JButton("Go");
		go.addActionListener(this);
		panel1.add(go);

		Zmienne.frame.add(panel1, BorderLayout.NORTH);
		
		JPanel panel2 = new Malowanko();
		panel2.setBackground(Color.white);
		panel2.setSize(800, 800);
		Zmienne.frame.add(panel2);
		
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (skierowany.isSelected()) {
			Zmienne.skierowana = true;
			
		}
		else Zmienne.skierowana = false;

		if (nieSkierowany.isSelected()) {
			Zmienne.nieSkierowana = true;
			
		}
		else Zmienne.nieSkierowana = false;

		if (etykietyK.isSelected()) {
			Zmienne.etykietyKrawedzi = true;
			
		}
		else Zmienne.etykietyKrawedzi = false;

		if (etykietyW.isSelected()) {
			Zmienne.etykietyWierzcholkow = true;
			
		}
		else Zmienne.etykietyWierzcholkow = false;

		if (source == go) {
			Zmienne.go = true;
		}
	}
}
