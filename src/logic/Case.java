package logic;

/**
 * Repräsentiert eine Hülse einer Patrone.
 * Für Spätere erweiterung gedacht.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Case extends AmmoPart {

    /**
     * Konstruktor erstellt eine neue Instanz einer Hülse ruft den super Konstruktor aus AmmoPart auf.
     *
     * @param name Name der Hülse
     * @param amountPack Packungsinhalt
     * @param pricePack Preis pro Packung
     *
     * @see AmmoPart
     */
    public Case(String name, int amountPack, double pricePack) {
        super(name, amountPack, pricePack);
    }

}
