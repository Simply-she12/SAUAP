package mx.avanti.desarrollo.autenticacion;
import java.util.Scanner;

public class Profesor {

    String identificador, nombre, apPaterno, apMaterno, rfc;

    public Profesor(String id, String nombre, String apPaterno, String apMaterno, String rfc) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.rfc = rfc;
}

    public String writeProfesor()
    {
        return ("Identificador del Profesor: " + identificador +
                "\nNombre: " + nombre +
                "\nApellido Paterno: " + apPaterno +
                "\nApellido Materno: " + apMaterno +
                "\nRFC: " + rfc);
    }
}
