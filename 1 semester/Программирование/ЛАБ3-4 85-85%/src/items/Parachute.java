package items;

import Exceptions.IllegalNameException;
import actions.PhysicalObject;

public class Parachute extends PhysicalObject {

    private static int parachutesLeft = 1;
    public Parachute(String name) throws IllegalNameException {
        super(name);
    }

    // Вложенный нестатический класс
    private class Ring {
        public void openParachute(){
            System.out.println("Кольцо дернули.");
            System.out.println("Парашют раскрылся.");
        }
    }

    // Вложенный статический класс
    public static class Stat {
        public void remainderOfParachutes(){
            parachutesLeft -= 1;
            System.out.println("Осталось парашютов " + parachutesLeft + ".");
        }
    }
    public void open() {
        Ring ring = new Ring();
        ring.openParachute();

        // Вложенный в метод класс
        final String color = "Зеленый";
        class Appearance{
            public void apperance(){
                System.out.println("Парашют " + color);
            }
        }
        Appearance appearance = new Appearance();
        appearance.apperance();
    }

    public void close(){
        Stat stat = new Stat();

        System.out.println("Парашют автоматически сложился.");
        stat.remainderOfParachutes();
    }

    public void toNullPointerException(){
        System.out.println("Это проверка RT исключения, не обращайте внимания!");
    }


    public void slowDown(){
        System.out.println(this.getName() + " замедлил падение.");
    }
}
