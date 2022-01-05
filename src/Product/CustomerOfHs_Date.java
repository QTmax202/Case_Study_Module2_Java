package Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerOfHs_Date {
    private Customer customerHs;
    private LocalDate startDateHs;
    private LocalDate endDateHs;


    public CustomerOfHs_Date(Customer customer, String startDateHs, String endDateHs) {
        this.customerHs = customer;
        this.startDateHs = LocalDate.parse(startDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        this.endDateHs = LocalDate.parse(endDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
    }

    public Customer getCustomer() {
        return customerHs;
    }

    public void setCustomer(Customer customer) {
        this.customerHs = customer;
    }

    public void setStartDateHsDate(String startDateHs) {
        this.startDateHs = LocalDate.parse(startDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setStartDateHs(String startDateHs) {
        this.startDateHs = LocalDate.parse(startDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getEndDateHs() {
        return endDateHs;
    }

    public void setEndDateHs(String endDateHs) {
        this.endDateHs = LocalDate.parse(endDateHs, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s [%s - %s]\n",customerHs.getName(), customerHs.getPhoneNumber(), startDateHs, endDateHs);
    }
}
