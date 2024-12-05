package allFxComponents;

import com.main.sams.student.ClassTime;
import com.main.sams.student.Group;
import com.main.sams.student.StudentPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import com.main.sams.appconnector.AppConnector;

import java.net.URL;
import java.util.ResourceBundle;

import static allFxComponents.ObecnośćRodzaj.NIEOBECNY;
import static allFxComponents.ObecnośćRodzaj.OBECNY;

public class MainController implements Initializable {
    private DziennikObecności dziennikObecności;

    private AppConnector connector;

    private String lastData;

    @FXML
    Button userprofilebutton;

    @FXML
    TextField grupaNazwa;

    @FXML
    TextField textData;

    @FXML
    TextField textGrupa;


    @FXML
    private TableView<Termin> tab1Data;

    @FXML
    private TableColumn<Termin,String> data1;
    private ObservableList<Termin> dataData;

    @FXML
    private TableView<Grupa> grupa1;

    @FXML
    private TableColumn<Termin,String> grupa3;
    private ObservableList<Termin> dataGroups4;

    @FXML
    private TableView<Termin> tab2Grupy;

    @FXML
    private TableColumn<Grupa,String> grupa2;
    private ObservableList<Grupa> dataGroups2;

    @FXML
    TextField ImieS;
    @FXML
    TextField NazwiskoS;
    @FXML
    TextField NIS;

    @FXML
    private Stage stage;

    private TabPane tabPane;

    //kolumny grup w oknie grupy
    @FXML
    private TableView<Grupa> tableGroup;

    @FXML
    private TableColumn<Grupa, String> TabGStudentsGrupa;
    @FXML
    private TableColumn<Grupa, String> tabCStudentschoice;
    private ObservableList<Grupa> dataGroups;


    //kolumny studentów w oknie grupy
    @FXML
    private TableView<Student> tabVStudents;
    @FXML
    private TableColumn<Student, String> tabCStudentsName;
    @FXML
    private TableColumn<Student, String> tabCStudentsSurname;
    @FXML
    private TableColumn<Student, Integer> tabCStudentsIndex;
    private ObservableList<Student> dataStudents;

    @FXML
    private TableView<Student> tab3Studenci;
    @FXML
    private TableColumn<Student, String> T1;
    @FXML
    private TableColumn<Student, String> T2;
    @FXML
    private TableColumn<Student, Integer> T3;

    @FXML
    private TableColumn<Student,String> T4;

    private ObservableList<Student> dataStudents3;

    //kolumny dla studentów w oknie studenci
    @FXML
    private TableView<Student> SStud;

    @FXML
    private TableColumn<Student, String> SImie;

    @FXML
    private TableColumn<Student, String> SNazwisko;

    @FXML
    private TableColumn<Student, String> SNI;

    @FXML
    private TableColumn<Student, Integer> Silo;
    private ObservableList<Student> dataStudents2;

    public void displayName(String username) {
        userprofilebutton.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.dziennikObecności = new DziennikObecności();

        this.connector = AppConnector.run("172.16.213.132");
        dziennikObecności.dodajGrupe("IO1");
        System.out.println(dziennikObecności.getGrupy().get(0).getGroupName());
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 423123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 124123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 893123);

        for (Student student : dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).getStudents()) {
            System.out.println(student.getName());
            System.out.println(student.getSurname());
            System.out.println(student.getIndex());
        }
        System.out.println("\n\n");
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).deleteStudent(893123);


        for (Student student : dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).getStudents()) {
            System.out.println(student.getName());
            System.out.println(student.getSurname());
            System.out.println(student.getIndex());
        }

        dziennikObecności.usunGrupe("IO1");
        System.out.println(dziennikObecności.getGrupy().size());

        dziennikObecności.dodajGrupe("IO1");
        System.out.println(dziennikObecności.getGrupy().get(0).getGroupName());
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 423123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 124123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Janek", "Zacier", 893123);

        dziennikObecności.dodajGrupe("IO2");
        System.out.println(dziennikObecności.getGrupy().get(1).getGroupName());
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 423123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 124123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("Zanek", "Zacier", 893123);

        dziennikObecności.dodajGrupe("IO3");
        System.out.println(dziennikObecności.getGrupy().get(2).getGroupName());
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 423123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 124123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 123123);
        dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().size() - 1).addStudent("panek", "Zacier", 893123);

        dziennikObecności.dodanieTerminu("IO1", "19-12-2222");
        dziennikObecności.dodanieTerminu("IO2", "10-12-2222");
        dziennikObecności.dodanieTerminu("IO3", "13-12-2222");

        System.out.println(dziennikObecności.getTerminy().get(0).getObecności());
        System.out.println(dziennikObecności.getTerminy().get(1).getObecności());
        System.out.println(dziennikObecności.getTerminy().get(2).getObecności());

        dziennikObecności.zapiszObcnosciDoStudenta();

        connector.addStudent("Janek", "Zacier", 423123);
        connector.addStudent("Kanej", "Cierza", 123123);
        connector.addStudent("Maciej", "Laciej", 143124);

        connector.addGroup("IO1", 2023);
        connector.addGroup("IO2", 2023);
        connector.addGroup("IO3", 2023);

        connector.addStudentToGroup(423123, 1);
        connector.addStudentToGroup(423123, 2);
        connector.addStudentToGroup(423123, 3);
        connector.addStudentToGroup(123123, 2);
        connector.addStudentToGroup(143124, 3);

        System.out.println("BBBBBBBBB\n");
        System.out.println(connector.getStudents());
        System.out.println(connector.getGroups());
        System.out.println("AAAAAAAAA\n");
        for(Group group: connector.getGroups())
        {
            dziennikObecności.dodajGrupe(group.getName());
        }

        for(Grupa grupa: dziennikObecności.getGrupy()){
            for(StudentPackage student1: connector.getStudentsInGroup(dziennikObecności.getGrupy().indexOf(grupa))){
                grupa.addStudent(student1.getName(),student1.getSurname(),student1.getIndex());
            }
        }

        for(Grupa grupa:dziennikObecności.getGrupy()){
            for(ClassTime ct :connector.getClassTimesOfGroup(dziennikObecności.getGrupy().indexOf(grupa))){
                dziennikObecności.dodajTermin(grupa.getGroupName(),ct.getClassDate());
            }
        }



        setAllTables();
    }

    private void setAllTables(){
        //wypełnienie grup nazwami grup
        TabGStudentsGrupa.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
        dataGroups = FXCollections.observableArrayList();
        for (Grupa grupa : dziennikObecności.getGrupy()) {
            dataGroups.add(grupa);
        }

        tableGroup.setItems(dataGroups);
        //wypełnienie w grupach studentami pierwszej grupy
        tabCStudentsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tabCStudentsSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        tabCStudentsIndex.setCellValueFactory(new PropertyValueFactory<>("Index"));


        dataStudents = FXCollections.observableArrayList();
        for (Student student : dziennikObecności.getGrupy().get(0).getStudents()) {
            dataStudents.add(student);
        }
        tabVStudents.setItems(dataStudents);
        //zakładka studenci (Studenci)


        SImie.setCellValueFactory(new PropertyValueFactory<>("Name"));
        SNazwisko.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        SNI.setCellValueFactory(new PropertyValueFactory<>("Index"));
        Silo.setCellValueFactory(new PropertyValueFactory<>("Obecnosc"));

        dataStudents2 = FXCollections.observableArrayList();
            for (Student student : dziennikObecności.getGrupy().get(0).getStudents()) {
                dataStudents2.add(student);
            }

        SStud.setItems(dataStudents2);

            //zakładka grupy dla (Studenci)
        grupa2.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
        dataGroups2 = FXCollections.observableArrayList();
        for (Grupa grupa : dziennikObecności.getGrupy()) {
            dataGroups2.add(grupa);
        }

        grupa1.setItems(dataGroups2);

        //zakładka daty dla terminów
        data1.setCellValueFactory(new PropertyValueFactory<>("data"));
        dataData = FXCollections.observableArrayList();
        for (Termin termin : dziennikObecności.getTerminy()) {
            dataData.add(termin);
        }

        tab1Data.setItems(dataData);

        //zakładka grupy dla terminów

        grupa3.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        dataGroups4 = FXCollections.observableArrayList();
        dataGroups4.add(dziennikObecności.getTerminy().get(0));
        tab2Grupy.setItems(dataGroups4);

        //zakładka studenci dla terminów
        T1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T2.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        T3.setCellValueFactory(new PropertyValueFactory<>("Index"));


        dataStudents3 = FXCollections.observableArrayList();
            var entryset = dziennikObecności.getTerminy().get(0).getObecności().entrySet();
            for(var entry:entryset){
                dataStudents3.add(entry.getKey());
        }
        tab3Studenci.setItems(dataStudents3);


    }

    @FXML
    private void selectGroupButton(ActionEvent event) {
        tabCStudentsName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tabCStudentsSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        tabCStudentsIndex.setCellValueFactory(new PropertyValueFactory<>("Index"));


        dataStudents = FXCollections.observableArrayList();
        for (Grupa group : this.dziennikObecności.getGrupy()) {
            if (group.getGroupName().equals(tableGroup.getSelectionModel().getSelectedItem().getGroupName())) {
                for (Student student : group.getStudents()) {
                    dataStudents.add(student);
                }
            }
        }

        tabVStudents.setItems(dataStudents);
    }

    @FXML
    private void zaznaczObecnosc(ActionEvent event){

        T1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T2.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        T3.setCellValueFactory(new PropertyValueFactory<>("Index"));
        T4.setCellValueFactory(new PropertyValueFactory<>("Helper"));


        dataStudents3 = FXCollections.observableArrayList();
        for (Termin termin : this.dziennikObecności.getTerminy()) {
            if (termin.getData().equals(lastData)) {
                var entryset = dziennikObecności.getTerminy().get(dziennikObecności.getTerminy().indexOf(termin)).getObecności().entrySet();
                for (var entry : entryset) {
                    Student stud1 = new Student(entry.getKey().getName(),entry.getKey().getSurname(),entry.getKey().getIndex());
                    if(entry.getKey().getIndex()==tab3Studenci.getSelectionModel().getSelectedItem().getIndex() && entry.getValue().getObecnośćRodzaj().equals(NIEOBECNY)){
                        dataStudents3.remove(entry.getKey());
                        entry.setValue(new Obecność(OBECNY));
                        stud1.setHelper("Obecny");
                    }
                    dataStudents3.add(stud1);
                }
            }
        }
        tab3Studenci.setItems(dataStudents3);
    }

    @FXML void usunTermin(ActionEvent event){
        for(Termin termin: dziennikObecności.getTerminy()){
            if(termin.getData().equals(tab1Data.getSelectionModel().getSelectedItem().getData())){
                dziennikObecności.getTerminy().remove(termin);
            }
        }
        tab1Data.getItems().removeAll(tab1Data.getSelectionModel().getSelectedItem());
    }

    @FXML void dodajTermin(ActionEvent event){

        data1.setCellValueFactory(new PropertyValueFactory<>("data"));


        String data = textData.getText();
        String grupa = textGrupa.getText();

        if(data.length()>=1 && grupa.length()>=1){
            dataData = FXCollections.observableArrayList();
            dziennikObecności.dodajTermin(grupa,data);
            connector.addClassTime("zajęcia",130,"data","13:00","14:00","łódź","brak");
        }

        for (Termin termin : dziennikObecności.getTerminy()) {
            dataData.add(termin);
        }
        tab1Data.setItems(dataData);

    }


    @FXML
    private void selectTerminAndGroupButton(ActionEvent event) {
        grupa3.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        dataGroups4 = FXCollections.observableArrayList();
        dataGroups4.add(dziennikObecności.getTerminy().get(0));
        tab2Grupy.setItems(dataGroups4);


        dataGroups4 = FXCollections.observableArrayList();
        for (Termin termin : this.dziennikObecności.getTerminy()) {
            if (termin.getGrupa().equals(tab1Data.getSelectionModel().getSelectedItem().getGrupa())) {
                dataGroups4.add(termin);

            }
        }
        tab2Grupy.setItems(dataGroups4);

        T1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T2.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        T3.setCellValueFactory(new PropertyValueFactory<>("Index"));


        dataStudents3 = FXCollections.observableArrayList();
        for (Termin termin : this.dziennikObecności.getTerminy()) {
            if (termin.getGrupa().equals(tab1Data.getSelectionModel().getSelectedItem().getGrupa())) {
                var entryset = dziennikObecności.getTerminy().get(dziennikObecności.getTerminy().indexOf(termin)).getObecności().entrySet();
                for (var entry : entryset) {
                    dataStudents3.add(entry.getKey());
                }
                lastData=termin.getData();
            }
        }
        tab3Studenci.setItems(dataStudents3);
    }

    @FXML
    private void selectGroupButton2(ActionEvent event) {
        SImie.setCellValueFactory(new PropertyValueFactory<>("Name"));
        SNazwisko.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        SNI.setCellValueFactory(new PropertyValueFactory<>("Index"));
        Silo.setCellValueFactory(new PropertyValueFactory<>("Obecnosc"));


        dataStudents2 = FXCollections.observableArrayList();
        for (Grupa group : this.dziennikObecności.getGrupy()) {
            if (group.getGroupName().equals(grupa1.getSelectionModel().getSelectedItem().getGroupName())) {
                for (Student student : group.getStudents()) {
                    dataStudents2.add(student);
                }
            }
        }

        SStud.setItems(dataStudents2);
    }

    @FXML
    private void usunStudenta(ActionEvent event){
        for (Grupa group : this.dziennikObecności.getGrupy()) {
                for (Student student : group.getStudents()) {
                    if (group.getGroupName().equals(SStud.getSelectionModel().getSelectedItem().getName())){
                    group.deleteStudent(student.getIndex());
                    connector.deleteStudent(student.getIndex());
                }
            }
        }
        SStud.getItems().removeAll(SStud.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void addGroup(ActionEvent event) {
        String username = grupaNazwa.getText();
        if (username.length() >= 1) {
            dziennikObecności.dodajGrupe(username);

            //okno grup (Grupy)
            TabGStudentsGrupa.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
            dataGroups = FXCollections.observableArrayList();

            for (Grupa grupa : dziennikObecności.getGrupy()) {
                dataGroups.add(grupa);
                connector.addGroup(username,2022);

            }

            tableGroup.setItems(dataGroups);
        }
    }


    @FXML
    private void addStudent(ActionEvent event) {

        SImie.setCellValueFactory(new PropertyValueFactory<>("Name"));
        SNazwisko.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        SNI.setCellValueFactory(new PropertyValueFactory<>("Index"));
        Silo.setCellValueFactory(new PropertyValueFactory<>("Obecnosc"));

        String imie = ImieS.getText();
        String nazwisko = NazwiskoS.getText();
        String index = NIS.getText();
        if (imie.length() >= 1 && nazwisko.length() >= 1 && index.length() >= 1) {
            dataStudents2 = FXCollections.observableArrayList();
            //dziennikObecności.getGrupy().get(dziennikObecności.getGrupy().indexOf(grupa1.getSelectionModel().getSelectedItem().getGroupName())).addStudent(imie, nazwisko, Integer.parseInt(index));
            for (Grupa group : this.dziennikObecności.getGrupy()) {
                if (group.getGroupName().equals(grupa1.getSelectionModel().getSelectedItem().getGroupName())) {
                    group.addStudent(imie, nazwisko, Integer.parseInt(index));
                    connector.addStudent(imie,nazwisko,Integer.parseInt(index));
                    dataStudents2 = FXCollections.observableArrayList();
                    for (Student student : group.getStudents()) {
                        dataStudents2.add(student);
                    }
                    SStud.setItems(dataStudents2);
                }
            }
        }
    }
}

