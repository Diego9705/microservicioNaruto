package org.jramirezdfernandez.exportacion;

import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.exportacion.VisitorFormato;
import org.jramirezdfernandez.jutsu.Jutsu;
//import org.jramirezdfernandez.mision.Mision;
import org.jramirezdfernandez.ninja.Ninja;

import java.util.List;
import java.util.Optional;

public class FormatoJSON extends VisitorFormato {

    @Override
    public byte[] exportarNinja(Ninja ninja) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (ninja != null) {
            sb.append("\"name\":").append(escJson(ninja.getName())).append(",")
                    .append("\"rank\":").append(escJson(ninja.getRank())).append(",")
                    .append("\"atk\":").append(ninja.getAtk()).append(",")
                    .append("\"def\":").append(ninja.getDef()).append(",")
                    .append("\"chakra\":").append(ninja.getChakra()).append(",")
                    .append("\"aldea\":").append(escJson(Optional.ofNullable(ninja.getAldea()).map(Aldea::getName).orElse(null))).append(",")
                    .append("\"jutsus\":[");
            List<Jutsu> j = ninja.getJutsus();
            if (j != null) {
                for (int k = 0; k < j.size(); k++) {
                    if (k > 0) sb.append(",");
                    sb.append(escJson(j.get(k).getName()));
                }
            }
            sb.append("]");
        }
        sb.append("}");
        return writeFile(sb.toString());
    }

    /*
    @Override
    public byte[] exportarMision(Mision mision) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (mision != null) {
            sb.append("\"name\":").append(escJson(mision.getName())).append(",")
            .append("\"rank\":").append(escJson(mision.getRank())).append(",")
            .append("\"recompensa\":").append(mision.getRecompensa()).append(",")
            .append("\"requisitorango\":").append(escJson(mision.getRequisitorango())).append(",")
            .append("\"ninja\":").append(escJson(Optional.ofNullable(mision.getNinja()).map(Ninja::getName).orElse(null)));
        }
        sb.append("}");
        return writeFile(sb.toString());
    }
    */


    private static String escJson(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int)c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append("\"");

        return sb.toString();
    }
}
