package allFxComponents;

import java.util.HashMap;

public class Termin {
    private String data;
    private String grupa;
    private HashMap<Student,Obecność> obecności;

    public Termin(String data, String grupa) {
        this.data = data;
        this.grupa = grupa;
        obecności = new HashMap<>();
    }

    public void zaladaowanieStudentowZGrupy(Student student,Obecność obecność){
        this.obecności.put(student,obecność);
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public HashMap<Student, Obecność> getObecności() {
        return obecności;
    }

    public void setObecności(HashMap<Student, Obecność> obecności) {
        this.obecności = obecności;
    }
}
