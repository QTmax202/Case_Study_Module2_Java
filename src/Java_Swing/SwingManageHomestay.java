package Java_Swing;

import javax.swing.*;
import java.awt.*;

public class SwingManageHomestay extends JFrame{
    private JList listCustomer;
    private JLabel LabelNameCus;
    private JLabel LabelGenderCus;
    private JLabel LabelPhoneCus;
    private JLabel LabelDateOfBirthHs;
    private JLabel labelnationalityCus;
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
    private String accountHomestay;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManageHomestay swingManageHomestay = new SwingManageHomestay();

    SwingManageHomestay(){
        super("Homestay");
        this.setContentPane(this.swingmanageHome);
        this.setPreferredSize(new Dimension(1080, 680));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}
