package org.jramirezdfernandez.ninja;



import jakarta.persistence.*;
import lombok.*;
import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.exportacion.ElementToVisit;
import org.jramirezdfernandez.exportacion.VisitorFormato;
import org.jramirezdfernandez.jutsu.Jutsu;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name ="t_ninjas")
public class Ninja implements ElementToVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String rank;

    private Integer atk;

    private Integer def;

    private Integer chakra;


    @ManyToOne
    @JoinColumn(name = "aldea_id")
    private Aldea aldea;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="ninja_jutsu",
            joinColumns = @JoinColumn(name = "ninja_id"),
            inverseJoinColumns = @JoinColumn(name = "jutsu_id")
    )
    private List<Jutsu> jutsus;


    @Override
    public byte[] aceptarExportarFormato(VisitorFormato formato) {
        return formato.exportarNinja(this);
    }
}
