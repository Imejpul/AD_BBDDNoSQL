package com.imejpul;

import com.imejpul.ManejoFicheros.GenerarArchivoConsultas;
import com.imejpul.Serializables.Circuito;
import com.imejpul.Serializables.Moto;
import com.imejpul.Serializables.Piloto;
import org.jetbrains.annotations.NotNull;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

public class ConsultasTablas {

    private Collection coll;

    private void obtenerColeccion() {
        final String driver = "org.exist.xmldb.DatabaseImpl";
        final String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/";
        try {
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);

            coll = DatabaseManager.getCollection(URI + "Competicion", "admin", "admin");

        } catch (XMLDBException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public boolean insertTablaPiloto(@NotNull Piloto p) {
        obtenerColeccion();
        String nuevoPiloto = "<Piloto><CODPILOTO>" + p.getCODPILOTO() + "</CODPILOTO>"
                + "<NOMBRE>" + p.getNOMBRE() + "</NOMBRE><EDAD>" + p.getEDAD() + "</EDAD>"
                + "<NUMVICTORIAS>" + p.getNUMVICTORIAS() + "</NUMVICTORIAS><NUMPODIOS>" + p.getNUMPODIOS() + "</NUMPODIOS>"
                + "<NUMCAMPEONATOS>" + p.getNUMCAMPEONATOS() + "</NUMCAMPEONATOS>"
                + "</Piloto>";
        try {
            if (coll != null) {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                System.out.printf("Insertando: %s \n", nuevoPiloto);
                ResourceSet result = servicio.query("update insert " + nuevoPiloto + " into /ListaPilotos/pilotos");
                GenerarArchivoConsultas.generarArchivoConsultas("update insert " + nuevoPiloto + " into /ListaPilotos/pilotos");
                coll.close(); //borramos
                System.out.println("¡Insertado!");
                return true;
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTablaPiloto(Piloto p) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(p)) {

            String query = "";
            String query2 = "";

            if (p.getNUMVICTORIAS() != 0 && p.getNUMPODIOS() != 0) {
                query = "update value\n" +
                        "/ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]/NUMVICTORIAS\n" +
                        "with data('" + p.getNUMVICTORIAS() + "')";
                query2 = "update value\n" +
                        "/ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]/NUMPODIOS\n" +
                        " with data('" + p.getNUMPODIOS() + "')";

            } else if (p.getNUMPODIOS() != 0) {
                query = "update value /ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]/NUMPODIOS with data('" + p.getNUMPODIOS() + "')";

            } else if (p.getNUMCAMPEONATOS() != 0) {
                query = "update value /ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]/NUMCAMPEONATOS with data('" + p.getNUMCAMPEONATOS() + "')";
            }

            try {
                if (coll != null && !query.equals("")) {
                    System.out.printf("Actualizando Piloto: %s\n", query);
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(query);
                    GenerarArchivoConsultas.generarArchivoConsultas(query);

                    if (!query2.equals("")) {
                        System.out.printf("Actualizando Piloto: %s\n", query2);
                        result = servicio.query(query2);
                        GenerarArchivoConsultas.generarArchivoConsultas(query2);
                    }

                    coll.close();
                    System.out.println("¡Actualizado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean deleteTablaPiloto(Piloto p) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(p)) {
            try {
                if (coll != null) {
                    System.out.printf("Borrando Piloto: %s\n", p.getCODPILOTO());
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(
                            "update delete /ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]");
                    GenerarArchivoConsultas.generarArchivoConsultas("update delete /ListaPilotos/pilotos/Piloto[CODPILOTO=" + p.getCODPILOTO() + "]");
                    coll.close();
                    System.out.println("¡Borrado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void viewDataFromTablaPiloto() {
        try {
            xmldbOperations.RetrieveResource("Competicion", "Pilotos.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void searchDataFromTablaPiloto(int opcion) {
        obtenerColeccion();
        System.out.println("Searching on Piloto.. ");
        //Piloto con mayor número victorias y sus datos
        //Piloto con mayor número podios y sus datos

        String query = "";
        if (opcion == 1) {
            query = "for $piloto in /ListaPilotos/pilotos/Piloto\n" +
                    "let $vic := $piloto/NUMVICTORIAS\n" +
                    "let $maxVic := max(/ListaPilotos/pilotos/Piloto/NUMVICTORIAS)\n" +
                    "return \n" +
                    "if ($vic=max(/ListaPilotos/pilotos/Piloto/NUMVICTORIAS)) then\n" +
                    "/ListaPilotos/pilotos/Piloto[NUMVICTORIAS=$maxVic]\n" +
                    "else()";
        } else if (opcion == 2) {
            query = "for $piloto in /ListaPilotos/pilotos/Piloto\n" +
                    "let $pod := $piloto/NUMPODIOS\n" +
                    "let $maxPod := max(/ListaPilotos/pilotos/Piloto/NUMPODIOS)\n" +
                    "return \n" +
                    "if ($pod=max(/ListaPilotos/pilotos/Piloto/NUMPODIOS)) then\n" +
                    "/ListaPilotos/pilotos/Piloto[NUMPODIOS=$maxPod]\n" +
                    "else()";
        }

        if (!query.equalsIgnoreCase("")) {
            try {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(query);
                GenerarArchivoConsultas.generarArchivoConsultas(query);
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                    coll.close();
                } else {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                    coll.close();
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("¡Consulta no realizada!");
        }
    }

    //-----------------------------------

    public boolean insertTablaMoto(@NotNull Moto m) {
        obtenerColeccion();
        String nuevaMoto = "<Moto><CODMOTO>" + m.getCODMOTO() + "</CODMOTO>"
                + "<MARCA>" + m.getMARCA() + "</MARCA><MODELO>" + m.getMODELO() + "</MODELO>"
                + "<CILINDRADA>" + m.getCILINDRADA() + "</CILINDRADA><EQUIPO>" + m.getEQUIPO() + "</EQUIPO>"
                + "<CODPILOTO>" + m.getCODPILOTO() + "</CODPILOTO>"
                + "</Moto>";
        try {
            if (coll != null) {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                System.out.printf("Insertando: %s \n", nuevaMoto);
                ResourceSet result = servicio.query("update insert " + nuevaMoto + " into /ListaMotos/motos");
                GenerarArchivoConsultas.generarArchivoConsultas("update insert " + nuevaMoto + " into /ListaMotos/motos");
                coll.close(); //borramos
                System.out.println("¡Insertado!");
                return true;
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTablaMoto(Moto m) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(m)) {

            String query = "";
            String query2 = "";

            if (m.getMARCA() != null && m.getMODELO() != null) {
                query = "update value\n" +
                        "/ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]/MARCA\n" +
                        "with data('" + m.getMARCA() + "')";
                query2 = "update value\n" +
                        "/ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]/MODELO\n" +
                        " with data('" + m.getMODELO() + "')";

            } else if (m.getMODELO() != null) {
                query = "update value /ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]/MODELO with data('" + m.getMODELO() + "')";

            } else if (m.getCODPILOTO() != 0) {
                query = "update value /ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]/CODPILOTO with data('" + m.getCODPILOTO() + "')";
            }

            try {
                if (coll != null && !query.equals("")) {
                    System.out.printf("Actualizando Moto: %s\n", query);
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(query);
                    GenerarArchivoConsultas.generarArchivoConsultas(query);

                    if (!query2.equals("")) {
                        System.out.printf("Actualizando Moto: %s\n", query2);
                        result = servicio.query(query2);
                        GenerarArchivoConsultas.generarArchivoConsultas(query2);
                    }

                    coll.close();
                    System.out.println("¡Actualizado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean deleteTablaMoto(Moto m) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(m)) {
            try {
                if (coll != null) {
                    System.out.printf("Borrando Moto: %s\n", m.getCODMOTO());
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(
                            "update delete /ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]");
                    GenerarArchivoConsultas.generarArchivoConsultas("update delete /ListaMotos/motos/Moto[CODMOTO=" + m.getCODMOTO() + "]");
                    coll.close();
                    System.out.println("¡Borrado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void viewDataFromTablaMoto() {
        try {
            xmldbOperations.RetrieveResource("Competicion", "Motos.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void searchDataFromTablaMoto(int opcion) {
        obtenerColeccion();
        System.out.println("Searching on Moto.. ");
        //equipo con más motos
        //marca con más motos

        String query = "";
        if (opcion == 1) {
            query = "(for $eqs in distinct-values(/ListaMotos/motos/Moto/EQUIPO)\n" +
                    "let $motos := /ListaMotos/motos/Moto[EQUIPO= $eqs]\n" +
                    "order by count($motos) descending\n" +
                    "return $motos)[1]";
        } else if (opcion == 2) {
            query = "(for $eqs in distinct-values(/ListaMotos/motos/Moto/MARCA)\n" +
                    "let $motos := /ListaMotos/motos/Moto[MARCA= $eqs]\n" +
                    "order by count($motos) descending\n" +
                    "return $motos)";
        }

        if (!query.equalsIgnoreCase("")) {
            try {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(query);
                GenerarArchivoConsultas.generarArchivoConsultas(query);
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                    coll.close();
                } else {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                    coll.close();
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("¡Consulta no realizada!");
        }
    }

    //-----------------------------------

    public boolean insertTablaCircuito(@NotNull Circuito c) {
        obtenerColeccion();
        String nuevaMoto = "<Circuito><CODCIRCUITO>" + c.getCODCIRCUITO() + "</CODCIRCUITO>"
                + "<LONGTOT>" + c.getLONGTOT() + "</LONGTOT><CURVTOT>" + c.getCURVTOT() + "</CURVTOT>"
                + "<CODMOTO>" + c.getCODMOTO() + "</CODMOTO></Circuito>";
        try {
            if (coll != null) {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                System.out.printf("Insertando: %s \n", nuevaMoto);
                ResourceSet result = servicio.query("update insert " + nuevaMoto + " into /ListaCircuitos/circuitos");
                GenerarArchivoConsultas.generarArchivoConsultas("update insert " + nuevaMoto + " into /ListaCircuitos/circuitos");
                coll.close(); //borramos
                System.out.println("¡Insertado!");
                return true;
            }

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTablaCircuito(Circuito c) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(c)) {
            String query = "";
            String query2 = "";

            if (c.getLONGTOT() != 0 && c.getCURVTOT() != 0) {
                query = "update value\n" +
                        "/ListaCircuitos/circuitos/Circuito[CODCIRCUITO=" + c.getCODCIRCUITO() + "]/LONGTOT\n" +
                        "with data('" + c.getLONGTOT() + "')";
                query2 = "update value\n" +
                        "/ListaCircuitos/circuitos/Circuito[CODCIRCUITO=" + c.getCODCIRCUITO() + "]/CURVTOT\n" +
                        " with data('" + c.getCURVTOT() + "')";
            }

            try {
                if (coll != null && !query.equals("")) {
                    System.out.printf("Actualizando Moto: %s\n", query);
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(query);
                    GenerarArchivoConsultas.generarArchivoConsultas(query);

                    System.out.printf("Actualizando Moto: %s\n", query2);
                    result = servicio.query(query2);
                    GenerarArchivoConsultas.generarArchivoConsultas(query2);

                    coll.close();
                    System.out.println("¡Actualizado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean deleteTablaCircuito(Circuito c) {
        obtenerColeccion();
        if (comprobarExsitenciaTupla(c)) {
            try {
                if (coll != null) {
                    System.out.printf("Borrando Circuito: %s\n", c.getCODCIRCUITO());
                    XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(
                            "update delete /ListaCircuitos/circuitos/Circuito[CODCIRCUITO=" + c.getCODCIRCUITO() + "]");
                    GenerarArchivoConsultas.generarArchivoConsultas("update delete /ListaCircuitos/circuitos/Circuito[CODCIRCUITO=" + c.getCODCIRCUITO() + "]");
                    coll.close();
                    System.out.println("¡Borrado!");
                    return true;
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void viewDataFromTablaCircuito() {
        try {
            xmldbOperations.RetrieveResource("Competicion", "Circuitos.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void searchDataFromTablaCircuito(int opcion) {
        obtenerColeccion();
        System.out.println("Searching on Circuito.. ");
        //Circuito con mayor longitud
        //Circuito con mas curvas

        String query = "";
        if (opcion == 1) {
            query = "for $circuito in /ListaCircuitos/circuitos/Circuito\n" +
                    "let $long := $circuito/LONGTOT\n" +
                    "let $maxLong := max(/ListaCircuitos/circuitos/Circuito/LONGTOT)\n" +
                    "return \n" +
                    "if ($long=$maxLong) then\n" +
                    "/ListaCircuitos/circuitos/Circuito[LONGTOT=$maxLong]\n" +
                    "else()";
        } else if (opcion == 2) {
            query = "for $circuito in /ListaCircuitos/circuitos/Circuito\n" +
                    "let $curv := $circuito/CURVTOT\n" +
                    "let $maxCurv := max(/ListaCircuitos/circuitos/Circuito/CURVTOT)\n" +
                    "return \n" +
                    "if ($curv=$maxCurv) then\n" +
                    "/ListaCircuitos/circuitos/Circuito[CURVTOT=$maxCurv]\n" +
                    "else()";
        }

        if (!query.equalsIgnoreCase("")) {
            try {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(query);
                GenerarArchivoConsultas.generarArchivoConsultas(query);
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                    coll.close();
                } else {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                    coll.close();
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("¡Consulta no realizada!");
        }
    }

    //--------------------------------------------------

    private boolean comprobarExsitenciaTupla(Object o) {
        System.out.println("Comprobando existencia de tupla..");
        String row = "";
        String code = "";
        int field = -1;
        String query = "";

        if (o instanceof Piloto) {
            row = "/ListaPilotos/pilotos/Piloto";
            code = "CODPILOTO";
            field = ((Piloto) o).getCODPILOTO();
            query = row + "[" + code + "=" + field + "]";
            //query = "for $piloto in " + row + "[" + code + "=" + field + "] return $piloto";

        } else if (o instanceof Moto) {
            row = "/ListaMotos/motos/Moto";
            code = "CODMOTO";
            field = ((Moto) o).getCODMOTO();
            query = row + "[" + code + "=" + field + "]";

        } else if (o instanceof Circuito) {
            row = "/ListaCircuitos/circuitos/Circuito";
            code = "CODCIRCUITO";
            field = ((Circuito) o).getCODCIRCUITO();
            query = row + "[" + code + "=" + field + "]";
        }
        try {
            XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(query);
            GenerarArchivoConsultas.generarArchivoConsultas(query);
            ResourceIterator i;
            i = result.getIterator();
            if (!i.hasMoreResources()) {
                coll.close();
                return false;
            } else {
                coll.close();
                return true;
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------------------------------------
    public void searchDataFromMultiTable(int opcion) {
        obtenerColeccion();
        System.out.println("Searching on MultiTable.. ");
        //equipo con piloto con más victorias
        //equipo con piloto con más podios

        String query = "";
        if (opcion == 1) {
            query = "for $moto in /ListaMotos/motos/Moto\n" +
                    "let $pilMaxVic := /ListaPilotos/pilotos/Piloto[NUMVICTORIAS=max(/ListaPilotos/pilotos/Piloto/NUMVICTORIAS)]\n" +
                    "return\n" +
                    "if ($moto/CODPILOTO = $pilMaxVic/CODPILOTO) then\n" +
                    "$moto/EQUIPO\n" +
                    "else()";
        } else if (opcion == 2) {
            query = "for $moto in /ListaMotos/motos/Moto\n" +
                    "let $pilMaxPod := /ListaPilotos/pilotos/Piloto[NUMPODIOS=max(/ListaPilotos/pilotos/Piloto/NUMPODIOS)]\n" +
                    "return\n" +
                    "if ($moto/CODPILOTO = $pilMaxPod/CODPILOTO) then\n" +
                    "$moto/EQUIPO\n" +
                    "else()";
        }

        if (!query.equalsIgnoreCase("")) {
            try {
                XPathQueryService servicio = (XPathQueryService) coll.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query(query);
                GenerarArchivoConsultas.generarArchivoConsultas(query);
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                    coll.close();
                } else {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                    coll.close();
                }
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("¡Consulta no realizada!");
        }
    }
}
