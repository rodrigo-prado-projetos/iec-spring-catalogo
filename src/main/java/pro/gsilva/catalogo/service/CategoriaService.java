package pro.gsilva.catalogo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.gsilva.catalogo.model.Categoria;

import java.util.List;

public interface CategoriaService {
    Page<Categoria> findAll(Pageable pageable);
    Categoria save(Categoria categoria);
    List<Categoria> findByNome(String nome);
    void excluir(long id);
    Categoria findById(long id);
}
