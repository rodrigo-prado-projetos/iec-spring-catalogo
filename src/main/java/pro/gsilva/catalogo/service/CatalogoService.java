package pro.gsilva.catalogo.service;

import java.util.List;

import pro.gsilva.catalogo.model.Musica;

public interface CatalogoService {
    List<Musica> findAll();
    public List<Musica> findAllNative();
    Musica findById(long id);
    Musica save(Musica musica);
    void excluir(long id);
    List<Musica> findByTitulo(String titulo);
    List<Musica> findMusicaByIdCategoria(Long nome);
}
