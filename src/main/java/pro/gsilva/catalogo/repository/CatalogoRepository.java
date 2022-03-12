package pro.gsilva.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.gsilva.catalogo.model.Musica;

import java.util.List;

public interface CatalogoRepository extends JpaRepository<Musica, Long>, CustomCatalogoRepository {
    List<Musica> findAllByTitulo(String titulo);

    List<Musica> findAllByTituloIsLike(String titulo);

    @Query("select m from Musica m where m.titulo like :titulo")
    List<Musica> findAllWithTituloLike(String titulo);

    @Query(value = "SELECT m.id,m.autor, m.titulo,m.data,m.letra,c.nome FROM tb_musica m INNER JOIN categoria c ON m.categoria_id = c.id", nativeQuery = true)
    public List<Object[]> findAllNative();

    @Query(value = "SELECT m.id,m.autor, m.titulo,m.data,m.letra,c.nome FROM tb_musica m INNER JOIN categoria c ON m.categoria_id =:id and c.id =:id", nativeQuery = true)
    public List<Object[]> findMusicaByIdCategoria(@Param(value = "id") Long id);
}
