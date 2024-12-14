public class Obstacle {
    private String name;
    private double reduce; 
    private int length;
    private String imgPath;

    public Obstacle(String name, double reduce, int length, String imgPath) {
        this.name = name;
        this.reduce = reduce;
        this.length = length;
        this.imgPath = imgPath;
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

    public String getImgPath(){
        return imgPath;
    }
}
