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
     * @see AmmoPart
     */
    public Bullet(String name, int amountPack, double pricePack) {
        super(name, amountPack, pricePack);
    }

}
