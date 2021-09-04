package logic;

/**
 * Repräsentiert das Zündhütchen einer Patrone.
 * Für Spätere erweiterung gedacht.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Primer extends AmmoPart {


    /**
     * Konstruktor erstellt eine neue Instanz eines Zünders ruft den super Konstruktor aus AmmoPart auf.
     *
     * @param name       Name des Zündhütchen
     * @param amountPack Packungsinhalt
     * @param pricePack  Preis pro Packung
     * @see AmmoPart
     */
    public Primer(String name, int amountPack, double pricePack) {
        super(name, amountPack, pricePack);
    }
}
