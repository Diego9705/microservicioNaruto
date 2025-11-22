package org.jramirezdfernandez.jutsu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/jutsus")
public class JutsuController {

    @Autowired
    private JutsuRepository jutsuRepository;

    @GetMapping
    public List<Jutsu> getAllJutsus() {
        return jutsuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jutsu> getJutsuById(@PathVariable Long id) {
        Optional<Jutsu> opt = jutsuRepository.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/predeterminados")
    public void jutsusPredeterminados() {
        jutsuRepository.save(Jutsu.builder().name("Clon de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Shurikens de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Espada de Agua").build());
        jutsuRepository.save(Jutsu.builder().name("Siervo de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Justsu Secreto de la Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Escudo de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Jutsu Secreto de la Niebla Cegadora").build());
        jutsuRepository.save(Jutsu.builder().name("Ocultación en el Frío").build());
        jutsuRepository.save(Jutsu.builder().name("Jaula de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Dragón de la Neblina").build());
        jutsuRepository.save(Jutsu.builder().name("Jutsu Prisión de Agua").build());
        jutsuRepository.save(Jutsu.builder().name("Lluvia Densa").build());
        jutsuRepository.save(Jutsu.builder().name("Agujas de Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Paso del Dios Niebla").build());
        jutsuRepository.save(Jutsu.builder().name("Fusión Total de la Niebla").build());

        jutsuRepository.save(Jutsu.builder().name("Máxima Técnica de Taijutsu de la Aldea Oculta de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Corriente de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Remolino de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Tempestad de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Sombra de la Hoja Danzante").build());
        jutsuRepository.save(Jutsu.builder().name("Arte Ninja, Shuriken de Teja").build());
        jutsuRepository.save(Jutsu.builder().name("Gran Remolino de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Golpe Destructor de Rocas de la Hoja").build());
        jutsuRepository.save(Jutsu.builder().name("Elemento Fuego: Jutsu de la Gran Llama").build());
        jutsuRepository.save(Jutsu.builder().name("Técnica de Ocultarse Entre las Hojas").build());
        jutsuRepository.save(Jutsu.builder().name("Hojas de Acero").build());
        jutsuRepository.save(Jutsu.builder().name("Técnica del Portador de la Oscuridad").build());
        jutsuRepository.save(Jutsu.builder().name("Técnica de Multiclones de Sombra").build());
        jutsuRepository.save(Jutsu.builder().name("Invocación: Dama de Hierro").build());
        jutsuRepository.save(Jutsu.builder().name("Dragón de Konoha").build());
        jutsuRepository.save(Jutsu.builder().name("Sello de la Pared de Hierro").build());
        jutsuRepository.save(Jutsu.builder().name("Manipulación de Técnicas").build());
        jutsuRepository.save(Jutsu.builder().name("Ninpō: Shisekiyōjin").build());

        jutsuRepository.save(Jutsu.builder().name("Suna Moji").build());
        jutsuRepository.save(Jutsu.builder().name("Noroi no Suna Ningyou no Jutsu").build());
        jutsuRepository.save(Jutsu.builder().name("Suna Bunshin").build());
        jutsuRepository.save(Jutsu.builder().name("Sunagoromo no Jutsu").build());
        jutsuRepository.save(Jutsu.builder().name("Sunaarashi no Jutsu").build());
        jutsuRepository.save(Jutsu.builder().name("Fuuton: Sajin Senpuu").build());
        jutsuRepository.save(Jutsu.builder().name("Saboten no Genjutsu").build());
        jutsuRepository.save(Jutsu.builder().name("Henkō Sajin: Nessa Meisai").build());
        jutsuRepository.save(Jutsu.builder().name("Mōbaku Sajin: Noizui").build());
        jutsuRepository.save(Jutsu.builder().name("Sajin: Shīsā").build());
        jutsuRepository.save(Jutsu.builder().name("Barrera de tormenta de arena").build());
    }

    @PostMapping
    public ResponseEntity<Jutsu> createJutsu(@RequestBody Jutsu jutsu) {
        Jutsu savedJutsu = jutsuRepository.save(jutsu);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedJutsu.getId()).toUri();
        return ResponseEntity.created(location).body(savedJutsu);
    }
}