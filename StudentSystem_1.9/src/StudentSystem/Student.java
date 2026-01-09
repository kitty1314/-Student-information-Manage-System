package StudentSystem;

public class Student {
    private String name;
    private int id;
    private int age;
    public  Student(String name, int age,int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    };

    public  Student() {
        name = "";
        id = 0;
        age = 0;
    };
    @Override
    public String toString() {
        String outPut = "学生姓名:" + name+" 年龄: " + age+ " 学号:" + id;
        return outPut;
    }

    public void setAge (int age) {
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }


}