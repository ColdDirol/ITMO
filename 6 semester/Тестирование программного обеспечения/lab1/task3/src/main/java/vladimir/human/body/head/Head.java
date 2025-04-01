package vladimir.human.body.head;

import lombok.Getter;
import vladimir.human.body.BodyPartAbstract;
import vladimir.human.emotion.EmotionLevel;
import vladimir.human.emotion.IEmotions;
import vladimir.utils.Validator;

@Getter
public class Head extends BodyPartAbstract implements IEmotions {

    private Jaw jaw;
    private Eyes eyes;

    public Head(String name, String owner) {
        super(name, owner);

        this.eyes = new Eyes("Глаза", getName(), getOwner());
        this.jaw = new Jaw("Челюсть", getName(), getOwner());
    }

    @Override
    public void move() {
        System.out.println(getOwner() + " шевелит " + getName());
    }

    @Override
    public void smileWithLevel(EmotionLevel lvl) {
        Validator.checkEnum(lvl);
        switch (lvl) {
            case LOW -> System.out.println(getName() + " " + getOwner() + " слабо улыбнулся");
            case MEDIUM -> System.out.println(getName() + " " + getOwner() + " улыбнулся");
            case HIGH -> System.out.println(getName() + " " + getOwner() + " широко улыбнулся");
        }
    }

    @Override
    public void beSurprised(EmotionLevel lvl) {
        Validator.checkEnum(lvl);
        switch (lvl) {
            case LOW -> System.out.println(getOwner() + " немного удивился");
            case MEDIUM -> System.out.println(getOwner() + " удивился");
            case HIGH -> System.out.println(getOwner() + " был ошеломлен");
        }
    }

    @Override
    public void beNervous(EmotionLevel lvl) {
        Validator.checkEnum(lvl);
        switch (lvl) {
            case LOW -> System.out.println(getOwner() + " чуть-чуть нервничал");
            case MEDIUM -> System.out.println(getOwner() + " нервничал");
            case HIGH -> System.out.println(getOwner() + " сильно нервничал");
        }
    }

    @Override
    public void notBelieveInOwnSmb(String what) {
        Validator.checkStrings(what);
        System.out.println(getOwner() + " не верил своему " + what);
    }

}
