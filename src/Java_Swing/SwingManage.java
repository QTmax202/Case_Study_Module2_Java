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
    private static ArrayList<Customer> customers ;
    private static ArrayList<Homestay> homestays ;
    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates ;
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static DefaultListModel<Homestay> listHomestayModel ;
    private static DefaultListModel<Customer> listCustomerModel ;
    private static DefaultListModel<HomestayOfCus_Date> listHomestayOfCusModel ;
    private static DefaultListModel<CustomerOfHs_Date> listCustomerOfHsModel ;

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
        listHomestayOfCusModel = new DefaultListModel<>();
        HomeOfCusList.setModel(listHomestayOfCusModel);
        listCustomerOfHsModel = new DefaultListModel<>();
        CusOfHomesList.setModel(listCustomerOfHsModel);

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
                if (homeNumer >= 0){
                    Homestay homestay = homestays.get(homeNumer);
                    homestays.remove(homestay);
                    LabelHs.setText("Xóa homestay "+homestay.getNameHs()+" thành công!");
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    refreshHomestayList();
                }
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
                if (CusNumer >= 0){
                    Customer customer = customers.get(CusNumer);
                    customers.remove(customer);
                    LabelCus.setText("Xóa khách hàng "+customer.getName()+" thành công!");
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    refreshCustomerList();
                }
            }
        });
        listCustomer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int CusNumer = listCustomer.getSelectedIndex();
                if (CusNumer >= 0){
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

    public void refreshHomestayList(){
        listHomestayModel.removeAllElements();
        for (Homestay homestay : homestays){
            listHomestayModel.addElement(homestay);
        }
    }

    public void refreshCustomerList(){
        listCustomerModel.removeAllElements();
        for (Customer customer : customers){
            listCustomerModel.addElement(customer);
        }
    }

    public void cusOfHomesList(String accHomes , String nameHomes){
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHomes)) == null){
            cusOfHome_Dates = new ArrayList<>();
        } else {
            labelCusOfHs.setText(String.format("Danh sách khách hàng của %s", nameHomes));
            cusOfHome_Dates = fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHomes));
            listCustomerOfHsModel.removeAllElements();
            for (CustomerOfHs_Date cusOfHs : cusOfHome_Dates){
                listCustomerOfHsModel.addElement(cusOfHs);
            }
        }
    }

    public void homeOfCusList(String accCus, String nameCus){
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null){
            homeOfCus_Dates = new ArrayList<>();
        } else {
            labelHomesOfCus.setText(String.format("Danh sách homestay của %s", nameCus));
            homeOfCus_Dates = fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
            listHomestayOfCusModel.removeAllElements();
            for (HomestayOfCus_Date homeOfCus : homeOfCus_Dates){
                listHomestayOfCusModel.addElement(homeOfCus);
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
                    fileHomeDate.writerFile(cusOfHome_Dates, String.format("file_Data/FileHomes%sData", textAccHs.getText()));
                    refreshHomestayList();
                    LabelHs.setText("Homestay " + homestay.getNameHs() + " tạo thành công!");
                } else {
                    LabelHs.setText("Tạo Homestay không thành công!");
                }
            }
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
                    fileCusDate.writerFile(homeOfCus_Dates, String.format("file_Data/FileCus%sData",textAccount.getText()));
                    refreshCustomerList();
                    LabelCus.setText("Tài khoản " + customer.getAccount() + " tạo thành công!");
                } else {
                    LabelCus.setText("Tạo tài khoản không thành công!");
                }
            }
        }
    }

    public void buttonSaveHsClicked(ActionEvent e){
        int homeNumer = listHomestay.getSelectedIndex();
        if (homeNumer >= 0){
            Homestay homestay = homestays.get(homeNumer);
            String nameHsNew = textNameHs.getText();
            if (nameHsNew.equals("")){
                homestay.setNameHs(homestay.getNameHs());
            } else {
                homestay.setNameHs(nameHsNew);
            }
            if (textPriceHs.getText().equals("")){
                homestay.setPriceHs(homestay.getPriceHs());
            } else {
                homestay.setPriceHs(Integer.parseInt(textPriceHs.getText()));
            }
            if (textPriceHs.getText().equals("") | !checkPhoneNumber(textPriceHs.getText())){
                homestay.setPhoneNumberHs(homestay.getPhoneNumberHs());
            } else {
                homestay.setPhoneNumberHs(textPriceHs.getText());
            }
            if (textAddressHs.getText().equals("")){
                homestay.setAddress(homestay.getAddress());
            } else {
                homestay.setAddress(textAddressHs.getText());
            }
            if (textQAddressHs.getText().equals("")){
                homestay.setCountyAddress(homestay.getCountyAddress());
            } else {
                homestay.setCountyAddress(textQAddressHs.getText());
            }
            if (textHighlightHs.getText().equals("")){
                homestay.setHighlight(homestay.getHighlight());
            } else {
                homestay.setHighlight(textHighlightHs.getText());
            }
            String passHsNew = textPassHs.getText();
            if (accountPasswordExample.validate(passHsNew)){
                homestay.setPassHomestay(passHsNew);
            } else {
                homestay.setPassHomestay(homestay.getPassHomestay());
            }
            Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
            LabelHs.setText("Lưu thành công!");
            refreshHomestayList();
        }
    }

    public void buttonSaveCusClicked(ActionEvent e){
        int CusNumer = listCustomer.getSelectedIndex();
        if (CusNumer >= 0){
            Customer customer = customers.get(CusNumer);
            if (textName.getText().equals("")){
                customer.setName(customer.getName());
            } else {
                customer.setName(textName.getText());
            }
            customer.setGender((String) comboBoxGender.getSelectedItem());
            if (textPhone.getText().equals("") | checkPhoneNumber(textPhone.getText())){
                customer.setPhoneNumber(customer.getPhoneNumber());
            } else {
                customer.setPhoneNumber(textPhone.getText());
            }
            if (textDayBirth.getText().equals("")){
                customer.setDateOfBirth(String.valueOf(customer.getDateOfBirth()));
            } else {
                customer.setDateOfBirth(textDayBirth.getText());
            }
            if (textNationlity.getText().equals("")){
                customer.setNationality(customer.getNationality());
            } else {
                customer.setNationality(textNationlity.getText());
            }
            if (accountPasswordExample.validate(textPassWord.getText())){
                customer.setPassword(customer.getPassword());
            } else {
                customer.setPassword(textPassWord.getText());
            }
            Read_Write_file.writerFile(customers, PATH_CUSTOMER);
            LabelCus.setText("Lưu thành công!");
            refreshCustomerList();
        }
    }
}