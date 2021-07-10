package billingapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private ComboBox<String> cartegory;
    @FXML
    private DatePicker date;
    @FXML
    private TextField route;
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> customerCol;
    @FXML
    private TableColumn<Customer, String> locationCol;
    @FXML
    private TableColumn<Customer, String> meterCol;
    @FXML
    private TableColumn<Customer, String> feeCol;
    @FXML
    private TableColumn<Customer, String> routeCol;
    @FXML
    private TableColumn<Customer, String> dateCol;
    @FXML
    private TableColumn<Customer, String> cartegoryCol;

//    innitializer
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            customerTableList();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> options = FXCollections.observableArrayList(
                "Domestic",
                "Commercial",
                "Institutional",
                "Industrial"
        );
        cartegory.setItems(options);
    }

    @FXML
    private void registerCustomer(ActionEvent event) {
        try {
            if (fullname.getText().isEmpty() || location.getText().isEmpty() || meter.getText().isEmpty() || connection.getText().isEmpty() || route.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Fill All fields!!");
                alert.setTitle("Empty Fields");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                String name = fullname.getText();
                String locat = location.getText();
                java.sql.Date dates = java.sql.Date.valueOf(date.getValue());
                String meterNo = meter.getText();
                String rut = route.getText();
                String fee = connection.getText();
                String cart = cartegory.getValue();

                Customer newCustomer = new Customer(name, dates, rut, cart, fee, locat, meterNo);
                newCustomer.RegisterCustomer();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Customer registered SuccessFully!!");
                alert.setTitle("Registered!");
                alert.setHeaderText(null);
                alert.show();

                fullname.setText("");
                location.setText("");
                date.setValue(null);
                meter.setText("");
                connection.setText("");
                route.setText("");
                cartegory.setValue(null);

//                redirect to the informations
            }
        } catch (Exception e) {
        }
    }

    //    fetch data for Events
    public ObservableList<Customer> getCustomerList() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Connection conn = DBconnection.getConnection();
        String query = "Select * from customer";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Customer customer;
            while (rs.next()) {
                customer = new Customer(rs.getString("name"), rs.getDate("date"), rs.getString("route"), rs.getString("cartegory"), rs.getString("fee"), rs.getString("location"), rs.getString("meter"));
                customerList.add(customer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return customerList;
    }
//    assign events in table

//    assign data to event table
    public void customerTableList() throws SQLException {
        ObservableList<Customer> list = (ObservableList<Customer>) getCustomerList();
        customerCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("location"));
        meterCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("meterNo"));
        feeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("fee"));
        routeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("route"));
        cartegoryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("cartegory"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("date"));

        CustomerTable.setItems(list);
    }

    @FXML
    private void clickSelect(MouseEvent event) {
    }

}
