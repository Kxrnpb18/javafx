module com.example.cars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.cars to javafx.fxml;
    exports com.example.cars;
}