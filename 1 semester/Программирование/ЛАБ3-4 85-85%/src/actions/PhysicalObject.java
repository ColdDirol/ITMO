package actions;

import Exceptions.IllegalNameException;

import java.util.Objects;

public abstract class PhysicalObject {
    private String name;

    public PhysicalObject(String name) throws IllegalNameException{
        checkName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalNameException {
        checkName(name);
        this.name = name;
    }

    public static void checkName(String name) throws IllegalNameException {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && !Character.isWhitespace(name.charAt(i))) {
                throw new IllegalNameException("Bad symbol in the name field!: " + name.charAt(i));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhysicalObject that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "* Класс PhysicalObject содержит поля: name";
    }
}
