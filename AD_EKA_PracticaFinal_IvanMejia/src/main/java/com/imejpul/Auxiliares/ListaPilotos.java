package com.imejpul.Auxiliares;

import com.imejpul.Serializables.Piloto;

import java.util.ArrayList;
import java.util.List;

public class ListaPilotos {

    private List<Piloto> pilotos = new ArrayList<Piloto>();

    public ListaPilotos(List<Piloto> pilotos) {
        this.pilotos = pilotos;
    }

    public ListaPilotos() {
    }

    public List<Piloto> getPilotos() {
        return pilotos;
    }

    public void addResultadoALista(Piloto piloto) {
        pilotos.add(piloto);
    }
}
