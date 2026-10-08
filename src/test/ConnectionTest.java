/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import util.DBConnection;
import java.sql.Connection;

/**
 *
 * @author Achintha
 */
public class ConnectionTest {
    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Database connection successful.");

            con.close();

        } catch (Exception e) {

            System.out.println(
                    "Database connection failed."
            );

            e.printStackTrace();
        }
    }    
}
