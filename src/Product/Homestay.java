package Product;

import java.io.Serializable;

public class Homestay implements Serializable {
    private String nameHs;
    private int priceHs;
    private String phoneNumberHs;
    private String address;
    private String countyAddress;
    private String highlight;
    private String accHomestay;
    private String passHomestay;

    public Homestay(String nameHs, int priceHs, String phoneNumberHs, String address, String countyAddress, String highlight, String acc, String pass) {
        this.nameHs = nameHs;
        this.priceHs = priceHs;
        this.phoneNumberHs = phoneNumberHs;
        this.address = address;
        this.countyAddress = countyAddress;
        this.highlight = highlight;
        this.accHomestay = acc;
        this.passHomestay = pass;
    }

    public String getNameHs() {
        return nameHs;
    }

    public void setNameHs(String nameHs) {
        this.nameHs = nameHs;
    }

    public int getPriceHs() {
        return priceHs;
    }

    public void setPriceHs(int priceHs) {
        this.priceHs = priceHs;
    }

    public String getPhoneNumberHs() {
        return phoneNumberHs;
    }

    public void setPhoneNumberHs(String phoneNumberHs) {
        this.phoneNumberHs = phoneNumberHs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getCountyAddress() {
        return countyAddress;
    }

    public void setCountyAddress(String countyAddress) {
        this.countyAddress = countyAddress;
    }

    public String getAccHomestay() {
        return accHomestay;
    }

    public void setAccHomestay(String accHomestay) {
        this.accHomestay = accHomestay;
    }

    public String getPassHomestay() {
        return passHomestay;
    }

    public void setPassHomestay(String passHomestay) {
        this.passHomestay = passHomestay;
    }

    @Override
    public String toString() {
        return String.format("%s-%s,%s\n",nameHs,address,countyAddress);
    }
}
