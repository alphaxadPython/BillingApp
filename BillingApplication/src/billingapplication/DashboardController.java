
package billingapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DashboardController implements Initializable {

    @FXML
    private TextField fullname;
    @FXML
    private TextField location;
    @FXML
    private TextField meter;
    @FXML
    private TextField connection;
    @FXML
    private ComboBox<?> cartegory;
    @FXML
    private DatePicker date;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registerCustomer(ActionEvent event) {
    }
    
}
