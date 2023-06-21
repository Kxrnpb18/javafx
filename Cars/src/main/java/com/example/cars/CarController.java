package com.example.cars;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CarController implements Initializable {
    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, Integer> carIdColumn;

    @FXML
    private TableColumn<Car, Integer> modelYearColumn;

    @FXML
    private TableColumn<Car, String> makeColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

    @FXML
    private TableColumn<Car, LocalDate> dateSoldColumn;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private Label totalCarsLabel;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private BarChart<String, Integer> carSalesBarChart;

    @FXML
    private CategoryAxis makeAxis;

    @FXML
    private NumberAxis countAxis;

    private ObservableList<Car> carData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the TableView columns
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        modelYearColumn.setCellValueFactory(new PropertyValueFactory<>("modelYear"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateSoldColumn.setCellValueFactory(new PropertyValueFactory<>("dateSold"));

        // Populate the TableView with data from the database
        carData = FXCollections.observableArrayList(CarDAO.getAllCarsFromDatabase());
        carTableView.setItems(carData);

        // Populate the ComboBox with years from the database
        List<Integer> years = CarDAO.getYearsOfCarSalesFromDatabase();
        yearComboBox.getItems().addAll(years);

        // Update labels and bar chart
        updateStatistics();

        // initialize the bar chart
        updateBarChart(carData);

        // Add listener to the ComboBox selection
        yearComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Filter the carData based on the selected year
                List<Car> filteredCars = carData.stream()
                        .filter(car -> car.getModelYear() == newValue)
                        .collect(Collectors.toList());

                // Update the TableView with the filtered data
                carTableView.setItems(FXCollections.observableArrayList(filteredCars));

                // Update the labels and bar chart
                updateStatistics();
                updateBarChart(filteredCars);
            }
        });
    }

    @FXML
    private void handleYearSelection() {
        int selectedYear = yearComboBox.getValue();
        ObservableList<Car> filteredData = carData.filtered(car -> car.getModelYear() == selectedYear);
        carTableView.setItems(filteredData);

        updateStatistics();
    }

    private void updateStatistics() {
        // Update the labels with the total cars and total sales
        int totalCars = carTableView.getItems().size();
        double totalSales = carTableView.getItems().stream()
                .mapToDouble(Car::getPrice)
                .sum();

        totalCarsLabel.setText(String.valueOf(totalCars));
        totalSalesLabel.setText(String.format("$%,.2f", totalSales));
    }


    private void updateBarChart(List<Car> cars) {
        // Clear the existing data in the bar chart
        carSalesBarChart.getData().clear();

        // Group the cars by make and count the occurrences
        Map<String, Long> carCountByMake = cars.stream()
                .collect(Collectors.groupingBy(Car::getMake, Collectors.counting()));

        // Create a series and add data points to it
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        carCountByMake.forEach((make, count) -> series.getData().add(new XYChart.Data<>(make, count.intValue())));

        // Add the series to the bar chart
        carSalesBarChart.getData().add(series);
    }

}
