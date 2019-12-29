package com.imejpul.Serializables;

import java.io.Serializable;

public class Moto implements Serializable {

    private int CODMOTO;
    private String MARCA;
    private String MODELO;
    private int CILINDRADA;
    private String EQUIPO;
    private int CODPILOTO;

    public Moto(int CODMOTO, String MARCA, String MODELO, int CILINDRADA, String EQUIPO, int CODPILOTO) {
        this.CODMOTO = CODMOTO;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.CILINDRADA = CILINDRADA;
        this.EQUIPO = EQUIPO;
        this.CODPILOTO = CODPILOTO;
    }

    public Moto() {
    }

    public int getCODMOTO() {
        return CODMOTO;
    }

    public void setCODMOTO(int CODMOTO) {
        this.CODMOTO = CODMOTO;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public int getCILINDRADA() {
        return CILINDRADA;
    }

    public void setCILINDRADA(int CILINDRADA) {
        this.CILINDRADA = CILINDRADA;
    }

    public String getEQUIPO() {
        return EQUIPO;
    }

    public void setEQUIPO(String EQUIPO) {
        this.EQUIPO = EQUIPO;
    }

    public int getCODPILOTO() {
        return CODPILOTO;
    }

    public void setCODPILOTO(int CODPILOTO) {
        this.CODPILOTO = CODPILOTO;
    }
}
