package com.example.cars;
import java.time.LocalDate;

public class Car {
    private int carId;
    private int modelYear;
    private String make;
    private String model;
    private double price;
    private LocalDate dateSold;

    public Car(int carId, int modelYear, String make, String model, double price, LocalDate dateSold) {
        this.carId = carId;
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
        this.price = price;
        this.dateSold = dateSold;
    }

    // Getters and setters for the Car attributes
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        if (validateCarId(carId)) {
            this.carId = carId;
        }
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        if (validateMake(make)) {
            this.make = make;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (validateModel(model)) {
            this.model = model;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (validatePrice(price)) {
            this.price = price;
        }
    }

    public LocalDate getDateSold() {
        return dateSold;
    }

    public void setDateSold(LocalDate dateSold) {
        if (validateDateSold(dateSold)) {
            this.dateSold = dateSold;
        }
    }

    // Add validation methods for each attribute
    private boolean validateCarId(int carId) {
        return carId > 0;
    }

    private boolean validateMake(String make) {
        return make.equals("Acura") || make.equals("Ford") || make.equals("Honda") ||
                make.equals("Nissan") || make.equals("Tesla");
    }

    private boolean validateModel(String model) {
        return model.length() >= 2;
    }

    private boolean validatePrice(double price) {
        return price > 0;
    }

    private boolean validateDateSold(LocalDate dateSold) {
        LocalDate currentDate = LocalDate.now();
        return !dateSold.isAfter(currentDate);
    }

}
