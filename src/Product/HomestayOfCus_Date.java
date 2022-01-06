package Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomestayOfCus_Date {
    private Homestay homestayOfCus;
    private LocalDate startDateOfCus;
    private LocalDate endDateOfCus;

    public HomestayOfCus_Date(Homestay homestay, String startDateCus, String endDateCus) {
        this.homestayOfCus = homestay;
        this.startDateOfCus = LocalDate.parse(startDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.endDateOfCus = LocalDate.parse(endDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Homestay getHomestayOfCus() {
        return homestayOfCus;
    }

    public void setHomestayOfCus(Homestay homestayOfCus) {
        this.homestayOfCus = homestayOfCus;
    }

    public LocalDate getStartDateCusCus() {
        return startDateOfCus;
    }

    public void setStartDateCusCus(String startDateCus) {
        this.startDateOfCus = LocalDate.parse(startDateCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getEndDateOfCus() {
        return endDateOfCus;
    }

    public void setEndDateOfCus(String endDateOfCus) {
        this.endDateOfCus = LocalDate.parse(endDateOfCus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return String.format("%s- %s,%s [%s - %s]\n",
                homestayOfCus.getNameHs(),
                homestayOfCus.getAddress(),
                homestayOfCus.getCountyAddress(),
                startDateOfCus.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                endDateOfCus.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
