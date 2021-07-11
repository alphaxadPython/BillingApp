package billingapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void loginAdmin() throws IOException {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Fill All fields!!");
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (username.getText().matches("admin") && password.getText().matches("123")) {
            FXMLLoader form = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Stage stage = (Stage) login.getScene().getWindow();
            Scene scene = new Scene(form.load());
            stage.setScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No such Account");
            alert.setTitle("Account error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

}
