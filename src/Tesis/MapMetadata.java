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
public class MapMetadata {
    
    private final ArrayList<int[]> zooms;
    private final int maxZoom;
    private final int minZoom;
    private final int dimX;
    private final int dimY;
    
    public MapMetadata() throws NullPointerException {
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
        zoom12[2] = 5;
        zoom12[3] = 4;
        zooms.add(zoom12);
        
        zoom13[0] = 2664;
        zoom13[1] = 3904;
        zoom13[2] = 10;
        zoom13[3] = 8;
        zooms.add(zoom13);
        
        zoom14[0] = 5328;
        zoom14[1] = 7808;
        zoom14[2] = 20;
        zoom14[3] = 16;
        zooms.add(zoom14);
        
        zoom15[0] = 10656;
        zoom15[1] = 15616;
        zoom15[2] = 40;
        zoom15[3] = 32;
        zooms.add(zoom15);
        
        zoom16[0] = 21312;
        zoom16[1] = 31232;
        zoom16[2] = 80;
        zoom16[3] = 64;
        zooms.add(zoom16);
        
        zoom17[0] = 42624;
        zoom17[1] = 62464;
        zoom17[2] = 160;
        zoom17[3] = 128;
        zooms.add(zoom17);
        
        zoom18[0] = 85248;
        zoom18[1] = 124928;
        zoom18[2] = 320;
        zoom18[3] = 256;
        zooms.add(zoom18);
        
        if (!verificacionDeMetadatos()) {
            throw new NullPointerException();
        }
    }
    
    private boolean verificacionDeMetadatos() {
        if (maxZoom < minZoom || dimX != dimY) {
            return false;
        } else {
            for (int i = 0; i < zooms.size() - 1; i++) {
                for (int j = 0; j < zooms.get(0).length; j++) {
                    if (2 * zooms.get(i)[j] != zooms.get(i + 1)[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
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
