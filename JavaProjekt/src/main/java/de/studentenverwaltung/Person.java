package de.studentenverwaltung;

import java.util.Date;

public abstract class Person {
    private String nachname;
    private String vorname;
    private String email;
    private Date geburtstag;

    public Person(String nachname, String vorname, String email, Date geburtstag) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.email = email;
        this.geburtstag = geburtstag;
    }
    public Person(){}

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getEmail() {
        return email;
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public void nachnameAendern(String nachname){
        this.nachname = nachname;
    }

    public void vornameAendern(String vorname){
        this.vorname = vorname;
    }

    public void emailAendern(String email){
        this.email = email;
    }

    public void geburtstagAendern(Date geburtstag){
        this.geburtstag = geburtstag;
    }
}