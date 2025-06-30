package mx.edu.utez.fxa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.fxa.modelo.Usuario;

import javax.swing.*;

public class HelloController {

    @FXML
    private Label titulo;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;

    @FXML
    private void cambiarTitulo(){
        titulo.setText("SISA2");
    }

    @FXML
    private void login(ActionEvent event){

        String usuario = user.getText();
        String contra = password.getText();

        System.out.println(usuario);
        System.out.println(contra);

        Usuario u = new Usuario();
        u.setMatricula(usuario);
        u.setContra(contra);

        //Deberiamos checar si existe en la base datos
        //select * from usuarios where username = usuario AND password = contra;

        if(u.login()){
            //Si existo en la BD
            //Acceder al sistema
            JOptionPane.showMessageDialog(null, "Bienvenido!!!");

            try{
                //1. Cargar el FXML en una instancia de FXMLLoader
                FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
                Parent root = loader.load();
                //2. Obtener la stage
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                //3. hacer preparar la escena
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
                System.err.println("Error algo raro paso :(");
            }


        }else{
            JOptionPane.showMessageDialog(null, "No tienes acceso al sistema, crea una cuenta");
        }

    }


}