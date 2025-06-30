package mx.edu.utez.fxa;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.fxa.modelo.Alumno;
import mx.edu.utez.fxa.modelo.dao.AlumnoDao;

public class EditarAlumno {

    @FXML
    private TextField alumnoId;
    @FXML
    private TextField alumnoNombre;
    @FXML
    private TextField alumnoMatricula;
    @FXML
    private TextField alumnoEdad;

    private Alumno alumno;
    private int idViejito;

    //Método para obtener el alumno desde la vista de Index
    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
        this.idViejito = alumno.getId();
    }

    public void actualizarAlumno(){
        String nombre = alumnoNombre.getText();
        String matricula = alumnoMatricula.getText();
        int edad = Integer.parseInt(alumnoEdad.getText());
        int id = Integer.parseInt(alumnoId.getText());

        alumno.setId(id);
        alumno.setNombre(nombre);
        alumno.setMatricula(matricula);
        alumno.setEdad(edad);

        AlumnoDao dao = new AlumnoDao();
        //Razon de ser de esta ventana
        dao.updateAlumno(idViejito, alumno);
        //Cerrar la ventana
        Stage stage = (Stage) alumnoId.getScene().getWindow();
        stage.close();

    }


}
