package data;
//TODO Javadoc schreiben
/**
 *
 */
public class Singleton {

    private static Singleton instance;
    private static AmmoFile dao;

    /**
     * Erstellt eine neue Instanz für ein Singleton Objekt.
     */
    private Singleton() {

    }

    /**
     * Liefert das Singleton Objekt zurück, wenn noch keins vorhanden ist, wird eines erstellt maximale anzahl ist eins.
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
     * Liefert das DAO Objekt zurück.
     *
     * @return MunDatei
     * @see AmmoFile
     */
    public AmmoFile getDAO() {
        return dao;
    }
}
