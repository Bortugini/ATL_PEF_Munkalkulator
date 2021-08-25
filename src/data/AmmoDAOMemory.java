package data;

import logic.Cartridge;

import java.util.ArrayList;

/**
 * Implementation um Munitions-Teile im Memory zu Speichern und zu Laden.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class AmmoDAOMemory implements AmmoDAO {

    /**
     * @see AmmoDAO
     */
    private final ArrayList<Cartridge> cartridges = new ArrayList<>();

    /**
     * @see AmmoDAO#getCartridge()
     */
    @Override
    public ArrayList<Cartridge> getCartridge() {
        return cartridges;
    }

    /**
     * @see AmmoDAO#save(Cartridge)
     */
    @Override
    public void save(Cartridge cartridge) {
        cartridges.add(cartridge);
    }
}
