public class DogClass {
    int id;
    String name;
    String condition;
    String skill;
    int baseSpeed;
    String imgPath;

    public DogClass(int id, String name, String condition, String skill, int baseSpeed, String imgPath) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.skill = skill;
        this.baseSpeed = baseSpeed;
        this.imgPath = imgPath;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
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

    public double getEffectiveSpeed(Arena arena) {
        double effectiveSpeed = this.baseSpeed;
        if (this.skill.equalsIgnoreCase(arena.getName())) {
            effectiveSpeed += 10; // Misalnya, menambah kecepatan sebesar 10 jika cocok
        }
        effectiveSpeed *= (1 - arena.getReduce()); // Mengurangi kecepatan berdasarkan arena
        return effectiveSpeed;
    }
}
