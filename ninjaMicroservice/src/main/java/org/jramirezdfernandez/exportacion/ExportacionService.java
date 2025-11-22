package org.jramirezdfernandez.exportacion;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExportacionService {

    private static final Map<Integer, ArrayList<Object>> map = new HashMap<Integer, ArrayList<Object>>() {{
        ArrayList<Object> listaJson = new ArrayList<>();

        listaJson.add(new FormatoJSON());
        listaJson.add("application/json");
        listaJson.add(".json");

        put(1, listaJson);

        ArrayList<Object> listaTexto = new ArrayList<>();

        listaTexto.add(new FormatoTexto());
        listaTexto.add("text/plain");
        listaTexto.add(".txt");

        put(2, listaTexto);

        ArrayList<Object> listaXml = new ArrayList<>();

        listaXml.add(new FormatoXML());
        listaXml.add("application/xml");
        listaXml.add(".xml");

        put(3, listaXml);

    }};

    public ArrayList<Object> obtenerPropiedadesExportar(Integer opcion){

        return map.get(opcion);
    }

    public ResponseEntity<byte[]> exportar(ElementToVisit element, Integer opcion) {

        ArrayList<Object> propiedades = obtenerPropiedadesExportar(opcion);

        byte[] fileContent = element.aceptarExportarFormato((VisitorFormato) propiedades.get(0));

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, (String) propiedades.get(1))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + element.getName() + propiedades.get(2) + "\"")
                .body(fileContent);

    }
}
