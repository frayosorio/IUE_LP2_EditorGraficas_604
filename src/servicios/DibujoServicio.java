package servicios;

import modelos.Nodo;
import modelos.TrazoDto;

import java.awt.Graphics;
import java.awt.Color;

public class DibujoServicio {

    private Nodo cabeza;

    public DibujoServicio() {
        cabeza = null;
    }

    public void agregar(Nodo nodo) {
        if (cabeza == null) {
            // lista ligada vacia
            cabeza = nodo;
        } else {
            // recorrer la lista hasta el ultimo
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nodo;
        }
    }

    public void dibujar(Graphics g) {
        // recorrer la lista completa
        Nodo actual = cabeza;
        while (actual != null) {
            actual.getTrazo().dibujar(g, actual.getColor());
            actual = actual.siguiente;
        }
    }

    public int getLongitud() {
        int totalNodos = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            totalNodos++;
            actual = actual.siguiente;
        }
        return totalNodos;
    }

    public boolean guardarJSON(String nombreArchivo) {
        TrazoDto[] trazos = new TrazoDto[getLongitud()];
        Nodo actual = cabeza;
        int fila = 0;
        while (actual != null) {
            trazos[fila] = actual.toDto();
            fila++;
            actual = actual.siguiente;
        }
        return ArchivoServicio.guardarJson(nombreArchivo, trazos);
    }


    // ********** Metodos Estaticos **********

    public static void limpiarLienzo(Graphics g, int ancho, int alto) {
        g.setColor(Color.black);
        g.fillRect(0, 0, ancho, alto);
    }

}
