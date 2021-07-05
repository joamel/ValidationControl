package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListView;

import java.util.ArrayList;


public class Controller {

    @FXML private TextField numberField;
    @FXML private ToggleGroup number;
    @FXML private RadioButton pRadio, cRadio, oRadio;
    @FXML private TabPane tp;
    @FXML private Tab tabPN, tabCN, tabON;
    @FXML private ListView<String> errorListPN, errorListCN, errorListON;

    /**
     * Metod som hanterar event när användaren trycker på knappen "Validera"
     */
    @FXML
    public void validateNumberClicked(ActionEvent event) {
        //errorListON.getItems().removeAll();
        String nr = numberField.getText();
        RadioButton selectedRB = (RadioButton) number.getSelectedToggle();
        String rBValue = selectedRB.getText();
        Alert alert = null;

        if (rBValue.equals(pRadio.getText())) {
            //Byter tab-vy till ogiltiga personnumren
            tp.getSelectionModel().select(tabPN);
            PersonalNumber pN = new PersonalNumber(nr);
            errorListPN.getItems().removeAll();

            //Om alla kriterier uppfylls
            if (pN.validate())
                alert = new Alert(Alert.AlertType.NONE, "Personnumret är giltigt!", ButtonType.OK);

            //annars
            else {
                //uppdateras listan med fel
                errorListPN.getItems().addAll(pN.getNumber());
                alert = new Alert(Alert.AlertType.ERROR, pN.getError(), ButtonType.OK);
            }
        }

        else if (rBValue.equals(cRadio.getText())) {
            //Byter tab-vy till ogiltiga samordningsnumren
            tp.getSelectionModel().select(tabCN);
            CoordNumber cN = new CoordNumber(nr);

            if (cN.validate())
                alert = new Alert(Alert.AlertType.NONE, "Samordningsnumret är giltigt!", ButtonType.OK);

            else {
                errorListCN.getItems().addAll(cN.getNumber());
                alert = new Alert(Alert.AlertType.ERROR, cN.getError(), ButtonType.OK);
            }
        }

        else if (rBValue.equals(oRadio.getText())) {
            //Byter tab-vy till ogiltiga organisationsnummer
            tp.getSelectionModel().select(tabON);
            OrgNumber oN = new OrgNumber(nr);

            if (oN.validate())
                alert = new Alert(Alert.AlertType.NONE, "Organisationsnumret är giltigt!", ButtonType.OK);

            else {
                errorListON.getItems().addAll(oN.getNumber());
                alert = new Alert(Alert.AlertType.ERROR, oN.getError(), ButtonType.OK);
            }
        }
        assert alert != null;
        alert.showAndWait();
    }



}
