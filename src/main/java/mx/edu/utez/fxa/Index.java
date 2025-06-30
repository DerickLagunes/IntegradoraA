package mx.edu.utez.fxa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.fxa.modelo.Alumno;
import mx.edu.utez.fxa.modelo.dao.AlumnoDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Index implements Initializable {

    @FXML
    private TextField alumnoId;
    @FXML
    private TextField alumnoNombre;
    @FXML
    private TextField alumnoMatricula;
    @FXML
    private TextField alumnoEdad;
    @FXML
    private Button alumnoBoton;
    @FXML
    private TableView<Alumno> alumnoTabla;
    @FXML
    private TableColumn<Alumno, Integer> tablaId;
    @FXML
    private TableColumn<Alumno, String> tablaNombre;
    @FXML
    private TableColumn<Alumno, String> tablaMatricula;
    @FXML
    private TableColumn<Alumno, Integer> tablaEdad;

    @FXML
    public void registrarAlumno(ActionEvent event){

        int id = Integer.parseInt(alumnoId.getText());
        String nombre = alumnoNombre.getText();
        String matricula = alumnoMatricula.getText();
        int edad = Integer.parseInt(alumnoEdad.getText());

        Alumno brandon = new Alumno(id, nombre, matricula, edad);

        AlumnoDao dao = new AlumnoDao();
        dao.createAlumno(brandon);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AlumnoDao dao = new AlumnoDao();
        List<Alumno> datos = dao.readAlumnos();
        ObservableList<Alumno> datosObservables = FXCollections.observableList(datos);

        tablaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tablaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        //Pintar los datos
        alumnoTabla.setItems(datosObservables);

        //Ponerle un evento a la tabla
        alumnoTabla.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !alumnoTabla.getSelectionModel().isEmpty()) {
                Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
                abrirVentanaEdicion(seleccionado);
            }
        });
    }

    public void abrirVentanaEdicion(Alumno alumno){
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarAlumno.fxml"));
        Parent root = loader.load();

        // Pasar el alumno al controlador
        EditarAlumno controller = loader.getController();
        controller.setAlumno(alumno); // Método que defines tú

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Editar Alumno");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        alumnoTabla.refresh();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }


}
