package Java_Swing;

import Product.AccountAdmin;
import Product.Customer;
import Product.Homestay;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingCustomerAccount extends JFrame {
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textDayBirth;
    private JTextField textNationlity;
    private JButton saveCustomer;
    private JTextField textAccount;
    private JTextField textPassWord;
    private JComboBox<String> comboBoxGender;
    private JPanel addCustomerAccount;
    private JLabel textNew;
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static ArrayList<Customer> customers;
    private static final ArrayList<Homestay> homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();

    SwingCustomerAccount() {
        super("Homestay");
        this.setPreferredSize(new Dimension(360, 510));
        this.setLocationRelativeTo(null);
        this.setContentPane(this.addCustomerAccount);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        saveCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonClicked(e);
            }
        });
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

    public void checkFileCustomer(){
        if (Read_Write_file.readFile(PATH_CUSTOMER) == null) {
            customers = new ArrayList<>();
        } else {
            customers = Read_Write_file.readFile(PATH_CUSTOMER);
        }
    }

    public void saveButtonClicked(ActionEvent e) {
        checkFileCustomer();
        Customer customer = new Customer(
                textName.getText(),
                (String) comboBoxGender.getSelectedItem(),
                textPhone.getText(),
                textDayBirth.getText(),
                textNationlity.getText(),
                textAccount.getText(),
                textPassWord.getText()
        );

        boolean checkAccountRegex = accountPasswordExample.validate(textAccount.getText());
        boolean checkPasswordRegex = accountPasswordExample.validate(textPassWord.getText());

        if (!checkAccountRegex | !checkPasswordRegex) {
            textNew.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (!checkAccount(textAccount.getText())) {
                textNew.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (!checkPhoneNumber(textPhone.getText())) {
                textNew.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = customers.add(customer);
                if (check) {
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    textNew.setText("Tài khoản " + customer.getAccount() + " tạo thành công!");
                } else {
                    textNew.setText("Tạo tài khoản không thành công!");
                }
            }
        }
    }
}

