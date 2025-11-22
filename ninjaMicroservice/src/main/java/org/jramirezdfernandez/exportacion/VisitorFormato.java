package org.jramirezdfernandez.exportacion;

//import org.jramirezdfernandez.mision.Mision;
import org.jramirezdfernandez.ninja.Ninja;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public abstract class VisitorFormato {

    public static String correcionCaracteres(String name) {
        if (name == null || name.trim().isEmpty()) return "desconocido";
        return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }

    public static byte[] writeFile(String content) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {
            writer.write(content);


        } catch (IOException e) {
            System.out.println("Error al exportar archivo " + e.getMessage());
        }
        return baos.toByteArray();
    }

    public abstract byte[] exportarNinja(Ninja ninja);
    //public abstract byte[] exportarMision(Mision mision);
}
