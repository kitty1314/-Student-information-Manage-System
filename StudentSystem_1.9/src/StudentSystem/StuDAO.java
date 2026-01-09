package StudentSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StuDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Kittyneko95";


    static {
        try {
            // MySQL 8.0+
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC Driver.");
            e.printStackTrace();
        }
    }


    public static boolean addStudent(String name, int age, int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO student(name, age, id) VALUES(?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            // 通用异常处理
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // 手动关闭资源
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteStudent(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM student WHERE id = ?")) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        }
        catch (SQLException e) {
        e.printStackTrace();
        return false;}
    }

    public static boolean updateAge(int age, int id) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("UPDATE student SET age = ? WHERE id = ?")) {

            ps.setInt(1, age);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;}
    }

    public static boolean updateId(int newId, int id) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("UPDATE student SET id = ? WHERE id = ?")) {

            ps.setInt(1, newId);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;}
    }

    public static boolean updateName(String name, int id) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("UPDATE student SET name = ? WHERE id = ?")) {

            ps.setString(1, name);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;}
    }

    public static Student findStudentById(int id) {
        String sql = "SELECT id, name, age FROM student WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("id")


                );
            }
            return null; // 没找到

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Student> findAll() {
        String sql = "SELECT id, name, age FROM student ORDER BY id";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("id")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;

    }

    public static int getStudentCount(){
        String sql = "SELECT COUNT(*) as count FROM student";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;



    }
}
//git config --global user.name "kitty1314" ssh-keygen -t rsa -b 4096 -C "3022941136@qq.com"
//git config --global user.email "3022941136@qq.com" ssh-keygen -t rsa -C "3022941136@qq.com"
//open ~/IdeaProjects/StudentSystem

