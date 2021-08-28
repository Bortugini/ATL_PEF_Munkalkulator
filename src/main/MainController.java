package main;

import data.AmmoFile;
import data.Singleton;
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
    private Button btnSave;
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
    private Tab tabCalc, tabCalculations;
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
    private MenuItem conMenuLoading;

    private final AmmoFile ammoFile = Singleton.getInstance(Storage.MEMORY).getDAO();
    private final ObservableList<Cartridge> cartridges = FXCollections.observableArrayList();
    private final DecimalFormat pattern = new DecimalFormat("#0.00");
    private Cartridge cartridge;

    //TODO Param url, resourceBundle nötig?
    /**
     * Initialize methode wird beim aufruf des controllers automatisch ausgeführt.
     * Und dient dazu alle Choice Boxen, Spinner und Buttons zu initialisieren.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initChoiceBoxes();
        initSpinners();
        initButton();
    }

    /**
     * Initialisiert den Berechnen Button.
     * Überprüft, ob alle felder ausgefüllt sind.
     * Wenn alle felder ausgefüllt sind, wird der Berechnen-Button freigeschaltet.
     */
    public void initButton() {
        btnSave.setDisable(isNull());
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

//TODO param event Nötig?
    /**
     * Diese Methode dient dazu die berechnete Patrone zu Speichern.
     *
     * @param event
     */
    public void save(ActionEvent event) {
        ammoFile.addCartridge(cartridge);
    }

//TODO param event Nötig?
    /**
     * Diese Methode dient dazu die gespeicherten Konfigurationen zu laden.
     * Es wird die ausgewälte konfiguration aus dem Berechnungen-Tab geladen und im Rechner Tab angezeigt.
     *
     * @param event
     */
    public void load(ActionEvent event) {
        int index = tblView.getSelectionModel().getFocusedIndex();
        Cartridge cartridge = ammoFile.getCartridge(index);

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
    }

    /**
     * Diese Methode wird bei jedem tab Wechsel ausgeführt und dient dazu immer die
     * Aktuellen Berechnungen im tab berechnungen anzuzeigen.
     * Wenn das tab Berechnungen ausgewählt ist, werden alle berechnungen die gespeichert wurden
     * der ObservableList cartridges zugewiesen und in der Liste angezeigt.
     * Wenn dann wider zurück zum tab Recher gewechselt wird wird die ObservableList cartridges geleert
     * damit die einträge nicht doppelt sind.
     */
    public void onTabView() {
        if (tabCalculations.isSelected()) {
            cartridges.addAll(ammoFile.getCartridges());
        } else {
            cartridges.removeAll(ammoFile.getCartridges());
        }
        tblView.setItems(cartridges);
        tblPowder.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPowder().getName()));
        tblBullet.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBullet().getName()));
        tblCase.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCase().getName()));
        tblAmmo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameCartridge()));
        tblPrimer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrimer().getName()));
        tblCosts.setCellValueFactory(data -> new SimpleStringProperty(String.format(pattern.format(data.getValue().calc(1)) + " Sfr.")));
        conMenuLoading.setDisable(tblView.getItems().isEmpty());
    }

//TODO param event Nötig?
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

//TODO param event Nötig?
    /**
     * Diese Methode dient dazu bei den Textfeldern nur Double zuzulassen.
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
     * Diese Methode Initialisiert alle ChoiceBoxen.
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
     * Diese Methode dient dazu zu überprüfen ob alle Textfelder ausgefüllt wurden.
     * Wenn alle Textfelder ausgefüllt wurden wird {@code true} Zurückgeliefert.
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

//TODO param event Nötig?
    /**
     * Diese Methode dient dazu um bei Textfeldern die eingabe zu Beschränken.
     * Überprüft mittels übergebenem REGEX filter.
     * Und bei bestimmten Feldern das keine 0 verwendet werden kann (Division durch 0).
     *
     * @param event  Key Event Objekt
     * @param filter Filter für die überprüfung REGEX
     */
    //https://www.it-swarm.com.de/de/input/was-ist-der-empfohlene-weg-um-ein-numerisches-textfield-javafx-zu-erstellen/940163968/
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
