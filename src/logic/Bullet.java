package logic;

/**
 * Repräsentiert das Geschoss einer Patrone.
 * Für Spätere erweiterung gedacht.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Bullet extends AmmoPart {


    /**
     * Konstruktor erstellt eine neue Instanz eies Geschosses ruft den super Konstruktor aus AmmoPart auf.
     *
     * @param name Name des Geschosses
     * @param amountPack Packungsinhalt
     * @param pricePack Preis pro Packung
     *
     * @see AmmoPart
     */
    public Bullet(String name, int amountPack, double pricePack) {
        super(name, amountPack, pricePack);
    }

}
