package billingapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.runtime.JSType;

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
    @FXML
    private AnchorPane registerTab;
    @FXML
    private AnchorPane infoTab;
    @FXML
    private AnchorPane billTab;
    @FXML
    private Button regist;
    @FXML
    private TabPane bigTab;
    @FXML
    private Button addNew;
    @FXML
    private Button deleteBtn;
    @FXML
    private AnchorPane registerTab1;
    @FXML
    private TextField editFullname;
    @FXML
    private TextField editLocation;
    @FXML
    private TextField editMeter;
    @FXML
    private TextField editRoute;
    @FXML
    private TextField editConnection;
    @FXML
    private Button regist1;
    @FXML
    private ComboBox<String> editCartegory;
    @FXML
    private DatePicker editDate;
    @FXML
    private TextField meterReading;
    @FXML
    private TextField feePaid;
    @FXML
    private Label meterReadLabel;
    @FXML
    private Label meterReadLabel22;
    @FXML
    private Label meterReadLabel221;
    @FXML
    private Pane BillPane;
    @FXML
    private Label meterReadLabel1;
    @FXML
    private Label meterReadLabel2;
    @FXML
    private Label meterleadlabelSide;
    @FXML
    private Label meterReadLabel21;
    @FXML
    private Label BillPaidLabel;
    @FXML
    private Label meterReadLabel211;
    @FXML
    private Label totalBilllabel;
    @FXML
    private Label meterReadLabel2111;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label montlyLabel;
    @FXML
    private Label quatelyLabel;
    @FXML
    private Label meterNoLabelSide;
    @FXML
    private Label custmlabel;
    @FXML
    private Label meternoLab;

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
                customerTableList();
//                redirect to the informations
                bigTab.getSelectionModel().select(1);
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

//    public variable to capture cell click data
    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
//    variables here
    public String custom;
    public String customerName;

//    on click data in the table for customers
    @FXML
    private void clickSelect(MouseEvent event) {
        if (event.getClickCount() == 1) //Checking double click
        {
            customerName = CustomerTable.getSelectionModel().getSelectedItem().customer;
            custom = CustomerTable.getSelectionModel().getSelectedItem().customer;

            editFullname.setText(CustomerTable.getSelectionModel().getSelectedItem().customer);
            editMeter.setText(CustomerTable.getSelectionModel().getSelectedItem().meterNo);
            editRoute.setText(CustomerTable.getSelectionModel().getSelectedItem().route);
            editCartegory.setValue(CustomerTable.getSelectionModel().getSelectedItem().cartegory);
            editConnection.setText(CustomerTable.getSelectionModel().getSelectedItem().fee);
            editLocation.setText(CustomerTable.getSelectionModel().getSelectedItem().location);
            editDate.setValue(LOCAL_DATE(CustomerTable.getSelectionModel().getSelectedItem().date.toString()));

//            pass values to the next labels
            custmlabel.setText(CustomerTable.getSelectionModel().getSelectedItem().customer);
            meternoLab.setText(CustomerTable.getSelectionModel().getSelectedItem().meterNo);
            meterNoLabelSide.setText(CustomerTable.getSelectionModel().getSelectedItem().meterNo);
            mete = CustomerTable.getSelectionModel().getSelectedItem().meterNo;

        }
    }

//    onclick add new button ..back to register tab
    @FXML
    private void AddNewTab(MouseEvent event) {
        bigTab.getSelectionModel().select(0);
    }

// delete customer function here
    @FXML
    private void DeleteCustomer(ActionEvent event) {
        try (Connection conn = DBconnection.getConnection()) {

            //Prepare the delete quesry
            String query = "DELETE FROM customer WHERE name=?";
            // creating the prepared statements
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, customerName);

            // Execute the preparedstatement
            preparedStmt.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted Successfully!!");
            alert.setTitle("Deleted");
            alert.setHeaderText(null);
            alert.show();
            customerTableList();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect the database!" + e.getMessage());
        }
        System.out.println("Deleting the Event");
    }

//    update customer here
    @FXML
    private void updateCustomer(ActionEvent event) {
        String customer = editFullname.getText();
        String location = editLocation.getText();
        String route = editRoute.getText();
        String meter = editMeter.getText();
        String cartegory = editCartegory.getValue();
        String fee = editConnection.getText();

        try (Connection conn = DBconnection.getConnection()) {

            // updating the customer table here
            String query = "UPDATE customer SET name=?, date=?, route=?, cartegory=?, fee=?, location=?, meter=? where name=?";

            // mysql update prepare statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, customer);
            java.sql.Date dates = java.sql.Date.valueOf(editDate.getValue());
            preparedStmt.setDate(2, dates);
            preparedStmt.setString(3, route);
            preparedStmt.setString(4, cartegory);
            preparedStmt.setString(5, fee);
            preparedStmt.setString(6, location);
            preparedStmt.setString(7, meter);
            preparedStmt.setString(8, customerName);

            // Execute the preparedstatement
            preparedStmt.execute();

            editFullname.setText("");
            editLocation.setText("");
            editDate.setValue(null);
            editMeter.setText("");
            editRoute.setText("");
            editCartegory.setValue(null);

            bigTab.getSelectionModel().select(1);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Updated Successfully!!!!");
            alert.setTitle("Updates");
            alert.setHeaderText(null);
            alert.show();

            customerTableList();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect the database!" + e.getMessage());
        }
        System.out.println("Customer has been updated!!");
    }

//    switching to update field
    @FXML
    private void goToUpdate(MouseEvent event) {
        bigTab.getSelectionModel().select(3);
    }
// variables to capture data here
    String Total;
    String monthly;
    String mete;
    final Integer unit = 1000;
    Integer bill;
    Integer blnc;
    Integer quater;

//    calculate bill fnction here!!
    @FXML
    private void caluClateBill(ActionEvent event) {
        try {
            if (meterReading.getText().isEmpty() || feePaid.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Fill All fields!!");
                alert.setTitle("Empty Fields");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                //            capture data for bill
                String read = meterReading.getText();
                String fee = feePaid.getText();

                meterleadlabelSide.setText(read + " Units");
                BillPaidLabel.setText(fee + " /= TZSH");

                meterReading.setText("");
                feePaid.setText("");

                //         caluclate total Bill
                bill = unit * Integer.parseInt(read);

                totalBilllabel.setText(bill.toString());
                Total = bill.toString();

//                caluclate balance due
                blnc = Integer.parseInt(fee) - bill;
                balanceLabel.setText(blnc.toString());

                //                monthly bill
                monthly = bill.toString();
                montlyLabel.setText(bill.toString());

                //                quately balance
                quater = bill / 20;
                quatelyLabel.setText(quater.toString());
            }
        } catch (Exception e) {
        }
    }

//    go to caluclate bill tab
    @FXML
    private void goToCaluclate(MouseEvent event) {
        bigTab.getSelectionModel().select(2);
    }

//    save Bill
    @FXML
    private void saveBill(ActionEvent event) {
        try {
            try (Connection conn = DBconnection.getConnection()) {

                // The mysql insert statement for table users_table
                String query = "INSERT INTO bills(customer, monthly, balance, quatery_Balance, meter, Tota) VALUES (?, ?, ?, ?, ?, ?)";

                // Create the mysql insert prepared statement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, customerName);
                preparedStmt.setString(2, monthly);
                preparedStmt.setString(3, blnc.toString());
                preparedStmt.setString(4, quater.toString());
                preparedStmt.setString(5, mete);
                preparedStmt.setString(6, Total);

                // Execute the preparedstatement
                preparedStmt.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Bill saved Successfully!!!!");
                alert.setTitle("Saved");
                alert.setHeaderText(null);
                alert.show();

                bigTab.getSelectionModel().select(1);

                conn.close();
            } catch (SQLException e) {
                System.out.println("Cannot connect the database!" + e.getMessage());
            }
            System.out.println("Bill has been inserted");
        } catch (Exception e) {
        }
    }

    @FXML
    private void goHome(MouseEvent event) {
        bigTab.getSelectionModel().select(0);

    }

    @FXML
    private void goConnect(MouseEvent event) {
        bigTab.getSelectionModel().select(1);
    }

}
