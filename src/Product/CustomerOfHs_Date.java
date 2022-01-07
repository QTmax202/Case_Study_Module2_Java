package Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerOfHs_Date implements Serializable {
    private Customer customerOfHs;
    private LocalDate startDateOfHs;
    private LocalDate endDateOfHs;
    private LocalDate registrationDateOfHs;


    public CustomerOfHs_Date(Customer customer, String startDateHs, String endDateHs, String registrationDateOfHs) {
        this.customerOfHs = customer;
        this.startDateOfHs = LocalDate.parse(startDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        this.endDateOfHs = LocalDate.parse(endDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        this.registrationDateOfHs = LocalDate.parse(registrationDateOfHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
    }

    public Customer getCustomer() {
        return customerOfHs;
    }

    public void setCustomer(Customer customer) {
        this.customerOfHs = customer;
    }

    public LocalDate getStartDateOfHs() {
        return startDateOfHs;
    }

    public void setStartDateOfHs(String startDateOfHs) {
        this.startDateOfHs = LocalDate.parse(startDateOfHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getEndDateOfHs() {
        return endDateOfHs;
    }

    public void setEndDateOfHs(String endDateOfHs) {
        this.endDateOfHs = LocalDate.parse(endDateOfHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getRegistrationDateOfHs() {
        return registrationDateOfHs;
    }

    public void setRegistrationDateOfHs(String registrationDateOfHs) {
        this.registrationDateOfHs = LocalDate.parse(registrationDateOfHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s [%s - %s]\n",
                customerOfHs.getName(),
                customerOfHs.getPhoneNumber(),
                startDateOfHs.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                endDateOfHs.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
