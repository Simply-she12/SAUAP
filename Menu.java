import mx.avanti.desarrollo.autenticacion.Profesor;

import java.util.Scanner;

public class Menu{

    public static void main(String[] args) {

        int op;
        int contador=0;
        String id, nombre, apPaterno, apMaterno, rfc;
        int p = 0;
        Profesor[] profesor = new Profesor[100];
        Scanner sc = new Scanner(System.in);
do{
        System.out.println("Menu: ");
        System.out.println("1. Altas ");
        System.out.println("2. Administracion ");
        op = sc.nextInt();
        switch (op) {
            case 1:
                //Altas
                System.out.println("Alta de profesor");
                System.out.println("Ingrese el nombre: ");
                nombre = sc.next();
                System.out.print("Ingrese apellido paterno: ");
                apPaterno = sc.nextLine();

                System.out.print("Ingrese apellido materno: ");
                apMaterno = sc.nextLine();

                System.out.print("Ingrese RFC: ");
                rfc = sc.nextLine();

                System.out.println("Profesor registrado correctamente");
                id = String.valueOf(p + 1);
                System.out.println("Identificador del profesor: " + id);
                profesor[p] = new Profesor(id, nombre, apPaterno, apMaterno, rfc);
                p = p + 1;
                break;

            case 2:
                //Administracion
                break;
        }}while (op!=5);
    }
}