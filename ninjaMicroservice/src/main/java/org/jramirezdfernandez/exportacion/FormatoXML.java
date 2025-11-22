package org.jramirezdfernandez.exportacion;

import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.exportacion.VisitorFormato;
import org.jramirezdfernandez.jutsu.Jutsu;
//import org.jramirezdfernandez.mision.Mision;
import org.jramirezdfernandez.ninja.Ninja;

import java.util.List;
import java.util.Optional;

public class FormatoXML extends VisitorFormato {


    @Override
    public byte[] exportarNinja(Ninja ninja) {

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<ninja>\n");
        if (ninja != null) {
            sb.append("  <name>").append(escXml(ninja.getName())).append("</name>\n");
            sb.append("  <rank>").append(escXml(ninja.getRank())).append("</rank>\n");
            sb.append("  <atk>").append(ninja.getAtk()!= null ? ninja.getAtk() : "").append("</atk>\n");
            sb.append("  <def>").append(ninja.getDef()!= null ? ninja.getDef() : "").append("</def>\n");
            sb.append("  <chakra>").append(ninja.getChakra()!= null ? ninja.getChakra() : "").append("</chakra>\n");
            sb.append("  <aldea>").append(escXml(Optional.ofNullable(ninja.getAldea()).map(Aldea::getName).orElse(""))).append("</aldea>\n");
            sb.append("  <jutsus>");
            List<Jutsu> j = ninja.getJutsus();
            if (j != null) {
                for (Jutsu s : j) {
                    sb.append("    <jutsu>").append(escXml(s.getName())).append("</jutsu>\n");
                }
            }
            sb.append("</jutsus>\n");
        }
        sb.append("</ninja>\n");
        return writeFile(sb.toString());
    }

    /*
    @Override
    public byte[] exportarMision(Mision mision) {

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<mision>\n");
        if (mision != null) {
            sb.append("  <name>").append(escXml(mision.getName())).append("</name>\n");
            sb.append("  <rank>").append(escXml(mision.getRank())).append("</rank>\n");
            sb.append("  <recompensa>").append(mision.getRecompensa()!= null ? mision.getRecompensa() : "").append("</recompensa>\n");
            sb.append("  <requisitorango>").append(escXml(mision.getRequisitorango())).append("</requisitorango>\n");
            sb.append("  <ninja>").append(escXml(Optional.ofNullable(mision.getNinja()).map(Ninja::getName).orElse(""))).append("</ninja>\n");
        }
        sb.append("</mision>\n");
        return writeFile(sb.toString());
    }

     */

    private static String escXml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }


}
