package vladimir;

import vladimir.human.Human;
import vladimir.human.Moster;
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
                12
        );

        Moster human = new Moster(
                "Человек",
                3080
        );

        Room room = new Room();

        artur.beNervous();
        artur.comeIn(room);
        artur.seeSmb(human);
        human.sit(new Armchair());
        human.move(human.getSecondHead());

        artur.notBelieve();
        artur.beSurprised();
    }
}
