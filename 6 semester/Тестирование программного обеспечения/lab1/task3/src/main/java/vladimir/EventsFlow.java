package vladimir;

import vladimir.human.Human;
import vladimir.human.body.BodyPart;
import vladimir.furniture.Armchair;
import vladimir.place.Room;

import java.time.LocalDateTime;

public class EventsFlow {

    public EventsFlow() {
        System.out.println(
                "Эта история произошла в " + LocalDateTime.now().getYear() + " году"
        );
    }

    public void story() {
        Human artur = new Human(
                "Артур",
                12,
                new BodyPart("Голова"),
                new BodyPart("Тело"),
                new BodyPart("Левая рука"),
                new BodyPart("Правая рука"),
                new BodyPart("Левая нога"),
                new BodyPart("Правая нога")
        );

        Human human = new Human(
                "Человек",
                3080,
                new BodyPart("Левая голова"),
                new BodyPart("Правая голова"),
                new BodyPart("Тело"),
                new BodyPart("Левая рука"),
                new BodyPart("Правая рука"),
                new BodyPart("Левая нога"),
                new BodyPart("Правая нога")
        );

        Room room = new Room();

        artur.beNervous();
        artur.comeIn(room);
        artur.seeSmb(human);
        human.sit(new Armchair());
        human.move();

        artur.notBelieve();
        artur.beSurprised();
    }
}
