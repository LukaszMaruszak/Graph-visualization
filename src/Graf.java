import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Scanner;

class Node {
	Point p;
	String id;
	String nazwa;

	Node(String id, Point p) {
		this.id = id;
		this.p = p;
	}

	public Point getPoint() {
		return this.p;
	}

	public String getNazwa() {
		return String.valueOf(id);
	}

	public String toString() {
		return this.id + " " + this.p;
	}
}

class Edge {
	Node n1;
	Node n2;
	String etykieta;

	Edge(Node n1, Node n2, String etykieta) {
		this.n1 = n1;
		this.n2 = n2;
		this.etykieta = etykieta;
	}

	public String Etykietka() {
		return this.etykieta;
	}

	public String toString() {
		return this.n1.id + " " + this.n2.id + " " + this.etykieta;
	}

}

public class Graf {
	public ArrayList<Point> wspolrzedneWierzcholka = new ArrayList<Point>();
	public int size;
	public int sizeEdge;
	public int iloscwierzchlkow = 0;
	public ArrayList<Node> node = new ArrayList<Node>();
	public static ArrayList<Node> nodeNazwy = new ArrayList<Node>();
	public ArrayList<Edge> edge = new ArrayList<Edge>();

	Graf() throws FileNotFoundException {

		Scanner scanner = new Scanner(new File("graf.txt"));
		String temp;
		String[] data;

		this.size = scanner.nextInt();
		wspolrzedneWierzcholka = obliczPunkty(size);
		this.sizeEdge = scanner.nextInt();

		scanner.nextLine();
		for (int i = 0; i < size; ++i) {

			Node n = new Node(scanner.nextLine().trim(), wspolrzedneWierzcholka.get(i));
			iloscwierzchlkow++;
			node.add(n);
			nodeNazwy.add(n);
		}
		scanner.nextLine();

		while (scanner.hasNextLine()) {
			temp = scanner.nextLine();
			data = temp.split(",");

			Edge newEdge = new Edge(szukaj(data[0].trim()), szukaj(data[1].trim()), data[2].trim());
			edge.add(newEdge);

			/*
			 * Edge newEdge = new Edge(node.get(Integer.parseInt(data[0].trim())),
			 * node.get(Integer.parseInt(data[1].trim())), data[2].trim());
			 * edge.add(newEdge);
			 */

		}

		scanner.close();
	}

	public static Node szukaj(String text) {

		for (int i = 0; i < nodeNazwy.size(); ++i) {
			if (nodeNazwy.get(i).id.equals(text)) {
				return nodeNazwy.get(i);
			}
		}

		return null;

	}

	public static ArrayList<Point> obliczPunkty(int ilosc) {
		ArrayList<Point> punkty = new ArrayList<Point>();
		double n = (Math.PI * 2) / ilosc;
		for (int i = 0; i < ilosc; ++i) {
			int x = (int) (Math.cos(n * i) * 300) + 370;
			int y = (int) (Math.sin(n * i) * 300) + 350;
			Point p = new Point(x, y);
			punkty.add(p);
		}
		return punkty;
	}

	public static ArrayList<Point> obliczPunktyNapisu(int ilosc) {
		ArrayList<Point> punkty = new ArrayList<Point>();
		double n = (Math.PI * 2) / ilosc;
		for (int i = 0; i < ilosc; ++i) {
			int x = (int) (Math.cos(n * i) * 340) + 370;
			int y = (int) (Math.sin(n * i) * 340) + 360;
			Point p = new Point(x, y);
			punkty.add(p);
		}
		return punkty;
	}

	// rysuje wierzcholki - punkty
	public void draw(Graphics g) {

		for (int i = 0; i < node.size(); ++i) {
			g.setColor(new Color(9, 186, 249));
			g.fillOval(node.get(i).getPoint().x, node.get(i).getPoint().y, 25, 25);
			// System.out.println(node);
		}

	}

	// rysuje etykiety nad wierzcholkami
	public void drawEtykietyWierzcholkow(Graphics g) {
		for (int j = 0; j < node.size(); ++j) {
			ArrayList<Point> punkty = new ArrayList<Point>();
			punkty = obliczPunktyNapisu(size);
			g.setFont(new Font("Arial", Font.BOLD, 13));
			g.setColor(Color.black);
			g.drawString(node.get(j).getNazwa(), punkty.get(j).x, punkty.get(j).y);
		}
	}

	// rysuje krawedzie nieskierowane
	public void drawEdge(Graphics e) {
		Graphics2D g = (Graphics2D) e;
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3.0f));

		for (int i = 0; i < edge.size(); ++i) {

			g.drawLine(edge.get(i).n1.getPoint().x + 12, edge.get(i).n1.getPoint().y + 12,
					edge.get(i).n2.getPoint().x + 12, edge.get(i).n2.getPoint().y + 12);
		}
	}


	// rysuje krawedzie skierwane
	public void drawSkierowane(Graphics e) {
		Graphics2D g = (Graphics2D) e;
		int x1, x2, y1, y2;

		for (int i = 0; i < edge.size(); ++i) {
			x1 = edge.get(i).n1.getPoint().x + 12;
			y1 = edge.get(i).n1.getPoint().y + 12;
			x2 = edge.get(i).n2.getPoint().x + 12;
			y2 = edge.get(i).n2.getPoint().y + 12;

			drawStrzalki(g, x1, y1, x2, y2);
		}

	}

	// metoda do rysowanie strzalek na krawedziach
	public void drawStrzalki(Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3.0f));
		int ARR_SIZE = 9;

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		// transform the draw
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g2.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g2.drawLine(0, 0, len, 0);
		len = len + 75;
		g2.fillPolygon(new int[] { len / 2, len / 2 - ARR_SIZE, len / 2 - ARR_SIZE },
				new int[] { 0, -ARR_SIZE, ARR_SIZE }, 3);

		try {
			g2.transform(at.createInverse());
		} catch (NoninvertibleTransformException ex) {
		}
	}

	// rysowanie etykiet krawedzi
	public void drawEtykietyKrawdzi(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(new Color(9, 96, 170));

		for (int i1 = 0; i1 < edge.size(); ++i1) {
			int x = (edge.get(i1).n2.p.x + 3 * edge.get(i1).n1.p.x) / 4;
			int y = (edge.get(i1).n2.p.y + 3 * edge.get(i1).n1.p.y) / 4;
			g.drawString(edge.get(i1).Etykietka(), x, y + 9);
		}
	}

	public static Node znajdzWierzcholek(String w) {
		Node liczba = null;
		for (int i = 0; i < nodeNazwy.size(); ++i) {
			if ((nodeNazwy.get(i).id).equals(w)) {
				liczba = nodeNazwy.get(i);
				System.out.println(nodeNazwy.get(i).id);
			} else
				liczba = null;
		}
		return liczba;
	}

}
