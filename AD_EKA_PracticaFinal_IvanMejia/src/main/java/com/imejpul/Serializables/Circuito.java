package com.imejpul.Serializables;

import java.io.Serializable;

public class Circuito implements Serializable {

    private int CODCIRCUITO;
    private int LONGTOT;
    private int CURVTOT;
    private int CODMOTO;

    public Circuito(int CODCIRCUITO, int LONGTOT, int CURVTOT, int CODMOTO) {
        this.CODCIRCUITO = CODCIRCUITO;
        this.LONGTOT = LONGTOT;
        this.CURVTOT = CURVTOT;
        this.CODMOTO = CODMOTO;
    }

    public Circuito() {
    }

    public int getCODCIRCUITO() {
        return CODCIRCUITO;
    }

    public void setCODCIRCUITO(int CODCIRCUITO) {
        this.CODCIRCUITO = CODCIRCUITO;
    }

    public int getLONGTOT() {
        return LONGTOT;
    }

    public void setLONGTOT(int LONGTOT) {
        this.LONGTOT = LONGTOT;
    }

    public int getCURVTOT() {
        return CURVTOT;
    }

    public void setCURVTOT(int CURVTOT) {
        this.CURVTOT = CURVTOT;
    }

    public int getCODMOTO() {
        return CODMOTO;
    }

    public void setCODMOTO(int CODMOTO) {
        this.CODMOTO = CODMOTO;
    }
}
