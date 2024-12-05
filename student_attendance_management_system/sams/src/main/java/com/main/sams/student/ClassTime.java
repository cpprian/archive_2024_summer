package com.main.sams.student;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassTime class is used to create a class time.
 *
 * It contains the following attributes:
 * - name - name of the class
 * - durationInMinutes - duration of the class
 * - classDate - date of the class
 * - startTime - start time of the class
 * - endTime - end time of the class
 * - location - location of the class
 * - description - description of the class
 *
 * @author Cyprian
 * @version 1.0
 * @since 2023-01-04
 */
public class ClassTime implements Serializable {
    /**
     * name is a String that represents the name of the class
     */
    private String name;

    /**
     * durationInMinutes is an int that represents the duration of the class (e.g. 60 minutes)
     */
    private int durationInMinutes;

    /**
     * classDate is a Date that represents the date of the class
     */
    private String classDate;

    /**
     * startTime is a String that represents the start time of the class
     */
    private String startTime;

    /**
     * endTime is a String that represents the end time of the class
     */
    private String endTime;

    /**
     * location is a String that represents the location of the class (e.g. room 1)
     */
    private String location;

    /**
     * description is a String that represents the description of the class
     */
    private String description;

    /**
     * ClassTime is a constructor that creates a new ClassTime object.
     *
     * @param name - name of the class
     * @param durationInMinutes - duration of the class
     * @param classDate - date of the class
     * @param startTime - start time of the class
     * @param endTime - end time of the class
     * @param location - location of the class
     * @param description - description of the class
     */
    public ClassTime(String name, int durationInMinutes, String classDate, String startTime, String endTime, String location, String description) {
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.classDate = classDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }

    /**
     * getName is a getter that returns the name of the class
     *
     * @return name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * getDurationInMinutes is a getter that returns the duration of the class
     *
     * @return duration of the class
     */
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    /**
     * getClassDate is a getter that returns the date of the class
     *
     * @return date of the class
     */
    public String getClassDate() {
        return classDate;
    }

    /**
     * getStartTime is a getter that returns the start time of the class
     *
     * @return start time of the class
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * getEndTime is a getter that returns the end time of the class
     *
     * @return end time of the class
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * getLocation is a getter that returns the location of the class
     *
     * @return location of the class
     */
    public String getLocation() {
        return location;
    }

    /**
     * getDescription is a getter that returns the description of the class
     *
     * @return description of the class
     */
    public String getDescription() {
        return description;
    }

    /**
     * setName is a setter that sets the name of the class
     *
     * @param name - name of the class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setDurationInMinutes is a setter that sets the duration of the class
     *
     * @param durationInMinutes - duration of the class
     */
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    /**
     * setClassDate is a setter that sets the date of the class
     *
     * @param classDate - date of the class
     */
    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    /**
     * setStartTime is a setter that sets the start time of the class
     *
     * @param startTime - start time of the class
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * setEndTime is a setter that sets the end time of the class
     *
     * @param endTime - end time of the class
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * setLocation is a setter that sets the location of the class
     *
     * @param location - location of the class
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * setDescription is a setter that sets the description of the class
     *
     * @param description - description of the class
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * changeClassDuration is a method that changes the duration of the class
     * It also changes the end time of the class.
     *
     * @param durationInMinutes - duration of the class
     */
    public void changeClassDuration(int durationInMinutes) {
        if (durationInMinutes <= 0) {
            System.out.println("ClassTime changeClassDuration: Duration must be greater than 0");
            return;
        }
        setDurationInMinutes(durationInMinutes);

        String newEndDate = calculateEndDate(getDurationInMinutes());
        setEndTime(newEndDate);
    }

    /**
     * calculateEndDate is a method that calculates the end time of the class
     *
     * @param durationInMinutes - duration of the class
     * @return end time of the class
     */
    private String calculateEndDate(int durationInMinutes) {
        String localStartTime = getStartTime();

        // convert string to date
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date parsedDate = dateFormat.parse(localStartTime);
            System.out.println(parsedDate);
            Date newDate = new java.util.Date(parsedDate.getTime() + durationInMinutes * 60000);
            localStartTime = dateFormat.format(newDate);
            System.out.println(localStartTime);
        } catch (Exception e) {
            System.out.println("ClassTime calculateEndDate: Error in calculating end date");
        }

        return localStartTime;
    }
}
