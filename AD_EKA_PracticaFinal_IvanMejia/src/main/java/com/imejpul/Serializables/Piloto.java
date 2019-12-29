package com.imejpul.Serializables;

import java.io.Serializable;

public class Piloto implements Serializable {

    private int CODPILOTO;
    private String NOMBRE;
    private int EDAD;
    private int NUMVICTORIAS;
    private int NUMPODIOS;
    private int NUMCAMPEONATOS;

    public Piloto(int CODPILOTO, String NOMBRE, int EDAD, int NUMVICTORIAS, int NUMPODIOS, int NUMCAMPEONATOS) {
        this.CODPILOTO = CODPILOTO;
        this.NOMBRE = NOMBRE;
        this.EDAD = EDAD;
        this.NUMVICTORIAS = NUMVICTORIAS;
        this.NUMPODIOS = NUMPODIOS;
        this.NUMCAMPEONATOS = NUMCAMPEONATOS;
    }

    public Piloto() {
    }

    public int getCODPILOTO() {
        return CODPILOTO;
    }

    public void setCODPILOTO(int CODPILOTO) {
        this.CODPILOTO = CODPILOTO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public int getEDAD() {
        return EDAD;
    }

    public void setEDAD(int EDAD) {
        this.EDAD = EDAD;
    }

    public int getNUMVICTORIAS() {
        return NUMVICTORIAS;
    }

    public void setNUMVICTORIAS(int NUMVICTORIAS) {
        this.NUMVICTORIAS = NUMVICTORIAS;
    }

    public int getNUMPODIOS() {
        return NUMPODIOS;
    }

    public void setNUMPODIOS(int NUMPODIOS) {
        this.NUMPODIOS = NUMPODIOS;
    }

    public int getNUMCAMPEONATOS() {
        return NUMCAMPEONATOS;
    }

    public void setNUMCAMPEONATOS(int NUMCAMPEONATOS) {
        this.NUMCAMPEONATOS = NUMCAMPEONATOS;
    }
}
