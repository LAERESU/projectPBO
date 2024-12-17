public abstract class AbstractHewan {
    String name;
    String condition;

    public AbstractHewan(String name, String condition){
        this.name = name;
        this.condition = condition;
    }

    public abstract String getName();

    public abstract String getDogCondition();
}
