package spaceobjects;

public enum IlluminateStatus {
    EMIT_LIGHT("Излучает свет"),
    ABSORB_LIGHT("Поглощает свет"),
    REFLECT_LIGHT("Отражает свет");
    private String title;

    IlluminateStatus(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
