package org.jramirezdfernandez.mision;


import jakarta.persistence.*;
import lombok.*;
import org.jramirezdfernandez.exportacion.ElementToVisit;
import org.jramirezdfernandez.exportacion.VisitorFormato;
import org.jramirezdfernandez.ninja.Ninja;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name="t_mision")
public class Mision implements ElementToVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String rank;

    private Integer recompensa;

    private String requisitorango;

    @ManyToOne
    @JoinColumn(name = "ninja_id")
    private Ninja ninja;


    @Override
    public byte[] aceptarExportarFormato(VisitorFormato formato) {

        return formato.exportarMision(this);
    }
}
