/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tesis;

import java.util.LinkedList;

/**
 *
 * @author gtroncone
 */
public class Simulacion {
    
    private static LinkedList<CentroDeAcumulacion> centrosDeAcum;
    
    public Simulacion() {
        centrosDeAcum = new LinkedList();
    }
    
    public void añadirCentro(CentroDeAcumulacion centro) {
        centrosDeAcum.add(centro);
    }

    public LinkedList<CentroDeAcumulacion> getCentrosDeAcum() {
        return centrosDeAcum;
    }
    
}
