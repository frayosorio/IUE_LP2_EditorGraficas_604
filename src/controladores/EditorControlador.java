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
        if (estado == Estado.NADA) {
            x = evento.getX();
            y = evento.getY();
            estado = Estado.TRAZANDO;

            System.out.println("primer clic en x=" + x + " y=" + y);
        } else {
            System.out.println("segundo clic en x=" + evento.getX() + " y=" + evento.getY());
            var g = vista.getGraficadorPanel();
            g.setColor(vista.getColorTrazoSeleccionado());
            switch (vista.getTipoTrazoSeleccionado()) {
                case LINEA:
                    g.drawLine(x, y, evento.getX(), evento.getY());
                    break;
                case RECTANGULO:
                    g.drawRect(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
                    break;
                case OVALO:
                    g.drawOval(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
            }
            estado = Estado.NADA;
        }
    }

    private void movimientoRaton(MouseEvent evento) {
        if(estado==Estado.TRAZANDO){
            DibujoServicio.limpiarLienzo(vista.getPnlGrafica());
            var g = vista.getGraficadorPanel();
            g.setColor(vista.getColorTrazoSeleccionado());
            switch (vista.getTipoTrazoSeleccionado()) {
                case LINEA:
                    g.drawLine(x, y, evento.getX(), evento.getY());
                    break;
                case RECTANGULO:
                    g.drawRect(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
                    break;
                case OVALO:
                    g.drawOval(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
            }
        }
    }

}
