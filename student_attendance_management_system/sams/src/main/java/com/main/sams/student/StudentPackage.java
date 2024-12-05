package com.main.sams.student;

import java.io.Serializable;

/**
 * Student is a class that represents a student.
 *
 * It contains the following fields:
 * name - student's name
 * surname - student's surname
 * index - student's index
 *
 * @author Cyprian
 * @version 1.1
 * @since 2023-01-06
 */
public class StudentPackage implements Serializable {
    /**
     * name is a String that represents the student's name
     */
    private String name;

    /**
     * surname is a String that represents the student's surname
     */
    private String surname;

    /**
     * index is an int that represents the student's index
     */
    private int index;

    /**
     * Student is a constructor that creates a new Student object.
     *
     * @param name - student's name
     * @param surname - student's surname
     * @param index - student's index
     */
    public StudentPackage(String name, String surname, int index) {
        this.name = name;
        this.surname = surname;
        this.index = index;
    }

    /**
     * getName is a getter that returns the student's name.
     *
     * @return student's name
     */
    public String getName() {
        return name;
    }

    /**
     * getSurname is a getter that returns the student's surname.
     *
     * @return student's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * getIndex is a getter that returns the student's index.
     *
     * @return student's index
     */
    public int getIndex() {
        return index;
    }


    /**
     * setName is a setter that sets the student's name.
     *
     * @param name - student's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setSurname is a setter that sets the student's surname.
     *
     * @param surname - student's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * setIndex is a setter that sets the student's index.
     *
     * @param index - student's index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
