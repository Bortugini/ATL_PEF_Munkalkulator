package test;

import logic.*;

/**
 * Test Klasse um die funktion der Berechnungen zu überprüfen.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Bullet bullet = new Bullet("Sierra", 100, 65);
        Powder powder = new Powder("RS 52", 1, 99, 43.8, "kg", "grs");
        Case aCase = new Case("Norma", 1, 0);
        Primer primer = new Primer("Fiocchi", 150, 6.8);
        Cartridge cartridge = new Cartridge(bullet, aCase, powder, primer, "308");

        System.out.println("Berechnung Test1 " + isEqual(cartridge));

        Bullet bullet1 = new Bullet("Sierra", 100, 65);
        Powder powder1 = new Powder("RS 52", 1000, 99, 2.838192258, "g", "g");
        Case case1 = new Case("Norma", 1, 0);
        Primer primer1 = new Primer("Fiocchi", 150, 6.8);
        Cartridge cartridge1 = new Cartridge(bullet, aCase, powder, primer, "308 2");

        System.out.println("Berechnung Test2 " + isEqual(cartridge1));

    }

    /**
     * Diese Methode dient dazu zu überprüfen, ob die Patrone korrekt berechnet wird.
     * Es wird true zurückgeliefert, wenn die berechnungen mit result übereinstimmt.
     *
     * @param cartridge Patronen Objekt
     * @return boolean gibt true zurück, wenn berechnung mit resultat übereinstimmt.
     * @see Cartridge
     */
    private static boolean isEqual(Cartridge cartridge) {
        double result = 0.9763143668760877;
        return result == cartridge.calc(1);
    }
}
