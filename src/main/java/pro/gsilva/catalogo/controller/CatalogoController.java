package pro.gsilva.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.service.CatalogoService;
import pro.gsilva.catalogo.service.CategoriaService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CatalogoController {
    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/musicas", method = RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAllNative();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/musicas/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/musicas/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getFormEdit(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaForm");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.GET)
    public String getMusicaForm(Musica musica) {
        return "musicaForm";
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.POST)
    public ModelAndView salvarMusica(@Valid @ModelAttribute("musica") Musica musica,
                                     BindingResult result, Model model) {
        if (result.hasErrors() || Objects.isNull(musica.getCategoria())) {
            ModelAndView musicaForm = new ModelAndView("musicaForm");
            musicaForm.addObject("mensagemCategoria", "Selecione uma categoria para vincular a musica");
            musicaForm.addObject("mensagem", "Verifique os errors do formulário");
            return musicaForm;
        }
        musica.setData(LocalDate.now());
        catalogoService.save(musica);
        return new ModelAndView("redirect:/musicas");
    }

    @GetMapping("/musicas/pesquisar")
    public ModelAndView pesquisar(@RequestParam("titulo") String titulo) {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findByTitulo(titulo);
        mv.addObject("musicas", musicas);
        return mv;
    }

    @GetMapping("/musicas/categoria/pesquisar")
    public ModelAndView pesquisarMusicaByCategoria(@RequestParam("nome") String nome) {
        ModelAndView mv = new ModelAndView("musicas");
        List<Categoria> categoria = categoriaService.findByNome(nome);
        List<Musica> musicas = new ArrayList<>();
        categoria.forEach(categoriaResul -> {
            List<Musica> musicaByIdCategoria = catalogoService.findMusicaByIdCategoria(categoriaResul.getId());
            musicas.addAll(musicaByIdCategoria);
        });
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/delMusica/{id}", method = RequestMethod.GET)
    public String delMusica(@PathVariable("id") long id) {
        catalogoService.excluir(id);
        return "redirect:/musicas";
    }

    @ModelAttribute("findAllCategorias")
    public List<Categoria> findAllCategorias() {
        Page<Categoria> all = categoriaService.findAll(PageRequest.of(0, 10));
        return all.getContent();
    }
}