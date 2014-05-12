/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjCostTracking;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author richardc
 */
public class ControllerHelp implements Initializable {
    @FXML
    private Button btnClose;
    @FXML
    private ImageView imgV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	Image banner = new Image(getClass().getResourceAsStream("/images/banner.png"));
        imgV.setImage(banner);
    }    

    @FXML
    private void btnClose_onClick(ActionEvent event) {
        Main.winCollection.get(Main.SCREEN_HELP).close();
    }
    
}
