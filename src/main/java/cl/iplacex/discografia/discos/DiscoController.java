package cl.iplacex.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.iplacex.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(
            IDiscoRepository discoRepo,
            IArtistaRepository artistaRepo) {

        this.discoRepo = discoRepo;
        this.artistaRepo = artistaRepo;
    }

    // CREAR DISCO
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.idArtista)) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        Disco nuevoDisco = discoRepo.save(disco);

        return new ResponseEntity<>(
            nuevoDisco,
            HttpStatus.CREATED
        );
    }

    // LISTAR TODOS LOS DISCOS
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        List<Disco> discos = discoRepo.findAll();

        return new ResponseEntity<>(
            discos,
            HttpStatus.OK
        );
    }

    // BUSCAR DISCO POR ID
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(
            @PathVariable String id) {

        Optional<Disco> disco = discoRepo.findById(id);

        if (disco.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        return new ResponseEntity<>(
            disco.get(),
            HttpStatus.OK
        );
    }

    // LISTAR DISCOS DE UN ARTISTA
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        if (!artistaRepo.existsById(id)) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        List<Disco> discos = discoRepo.findDiscosByIdArtista(id);

        return new ResponseEntity<>(
            discos,
            HttpStatus.OK
        );
    }
}