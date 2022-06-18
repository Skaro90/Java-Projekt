package de.studentenverwaltung;

import java.sql.*;
import java.util.Date;

public class Datenbank {
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    Datenbank(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaprojekt", "root", "test");
            statement = connection.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void zeigeStudenten(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM student");
            while(resultSet.next()){
                System.out.println((resultSet.getString("name")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ResultSet ladeBetreuer(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM betreuer");
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet ladeFirma(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM firma INNER JOIN adresse ON firma.aid = adresse.aid");
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet ladeRaum(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM raum");
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet ladeKurs(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM kurs");
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet ladeStudent(){
        try{
            resultSet = statement.executeQuery("SELECT * FROM student");
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int Studentanlegen(String name, String vorname, Date geburtsdatum, String email, String matrikelNummer, Firma firma, Kurs kurs, Student.Vorkenntnisse vk){
        try{
            String query ="INSERT INTO `javaprojekt`.`student`" +"(`matrikelnummer`," +"`name`," +"`vorname`," +"`email`," +"`geburtstag`," +"`Vorkenntnisse`," +"`fid`," +"`kid`)" +"VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,matrikelNummer);
            pstmt.setString(2,name);
            pstmt.setString(3,vorname);
            pstmt.setString(4,email);
            pstmt.setDate(5,new java.sql.Date(geburtsdatum.getTime()));
            pstmt.setInt(6,vk.ordinal());
            pstmt.setInt(7,firma.getFirmenId());
            pstmt.setInt(8,kurs.getKursId());
            pstmt.execute();
            resultSet = statement.executeQuery("SELECT student.sid FROM student WHERE student.matrikelnummer = '" + matrikelNummer +"'");
            while(resultSet.next()){
                return resultSet.getInt("sid");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int adresseanlegen(String strasse, String hausnummer, String postleitzahl, String stadt){
        try{
            String query ="INSERT INTO `javaprojekt`.`adresse`\n" +
                    "(`strasse`,\n" +
                    "`hausnummer`,\n" +
                    "`plz`,\n" +
                    "`stadt`)" +"VALUES(?,?,?,?)";
            System.out.println();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,strasse);
            pstmt.setString(2,hausnummer);
            pstmt.setString(3,postleitzahl);
            pstmt.setString(4,stadt);
            pstmt.execute();

            resultSet = statement.executeQuery("SELECT aid FROM adresse WHERE strasse = '"+ strasse+"' AND hausnummer = '"+hausnummer+"' AND plz = '"+postleitzahl+"' AND stadt= '"+stadt+"'");
            while(resultSet.next()){
                return resultSet.getInt("aid");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int firmaanlegen(String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, Betreuer betreuer){

        try{

            int aid = 0;
            resultSet = statement.executeQuery("SELECT aid FROM adresse WHERE strasse = '"+ strasse+"' AND hausnummer = '"+hausnummer+"' AND plz = '"+postleitzahl+"' AND stadt= '"+stadt+"'");
            while(resultSet.next()){
                aid=resultSet.getInt("aid");
            }
            if(aid == 0){
                aid = adresseanlegen(strasse,hausnummer,postleitzahl,stadt);
            }
            String query = "INSERT INTO `javaprojekt`.`firma`\n" +
                    "(`bezeichnung`,\n" +
                    "`aid`,\n" +
                    "`bid`)" + "VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,firmenname);
            pstmt.setInt(2,aid);
            pstmt.setInt(3,betreuer.getBetreuerId());
            pstmt.execute();
            resultSet = statement.executeQuery("SELECT fid FROM firma WHERE bezeichnung = '"+firmenname+"'");
            while(resultSet.next()){
                return resultSet.getInt("fid");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int betreueranlegen(String nachname, String vorname, String email, Date geburtstag, String telefonnummer){
       try {
           String query = "INSERT INTO `javaprojekt`.`betreuer`\n" +
                   "(`name`,\n" +
                   "`vorname`,\n" +
                   "`email`,\n" +
                   "`geburtstag`,\n" +
                   "`telefonnummer`)\n" +
                   "VALUES(?,?,?,?,?)";
           PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1,nachname);
           pstmt.setString(2,vorname);
           pstmt.setString(3,email);
           pstmt.setDate(4,new java.sql.Date(geburtstag.getTime()));
           pstmt.setString(5,telefonnummer);
           pstmt.execute();

           resultSet = statement.executeQuery("SELECT bid FROM betreuer WHERE email='" + email + "'");
           while(resultSet.next()){
               return resultSet.getInt("bid");
           }

       }catch(Exception e){
           e.printStackTrace();
       }
        return 0;
    }

    public int kursanlegen(String kursName, Raum raum){
        try {
            String query = "INSERT INTO `javaprojekt`.`kurs`\n" +
                    "(`bezeichnung`,\n" +
                    "`rid`)\n" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,kursName);
            if(raum != null) {
                pstmt.setInt(2, raum.getRaumId());
            }else{
                pstmt.setNull(2, Types.NULL);
            }
            pstmt.execute();

            resultSet = statement.executeQuery("SELECT kid FROM kurs WHERE bezeichnung = '"+kursName+"'");
            while(resultSet.next()){
                return resultSet.getInt("kid");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int raumanlegen(String raumNummer, int kapazitaet){
        try {
            String query = "INSERT INTO `javaprojekt`.`raum`\n" +
                    "(`nummer`,\n" +
                    "`kapazität`)\n" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,raumNummer);
            pstmt.setInt(2,kapazitaet);
            pstmt.execute();

            resultSet = statement.executeQuery("SELECT rid FROM raum WHERE nummer = '"+raumNummer+"'");
            while(resultSet.next()){
                return resultSet.getInt("rid");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int exmatrikulieren(Student student){
        try {
            String query = "DELETE FROM `javaprojekt`.`student`\n" +
                    "WHERE sid=?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,student.getStudentId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int raumZuweisen(Kurs kurs, Raum raum){
        try {
            String query = "UPDATE `javaprojekt`.`kurs`\n" +
                    "SET\n" +
                    "`rid` = ?\n" +
                    "WHERE `kid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,raum.getRaumId());
            pstmt.setInt(2,kurs.getKursId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int studentVersetzen(Student student, Kurs kurs){
        try {
            String query = "UPDATE `javaprojekt`.`student`\n" +
                    "SET\n" +
                    "`kid` = ?\n" +
                    "WHERE `sid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,kurs.getKursId());
            pstmt.setInt(2,student.getStudentId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int betreuerWechseln(Firma firma, Betreuer betreuer){
        try {
            String query = "UPDATE `javaprojekt`.`firma`\n" +
                    "SET\n" +
                    "`bid` = ?\n" +
                    "WHERE `fid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,betreuer.getBetreuerId());
            pstmt.setInt(2,firma.getFirmenId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int firmaloeschen(Firma firma){
        try {
            String query = "DELETE FROM `javaprojekt`.`firma`\n" +
                    "WHERE fid=?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,firma.getFirmenId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int betreuerloeschen(Betreuer betreuer){
        try {
            String query = "DELETE FROM `javaprojekt`.`betreuer`\n" +
                    "WHERE bid=?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,betreuer.getBetreuerId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int kursloeschen(Kurs kurs){
        try {
            String query = "DELETE FROM `javaprojekt`.`kurs`\n" +
                    "WHERE kid=?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,kurs.getKursId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int raumloeschen(Raum raum){
        try {
            String query = "DELETE FROM `javaprojekt`.`raum`\n" +
                    "WHERE rid=?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,raum.getRaumId());
            int val = pstmt.executeUpdate();
            return val;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int kursupdate(Kurs kurs, String nameneu){
        try{
            String query = "UPDATE `javaprojekt`.`kurs`\n" +
                "SET\n" +
                "`bezeichnung` = ?,\n" +
                "WHERE `kid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,nameneu);
            pstmt.setInt(2,kurs.getKursId());
            int val = pstmt.executeUpdate();
            return val;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int studentupdate(Student student, String name,String vorname,String email, Date geburtstag){
        try{
            String query = "UPDATE `javaprojekt`.`student`\n" +
                    "SET\n" +
                    "`name` = ?,\n" +
                    "`vorname` = ?,\n" +
                    "`email` = ?,\n" +
                    "`geburtstag` = ?\n" +
                    "WHERE `sid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setString(2,vorname);
            pstmt.setString(3,email);
            pstmt.setDate(4,new java.sql.Date(geburtstag.getTime()));
            pstmt.setInt(5,student.getStudentId());
            int val = pstmt.executeUpdate();
            return val;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int raumupdate(Raum raum,String raumname, int kapazitaet){
        try{
            String query = "UPDATE `javaprojekt`.`raum`\n" +
                    "SET\n" +
                    "`nummer` = ?,\n" +
                    "`kapazität` = ?\n" +
                    "WHERE `rid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,raumname);
            pstmt.setInt(2,kapazitaet);
            pstmt.setInt(3,raum.getRaumId());
            int val = pstmt.executeUpdate();
            return val;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int firmenupdate(Firma firma, String firmenname,String strasse, String hausnummer, String postleitzahl, String stadt, String betreuerNachname, String betreuerVorname, String betreuerEmail, Date betreuerGeburtstag, String betreuerTelefonnummer){
        boolean mehrfach = false;
        int aid_neu = 0;
        try{
            String query = "UPDATE `javaprojekt`.`betreuer`\n" +
                    "SET\n" +
                    "`name` = ?,\n" +
                    "`vorname` = ?,\n" +
                    "`email` = ?,\n" +
                    "`geburtstag` = ?,\n" +
                    "`telefonnummer` = ?\n" +
                    "WHERE `bid` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,betreuerNachname);
            pstmt.setString(2,betreuerVorname);
            pstmt.setString(3,betreuerEmail);
            pstmt.setDate(4,new java.sql.Date(betreuerGeburtstag.getTime()));
            pstmt.setString(5,betreuerTelefonnummer);
            pstmt.setInt(6,firma.getBetreuer().getBetreuerId());
            pstmt.executeUpdate();
            resultSet = statement.executeQuery("SELECT fid,aid FROM firma WHERE aid = (SELECT aid FROM firma WHERE bezeichnung = '" + firma.getFirmenname()+"')");
            aid_neu = resultSet.getInt("aid");
            while(resultSet.next()){
                if(resultSet.getInt("fid") != firma.getFirmenId()){
                    mehrfach = true;
                }
            }
            if(mehrfach){
                aid_neu = adresseanlegen(strasse, hausnummer, postleitzahl, stadt);
            }

            query = "UPDATE `javaprojekt`.`firma`\n" +
                    "SET\n" +
                    "`bezeichnung` = ?,\n" +
                    "`aid` = ?,\n" +
                    "WHERE `fid` = ?;";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1,firmenname);
            pstmt.setInt(2,aid_neu);
            pstmt.setInt(3,firma.getFirmenId());
            return pstmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
