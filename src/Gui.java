import javax.swing.*;

public class Gui {

    public static String getPasswort(String benutzername){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Bitte Passwort fuer " + benutzername + " ein:");
        JPasswordField pass = new JPasswordField(32);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "The title",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if(option == 0)
        {
            char[] password = pass.getPassword();
            return password.toString();
        }return null;
    }
}
