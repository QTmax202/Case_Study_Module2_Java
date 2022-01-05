package Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer_Date {
    private Homestay homestayCus;
    private LocalDate registrationDateCus;

    public Customer_Date(Homestay homestay, String registrationDate) {
        this.homestayCus = homestay;
        this.registrationDateCus = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Homestay getHomestayCus() {
        return homestayCus;
    }

    public void setHomestayCus(Homestay homestayCus) {
        this.homestayCus = homestayCus;
    }

    public LocalDate getRegistrationDateCus() {
        return registrationDateCus;
    }

    public void setRegistrationDateCus(String registrationDate) {
        this.registrationDateCus = LocalDate.parse(registrationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s,%s- %s\n", homestayCus.getNameHs(), homestayCus.getAddress() , homestayCus.getCountyAddress(), registrationDateCus);
    }
}
