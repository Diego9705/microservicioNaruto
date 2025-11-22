package org.jramirezdfernandez.mision;

import org.hibernate.ObjectNotFoundException;
import org.jramirezdfernandez.exportacion.ExportacionService;
import org.jramirezdfernandez.ninja.Ninja;
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
@RequestMapping("/api/misiones")
public class MisionController {

    @Autowired
    private MisionRepository misionRepository;



    private final MisionMapper misionMapper;

    private final MisionService misionService;

    private final ExportacionService exportacionService;

    public MisionController(MisionMapper misionMapper, MisionService misionService, ExportacionService exportacionService) {
        this.misionMapper = misionMapper;
        this.misionService = misionService;
        this.exportacionService = exportacionService;
    }

    @GetMapping
    public List<MisionDTO> getAllMisiones() {
        List<Mision> misiones = misionRepository.findAll();
        return misiones.stream().map(misionMapper::misionToMisionDTO).toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Mision> getMisionById(@PathVariable Long id) {

        Optional<Mision> opt = misionRepository.findById(id);

        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/predeterminados")
    public void misionesPredeterminados() {
        misionRepository.save(Mision.builder().name("Exploración").rank("D").recompensa(10).requisitorango("Genin").build());
        misionRepository.save(Mision.builder().name("Recopilación de datos").rank("C").recompensa(15).requisitorango("Genin").build());
        misionRepository.save(Mision.builder().name("Escolta").rank("B").recompensa(20).requisitorango("Chunin").build());
        misionRepository.save(Mision.builder().name("Rescate").rank("A").recompensa(25).requisitorango("Chunin").build());
        misionRepository.save(Mision.builder().name("Batalla").rank("S").recompensa(30).requisitorango("Jonin").build());
    }


    @GetMapping("/{id_mision}/{opcion}")
    public ResponseEntity<byte[]> exportarMision(@PathVariable Long id_mision,@PathVariable Integer opcion) throws IOException {
        Optional<Mision> misionVerificar = misionRepository.findById(id_mision);

        if (misionVerificar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Mision mision = misionVerificar.get();

        return exportacionService.exportar(mision,opcion);
    }


    @PostMapping
    public ResponseEntity<Mision> createMision (@RequestBody Mision mision) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mision.getId()).toUri();

        misionRepository.save(mision);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping
    public ResponseEntity<Mision> modificarMision(@RequestBody Mision mision) {

        Optional<Mision> misionValidacionOpt = misionRepository.findById(mision.getId());

        if (misionValidacionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Mision misionBase = misionValidacionOpt.get();
        misionBase.setName(mision.getName());
        misionBase.setRank(mision.getRank());
        misionBase.setRecompensa(mision.getRecompensa());
        misionBase.setRequisitorango(mision.getRequisitorango());
        misionBase.setNinja(mision.getNinja());

        Mision misionUpdated = misionRepository.save(misionBase);

        return ResponseEntity.ok(misionUpdated);

    }


    @PatchMapping("/{id_mision}")
    public ResponseEntity<Mision> conectarNinja(@PathVariable Long id_mision, @RequestBody Ninja ninja) {


        Optional<Mision> misionValidacion =  misionRepository.findById(id_mision);

        if (misionValidacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        if (misionValidacion.get().getNinja() != null){
            return ResponseEntity.badRequest().build();
        }


        Mision mision = misionValidacion.get();

        boolean validacion = misionService.validarRango(mision.getRequisitorango(),ninja.getRank());


        if (validacion){
            mision.setNinja(ninja);
            misionRepository.save(mision);
            return ResponseEntity.ok(mision);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMision(@PathVariable Long id) {
        try{
            misionRepository.deleteById(id);
            return  ResponseEntity.noContent().build();

        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
