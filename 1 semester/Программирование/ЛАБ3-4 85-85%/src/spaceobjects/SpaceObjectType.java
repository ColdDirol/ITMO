package spaceobjects;

public enum SpaceObjectType {
    STAR("Звезда"),
    PLANET("Планета"),
    SATALLITE("Спутник"),
    ASTEROID("Астеройд"),
    COMET("Комета");
    private String title;

    SpaceObjectType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
