package ProjCostTracking;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;


/**
 * FXML Controller class
 *
 * @author richardc
 */
public class LoginController implements Initializable {
    private Util u = new Util();
    static final int MAX_LOGIN_TRY = 3;
    private int login_tries = 0;
            
    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txfPassword;

    @FXML
    private TextField txfUsername;
    @FXML
    private Font x1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
    }    
   
    private void login(){
        boolean pass = false;
        pass = Main.db.setLoginUser(txfUsername.getText(), txfPassword.getText());
        
        if (pass){
            try {
                login_tries = 0;
                txfUsername.setText("");
                txfPassword.setText("");
                Main.winCollection.get(Main.SCREEN_MAINMENU).show();
                Main.winCollection.get(Main.SCREEN_LOGIN).close();
                Main.log(Main.LOGINOK, null, null);
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            login_tries++;
            if (login_tries == 3){
                System.exit(0);
            }
            
            lblMessage.setText("Wrong user name or password." + " Fail Count:"+ login_tries + "/" + MAX_LOGIN_TRY);
            txfPassword.setText("");
            txfUsername.requestFocus();
            txfUsername.selectAll();
        }
    }
    /*
    private void testDB(){
        Statement stat = null;
        ResultSet rs = null;

        String sql = "Select * from user"; 
        u.debug(sql);

        try {
                stat = db.getMySQLcon().createStatement();
                rs = stat.executeQuery(sql);
                
                while(rs.next()){
                    u.debug("userid:"+rs.getString(1) + "\tusername:"+rs.getString(2) + "\tpassword:"+rs.getString(3));
                }
                
        }catch (SQLException e) {
                u.debug("User Exception :" + e.toString());
        }

    }
       */
    
    @FXML
    private void btnLogin_onClick(ActionEvent event) {
        login();
    }

    @FXML
    private void btnExit_onClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void txfPassword_onEnter(ActionEvent event) {
        login();
    }
    
}
