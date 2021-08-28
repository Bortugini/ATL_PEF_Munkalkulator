package data;

import logic.Cartridge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Implementation um Munitions-Teile als TXT Datei zu Speichert und zu Laden.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class AmmoDAOTXT implements AmmoDAO {

    /**
     * @see AmmoDAO
     */
    private final ArrayList<Cartridge> cartridges = new ArrayList<>();

    /**
     * @see AmmoDAO#getCartridge()
     */
    public ArrayList<Cartridge> getCartridge() {
        //TODO aus TXT Laden Implementieren
        return cartridges;
    }

    /**
     * @see AmmoDAO#save(Cartridge)
     */
    @Override
    public void save(Cartridge cartridge) {
        //TODO File auslesen daten in string speichern und datei speichern toString methode verwenden
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("D:\\Programirung\\InteliJ\\ATL_PEF_Munkalkulator\\src\\data\\ammoPart.txt")));
            pWriter.println(cartridge.getBullet().getData());
            pWriter.println(cartridge.getCase().getData());
            pWriter.println(cartridge.getPowder().getData());
            pWriter.println(cartridge.getPrimer().getData());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (pWriter != null) {
                pWriter.flush();
                pWriter.close();
            }
        }
    }
}

