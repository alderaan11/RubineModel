package classifieur;

import algebre.Matrice;
import algebre.Vecteur;
import geste.Geste;
import geste.Lexique;
import geste.Trace;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rubine implements Recognizer{
	private Lexique lexicon;
	private Matrice eotccm; // estimate of the common covariance matrix
	private Matrice inverseEotccm; // inverse of eotccm
	
/*
1) Gestes
-Init Cov -> Init Esp
2) Rubine -> calcul eeotcm -> calcul inverse 
3) InitEstimator -> calcul des poids et biais g`=somme ^-1 g et biais -1/2 <g barre,g`>

 */

	@Override
	public void init(Lexique l) {
		if (l != null){
			this.lexicon = l;
		}
		ArrayList<Geste> gestes = lexicon.getGestes();
		System.out.println("gestes.size() = "+gestes.size());
		int nbGestes = gestes.size();
		double ng = 0;
		for(Geste g : gestes){
			g.initEsperance();
			g.initCovMatrix();
			ng += g.getTraces().size();
		}
		int dimension = gestes.get(0).getCovMatrix().getDimension();
		double[][] sigma = new double[dimension][dimension];


		for (int i = 0; i < dimension; i++) {
			for (int j=0; j < dimension; j++) {
				double b = 0;
				for(Geste g: gestes) {
					 b += g.getCovMatrix().get(i,j);
				}
				sigma[i][j] = b / -gestes.size() + ng;
			}
		}
		this.eotccm = new Matrice(sigma);
		this.inverseEotccm = this.eotccm.inverse();

	}

	@Override
	//le lexique passé en paramètre doit être initialisé avant l'appel à test
	public double[] test(Lexique lexicon) {
		return null; //todo
	}

	@Override
	public Geste recognize(Trace t) {
		return null; //todo
		//pour chaque du lexique
		// mesure = < f,t> + biais
		//-> max des mesures
		//avant de comparer : vérifier que <j^2*distance de mahalanobis> 1/2 * diemnsion des features


	}

	@Override
			public double squaredMahalanobis(Vecteur t, Vecteur g) {
		return 0; //todo
	}


}
