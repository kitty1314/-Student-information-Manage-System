package StudentSystem;

import java.util.List;

public class StuService {
    public static List<Student> showAllStudents(){
        return StuDAO.findAll();
    }
    public static boolean checkIdStu(int id){
        boolean result = StuDAO.findStudentById(id)!= null;
        return result;
    }
    public static int getStudentCount(){
        return StuDAO.getStudentCount();
    }
    public static String printStu(int id){
        Student student = StuDAO.findStudentById(id);
        String result = "";
        if(student!=null){
            result = " 姓名："+student.getName()+" 年龄："+student.getAge()+" 学号："+student.getId();
        }
        return result;
    }
    public static void addStudents(String name, int age, int id){
        boolean success = StuDAO.addStudent(name, age, id);
        if (!success) {throw new RuntimeException("添加失败");}
        System.out.println("添加成功");
    }
    public static void deleteStudents(int id){
        boolean success = StuDAO.deleteStudent(id);
        if (!success) {throw new RuntimeException("删除失败");}
        System.out.println("删除成功");
    }
    public static void setNewId(int newId,int oldId){
        if (oldId <= 0 || newId <= 0) {
            throw new IllegalArgumentException("学号必须为正数");
        }
        if (oldId == newId) {throw new IllegalArgumentException("旧学号不能与新学号相同！");}
        if(checkIdStu(newId)){throw new IllegalArgumentException("学号重复，请换一个学号输入！");}
        boolean success = StuDAO.updateId(newId,oldId);
        if (!success) {throw new RuntimeException("更新学号失败");}
        System.out.println("学号更新成功");

    }
    public static void setName(int id,String newName){
        boolean success = StuDAO.updateName(newName,id);
        if (!success) {throw new RuntimeException("更新名字失败");}
        System.out.println("名字更新成功");
    }
    public static void setAge(int id,int newAge){
        boolean success = StuDAO.updateAge(newAge,id);
        if (!success) {throw new RuntimeException("更新年龄失败");}
        System.out.println("年龄更新成功");
    }

}
