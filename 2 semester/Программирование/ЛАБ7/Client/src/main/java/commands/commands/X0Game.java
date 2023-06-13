package commands.commands;

import java.util.Scanner;

public class X0Game {
    public void game() {
        Scanner scanner = new Scanner(System.in);
        String answer = null;
        char [][] field = new char[4][4];
        field[0][0] = '\\';
        field[1][0] = 'a';
        field[2][0] = 'b';
        field[3][0] = 'c';
        field[0][1] = '1';
        field[0][2] = '2';
        field[0][3] = '3';

        while (true) {
            System.out.print("Choose your side X or 0: ");
            answer = scanner.nextLine();
            System.out.println(answer);
            if (!answer.equals("X") & !answer.equals("0")) {
                System.out.println("Enter correct variant, stupid!");
                continue;
            }
            for(int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(field[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("You can use *stop* command to stop game...");
        }
    }

    @Override
    public String toString() {
        return "ColdDirol";
    }
}
