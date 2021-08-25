package logic;

/**
 * Repräsentiert das Pulver einer Patrone.
 * Grs ist die Abkürzung für Grain amerikanische Gewichtseinheit.
 * Sie wird für die Gewichtsangabe des Pulvers oder des Geschosses in Patronen verwendet.
 *
 * @author Thomas Saner
 * @version 1.0
 */

public class Powder extends AmmoPart {

    /**
     * Verladene Menge Pulver
     */
    private double amountLoaded;

    /**
     * Einheit der Packung
     */
    private String unitPack;

    /**
     * Einheit der verladenen Menge
     */
    private String unitAmountLoaded;

    /**
     * Konstante die den Umrechnungsfaktor repräsentiert,
     * wird für die umrechnung von grs in g benötigt.<br/>
     * Quellen:<br />
     *<a href="https://www.translatorscafe.com/unit-converter/de-DE/mass/2-46/gram-grain/">Quelle 1 </a><br/>
     * <a href="https://de.wikipedia.org/wiki/Gran_(Einheit)">Quelle 2</a>
     */
    private static final double GRAIN_FACTOR = 15.4323583529;


    /**
     * Erstellt eine neue Instanz für ein {@code Powder} Objekt.
     *
     * @param name Name des Pulvers
     * @param amountPack Menge pro Packung Pulver in g
     * @param pricePack Preis pro Packung Pulver
     * @param amountLoaded Verladene Menge Pulver pro Patrone
     * @param unitPack Einheit der Verpackung kg oder g
     * @param unitAmountLoaded Einheit der verladenen Menge grs oder g
     */
    public Powder(String name, int amountPack, double pricePack, double amountLoaded, String unitPack, String unitAmountLoaded) {
        super(name, amountPack, pricePack);
        this.amountLoaded = amountLoaded;
        this.unitPack = unitPack;
        this.unitAmountLoaded = unitAmountLoaded;
        super.setPricePcs(getPricePerGrs(pricePack, amountPack, amountLoaded));
    }

    /**
     * Liefert die Einheit der Packung zurück.
     *
     * @return {@code String} mit der einheit
     */
    public String getUnitPack() {
        return unitPack;
    }

    /**
     * Liefert die Einheit der verladenen Menge zurück.
     *
     * @return {@code String} mit der Einheit
     */
    public String getUnitAmountLoaded() {
        return unitAmountLoaded;
    }

    /**
     * Liefert die verladene Menge Pulver zurück in grs.
     *
     * @return {@code double} Verladene Menge Pulver
     */
    public double getAmountLoaded() {
        return this.amountLoaded;
    }

    /**
     * @return {@code String} mit allen daten in folgendem Format:<br/>
     * {@code Class:name:pricePcs:amountPack:pricePack:amountLoaded}
     *
     * @see AmmoPart#getData()
     */
    @Override
    public String getData() {
        String data = super.getData();
        return String.format(data + ":" + amountLoaded);
    }

    /**
     * Mit dieser Methode wird berechnet was ein grs Pulver kostet.
     * Ausserdem rechnet die Methode die Einheiten von Gramm in grain
     * bei der verladenen Menge Pulver und Kilogramm in Gramm bei der Menge pro Packung um.
     * Dies ist wichtig, weil sonst die Formel für die Berechnung des Preises pro Packung nicht stimmen würde.
     *
     * @param pricePack Preis pro Packung
     * @param amountPack Menge pro Packung in g
     * @param amountLoaded verladene Menge in grs
     * @return {@code double} Preis pro grs
     */
    private double getPricePerGrs(double pricePack, int amountPack, double amountLoaded) {

        if (this.unitPack.equals("kg")) {
            amountPack = amountPack * 1000;
            setAmountPack(amountPack);
            this.unitPack = "g";
        }
        if (this.unitAmountLoaded.equals("g")) {
            this.amountLoaded = amountLoaded * GRAIN_FACTOR;
            this.unitAmountLoaded = "grs";
        }
        //Berechnung vom Preis von einem g Pulver
        pricePack /= amountPack;
        /*
        Erster Schritt umrechnung von g in grs
        Zweiter Schritt Berechnung des Preises der Verladenen menge
        */
        pricePack = pricePack / GRAIN_FACTOR * amountLoaded;
        return pricePack;
    }
}
