package org.jramirezdfernandez.ninja;

import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.jutsu.Jutsu;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface NinjaMapper {

    default String mapAldea(Aldea aldea) {
        if (aldea == null) {
            return null;
        }
        return aldea.getName();
    }
    default List<String> mapJutsu(List<Jutsu> jutsus){

        return jutsus.stream().map(Jutsu::getName).toList();

    }


    NinjaDTO ninjaToNinjaDto(Ninja ninja);
}