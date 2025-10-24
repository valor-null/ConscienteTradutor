package br.com.fiap.conscientetradutor.web;

import br.com.fiap.conscientetradutor.core.ConscienteTradutorService;
import br.com.fiap.conscientetradutor.web.dto.TraducaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TraducaoController {

    private final ConscienteTradutorService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("req", new TraducaoDTO());
        return "index";
    }

    @PostMapping("/traduzir")
    public String traduzir(@ModelAttribute("req") TraducaoDTO req, Model model) {
        var resp = service.traduzir(req.getTextoOriginal(), req.isModoInclusivo(), req.isNeutralizarOfensas());
        model.addAttribute("resp", resp);
        return "index";
    }
}
