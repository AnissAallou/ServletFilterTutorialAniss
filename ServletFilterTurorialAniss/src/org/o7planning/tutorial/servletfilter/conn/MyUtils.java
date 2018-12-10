package org.o7planning.tutorial.servletfilter.conn;

import java.sql.Connection;
import javax.servlet.ServletRequest;

public class MyUtils {
	
    public static final String ATT_NAME = "MY_CONNECTION_ATTRIBUTE";
    
    // Stockez l'objet Connection dans un attribut (attribute) de la demande.
    // Des informations stockées existent uniquement dans le temps de l'exécution de la demande (request)
    // jusuq'à que des données seront envoyées vers le navigateur de l'utilisateur.
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME, conn);
    }
 
    // Obtenez l'objet Connection qui a été stocké dans un attribut de la demande.
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME);
        return conn;
    }
    
}
