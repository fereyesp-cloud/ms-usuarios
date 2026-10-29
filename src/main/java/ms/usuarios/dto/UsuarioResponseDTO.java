package ms.usuarios.dto;

import java.time.LocalDateTime;

import lombok.Data;
import ms.usuarios.model.Rol;
import ms.usuarios.model.Usuario;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    private Boolean activo;
    private LocalDateTime fechaRegistro;

    public static UsuarioResponseDTO desdeEntidad(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setActivo(usuario.getActivo());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }
}