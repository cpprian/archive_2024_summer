package com.main.sams.server.database;

import com.main.sams.student.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseWorker {
    private static DatabaseWorker instance = null;
    private static final String DATABASE_NAME = "sams";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "sams";
    private static final String PASSWORD = "123";

    private String connectionString = "";

    private DatabaseWorker(String hostname, String port) {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            System.out.println("DatabaseWorker constructor: Failed to load driver");
        }

        connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + DATABASE_NAME;
        try(Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
            Statement statement = conn.createStatement()) {
            // create tables
            statement.execute(CreateTableStatement.createClasstimeTable());
            statement.execute(CreateTableStatement.createGroupTable());
            statement.execute(CreateTableStatement.createStudentTable());
            statement.execute(CreateTableStatement.createStudentGroupTable());
            statement.execute(CreateTableStatement.createAttendanceTable());

            System.out.println("Database is OK");
        } catch (SQLException e) {
            System.out.println("DatabaseWorker constructor: Failed to create database, " + e.getMessage());
            System.exit(1);
        }
    }

    public static DatabaseWorker getInstance(String hostname, String port) {
        if (instance == null) {
            instance = new DatabaseWorker(hostname, port);
        }
        return instance;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void addStudent(String name, String surname, int studentNumber) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.addStudent(name, surname, studentNumber));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker addStudent: Failed to add student, " + e.getMessage());
        }
    }

    public void deleteStudent(int studentNumber) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.deleteStudent(studentNumber));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker deleteStudent: Failed to delete student, " + e.getMessage());
        }
    }

    public void addGroup(String groupName, int groupYear) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.addGroup(groupName, groupYear));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker addGroup: Failed to add group, " + e.getMessage());
        }
    }

    public void deleteGroup(int groupID) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.deleteGroup(groupID));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker deleteGroup: Failed to delete group, " + e.getMessage());
        }
    }

    public void addStudentToGroup(int studentNumber, int groupID) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.addStudentToGroup(studentNumber, groupID));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker addStudentToGroup: Failed to add student to group, " + e.getMessage());
        }
    }

    public void deleteStudentFromGroup(int studentNumber, int groupID) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.deleteStudentFromGroup(studentNumber, groupID));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker deleteStudentFromGroup: Failed to delete student from group, " + e.getMessage());
        }
    }

    public void addClassTime(String className, int durationInMinutes, String classTimeDate,
                             String classTimeStartTime, String classTimeEndTime, String location, String description) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.addClassTime(className, durationInMinutes, classTimeDate,
                    classTimeStartTime, classTimeEndTime, location, description));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker addClassTime: Failed to add class time, " + e.getMessage());
        }
    }

    public void addAttendance(int studentNumber, AttendanceType attendanceType, int classTimeID) {
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            statement.execute(StudentDbUtil.addAttendance(studentNumber, attendanceType, classTimeID));
        } catch (SQLException e) {
            System.out.println("DatabaseWorker addAttendance: Failed to add attendance, " + e.getMessage());
        }
    }

    public ArrayList<StudentPackage> getStudents() {
        ArrayList<StudentPackage> studentPackages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllStudents());
            while (resultSet.next()) {
                studentPackages.add(new StudentPackage(resultSet.getString("name"), resultSet.getString("surname"),
                        resultSet.getInt("studentnumber")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getStudents: Failed to get students, " + e.getMessage());
        }
        return studentPackages;
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllGroups());
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getString("groupname"), resultSet.getInt("groupyear")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getGroups: Failed to get groups, " + e.getMessage());
        }
        System.out.println("DatabaseWorker getGroups: " + groups);
        return groups;
    }

    public ArrayList<ClassTime> getClassTimes() {
        ArrayList<ClassTime> classTimes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllClassTimes());
            while (resultSet.next()) {
                classTimes.add(new ClassTime(resultSet.getString("classtimename"), resultSet.getInt("durationinminutes"),
                        resultSet.getString("classtimedate"), resultSet.getString("starttime"),
                        resultSet.getString("endtime"), resultSet.getString("location"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getClassTimes: Failed to get class times, " + e.getMessage());
        }
        return classTimes;
    }

    public ArrayList<Attendance> getAttendances() {
        ArrayList<Attendance> attendances = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllAttendances());
            iterAttendance(attendances, resultSet);
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getAttendances: Failed to get attendances, " + e.getMessage());
        }
        return attendances;
    }

    private void iterAttendance(ArrayList<Attendance> attendances, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            attendances.add(new Attendance(
                    new StudentPackage(resultSet.getString("name"), resultSet.getString("surname"),
                            resultSet.getInt("studentnumber")),
                    resultSet.getInt("attendancetype"),
                    new ClassTime(resultSet.getString("classtimename"), resultSet.getInt("durationinminutes"),
                            resultSet.getString("classtimedate"), resultSet.getString("starttime"),
                            resultSet.getString("endtime"), resultSet.getString("location"),
                            resultSet.getString("description"))
            ));
        }
    }

    public ArrayList<StudentPackage> getStudentsInGroup(int groupID) {
        ArrayList<StudentPackage> studentPackages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllStudentsInGroup(groupID));
            while (resultSet.next()) {
                studentPackages.add(new StudentPackage(resultSet.getString("name"), resultSet.getString("surname"),
                        resultSet.getInt("studentnumber")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getStudentsInGroup: Failed to get students in group, " + e.getMessage());
        }
        return studentPackages;
    }

    public ArrayList<Group> getAllGroupsOfStudent(int studentNumber) {
        ArrayList<Group> groups = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllGroupsOfStudent(studentNumber));
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getString("groupname"), resultSet.getInt("groupyear")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getAllGroupsOfStudent: Failed to get groups of student, " + e.getMessage());
        }
        return groups;
    }

    public ArrayList<Attendance> getStudentAttendances(int studentNumber, int classTimeID) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printStudentAttendance(studentNumber, classTimeID));
            iterAttendance(attendances, resultSet);
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getStudentAttendances: Failed to get student attendances, " + e.getMessage());
        }
        return attendances;
    }

    public ArrayList<ClassTime> getAllClassTimesOfStudent(int studentNumber) {
        ArrayList<ClassTime> classTimes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllClassTimesOfStudent(studentNumber));
            while (resultSet.next()) {
                classTimes.add(new ClassTime(resultSet.getString("classtimename"), resultSet.getInt("durationinminutes"),
                        resultSet.getString("classtimedate"), resultSet.getString("starttime"),
                        resultSet.getString("endtime"), resultSet.getString("location"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getAllClassTimesOfStudent: Failed to get class times of student, " + e.getMessage());
        }
        return classTimes;
    }

    public ArrayList<ClassTime> getAllClassTimesOfGroup(int groupID) {
        ArrayList<ClassTime> classTimes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printAllClassTimesOfGroup(groupID));
            while (resultSet.next()) {
                classTimes.add(new ClassTime(resultSet.getString("classtimename"), resultSet.getInt("durationinminutes"),
                        resultSet.getString("classtimedate"), resultSet.getString("starttime"),
                        resultSet.getString("endtime"), resultSet.getString("location"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getAllClassTimesOfGroup: Failed to get class times of group, " + e.getMessage());
        }
        return classTimes;
    }

    public ArrayList<Attendance> getGroupAttendances(int groupID, int classTimeID) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getConnectionString(), USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(StudentDbUtil.printGroupAttendance(groupID, classTimeID));
            iterAttendance(attendances, resultSet);
        } catch (SQLException e) {
            System.out.println("DatabaseWorker getGroupAttendances: Failed to get group attendances, " + e.getMessage());
        }
        return attendances;
    }
}
