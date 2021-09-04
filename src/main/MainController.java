package main;

import data.AmmoFactory;
import data.Storage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import logic.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Controller für das Hauptfenster.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class MainController implements Initializable {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfName;
    //**********Zünder************
    @FXML
    private TextField tfNamePrimer, tfPricePrimer, tfAmountPrimer;
    //**********Hülse************
    @FXML
    private TextField tfNameCase, tfPriceCase, tfAmountCase;
    //**********Pulver************
    @FXML
    private TextField tfNamePowder, tfPricePowder, tfAmountPowder, tfAmountLoaded;
    //**********Geschoss************
    @FXML
    private TextField tfNameBullet, tfPriceBullet, tfAmountBullet;
    //**********Output************
    @FXML
    private Label lbOut1, lbOut2, lbOut3;
    //**********ChoiceBox************
    @FXML
    private ChoiceBox<String> cbUnitPack;
    @FXML
    private ChoiceBox<String> cbUnitAmountLoaded;
    //**********Spinner************
    @FXML
    private Spinner sp1, sp2, sp3;
    //**********Tab************
    @FXML
    private Tab tabCalculations;
    //**********TableView************
    @FXML
    private TableView<Cartridge> tblView;
    @FXML
    private TableColumn<Cartridge, String> tblBullet;
    @FXML
    private TableColumn<Cartridge, String> tblPowder;
    @FXML
    private TableColumn<Cartridge, String> tblCase;
    @FXML
    private TableColumn<Cartridge, String> tblPrimer;
    @FXML
    private TableColumn<Cartridge, String> tblAmmo;
    @FXML
    private TableColumn<Cartridge, String> tblCosts;
    //**********ContextMenu************
    @FXML
    private MenuItem conMenuLoading, conMenuExport;
    //**********MenuBar************
    @FXML
    private MenuItem menuOpen, menuSave;
    @FXML
    private MenuItem menuAdd, menuDell;
    //**********Klassen Variablen************
    /**
     * {@code AmmoFactory} Objekt speichert die {@code Cartridge} im Memory.
     *
     * @see AmmoFactory
     */
    private final AmmoFactory ammoFactory = new AmmoFactory(Storage.MEMORY);

    /**
     * Wird benötigt, um die daten in der TableView anzuzeigen.
     *
     * @see Cartridge
     */
    private final ObservableList<Cartridge> cartridges = FXCollections.observableArrayList();

    /**
     * Format für die Ausgabe der Zahlen der Felder Berechnungen.
     */
    private final DecimalFormat pattern = new DecimalFormat("#0.00");

    /**
     * {@code Cartridge} Objekt
     *
     * @see Cartridge
     */
    private Cartridge cartridge;
    //**********Public Methoden************

    /**
     * Initialize methode wird beim aufruf des controllers automatisch ausgeführt.
     * Und dient dazu alle Choice Boxen, Spinner und Buttons zu initialisieren.
     *
     * @param url            URL Objekt
     * @param resourceBundle ResourceBundle Objekt
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initChoiceBoxes();
        initSpinners();
        initButton();
        initMenu();
    }

    /**
     * Diese Methode dient dazu, zu überprüfen, ob alle felder ausgefüllt sind.
     * Wenn alle felder ausgefüllt sind, wird der Berechnen-Button freigeschaltet.
     */
    public void ifAllFieldsFull() {
        initButton();
        initMenu();
    }

    /**
     * Berechnet die Kosten für die Anzahl Patronen, die in den Spinnern eingestellt sind.
     * Wird bei jeder änderung neu ausgeführt.
     */
    @FXML
    public void calc() {
        if (!isNull()) {
            Powder powder = new Powder(tfNamePowder.getText(), Integer.parseInt(tfAmountPowder.getText()), Double.parseDouble(tfPricePowder.getText())
                    , Double.parseDouble(tfAmountLoaded.getText()), cbUnitPack.getValue(), cbUnitAmountLoaded.getValue());
            Case aCase = new Case(tfNameCase.getText(), Integer.parseInt(tfAmountCase.getText()), Double.parseDouble(tfPriceCase.getText()));
            Bullet aBullet = new Bullet(tfNameBullet.getText(), Integer.parseInt(tfAmountBullet.getText()), Double.parseDouble(tfPriceBullet.getText()));
            Primer aPrimer = new Primer(tfNamePrimer.getText(), Integer.parseInt(tfAmountPrimer.getText()), Double.parseDouble(tfPricePrimer.getText()));
            cartridge = new Cartridge(aBullet, aCase, powder, aPrimer, tfName.getText());

            Spinner[] spinners = {sp1, sp2, sp3};
            Label[] labels = {lbOut1, lbOut2, lbOut3};
            for (int i = 0; i < spinners.length; i++) {
                Double price = cartridge.calc(Integer.parseInt(spinners[i].getValue().toString()));
                labels[i].setText(pattern.format(price));
            }

        }
    }

    /**
     * Diese Methode dient dazu die berechnete Patrone zu Speichern.
     * Je nachdem welcher button gedrückt wird eine andere Speicher methode ausgeführt.
     *
     * @param event ActionEvent Objekt
     */
    public void save(ActionEvent event) {
        if (event.getSource().equals(btnAdd) || event.getSource().equals(menuAdd)) {
            ammoFactory.addCartridge(cartridge);
            if (event.getSource().equals(menuAdd) && tabCalculations.isSelected()) {
                loadTabView();
            }
        }
        if (event.getSource().equals(menuSave)) {
            AmmoFactory ammoFactoryTXT = new AmmoFactory(Storage.TXT);
            ammoFactoryTXT.clear();
            ammoFactoryTXT.addCartridges(ammoFactory.getCartridges());
        }
    }

    /**
     * Diese Methode dient dazu Alle elemente in ammoFactory zu entfernen,
     * und eine Leere TableView anzuzeigen.
     */
    public void dell() {
        ammoFactory.clear();
        loadTabView();
    }

    /**
     * Diese Methode dient dazu die gespeicherten Konfigurationen zu laden.
     * Es wird die ausgewählte konfiguration aus dem Berechnungen-Tab geladen und im Rechner Tab angezeigt.
     *
     * @param event ActionEvent Objekt
     */
    public void load(ActionEvent event) {
        if (event.getSource().equals(conMenuLoading)) {
            int index = tblView.getSelectionModel().getFocusedIndex();
            Cartridge cartridge = ammoFactory.getCartridge(index);

            tfName.setText(cartridge.getNameCartridge());
            tfNameCase.setText(cartridge.getCase().getName());
            tfNamePowder.setText(cartridge.getPowder().getName());
            tfNamePrimer.setText(cartridge.getPrimer().getName());
            tfNameBullet.setText(cartridge.getBullet().getName());

            tfAmountPrimer.setText(Integer.toString(cartridge.getPrimer().getAmountPack()));
            tfAmountPowder.setText(Integer.toString(cartridge.getPowder().getAmountPack()));
            tfAmountBullet.setText(Integer.toString(cartridge.getBullet().getAmountPack()));
            tfAmountCase.setText(Integer.toString(cartridge.getCase().getAmountPack()));

            tfPriceCase.setText(Double.toString(cartridge.getCase().getPricePack()));
            tfPricePrimer.setText(Double.toString(cartridge.getPrimer().getPricePack()));
            tfPricePowder.setText(Double.toString(cartridge.getPowder().getPricePack()));
            tfPriceBullet.setText(Double.toString(cartridge.getBullet().getPricePack()));
            tfAmountLoaded.setText(Double.toString(cartridge.getPowder().getAmountLoaded()));

            cbUnitPack.setValue(cartridge.getPowder().getUnitPack());
            cbUnitAmountLoaded.setValue(cartridge.getPowder().getUnitAmountLoaded());
            calc();
            ifAllFieldsFull();
        }
        if (event.getSource().equals(menuOpen)) {
            AmmoFactory ammoFactoryTXT = new AmmoFactory(Storage.TXT);
            ammoFactory.clear();
            ammoFactory.addCartridges(ammoFactoryTXT.getCartridges());
            loadTabView();
        }

    }

    /**
     * Diese Methode dient dazu die aktuellen Berechnungen im tab berechnungen anzuzeigen.
     * Wenn die Methode ausgeführt wird werden alle berechnungen die gespeichert wurden
     * der ObservableList cartridges zugewiesen und in der Liste angezeigt.
     * Die ObservableList cartridges wird bei jedem aufruf geleert
     * damit die einträge nicht doppelt sind.
     * Ausserdem wird überprüft, ob die ammoFactory leer ist, wenn das der fall ist,
     * wird der text "Kein Inhalt in Tabelle" angezeigt.
     */
    public void loadTabView() {
        cartridges.clear();
        if (ammoFactory.getCartridges().isEmpty()) {
            tblView.setPlaceholder(new Label("Kein Inhalt in Tabelle"));
        } else {
            cartridges.addAll(ammoFactory.getCartridges());
            tblView.setItems(cartridges);
            tblPowder.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPowder().getName()));
            tblBullet.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBullet().getName()));
            tblCase.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCase().getName()));
            tblAmmo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameCartridge()));
            tblPrimer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrimer().getName()));
            tblCosts.setCellValueFactory(data -> new SimpleStringProperty(pattern.format(data.getValue().calc(1)) + " Sfr."));
        }
        initMenu();
    }

    /**
     * Diese Methode dient dazu bei den Textfeldern nur int zuzulassen.
     * In dieser Methode wird nur der REGEX Filter für die Methode validation gesetzt.
     *
     * @param event Key Event Objekt
     * @see MainController#validation(KeyEvent, String)
     */
    @FXML
    public void isInt(KeyEvent event) {
        /*
         * \\d = 0 - 9
         * * = 0 - mehr
         */
        validation(event, "\\d*");
    }

    /**
     * Diese Methode dient dazu, bei den Textfeldern nur Double zuzulassen.
     * In dieser Methode wird nur der REGEX Filter für die Methode validation gesetzt.
     *
     * @param event Key Event Objekt
     * @see MainController#validation(KeyEvent, String)
     */
    @FXML
    public void isDouble(KeyEvent event) {
        /*
         * [\\d]= 0 - 9
         * * = 0 - mehr
         * [\\.] = .
         * ? = 0 - 1
         * [\\d] = 0 - 9
         * * = 0 - mehr
         */
        validation(event, "[\\d]*[\\.]?[\\d]*");
    }

    /**
     * Diese Methode dient dazu, das Hauptfenster zu schliessen und damit das Programm zu beenden.
     */
    public void closeWindow() {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    /**
     * Diese Methode dient dazu, den Hilfe Dialog anzuzeigen.
     */
    public void displayHelp() {
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("Über Munitions Kalkulator");
        helpDialog.setHeaderText("Munitions Kalkulator v 1.0");
        helpDialog.setContentText(String.format("Autor: Thomas Saner%n" +
                "Beschreibung: Kalkulator um Munitionspreise zu Berechnen.%n" +
                "Benutzung: Alle Felder Ausfüllen,%n" +
                "gewünschte Menge einstellen.%n" +
                "Der Preis wird Automatisch berechnet.%n" +
                ""));
        helpDialog.showAndWait();
    }
    //**********Private Methoden************

    /**
     * Diese Methode Initialisiert alle Buttons.
     */
    private void initButton() {
        btnAdd.setDisable(isNull());
    }

    /**
     * Diese Methode initialisiert alle MenuItems.
     */
    private void initMenu() {
        menuAdd.setDisable(isNull());
        menuDell.setDisable(ammoFactory.getCartridges().isEmpty());
        menuSave.setDisable(ammoFactory.getCartridges().isEmpty());
        conMenuLoading.setDisable(tblView.getItems().isEmpty());
        conMenuExport.setDisable(tblView.getItems().isEmpty());
    }

    /**
     * Diese Methode Initialisiert alle ChoiceBoxen und fügt einen Listener hinzu.
     */
    private void initChoiceBoxes() {
        cbUnitPack.getItems().addAll("kg", "g");
        cbUnitPack.setValue("kg");
        cbUnitPack.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                calc();
            }
        });
        cbUnitAmountLoaded.getItems().addAll("grs", "g");
        cbUnitAmountLoaded.setValue("grs");
        cbUnitAmountLoaded.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                calc();
            }
        });
    }

    /**
     * Diese Methode initialisiert alle Spinner.
     */
    private void initSpinners() {
        Spinner[] spinners = {sp1, sp2, sp3};
        int[] initialValues = {1, 10, 100};
        for (int i = 0; i < initialValues.length; i++) {
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(initialValues[i], 1000, initialValues[i], initialValues[i]);
            spinners[i].setValueFactory(valueFactory);
            spinners[i].valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    calc();
                }
            });
        }
    }

    /**
     * Diese Methode dient dazu zu überprüfen, ob alle Textfelder ausgefüllt wurden.
     * Wenn alle Textfelder ausgefüllt wurden, wird {@code true} Zurückgeliefert.
     *
     * @return {@code true} wenn alle Textfelder Ausgefüllt
     */
    private boolean isNull() {
        return tfAmountBullet.getText().equals("") || tfAmountCase.getText().equals("")
                || tfAmountPowder.getText().equals("") || tfAmountLoaded.getText().equals("")
                || tfNameBullet.getText().equals("") || tfAmountPrimer.getText().equals("")
                || tfNameCase.getText().equals("") || tfName.getText().equals("")
                || tfNamePowder.getText().equals("") || tfPricePowder.getText().equals("")
                || tfNamePrimer.getText().equals("") || tfPriceBullet.getText().equals("")
                || tfPricePrimer.getText().equals("") || tfPriceCase.getText().equals("");
    }

    /**
     * Diese Methode dient dazu um bei Textfeldern die eingabe zu Beschränken.
     * Überprüft mittels übergebenem REGEX filter.
     * Und bei bestimmten Feldern das keine 0 verwendet werden kann (Division durch 0).
     *
     * @param event  Key Event Objekt
     * @param filter Filter für die überprüfung REGEX
     */
    private void validation(KeyEvent event, String filter) {
        TextField tf = (TextField) event.getSource();
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            if (!newValue.matches(filter)) {
                tf.setText(oldValue);
            }
            if (tf.getId().equals("tfMengeZünder") || tf.getId().equals("tfMengeHülse")
                    || tf.getId().equals("tfMengePulver") || tf.getId().equals("tfMengeGeschoss")) {
                if (tf.getText().equals("0")) {
                    tf.setText(oldValue);
                }
            }
        };
        tf.textProperty().addListener(listener);
    }
}
