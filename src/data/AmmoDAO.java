package data;

import logic.Cartridge;

import java.util.ArrayList;

/**
 * Interface um {@code Cartridge} zu Speichern.
 * Dieses Interface muss implementiert werden,
 * um eine neue Variante um Patronen zu Speichern zur verfügung zu stellen.
 *
 * @author Thomas Saner
 * @version 1.0
 * @see Cartridge
 */
public interface AmmoDAO {

    /**
     * Implementierung die eine {@code Cartridge} Liste zurückliefert.
     *
     * @return {@code Cartridge} als ArrayList
     */
    ArrayList<Cartridge> getCartridges();

    /**
     * Implementierung um {@code Cartridge} zu Speichern.
     *
     * @param cartridge Patronen Objekt
     * @see Cartridge
     */
    void addCartridge(Cartridge cartridge);

    /**
     * Implementierung um eine ArrayList mit {@code Cartridge} zu Speichern.
     *
     * @param cartridges ArrayList mit {@code Cartridge}
     */
    void addCartridges(ArrayList<Cartridge> cartridges);

    /**
     * Implementierung um die ArrayList zu Leeren.
     */
    void clear();
}

