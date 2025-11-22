package org.jramirezdfernandez.aldea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/aldeas")
public class AldeaController {

    @Autowired
    private AldeaRepository aldeaRepository;

    @GetMapping
    public List<Aldea> getAllaldeas() {
        return aldeaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aldea> getAldeaById(@PathVariable Long id) {
        Optional<Aldea> opt = aldeaRepository.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/predeterminados")
    public void aldeasPredeterminados() {
        aldeaRepository.save(Aldea.builder().name("Aldea Konoha").build());
        aldeaRepository.save(Aldea.builder().name("Aldea Kiri").build());
        aldeaRepository.save(Aldea.builder().name("Aldea Suna").build());
    }

    @PostMapping
    public ResponseEntity<Aldea> createAldea(@RequestBody Aldea aldea) {
        Aldea savedAldea = aldeaRepository.save(aldea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAldea.getId()).toUri();
        return ResponseEntity.created(location).body(savedAldea);
    }
}