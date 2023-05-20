import Exceptions.IllegalNameException;
import Exceptions.NullException;
import annotations.Test;
import atmosphere.weather.Weather;
import characters.shorties.*;
import items.Parachute;
import plants.*;
import spaceobjects.SpaceObject;
import spaceobjects.satellites.Moon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static plants.FruitType.LUNAR_RESPBERRY;
import static plants.FruitType.EARTHLY_RESPBERRY;

public class Main {
    public static void main(String[] args) throws NullException, IllegalNameException, FileNotFoundException, IllegalAccessException, InstantiationException {
        //коротышки и лунатышки
        System.out.println("Check compiler message");

        //Shorties neznayka = new Neznayka("Д-503");
        Shorties neznayka = new Neznayka("Незнайка");
        Shorties znayka = new Znayka("Знайка");
        Shorties zvezdochka = new Zvezdochka("Звездочка");
        Shorties spruts = new Spruts("Спрутс");
        Parachute parachute = new Parachute("Парашют");

        //Parachute.Stat stat = new Parachute.Stat();

        //растения
        Plant bush = new Bush("Куст");
        Plant appleTree = new AppleTree("Яблоня");
        Plant appleTree2 = new AppleTree("Яблоня");
        Plant pearTree = new PearTree("Груша");

        // Анонимный класс
        Weather wind = new Weather("Ветер") {
            @Override
            public void wind() {
                System.out.println("Ветер дует на " + this.getDirection());
            }
        };
        SpaceObject moon = new Moon("Луна");


        neznayka.fear();
        parachute.open();
        parachute.slowDown();
        neznayka.landing();
        parachute.close();
        neznayka.postFlyingEffect();

        enter();

        neznayka.lookAround();
        bush.getLeavesType();
        wind.wind();
        wind.toString();

        neznayka.putOffClothes(neznayka.getClothes());
        neznayka.canBreathe();
        moon.checkOxygene();

        enter();
        enter();

        neznayka.breathe();
        neznayka.relax();
        neznayka.fun();
        neznayka.easy();
        neznayka.hideItem(neznayka.getClothes());
        neznayka.lookAround();

        enter();

        if (((Plant) appleTree).hashCode() == ((Plant) bush).hashCode() && ((Plant) appleTree).equals(bush)) {
            System.out.println(appleTree.getName() + " то же самое, что и " + bush.getName() + ".");
        } else {
            System.out.println(appleTree.getName() + " не " + bush.getName() + ".");
        }

        appleTree.height();
        appleTree.fruitsCheck();
        appleTree.fruitSize();

        enter();

        neznayka.tasteFruit(appleTree.getFruitType().toString());
        appleTree.fruitFlavor();
        neznayka.sour();
        pearTree.fruitsCheck();
        neznayka.tasteFruit(pearTree.getFruitType().toString());
        pearTree.fruitFlavor();

        if (appleTree.getFruitType() != pearTree.getFruitType()) {
            System.out.println(appleTree.getFruitType() + " не " + pearTree.getFruitType() + ".");
        } else {
            System.out.println(appleTree.getFruitType() + " это тоже " + pearTree.getFruitType() + ".");
        }

        enter();
        enter();

        neznayka.findingEat();
        neznayka.hungry();
        neznayka.tasteFruit(bush.getFruitType().toString());

        if (LUNAR_RESPBERRY.toString() == EARTHLY_RESPBERRY.toString()) {
            System.out.println(LUNAR_RESPBERRY.toString() + " не " + EARTHLY_RESPBERRY.toString());
        } else {
            System.out.println(LUNAR_RESPBERRY.toString() + " это " + EARTHLY_RESPBERRY.toString());
        }

        neznayka.cantGetEnough();
        System.out.println("***");

        //Checked Exception (Compile time exception) - Исключения во время компиляции
        //Unchecked Exception (Runtime exception) - Исключения во время выполнения программы


        int x=4,y=0;
        int currentLine = new Throwable().getStackTrace()[0].getLineNumber() - 1;

//        try{
//            System.out.println(x/y);
//        }
//        catch (ArithmeticException e){
//            System.err.println("Одно из полей равно нулю. Обрати внимание на строку: " + currentLine);
//        }

        ReflectionChecker reflectionChecker = new ReflectionChecker();
        Test test = new Test();

        System.out.println(test.getMyName());
        System.out.println(test.getSecondName());
        reflectionChecker.changePrivateFields(test);

        System.out.println(test.getMyName());
        System.out.println(test.getSecondName());

        Object testClone = reflectionChecker.createNewObject(test);
        reflectionChecker.showClassFields(testClone);

        if(y == 0){
            throw new ArithmeticException("Одно из полей равно нулю. Обрати внимание на строку: " + currentLine);
            //System.err.println("Одно из полей равно нулю. Обрати внимание на строку: " + currentLine);
        }

        /*
        Parachute mistakeParachute;
        try {
            mistakeParachute.toNullPointerException();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
         */

        System.out.println("Check compiler message");

        //open();
    }

    private static void enter(){
        System.out.println();
    }

    private static void open() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("CreaturesCharacteristics.txt");
    }
}