package allFxComponents;

import java.util.ArrayList;

import static allFxComponents.ObecnośćRodzaj.NIEOBECNY;
import static allFxComponents.ObecnośćRodzaj.OBECNY;

public class DziennikObecności {
    private ArrayList<Termin> terminy;


    private ArrayList<Grupa> grupy;

    public DziennikObecności() {
        this.terminy = new ArrayList<>();
        this.grupy = new ArrayList<>();
    }


    //dodanie grupy wraz ze studentami do konkretnego terminu
    public void dodanieTerminu(String nazwaGrupy,String data){
            int indexGrupy=-1;
            for(Grupa grupa: grupy) {
                if(grupa.getGroupName()==nazwaGrupy) {
                    indexGrupy=grupy.indexOf(grupa);
                }
            }
            if(indexGrupy==-1){
                throw new IndexOutOfBoundsException();
            }
            terminy.add(new Termin(data,nazwaGrupy));
            terminy.get(terminy.size()-1).setGrupa(nazwaGrupy);
            for(Termin termin: terminy) {
                if (termin.getGrupa() == nazwaGrupy) {
                    dodajStudentowDoTerminu(indexGrupy,termin);
                }
            }
    }

    //ile obecności ma dany student

    public int ileObecnosci(int index){
        int result=0;
        for(Termin termin: terminy){
            var entryset = termin.getObecności().entrySet();
            for(var entry:entryset){
                if(entry.getKey().getIndex()==index ){
                    if(entry.getValue().getObecnośćRodzaj().equals(OBECNY)){
                    result+=1;
                }
            }
        }}
        return result;
    }

    //przypisanie obecności każdemu studentowi
    public void zapiszObcnosciDoStudenta(){
        for(Grupa grupa:grupy){
            for(Student student:grupa.getStudents()){
                student.setObecnosc(ileObecnosci(student.getIndex()));
            }
        }
    }

    //dodanie sudentów do terminu
    public void dodajStudentowDoTerminu(int indexGrupy,Termin termin){
        for (Student student : grupy.get(indexGrupy).getStudents()) {
            termin.zaladaowanieStudentowZGrupy(student, new Obecność(NIEOBECNY));
        }
    }

    //dodaj grupę
    public void dodajGrupe(String nazwa){
        if(!grupy.contains(nazwa)) {
            this.grupy.add(new Grupa(nazwa));
        }
    }

    //usuń grupe
    public void usunGrupe(String nazwa){
        int indexDelete=-1;
        for(Grupa grupa: grupy){
            if(grupa.getGroupName().equals(nazwa)){
                indexDelete=grupy.indexOf(grupa);
            }
        }
        if(indexDelete>=0) {
            grupy.remove(indexDelete);
        }
    }

    //gettery i settery
    public ArrayList<Termin> getTermiy() {
        return terminy;
    }

    public void setTermiy(ArrayList<Termin> terminy) {
        this.terminy = terminy;
    }

    public void dodajTermin(String grupa,String data){
        this.terminy.add(new Termin(data,grupa));

    }

    public void usunTermin(String grupa,String data){

    }

    public ArrayList<Termin> getTerminy() {
        return terminy;
    }

    public void setTerminy(ArrayList<Termin> terminy) {
        this.terminy = terminy;
    }

    public ArrayList<Grupa> getGrupy() {
        return grupy;
    }

    public void setGrupy(ArrayList<Grupa> grupy) {
        this.grupy = grupy;
    }

    ///metody zaciagające z bazy danych
}
