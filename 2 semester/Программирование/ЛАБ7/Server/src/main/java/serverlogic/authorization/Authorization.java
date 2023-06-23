package serverlogic.authorization;

import database.HashingMD2;
import database.actions.UserActions;
import serverlogic.outputchannel.ResponseLogic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Authorization {
    ResponseLogic responseLogic = new ResponseLogic();
    UserActions userActions = new UserActions();
    private DataOutputStream dataOutputStream;

    HashingMD2 hashingMD2 = new HashingMD2();
    private Integer clientId;

    public Authorization (Integer clientId) {
        this.clientId = clientId;
    }

    private ArrayList<String> greetingArrayList = new ArrayList<>();

    {
        greetingArrayList.add("Please login: ");
        greetingArrayList.add("1. Sing In");
        greetingArrayList.add("2. Sing Up");
        greetingArrayList.add("3. Exit");
        greetingArrayList.add("Choose your answer: ");
    }
    public void authorization() throws IOException, InterruptedException {
        String answer;
        while(true) {
            responseLogic.sendResponseAsArrayList(greetingArrayList, clientId);
            answer = responseLogic.getRequest(clientId);
            if(answer.equals("1")) {
                if(signIn(clientId)) {
                    responseLogic.sendResponseAsString(Boolean.toString(true), clientId);
                    break;
                }
                else responseLogic.sendResponseAsString(Boolean.toString(false), clientId);
            }
            if(answer.equals("2")) {
                if(signUp(clientId)) {
                    responseLogic.sendResponseAsString(Boolean.toString(true), clientId);
                    break;
                }
                else responseLogic.sendResponseAsString(Boolean.toString(false), clientId);
            }
            if(answer.equals("3")) {
                break;
            }
        }
    }

    public boolean signIn(Integer clientId) throws IOException {
        String username = null;
        String password = null;
        responseLogic.sendResponseAsString("Please enter your username: ", clientId);
        username = responseLogic.getRequest(clientId);
        responseLogic.sendResponseAsString("Please enter your password: ", clientId);
        password = responseLogic.getRequest(clientId);

        if (userActions.userExists(username) & userActions.getPermisionToLogin(username, password)) {
            System.out.println("User " + username + " user has been logged in!");
            return true;
        }
        else {
            return false;
        }
    }

    public boolean signUp(Integer clientId) throws IOException {
        String username = null;
        String password = null;
        responseLogic.sendResponseAsString("Please create a username: ", clientId);
        username = responseLogic.getRequest(clientId);
        responseLogic.sendResponseAsString("Please create a пароль: ", clientId);
        password = responseLogic.getRequest(clientId);

        if (userActions.registerUser(username, password)) {
            System.out.println("User " + username + " has been registered and logged in!");
            return true;
        }
        else {
            return false;
        }
    }

//                if (answer.equals("2")) {
//                System.out.print("Пожалуйста, придумайте username: ");
//                username = scanner.nextLine();
//                while (userActions.userExists(username)) {
//                    username = scanner.nextLine();
//                }
//                System.out.print("Пожалуйста, придумайте пароль: ");
//                password = scanner.nextLine();
//                while (password.length() < 7) {
//                    password = scanner.nextLine();
//                }
//
//                userActions.registerUser(username, password);
//                User user = userActions.getUser(username, hashingMD2.encodeStringMD2(password));
//                System.out.println(user.getId());
//                System.out.println(user.getUsername());
//            }
}
