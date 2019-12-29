package com.imejpul.ManejoFicheros;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerarArchivoConsultas {

    public static void generarArchivoConsultas(String query) {
        PrintWriter printWriter;
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("./Consultas.txt", true);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(query);
            printWriter.close();
            System.out.println("¡Consulta almacenada en archivo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error crearArchivoConsultas() " + e.getMessage());
        }
    }
}
