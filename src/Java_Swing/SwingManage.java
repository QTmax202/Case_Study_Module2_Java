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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SwingManage extends JFrame {
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
    private JList<HomestayOfCus_Date> HomeOfCusList;
    private JList<CustomerOfHs_Date> CusOfHomesList;
    private JLabel labelCusOfHs;
    private JLabel labelHomesOfCus;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManage manage = new SwingManage();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<HomestayOfCus_Date> fileCusDate = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static ArrayList<Customer> customers;
    private static ArrayList<Homestay> homestays;
//    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
//    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates;
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static final PhoneNumberExample phoneNumberExample = new PhoneNumberExample();
    private static DefaultListModel<Homestay> listHomestayModel;
    private static DefaultListModel<Customer> listCustomerModel;
    private static DefaultListModel<HomestayOfCus_Date> listHomestayOfCusModel;
    private static DefaultListModel<CustomerOfHs_Date> listCustomerOfHsModel;

    SwingManage() {
        super("Homestay");
        this.setContentPane(this.swingManage);
        this.setPreferredSize(new Dimension(1820, 680));
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

        listCustomerOfHsModel = new DefaultListModel<>();
        CusOfHomesList.setModel(listCustomerOfHsModel);

        listHomestayOfCusModel = new DefaultListModel<>();
        HomeOfCusList.setModel(listHomestayOfCusModel);

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
                buttonSaveHsClicked(e);
            }
        });
        deleteHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    homestays.remove(homestay);
                    LabelHs.setText("Xóa homestay " + homestay.getNameHs() + " thành công!");
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    refreshHomestayList();
                }
            }
        });
        listHomestay.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    textNameHs.setText(homestay.getNameHs());
                    textPriceHs.setText(String.valueOf(homestay.getPriceHs()));
                    textPhoneHs.setText(homestay.getPhoneNumberHs());
                    textAddressHs.setText(homestay.getAddress());
                    textQAddressHs.setText(homestay.getCountyAddress());
                    textHighlightHs.setText(homestay.getHighlight());
                    textAccHs.setText(homestay.getAccHomestay());
                    textPassHs.setText(homestay.getPassHomestay());
                    cusOfHomesList(homestay.getAccHomestay(), homestay.getNameHs());
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
                buttonSaveCusClicked(e);
            }
        });
        deleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CusNumer = listCustomer.getSelectedIndex();
                if (CusNumer >= 0) {
                    Customer customer = customers.get(CusNumer);
                    customers.remove(customer);
                    LabelCus.setText("Xóa khách hàng " + customer.getName() + " thành công!");
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    refreshCustomerList();
                }
            }
        });
        listCustomer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int CusNumer = listCustomer.getSelectedIndex();
                if (CusNumer >= 0) {
                    Customer customer = customers.get(CusNumer);
                    textName.setText(customer.getName());
                    comboBoxGender.setSelectedItem(customer.getGender());
                    textPhone.setText(customer.getPhoneNumber());
                    textDayBirth.setText(customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    textNationlity.setText(customer.getNationality());
                    textAccount.setText(customer.getAccount());
                    textPassWord.setText(customer.getPassword());
                    homeOfCusList(customer.getAccount(), customer.getName());
                    saveCustomer.setEnabled(true);
                    deleteCustomer.setEnabled(true);
                } else {
                    saveCustomer.setEnabled(false);
                    deleteCustomer.setEnabled(false);
                }
            }
        });
        CusOfHomesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        HomeOfCusList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }

    public void refreshHomestayList() {
        listHomestayModel.removeAllElements();
        for (Homestay homestay : homestays) {
            listHomestayModel.addElement(homestay);
        }
    }

    public void refreshCustomerList() {
        listCustomerModel.removeAllElements();
        for (Customer customer : customers) {
            listCustomerModel.addElement(customer);
        }
    }

    public void cusOfHomesList(String accHomes, String nameHomes) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHomes)) != null) {
            labelCusOfHs.setText(String.format("Danh sách khách hàng của %s", nameHomes));
            listCustomerOfHsModel.removeAllElements();
            for (CustomerOfHs_Date cusOfHs : checkFileCusOfHs_Date(accHomes)) {
                listCustomerOfHsModel.addElement(cusOfHs);
            }
        }
    }

    public void homeOfCusList(String accCus, String nameCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) != null) {
            labelHomesOfCus.setText(String.format("Danh sách homestay của %s", nameCus));
            listHomestayOfCusModel.removeAllElements();
            for (HomestayOfCus_Date homeOfCus : checkFileHomeOfCus_Date(accCus)) {
                listHomestayOfCusModel.addElement(homeOfCus);
            }
        }
    }

    public void checkFileHs() {
        if (Read_Write_file1.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
        }
    }

    public void checkFileCustomer() {
        if (Read_Write_file.readFile(PATH_CUSTOMER) == null) {
            customers = new ArrayList<>();
        } else {
            customers = Read_Write_file.readFile(PATH_CUSTOMER);
        }
    }

    public boolean checkAccount(String account) {
        for (Customer customer : customers) {
            if (customer.getAccount().equals(account)) {
                return true;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getAccHomestay().equals(account)) {
                return true;
            }
        }
        for (AccountAdmin acc : accountAdmins.getListAccountAdmin()) {
            if (acc.getAdminAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getPhoneNumberHs().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CustomerOfHs_Date> checkFileCusOfHs_Date(String accHs) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
            return new ArrayList<>();
        } else {
            return fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
        }
    }

    public void buttonAddHsClicked(ActionEvent e) {
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
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhoneHs.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelHs.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (checkAccount(textAccHs.getText())) {
                LabelHs.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (checkPhoneNumber(textPhoneHs.getText())) {
                LabelHs.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = homestays.add(homestay);
                if (check) {
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    fileHomeDate.writerFile(checkFileCusOfHs_Date(textAccHs.getText()), String.format("file_Data/FileHomes%sData", textAccHs.getText()));
                    refreshHomestayList();
                    LabelHs.setText("Homestay " + homestay.getNameHs() + " tạo thành công!");
                } else {
                    LabelHs.setText("Tạo Homestay không thành công!");
                }
            }
        }
    }

    public ArrayList<HomestayOfCus_Date> checkFileHomeOfCus_Date(String accCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
            return new ArrayList<>();
        } else {
            return fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
        }
    }

    public void buttonAddCusClicked(ActionEvent e) {
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
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhone.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelCus.setText("Không có ký tự đặc biệt hay dấu cách!");
        } else {
            if (checkAccount(textAccount.getText())) {
                LabelCus.setText("Bị trùng tài khoản, xin nhập tài khoản khác!");
            } else if (checkPhoneNumber(textPhone.getText())) {
                LabelCus.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
            } else {
                boolean check = customers.add(customer);
                if (check) {
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    fileCusDate.writerFile(checkFileHomeOfCus_Date(textAccount.getText()), String.format("file_Data/FileCus%sData", textAccount.getText()));
                    refreshCustomerList();
                    LabelCus.setText("Tài khoản " + customer.getAccount() + " tạo thành công!");
                } else {
                    LabelCus.setText("Tạo tài khoản không thành công!");
                }
            }
        }
    }

    public void buttonSaveHsClicked(ActionEvent e) {
        int homeNumer = listHomestay.getSelectedIndex();
        if (homeNumer >= 0) {
            Homestay homestay = homestays.get(homeNumer);
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
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    if (checkPhoneNumber(textPhoneHs.getText())) {
                        LabelHs.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
                    } else {
                        boolean check = homestays.add(homestayNew);
                        if (check ) {
                            Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                            refreshHomestayList();
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
    }

    public void buttonSaveCusClicked(ActionEvent e) {
        int CusNumer = listCustomer.getSelectedIndex();
        if (CusNumer >= 0) {
            Customer customer = customers.get(CusNumer);
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
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    if (checkPhoneNumber(textPhone.getText())) {
                        LabelCus.setText("Bị trùng số điện thoại, xin nhập số điện thoại khác!");
                    } else {
                        boolean check = customers.add(customerNew);
                        if (check) {
                            Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                            refreshCustomerList();
                            LabelCus.setText("Tài khoản " + customerNew.getAccount() + "  lưu thành công!");
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
    }
}