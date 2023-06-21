package com.example.cars;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/F22Midterm";
    private static final String DB_USER = "student";
    private static final String DB_PASSWORD = "student";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static List<Car> getAllCarsFromDatabase() {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT * FROM carSales";
            try (ResultSet resultSet = connection.createStatement().executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    int carId = resultSet.getInt("carID");
                    int modelYear = resultSet.getInt("modelYear");
                    String make = resultSet.getString("make");
                    String model = resultSet.getString("model");
                    int price = resultSet.getInt("price");
                    LocalDate dateSold = resultSet.getDate("dateSold").toLocalDate();

                    Car car = new Car(carId, modelYear, make, model, price, dateSold);
                    cars.add(car);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static List<Integer> getYearsOfCarSalesFromDatabase() {
        List<Integer> years = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT DISTINCT modelYear FROM carSales";
            try (ResultSet resultSet = connection.createStatement().executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    int modelYear = resultSet.getInt("modelYear");
                    years.add(modelYear);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return years;
    }
}
