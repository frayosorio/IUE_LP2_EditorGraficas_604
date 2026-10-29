package controladores;

import com.fasterxml.jackson.core.type.TypeReference;
import modelos.*;
import servicios.ArchivoServicio;
import servicios.DibujoServicio;
import vistas.EditorVista;

import java.awt.event.*;
import java.util.List;

public class EditorControlador {

    private final EditorVista vista;

    private Estado estado;
    private int x, y;

    private DibujoServicio dibujo;
    String nombreArchivo = "";

    public EditorControlador(EditorVista vista) {
        this.vista = vista;
        estado = Estado.NADA;
        dibujo = new DibujoServicio();

        this.vista.setIniciarLienzo(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                iniciarLienzo();
            }
        });

        this.vista.setClickPanelGrafica(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                clicRaton(evento);
            }
        });

        this.vista.setMovimientoRatonPanelGrafica(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evento) {
                movimientoRaton(evento);
            }
        });

        this.vista.setGuardarDibujo(evento -> {
            guardarDibujo();
        });

        this.vista.setCargarDibujo(evento -> cargarDibujo());
    }

    private void iniciarLienzo() {
        DibujoServicio.limpiarLienzo(vista.getGraficadorPanel(), vista.getPnlGrafica().getWidth(), vista.getPnlGrafica().getHeight());
    }

    private void redibujar() {
        DibujoServicio.limpiarLienzo(vista.getGraficadorPanel(), vista.getPnlGrafica().getWidth(), vista.getPnlGrafica().getHeight());
        dibujo.dibujar(vista.getGraficadorPanel());
    }

    private void clicRaton(MouseEvent evento) {
        if (estado == Estado.NADA) {
            x = evento.getX();
            y = evento.getY();
            estado = Estado.TRAZANDO;

            System.out.println("primer clic en x=" + x + " y=" + y);
        } else {
            System.out.println("segundo clic en x=" + evento.getX() + " y=" + evento.getY());

            Trazo trazo = switch (vista.getTipoTrazoSeleccionado()) {
                case LINEA -> new Linea(x, y, evento.getX(), evento.getY());
                case RECTANGULO -> new Rectangulo(x, y, evento.getX(), evento.getY());
                case OVALO -> new Ovalo(x, y, evento.getX(), evento.getY());
            };
            dibujo.agregar(new Nodo(trazo, vista.getColorTrazoSeleccionado()));
            redibujar();
            estado = Estado.NADA;
        }
    }

    private void movimientoRaton(MouseEvent evento) {
        if (estado == Estado.TRAZANDO) {
            iniciarLienzo();
            Trazo trazo = switch (vista.getTipoTrazoSeleccionado()) {
                case LINEA -> new Linea(x, y, evento.getX(), evento.getY());
                case RECTANGULO -> new Rectangulo(x, y, evento.getX(), evento.getY());
                case OVALO -> new Ovalo(x, y, evento.getX(), evento.getY());
            };
            redibujar();
            trazo.dibujar(vista.getGraficadorPanel(), vista.getColorTrazoSeleccionado());
        }
    }

    private void guardarDibujo() {
        if (nombreArchivo.equals("")) {
            nombreArchivo = ArchivoServicio.elegirArchivo(true);
        }
        if (!nombreArchivo.equals("")) {
            dibujo.guardarJSON(nombreArchivo);
            vista.mostrarMensaje("El archivo con el dibujo fue guardado");
        }
    }

    private void cargarDibujo() {
        nombreArchivo = ArchivoServicio.elegirArchivo(false);
        if (!nombreArchivo.isEmpty()) {
            dibujo.cargarDesdeJSON(nombreArchivo);
            redibujar();
        }
    }

}
