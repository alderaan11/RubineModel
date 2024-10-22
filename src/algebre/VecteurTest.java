/*package algebre;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class VecteurTest {

    @Test
    public void testConstructorAndGet() {
        double[] coords = {1.0, 2.0, 3.0};
        Vecteur v = new Vecteur(coords);
        assertEquals(1.0, v.get(0), 1e-9);
        assertEquals(2.0, v.get(1), 1e-9);
        assertEquals(3.0, v.get(2), 1e-9);
    }

    @Test
    public void testProduitScalaire() {
        double[] coords1 = {1.0, 2.0, 3.0};
        double[] coords2 = {4.0, 5.0, 6.0};
        Vecteur v1 = new Vecteur(coords1);
        Vecteur v2 = new Vecteur(coords2);
        assertEquals(32.0, v1.produitScalaire(v2), 1e-9);
    }

    @Test
    public void testNorme() {
        double[] coords = {3.0, 4.0};
        Vecteur v = new Vecteur(coords);
        assertEquals(25.0, v.norme(), 1e-9);
    }

    @Test
    public void testMinus() {
        double[] coords1 = {5.0, 6.0};
        double[] coords2 = {3.0, 4.0};
        Vecteur v1 = new Vecteur(coords1);
        Vecteur v2 = new Vecteur(coords2);
        Vecteur result = v1.minus(v2);
        assertEquals(2.0, result.get(0), 1e-9);
        assertEquals(2.0, result.get(1), 1e-9);
    }

    @Test
    public void testEsperance() {
        ArrayList<Vecteur> lv = new ArrayList<>();
        lv.add(new Vecteur(new double[]{1.0, 2.0}));
        lv.add(new Vecteur(new double[]{3.0, 4.0}));
        Vecteur esperance = Vecteur.esperance(lv);
        assertEquals(2.0, esperance.get(0), 1e-9);
        assertEquals(3.0, esperance.get(1), 1e-9);
    }
}*/