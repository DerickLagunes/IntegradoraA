package mx.edu.utez.fxa.modelo.dao;

import mx.edu.utez.fxa.modelo.Alumno;
import mx.edu.utez.fxa.utils.OracleDatabaseConnectionManager;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {

    public boolean createAlumno(Alumno a) {
        //obtener la conexión

        //Preparar el sql statement
        String query = "INSERT INTO alumno(id,nombre,matricula,edad) VALUES(?,?,?,?)";
        try {
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,a.getId());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getMatricula());
            ps.setInt(4,a.getEdad());
            int resultado = ps.executeUpdate();
            if(resultado > 0){
                JOptionPane.showMessageDialog(null, "Se inserto el registro!!!");

                conn.close();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }


    //Función de Lectura (R) del CRUD
    public List<Alumno> readAlumnos(){
        //Paso 1: obtener la conexión
        //Paso 2: configurar el SQL y la lista de retorno
        String query = "SELECT * FROM alumno ORDER BY id ASC";
        List<Alumno> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            //Paso 3: iterar el resultSet para traducir los datos de BD a Java
            while(rs.next()){
                Alumno a = new Alumno();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setMatricula(rs.getString("matricula"));
                a.setEdad(rs.getInt("edad"));
                lista.add(a);
            }
            //Paso 4: Cerrar connecciones
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        //Paso 5: Retornar mi lista
        return lista;
    }

    public static void main(String[] args) {
        AlumnoDao dao = new AlumnoDao();
        List<Alumno> datos = dao.readAlumnos();
        //Ciclo Foreach
        for(Alumno a : datos){
            System.out.println(a.getNombre());
        }
    }

    public boolean updateAlumno(int id, Alumno a){
        //obtener la conexión
        //Preparar el sql statement
        String query = "UPDATE alumno SET id=?,nombre=?,matricula=?,edad=? WHERE id=?";
        try {
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,a.getId());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getMatricula());
            ps.setInt(4,a.getEdad());
            ps.setInt(5,id);
            int resultado = ps.executeUpdate();
            if(resultado > 0){
                conn.close();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
