package allFxComponents;

import javafx.scene.control.Button;

public class Student {
    private String name;
    private String surname;
    private Integer index;

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
    }

    private String helper;

    public Integer getObecnosc() {
        return obecnosc;
    }

    public void setObecnosc(Integer obecnosc) {
        this.obecnosc = obecnosc;
    }

    private Integer obecnosc;
    private Button button;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", index=" + index +
                '}';
    }

    public Student(String name, String surname, int index) {
        this.name = name;
        this.surname = surname;
        this.index = index;
        this.obecnosc = 0;
        this.button = new Button("Dodaj obecność");
        this.helper = "Nieobecny";
    }

    //gettery i settery
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
