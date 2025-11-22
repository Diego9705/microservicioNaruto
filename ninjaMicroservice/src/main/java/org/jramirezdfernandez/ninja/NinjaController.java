package org.jramirezdfernandez.ninja;

import org.hibernate.ObjectNotFoundException;
import org.jramirezdfernandez.aldea.Aldea;
import org.jramirezdfernandez.exportacion.ExportacionService;
import org.jramirezdfernandez.jutsu.Jutsu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/ninjas")
public class NinjaController {

    @Autowired
    private NinjaRepository ninjaRepository;


    private final ExportacionService exportacionService;

    private final NinjaMapper ninjaMapper;

    public NinjaController(ExportacionService exportacionService, NinjaMapper ninjaMapper) {
        this.exportacionService = exportacionService;
        this.ninjaMapper = ninjaMapper;
    }

    @GetMapping
    public List<NinjaDTO> getAllNinjas() {
        List<Ninja> ninjas = ninjaRepository.findAllWithAldea();
        return ninjas.stream().map(ninjaMapper::ninjaToNinjaDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ninja> getNinjaById(@PathVariable Long id) {

        Optional<Ninja> ninjaValidacion = ninjaRepository.findById(id);

        return ninjaValidacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id_ninja}/{opcion}")
    public ResponseEntity<byte[]> exportarNinja(@PathVariable Long id_ninja,@PathVariable Integer opcion) throws IOException {
        Optional<Ninja> ninjaVerificar = ninjaRepository.findById(id_ninja);

        if (ninjaVerificar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ninja ninja = ninjaVerificar.get();

        return exportacionService.exportar(ninja,opcion);
    }

    @PostMapping
    public ResponseEntity<Ninja> createNinja(@RequestBody Ninja ninja) {
        Ninja savedNinja = ninjaRepository.save(ninja);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedNinja.getId()).toUri();
        return ResponseEntity.created(location).body(savedNinja);
    }

    @PatchMapping
    public ResponseEntity<Ninja> modificarNinja(@RequestBody Ninja ninja) {
        Optional<Ninja> ninjaValidacionOpt = ninjaRepository.findById(ninja.getId());

        if (ninjaValidacionOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Ninja baseNinja = ninjaValidacionOpt.get();
        baseNinja.setName(ninja.getName());
        baseNinja.setRank(ninja.getRank());
        baseNinja.setAtk(ninja.getAtk());
        baseNinja.setDef(ninja.getDef());
        baseNinja.setChakra(ninja.getChakra());
        baseNinja.setAldea(ninja.getAldea());

        Ninja updatedNinja = ninjaRepository.save(baseNinja);
        return ResponseEntity.ok(updatedNinja);
    }

    @PatchMapping("/conectarnj/{id_ninja}")
    public ResponseEntity<Ninja> conectarJutsu(@PathVariable Long id_ninja, @RequestBody Jutsu jutsu) {

        Optional<Ninja> ninjaValidacion = ninjaRepository.findById(id_ninja);

        if (ninjaValidacion.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Ninja ninja =  ninjaValidacion.get();

        List<Long> listaIds = ninja.getJutsus().stream().map(Jutsu::getId).toList();

        if (listaIds.contains(jutsu.getId()) ){
            return ResponseEntity.status(409).body(ninja);
        }

        List<Jutsu> listaJutsus = ninja.getJutsus();

        listaJutsus.add(jutsu);
        ninja.setJutsus(listaJutsus);

        ninjaRepository.save(ninja);

        return ResponseEntity.ok(ninja);
    }

    @PatchMapping("/conectarna/{id_ninja}")
    public ResponseEntity<Ninja> conectarAldea(@PathVariable Long id_ninja, @RequestBody Aldea aldea) {

        Optional<Ninja> ninjaValidacion = ninjaRepository.findById(id_ninja);

        if (ninjaValidacion.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Ninja ninja =  ninjaValidacion.get();

        ninja.setAldea(aldea);

        ninjaRepository.save(ninja);

        return ResponseEntity.ok(ninja);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNinja(@PathVariable Long id) {
        try{
            ninjaRepository.deleteById(id);
            return  ResponseEntity.noContent().build();

        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}