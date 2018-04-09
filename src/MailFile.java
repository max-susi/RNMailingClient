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
        new Mailing().sendeMail(empfaenger, anhang, konto);
    }

    private static boolean aufrufPruefen(String[] args){
        if (args.length == 2){
            File checkanhang = new File(args[1]);
            return checkanhang.exists() && checkanhang.isFile();
        } else{
            System.err.println("Falscher Aufruf: Keine 2 Parameter ");
            System.out.println("Empfaenger: " + args[0]);
            System.out.println("Anhang: " + args[1]);
        }
        System.out.println("Aufrufparameter: empfaengeradresse /pfad/zum/anhang.txt");

        return false;
    }

    private static String getPasswort(String benutzername){
        String passwort = Gui.getPasswort(benutzername);
//        System.out.println("hacker: pw ist " + passwort);
        if (passwort == null){
            System.exit(1);
        }
        return passwort;
    }

    private static Konto createDefaultUser(){
        return new Konto("alexandermax.rickert@haw-hamburg.de", "acc196", "mailgate.informatik.haw-hamburg.de", 465);
    }
}
