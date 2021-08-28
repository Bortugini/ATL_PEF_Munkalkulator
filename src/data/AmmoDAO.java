package data;

import logic.Cartridge;

import java.util.ArrayList;

/**
 * Interface um {@code Cartridge} zu Speichern.
 * Dieses Interface muss implementiert werden,
 * um eine neue variante um Patronen zu Speichern zur verfügung zu stellen.
 *
 * @see Cartridge
 *
 * @author Thomas Saner
 * @version 1.0
 */
public interface AmmoDAO {

    /**
     * Implementierung die eine {@code Cartridge} Liste zurückliefert.
     *
     * @return {@code Cartridge} als ArrayList
     */
    ArrayList<Cartridge> getCartridge();

    /**
     * Implementierung um {@code Cartridge} zu Speichern.
     *
     * @param cartridge Patronen Objekt
     * @see Cartridge
     */
    void save(Cartridge cartridge);
}
