package com.imejpul.ManejoFicheros;

import com.imejpul.Auxiliares.ListaCircuitos;
import com.imejpul.Serializables.Circuito;
import com.thoughtworks.xstream.XStream;

import java.io.*;

public class ManejoFicheroCircuito {

    public void escribirFicheroCircuito(Circuito circuito) {
        //objeto file
        File fichero = new File("./.datDocs/FichCircuitos.dat");
        //Flujo de salida
        try {
            FileOutputStream fileout = new FileOutputStream(fichero, true);
            //Conecta el flujo de bytes al flujo de datos
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            dataOS.writeObject(circuito);
            dataOS.close();
        } catch (IOException e) {
            System.out.println(" - Fin fichero Circuitos - ");
            //e.printStackTrace();
        }
    }

    public void escribirFicheroCircuitoXML() {

        File fichero = new File("./.datDocs/FichCircuitos.dat");

        ObjectInputStream dataIS = null;
        FileInputStream filein = null;
        try {
            filein = new FileInputStream(fichero);

        } catch (IOException e) {
            System.out.println(" - Fin fichero Circuitos (XML) - ");
            //e.printStackTrace();
        }

        //Creamos un objeto Lista
        ListaCircuitos lr = new ListaCircuitos();
        try {
            while (true) {
                //lectura del fichero
                dataIS = new ObjectInputStream(filein);
                Circuito circuito = (Circuito) dataIS.readObject();
                lr.addResultadoALista(circuito);

            }
        } catch (IOException o) {
            System.out.println(" - Fin fichero Circuitos (XML)- ");
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
            xstream.alias("ListaCircuitos", ListaCircuitos.class);
            xstream.alias("Circuito", Circuito.class);
            //quitar etiqueta lista (atributo de la clase ListaPersonas)
            xstream.addImplicitCollection(ListaCircuitos.class, "Lista");
            //Insrtarlos objetos en el XML
            xstream.toXML(lr, new FileOutputStream("./XMLDocs/Circuitos.xml", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
