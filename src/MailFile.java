import java.io.File;
import java.io.IOException;

public class MailFile {
    public static void main(String[] args) {
        if (!aufrufPruefen(args)){
            System.exit(1);
        }
        String empfaenger = args[0];
        File anhang = new File(args[1]);

        Konto konto = new Konto();
        if (konto.getBenutzername() == null){
            System.out.println("Erstelle default user");
            konto = createDefaultUser();
            konto.saveKonto();
        }
        konto.setPasswort(getPasswort(konto.getBenutzername()));
        Mailing.sendeMail(empfaenger, anhang, konto);
    }

    private static boolean aufrufPruefen(String[] args){
        if (args.length == 2){
            if (args[0].toLowerCase().matches("^\\w+[@]\\w+[.][a-z]+$")){
                File checkanhang = new File(args[1]);
                return checkanhang.exists() && checkanhang.isFile();
            }
        }
        System.out.println("Aufrufparameter: empfaengeradresse /pfad/zum/anhang.txt");

        return false;
    }

    private static String getPasswort(String benutzername){
        String passwort = Gui.getPasswort(benutzername);
        if (passwort == null){
            System.exit(1);
        }
        return passwort;
    }

    private static Konto createDefaultUser(){
        return new Konto("alexandermax.rickert@haw-hamburg.de", "acc196", "mailgate.informatik.haw-hamburg.de", 465);
    }
}
