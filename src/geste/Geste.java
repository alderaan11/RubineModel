package geste;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;

import algebre.Matrice;
import algebre.Vecteur;
import algebre.Vecteur2D;
import classifieur.Estimable;

public class Geste implements Estimable{
	private String nom;
	private ArrayList<Trace> traces; 
	private Trace modele;
	private Matrice covariance;
	private Vecteur esperance;
	private Vecteur weightVector;
	private double bias;
	
	public Geste(String nom, Trace model) {
		this.nom = nom;
		this.modele = model;
		this.traces = new ArrayList<Trace>();
	}
		

	public ArrayList<Trace> getTraces() {
		return this.traces;
	}

	public void addTrace(Trace t) {
		traces.add(t);	
	}

	public Trace get(int i) {
		return traces.get(i);
	}

	public void clear() {
		traces.clear();		
	}

	public String getName() {
		return nom;
	}

	public void hide() {
		for (Trace t: traces)
			t.setVisible(false);
	}

	public void drawModel(Graphics2D g) {
		if (modele == null) System.out.println("warning in drawModel(g2d)@Geste ->this model is null");
		else {
			g.translate(20, 20);
			modele.drawLines(g);
		}
	}

	public void drawModel(Graphics2D g2d, int i, int j) {
		if (modele == null) System.out.println("warning in drawModel(g2d,i,j)@Geste ->this model is null");
		else {
			AffineTransform t = g2d.getTransform();
			g2d.translate(20*i, 20*j);
			modele.drawLines(g2d);
			g2d.setTransform(t);
		}
		
	}

	public void initCovMatrix() {
		ArrayList<Vecteur> f = new ArrayList<Vecteur>();
		for (Trace t: traces) {
			f.add(t.getFeatureVector());
		}
		this.covariance = Matrice.covariance(f);
	}

	public void initEsperance() {
		ArrayList<Vecteur> f = new ArrayList<Vecteur>();

		for (Trace t: traces) {
			t.initFeatures();
			f.add(t.getFeatureVector());
		}

		this.esperance = Vecteur.esperance(f);
	}
	public void initEstimators(Matrice inverseEotccm) {
		this.weightVector = inverseEotccm.mult(this.esperance);
		this.bias = -(1.0/2.0) * this.weightVector.produitScalaire(this.esperance);

	}

	public Matrice getCovMatrix() {
		return this.covariance;
	}


	public Vecteur getWeightVector() {
		return null; //todo
	}
	public double getBias() {
		return 0; //todo
	}

	public Vecteur getEsperance() {
		return this.esperance;
	}
	public void initFeatures() {
		for (Trace t: traces) {
			t.initFeatures();
		}
	}



}
