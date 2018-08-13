/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tesis;

import java.util.ArrayList;

/**
 *
 * @author gtroncone
 */
public class Metadata {
    
    private final ArrayList<int[]> zooms;
    private final int maxZoom;
    private final int minZoom;
    private final int dimX;
    private final int dimY;
    
    public Metadata() {
        maxZoom = 18;
        minZoom = 12;
        dimX = 256;
        dimY = 256;
        zooms = new ArrayList<>();
        int[] zoom12 = new int[4];
        int[] zoom13 = new int[4];
        int[] zoom14 = new int[4];
        int[] zoom15 = new int[4];
        int[] zoom16 = new int[4];
        int[] zoom17 = new int[4];
        int[] zoom18 = new int[4];
        
        /*
            Indice 0: representa la baseX
            Indice 1: representa la baseY
            Indice 2: representa numX
            Indice 3: representa numY
        */
        
        zoom12[0] = 1332;
        zoom12[1] = 1952;
        zoom12[2] = 4;
        zoom12[3] = 3;
        zooms.add(zoom12);
        
        zoom13[0] = 2665;
        zoom13[1] = 3905;
        zoom13[2] = 7;
        zoom13[3] = 4;
        zooms.add(zoom13);
        
        zoom14[0] = 5330;
        zoom14[1] = 7810;
        zoom14[2] = 14;
        zoom14[3] = 8;
        zooms.add(zoom14);
        
        zoom15[0] = 10661;
        zoom15[1] = 15620;
        zoom15[2] = 26;
        zoom15[3] = 15;
        zooms.add(zoom15);
        
        zoom16[0] = 21323;
        zoom16[1] = 31240;
        zoom16[2] = 51;
        zoom16[3] = 30;
        zooms.add(zoom16);
        
        zoom17[0] = 42646;
        zoom17[1] = 62480;
        zoom17[2] = 102;
        zoom17[3] = 59;
        zooms.add(zoom17);
        
        zoom18[0] = 85293;
        zoom18[1] = 124960;
        zoom18[2] = 203;
        zoom18[3] = 117;
        zooms.add(zoom18);
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }
    
    public int getDato(int zoom, String llave) {
        int indiceArray;
        int indiceZoom;
        
        switch (zoom) {
            case 12:
                indiceArray = 0;
                break;
            case 13:
                indiceArray = 1;
                break;
            case 14:
                indiceArray = 2;
                break;
            case 15:
                indiceArray = 3;
                break;
            case 16:
                indiceArray = 4;
                break;
            case 17:
                indiceArray = 5;
                break;
            case 18:
                indiceArray = 6;
                break;
            default:
                return -1;
        }
        
        switch (llave) {
            case "baseX":
                indiceZoom = 0;
                break;
            case "baseY":
                indiceZoom = 1;
                break;
            case "numX":
                indiceZoom = 2;
                break;
            case "numY":
                indiceZoom = 3;
                break;
            default:
                return -1;
        }
        
        return zooms.get(indiceArray)[indiceZoom];
    }
    
}
