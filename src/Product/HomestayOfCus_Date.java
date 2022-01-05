package Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomestayOfCus_Date {
    private Homestay homestayCus;
    private LocalDate startDateCus;
    private LocalDate endDateCus;

    public HomestayOfCus_Date(Homestay homestay, String startDateCus, String endDateCus) {
        this.homestayCus = homestay;
        this.startDateCus = LocalDate.parse(startDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.endDateCus = LocalDate.parse(endDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Homestay getHomestayCus() {
        return homestayCus;
    }

    public void setHomestayCus(Homestay homestayCus) {
        this.homestayCus = homestayCus;
    }

    public LocalDate getStartDateCusCus() {
        return startDateCus;
    }

    public void setStartDateCusCus(String startDateCus) {
        this.startDateCus = LocalDate.parse(startDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getEndDateCus() {
        return endDateCus;
    }

    public void setEndDateCus(String endDateCus) {
        this.endDateCus = LocalDate.parse(endDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s,%s [%s - %s]\n", homestayCus.getNameHs(), homestayCus.getAddress() , homestayCus.getCountyAddress(), startDateCus, endDateCus);
    }
}
