import java.io.*;
import java.util.Properties;

public class Konto {
    private static final String propDatei = "./config.properties";
    private String email;
    private String benutzername;
    private String passwort;
    private String hostname;
    private int port;

    public Konto(String email, String benutzername, String hostname, int port) {
        this.email = email;
        this.benutzername = benutzername;
        this.hostname = hostname;
        this.port = port;
    }

    public Konto(){
        getKonto();
    };

    private Konto getKonto(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propDatei));
            email = properties.getProperty("email");
            benutzername = properties.getProperty("benutzername");
            hostname = properties.getProperty("hostname");
            port = Integer.parseInt(properties.getProperty("port", "25"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void saveKonto(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propDatei));
            properties.setProperty("email", email);
            properties.setProperty("benutzername", benutzername);
            properties.setProperty("hostname", hostname);
            properties.setProperty("port", port + "");
            properties.store(new FileOutputStream(propDatei), "max");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
