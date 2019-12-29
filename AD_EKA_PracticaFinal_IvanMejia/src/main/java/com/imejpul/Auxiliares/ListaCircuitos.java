package com.imejpul.Auxiliares;

import com.imejpul.Serializables.Circuito;

import java.util.ArrayList;
import java.util.List;

public class ListaCircuitos {

    private List<Circuito> circuitos = new ArrayList<Circuito>();

    public ListaCircuitos(List<Circuito> circuitos) {
        this.circuitos = circuitos;
    }

    public ListaCircuitos() {
    }

    public List<Circuito> getCircuitos() {
        return circuitos;
    }

    public void addResultadoALista(Circuito circuito) {
        circuitos.add(circuito);
    }
}
