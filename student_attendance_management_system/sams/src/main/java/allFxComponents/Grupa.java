package allFxComponents;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class Grupa {
    private String groupName;
    private ArrayList<Student> students;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    private Button button;


    public Grupa(String groupName) {
        this.groupName = groupName;
        students = new ArrayList<>();
        this.button = new Button("Dodaj obecność");
    }

    //dodanie studenta
    public void addStudent(String name, String surname, int index){
        if(!students.contains(new Student(name,surname,index))) {
            students.add(new Student(name, surname, index));
        }
    }

    //usunięcie studenta
    public void deleteStudent(int index){
        int indexDelete=-1;
        for(Student student: students){
            if(student.getIndex()==index){
                indexDelete=students.indexOf(student);
            }
        }
        if(indexDelete>=0) {
            students.remove(indexDelete);
        }
    }


    //gettery i settery
    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
