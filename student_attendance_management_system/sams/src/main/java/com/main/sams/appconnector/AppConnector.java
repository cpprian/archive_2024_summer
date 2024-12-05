package com.main.sams.appconnector;

import com.google.gson.Gson;
import com.main.sams.server.client.Client;
import com.main.sams.server.server.RequestType;
import com.main.sams.server.server.SocketPackage;
import com.main.sams.student.*;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AppConnector {
    private static AppConnector instance = null;
    private Client client = null;
    private Gson gson = null;

    public AppConnector(String hostname) {
        client = Client.getInstance(hostname);
        gson = new Gson();
    }

    private static AppConnector getInstance(String hostname) {
        if (instance == null) {
            instance = new AppConnector(hostname);
        }
        return instance;
    }

    public static AppConnector run(String hostname) {
        return getInstance(hostname);
    }

    public void stop() {
        client.cleanUpClient();
    }

    public void addStudent(String name, String surname, int index) {
        StudentPackage studentPackage = new StudentPackage(name, surname, index);
        RequestType requestType = RequestType.ADD_STUDENT;
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void deleteStudent(int index) {
        StudentPackage studentPackage = new StudentPackage(null, null, index);
        RequestType requestType = RequestType.DELETE_STUDENT;
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void addGroup(String name, int year) {
        Group group = new Group(name, year);
        RequestType requestType = RequestType.ADD_GROUP;
        String groupJson = gson.toJson(group);
        SocketPackage socketPackage = new SocketPackage(requestType, groupJson,0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void deleteGroup(String name, int year) {
        Group group = new Group(name, year);
        RequestType requestType = RequestType.DELETE_GROUP;
        String groupJson = gson.toJson(group);
        SocketPackage socketPackage = new SocketPackage(requestType, groupJson, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void addStudentToGroup(int index, int groupID) {
        StudentPackage studentPackage = new StudentPackage(null, null, index);
        RequestType requestType = RequestType.ADD_STUDENT_TO_GROUP;
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson, groupID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void deleteStudentFromGroup(int index, int groupID) {
        StudentPackage studentPackage = new StudentPackage(null, null, index);
        RequestType requestType = RequestType.DELETE_STUDENT_FROM_GROUP;
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson, groupID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void addClassTime(String name, int durationInMinutes, String classDate, String startTime, String endTime, String location, String description) {
        ClassTime classTime = new ClassTime(name, durationInMinutes, classDate, startTime, endTime, location, description);
        RequestType requestType = RequestType.ADD_CLASS_TIME;
        String classTimeJson = gson.toJson(classTime);
        SocketPackage socketPackage = new SocketPackage(requestType, classTimeJson, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public void addAttendance(int index, int classTimeID) {
        StudentPackage studentPackage = new StudentPackage(null, null, index);
        RequestType requestType = RequestType.ADD_ATTENDANCE;
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson, classTimeID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        System.out.println(response);
    }

    public StudentPackage[] getStudents() {
        RequestType requestType = RequestType.PRINT_ALL_STUDENTS;
        SocketPackage socketPackage = new SocketPackage(requestType, null,  0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        return gson.fromJson(response.getDataPackage(), StudentPackage[].class);
    }

    public Group[] getGroups() {
        RequestType requestType = RequestType.PRINT_ALL_GROUPS;
        SocketPackage socketPackage = new SocketPackage(requestType, null, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        for (int i = 0; i < 1000; i++) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SocketPackage response = client.receiveResponse();
        System.out.println(response.getDataPackage());
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getGroups() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Group[].class);
    }

    public ClassTime[] getClassTimes() {
        RequestType requestType = RequestType.PRINT_ALL_CLASS_TIMES;
        SocketPackage socketPackage = new SocketPackage(requestType, null, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getClassTimes() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), ClassTime[].class);
    }

    public Attendance[] getAttendances() {
        RequestType requestType = RequestType.PRINT_ALL_ATTENDANCES;
        SocketPackage socketPackage = new SocketPackage(requestType, null, 0, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getAttendances() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Attendance[].class);
    }

    public StudentPackage[] getStudentsInGroup(int groupID) {
        RequestType requestType = RequestType.PRINT_STUDENTS_IN_GROUP;
        SocketPackage socketPackage = new SocketPackage(requestType, null, groupID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getStudentsInGroup() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), StudentPackage[].class);
    }

    public Group[] getGroupsOfStudent(int index) {
        RequestType requestType = RequestType.PRINT_ALL_GROUPS_OF_STUDENT;
        SocketPackage socketPackage = new SocketPackage(requestType, null, index, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getGroupsOfStudent() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Group[].class);
    }

    public Attendance[] getAttendancesOfStudent(int index, int classTimeID) {
        RequestType requestType = RequestType.PRINT_ALL_STUDENT_ATTENDANCES;
        StudentPackage studentPackage = new StudentPackage(null, null, index);
        String studentJson = gson.toJson(studentPackage);
        SocketPackage socketPackage = new SocketPackage(requestType, studentJson,  classTimeID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getAttendancesOfStudent() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Attendance[].class);
    }

    public Attendance[] getClassTimesOfStudent(int index) {
        RequestType requestType = RequestType.PRINT_ALL_CLASS_TIMES_OF_STUDENT;
        SocketPackage socketPackage = new SocketPackage(requestType, null, index, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getClassTimesOfStudent() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Attendance[].class);
    }

    public ClassTime[] getClassTimesOfGroup(int groupID) {
        RequestType requestType = RequestType.PRINT_ALL_CLASS_TIMES_OF_GROUP;
        SocketPackage socketPackage = new SocketPackage(requestType, null, groupID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getClassTimesOfGroup() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), ClassTime[].class);
    }

    public Attendance[] getAttendancesOfGroup(int groupID, int classTimeID) {
        RequestType requestType = RequestType.PRINT_GROUP_ATTENDANCE;
        SocketPackage socketPackage = new SocketPackage(requestType, null, groupID, 0);
        String json = gson.toJson(socketPackage);
        client.sendRequest(json);
        SocketPackage response = client.receiveResponse();
        if (response.getRequestType() == RequestType.SERVER_REJECTED) {
            System.out.println("getAttendancesOfGroup() rejected");
            return null;
        }
        return gson.fromJson(response.getDataPackage(), Attendance[].class);
    }
}
