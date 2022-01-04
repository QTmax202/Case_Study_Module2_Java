package Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Homestay_Customer implements Serializable {
    private Homestay homestay;
    private LocalDate registrationDate;
    private Customer customer;

    public Homestay_Customer(Homestay homestay, String registrationDate, Customer customer) {
        this.homestay = homestay;
        this.registrationDate = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.customer = customer;
    }

    public Homestay getHomestay() {
        return homestay;
    }

    public void setHomestay(Homestay homestay) {
        this.homestay = homestay;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
