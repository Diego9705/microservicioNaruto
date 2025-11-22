package org.jramirezdfernandez.mision;


import org.jramirezdfernandez.mision.Mision;
import org.jramirezdfernandez.mision.MisionDTO;
import org.jramirezdfernandez.ninja.Ninja;
import org.mapstruct.Mapper;

@Mapper
public interface MisionMapper {

    default String map(Ninja ninja) {
        if (ninja == null) {
            return null;
        }
        return ninja.getName();
    }

    MisionDTO misionToMisionDTO(Mision mision);



}
