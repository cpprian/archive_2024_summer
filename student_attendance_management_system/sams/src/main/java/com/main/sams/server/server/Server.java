package com.main.sams.server.server;

import com.google.gson.Gson;
import com.main.sams.server.database.DatabaseWorker;
import com.main.sams.student.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * Server class that handles all the requests from the client
 *
 * @author cpprian
 * @version 1.0
 * @since 2023-01-06
 */
public class Server {

    /**
     * instance of the server, used for singleton
     */
    private static Server instance = null;

    /**
     * serverSocket used to listen for incoming connections
     */
    private ServerSocket serverSocket = null;

    /**
     * socket used to communicate with the client
     */
    private Socket clientSocket = null;

    /**
     * logger used to log the server's actions
     */
    private System.Logger logger = null;

    /**
     * PORT is used to set the port that the server will listen on
     */
    private static final int PORT = 8080;

    /**
     * out is used to write to the client
     */
    private BufferedWriter out = null;

    /**
     * in is used to read from the client
     */
    private BufferedReader in = null;

    /**
     * databaseWorker is used to communicate with the database
     */
    private DatabaseWorker databaseWorker = null;

    /**
     * dbHost is used to set the host of the database
     */
    private String dbHost = "";

    /**
     * dbPort is used to set the port of the database
     */
    private String dbPort = "";

    private Gson gson = null;

    /**
     * constructor for the server, sets the logger and databaseWorker and starts the server
     *
     * @param hostname the hostname of the database
     * @param port the port of the database
     */
    private Server(String hostname, String port) {
        try {
            dbHost = hostname;
            dbPort = port;
            gson = new Gson();
            serverSocket = new ServerSocket(PORT);
            logger = System.getLogger("Server");
            logger.log(System.Logger.Level.INFO, "Server started on port " + PORT + " and ip address " + serverSocket.getInetAddress());
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Server constructor: Server failed to start on port " + PORT);
            System.exit(1);
        }
    }

    /**
     * main method for the server, creates a new server instance
     *
     * @param args the arguments passed to the server
     * @throws IOException if the server fails to start
     */
    public static void main(String[] args) throws IOException {
        Server server = Server.getInstance(args[0], args[1]);
        server.runServer();
        server.cleanUpServer();
    }

    /**
     * getInstance method for the server, used to create a new server instance
     *
     * @param hostname the hostname of the database
     * @param port the port of the database
     * @return the server instance
     */
    public static Server getInstance(String hostname, String port) {
        if (instance == null) {
            instance = new Server(hostname, port);
        }
        return instance;
    }

    /**
     * getDbHost is used to get the hostname of the database
     *
     * @return the hostname of the database
     */
    private String getDbHost() {
        return dbHost;
    }

    /**
     * setDbHost is used to set the hostname of the database
     */
    private void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    /**
     * getDbPort is used to get the port of the database
     *
     * @return the port of the database
     */
    private String getDbPort() {
        return dbPort;
    }

    /**
     * setDbPort is used to set the port of the database
     */
    private void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    /**
     * runServer is used to run the server and handle all the requests from the client
     *
     * @throws IOException if the server fails to start
     */
    private void runServer() {
        try {
            databaseWorker = DatabaseWorker.getInstance(getDbHost(), getDbPort());
            // handle one client at a time
            clientSocket = serverSocket.accept();
            logger.log(System.Logger.Level.INFO, "Client connected: " + clientSocket.getInetAddress().getHostAddress());
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

            receivePackage();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Server runServer: Server failed to run");
            cleanUpServer();
            System.exit(1);
        }
    }

    /**
     * cleanUpServer is used to clean up the server
     * it closes the serverSocket, clientSocket, out and in
     *
     * @throws IOException if the server fails to close the connection
     */
    private void cleanUpServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Server cleanUpServer: Server failed to clean up");
            System.exit(1);
        }
    }

    /**
     * receivePackage is used to receive a package from the client
     *
     * @throws IOException if the server fails to receive the package
     */
    private void receivePackage() {
        try {
            while (clientSocket.isConnected()) {
                // read from client
                String received = in.readLine();
                if (received == null) {
                    break;
                }
                logger.log(System.Logger.Level.INFO, "Received: " + received);
                // parse the package
                SocketPackage socketPackage = gson.fromJson(received, SocketPackage.class);
                if (socketPackage == null) {
                    logger.log(System.Logger.Level.INFO, "Received package from client is null. Closing connection.");
                    continue;
                }
                logger.log(System.Logger.Level.INFO, "Received package from client: " + socketPackage);

                // process package
                new Thread(() -> {
                    sendPackage(processPackage(socketPackage));
                }).start();
            }
        } catch (Exception e) {
            if (e.getClass() == EOFException.class) {
                logger.log(System.Logger.Level.INFO, "Client disconnected");
            } else {
                logger.log(System.Logger.Level.ERROR, "Server receivePackage: Server failed to receive package, error message: " + e.getMessage() + " error type: " + e.getClass());
            }

            cleanUpServer();
            System.exit(1);
        }
    }

    /**
     * sendPackage is used to send a package to the client
     *
     * @param socketPackage the package to send to the client
     * @throws IOException if the server fails to send the package
     */
    private void sendPackage(SocketPackage socketPackage) {
        try {
            // write to client
            if (!clientSocket.isConnected()) {
                logger.log(System.Logger.Level.INFO, "Client is not connected. Closing connection.");
                return;
            }
            String json = gson.toJson(socketPackage);
            System.out.println("Sending: " + json);
            out.write(json + "\n");
            out.flush();
            logger.log(System.Logger.Level.INFO, "Sent package to client: " + socketPackage);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Server sendPackage: Server failed to send package");
            cleanUpServer();
            System.exit(1);
        }
    }

    /**
     * processPackage is used to process the package received from the client
     *
     * @param socketPackage the package received from the client
     * @return the package to send to the client
     */
    private SocketPackage processPackage(SocketPackage socketPackage) {
        SocketPackage responsePackage = null;

        switch (socketPackage.getRequestType()) {
            case ADD_STUDENT: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                databaseWorker.addStudent(studentPackage.getName(), studentPackage.getSurname(), studentPackage.getIndex());
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case DELETE_STUDENT: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                databaseWorker.deleteStudent(studentPackage.getIndex());
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case ADD_GROUP: {
                Group group = gson.fromJson(socketPackage.getDataPackage(), Group.class);
                databaseWorker.addGroup(group.getName(), group.getYear());
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case DELETE_GROUP: {
                int groupId = socketPackage.getId1();
                databaseWorker.deleteGroup(groupId);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case ADD_STUDENT_TO_GROUP: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                int groupId = socketPackage.getId1();
                databaseWorker.addStudentToGroup(studentPackage.getIndex(), groupId);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case DELETE_STUDENT_FROM_GROUP: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                int groupId = socketPackage.getId1();
                databaseWorker.deleteStudentFromGroup(studentPackage.getIndex(), groupId);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case ADD_CLASS_TIME: {
                ClassTime classTime = gson.fromJson(socketPackage.getDataPackage(), ClassTime.class);
                databaseWorker.addClassTime(classTime.getName(), classTime.getDurationInMinutes(), classTime.getClassDate(),
                        classTime.getStartTime(), classTime.getEndTime(), classTime.getLocation(), classTime.getDescription());
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, null, 0, 0);
                break;
            }
            case ADD_ATTENDANCE: {
                Attendance attendance = gson.fromJson(socketPackage.getDataPackage(), Attendance.class);
                int classTimeId = socketPackage.getId1();
                databaseWorker.addAttendance(attendance.getStudent().getIndex(), attendance.getAttendanceType(), classTimeId);
                break;
            }
            case PRINT_GROUP_ATTENDANCE: {
                int groupid = socketPackage.getId1();
                int classTimeId = socketPackage.getId2();
                Attendance[] attendances = databaseWorker.getGroupAttendances(groupid, classTimeId).toArray(new Attendance[0]);
                String json = gson.toJson(attendances);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            case PRINT_ALL_STUDENTS: {
                StudentPackage[] studentPackages = databaseWorker.getStudents().toArray(new StudentPackage[0]);
                String json = gson.toJson(studentPackages);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            case PRINT_ALL_GROUPS: {
                Group[] groups = databaseWorker.getGroups().toArray(new Group[0]);
                String json = gson.toJson(groups);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json,0, 0);
                break;
            }
            case PRINT_ALL_CLASS_TIMES: {
                ClassTime[] classTimes = databaseWorker.getClassTimes().toArray(new ClassTime[0]);
                String json = gson.toJson(classTimes);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            case PRINT_ALL_ATTENDANCES: {
                Attendance[] attendances = databaseWorker.getAttendances().toArray(new Attendance[0]);
                String json = gson.toJson(attendances);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json,0, 0);
                break;
            }
            case PRINT_STUDENTS_IN_GROUP: {
                int groupid = socketPackage.getId1();
                StudentPackage[] studentPackages = databaseWorker.getStudentsInGroup(groupid).toArray(new StudentPackage[0]);
                String json = gson.toJson(studentPackages);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            case PRINT_ALL_GROUPS_OF_STUDENT: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                Group[] groups = databaseWorker.getAllGroupsOfStudent(studentPackage.getIndex()).toArray(new Group[0]);
                String json = gson.toJson(groups);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json,0, 0);
                break;
            }
            case PRINT_ALL_STUDENT_ATTENDANCES: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                int classTimeId = socketPackage.getId1();
                Attendance[] attendances = databaseWorker.getStudentAttendances(studentPackage.getIndex(), classTimeId).toArray(new Attendance[0]);
                String json = gson.toJson(attendances);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json,0, 0);
                break;
            }
            case PRINT_ALL_CLASS_TIMES_OF_STUDENT: {
                StudentPackage studentPackage = gson.fromJson(socketPackage.getDataPackage(), StudentPackage.class);
                ClassTime[] classTimes = databaseWorker.getAllClassTimesOfStudent(studentPackage.getIndex()).toArray(new ClassTime[0]);
                String json = gson.toJson(classTimes);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            case PRINT_ALL_CLASS_TIMES_OF_GROUP: {
                int groupId = socketPackage.getId1();
                ClassTime[] classTimes = databaseWorker.getAllClassTimesOfGroup(groupId).toArray(new ClassTime[0]);
                String json = gson.toJson(classTimes);
                responsePackage = new SocketPackage(RequestType.SERVER_ACCEPTED, json, 0, 0);
                break;
            }
            default: {
                logger.log(System.Logger.Level.ERROR, "Server processPackage: Invalid request type");
                responsePackage = new SocketPackage(RequestType.SERVER_REJECTED, null,0, 0);
                break;
            }
        }
        return responsePackage;
    }
}
