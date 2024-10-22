package geste;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import algebre.Vecteur;
import algebre.Vecteur2D;
import classifieur.Featured;
import ui.Style;
import ui.io.ReadWritePoint;

public class Trace implements Featured{
	private ArrayList<PointVisible> points;
	private Style style = new Style();
	private Vecteur features;
	private boolean visible;

	public Trace(boolean model) {
		if (model)
			style = Style.getModelStyle();
		points = new ArrayList<PointVisible>();
		visible = true;
	}

	public Trace(boolean model, String fileName) {
		this(model);
		File f = new File(fileName);
		ReadWritePoint rwp = new ReadWritePoint(f);
		points = rwp.read();
	}

	public void add(Point p, long timeStamp) {
		add(new PointVisible(p.x, p.y, timeStamp));
	}

	public void add(PointVisible p) {
		points.add(p);
	}

	public void showInfos(Graphics2D g) {
		Rectangle r = computeBoundingBox();
		String features = points.size() + " points ";
		g.translate(-r.x, -r.y);
		g.scale(2, 2);
		g.drawString(features, r.x, r.y - 10);
		g.scale(.5, .5);
		g.translate(r.x, r.y);
	}

	public void draw(Graphics2D g) {
		if (visible) {
			if (style.drawLine()) {
				drawLines(g);
			}
			if (style.drawPoints()) {
				drawPoints(g);
			}
			showInfos(g);
		}
	}

	public void drawPoints(Graphics2D g) {
		for (PointVisible p : points) {
			p.dessine(g, style);
		}
	}

	public void drawLines(Graphics2D g) {
		PointVisible p1, p2;
		//g.setColor(style.color());
		g.setColor(new Color(128,128,128));
		for (int i = 0; i < points.size() - 1; i++) {
			p1 = points.get(i);
			p2 = points.get(i + 1);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	public Rectangle computeBoundingBox() {
		int minx, miny, maxx, maxy;
		minx = points.get(0).x;
		maxx = points.get(0).x;
		miny = points.get(0).y;
		maxy = points.get(0).y;
		for (PointVisible p : points) {
			if (p.x < minx)
				minx = p.x;
			if (p.y < miny)
				miny = p.y;
			if (p.x > maxx)
				maxx = p.x;
			if (p.y > maxy)
				maxy = p.y;
		}
		return new Rectangle(minx, miny, maxx - minx, maxy - miny);
	}
	private double[] getMinMax() {
		double[] res = new double[4];
		int xMax = points.getFirst().x;
		int yMax = points.getFirst().y;
		int yMin = points.getFirst().y;
		int xMin = points.getFirst().x;
		for (PointVisible p : points) {
			if (p.x > xMax) xMax = p.x;
			if (p.y > yMax) yMax = p.y;
			if (p.x < xMin) xMin = p.x;
			if (p.y < yMin) yMin = p.y;
		}
		res[0] = xMax; res[1] = yMax; res[2] = xMin; res[3] = yMin;
		return res;
	}
	private double featureOne() {
		if (points.size() >= 3){
			PointVisible firstPoint = points.getFirst();
			double xZero = firstPoint.x;
			double yZero = firstPoint.y;

			PointVisible thirdPoint = points.get(2);
			double xTwo = thirdPoint.x;
			double yTwo = thirdPoint.y;

			return (xTwo - xZero) / Math.sqrt(Math.pow(xTwo - xZero, 2) + Math.pow(yTwo - yZero, 2));
		}
		return -1;
	}
	private double featureTwo() {
		double f2 = this.points.get(2).y - this.points.get(0).y /
				Math.sqrt(Math.pow((this.points.get(2).x - this.points.get(0).x),2) +
						Math.pow((this.points.get(2).y - this.points.get(0).y),2));
		return f2;
	}
	public double featureThree() {
		double[] valeurs = getMinMax();
		return Math.sqrt(
				(Math.pow(valeurs[0] - valeurs[2], 2))
						+ (Math.pow(valeurs[1] - valeurs[3], 2)));
	}
	private double featureFour() {
		double f4;
		double[] xymm = getMinMax();
		Vecteur2D v2d = new Vecteur2D(xymm[0]-xymm[2], xymm[1]-xymm[3]);
		f4 = v2d.angle();
		return f4;
	}
	public double featureFive() {
		double f5 = Math.sqrt( Math.pow((this.points.get(this.points.size()-1).x -
				this.points.get(0).x),2) + Math.pow((this.points.get(this.points.size()-1).y -
				this.points.get(0).y),2)  );
		return f5;
	}

	private double featureTwelve(){
		double f12 = 0;
		for(int p=0; p<this.points.size()-1; p++) {
			double deltax = this.points.get(p+1).x - this.points.get(p).x;
			double deltay = this.points.get(p+1).y - this.points.get(p).y;
			double deltat = this.points.get(p+1).getTimeStamp() - this.points.get(p).getTimeStamp();
			double val = (Math.pow(deltax,2) + Math.pow(deltay,2) / Math.pow(deltat,2));
			if(val > f12) f12 = val;
		}
		return f12;
	}
	public double featureThirteen() {
		return points.get(points.size()-1).getTimeStamp() - points.get(0).getTimeStamp();
	}
	public void initFeatures() {
		this.features = new Vecteur(new double[] {this.featureOne(), this.featureTwo(), this.featureThree(), this.featureFour(), this.featureFive(), this.featureTwelve(), this.featureThirteen()});
	}

	public int exportWhenConfirmed(String filePath) {
		Path p = Paths.get(filePath);
		int userInput = JOptionPane.NO_OPTION;
		if (Files.exists(p)) {
			userInput = JOptionPane.showConfirmDialog(null,
					p.getFileName() + ": file exists, overwrite existing file ?", "", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			System.out.println("Export cancelled");
			if (userInput != JOptionPane.YES_OPTION)
				return userInput;
		}
		export(filePath, true);
		return userInput;
	}

	private void export(String path, boolean overwrite) {
		File f = new File(path);
		if (f.exists() && !overwrite)
			return;
		ReadWritePoint rw = new ReadWritePoint(f);
		Rectangle r = computeBoundingBox();
		int x, y;
		for (PointVisible p : points) {
			x = p.x - r.x;
			y = p.y - r.y;
			rw.add(x + ";" + y + ";" + p.getTimeStamp());
		}
		rw.write();
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public Vecteur getFeatureVector() {
		return this.features;	}

	public int size() {
		return points.size();
	}



}
