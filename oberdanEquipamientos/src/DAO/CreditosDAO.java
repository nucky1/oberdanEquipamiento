/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Models.Credito;
import com.mysql.jdbc.Connection;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Hernan
 */
public class CreditosDAO {
    private static CreditosDAO CreditosDao=null;
    private Statics.Conexion conexion= Statics.Conexion.getInstance();
    public CreditosDAO(){
        
    }
    public static CreditosDAO getInstance(){
        if(CreditosDao== null){
            CreditosDao = new CreditosDAO();
        }
        return CreditosDao;
    }
    public List <Credito> buscarCredito (int id) throws SQLException{
        String SQL = "SELECT credito.* FROM credito"
                +" WHERE credito.id ="+id+ "AND credito.state= 'ACTIVO'";
        ResultSet rs = conexion.EjecutarConsultaSQL(SQL);
        List<Credito> list = new ArrayList();
        while(rs.next()){
            Credito c = new Credito();
            
        }
        return list;
    }
    
}
