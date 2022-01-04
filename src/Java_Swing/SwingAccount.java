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

public class SwingAccount extends JFrame{
    private JPanel accountPanel;
    private JTextField textAccountField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JButton registrationButton;
    private JLabel logLabel;
    private JButton registrationHomestayButton;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingCustomerAccount swingCustomerAccount = new SwingCustomerAccount();
    private static final SwingCustomer swingCustomer = new SwingCustomer();
    private static final SwingManage swingManage = new SwingManage();
    private static final SwingHomestayAccount swingHomestayAccount = new SwingHomestayAccount();
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final ArrayList<Customer> customers = Read_Write_file.readFile(PATH_CUSTOMER);
    private static final ArrayList<Homestay> homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();

    SwingAccount(){
        super("Homestay");
        this.setPreferredSize(new Dimension(400, 300));
        this.setLocationRelativeTo(null);
        this.setContentPane(this.accountPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInButton(e);
            }
        });
        
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingCustomerAccount.setVisible(true);
            }
        });
        registrationHomestayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingHomestayAccount.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        swingAccount.setVisible(true);
    }

    public void logInButton(ActionEvent e){

        boolean checkAccRegex = accountPasswordExample.validate(textAccountField.getText());
        boolean checkPassRegex = accountPasswordExample.validate(passwordField.getText());

        if (textAccountField.getText().equals("") || passwordField.getText().equals("")){
            logLabel.setText("xin mời đăng nhập!");
        } else {
            if(!checkAccRegex | !checkPassRegex){
                logLabel.setText("Không có ký tự đặc biệt hay dấu cách!");
            } else if (!logInHomestay()){
                logLabel.setText("Tài khoản không có trong dữ liệu!");
            }
        }
    }

    public boolean logInHomestay(){
        for (AccountAdmin acc : accountAdmins.getListAccountAdmin()){
            if (acc.getAdminAccount().equals(textAccountField.getText()) && acc.getAdminPassword().equals(passwordField.getText())){
                swingManage.setVisible(true);
                swingAccount.setVisible(false);
                swingManage.refreshHomestayList();
                swingManage.refreshCustomerList();
                swingManage.checkFileHs();
                swingManage.checkFileCustomer();
                return true;
            }
        }
        for (Customer customer : customers){
            if (customer.getAccount().equals(textAccountField.getText()) && customer.getPassword().equals(passwordField.getText())){
                swingCustomer.setVisible(true);
                swingCustomer.accountCustomer = customer.getAccount();
                swingAccount.setVisible(false);
                return true;
            }
        }
        for (Homestay homestay : homestays){
            if (homestay.getAccHomestay().equals(textAccountField.getText()) && homestay.getPassHomestay().equals(passwordField.getText())){
                swingCustomer.setVisible(true);
//                swingCustomer.accountHomestay = homestay.getAccHomestay();
                swingAccount.setVisible(false);
                return true;
            }
        }
        return false;
    }
}
