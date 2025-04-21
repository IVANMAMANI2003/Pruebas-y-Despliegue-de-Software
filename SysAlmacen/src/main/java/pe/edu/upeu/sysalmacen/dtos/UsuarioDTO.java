package pe.edu.upeu.sysalmacen.dtos;

import java.util.Arrays;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDTO {

        public static class UsuarioCrearDto {
            private String user;
            private String clave;
            private String rol;
    
            // Getters y setters
            public String getUser() {
                return user;
            }
    
            public void setUser(String user) {
                this.user = user;
            }
    
            public String getClave() {
                return clave;
            }
    
            public void setClave(String clave) {
                this.clave = clave;
            }
    
            public String getRol() {
                return rol;
            }
    
            public void setRol(String rol) {
                this.rol = rol;
            }
        }

    private Long idUsuario;
    @NotNull
    private String user;
    @NotNull
    private String estado;
    private String token;

    public record CredencialesDto(String user, char[] clave) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CredencialesDto that = (CredencialesDto) o;
            return user.equals(that.user) && Arrays.equals(clave, that.clave);
        }
    
        @Override
        public int hashCode() {
            int result = user.hashCode();
            result = 31 * result + Arrays.hashCode(clave);
            return result;
        }
    
        @Override
        public String toString() {
            return "CredencialesDto{" +
                    "user='" + user + '\'' +
                    ", clave=" + Arrays.toString(clave) +
                    '}';
        }
    }
}