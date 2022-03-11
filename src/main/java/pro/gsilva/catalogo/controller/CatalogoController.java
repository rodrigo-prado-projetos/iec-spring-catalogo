package pro.gsilva.catalogo.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.service.CatalogoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogoController {
    
    @Autowired 
    private CatalogoService catalogoService;

    @RequestMapping(value="/musicas", method=RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value="/musicas/{id}", method=RequestMethod.GET)
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

    @RequestMapping(value="/addMusica", method=RequestMethod.GET)
    public String getMusicaForm(Musica musica) {
        return "musicaForm";
    }
    
    @RequestMapping(value="/addMusica", method=RequestMethod.POST)
    public ModelAndView salvarMusica(@Valid @ModelAttribute("musica") Musica musica, 
           BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelAndView musicaForm = new ModelAndView("musicaForm");
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
    
    @RequestMapping(value="/delMusica/{id}", method=RequestMethod.GET)
    public String delMusica(@PathVariable("id") long id) {
        catalogoService.excluir(id);
        return "redirect:/musicas";
    }
        

}
