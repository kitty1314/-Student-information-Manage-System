package StudentSystem;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class StudentApp {
    Scanner s = new Scanner(System.in);

    public void start(){
    while(true){
        showMenu();
        try {
            int sc = s.nextInt();
            s.nextLine();

            if (sc == 5) {
                System.out.println("正在退出系统...");
                break;
            }
            StuChoice(sc);

        } catch (InputMismatchException e) {
            System.out.println("请输入数字！");
            s.nextLine();
        }
    }
        System.out.println("感谢使用，再见！");
        s.close();
    }

    private void showMenu(){
        System.out.println("欢迎来到学生系统");
        System.out.println("0-查看目前所有学生信息");
        System.out.println("1-查找学生信息");
        System.out.println("2-添加学生信息");
        System.out.println("3-删除学生信息");
        System.out.println("4-修改学生信息");
        System.out.println("5-退出");
    }

    private void StuChoice(int sc){
        switch(sc){
            case 0 -> show();
            case 1 -> findStudent();
            case 2 -> addStudent();
            case 3 -> deleteStudent();
            case 4 -> changeStudent();
            case 5 -> {}
            default -> System.out.println("请输入正确的选项");
        }
    }

    private void show(){
        List<Student> stu = StuService.showAllStudents();
        for(int a = 0; a < StuDAO.getStudentCount(); a++){
            System.out.println(stu.get(a));
        }
    }

    private void changeStudent(){
        int studentAge;
        String studentName;
        int studentId;
        Scanner scc = new Scanner(System.in);
        if(isEmpty()){
            handleEmpty();
            return;
        }
        try{
            System.out.println("请输入需要修改的学生学号");
            int targetId = scc.nextInt();
            scc.nextLine();
            if(!StuService.checkIdStu(targetId)){
                System.out.println("该学号不存在！");
                return;
            }

            System.out.println("请输入需要修改的内容，1-修改学号，2-修改名字，3-修改年龄，0-取消修改");

            String cho = scc.next();
            scc.nextLine();

            switch (cho) {

                case "1" -> {
                    System.out.println("请输入新学号");
                    studentId =getValidId(scc);
                    StuService.setNewId(targetId,studentId);
                    System.out.println("学号修改成功，目前该生学号为" + studentId);
                }
                case "2" -> {
                    System.out.println("请输入新名字");
                    studentName = scc.nextLine();
                    StuService.setName(targetId,studentName);
                    System.out.println("名字修改成功，目前该生名字为" + studentName);
                }
                case "3" -> {
                    System.out.println("请输入新年龄");
                    studentAge =getValidAge(scc);
                    StuService.setAge(targetId,studentAge);
                    System.out.println("年龄修改成功，目前该生年龄为" +studentAge );
                }
                case "0" -> {
                    return;
                }
            }
            return;

        }
        catch (InputMismatchException e) {
            System.out.println("学号格式错误：请输入数字");
        }

    }

    private void addStudent() {                             //添加学生
        Scanner s = new Scanner(System.in);
        int studentAge;
        String studentName;
        int studentId;
        try {                                               //验证+调用
            System.out.println("请输入学生姓名");
            studentName = s.nextLine();
            studentAge =getValidAge(s);
            studentId = getValidId(s);
            StuService.addStudents(studentName, studentAge, studentId);
        }
        catch (Exception e) {                                 // 抛出异常
            System.out.println("添加学生失败: " + e.getMessage());
        }
    }

    private void deleteStudent(){                               //删除学生
        Scanner id = new Scanner(System.in);
        try{
            System.out.println("请输入需要删除的学生学号");
            int studentId = id.nextInt();
            while (!checkId(studentId)){                        //检查id是否存在
                System.out.println("无对应学生，请尝试输入其他id,或输入0取消创建");
                studentId = id.nextInt();
                if(studentId==0){
                    return;
                }
            }
            StuService.deleteStudents(studentId);
        }
        catch (InputMismatchException e){
            System.out.println("id错误：请输入数字！");
            id.nextLine();
        }
    }

    private void findStudent(){
        Scanner scc = new Scanner(System.in);
        if(isEmpty()){
            handleEmpty();
            return;
        }

        try {
            System.out.println("请输入需要查找的学生学号，输入0取消查找");
            int targetId = scc.nextInt();
            scc.nextLine();

            while (!checkId(targetId)) {                        //检查id是否存在
                System.out.println("无对应学生，请尝试输入其他id,或输入0取消创建");
                targetId = scc.nextInt();
                scc.nextLine();
                if (targetId == 0) {
                    System.out.println("取消查找，返回中...");
                    return;
                }
            }
            String result = StuService.printStu(targetId);
            System.out.println("查找到该学生："+result);
        }

            catch (InputMismatchException e) {
                System.out.println("学号格式错误：请输入数字");
                scc.nextLine();
            }
        }

    private boolean isEmpty(){
        return StuService.getStudentCount() == 0;
    }

    private void handleEmpty() {                             //若列表里没有任何学生，询问是否添加
        while (true) {
            System.out.println("系统里面没有学生信息，是否先添加学生信息？y-跳转添加页面，n-返回初始界面");
            Scanner s = new Scanner(System.in);
            String choice = s.next();
            switch (choice) {
                case "y" -> {
                    addStudent();
                    return;
                }
                case "n" -> {
                    System.out.println("正在返回中...");
                    return;
                }
                default -> System.out.println("请输入正确的选项");
            }
        }
    }

    private boolean checkId(int id){
        boolean result = StuService.checkIdStu(id);
        return result ;
    }

    private int getValidAge(Scanner s){                            //检查年龄输入是否正确
        while (true) {
            int studentAge;
            try {
                System.out.println("请输入学生年龄");
                studentAge = s.nextInt();
                s.nextLine();
                if(studentAge < 0 || studentAge > 100){             //检查区间
                    System.out.println("年龄需要在0-100之间");
                    continue;                                       //若区间不对，打回
                }
                return studentAge;
            } catch (InputMismatchException e) {
                System.out.println("年龄格式错误：请输入数字！");
                s.nextLine();
            }
        }
    }

    private int getValidId(Scanner s){                            //检查id输入是否正确
        int studentID;
        while (true) {
            try {
                System.out.println("请输入学生学号,或输入0退出。");
                studentID = s.nextInt();
                s.nextLine();
                if (studentID == 0) {
                    return -1;
                }
                if (checkId(studentID)) {                        //检查id是否重复
                    System.out.println("学号重复，请尝试输入其他学号");
                    continue;
                }
                return studentID;
            } catch (InputMismatchException e) {
                System.out.println("学号格式错误：请输入数字！");
                s.nextLine();
            }
        }
    }
}
