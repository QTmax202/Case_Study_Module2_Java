package Java_Swing;

import Product.Customer;
import Product.CustomerOfHs_Date;
import Product.Homestay;
import Product.HomestayOfCus_Date;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;
import Regex.PhoneNumberExample;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SwingManageHomestay extends JFrame{
    private JList<CustomerOfHs_Date> listCustomer;
    private JLabel LabelNameCus;
    private JLabel LabelGenderCus;
    private JLabel LabelPhoneCus;
    private JLabel LabelDateOfBirthHs;
    private JLabel LabelnationalityCus;
    private JLabel LabelRegistrationDateCus;
    private JTextField textQAddressHs;
    private JTextField textAddressHs;
    private JButton saveHs;
    private JTextField textAccHs;
    private JTextField textNameHs;
    private JTextField textPriceHs;
    private JTextField textPhoneHs;
    private JTextField textHighlightHs;
    private JLabel LabelHs;
    private JTextField textPassHs;
    private JButton deleteCusButton;
    private JPanel swingmanageHome;
    private JLabel LabelEndDate;
    private JLabel LabelStartDate;
    public Homestay homestay;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManageHomestay swingManageHomestay = new SwingManageHomestay();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> file_Customer = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> file_Homestay = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<HomestayOfCus_Date> fileCusDate = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static ArrayList<Customer> customers ;
    private static final ArrayList<Homestay> homestays = file_Homestay.readFile(PATH_HOMESTAY);;
    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates;
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static final PhoneNumberExample phoneNumberExample = new PhoneNumberExample();
    private static DefaultListModel<CustomerOfHs_Date> listCustomerOfHsModel;

    SwingManageHomestay(){
        super("Homestay");
        this.setContentPane(this.swingmanageHome);
        this.setPreferredSize(new Dimension(1080, 680));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        listCustomerOfHsModel = new DefaultListModel<>();
        listCustomer.setModel(listCustomerOfHsModel);


        saveHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveHomesClicked(e);
            }
        });
        listCustomer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int CusOfHomesNumer = listCustomer.getSelectedIndex();
                if (CusOfHomesNumer >= 0) {
                    CustomerOfHs_Date customerOfHs = cusOfHome_Dates.get(CusOfHomesNumer);
                    LabelNameCus.setText(customerOfHs.getCustomer().getName());
                    LabelGenderCus.setText(customerOfHs.getCustomer().getGender());
                    LabelPhoneCus.setText(customerOfHs.getCustomer().getPhoneNumber());
                    LabelDateOfBirthHs.setText(customerOfHs.getCustomer().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    LabelnationalityCus.setText(customerOfHs.getCustomer().getNationality());
                    LabelRegistrationDateCus.setText(customerOfHs.getRegistrationDateOfHs().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    LabelStartDate.setText(customerOfHs.getStartDateOfHs().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    LabelEndDate.setText(customerOfHs.getEndDateOfHs().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
        deleteCusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonDeleteCusOfHomesClicked(e);
            }
        });
    }

    public void checkFileCustomer(){
        if (file_Customer.readFile(PATH_CUSTOMER) == null) {
            customers = new ArrayList<>();
        } else {
            customers = file_Customer.readFile(PATH_CUSTOMER);
        }
    }

    public void checkFileHomeOfCus_Date(String accCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
            homeOfCus_Dates = new ArrayList<>();
        } else {
            homeOfCus_Dates = fileCusDate.readFile(String.format("file_Data/FileCus%sData", homestay.getAccHomestay()));
        }
    }

    public void checkFileCusOfHs_Date() {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay())) == null) {
            cusOfHome_Dates = new ArrayList<>();
        } else {
            cusOfHome_Dates = fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
        }
    }

    public void refreshHomestayList() {
        listCustomerOfHsModel.removeAllElements();
        for (CustomerOfHs_Date cusOfHomes : cusOfHome_Dates) {
            listCustomerOfHsModel.addElement(cusOfHomes);
        }
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

    public void buttonSaveHomesClicked(ActionEvent e){
        Homestay homestayNew = new Homestay(
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
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhoneHs.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelHs.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            boolean check = homestays.add(homestayNew);
            if (textAccHs.getText().equals(homestay.getAccHomestay())){
                if (check ) {
                    homestays.removeIf((homes) -> (homes.getAccHomestay().equals(homestay.getAccHomestay())));
                    file_Homestay.writerFile(homestays, PATH_HOMESTAY);
                    refreshHomestayList();
                    LabelHs.setText("Homestay " + homestayNew.getNameHs() + " lưu thành công!");
                } else {
                    LabelHs.setText("Tạo Homestay lưu không thành công!");
                }
            } else {
                textAccHs.setText(homestay.getAccHomestay());
                LabelHs.setText("Tên tài khoản không được thay đổi!");
            }
        }
    }

    public void buttonDeleteCusOfHomesClicked(ActionEvent e){
        int CusOfHomesNumer = listCustomer.getSelectedIndex();
        if (CusOfHomesNumer >= 0) {
            CustomerOfHs_Date customerOfHs = cusOfHome_Dates.get(CusOfHomesNumer);
            cusOfHome_Dates.remove(customerOfHs);
            fileHomeDate.writerFile(cusOfHome_Dates,String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
            checkFileHomeOfCus_Date(customerOfHs.getCustomer().getAccount());
            homeOfCus_Dates.removeIf((homeOfCus) -> (homeOfCus.getHomestayOfCus().equals(homestay)));
            fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customerOfHs.getCustomer().getAccount()));
            refreshHomestayList();
        }
    }

    public void startSwing() {
        textNameHs.setText(homestay.getNameHs());
        textPriceHs.setText(String.valueOf(homestay.getPriceHs()));
        textPhoneHs.setText(homestay.getPhoneNumberHs());
        textAddressHs.setText(homestay.getAddress());
        textQAddressHs.setText(homestay.getCountyAddress());
        textHighlightHs.setText(homestay.getHighlight());
        textAccHs.setText(homestay.getAccHomestay());
        textPassHs.setText(homestay.getPassHomestay());
    }
}
