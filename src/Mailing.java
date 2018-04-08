import java.io.File;

public class Mailing {
    public static boolean sendeMail(String empfaenger, File anhang, Konto absender){
        // TODO implement smtb protocoll
        System.out.println("sende mail von " + absender.getEmail() + " an " + empfaenger + " mit inhalt " + anhang.getName());

        return false;

    }

}
