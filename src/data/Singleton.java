package data;

/**
 * Singleton Klasse um den zugriff auf die daten von überall her zu gewährleisten.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Singleton {

    /**
     * Singleton Instanz Variable
     */
    private static Singleton instance;
    /**
     * AmmoFile Instanz Variable
     */
    private static AmmoFile dao;

    /**
     * Erstellt eine neue Instanz für ein Singleton Objekt.
     */
    private Singleton() {

    }

    /**
     * Diese Methode dient dazu das Singleton Objekt zurück zu liefern, wenn noch keins vorhanden ist,
     * wird eines erstellt die maximale anzahl ist eins.
     *
     * @param storage Konstante aus enum {@code Storage}
     * @return Singleton Objekt
     *
     * @see Storage
     */
    public static synchronized Singleton getInstance(Storage storage) {
        if (instance == null) {
            instance = new Singleton();
            Singleton.dao = new AmmoFile(storage);
        }

        return instance;
    }

    /**
     * Diese Methode dient dazu das DAO Objekt zurück zu liefern.
     *
     * @return AmmoFile
     *
     * @see AmmoFile
     */
    public AmmoFile getDAO() {
        return dao;
    }
}
