package Java_Swing;

import Product.*;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;
import Regex.PhoneNumberExample;

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
    private JLabel LabelCus;
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
//    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
//    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates;
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static final PhoneNumberExample phoneNumberExample = new PhoneNumberExample();
    private static DefaultListModel<Homestay> listHomestayModel ;
    private static DefaultListModel<HomestayOfCus_Date> listHomestayOfCusModel;

    SwingCustomer() {
        super("Homestay");
        this.setPreferredSize(new Dimension(1680, 680));
        this.setContentPane(this.swingCustomer);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        registrationHomestayButton.setEnabled(false);
        deleteHomeOfMyButton.setEnabled(false);

        listHomestayModel = new DefaultListModel<>();
        listHomestay.setModel(listHomestayModel);

        listHomestayOfCusModel = new DefaultListModel<>();
        listHomestayOfMy.setModel(listHomestayOfCusModel);

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
                    ArrayList<HomestayOfCus_Date> homeOfCus_Dates = checkFileHomeOfCus_Date(customer.getAccount());
                    HomestayOfCus_Date homeOfCus = homeOfCus_Dates.get(homeNumer);
                    nameOfHomestay.setText(homeOfCus.getHomestayOfCus().getNameHs());
                    priceOfHomestay.setText(String.valueOf(homeOfCus.getHomestayOfCus().getPriceHs()));
                    phoneNumberOfHomestay.setText(homeOfCus.getHomestayOfCus().getPhoneNumberHs());
                    addressOfHomestay.setText(String.format("%s, %s", homeOfCus.getHomestayOfCus().getAddress(), homeOfCus.getHomestayOfCus().getCountyAddress()));
                    highlightOfHomestay.setText(homeOfCus.getHomestayOfCus().getHighlight());
                    textStartDate.setText(homeOfCus.getStartDateCusCus().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    textEndDate.setText(homeOfCus.getEndDateOfCus().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    deleteHomeOfMyButton.setEnabled(true);
                } else {
                    deleteHomeOfMyButton.setEnabled(false);
                }
            }
        });
        deleteHomeOfMyButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int homeNumer = listHomestayOfMy.getSelectedIndex();
                if (homeNumer >= 0) {
                    ArrayList<HomestayOfCus_Date> homeOfCus_Dates = checkFileHomeOfCus_Date(customer.getAccount());
                    HomestayOfCus_Date homeOfCus = homeOfCus_Dates.get(homeNumer);
                    homeOfCus_Dates.remove(homeOfCus);
                    fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                    ArrayList<CustomerOfHs_Date> cusOfHome_Dates = checkFileCusOfHs_Date(homeOfCus.getHomestayOfCus().getAccHomestay());
                    cusOfHome_Dates.removeIf((cusOfHome_Date) -> (cusOfHome_Date.getCustomer().getAccount().equals(customer.getAccount())));
                    fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homeOfCus.getHomestayOfCus().getAccHomestay()));
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
                    if (checkregistrationDate()) {
                        ArrayList<HomestayOfCus_Date> homeOfCus_Dates = checkFileHomeOfCus_Date(customer.getAccount());
                        ArrayList<CustomerOfHs_Date> cusOfHome_Dates = checkFileCusOfHs_Date(homestay.getAccHomestay());
                        try {
                            for (HomestayOfCus_Date homeOfCus : homeOfCus_Dates) {
                                if (homeOfCus.getHomestayOfCus().getAccHomestay().equals(homestay.getAccHomestay())) {
                                    homeOfCus_Dates.remove(homeOfCus);
                                    fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                                    cusOfHome_Dates.removeIf((cusOfHome_Date) -> (cusOfHome_Date.getCustomer().getAccount().equals(customer.getAccount())));
                                    fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            HomestayOfCus_Date homeOfCusNew = new HomestayOfCus_Date(
                                    homestay,
                                    textStartDate.getText(),
                                    textEndDate.getText()
                            );
                            homeOfCus_Dates.add(homeOfCusNew);
                            fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData", customer.getAccount()));
                            CustomerOfHs_Date cusOfHomeNew = new CustomerOfHs_Date(
                                    customer,
                                    textStartDate.getText(),
                                    textEndDate.getText(),
                                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            cusOfHome_Dates.add(cusOfHomeNew);
                            fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", homestay.getAccHomestay()));
                            LabelRegistration.setText("Đăng ký thành công!");
                            listHomeOfCus();
                        }
                    } else {
                        LabelRegistration.setText("Đăng ký không thành công!");
                    }
                }
            }
        });
    }

    public void buttonSaveCusClicked(ActionEvent e) {
        Customer customerNew = new Customer(
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
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhone.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelCus.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (textAccount.getText().equals(customer.getAccount())){
                customers.removeIf((cus) -> (cus.getAccount().equals(customer.getAccount())));
                file_Customer.writerFile(customers, PATH_CUSTOMER);
                if (!checkPhoneNumber(textPhone.getText())){
                    LabelCus.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
                } else {
                    boolean check = customers.add(customerNew);
                    if (check) {
                        file_Customer.writerFile(customers, PATH_CUSTOMER);
                        LabelCus.setText("Tài khoản " + customerNew.getAccount() + " lưu thành công!");
                    } else {
                        LabelCus.setText("Tài khoản lưu không thành công!");
                    }
                }
            } else {
                textAccount.setText(customer.getAccount());
                LabelCus.setText("Tên tài khoản không được thay đổi!");
            }

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
        listHomestayOfCusModel.removeAllElements();
        for (HomestayOfCus_Date homeOfCus : checkFileHomeOfCus_Date(customer.getAccount())) {
            listHomestayOfCusModel.addElement(homeOfCus);
        }
    }

//    public void checkFileHomeOfCus_Date() {
//        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", customer.getName())) == null) {
//            homeOfCus_Dates = new ArrayList<>();
//        } else {
//            homeOfCus_Dates = fileCusDate.readFile(String.format("file_Data/FileCus%sData", customer.getName()));
//        }
//    }

    public ArrayList<HomestayOfCus_Date> checkFileHomeOfCus_Date(String accCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
            return new ArrayList<>();
        } else {
            return fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
        }
    }

    public ArrayList<CustomerOfHs_Date> checkFileCusOfHs_Date(String accHs) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
            return new ArrayList<>();
        } else {
            return fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
        }
    }

//    public ArrayList<HomestayOfCus_Date> checkFileHomeOfCus_Date(String accCus) {
//        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
//            return new ArrayList<>();
//        } else {
//            return fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
//        }
//    }

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

        return (intStartDate < intEndDate & intStartDate >= intNowDate);
    }
}