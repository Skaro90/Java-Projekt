package JavaProjekt.src.main.java.de.studentenverwaltung;

import java.util.Date;

public abstract class Person {
    private String nachname;
    private String vornahme;
    private String email;
    private Date geburtstag;

    public Person(String nachname, String vornahme, String email, Date geburtstag) {
        this.nachname = nachname;
        this.vornahme = vornahme;
        this.email = email;
        this.geburtstag = geburtstag;
    }
}
