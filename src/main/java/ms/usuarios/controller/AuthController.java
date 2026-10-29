package ms.usuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ms.usuarios.dto.LoginRequestDTO;
import ms.usuarios.dto.UsuarioResponseDTO;
import ms.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST: login
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.login(dto);
        return ResponseEntity.ok(response);
    }
}