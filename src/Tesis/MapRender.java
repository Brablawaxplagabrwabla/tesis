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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class MapRender {

    private final int NUM_X;
    private final int NUM_Y;
    private final MapMetadata metadata;
    private final int RADIO_PUNTO = 10;
    
    private BufferedImage[][] images;
    private BufferedImage unida;
    private BufferedImage mapa;
    
    private LinkedList<CentroDeAcumulacion> poligonos;
    private LinkedList<Point> puntosDeCarga;
    private LinkedList<Point[]> lineasDeCarga;
    
    private int zoom;
    private int x;
    private int y;

    public MapRender() {
        this.NUM_X = 3;
        this.NUM_Y = 2;
        this.zoom = 12;
        this.x = 0;
        this.y = 0;
        puntosDeCarga = new LinkedList();
        lineasDeCarga = new LinkedList();
        poligonos = new LinkedList();
        try {
            this.metadata = new MapMetadata();            
        } catch (NullPointerException e) {
            throw e;
        }
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
            añadirPoligonos();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private void añadirPoligonos() {
        Graphics2D g2d = unida.createGraphics();
        g2d.setColor(new Color(1f, 0f, 0f, .2f));
        for (int i = 0; i < poligonos.size(); i++) {
            g2d.fillPolygon(poligonos.get(i));
        }
    }
    
    private String generaFilepath(int i, int j) {
        String resultado = "tiles/zoom_" + zoom + "/gm_";
        resultado += (metadata.getDato(zoom, "baseX") + i + this.x) + "_";
        resultado += (metadata.getDato(zoom, "baseY") + j + this.y) + "_";
        resultado += zoom + ".png";
        return resultado;
    }
    
    public Point getCentroid(Point p) {
        if (poligonos.isEmpty()) {
            return null;
        }
        for (CentroDeAcumulacion centro : poligonos) {
            if (centro.contains(p)) {
                return centroide(centro);
            }
        }
        return null;
    }
    
    private Point centroide(Polygon p) {
        double coordX = 0;
        double coordY = 0;
        for (int i = 0; i < p.npoints; i++) {
            coordX += p.xpoints[i];
            coordY += p.ypoints[i];
        }
        coordX /= (double) p.npoints;
        coordY /= (double) p.npoints;
        
        return new Point((int) Math.floor(coordX), (int) Math.floor(coordY));
    }
    
    private void unirImagenes() {
        BufferedImage result = new BufferedImage(NUM_X * metadata.getDimX(), NUM_Y * metadata.getDimY(), BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        Graphics2D g2d = result.createGraphics();
        
        for(int x = 0; x < images.length; x++) {
            for (int y = 0; y < images[0].length; y++) {
                g.drawImage(images[x][y], metadata.getDimX() * x, metadata.getDimY() * y, null);
            }
        }
        
        this.mapa = dibujarMapa(result);
        
        //g2d.draw(new Line2D.Double(0, 0, 512, 512));
        //g2d.setColor(new Color(1f,0f,0f,.1f));       
        
        // dibujarPoligonos();
        this.unida = result;
    }
    
    public BufferedImage renderizarPunto(Point punto, boolean añadir/*, Color color*/) {
        int radio = (int) Math.floor((double) RADIO_PUNTO / 2.0);
        if (añadir) {
            puntosDeCarga.add(punto);           
        }
        Graphics2D g2d = unida.createGraphics();
        g2d.fillOval((int) Math.floor(punto.getX()) - radio,
                (int) Math.floor(punto.getY()) - radio, RADIO_PUNTO, RADIO_PUNTO);
        return unida;
    }
    
    public BufferedImage renderizarLineaCarga(Point puntoi, Point puntof, boolean eliminar) {
        if (eliminar) {
            eliminarLineas();            
        }
        renderizarPunto(puntosDeCarga.getFirst(), false);
        Graphics2D g2d = unida.createGraphics();
        g2d.drawLine((int) Math.floor(puntoi.getX()), (int) Math.floor(puntoi.getY()),
                (int) Math.floor(puntof.getX()), (int) Math.floor(puntof.getY()));
        return unida;
    }
    
    public void añadirLineaDeCarga(Point puntoi, Point puntof) {
        Point[] puntos = new Point[2];
        puntos[0] = puntoi;
        puntos[1] = puntof;
        lineasDeCarga.add(puntos);
    }
    
    public void finalizarGeneracionPoligono() {
        puntosDeCarga.clear();
        lineasDeCarga.clear();
    }
    
    private void eliminarLineas() {
        render();
        for (Point puntoDeCarga : puntosDeCarga) {
            renderizarPunto(puntoDeCarga, false);
        }
        for (Point[] lineaDeCarga : lineasDeCarga) {
            renderizarLineaCarga(lineaDeCarga[0], lineaDeCarga[1], false);
        }
    }
    
    private BufferedImage dibujarMapa(BufferedImage mapa) {
        return new BufferedImage(mapa.getColorModel(), mapa.copyData(null), 
                mapa.getColorModel().isAlphaPremultiplied(), null);
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
        this.x = 2 * this.x + 1;
        this.y = 2 * (this.y + 1);
        this.zoom++;
    }
    
    public void disminuirZoom() {
        this.x = (this.x - 1) / 2;
        this.y = (this.y / 2) - 1;
        
        while (this.x < 0) {
            this.x++;
        }
        
        while (this.y < 0) {
            this.y++;
        }
        
        this.zoom--;
        
        while (this.x > this.metadata.getDato(zoom, "numX") - this.NUM_X) {
            this.x--;
        }
        
        while (this.y > this.metadata.getDato(zoom, "numY") - this.NUM_Y) {
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

    public MapMetadata getMetadata() {
        return metadata;
    }

    public int getZoom() {
        return zoom;
    }

    public void setPoligonos(LinkedList<CentroDeAcumulacion> poligonos) {
        this.poligonos = poligonos;
    }

    public void añadirPoligono(CentroDeAcumulacion centro) {
        this.poligonos.add(centro);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
