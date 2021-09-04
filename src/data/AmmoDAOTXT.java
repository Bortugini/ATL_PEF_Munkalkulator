package data;

import logic.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementation um Munitions-Teile als TXT Datei zu Speichern und zu Laden.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class AmmoDAOTXT implements AmmoDAO {
    /**
     * Speicher Path für die Datei.
     */
    private final String PATH = System.getProperty("user.dir") + "\\src\\data\\ammoPart.txt";

    /**
     * @see AmmoDAO#getCartridges()
     */
    public ArrayList<Cartridge> getCartridges() {
        ArrayList<Cartridge> cartridges = new ArrayList<>();
        String name = null;
        String[] data;
        Bullet bullet = null;
        Case aCase = null;
        Powder powder = null;
        Primer primer = null;
        Cartridge cartridge = null;

        try (Scanner in = new Scanner(new File(PATH))) {
            while (in.hasNext()) {
                data = in.nextLine().split(":");
                switch (data[0]) {
                    case "Bullet":
                        bullet = new Bullet(data[1], Integer.parseInt(data[3]), Double.parseDouble(data[4]));
                        break;
                    case "Case":
                        aCase = new Case(data[1], Integer.parseInt(data[3]), Double.parseDouble(data[4]));
                        break;
                    case "Powder":
                        powder = new Powder(data[1], Integer.parseInt(data[3]), Double.parseDouble(data[4]),
                                Double.parseDouble(data[5]), data[6], data[7]);
                        break;
                    case "Primer":
                        primer = new Primer(data[1], Integer.parseInt(data[3]), Double.parseDouble(data[4]));
                        cartridge = new Cartridge(bullet, aCase, powder, primer, name);
                        cartridges.add(cartridge);
                        break;
                    case "Name":
                        name = data[1];
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cartridges;
    }


    /**
     * @see AmmoDAO#addCartridge(Cartridge)
     */
    @Override
    public void addCartridge(Cartridge cartridge) {
        try (FileWriter fileWriter = new FileWriter(PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println("Name:" + cartridge.getNameCartridge());
            out.println(cartridge.getBullet().getData());
            out.println(cartridge.getCase().getData());
            out.println(cartridge.getPowder().getData());
            out.println(cartridge.getPrimer().getData());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * @see AmmoDAO#addCartridges(ArrayList)
     */
    @Override
    public void addCartridges(ArrayList<Cartridge> cartridges) {
        for (Cartridge cartridge : cartridges) {
            addCartridge(cartridge);
        }

    }

    /**
     * @see AmmoDAO#clear()
     */
    @Override
    public void clear() {
        try (FileWriter fileWriter = new FileWriter(PATH, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.write("");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

