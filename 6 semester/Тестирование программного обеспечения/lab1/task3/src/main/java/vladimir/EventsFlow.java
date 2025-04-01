package vladimir;

import vladimir.furniture.Controller;
import vladimir.human.Human;
import vladimir.human.Monster;
import vladimir.furniture.Armchair;
import vladimir.human.emotion.EmotionLevel;
import vladimir.place.Room;

import java.time.LocalDateTime;

public class EventsFlow {

    public EventsFlow() {
        System.out.println();
        System.out.println(
                "Эта история произошла в " + LocalDateTime.now().getYear() + " году"
        );
    }

    public void story() {
        System.out.println();
        Human artur = new Human(
                "Артур",
                12
        );

        Monster human = new Monster(
                "Человек",
                3080
        );

        Room room = new Room();
        Armchair armchair = new Armchair("Кресло");
        Controller controller = new Controller("Пульт управления");

        System.out.println();

        artur.getHead().beNervous(EmotionLevel.MEDIUM);
        artur.comeIn(room);
        artur.getHead().beSurprised(EmotionLevel.HIGH);
        artur.getHead().getEyes().seeSmb(human.getName());
        human.getLegs().getAss().sitTo(armchair.getName());
        human.getLegs().putOn(controller.getName());
        human.getHead().smileWithLevel(EmotionLevel.HIGH);
        human.getSecondHead().getJaw().getTeeth().touchWithHands();
        human.move(human.getSecondHead());

        artur.getHead().notBelieveInOwnSmb(artur.getHead().getEyes().getName());
        artur.getHead().getJaw().dropping();
    }
}
