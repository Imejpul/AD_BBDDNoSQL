package com.imejpul.Auxiliares;

import com.imejpul.Serializables.Moto;

import java.util.ArrayList;
import java.util.List;

public class ListaMotos {

    private List<Moto> motos = new ArrayList<Moto>();

    public ListaMotos(List<Moto> motos) {
        this.motos = motos;
    }

    public ListaMotos() {
    }

    public List<Moto> getMotos() {
        return motos;
    }

    public void addResultadoALista(Moto moto) {
        motos.add(moto);
    }
}
