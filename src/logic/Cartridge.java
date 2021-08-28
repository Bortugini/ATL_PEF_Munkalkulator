package logic;

/**
 * Repräsentiert eine Patrone.
 * Eine Patrone besteht aus einem Geschoss {@code Bullet}, einer Hülse {@code Case},
 * Pulver {@code Powder}, einem Zündhütchen {@code Primer}, und einem Namen {@code String}.
 *
 * @see Bullet
 * @see Case
 * @see Powder
 * @see Primer
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Cartridge {

    /**
     * @see Bullet
     */
    private final Bullet bullet;
    /**
     * @see Case
     */
    private final Case aCase;
    /**
     * @see Powder
     */
    private final Powder powder;
    /**
     * @see Primer
     */
    private final Primer primer;
    /**
     * Name der Patrone
     */
    private final String nameCartridge;

    /**
     * Konstruktor erstellt eine neue Instanz für eine Patrone.
     *
     * @param bullet Bullet Objekt
     * @param aCase Case Objekt
     * @param powder Powder Objekt
     * @param primer Primer Objekt
     * @param nameCartridge Name der Patrone
     *
     * @see Bullet
     * @see Case
     * @see Powder
     * @see Primer
     */
    public Cartridge(Bullet bullet, Case aCase, Powder powder, Primer primer, String nameCartridge) {
        this.bullet = bullet;
        this.aCase = aCase;
        this.powder = powder;
        this.primer = primer;
        this.nameCartridge = nameCartridge;
    }

    /**
     * Diese Methode dient dazu den Namen der Patrone Zurückzuliefern.
     *
     * @return {@code String} Name
     */
    public String getNameCartridge() {
        return nameCartridge;
    }

    /**
     * Diese Methode dient dazu das {@code Bullet} Objekt Zurückzuliefern.
     *
     * @return {@code Bullet} Objekt
     * @see Bullet
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Diese Methode dient dazu das {@code Case} Objekt Zurückzuliefern.
     *
     * @return {@code Case} Objekt
     */
    public Case getCase() {
        return aCase;
    }

    /**
     * Diese Methode dient dazu das {@code Powder} Objekt Zurückzuliefern.
     *
     * @return {@code Powder} Objekt
     */
    public Powder getPowder() {
        return powder;
    }

    /**
     * Diese Methode dient dazu das {@code Primer} Objekt Zurückzuliefern.
     *
     * @return {@code Primer} Objekt
     */
    public Primer getPrimer() {
        return primer;
    }

    /**
     * Diese Methode dient dazu den Preis für die Anzahl Patronen zu berechnen und liefert diesen zurück.
     *
     * @param quantity Anzahl Patronen
     * @return {@code double} Preis für anzahl Patronen
     */
    public double calc(int quantity) {
        double price = this.bullet.getPricePcs() + this.aCase.getPricePcs() + this.powder.getPricePcs() + this.primer.getPricePcs();
        price = price * quantity;
        return price;
    }

    /**
     * Diese Methode dient dazu eine {@code String} Representation des Objektes Zurückliefern.
     *
     * @return {@code String} Representation des Objektes
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cartridge{");
        sb.append("bullet=").append(bullet);
        sb.append(", aCase=").append(aCase);
        sb.append(", powder=").append(powder);
        sb.append(", primer=").append(primer);
        sb.append(", nameCartridge='").append(nameCartridge).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
