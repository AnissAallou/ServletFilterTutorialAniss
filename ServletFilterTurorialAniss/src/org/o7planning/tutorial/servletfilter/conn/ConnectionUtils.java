package org.o7planning.tutorial.servletfilter.conn;

import java.sql.Connection;

public class ConnectionUtils {

    public static Connection getConnection() {
    	 
        // Cr�ez une Connection (Connexion) vers la base de donn�es.
        Connection conn = null;
 
        // .....
        return conn;
    }
 
    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
 
    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
    
}
