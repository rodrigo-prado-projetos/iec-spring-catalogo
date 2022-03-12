package pro.gsilva.catalogo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.repository.CatalogoRepository;
import pro.gsilva.catalogo.service.CatalogoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogoServiceImpl implements CatalogoService {
    @Autowired
    private CatalogoRepository catalogoRepository;

    @Override
    public List<Musica> findAll() {
        return catalogoRepository.findAll();
    }

    @Override
    public Musica findById(long id) {
        return catalogoRepository.findById(id).get();
    }

    @Override
    public Musica save(Musica musica) {
        return catalogoRepository.save(musica);
    }

    @Override
    public void excluir(long id) {
        catalogoRepository.deleteById(id);
    }

    @Override
    public List<Musica> findByTitulo(String titulo) {
        String tituloLike = titulo + "%";
        return catalogoRepository.findAllByTituloIsLike(tituloLike);
    }

    @Override
    public List<Musica> findAllNative() {
        List<Object[]> allNative = catalogoRepository.findAllNative();
        List<Musica> musicas = new ArrayList<>();
        for (Object[] obj : allNative) {
            Musica musica = getMusica(obj);
            musicas.add(musica);
        }
        return musicas;
    }

    @Override
    public List<Musica> findMusicaByIdCategoria(Long id) {
        List<Object[]> allNative = catalogoRepository.findMusicaByIdCategoria(id);

        List<Musica> musicas = new ArrayList<>();
        for (Object[] obj : allNative) {
            Musica musica = getMusica(obj);
            musicas.add(musica);
        }
        return musicas;
    }

    private Musica getMusica(Object[] obj) {
        Musica musica = new Musica();
        musica.setId((Long) Long.parseLong(obj[0].toString()));
        musica.setAutor((String) obj[1]);
        musica.setTitulo((String) obj[2]);
        musica.setData((LocalDate) LocalDate.parse(obj[3].toString()));
        musica.setLetra((String) obj[4]);
        Categoria categoria = new Categoria();
        categoria.setNome((String) obj[5]);
        musica.setCategoria(categoria);
        return musica;
    }
}