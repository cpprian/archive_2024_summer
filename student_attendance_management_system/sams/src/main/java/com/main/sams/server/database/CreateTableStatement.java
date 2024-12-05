package com.main.sams.server.database;

public class CreateTableStatement {
    public static String createGroupTable() {
        return """
CREATE TABLE IF NOT EXISTS sgroup (
    groupid     INT NOT NULL AUTO_INCREMENT,
    groupname   VARCHAR(50) NOT NULL UNIQUE,
    groupyear   INT NOT NULL,
    PRIMARY KEY(groupid)
);
                """;
    }

    public static String createStudentTable() {
        return """
CREATE TABLE IF NOT EXISTS studentPackage (
    studentid       INT NOT NULL AUTO_INCREMENT,
    studentname     VARCHAR(25) NOT NULL,
    studentsurname  VARCHAR(25) NOT NULL,
    studentnumber   INT NOT NULL UNIQUE,
    PRIMARY KEY (studentid)
);
                """;
    }

    public static String createStudentGroupTable() {
        return """
CREATE TABLE IF NOT EXISTS studentgroup (
    studentgroupid  INT NOT NULL AUTO_INCREMENT,
    studentid       INT NOT NULL,
    groupid         INT NOT NULL,
    PRIMARY KEY (studentgroupid),
    FOREIGN KEY (studentid) REFERENCES studentPackage(studentid) ON DELETE CASCADE,
    FOREIGN KEY (groupid) REFERENCES sgroup(groupid) ON DELETE CASCADE
);
                """;
    }

    public static String createAttendanceTable() {
        return """
CREATE TABLE IF NOT EXISTS attendance (
    attendanceid    INT NOT NULL AUTO_INCREMENT,
    studentid       INT NOT NULL,
    attendancetype  INT NOT NULL,
    classtimeid     INT NOT NULL,
    PRIMARY KEY (attendanceid),
    FOREIGN KEY (studentid) REFERENCES studentPackage(studentid) ON DELETE CASCADE,
    FOREIGN KEY (classtimeid) REFERENCES classtime(classtimeid) ON DELETE CASCADE
);
                """;
    }

    public static String createClasstimeTable() {
        return """
CREATE TABLE IF NOT EXISTS classtime (
    classtimeid         INT NOT NULL AUTO_INCREMENT,
    classtimename       VARCHAR(255) NOT NULL,
    durationinminutes   INT NOT NULL,
    classtimedate       VARCHAR(50) NOT NULL,
    starttime           VARCHAR(25) NOT NULL,
    endtime             VARCHAR(25) NOT NULL,
    location            VARCHAR(255) NOT NULL,
    description         VARCHAR(255) NOT NULL,
    PRIMARY KEY (classtimeid)
);
                """;
    }
}
