import javax.swing.*;
import java.awt.event.*;

public class ATMSimulator extends JFrame implements ActionListener {

    // Login components
    private JButton loginBtn;
    private JTextField userField;
    private JPasswordField passField;
    // ATM functional components
    private JButton withdrawBtn, changePassBtn, langBtn;
    private JButton exitBtn;
    private JLabel messageLabel;

    // Language buttons
    private JButton hindiBtn, englishBtn;

    // Language State
    private boolean isEnglish = true;

    // Account details
    private String password = "1234";  // Default password
    private double balance = 10000;

    public ATMSimulator() {
        showLanguageSelection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);
        setVisible(true);
    }

    // Language Selection Screen
    private void showLanguageSelection() {
        getContentPane().removeAll();
        repaint();
        setLayout(null);

        JLabel label = new JLabel("Select Language / भाषा चुनें");
        label.setBounds(100, 40, 250, 30);
        add(label);

        englishBtn = new JButton("English");
        englishBtn.setBounds(100, 100, 200, 30);
        englishBtn.addActionListener(this);
        add(englishBtn);

        hindiBtn = new JButton("Hindi");
        hindiBtn.setBounds(100, 150, 200, 30);
        hindiBtn.addActionListener(this);
        add(hindiBtn);

        setVisible(true);
    }

    // Login window
    private void showLoginScreen() {
        getContentPane().removeAll();
        repaint();
        setLayout(null);

        JLabel userLabel = new JLabel(isEnglish ? "User ID:" : "उपयोगकर्ता आईडी:");
        userLabel.setBounds(50, 40, 120, 30);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(180, 40, 150, 30);
        add(userField);

        JLabel passLabel = new JLabel(isEnglish ? "Password:" : "पासवर्ड:");
        passLabel.setBounds(50, 90, 120, 30);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(180, 90, 150, 30);
        add(passField);

        loginBtn = new JButton(isEnglish ? "Login" : "लॉगिन");
        loginBtn.setBounds(130, 150, 150, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        setVisible(true);
    }

    // ATM Menu Screen
    private void showATMMenu() {
        getContentPane().removeAll();
        repaint();
        setLayout(null);

        messageLabel = new JLabel(isEnglish ? "Welcome to ATM" : "एटीएम में आपका स्वागत है");
        messageLabel.setBounds(120, 20, 200, 30);
        add(messageLabel);

        withdrawBtn = new JButton(isEnglish ? "Withdraw Amount" : "राशि निकालें");
        withdrawBtn.setBounds(100, 60, 200, 30);
        withdrawBtn.addActionListener(this);
        add(withdrawBtn);

        changePassBtn = new JButton(isEnglish ? "Change Password" : "पासवर्ड बदलें");
        changePassBtn.setBounds(100, 100, 200, 30);
        changePassBtn.addActionListener(this);
        add(changePassBtn);

        langBtn = new JButton(isEnglish ? "Select Language" : "भाषा चुनें");
        langBtn.setBounds(100, 140,200, 30);
        langBtn.addActionListener(this);
        add(langBtn);

        exitBtn = new JButton(isEnglish ? "Exit" : "बाहर जाएं");
        exitBtn.setBounds(100, 180, 200, 30);
        exitBtn.addActionListener(this);
        add(exitBtn);

        setVisible(true);
    }

    // Withdraw Window
    private void withdrawAmount() {
        try {
            String input = JOptionPane.showInputDialog(this, isEnglish ? "Enter withdraw amount:" : "निकासी राशि दर्ज करें:");
            if (input == null) return;

            input = input.trim();  // string manipulation

            double amt = Double.parseDouble(input);

            if (amt <= 0) throw new Exception(isEnglish ? "Invalid amount" : "अमान्य राशि");
            if (amt > balance) throw new Exception(isEnglish ? "Insufficient balance" : "पर्याप्त शेष नहीं");

            balance -= amt;

            JOptionPane.showMessageDialog(this,
                    isEnglish ? "Withdraw Successful!\nRemaining balance: " + balance
                            : "निकासी सफल!\nशेष राशि: " + balance);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, isEnglish ? "Amount must be numeric!" : "राशि संख्यात्मक होनी चाहिए!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void changePassword() {
        String oldPass = JOptionPane.showInputDialog(this, isEnglish ? "Enter old password:" : "पुराना पासवर्ड दर्ज करें:");
        if (oldPass == null) return;

        if (!oldPass.equals(password)) {
            JOptionPane.showMessageDialog(this, isEnglish ? "Wrong Password!" : "गलत पासवर्ड!");
            return;
        }

        String newPass = JOptionPane.showInputDialog(this, isEnglish ? "Enter new password:" : "नया पासवर्ड दर्ज करें:");
        if (newPass == null) return;

        newPass = newPass.trim();
        password = newPass;

        JOptionPane.showMessageDialog(this, isEnglish ? "Password Changed Successfully!" : "पासवर्ड सफलतापूर्वक बदल दिया गया!");
    }

    private void switchLanguage() {
        showLanguageSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == englishBtn) {
            isEnglish = true;
            showLoginScreen();
        }

        if (e.getSource() == hindiBtn) {
            isEnglish = false;
            showLoginScreen();
        }

        if (e.getSource() == loginBtn) {
            String passTxt = new String(passField.getPassword());
            if (passTxt.equals(password)) showATMMenu();
            else JOptionPane.showMessageDialog(this, isEnglish ? "Login Failed! Try Again." : "लॉगिन विफल! पुनः प्रयास करें।");
        }

        if (e.getSource() == withdrawBtn) withdrawAmount();
        if (e.getSource() == changePassBtn) changePassword();
        if (e.getSource() == langBtn) switchLanguage();
        if (e.getSource() == exitBtn) System.exit(0);
    }

    public static void main(String[] args) {
        new ATMSimulator();
    }
}
