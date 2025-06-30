package mx.edu.utez.fxa.modelo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Usuario {

    private String nombre;
    private int edad;
    private String matricula;
    private String contra;

    public Usuario() {}

    public Usuario(String nombre, int edad, String matricula, String contra) {
        this.nombre = nombre;
        this.edad = edad;
        this.matricula = matricula;
        this.contra = contra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public boolean login(){
        //Sirve para saber si el usuario existe en la BD o no
        try{
            String path = "/mx/edu/utez/fxa/baseDeDatos.txt";
            InputStream stream = getClass().getResourceAsStream(path);
            Scanner scanner = new Scanner(stream);

            //Iterar cada linea del archivo para obtener valores
            while(scanner.hasNextLine()){ //mientras el archivo tenga una linea siguiente
                String linea = scanner.nextLine();
                String matricula = linea.split(",")[2];
                String contra = linea.split(",")[3];
                System.out.println(matricula);
                System.out.println(contra);

                //Vamos a comparar que sean las credenciales del usuario actual
                if(this.matricula.equals(matricula) && this.contra.equals(contra)){
                    return true;
                }
            }
            return false;
        }catch(Exception e){
            System.out.println("Error, no se encontro la BD o el usuario");
            return false;
        }
    }
}
