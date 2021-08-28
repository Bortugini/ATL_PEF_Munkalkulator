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
     * Diese Methode dient dazu eine {@code ArrayList} mit {@code Cartridges} zurück zu liefern,
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
     * Diese Methode dient dazu eine {@code Cartridge} die aufgrund des indexes in
     * der {@code ArrayList} ausgewählt wurde zurück zu liefern.
     *
     * @param index Stelle im ArrayList des benötigten Objektes
     * @return {@code Cartridge} Patrone
     */
    public Cartridge getCartridge(int index) {
        return getCartridges().get(index);
    }

    /**
     * Diese Methode dient dazu die save Methode aus Implementierungen vom Interface DateiDAO auf zu rufen,
     * um Patronen hinzuzufügen.
     *
     * @param cartridge Patronen Objekt
     *
     * @see AmmoDAO#save(Cartridge)
     * @see Cartridge
     */
    public void addCartridge(Cartridge cartridge) {
        this.ammoDAO.save(cartridge);
    }

    /**
     * Diese Methode dient dazu den gewählten Speicher Konstruktor zu erstellen.
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
