package com.imejpul;

import com.imejpul.ManejoFicheros.ManejoFicheroCircuito;
import com.imejpul.ManejoFicheros.ManejoFicheroMoto;
import com.imejpul.ManejoFicheros.ManejoFicheroPiloto;
import com.imejpul.Serializables.Circuito;
import com.imejpul.Serializables.Moto;
import com.imejpul.Serializables.Piloto;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static List<Piloto> pilotos = new ArrayList<Piloto>();
    private static List<Moto> motos = new ArrayList<Moto>();
    private static List<Circuito> circuitos = new ArrayList<Circuito>();

    public static void main(String[] args) throws IOException {

        int opcion = 0;

        do {
            showMenuPpal();
            System.out.print("¿Opción?: ");
            opcion = Integer.parseInt(br.readLine());

            switch (opcion) {
                case 1:
                    System.out.println("Generando XMLs..");
                    try {
                        generarDatosTablaPiloto();
                        ManejoFicheroPiloto mfp = new ManejoFicheroPiloto();
                        for (Piloto p : pilotos) {
                            mfp.escribirFicheroPiloto(p);
                        }
                        mfp.escribirFicheroPilotoXML();
                        xmldbOperations.createCollectionResource("Competicion", "./XMLDocs/Pilotos.xml");
                        xmldbOperations.RetrieveResource("Competicion", "Pilotos.xml");

                        //---------------------
                        generarDatosTablaMoto();
                        ManejoFicheroMoto mfm = new ManejoFicheroMoto();
                        for (Moto m : motos) {
                            mfm.escribirFicheroMoto(m);
                        }
                        mfm.escribirFicheroMotoXML();
                        xmldbOperations.createCollectionResource("Competicion", "./XMLDocs/Motos.xml");
                        xmldbOperations.RetrieveResource("Competicion", "Motos.xml");

                        //-------------------------

                        generarDatosTablaCircuito();
                        ManejoFicheroCircuito mfc = new ManejoFicheroCircuito();
                        for (Circuito c : circuitos) {
                            mfc.escribirFicheroCircuito(c);
                        }
                        mfc.escribirFicheroCircuitoXML();
                        xmldbOperations.createCollectionResource("Competicion", "./XMLDocs/Circuitos.xml");
                        xmldbOperations.RetrieveResource("Competicion", "Circuitos.xml");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    int opcionConsultas = 0;

                    do {
                        showMenuConsultas();
                        System.out.print("¿Opción?: ");
                        opcionConsultas = Integer.parseInt(br.readLine());

                        int opcionTabla = 0;
                        ConsultasTablas ct = new ConsultasTablas();

                        switch (opcionConsultas) {
                            case 1:

                                showTablesToOperate();
                                System.out.print("¿Opción?: ");
                                opcionTabla = Integer.parseInt(br.readLine());

                                switch (opcionTabla) {
                                    case 1:
                                        System.out.println("Insertado Piloto-> " + ct.insertTablaPiloto(obtenerDatosPiloto(1)));
                                        break;
                                    case 2:
                                        System.out.println("Insertada Moto-> " + ct.insertTablaMoto(obtenerDatosMoto(1)));
                                        break;
                                    case 3:
                                        System.out.println("Insertado Circuito-> " + ct.insertTablaCircuito(obtenerDatosCircuito(1)));
                                        break;
                                    case 4:
                                        showConsultasMultiTabla(ct);
                                        break;
                                    case 5:
                                        System.out.println("Saliendo de elección de tabla..");
                                        break;

                                }
                                break;
                            case 2:
                                showTablesToOperate();
                                System.out.print("¿Opción?: ");
                                opcionTabla = Integer.parseInt(br.readLine());

                                switch (opcionTabla) {
                                    case 1:
                                        System.out.println("Actualizado Piloto-> " + ct.updateTablaPiloto(obtenerDatosPiloto(2)));
                                        break;
                                    case 2:
                                        System.out.println("Actualizado Moto-> " + ct.updateTablaMoto(obtenerDatosMoto(2)));
                                        break;
                                    case 3:
                                        System.out.println("Actualizado Circuito-> " + ct.updateTablaCircuito(obtenerDatosCircuito(2)));
                                        break;
                                    case 4:
                                        showConsultasMultiTabla(ct);
                                        break;
                                    case 5:
                                        System.out.println("Saliendo de elección de tabla..");
                                        break;

                                }
                                break;
                            case 3:
                                showTablesToOperate();
                                System.out.print("¿Opción?: ");
                                opcionTabla = Integer.parseInt(br.readLine());

                                switch (opcionTabla) {
                                    case 1:
                                        System.out.println("Eliminado Piloto-> " + ct.deleteTablaPiloto(obtenerDatosPiloto(3)));
                                        break;
                                    case 2:
                                        System.out.println("Eliminado Moto-> " + ct.deleteTablaMoto(obtenerDatosMoto(3)));
                                        break;
                                    case 3:
                                        System.out.println("Eliminado Circuito-> " + ct.deleteTablaCircuito(obtenerDatosCircuito(3)));
                                        break;
                                    case 4:
                                        showConsultasMultiTabla(ct);
                                        break;
                                    case 5:
                                        System.out.println("Saliendo de elección de tabla..");
                                        break;
                                }
                                break;
                            case 4:
                                showTablesToOperate();
                                System.out.print("¿Opción?: ");
                                opcionTabla = Integer.parseInt(br.readLine());

                                switch (opcionTabla) {
                                    case 1:
                                        ct.viewDataFromTablaPiloto();
                                        break;
                                    case 2:
                                        ct.viewDataFromTablaMoto();
                                        break;
                                    case 3:
                                        ct.viewDataFromTablaCircuito();
                                        break;
                                    case 4:
                                        showConsultasMultiTabla(ct);
                                        break;
                                    case 5:
                                        System.out.println("Saliendo de elección de tabla..");
                                        break;
                                }
                                break;
                            case 5:
                                showTablesToOperate();
                                System.out.print("¿Opción?: ");
                                opcionTabla = Integer.parseInt(br.readLine());

                                int opElegida = 0;

                                switch (opcionTabla) {
                                    case 1:
                                        opElegida = obtenerOpcionBusquedaTablaPiloto();
                                        if (opElegida != 3) {
                                            ct.searchDataFromTablaPiloto(opElegida);
                                        }
                                        break;
                                    case 2:
                                        opElegida = obtenerOpcionBusquedaTablaMoto();
                                        if (opElegida != 3) {
                                            ct.searchDataFromTablaMoto(opElegida);
                                        }
                                        break;
                                    case 3:
                                        opElegida = obtenerOpcionBusquedaTablaCircuito();
                                        if (opElegida != 3) {
                                            ct.searchDataFromTablaCircuito(opElegida);
                                        }
                                        break;
                                    case 4:
                                        showConsultasMultiTabla(ct);
                                        break;
                                    case 5:
                                        System.out.println("Saliendo de elección de tabla..");
                                        break;
                                }
                                break;
                            case 6:
                                System.out.println("Saliendo de consultas XPath - XQuery..");
                                break;
                        }

                    } while (opcionConsultas != 6);
                    break;
                case 3:
                    System.out.println("Fin.");
                    break;
            }
        } while (opcion != 3);
    }

    private static void showMenuPpal() {
        System.out.println("............................................................\n"
                + ".  1 Generar documentos XML. \n"
                + ".  2 Realizar consultas XPath - XQuery.  \n"
                + ".  3 SALIR.\n"
                + "............................................................\n");
    }

    private static void showMenuConsultas() {
        System.out.println("............................................................\n"
                + ".  1 Insertar datos. \n"
                + ".  2 Modificar datos.  \n"
                + ".  3 Eliminar datos.\n"
                + ".  4 Visualizar datos.\n"
                + ".  5 Búsqueda datos.\n"
                + ".  6 SALIR.\n"
                + "............................................................\n");
    }

    private static void showTablesToOperate() {
        System.out.println("............................................................\n"
                + ".  1 Tabla Piloto. \n"
                + ".  2 Tabla Moto.  \n"
                + ".  3 Tabla Circuito.\n"
                + ".  4 Varias Tablas.\n"
                + ".  5 SALIR.\n"
                + "............................................................\n");
    }

    private static void showConsultasMultiTabla(ConsultasTablas ct) throws IOException {
        System.out.println("............................................................\n"
                + ".  1 Equipo con piloto con más victorias. \n"
                + ".  2 Equipo con piloto con más podios.  \n"
                + ".  3 SALIR.\n"
                + "............................................................\n");

        int opcionMultiTabla = 0;
        System.out.print("¿Opción?: ");
        opcionMultiTabla = Integer.parseInt(br.readLine());

        switch (opcionMultiTabla) {
            case 1:
                ct.searchDataFromMultiTable(1);
                break;
            case 2:
                ct.searchDataFromMultiTable(2);
                break;
            case 3:
                System.out.println("Saliendo de búsqueda multitabla..");
                ;
                break;
        }
    }

    private static void generarDatosTablaPiloto() {
        Piloto p = new Piloto();
        p.setCODPILOTO(1);
        p.setEDAD(30);
        p.setNOMBRE("Ivan");
        p.setNUMVICTORIAS(0);
        p.setNUMPODIOS(0);
        p.setNUMCAMPEONATOS(0);
        pilotos.add(p);

        Piloto p2 = new Piloto();
        p2.setCODPILOTO(2);
        p2.setEDAD(22);
        p2.setNOMBRE("Pedro");
        p2.setNUMVICTORIAS(2);
        p2.setNUMPODIOS(4);
        p2.setNUMCAMPEONATOS(0);
        pilotos.add(p2);

        Piloto p3 = new Piloto();
        p3.setCODPILOTO(3);
        p3.setEDAD(19);
        p3.setNOMBRE("Oscar");
        p3.setNUMVICTORIAS(0);
        p3.setNUMPODIOS(6);
        p3.setNUMCAMPEONATOS(0);
        pilotos.add(p3);

        Piloto p4 = new Piloto();
        p4.setCODPILOTO(4);
        p4.setEDAD(26);
        p4.setNOMBRE("Luis");
        p4.setNUMVICTORIAS(1);
        p4.setNUMPODIOS(1);
        p4.setNUMCAMPEONATOS(0);
        pilotos.add(p4);

        Piloto p5 = new Piloto();
        p5.setCODPILOTO(5);
        p5.setEDAD(22);
        p5.setNOMBRE("Andoni");
        p5.setNUMVICTORIAS(7);
        p5.setNUMPODIOS(7);
        p5.setNUMCAMPEONATOS(1);
        pilotos.add(p5);
    }

    private static void generarDatosTablaMoto() {
        Moto m = new Moto();
        m.setCODMOTO(1);
        m.setMARCA("HONDA");
        m.setMODELO("CBR600F2");
        m.setCILINDRADA(599);
        m.setEQUIPO("HENLEY");
        m.setCODPILOTO(pilotos.get(0).getCODPILOTO());
        motos.add(m);

        Moto m2 = new Moto();
        m2.setCODMOTO(2);
        m2.setMARCA("HONDA");
        m2.setMODELO("CBR600F2");
        m2.setCILINDRADA(599);
        m2.setEQUIPO("HENLEY");
        m2.setCODPILOTO(pilotos.get(1).getCODPILOTO());
        motos.add(m2);

        Moto m3 = new Moto();
        m3.setCODMOTO(3);
        m3.setMARCA("KAWASAKI");
        m3.setMODELO("ZZR600");
        m3.setCILINDRADA(599);
        m3.setEQUIPO("KRT");
        m3.setCODPILOTO(pilotos.get(2).getCODPILOTO());
        motos.add(m3);

        Moto m4 = new Moto();
        m4.setCODMOTO(4);
        m4.setMARCA("YAMAHA");
        m4.setMODELO("YZF600");
        m4.setCILINDRADA(599);
        m4.setEQUIPO("PETRONAS");
        m4.setCODPILOTO(pilotos.get(3).getCODPILOTO());
        motos.add(m4);

        Moto m5 = new Moto();
        m5.setCODMOTO(5);
        m5.setMARCA("YAMAHA");
        m5.setMODELO("YZF600");
        m5.setCILINDRADA(599);
        m5.setEQUIPO("PETRONAS");
        m5.setCODPILOTO(pilotos.get(4).getCODPILOTO());
        motos.add(m5);
    }

    private static void generarDatosTablaCircuito() {
        Circuito c = new Circuito();
        c.setCODCIRCUITO(1);
        c.setLONGTOT(3256);
        c.setCURVTOT(16);
        c.setCODMOTO(motos.get(0).getCODMOTO());
        circuitos.add(c);

        Circuito c2 = new Circuito();
        c2.setCODCIRCUITO(2);
        c2.setLONGTOT(2589);
        c2.setCURVTOT(13);
        c2.setCODMOTO(motos.get(0).getCODMOTO());
        circuitos.add(c2);

        Circuito c3 = new Circuito();
        c3.setCODCIRCUITO(3);
        c3.setLONGTOT(4598);
        c3.setCURVTOT(9);
        c3.setCODMOTO(motos.get(1).getCODMOTO());
        circuitos.add(c3);

        Circuito c4 = new Circuito();
        c4.setCODCIRCUITO(4);
        c4.setLONGTOT(3412);
        c4.setCURVTOT(8);
        c4.setCODMOTO(motos.get(1).getCODMOTO());
        circuitos.add(c4);

        Circuito c5 = new Circuito();
        c5.setCODCIRCUITO(5);
        c5.setLONGTOT(5689);
        c5.setCURVTOT(17);
        c5.setCODMOTO(motos.get(2).getCODMOTO());
        circuitos.add(c5);

        Circuito c6 = new Circuito();
        c6.setCODCIRCUITO(6);
        c6.setLONGTOT(1985);
        c6.setCURVTOT(11);
        c6.setCODMOTO(motos.get(2).getCODMOTO());
        circuitos.add(c6);

    }

    @NotNull
    private static Piloto obtenerDatosPiloto(int tipoConsulta) throws IOException {
        Piloto piloto = new Piloto();
        switch (tipoConsulta) {
            case 1: //insertar
                System.out.println("-Introduzca datos Piloto-");

                System.out.print("¿CODPILOTO?:");
                piloto.setCODPILOTO(Integer.parseInt(br.readLine()));

                System.out.print("¿NOMBRE?:");
                piloto.setNOMBRE(br.readLine());

                System.out.print("¿EDAD?:");
                piloto.setEDAD(Integer.parseInt(br.readLine()));

                System.out.print("¿NUMVICTORIAS?:");
                piloto.setNUMVICTORIAS(Integer.parseInt(br.readLine()));

                System.out.print("¿NUMPODIOS?:");
                piloto.setNUMPODIOS(Integer.parseInt(br.readLine()));

                System.out.print("¿NUMCAPEONATOS?:");
                piloto.setNUMCAMPEONATOS(Integer.parseInt(br.readLine()));
                return piloto;

            case 2: //modificar
                System.out.println("-Introduzca datos Piloto-");

                System.out.print("¿CODPILOTO?:");
                piloto.setCODPILOTO(Integer.parseInt(br.readLine()));

                int opcion;
                System.out.println("¿Campos a modificar?");
                System.out.println("1 - NUMVICTORIAS + NUMPODIOS . \n" +
                        "2 - NUMPODIOS. \n" +
                        "3 - NUMCAPEONATOS");
                System.out.print("¿Opción?: ");
                opcion = Integer.parseInt(br.readLine());

                switch (opcion) {
                    case 1:
                        System.out.print("¿NUMVICTORIAS?:");
                        piloto.setNUMVICTORIAS(Integer.parseInt(br.readLine()));

                        System.out.print("¿NUMPODIOS?:");
                        piloto.setNUMPODIOS(Integer.parseInt(br.readLine()));
                        return piloto;
                    case 2:
                        System.out.print("¿NUMPODIOS?:");
                        piloto.setNUMPODIOS(Integer.parseInt(br.readLine()));
                        return piloto;
                    case 3:
                        System.out.print("¿NUMCAPEONATOS?:");
                        piloto.setNUMCAMPEONATOS(Integer.parseInt(br.readLine()));
                        return piloto;
                }
                return piloto;

            case 3: //eliminar
                System.out.println("-Introduzca datos Piloto-");
                System.out.print("¿CODPILOTO?:");
                piloto.setCODPILOTO(Integer.parseInt(br.readLine()));
                return piloto;
        }
        return piloto;
    }

    @NotNull
    private static Moto obtenerDatosMoto(int tipoConsulta) throws IOException {
        Moto moto = new Moto();
        switch (tipoConsulta) {
            case 1: //insertar
                System.out.println("-Introduzca datos Moto-");

                System.out.print("¿CODMOTO?:");
                moto.setCODMOTO(Integer.parseInt(br.readLine()));

                System.out.print("¿MARCA?:");
                moto.setMARCA(br.readLine());

                System.out.print("¿MODELO?:");
                moto.setMODELO(br.readLine());

                System.out.print("¿CILINDRADA?:");
                moto.setCILINDRADA(Integer.parseInt(br.readLine()));

                System.out.print("¿EQUIPO?:");
                moto.setEQUIPO(br.readLine());

                System.out.print("¿CODPILOTO?:");
                moto.setCODPILOTO(Integer.parseInt(br.readLine()));
                return moto;

            case 2: //modificar
                System.out.println("-Introduzca datos Moto-");

                System.out.print("¿CODMOTO?:");
                moto.setCODMOTO(Integer.parseInt(br.readLine()));

                int opcion;
                System.out.println("¿Campos a modificar?");
                System.out.println("1 - MARCA + MODELO . \n" +
                        "2 - MODELO. \n" +
                        "3 - CODPILOTO");
                System.out.print("¿Opción?: ");
                opcion = Integer.parseInt(br.readLine());

                switch (opcion) {
                    case 1:
                        System.out.print("¿MARCA?:");
                        moto.setMARCA(br.readLine());

                        System.out.print("¿MODELO?:");
                        moto.setMODELO(br.readLine());
                        return moto;
                    case 2:
                        System.out.print("¿MODELO?:");
                        moto.setMODELO(br.readLine());
                        return moto;
                    case 3:
                        System.out.print("¿CODPILOTO?:");
                        moto.setCODPILOTO(Integer.parseInt(br.readLine()));
                        return moto;
                }
                return moto;

            case 3: //eliminar
                System.out.println("-Introduzca datos Moto-");
                System.out.print("¿CODMOTO?:");
                moto.setCODMOTO(Integer.parseInt(br.readLine()));
                return moto;
        }
        return moto;
    }

    @NotNull
    private static Circuito obtenerDatosCircuito(int tipoConsulta) throws IOException {
        Circuito circuito = new Circuito();
        switch (tipoConsulta) {
            case 1: //insertar
                System.out.println("-Introduzca datos Circuito-");

                System.out.print("¿CODCIRCUITO?:");
                circuito.setCODCIRCUITO(Integer.parseInt(br.readLine()));

                System.out.print("¿LONGTOT?:");
                circuito.setLONGTOT(Integer.parseInt(br.readLine()));

                System.out.print("¿CURVTOT?:");
                circuito.setCURVTOT(Integer.parseInt(br.readLine()));

                System.out.print("¿CODMOTO?:");
                circuito.setCODMOTO(Integer.parseInt(br.readLine()));
                return circuito;

            case 2: //modificar
                System.out.println("-Introduzca datos Circuito-");

                System.out.print("¿CODCIRCUITO?:");
                circuito.setCODCIRCUITO(Integer.parseInt(br.readLine()));

                System.out.print("¿LONGTOT?:");
                circuito.setLONGTOT(Integer.parseInt(br.readLine()));

                System.out.print("¿CURVTOT?:");
                circuito.setCURVTOT(Integer.parseInt(br.readLine()));
                return circuito;

            case 3: //eliminar
                System.out.println("-Introduzca datos Circuito-");
                System.out.print("¿CODCIRCUITO?:");
                circuito.setCODCIRCUITO(Integer.parseInt(br.readLine()));
                return circuito;
        }
        return circuito;
    }

    private static int obtenerOpcionBusquedaTablaPiloto() throws IOException {
        int opcion = 0;
        System.out.println("............................................................\n"
                + ".  1 Piloto con mayor número victorias. \n"
                + ".  2 Piloto con mayor número podios.  \n"
                + ".  3 SALIR.\n"
                + "............................................................\n");
        System.out.print("¿Opción?: ");
        opcion = Integer.parseInt(br.readLine());

        switch (opcion) {
            case 1:
                return opcion = 1;
            case 2:
                return opcion = 2;
            case 3:
                System.out.println("Saliendo de búsqueda tabla piloto..");
        }
        return 3;
    }

    private static int obtenerOpcionBusquedaTablaMoto() throws IOException {
        int opcion = 0;
        System.out.println("............................................................\n"
                + ".  1 Equipo con más motos. \n"
                + ".  2 Marca con más motos.  \n"
                + ".  3 SALIR.\n"
                + "............................................................\n");
        System.out.print("¿Opción?: ");
        opcion = Integer.parseInt(br.readLine());

        switch (opcion) {
            case 1:
                return opcion = 1;
            case 2:
                return opcion = 2;
            case 3:
                System.out.println("Saliendo de búsqueda tabla moto..");
        }
        return 3;
    }

    private static int obtenerOpcionBusquedaTablaCircuito() throws IOException {
        int opcion = 0;
        System.out.println("............................................................\n"
                + ".  1 Circuito con mayor longitud. \n"
                + ".  2 Circuito con mas curvas.  \n"
                + ".  3 SALIR.\n"
                + "............................................................\n");
        System.out.print("¿Opción?: ");
        opcion = Integer.parseInt(br.readLine());

        switch (opcion) {
            case 1:
                return opcion = 1;
            case 2:
                return opcion = 2;
            case 3:
                System.out.println("Saliendo de búsqueda tabla circuito..");
        }
        return 3;
    }
}
