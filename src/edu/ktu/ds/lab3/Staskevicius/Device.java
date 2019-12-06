/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.Staskevicius;

import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.Parsable;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Tomas
 */
public class Device implements Parsable<Device>{
        // Bendri duomenys visiems automobiliams (visai klasei)
    private static final int minYear = 2005;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 100.0;
    private static final double maxPrice = 333000.0;

    private String brand = "";
    private String model = "";
    private int year = -1;
    private double screenSize = -1.0;
    private double price = -1.0;

    public Device() {
    }

    public Device(String brand, String model, int year, double screenSize, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.screenSize = screenSize;
        this.price = price;
        validate();
    }

    public Device(String dataString) {
        this.parse(dataString);
        validate();
    }

    public Device(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
        this.screenSize = builder.screenSize;
        this.price = builder.price;
        validate();
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }

        if (!errorType.isEmpty()) {
            Ks.ern("Prietaisas yra blogai sugeneruotas: " + errorType);
        }
    }

    @Override
    public void parse(String dataString) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(dataString);
           brand = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            screenSize = scanner.nextInt();
            price = scanner.nextDouble();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + dataString);
        }
    }

    @Override
    public String toString() {
        return brand + "_" + model + ":" + year + " " + getScreenSize() + " "
                + String.format("%4.1f", price);
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setScreenSize(int mileage) {
        this.screenSize = mileage;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.brand);
        hash = 29 * hash + Objects.hashCode(this.model);
        hash = 29 * hash + this.year;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.screenSize) ^ (Double.doubleToLongBits(this.screenSize) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Device other = (Device) obj;
        if (this.year != other.year) {
            return false;
        }
        if (this.screenSize != other.screenSize) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }

    // Car klases objektų gamintojas
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MODELS = { // galimų automobilių markių ir jų modelių masyvas
            {"Iphone", "11", "X", "8", "9"},
                {"Samsung", "Galaxy J3", "Galaxy S10", "Note 10+", "Galaxy A70"},
                {"Sony", "Xperia 2", "Xperia 10"},
                {"Xiaomi", "Redmi Go", "Redmi Note 7", "Mi 9T"},
                {"Apple", "Ipod 3", "Watch 2", "VR"},
                {"CAT", "S41", "B25", "B30", "S31"},
                {"Huawei", "P30", "P20", "Y5"},
                {"Google", "Watch 1", "Watch 2", "Glasses"}
        };

        private String brand = "";
        private String model = "";
        private int year = -1;
        private int screenSize = -1;
        private double price = -1.0;

        public Device build() {
            return new Device(this);
        }

        public Device buildRandom() {
            int ma = RANDOM.nextInt(MODELS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(MODELS[ma].length - 1) + 1;// modelio indeksas 1..
            return new Device(MODELS[ma][0],
                    MODELS[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    6000 + RANDOM.nextInt(222000),// rida tarp 6000 ir 228000
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder screenSize(int screenSize) {
            this.screenSize = screenSize;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
