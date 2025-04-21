package pe.edu.upeu.sysalmacen.security;



import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysalmacen.modelo.Usuario;
import pe.edu.upeu.sysalmacen.modelo.UsuarioRol;
import pe.edu.upeu.sysalmacen.repositorio.IUsuarioRepository;
import pe.edu.upeu.sysalmacen.repositorio.IUsuarioRolRepository;

//Clase S4
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUsuarioRolRepository repo;
    private final IUsuarioRepository repoU;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repoU.findOneByUser(username).orElse(null);
        if (u == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        List<UsuarioRol> user = repo.findOneByUsuarioUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Roles not found for username: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        user.forEach(rol -> roles.add(new SimpleGrantedAuthority(rol.getRol().getNombre().name())));

        return new org.springframework.security.core.userdetails.User(u.getUser(), u.getClave(), roles);
    }
}