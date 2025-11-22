package org.jramirezdfernandez.exportacion;

import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.exportacion.VisitorFormato;
import org.jramirezdfernandez.jutsu.Jutsu;
//import org.jramirezdfernandez.mision.Mision;
import org.jramirezdfernandez.ninja.Ninja;

import java.util.List;
import java.util.Optional;

public class FormatoTexto extends VisitorFormato {


    @Override
    public byte[] exportarNinja(Ninja ninja) {

        StringBuilder sb = new StringBuilder();
        if (ninja != null) {
            sb.append("Nombre: ").append(ninja.getName()).append("\n");
            sb.append("Rango: ").append(ninja.getRank()).append("\n");
            sb.append("Ataque: ").append(ninja.getAtk()).append("\n");
            sb.append("Defensa: ").append(ninja.getDef()).append("\n");
            sb.append("Chakra: ").append(ninja.getChakra()).append("\n");
            sb.append("Aldea: ").append(Optional.ofNullable(ninja.getAldea()).map(Aldea::getName).orElse("null")).append("\n");
            List<Jutsu> j = ninja.getJutsus();
            if (j != null && !j.isEmpty()) {
                sb.append("Jutsus: ");
                for (int k = 0; k < j.size(); k++) {
                    if (k > 0) sb.append(", ");
                    sb.append(j.get(k).getName());
                }
                sb.append("\n");
            }
        }
        return writeFile(sb.toString());
    }
    /*
    @Override
    public byte[] exportarMision(Mision mision) {

        StringBuilder sb = new StringBuilder();
        if (mision != null) {
            sb.append("Nombre: ").append(mision.getName()).append("\n");
            sb.append("Rango: ").append(mision.getRank()).append("\n");
            sb.append("Recompensa: ").append(mision.getRecompensa()).append("\n");
            sb.append("Requisito Rango: ").append(mision.getRequisitorango()).append("\n");
            sb.append("Ninja: ").append(Optional.ofNullable(mision.getNinja()).map(Ninja::getName).orElse("null")).append("\n");
        }
        return writeFile(sb.toString());
    }
    */

}
