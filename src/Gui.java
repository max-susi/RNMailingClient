import javax.swing.*;

public class Gui {

    public static String getPasswort(String benutzername){
        char[] password = null;
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Passwort fuer " + benutzername + " eingeben:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[] { "OK", "Cancel" };
        int option = JOptionPane.showOptionDialog(null, panel, "Account Password", JOptionPane.NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == 0) { // Clicking OK button
            password = pass.getPassword();
        } else {
            System.exit(0);
        }
        return new String(password);
    }
}
