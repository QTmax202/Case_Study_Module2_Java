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
    private JButton buttonLogOut;
    public Homestay homestay;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManageHomestay swingManageHomestay = new SwingManageHomestay();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> file_Customer = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> file_Homestay = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<HomestayOfCus_Date> fileCusDate = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static ArrayList<Homestay> homestays;
    private static ArrayList<Customer> customers;
//    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
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
        buttonLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingManageHomestay.setEnabled(false);
                swingAccount.setVisible(true);
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

    public void checkFileHs() {
        if (file_Homestay.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = file_Homestay.readFile(PATH_HOMESTAY);
        }
    }

   public void checkFileCusOfHomes_Date() {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay())) == null) {
            cusOfHome_Dates = new ArrayList<>();
        } else {
            cusOfHome_Dates = fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
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

    public ArrayList<HomestayOfCus_Date> checkFileHomeOfCus_Date(String accCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
            return new ArrayList<>();
        } else {
            return fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
        }
    }

//    public ArrayList<CustomerOfHs_Date> checkFileCusOfHs_Date(String accHs) {
//        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
//            return new ArrayList<>();
//        } else {
//            return fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
//        }
//    }

//    public void checkFileCusOfHs_Date() {
//        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay())) == null) {
//            cusOfHome_Dates = new ArrayList<>();
//        } else {
//            cusOfHome_Dates = fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
//        }
//    }

    public void cusOfHomesList() {
        listCustomerOfHsModel.removeAllElements();
        for (CustomerOfHs_Date cusOfHomes : cusOfHome_Dates) {
            listCustomerOfHsModel.addElement(cusOfHomes);
        }
    }

//    public void refreshHomestayList() {
//        listCustomerOfHsModel.removeAllElements();
//        for (CustomerOfHs_Date cusOfHomes : checkFileCusOfHs_Date(homestay.getAccHomestay())) {
//            listCustomerOfHsModel.addElement(cusOfHomes);
//        }
//    }

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
            if (textAccHs.getText().equals(homestay.getAccHomestay())){
                homestays.removeIf((homes) -> (homes.getAccHomestay().equals(homestay.getAccHomestay())));
                file_Homestay.writerFile(homestays, PATH_HOMESTAY);
                if (!checkPhoneNumber(textPhoneHs.getText())){
                    LabelHs.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
                } else {
                    boolean check = homestays.add(homestayNew);
                    if (check ) {
                        file_Homestay.writerFile(homestays, PATH_HOMESTAY);
                        cusOfHomesList();
                        LabelHs.setText("Homestay " + homestayNew.getNameHs() + " lưu thành công!");
                    } else {
                        LabelHs.setText("Tạo Homestay lưu không thành công!");
                    }
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
            ArrayList<HomestayOfCus_Date> homeOfCus_Dates = checkFileHomeOfCus_Date(customerOfHs.getCustomer().getAccount());
            homeOfCus_Dates.removeIf((homeOfCus) -> (homeOfCus.getHomestayOfCus().getAccHomestay().equals(homestay.getAccHomestay())));
            fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customerOfHs.getCustomer().getAccount()));
            cusOfHome_Dates.remove(customerOfHs);
            fileHomeDate.writerFile(cusOfHome_Dates,String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
            cusOfHomesList();
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