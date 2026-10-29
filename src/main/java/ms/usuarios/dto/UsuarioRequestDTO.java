package ms.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ms.usuarios.model.Rol;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio") 
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 8, max = 20, message = "El password debe tener entre 8 y 20 caracteres")
    private String password;

    private Rol rol;
}