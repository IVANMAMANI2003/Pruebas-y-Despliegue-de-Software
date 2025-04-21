package pe.edu.upeu.sysalmacen.control;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysalmacen.dtos.UsuarioDTO;
import pe.edu.upeu.sysalmacen.security.JwtTokenUtil;
import pe.edu.upeu.sysalmacen.security.JwtUserDetailsService;
import pe.edu.upeu.sysalmacen.servicio.IUsuarioService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final IUsuarioService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody @Valid UsuarioDTO.CredencialesDto credentialsDto, HttpServletRequest request) {
        UsuarioDTO userDto = userService.login(credentialsDto);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(credentialsDto.user());
        userDto.setToken(jwtTokenUtil.generateToken(userDetails));
        request.getSession().setAttribute("USER_SESSION", userDto.getUser());
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody @Valid UsuarioDTO.UsuarioCrearDto user) {
        logger.info("Passss....{}", user.getRol());
        UsuarioDTO createdUser = userService.register(user);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUser());
        createdUser.setToken(jwtTokenUtil.generateToken(userDetails));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUser())).body(createdUser);
    }
}
