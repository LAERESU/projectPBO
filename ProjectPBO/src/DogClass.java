public class DogClass {
    int id;
    String name;
    String condition;
    String skill;
    int baseSpeed;
    String imgPath;

    public DogClass(int id, String name, String condition, String skill, int baseSkill, String imgPath){
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.skill = skill;
        this.baseSpeed = baseSkill;
        this.imgPath = imgPath;
    }

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCondition(){
        return condition;
    }

    public String skill(){
        return skill;
    }

    public int baseSpeed(){
        return baseSpeed;
    }
}
