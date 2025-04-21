package pe.edu.upeu.sysalmacen.servicio.impl;

import java.time.LocalDateTime;
import java.util.List;

import pe.edu.upeu.sysalmacen.excepciones.CustomResponse;
import pe.edu.upeu.sysalmacen.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysalmacen.repositorio.ICrudGenericoRepository;
import pe.edu.upeu.sysalmacen.servicio.ICrudGenericoService;

public abstract class CrudGenericoServiceImp<T, I> implements ICrudGenericoService<T, I> {

    private static final String ID_NOT_FOUND_MESSAGE = "ID NOT FOUND: ";

    // Método abstracto para obtener el repositorio específico
    protected abstract ICrudGenericoRepository<T, I> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(I id, T t) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(ID_NOT_FOUND_MESSAGE + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(I id) {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(ID_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public CustomResponse delete(I id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException(ID_NOT_FOUND_MESSAGE + id));
        getRepo().deleteById(id);

        CustomResponse response = new CustomResponse();
        response.setStatusCode(200);
        response.setDatetime(LocalDateTime.now());
        response.setMessage("true");
        response.setDetails("Todo Ok");
        return response;
    }
}