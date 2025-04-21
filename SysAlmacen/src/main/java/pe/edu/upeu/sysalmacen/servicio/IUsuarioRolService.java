package pe.edu.upeu.sysalmacen.servicio;

import java.util.List;

import pe.edu.upeu.sysalmacen.modelo.UsuarioRol;

public interface IUsuarioRolService {
    List<UsuarioRol> findOneByUsuarioUser(String user);
    UsuarioRol save(UsuarioRol ur);

}