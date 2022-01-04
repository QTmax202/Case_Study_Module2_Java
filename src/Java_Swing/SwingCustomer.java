package Java_Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingCustomer extends JFrame{
    private JList listHomestay1;
    private JPanel swingCustomer;
    private JList listHomestayOfMy;
    private JTextField textregistrationDate;
    private JButton sửaNgàyButton;
    private JButton xóaHomestayButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton tìmHomestayButton1;
    private JButton đặtHomestayButton;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JLabel nameOfHomestay;
    private JLabel priceOfHomestay;
    private JLabel phoneNumberOfHomestay;
    private JLabel addressOfHomestay;
    private JLabel highlightOfHomestay;
    private JButton logOutButton;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingCustomer swingCustomer1 = new SwingCustomer();

    SwingCustomer(){
        super("Homestay");
        this.setPreferredSize(new Dimension(1080, 680));
//        this.setLocationRelativeTo(null);
        this.setContentPane(this.swingCustomer);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingCustomer1.setVisible(false);
                swingAccount.setVisible(true);
            }
        });
    }
}
