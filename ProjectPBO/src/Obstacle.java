public class Obstacle {
    private String name;
    private double reduce; 
    private int length;

    public Obstacle(String name, double reduce, int length) {
        this.name = name;
        this.reduce = reduce;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public double getReduce() {
        return reduce;
    }

    public int getLength() {
        return length;
    }
}
