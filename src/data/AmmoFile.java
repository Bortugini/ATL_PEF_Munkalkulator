package data;

import logic.Cartridge;

import java.util.ArrayList;

/**
 * Factory für die erstellung verschiedener Speicher Objekte.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class AmmoFile {

    /**
     * @see AmmoDAO
     */
    private AmmoDAO ammoDAO;

    /**
     * Erstellt eine neue Instanz für ein {@code AmmoFile}.
     *
     * @param storage Konstante aus enum {@code Storage}
     *
     * @see Storage
     */
    AmmoFile(Storage storage) {
        this.setDAO(storage);
    }

    /**
     * Liefert eine {@code ArrayList} mit {@code Cartridges} zurück,
     * aus Implementierungen vom Interface {@code AmmoDAO}.
     *
     * @return {@code ArrayList<Cartridge>} Patronen
     *
     * @see AmmoDAO#getCartridge()
     * @see Cartridge
     */
    public ArrayList<Cartridge> getCartridges() {
        return this.ammoDAO.getCartridge();
    }

    /**
     * Liefert eine {@code Cartridge} die aufgrund des indexes in
     * der {@code ArrayList} ausgewählt wurde zurück.
     *
     * @param index Stelle im ArrayList des benötigten Objektes
     * @return {@code Cartridge} Patrone
     */
    public Cartridge getCartridge(int index) {
        return getCartridges().get(index);
    }

    /**
     * Ruft die save Methode aus Implementierungen vom Interface DateiDAO auf.
     * Um Patronen hinzuzufügen.
     *
     * @param cartridge Patronen Objekt
     * @see AmmoDAO#save(Cartridge)
     * @see Cartridge
     */
    public void addCartridge(Cartridge cartridge) {
        this.ammoDAO.save(cartridge);
    }

    /**
     * Diese methode erstellt den gewählten Speicher Konstruktor.
     * Aufgrund des Parameters {@code storage} im Konstruktor.
     *
     * @param storage Gewählte Speicher Methode aus enum {@code Storage}
     */
    private void setDAO(Storage storage) {
        switch (storage) {
            case MEMORY:
                ammoDAO = new AmmoDAOMemory();
                break;
            case TXT:
                ammoDAO = new AmmoDAOTXT();
                break;
        }
    }
}
