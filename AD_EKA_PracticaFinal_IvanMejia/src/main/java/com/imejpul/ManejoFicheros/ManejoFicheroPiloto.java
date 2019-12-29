package com.imejpul.ManejoFicheros;

import com.imejpul.Auxiliares.ListaPilotos;
import com.imejpul.Serializables.Piloto;
import com.thoughtworks.xstream.XStream;

import java.io.*;

public class ManejoFicheroPiloto {

    public void escribirFicheroPiloto(Piloto piloto) {
        //objeto file
        File fichero = new File("./.datDocs/FichPilotos.dat");
        //Flujo de salida
        try {
            FileOutputStream fileout = new FileOutputStream(fichero, true);
            //Conecta el flujo de bytes al flujo de datos
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            dataOS.writeObject(piloto);
            dataOS.close();
        } catch (IOException e) {
            System.out.println(" - Fin fichero Pilotos - ");
            //e.printStackTrace();
        }
    }

    public void escribirFicheroPilotoXML() {

        File fichero = new File("./.datDocs/FichPilotos.dat");

        ObjectInputStream dataIS = null;
        FileInputStream filein = null;
        try {
            filein = new FileInputStream(fichero);

        } catch (IOException e) {
            System.out.println(" - Fin fichero Pilotos (XML) - ");
            //e.printStackTrace();
        }

        //Creamos un objeto Lista
        ListaPilotos lr = new ListaPilotos();
        try {
            while (true) {
                //lectura del fichero
                dataIS = new ObjectInputStream(filein);
                Piloto piloto = (Piloto) dataIS.readObject();
                lr.addResultadoALista(piloto);

            }
        } catch (IOException o) {
            System.out.println(" - Fin fichero Pilotos (XML)- ");
            //o.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dataIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("ListaPilotos", ListaPilotos.class);
            xstream.alias("Piloto", Piloto.class);
            //quitar etiqueta lista (atributo de la clase ListaPersonas)
            xstream.addImplicitCollection(ListaPilotos.class, "Lista");
            //Insrtarlos objetos en el XML
            xstream.toXML(lr, new FileOutputStream("./XMLDocs/Pilotos.xml", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
