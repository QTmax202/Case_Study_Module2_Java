package Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerOfHs_Date {
    private Customer customerHs;
    private LocalDate registrationDateHs;


    public CustomerOfHs_Date(Customer customer, String registrationDate) {
        this.customerHs = customer;
        this.registrationDateHs = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
    }

    public Customer getCustomer() {
        return customerHs;
    }

    public void setCustomer(Customer customer) {
        this.customerHs = customer;
    }

    public LocalDate getRegistrationDate() {
        return registrationDateHs;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDateHs = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s- %s\n",customerHs.getName(), customerHs.getPhoneNumber(), registrationDateHs);
    }
}
