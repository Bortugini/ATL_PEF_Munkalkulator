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
     * @see AmmoDAO#getCartridges()
     */
    @Override
    public ArrayList<Cartridge> getCartridges() {
        return cartridges;
    }

    /**
     * @see AmmoDAO#addCartridge(Cartridge)
     */
    @Override
    public void addCartridge(Cartridge cartridge) {
        this.cartridges.add(cartridge);
    }

    /**
     * @see AmmoDAO#addCartridges(ArrayList)
     */
    @Override
    public void addCartridges(ArrayList<Cartridge> cartridges) {
        this.cartridges.addAll(cartridges);
    }

    /**
     * @see AmmoDAO#clear()
     */
    @Override
    public void clear() {
        cartridges.clear();
    }
}
