package com.main.sams.student;

import java.io.Serializable;
/**
 * Group class is used to create a group of Student objects.
 *
 * It contains group name, group year.
 *
 * @author Cyprian
 * @version 1.1
 * @since 2023-01-06
 */
public class Group implements Serializable {
    /**
     * name is a String that represents the group's name
     */
    private String name;

    /**
     * year is an int that represents the group's year
     */
    private int year;

    /**
     * This is a constructor that creates a Group object with the given name and year.
     *
     * @param name is a String that represents the group's name
     * @param year is an int that represents the group's year
     */
    public Group(String name, int year) {
        this.name = name;
        this.year = year;
    }

    /**
     * getName method returns the name of the group
     *
     * @return a String that represents the group's name
     */
    public String getName() {
        return name;
    }

    /**
     * getYear method returns the year of the group
     *
     * @return an int that represents the group's year
     */
    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Group [name=" + name + ", year=" + year + "]";
    }
}
