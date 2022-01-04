package Product_Manage;

import Product.AccountAdmin;
import Product.Customer;
import Product.Homestay;
import Read_Write_file.IO_Read_Write_File;

import java.util.ArrayList;

public class CheckLoginConditions {
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final ArrayList<Customer> customers = Read_Write_file.readFile(PATH_CUSTOMER);
    private static final ArrayList<Homestay> homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
    private static final AccountAdmin accountAdmins = new AccountAdmin();

    public boolean checkAccount(String account) {
        for (Customer customer : customers) {
            if (customer.getAccount().equals(account)) {
                return false;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getAccHomestay().equals(account)) {
                return false;
            }
        }
        for (AccountAdmin acc : accountAdmins.getListAccountAdmin()) {
            if (acc.getAdminAccount().equals(account)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getPhoneNumberHs().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
}
