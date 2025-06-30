package mx.edu.utez.fxa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.fxa.modelo.Alumno;
import mx.edu.utez.fxa.modelo.dao.AlumnoDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private Button alumnoBorrar;

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
            } else if (event.getClickCount() == 1 && !alumnoTabla.getSelectionModel().isEmpty()) {
                alumnoBorrar.setDisable(false);
            }
        });
        alumnoTabla.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
                if(seleccionado != null){
                    if(confirmDelete()){
                        dao.deleteAlumno(seleccionado.getId());
                        alumnoTabla.getItems().remove(seleccionado);
                        alumnoTabla.refresh();
                    }
                }
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

    private boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro que deseas eliminar este registro?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void borrarAlumno(){
        if (confirmDelete()){
            AlumnoDao dao = new AlumnoDao();
            Alumno seleccionado = (Alumno) alumnoTabla.getSelectionModel().getSelectedItem();
            alumnoTabla.getItems().remove(seleccionado);
            alumnoTabla.refresh();
            dao.deleteAlumno(seleccionado.getId());
        }
    }

}
