package ms.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import ms.usuarios.dto.UsuarioRequestDTO;
import ms.usuarios.dto.UsuarioResponseDTO;
import ms.usuarios.service.UsuarioService;



@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST: registrar usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //GET: buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // GET: listar usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // PUT: actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.actualizar(id, dto);
        return ResponseEntity.ok(response);
    }
    

    //Delete: eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String , String>> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado"));
    }
}