package cl.iplacex.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository artistaRepo;

    public ArtistaController(IArtistaRepository artistaRepo) {
        this.artistaRepo = artistaRepo;
    }

    // CREAR ARTISTA
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        Artista nuevoArtista = artistaRepo.save(artista);

        return new ResponseEntity<>(
            nuevoArtista,
            HttpStatus.CREATED
        );
    }

    // LISTAR TODOS LOS ARTISTAS
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {

        List<Artista> artistas = artistaRepo.findAll();

        return new ResponseEntity<>(
            artistas,
            HttpStatus.OK
        );
    }

    // BUSCAR ARTISTA POR ID
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(
            @PathVariable String id) {

        Optional<Artista> artista = artistaRepo.findById(id);

        if (artista.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        return new ResponseEntity<>(
            artista.get(),
            HttpStatus.OK
        );
    }

    // ACTUALIZAR ARTISTA
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        Optional<Artista> artistaExistente = artistaRepo.findById(id);

        if (artistaExistente.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        artista._id = id;

        Artista artistaActualizado = artistaRepo.save(artista);

        return new ResponseEntity<>(
            artistaActualizado,
            HttpStatus.OK
        );
    }

    // ELIMINAR ARTISTA
    @DeleteMapping(
        value = "/artista/{id}"
    )
    public ResponseEntity<Void> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        Optional<Artista> artista = artistaRepo.findById(id);

        if (artista.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }

        artistaRepo.deleteById(id);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }
}