import java.util.Scanner;
import pokemons.Monferno;
import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();


        Scanner input = new Scanner(System.in);

        System.out.println("Выберите покемонов в команду союзников");

        int allyCount = 0;
        int foeCount = 0;

        String str = input.nextLine();
        if (str.contains("Furfrou")) {
            b.addAlly(new Furfrou("   ", 1));
            allyCount++;
        }
        if (str.contains("Yamask")) {
            b.addAlly(new Yamask("    ", 1));
            allyCount++;
        }
        if (str.contains("Cofagrigus")) {
            b.addAlly(new Cofagrigus("", 1));
            allyCount++;
        }
        if (str.contains("Chimchar")) {
            b.addAlly(new Chimchar("  ", 1));
            allyCount++;
        }
        if (str.contains("Monferno")) {
            b.addAlly(new Monferno("  ", 1));
            allyCount++;
        }
        if (str.contains("Infernape")) {
            b.addAlly(new Infernape(" ", 1));
            allyCount++;
        }

        System.out.println("Выберите покемонов в команду Противника");

        str = input.nextLine();
        if (str.contains("Furfrou")) {
            b.addFoe(new Furfrou("   ", 1));
            foeCount++;
        }
        if (str.contains("Yamask")) {
            b.addFoe(new Yamask("    ", 1));
            foeCount++;
        }
        if (str.contains("Cofagrigus")) {
            b.addFoe(new Cofagrigus("", 1));
            foeCount++;
        }
        if (str.contains("Chimchar")) {
            b.addFoe(new Chimchar("  ", 1));
            foeCount++;
        }
        if (str.contains("Monferno")) {
            b.addFoe(new Monferno("  ", 1));
            foeCount++;
        }
        if (str.contains("Infernape")) {
            b.addFoe(new Infernape(" ", 1));
            foeCount++;
        }

        if(allyCount == 0 && foeCount == 0){
            System.out.println("-Поле бытвы пустует");
            System.exit(0);
        }
        if(allyCount == 0){
            System.out.println("-Команда Противника единогласно побеждает");
            System.exit(0);
        }
        if(foeCount == 0){
            System.out.println("-Команда Союзников единогласно побеждает");
            System.exit(0);
        }

        b.go();
    }
}