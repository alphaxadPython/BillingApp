package billingapplication;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer extends BillingApp {

    java.sql.Date date;
    String route;
    String cartegory;
    String fee;
    String location;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCartegory() {
        return cartegory;
    }

    public void setCartegory(String cartegory) {
        this.cartegory = cartegory;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public Customer(String customer, Date date, String route, String cartegory, String fee, String location, String meterNo) {
        this.customer = customer;
        this.date = date;
        this.route = route;
        this.cartegory = cartegory;
        this.fee = fee;
        this.location = location;
        this.meterNo = meterNo;
    }

    // register new user here!!
    public void RegisterCustomer() {
        try (Connection conn = DBconnection.getConnection()) {
            // Insert data query
            String query = "INSERT INTO customer (name, date, route, cartegory, fee, location, meter)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            // preparing the prepared statement to insert data
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, customer);
            preparedStmt.setDate(2, date);
            preparedStmt.setString(3, route);
            preparedStmt.setString(4, cartegory);
            preparedStmt.setString(5, fee);
            preparedStmt.setString(6, location);
            preparedStmt.setString(7, meterNo);

            // Executing the preparedstatement
            preparedStmt.execute();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect the database!" + e.getMessage());
        }
        System.out.println("User ahve been registered");
    }
}

