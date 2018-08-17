/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tesis;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author gtroncone
 */
public class CentroDeAcumulacion extends Polygon {
    
    private int[] contexto;
    
    public CentroDeAcumulacion(int[] puntosX, int[] puntosY, int zoom, int x, int y) {
        super(puntosX, puntosY, puntosX.length);
        contexto = new int[3];
        contexto[0] = zoom;
        contexto[1] = x;
        contexto[2] = y;
    }

    public int[] getContexto() {
        return contexto;
    }

    public int getNpoints() {
        return npoints;
    }

    public int[] getXpoints() {
        return xpoints;
    }

    public int[] getYpoints() {
        return ypoints;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    
    
    
}
