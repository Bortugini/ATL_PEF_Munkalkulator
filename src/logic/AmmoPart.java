package logic;

import java.util.StringJoiner;

/**
 * Repräsentiert Munitionsteile.
 * Super Klasse AmmoPart.
 * Abstrakte Klasse, um Munitionsteile darzustellen.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public abstract class AmmoPart {

    /**
     * Name des Munitions-Teiles (Bezeichnung)
     */
    private String name;
    /**
     * Preis pro Stk.
     */
    private double pricePcs;
    /**
     * Preis für ein ganzes Pack.
     */
    private final double pricePack;
    /**
     * Anzahl Stk. im Pack.
     */
    private int amountPack;

    /**
     * Konstruktor erstellt eine neue Instanz für ein Munitions-Teil.
     *
     * @param name Name des Munitions-Teiles
     * @param pricePack Preis pro Packung
     * @param amountPack Menge pro Packung
     */
    public AmmoPart(String name, int amountPack, double pricePack) {
        this.name = name;
        this.amountPack = amountPack;
        this.pricePcs = pricePack / this.amountPack;
        this.pricePack = pricePack;
    }

    /**
     * Diese Methode dient dazu den Namen des Teiles Zurückzuliefern.
     *
     * @return {@code String} Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Diese Methode dient dazu den Parameter {@code name} (Namen des Teiles) zu ändern.
     * Es darf ein beliebiger {@code String} als Parameter übergeben werden.
     *
     * @param name Name des Teiles als {@code String}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Diese Methode dient dazu den Preis eines Stk. Zurückzuliefern.
     *
     * @return {@code double} Preis pro Stk.
     */
    public double getPricePcs() {
        return this.pricePcs;
    }

    /**
     * Diese Methode dient dazu den Parameter {@code pricePcs} (Preis pro Stk) zu ändern.
     * Es sind sämtliche {@code Double} werte erlaubt inkl. 0.
     *
     * @param pricePcs Preis pro Stk.
     */
    public void setPricePcs(double pricePcs) {
        this.pricePcs = pricePcs;
    }

    /**
     * Diese Methode dient dazu die Menge pro Packung Zurückzuliefern.
     *
     * @return {@code int} Menge pro Packung
     */
    public int getAmountPack() {
        return this.amountPack;
    }

    /**
     * Diese Methode dient dazu den Preis für ein ganzes Pack an Teilen Zurückzuliefern.
     *
     * @return {@code double} Preis pro Packung.
     */
    public double getPricePack() {
        return pricePack;
    }

    /**
     * Diese Methode dient dazu den Parameter {@code amountPack} (Menge pro Packung) zu ändern.
     * Es sind sämtliche {@code int} erlaubt ausser 0.
     *
     * @param amountPack Menge pro Packung
     */
    public void setAmountPack(int amountPack) {
        this.amountPack = amountPack;
    }

    /**
     * Diese Methode dient dazu alle Parameter des Objektes als {@code String} Zurückzuliefern.
     * In folgendem Format:<br/>
     * {@code Class:name:pricePcs:amountPack:pricePack}
     *
     * @return {@code String} Format: {@code Class:name:pricePcs:amountPack:pricePack}
     */
    public String getData() {
        return String.format(getClass().getSimpleName() + ":" + name + ":" + pricePcs + ":"
                + amountPack + ":" + pricePack);
    }

    /**
     * Diese Methode dient dazu eine {@code String} Representation des Objektes Zurückzuliefern.
     *
     * @return {@code String} Representation des Objektes
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", AmmoPart.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("preisStk=" + pricePcs)
                .add("preisPackung=" + pricePack)
                .add("mengePackung=" + amountPack)
                .toString();
    }
}
