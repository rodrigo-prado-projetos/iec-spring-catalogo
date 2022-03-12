package pro.gsilva.catalogo.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "TB_MUSICA")
@Data
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String autor;

    @NotBlank
    private String titulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "UTC-03")
    private LocalDate data;

    @NotBlank
    @Lob
    private String letra;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = true)
    private Categoria categoria;


}
