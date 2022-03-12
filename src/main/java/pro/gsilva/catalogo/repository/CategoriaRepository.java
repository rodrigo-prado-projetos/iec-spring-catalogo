package pro.gsilva.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
//    @Query("select m from Categoria c where c.nome like :nome")
//    List<Categoria> findAllWithNomeLike(String nome);

    List<Categoria> findAllByNomeIsLike(String nome);
}