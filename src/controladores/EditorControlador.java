package controladores;

import modelos.Estado;
import servicios.DibujoServicio;
import vistas.EditorVista;

import java.awt.event.*;

public class EditorControlador {

    private final EditorVista vista;

    private Estado estado;
    private int x, y;

    public EditorControlador(EditorVista vista) {
        this.vista = vista;
        estado = Estado.NADA;

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
    }

    private void iniciarLienzo() {
        DibujoServicio.limpiarLienzo(vista.getPnlGrafica());
    }

    private void clicRaton(MouseEvent evento) {
    }

    private void movimientoRaton(MouseEvent evento) {
    }

}
