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
import pro.gsilva.catalogo.service.CategoriaService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/categorias", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView getCategorias(@RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ModelAndView mv = new ModelAndView("categorias");
        Page<Categoria> categorias = categoriaService.findAll(PageRequest.of(currentPage - 1, pageSize));
        mv.addObject("categorias", categorias);

        int totalPages = categorias.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }

        return mv;
    }


    @GetMapping("/categorias/pesquisar")
    public ModelAndView pesquisar(@RequestParam("nome") String nome) {
        ModelAndView mv = new ModelAndView("categorias");
        List<Categoria> categorias = categoriaService.findByNome(nome);
        mv.addObject("categorias", categorias);
        return mv;
    }


    @RequestMapping(value = "/categoria", method = RequestMethod.GET)
    public ModelAndView getCategorias() {
        ModelAndView mv = new ModelAndView("categorias");
        Page<Categoria> categorias = categoriaService.findAll(PageRequest.of(0, 10));
        mv.addObject("categorias", categorias.getContent());
        return mv;
    }

    @RequestMapping(value = "/categorias/teste", method = RequestMethod.GET)
    public ModelAndView getTodasCategoria() {
        ModelAndView mv = new ModelAndView("musicaForm");
        Page<Categoria> categorias = categoriaService.findAll(PageRequest.of(0, 10));
        mv.addObject("categoria", categorias.getContent());
        return mv;
    }

    @RequestMapping(value = "/categorias/{id}", method = RequestMethod.GET)
    public ModelAndView getCategoriaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Categoria categoria = categoriaService.findById(id);
        mv.addObject("categoria", categoria);
        return mv;
    }

    @RequestMapping(value = "/addCategoria", method = RequestMethod.GET)
    public String getCategoriaForm(Categoria categoria) {
        return "categoriaForm";
    }

    @RequestMapping(value = "/addCategoria", method = RequestMethod.POST)
    public ModelAndView salvarMusica(@Valid @ModelAttribute("categoria") Categoria categoria,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelAndView categoriaForm = new ModelAndView("categoriaForm");
            categoriaForm.addObject("mensagem", "Verifique os errors do formulário");
            return categoriaForm;
        }
        categoriaService.save(categoria);
        return new ModelAndView("redirect:/musicas");
    }

    @RequestMapping(value = "/categorias/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getCategoriaFormEdit(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("categoriaForm");
        Categoria categoria = categoriaService.findById(id);
        mv.addObject("categoria", categoria);
        return mv;
    }

    @RequestMapping(value = "/delCategoria/{id}", method = RequestMethod.GET)
    public String delCategoria(@PathVariable("id") long id) {
        categoriaService.excluir(id);
        return "redirect:/categorias";
    }
}