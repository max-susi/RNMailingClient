import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Mailing {
    private DataOutputStream outputToServer;
    private BufferedReader returnFromServer;
    private SSLSocket sslSocket;

    public boolean sendeMail(String empfaenger, File anhang, Konto absender) {
        System.out.println("sende mail von " + absender.getEmail() + " an " + empfaenger + " mit inhalt " + anhang.getName());
        String mailtext = "Howdy Partner! \n Hier ist meine Datei:";


        try {
            open(absender.getHostname(), absender.getPort());

            // HELO
            sendeAnServer("EHLO " + absender.getHostname());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Server:" + leseAntwort());
            System.out.println("Server:" + leseAntwort());

            // AUTH

            sendeAnServer("AUTH PLAIN");
            System.out.println("Server:" + leseAntwort());
            sendeAnServer(kodiereAuth(absender));
            String returnval= returnFromServer.readLine();
            if (returnval.contains("authentication failure")){
                System.out.println("Fehler bei Authentifizierung");
                close();
                return false;
            }
            System.out.println("Server:" + returnval);


            // MAIL FROM
            sendeAnServer("MAIL FROM: <" + absender.getEmail() + ">");
            System.out.println("Server:" + leseAntwort());

            // RCPT TO
            sendeAnServer("RCPT TO: <" + empfaenger + ">");
            System.out.println("Server:" + leseAntwort());

            // DATA
            sendeAnServer("DATA");
            System.out.println("Server:" + leseAntwort());
            sendeAnServer("From: " + absender.getEmail());
            sendeAnServer("To: " + empfaenger);
            sendeAnServer("Subject: RN-Prakikum Testmail");
            sendeAnServer("MIME-Version: 1.0");
            sendeAnServer("Content-Type: multipart/mixed; boundary=98766789");
            sendeAnServer("");
            sendeAnServer("--98766789");
            sendeAnServer("Content-Type: text/plain");
            sendeAnServer(mailtext);
            sendeAnServer("");
            sendeAnServer("--98766789");
            haengeDateiAn(anhang);

            // QUIT
            sendeAnServer(".");
            System.out.println("Server:" + leseAntwort());
            sendeAnServer("QUIT");
            System.out.println("Server:" + leseAntwort());

            close();
        } catch (IOException e) {
        }

        return true;

    }

    private void open(String host, int port) throws IOException {
        this.sslSocket = (SSLSocket) (SSLSocketFactory.getDefault()).createSocket(host, port);
        this.outputToServer = new DataOutputStream(sslSocket.getOutputStream());
        this.returnFromServer = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
    }

    private void close() throws IOException {
        this.outputToServer.close();
        this.returnFromServer.close();
        this.sslSocket.close();
    }

    public void haengeDateiAn(File anhang){
        try {
            sendeAnServer("Content-Transfer-Encoding: base64");
            sendeAnServer("Content-Type: " + Files.probeContentType(anhang.toPath()));
            sendeAnServer( "Content-Disposition: attachment; filename=" + anhang.getName());
            sendeAnServer(kodiereAnhang(anhang));
            sendeAnServer("--98766789--");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server:" + leseAntwort());
    }

    public String kodiereAnhang(File anhang) {
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(anhang.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String kodiereAuth(Konto absender) {
//        return Base64.getEncoder().encode(("\0" + konto.getBenutzername() + "\0" + konto.getPasswort()).getBytes()).toString();
//        System.out.println("kodiere " + absender.getBenutzername() + " und pw " + absender.getPasswort() );
        byte[] rawAuth = ("\0" + absender.getBenutzername() + "\0" + absender.getPasswort()).getBytes();
        return Base64.getEncoder().encodeToString(rawAuth);
    }

    private void sendeAnServer(String line) throws IOException {
        System.out.println("Sende " + line + " an server");
        outputToServer.writeBytes(line + "\n\r");
    }

    private ArrayList<String> leseAntwort() {
        ArrayList<String> rueckgabe = new ArrayList<>();
        try {
            do {
                rueckgabe.add(this.returnFromServer.readLine());
            } while (this.returnFromServer.ready());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rueckgabe;
    }

}
