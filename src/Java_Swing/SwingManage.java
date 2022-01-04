package Java_Swing;

import Product.AccountAdmin;
import Product.Customer;
import Product.Homestay;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SwingManage extends JFrame{
    private JList<Homestay> listHomestay;
    private JTextField textNameHs;
    private JTextField textPriceHs;
    private JTextField textPhoneHs;
    private JTextField textQAddressHs;
    private JTextField textHighlightHs;
    private JButton addHs;
    private JButton deleteHs;
    private JList<Customer> listCustomer;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textDayBirth;
    private JTextField textNationlity;
    private JButton saveCustomer;
    private JTextField textAccount;
    private JTextField textPassWord;
    private JComboBox<String> comboBoxGender;
    private JPanel swingManage;
    private JButton deleteCustomer;
    private JTextField textAddressHs;
    private JButton saveHs;
    private JButton logOut;
    private JButton addCustomerButton;
    private JTextField textAccHs;
    private JTextField textPassHs;
    private JLabel LabelHs;
    private JLabel LabelCus;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManage manage = new SwingManage();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static ArrayList<Customer> customers ;
    private static ArrayList<Homestay> homestays ;
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static DefaultListModel<Homestay> listHomestayModel ;
    private static DefaultListModel<Customer> listCustomerModel ;

    SwingManage(){
        super("Homestay");
        this.setContentPane(this.swingManage);
        this.setPreferredSize(new Dimension(1560, 680));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        saveHs.setEnabled(false);
        deleteHs.setEnabled(false);
        saveCustomer.setEnabled(false);
        deleteCustomer.setEnabled(false);

        listHomestayModel = new DefaultListModel<>();
        listCustomerModel = new DefaultListModel<>();
        listHomestay.setModel(listHomestayModel);
        listCustomer.setModel(listCustomerModel);

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manage.setVisible(false);
                swingAccount.setVisible(true);
            }
        });

        addHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddHsClicked(e);
            }
        });
        saveHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHomestayList();
            }
        });
        deleteHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                checkFileHs();
                homestays.removeIf((homestay) -> (homestay.getAccHomestay().equals(textAccHs.getText())));
                Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                refreshHomestayList();
            }
        });
        listHomestay.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0){
                    Homestay homestay = homestays.get(homeNumer);
                    textNameHs.setText(homestay.getNameHs());
                    textPriceHs.setText(String.valueOf(homestay.getPriceHs()));
                    textPhoneHs.setText(homestay.getPhoneNumberHs());
                    textAddressHs.setText(homestay.getAddress());
                    textQAddressHs.setText(homestay.getCountyAddress());
                    textHighlightHs.setText(homestay.getHighlight());
                    textAccHs.setText(homestay.getAccHomestay());
                    textPassHs.setText(homestay.getPassHomestay());
                    saveHs.setEnabled(true);
                    deleteHs.setEnabled(true);
                } else {
                    saveHs.setEnabled(false);
                    deleteHs.setEnabled(false);
                }
            }
        });
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddCusClicked(e);
            }
        });
        saveCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCustomerList();
            }
        });
        deleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customers.removeIf((customer) -> (customer.getAccount().equals(textAccount.getText())));
                Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                refreshCustomerList();
            }
        });
        listCustomer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listCustomer.getSelectedIndex();
                if (homeNumer >= 0){
                    Customer customer = customers.get(homeNumer);
                    textName.setText(customer.getName());
                    comboBoxGender.setSelectedItem(customer.getGender());
                    textPhone.setText(customer.getPhoneNumber());
                    textDayBirth.setText(customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    textNationlity.setText(customer.getNationality());
                    textAccount.setText(customer.getAccount());
                    textPassWord.setText(customer.getPassword());
                    saveCustomer.setEnabled(true);
                    deleteCustomer.setEnabled(true);
                } else {
                    saveCustomer.setEnabled(false);
                    deleteCustomer.setEnabled(false);
                }
            }
        });
    }

    public void refreshHomestayList(){
        checkFileHs();
        listHomestayModel.removeAllElements();
        for (Homestay homestay : homestays){
            listHomestayModel.addElement(homestay);
        }
    }

    public void refreshCustomerList(){
        checkFileCustomer();
        listCustomerModel.removeAllElements();
        for (Customer customer : customers){
            listCustomerModel.addElement(customer);
        }
    }

    public void checkFileHs(){
        if (Read_Write_file1.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
        }
    }

    public void checkFileCustomer(){
        if (Read_Write_file.readFile(PATH_CUSTOMER) == null) {
            customers = new ArrayList<>();
        } else {
            customers = Read_Write_file.readFile(PATH_CUSTOMER);
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

    public void buttonAddHsClicked(ActionEvent e){
//        checkFileHs();
        Homestay homestay = new Homestay(
                textNameHs.getText(),
                Integer.parseInt(textPriceHs.getText()),
                textPhoneHs.getText(),
                textAddressHs.getText(),
                textQAddressHs.getText(),
                textHighlightHs.getText(),
                textAccHs.getText(),
                textPassHs.getText()
        );

        boolean checkAccountRegex = accountPasswordExample.validate(textAccHs.getText());
        boolean checkPasswordRegex = accountPasswordExample.validate(textPassHs.getText());

        if (!checkAccountRegex | !checkPasswordRegex) {
            LabelHs.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (!checkAccount(textAccHs.getText())) {
                LabelHs.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (!checkPhoneNumber(textPhoneHs.getText())) {
                LabelHs.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = homestays.add(homestay);
                if (check) {
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    refreshHomestayList();
                    LabelHs.setText("Homestay " + homestay.getNameHs() + " tạo thành công!");
                } else {
                    LabelHs.setText("Tạo Homestay không thành công!");
                }
            }
        }
    }

    public void buttonAddCusClicked(ActionEvent e) {
//        checkFileCustomer();
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
            LabelCus.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (!checkAccount(textAccount.getText())) {
                LabelCus.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (!checkPhoneNumber(textPhone.getText())) {
                LabelCus.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = customers.add(customer);
                if (check) {
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    refreshCustomerList();
                    LabelCus.setText("Tài khoản " + customer.getAccount() + " tạo thành công!");
                } else {
                    LabelCus.setText("Tạo tài khoản không thành công!");
                }
            }
        }
    }
}
