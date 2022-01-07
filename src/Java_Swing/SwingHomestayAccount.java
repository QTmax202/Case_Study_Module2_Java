package Java_Swing;

import Product.AccountAdmin;
import Product.Customer;
import Product.CustomerOfHs_Date;
import Product.Homestay;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;
import Regex.PhoneNumberExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingHomestayAccount extends JFrame{
    private JTextField textNameHs;
    private JTextField textPriceHs;
    private JTextField textPhoneHs;
    private JTextField textQAddressHs;
    private JTextField textHighlightHs;
    private JTextField textAddressHS;
    private JButton saveHs;
    private JTextField textAccHs;
    private JTextField textPassHs;
    private JPanel addHomestayAccount;
    private JLabel textNew;
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final ArrayList<Customer> customers = Read_Write_file.readFile(PATH_CUSTOMER);
    private static ArrayList<Homestay> homestays;
//    private static ArrayList<CustomerOfHs_Date> cusOfHome_Date ;
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static final PhoneNumberExample phoneNumberExample = new PhoneNumberExample();

    SwingHomestayAccount(){
        super("Homestay");
        this.setPreferredSize(new Dimension(560, 510));
        this.setLocationRelativeTo(null);
        this.setContentPane(this.addHomestayAccount);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        saveHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveHs(e);
            }
        });
    }

    public void buttonSaveHs(ActionEvent e){
        checkFileHs();
        Homestay homestay = new Homestay(
                textNameHs.getText(),
                Integer.parseInt(textPriceHs.getText()),
                textPhoneHs.getText(),
                textAddressHS.getText(),
                textQAddressHs.getText(),
                textHighlightHs.getText(),
                textAccHs.getText(),
                textPassHs.getText()
        );

        boolean checkAccountRegex = accountPasswordExample.validate(textAccHs.getText());
        boolean checkPasswordRegex = accountPasswordExample.validate(textPassHs.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !phoneNumberExample.validate(textPhoneHs.getText())) {
            textNew.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (!checkAccount(textAccHs.getText())) {
                textNew.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (!checkPhoneNumber(textPhoneHs.getText())) {
                textNew.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = homestays.add(homestay);
                if (check) {
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    fileHomeDate.writerFile(checkFileCusOfHs_Date(textAccHs.getText()), String.format("file_Data/FileHomes%sData", textAccHs.getText()));
                    textNew.setText("Homestay " + homestay.getNameHs() + " tạo thành công!");
                } else {
                    textNew.setText("Tạo Homestay không thành công!");
                }
            }
        }
    }

    public void checkFileHs(){
        if (Read_Write_file1.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
        }
    }

    public ArrayList<CustomerOfHs_Date> checkFileCusOfHs_Date(String accHs) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
            return new ArrayList<>();
        } else {
            return fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
        }
    }

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