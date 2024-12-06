public class DogClass {
    int id;
    String name;
    String condition;
    String skill;
    int baseSpeed;
    String imgPath;
    private int price;

    public DogClass(int id, String name, String condition, String skill, int baseSpeed, String imgPath, int price) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.skill = skill;
        this.baseSpeed = baseSpeed;
        this.imgPath = imgPath;
        this.price = price;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDogCondition() {
        return condition;
    }

    public String getSkill() {
        return skill;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getPrice() {
        return price;
    }

    public double getEffectiveSpeed(Arena arena) {
        double effectiveSpeed = this.baseSpeed;
        if (this.skill.equalsIgnoreCase(arena.getName())) {
            effectiveSpeed += 10; // Misalnya, menambah kecepatan sebesar 10 jika cocok
        }
        effectiveSpeed *= (1 - arena.getReduce()); // Mengurangi kecepatan berdasarkan arena
        return effectiveSpeed;
    }
}
