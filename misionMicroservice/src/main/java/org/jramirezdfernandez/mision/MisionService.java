package org.jramirezdfernandez.mision;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MisionService {

    private static final Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("Genin", 1);
        put("Chunin", 2);
        put("Jonin",3);
    }};

    public boolean validarRango(String rangoMision, String rangoNinja){

        Integer nMision = map.get(rangoMision);

        Integer nNinja = map.get(rangoNinja);

        return nNinja >= nMision;

    }

}
