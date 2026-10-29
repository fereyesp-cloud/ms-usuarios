package ms.usuarios.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.usuarios.dto.UsuarioRequestDTO;
import ms.usuarios.dto.UsuarioResponseDTO;
import ms.usuarios.model.Usuario;
import ms.usuarios.repository.UsuarioRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // POST: registrar
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado con id {}", guardado.getId());
        return UsuarioResponseDTO.desdeEntidad(guardado);
    }

    // GET: buscar por id
    public UsuarioResponseDTO buscarPorId(Long id) {
        log.info("Buscando usuario con id {}", id);

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario no encontrado con id {}", id);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            });

        log.debug("Usuario encontrado: id={}, nombre={}, email={}",
            usuario.getId(), usuario.getNombre(), usuario.getEmail());
        return UsuarioResponseDTO.desdeEntidad(usuario);
    }

    // GET: listar todos
    public List<UsuarioResponseDTO> listar() {
        log.info("Listando usuarios");

        List<Usuario> usuarios = usuarioRepository.findAll();
        log.debug("Usuarios encontrados: {}", usuarios.size());

        return usuarios.stream()
            .map(UsuarioResponseDTO::desdeEntidad)
            .toList();
    }

    // PUT: actualizar
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        log.info("Actualizando usuario con id {}", id);

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario no encontrado con id {}", id);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            });

        // Validar que el email no se repita cuando se cambia
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado correctamente con id {}", id);
        return UsuarioResponseDTO.desdeEntidad(actualizado);
    }

    // DELETE: eliminar
    public void eliminar(Long id) {
        log.warn("Eliminando usuario con id {}", id);

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Usuario no encontrado con id {}", id);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            });

        usuarioRepository.delete(usuario);
        log.warn("Usuario eliminado correctamente con id {}", id);
    }
}