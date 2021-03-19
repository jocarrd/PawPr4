/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;

import paw.bd.Paginador;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;

/**
 *
 * @author usuariolocal
 */
public class GestorBD {
     
     
     public static List<Articulo> getArticulos(int pagina, int tamanioPagina)
 throws ExcepcionDeAplicacion{
         Connection con = null;
        Statement stmt = null;   
        ResultSet rs = null;
        List<Articulo> nombres = new ArrayList<>();
        
        
        try {
            con = paw.bd.ConnectionManager.getConnection();
            stmt = con.createStatement();
            String consulta = "select * from articulo";

            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                Articulo nuevo = new Articulo(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                nombres.add(nuevo);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
               if(con!=null) con.close();
            } catch (SQLException ex) {
               
            }
        }

        return nombres;
     }
     
     public static Paginador getPaginadorArticulos(int tamanioPagina)
 throws ExcepcionDeAplicacion{
         
       paw.bd.GestorBD s = new paw.bd.GestorBD ();
       int registros=s.getArticulos().size();
         
         
         
         
         
         return new Paginador(registros,tamanioPagina);
         
     }

}
