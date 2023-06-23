package serverlogic.authorization;

import collection.userscollection.User;
import database.HashingMD2;
import database.actions.UserActions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;

public class SignIn {
    private String username = null;
    private String password = null;

    UserActions userActions = new UserActions();
    HashingMD2 hashingMD2 = new HashingMD2();
    Scanner scanner = new Scanner(System.in);


    public void signIn() {
        System.out.print("Пожалуйста, введите username: ");
        username = scanner.nextLine();
        System.out.print("Пожалуйста, введите пароль: ");
        password = scanner.nextLine();

        if (userActions.userExists(username) & userActions.getPermisionToLogin(username, password)) {
            System.out.println("Вход разрешен!");
            User user = userActions.getUser(username, hashingMD2.encodeStringMD2(password));
            System.out.println(user.getId());
            System.out.println(user.getUsername());
        }
    }
}
