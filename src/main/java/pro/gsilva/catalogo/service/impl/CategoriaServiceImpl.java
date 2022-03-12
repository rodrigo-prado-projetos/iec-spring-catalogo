package pro.gsilva.catalogo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.repository.CategoriaRepository;
import pro.gsilva.catalogo.service.CategoriaService;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        String nomeLike = nome + "%";
        return categoriaRepository.findAllByNomeIsLike(nomeLike);
    }

    @Override
    public void excluir(long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria findById(long id) {
        return categoriaRepository.findById(id).get();
    }
}
