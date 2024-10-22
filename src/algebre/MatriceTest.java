package algebre;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MatriceTest {

    @Test
    public void testMultVecteur() {
        double[][] coef = {{1, 2}, {3, 4}};
        Matrice m = new Matrice(coef);
        Vecteur v = new Vecteur(new double[]{1, 1});
        Vecteur result = m.mult(v);
        assertEquals(3.0, result.get(0));
        assertEquals(7.0, result.get(1));
    }

    @Test
    public void testMultMatrice() {
        double[][] coef1 = {{1, 2}, {3, 4}};
        double[][] coef2 = {{2, 0}, {1, 2}};
        Matrice m1 = new Matrice(coef1);
        Matrice m2 = new Matrice(coef2);
        Matrice result = m1.mult(m2);
        assertEquals(4.0, result.get(0, 0));
        assertEquals(4.0, result.get(0, 1));
        assertEquals(10.0, result.get(1, 0));
        assertEquals(8.0, result.get(1, 1));
    }


}