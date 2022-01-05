package Java_Swing;

import Product.*;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SwingCustomer extends JFrame {
    private JList<Homestay> listHomestay;
    private JPanel swingCustomer;
    private JList<HomestayOfCus_Date> listHomestayOfMy;
    private JButton deleteHomeOfMyButton;
    private JButton registrationHomestayButton;
    private JTextField textStartDate;
    private JLabel nameOfHomestay;
    private JLabel priceOfHomestay;
    private JLabel phoneNumberOfHomestay;
    private JLabel addressOfHomestay;
    private JLabel highlightOfHomestay;
    private JButton logOutButton;
    private JComboBox<String> comboBoxGender;
    private JButton saveCusButton;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textDayBirth;
    private JTextField textNationlity;
    private JTextField textAccount;
    private JTextField textPassWord;
    private JTextField textEndDate;
    private JLabel LabelRegistration;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingCustomer swingCustomer1 = new SwingCustomer();
    public static Customer customer;
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> file_Customer = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> file_Homestay = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<HomestayOfCus_Date> fileCusDate = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static final ArrayList<Customer> customers = file_Customer.readFile(PATH_CUSTOMER);
    private static ArrayList<Homestay> homestays ;
    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates;
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static DefaultListModel<Homestay> listHomestayModel;

    SwingCustomer() {
        super("Homestay");
        this.setPreferredSize(new Dimension(1360, 680));
        this.setContentPane(this.swingCustomer);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        registrationHomestayButton.setEnabled(false);
        deleteHomeOfMyButton.setEnabled(false);

        listHomestayModel = new DefaultListModel<>();
        listHomestay.setModel(listHomestayModel);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingCustomer1.setVisible(false);
                swingAccount.setVisible(true);
            }
        });
        listHomestay.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    nameOfHomestay.setText(homestay.getNameHs());
                    priceOfHomestay.setText(String.valueOf(homestay.getPriceHs()));
                    phoneNumberOfHomestay.setText(homestay.getPhoneNumberHs());
                    addressOfHomestay.setText(String.format("%s, %s", homestay.getAddress(), homestay.getCountyAddress()));
                    highlightOfHomestay.setText(homestay.getHighlight());
                    registrationHomestayButton.setEnabled(true);
                } else {
                    registrationHomestayButton.setEnabled(false);
                }
            }
        });
        registrationHomestayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        saveCusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveCusClicked(e);
            }
        });
        listHomestayOfMy.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listHomestayOfMy.getSelectedIndex();
                if (homeNumer >= 0) {
                    HomestayOfCus_Date homeOfCus = homeOfCus_Dates.get(homeNumer);
                    nameOfHomestay.setText(homeOfCus.getHomestayCus().getNameHs());
                    priceOfHomestay.setText(String.valueOf(homeOfCus.getHomestayCus().getPriceHs()));
                    phoneNumberOfHomestay.setText(homeOfCus.getHomestayCus().getPhoneNumberHs());
                    addressOfHomestay.setText(String.format("%s, %s", homeOfCus.getHomestayCus().getAddress(), homeOfCus.getHomestayCus().getCountyAddress()));
                    highlightOfHomestay.setText(homeOfCus.getHomestayCus().getHighlight());
                    deleteHomeOfMyButton.setEnabled(true);
                } else {
                    registrationHomestayButton.setEnabled(false);
                }
            }
        });
        deleteHomeOfMyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int homeNumer = listHomestayOfMy.getSelectedIndex();
                if (homeNumer >= 0) {
                    checkFileHomeOfCus_Date();
                    HomestayOfCus_Date homeOfCus = homeOfCus_Dates.get(homeNumer);
                    homeOfCus_Dates.remove(homeOfCus);
                    checkFileCusOfHs_Date(homeOfCus.getHomestayCus().getNameHs());
                    cusOfHome_Dates.removeIf((cusOfHome_Date) -> (cusOfHome_Date.getCustomer() == customer));
                    fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homeOfCus.getHomestayCus().getAccHomestay()));
                    fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                    listHomeOfCus();
                }
            }
        });
        registrationHomestayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    checkFileHomeOfCus_Date();
                    checkFileCusOfHs_Date(homestay.getAccHomestay());
                    if (checkregistrationDate()) {
                        for (HomestayOfCus_Date homeOfCus : homeOfCus_Dates) {
                            if (homeOfCus.getHomestayCus().getAccHomestay().equals(homestay.getAccHomestay())) {
                                homeOfCus_Dates.remove(homeOfCus);
                                fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                                cusOfHome_Dates.removeIf((cusOfHome_Date) -> (cusOfHome_Date.getCustomer() == customer));
                                fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
                                break;
                            }
                        }
                        HomestayOfCus_Date homeOfCus = new HomestayOfCus_Date(homestay, textStartDate.getText(), textEndDate.getText());
                        homeOfCus_Dates.add(homeOfCus);
                        fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                        CustomerOfHs_Date cusOfHome = new CustomerOfHs_Date(customer, textStartDate.getText(), textEndDate.getText());
                        cusOfHome_Dates.add(cusOfHome);
                        fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
                        LabelRegistration.setText("Đăng ký thành công!");
                        listHomeOfCus();
                    } else {
                        LabelRegistration.setText("Đăng ký không thành công!");
                    }
                }
            }
        });
    }

    public void buttonSaveCusClicked(ActionEvent e) {
        if (textName.getText().equals("")) {
            customer.setName(customer.getName());
        } else {
            customer.setName(textName.getText());
        }
        customer.setGender((String) comboBoxGender.getSelectedItem());
        if (textPhone.getText().equals("") | checkPhoneNumber(textPhone.getText())) {
            customer.setPhoneNumber(customer.getPhoneNumber());
        } else {
            customer.setPhoneNumber(textPhone.getText());
        }
        if (textDayBirth.getText().equals("")) {
            customer.setDateOfBirth(String.valueOf(customer.getDateOfBirth()));
        } else {
            customer.setDateOfBirth(textDayBirth.getText());
        }
        if (textNationlity.getText().equals("")) {
            customer.setNationality(customer.getNationality());
        } else {
            customer.setNationality(textNationlity.getText());
        }
        if (accountPasswordExample.validate(textPassWord.getText())) {
            customer.setPassword(customer.getPassword());
        } else {
            customer.setPassword(textPassWord.getText());
        }
        file_Customer.writerFile(customers, PATH_CUSTOMER);
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

    public void startSwing() {
        textName.setText(customer.getName());
        comboBoxGender.setSelectedItem(customer.getGender());
        textPhone.setText(customer.getPhoneNumber());
        textDayBirth.setText(customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        textNationlity.setText(customer.getNationality());
        textAccount.setText(customer.getAccount());
        textPassWord.setText(customer.getPassword());
    }

    public void refreshHomestayList() {
        listHomestayModel.removeAllElements();
        for (Homestay homestay : homestays) {
            listHomestayModel.addElement(homestay);
        }
    }

    public void listHomeOfCus() {
        DefaultListModel<HomestayOfCus_Date> listHomestayOfCusModel = new DefaultListModel<>();
        listHomestayOfMy.setModel(listHomestayOfCusModel);
        listHomestayOfCusModel.removeAllElements();
        for (HomestayOfCus_Date homeOfCus : homeOfCus_Dates) {
            listHomestayOfCusModel.addElement(homeOfCus);
        }
    }


    public void checkFileHomeOfCus_Date() {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", customer.getName())) == null) {
            homeOfCus_Dates = new ArrayList<>();
        } else {
            homeOfCus_Dates = fileCusDate.readFile(String.format("file_Data/FileCus%sData", customer.getName()));
        }
    }


    public void checkFileCusOfHs_Date(String accHs) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
            cusOfHome_Dates = new ArrayList<>();
        } else {
            cusOfHome_Dates = fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
        }
    }

    public void checkFileHomes(){
        if (file_Homestay.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = file_Homestay.readFile(PATH_HOMESTAY);
        }
    }

    public boolean checkregistrationDate() {
        LocalDate startDate = LocalDate.parse(textStartDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse(textEndDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int intStartDate = startDate.getYear() * 365 + startDate.getDayOfYear();
        int intEndDate = endDate.getYear() * 365 + endDate.getDayOfYear();
        int intNowDate = LocalDate.now().getYear() * 365 + LocalDate.now().getDayOfYear();

        return (intStartDate < intEndDate & intStartDate > intNowDate);
    }
}