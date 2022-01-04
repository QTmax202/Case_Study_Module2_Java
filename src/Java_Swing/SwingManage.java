package Java_Swing;

import Product.AccountAdmin;
import Product.Customer;
import Product.Homestay;
import Read_Write_file.IO_Read_Write_File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel lableName;
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
    private static final SwingAccount swingAccount1 = new SwingAccount();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final ArrayList<Customer> customers = Read_Write_file.readFile(PATH_CUSTOMER);
    private static final ArrayList<Homestay> homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static DefaultListModel<Homestay> listHomestayModel ;

    SwingManage(){
        super("Homestay");
        this.setContentPane(this.swingManage);
        this.setPreferredSize(new Dimension(1180, 680));
//        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        listHomestayModel = new DefaultListModel<>();
        listHomestay.setModel(listHomestayModel);
//        refreshHomestayList();

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingAccount1.setVisible(false);
                swingAccount.setVisible(true);
            }
        });

        addHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        saveHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                }
            }
        });
    }

    public void refreshHomestayList(){
        listHomestayModel.removeAllElements();
//        System.out.printf("%s-%s,%s\n",homestay.getNameHs(), homestay.getAddress(), homestay.getCountyAddress());
        for (Homestay homestay : homestays){
            System.out.printf("%s-%s,%s\n",homestay.getNameHs(), homestay.getAddress(), homestay.getCountyAddress());
            listHomestayModel.addElement(homestay);
        }
    }
}
