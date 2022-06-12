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
}
