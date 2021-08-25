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
    private Button btnSpeichern;
    @FXML
    private TextField tfName;
    //**********Zünder************
    @FXML
    private TextField tfNameZünder, tfPreisZünder, tfMengeZünder;
    //**********Hülse************
    @FXML
    private TextField tfNameHülse, tfPreisHülse, tfMengeHülse;
    //**********Pulver************
    @FXML
    private TextField tfNamePulver, tfPreisPulver, tfMengePulver, tfMengeVerladenPulver;
    //**********Geschoss************
    @FXML
    private TextField tfNameGeschoss, tfPreisGeschoss, tfMengeGeschoss;
    //**********Output************
    @FXML
    private Label lbOut1, lbOut2, lbOut3;
    //**********ChoiceBox************
    @FXML
    private ChoiceBox<String> cbEinheitPackung;
    @FXML
    private ChoiceBox<String> cbEinheitVerladeneMenge;
    //**********Spinner************
    @FXML
    private Spinner sp1, sp2, sp3;
    //**********Tab************
    @FXML
    private Tab tabRechner, tabBerechnungen;
    //**********TableView************
    @FXML
    private TableView<Cartridge> tblView;
    @FXML
    private TableColumn<Cartridge, String> tblGeschoss;
    @FXML
    private TableColumn<Cartridge, String> tblPulver;
    @FXML
    private TableColumn<Cartridge, String> tblHülse;
    @FXML
    private TableColumn<Cartridge, String> tblZünder;
    @FXML
    private TableColumn<Cartridge, String> tblMunition;
    @FXML
    private TableColumn<Cartridge, String> tblKosten;
    //**********ContextMenu************
    @FXML
    private MenuItem conMenuLaden;

    private final AmmoFile ammoFile = Singleton.getInstance(Storage.MEMORY).getDAO();
    private final ObservableList<Cartridge> patronen = FXCollections.observableArrayList();
    private final DecimalFormat pattern = new DecimalFormat("#0.00");
    private Cartridge cartridge;

    /**
     * Initialize methode wird beim aufruf des controllers automatisch ausgeführt.
     *
     * @param url
     * @param resourceBundle
     */
    //TODO parameter Beschrieben
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
        btnSpeichern.setDisable(isNull());
    }

    //TODO Javadoc schreiben

    /**
     *
     */
    @FXML
    public void berechnen() {
        if (!isNull()) {
            Powder powder = new Powder(tfNamePulver.getText(), Integer.parseInt(tfMengePulver.getText()), Double.parseDouble(tfPreisPulver.getText())
                    , Double.parseDouble(tfMengeVerladenPulver.getText()), cbEinheitPackung.getValue(), cbEinheitVerladeneMenge.getValue());
            Case aCase = new Case(tfNameHülse.getText(), Integer.parseInt(tfMengeHülse.getText()), Double.parseDouble(tfPreisHülse.getText()));
            Bullet bullet = new Bullet(tfNameGeschoss.getText(), Integer.parseInt(tfMengeGeschoss.getText()), Double.parseDouble(tfPreisGeschoss.getText()));
            Primer primer = new Primer(tfNameZünder.getText(), Integer.parseInt(tfMengeZünder.getText()), Double.parseDouble(tfPreisZünder.getText()));
            cartridge = new Cartridge(bullet, aCase, powder, primer, tfName.getText());

            Spinner[] spinners = {sp1, sp2, sp3};
            Label[] labels = {lbOut1, lbOut2, lbOut3};
            for (int i = 0; i < spinners.length; i++) {
                Double price = cartridge.calc(Integer.parseInt(spinners[i].getValue().toString()));
                labels[i].setText(pattern.format(price));
            }

        }
    }
//TODO Javadoc schreiben

    /**
     * @param event
     */
    public void save(ActionEvent event) {
        ammoFile.addCartridge(cartridge);
    }
//TODO Javadoc schreiben

    /**
     * @param event
     */
    public void load(ActionEvent event) {
        int index = tblView.getSelectionModel().getFocusedIndex();
        Cartridge cartridge = ammoFile.getCartridge(index);

        tfName.setText(cartridge.getNameCartridge());
        tfNameHülse.setText(cartridge.getCase().getName());
        tfNamePulver.setText(cartridge.getPowder().getName());
        tfNameZünder.setText(cartridge.getPrimer().getName());
        tfNameGeschoss.setText(cartridge.getBullet().getName());

        tfMengeZünder.setText(Integer.toString(cartridge.getPrimer().getAmountPack()));
        tfMengePulver.setText(Integer.toString(cartridge.getPowder().getAmountPack()));
        tfMengeGeschoss.setText(Integer.toString(cartridge.getBullet().getAmountPack()));
        tfMengeHülse.setText(Integer.toString(cartridge.getCase().getAmountPack()));

        tfPreisHülse.setText(Double.toString(cartridge.getCase().getPricePack()));
        tfPreisZünder.setText(Double.toString(cartridge.getPrimer().getPricePack()));
        tfPreisPulver.setText(Double.toString(cartridge.getPowder().getPricePack()));
        tfPreisGeschoss.setText(Double.toString(cartridge.getBullet().getPricePack()));
        tfMengeVerladenPulver.setText(Double.toString(cartridge.getPowder().getAmountLoaded()));

        cbEinheitPackung.setValue(cartridge.getPowder().getUnitPack());
        cbEinheitVerladeneMenge.setValue(cartridge.getPowder().getUnitAmountLoaded());
    }

    //TODO Javadoc Schreiben

    /**
     *
     */
    public void onTabView() {
        if (tabBerechnungen.isSelected()) {
            patronen.addAll(ammoFile.getCartridges());
        } else {
            patronen.removeAll(ammoFile.getCartridges());
        }
        tblView.setItems(patronen);
        tblPulver.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPowder().getName()));
        tblGeschoss.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBullet().getName()));
        tblHülse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCase().getName()));
        tblMunition.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameCartridge()));
        tblZünder.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrimer().getName()));
        tblKosten.setCellValueFactory(data -> new SimpleStringProperty(String.format(pattern.format(data.getValue().calc(1)) + " Sfr.")));
        conMenuLaden.setDisable(tblView.getItems().isEmpty());
    }
    //TODO Javadoc schreiben

    /**
     * @param event Key Event Objekt
     */
    //https://www.it-swarm.com.de/de/input/was-ist-der-empfohlene-weg-um-ein-numerisches-textfield-javafx-zu-erstellen/940163968/
    @FXML
    public void isInt(KeyEvent event) {
        /*
         * \\d = 0 - 9 * = 0 - mehr
         */
        validierung(event, "\\d*");
    }
    //TODO Javadoc schreiben

    /**
     * @param event Key Event Objekt
     */
    @FXML
    public void isDouble(KeyEvent event) {
        /*
         * [\\d]= 0 - 9 * = 0 - mehr
         * [\\.] = . ? = 0 - 1
         * [\\d] = 0 - 9 * = 0 - mehr
         */
        validierung(event, "[\\d]*[\\.]?[\\d]*");
    }

    /**
     * Initialisiert alle ChoiceBoxen.
     */
    private void initChoiceBoxes() {
        cbEinheitPackung.getItems().addAll("kg", "g");
        cbEinheitPackung.setValue("kg");
        cbEinheitPackung.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                berechnen();
            }
        });
        cbEinheitVerladeneMenge.getItems().addAll("grs", "g");
        cbEinheitVerladeneMenge.setValue("grs");
        cbEinheitVerladeneMenge.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                berechnen();
            }
        });
    }

    /**
     * Initialisiert alle Spinner
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
                    berechnen();
                }
            });
        }
    }
//TODO Javadoc schreiben

    /**
     * @return
     */
    private boolean isNull() {
        return tfMengeGeschoss.getText().equals("") || tfMengeHülse.getText().equals("")
                || tfMengePulver.getText().equals("") || tfMengeVerladenPulver.getText().equals("")
                || tfNameGeschoss.getText().equals("") || tfMengeZünder.getText().equals("")
                || tfNameHülse.getText().equals("") || tfName.getText().equals("")
                || tfNamePulver.getText().equals("") || tfPreisPulver.getText().equals("")
                || tfNameZünder.getText().equals("") || tfPreisGeschoss.getText().equals("")
                || tfPreisZünder.getText().equals("") || tfPreisHülse.getText().equals("");
    }

    /**
     * Methode um bei Textfeldern die eingabe zu Beschränken.
     * Überprüft mittels filter und bei bestimmten Feldern das keine 0 verwendet werden kann (Division durch 0).
     *
     * @param event  Key Event Objekt
     * @param filter Filter für die überprüfung REGEX
     */
    private void validierung(KeyEvent event, String filter) {
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
