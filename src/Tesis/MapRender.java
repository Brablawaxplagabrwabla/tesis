/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tesis;

/**
 *
 * @author gtroncone
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapRender {

    private final int NUM_X;
    private final int NUM_Y;
    private final Metadata metadata;
    
    private BufferedImage[][] images;
    private BufferedImage unida;
    private int zoom;
    private int x;
    private int y;

    public MapRender() {
        this.NUM_X = 3;
        this.NUM_Y = 2;
        this.zoom = 12;
        this.x = 0;
        this.y = 0;
        this.metadata = new Metadata();
    }
    
    private void render() {
        images = new BufferedImage[NUM_X][NUM_Y];
        try {
            for (int i = 0; i < images.length; i++) {
                for (int j = 0; j < images[0].length; j++) {
                    String filepath = this.generaFilepath(i, j);
                    BufferedImage image = ImageIO.read(new File(filepath));
                    images[i][j] = image;
                }
            }
            unirImagenes();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private String generaFilepath(int i, int j) {
        String resultado = "tiles/zoom_" + zoom + "/gm_";
        resultado += (metadata.getDato(zoom, "baseX") + i + this.x) + "_";
        resultado += (metadata.getDato(zoom, "baseY") + j + this.y) + "_";
        resultado += zoom + ".png";
        return resultado;
    }
    
    private void unirImagenes() {
        BufferedImage result = new BufferedImage(NUM_X * metadata.getDimX(), NUM_Y * metadata.getDimY(), BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        
        for(int x = 0; x < images.length; x++) {
            for (int y = 0; y < images[0].length; y++) {
                g.drawImage(images[x][y], metadata.getDimX() * x, metadata.getDimY() * y, null);
            }
        }
        unida = result;
    }

    public BufferedImage getRender() {
        if (this.x + NUM_X <= metadata.getDato(zoom, "numX") && 
                this.y + NUM_Y <= metadata.getDato(zoom, "numY")) {
            this.render();
        } else {
            System.out.println("Esto explotó");
            return null;
        }
        return unida;
    }

    public void aumentarZoom() {
        double factorX = (double) metadata.getDato(zoom + 1, "numX") / (double) metadata.getDato(zoom, "numX");
        double factorY = (double) metadata.getDato(zoom + 1, "numY") / (double) metadata.getDato(zoom, "numY");
        
        double indiceX = (double) this.x + 1.5;
        double indiceY = (double) this.y + 0.5;

        int nuevoX = (int) Math.ceil(factorX * indiceX - 1);
        int nuevoY = (int) Math.ceil(factorY * indiceY - 1);
        
        this.x = nuevoX;
        this.y = nuevoY;
        this.zoom++;
    }
    
    public void disminuirZoom() {
        double factorX = (double) metadata.getDato(zoom - 1, "numX") / (double) metadata.getDato(zoom, "numX");
        double factorY = (double) metadata.getDato(zoom - 1, "numY") / (double) metadata.getDato(zoom, "numY");
        
        double indiceX = (double) this.x + 1;
        double indiceY = (double) this.y + 0.5;
        
        int nuevoX = (int) Math.ceil(factorX * indiceX - 1.5);
        int nuevoY = (int) Math.ceil(factorY * indiceY - 1);
        
        this.x = nuevoX;
        this.y = nuevoY;

        this.zoom--;
        
        while (this.x + NUM_X > metadata.getDato(zoom, "numX")) {
            this.x--;
        }
        
        while (this.y + NUM_Y > metadata.getDato(zoom, "numY")) {
            this.y--;
        }        
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNUM_X() {
        return NUM_X;
    }

    public int getNUM_Y() {
        return NUM_Y;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public int getZoom() {
        return zoom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
