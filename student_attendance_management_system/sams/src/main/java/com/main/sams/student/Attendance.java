package com.main.sams.student;

import java.io.Serializable;

/**
 * Attendance class is used to store the attendance of each student for each subject.
 *
 * @author Cyprian
 * @version 1.1
 * @since 2023-01-06
 */
public class Attendance implements Serializable {
    /**
     * classTime is an object that store all the information about the class time
     */
    private ClassTime classTime;

    /**
     * attendanceType is an enum that represents the type of attendance
     * @see AttendanceType
     */
    private AttendanceType attendanceType;

    /**
     * student is an object that store all the information about the student
     */
    private StudentPackage studentPackage;

    /**
     * Attendance is a constructor that creates a new Attendance object.
     *
     * @param classTime - class time
     * @param attendanceType - attendance type
     */
    public Attendance(StudentPackage studentPackage, int attendanceType, ClassTime classTime) {
        this.classTime = classTime;
        this.attendanceType = AttendanceType.values()[attendanceType];
        this.studentPackage = studentPackage;
    }

    /**
     * getClassTime is a getter that returns the class time.
     *
     * @return ClassTime object
     */
    public ClassTime getClassTime() {
        return classTime;
    }

    /**
     * getAttendanceType is a getter that returns the attendance type.
     *
     * @return AttendanceType enum
     */
    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    /**
     * setClassTime is a setter that sets the class time.
     *
     * @param classTime - class time
     */
    public void setClassTime(ClassTime classTime) {
        this.classTime = classTime;
    }

    /**
     * setAttendanceType is a setter that sets the attendance type.
     *
     * @param attendanceType - attendance type
     */
    public void setAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

    /**
     * getStudent is a getter that returns the student.
     *
     * @return Student object
     */
    public StudentPackage getStudent() {
        return studentPackage;
    }

    /**
     * setStudent is a setter that sets the student.
     *
     * @param studentPackage - student
     */
    public void setStudent(StudentPackage studentPackage) {
        this.studentPackage = studentPackage;
    }
}
