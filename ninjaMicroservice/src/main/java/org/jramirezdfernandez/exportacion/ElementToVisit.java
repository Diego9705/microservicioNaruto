package org.jramirezdfernandez.exportacion;

import org.jramirezdfernandez.exportacion.VisitorFormato;

public interface ElementToVisit {
    byte[] aceptarExportarFormato(VisitorFormato formato);

    String getName();
}
