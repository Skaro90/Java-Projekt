package de.studentenverwaltung;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class StudentenVerwaltung{
//    Foreign Keys

    private ArrayList<Betreuer> betreuerListe;
    private ArrayList<Firma> firmaListe;
    private ArrayList<Student> studentenListe;
    private ArrayList<Kurs> kursListe;
    private ArrayList<Raum> raumListe;

    public StudentenVerwaltung() {
        //this.datenLaden();
    }

    //private datenLaden(){ }

    public Student studentAnlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk){
        Student s = new Student(name,vorname,geburtsdatum,email,matrikelNummer,firma,kurs,vk);
        this.studentenListe.add(s);
        //dbfunc aufrufen
        return s;
    }

    public Firma firmaAnlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){
        Firma f = new Firma(firmenname,strasse,hausnummer,postleitzahl,stadt,betreuer);
        this.firmaListe.add(f);
        //dbfunc
        return f;
    }

    public Betreuer betreuerAnlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer, Firma firma){
        Betreuer b = new Betreuer(nachname,vorname,email,geburtstag,telefonnummer,firma);
        this.betreuerListe.add(b);
        //dbfunc
        return b;
    }

    public Kurs kursAnlegen(String kursName, Raum raum){
        Kurs k = new Kurs(kursName,raum);
        this.kursListe.add(k);
        //dbfunc
        return k;
    }

    public Raum raumAnlegen(String raumNummer, int kapazitaet, Kurs kurs){
        Raum r = new Raum(raumNummer,kapazitaet,kurs);
        this.raumListe.add(r);
        //dbfunc
        return r;
    }

    public Betreuer findeBetreuer(String email){
        for (Betreuer tmp : betreuerListe){
            if(tmp.getEmail().equals(email)){
                return tmp;
            }
        }
        return null;
    }

    public Student findeStudent(String matrikelNummer){
        for(Student tmp: studentenListe){
            if(tmp.getMatrikelnummer().equals(matrikelNummer)){
                return tmp;
            }
        }
        return null;
    }

    public Raum findeRaum(String raumNummer){
        for(Raum tmp: raumListe){
            if(tmp.getRaumNummer().equals(raumNummer)){
                return tmp;
            }
        }
        return null;
    }

    public Kurs findeKurs(String bezeichnung){
        for(Kurs tmp: kursListe){
            if(tmp.getKursName().equals(bezeichnung)){
                return tmp;
            }
        }
        return null;
    }

    public void exmatrikulieren(Student student){
        //firma/kurs löschen (neue func in student)
        studentenListe.remove(student);
        //dbfunc
    }

    public void raumZuweisen(Kurs kurs, Raum raum){
        //kurs.raumWechseln(raum);
        //dbfunc
    }

    public void studentVersetzten(Student student, Kurs kurs){
        //student.versetzten(kurs);
        //dbfunc
    }

    public void betreuerWechseln(Firma firma, Betreuer betreuer){
        //firma.betreuerWechsel(betreuer);
        //dbfunc

    }
}
