package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.entity.Yard;
import br.com.mottu.challange.domain.repository.UnitRepository;
import br.com.mottu.challange.domain.repository.YardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/yards")
public class YardWebController {

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping
    public String listYards(@PageableDefault(size = 10, sort = "name") Pageable pageable, Model model) {
        Page<Yard> yardPage = yardRepository.findAll(pageable);
        model.addAttribute("yardPage", yardPage);
        return "yards/list";
    }

    private void loadFormDependencies(Model model) {
        model.addAttribute("allUnits", unitRepository.findAll());
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("yard", new Yard());
        model.addAttribute("pageTitle", "Cadastrar Novo Pátio");
        loadFormDependencies(model);
        return "yards/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Yard yard = yardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do pátio inválido: " + id));
        model.addAttribute("yard", yard);
        model.addAttribute("pageTitle", "Editar Pátio (ID: " + id + ")");
        loadFormDependencies(model);
        return "yards/form";
    }

    @PostMapping("/save")
    public String saveYard(@ModelAttribute Yard yard, RedirectAttributes redirectAttributes) {
        yardRepository.save(yard);
        redirectAttributes.addFlashAttribute("message", "Pátio salvo com sucesso!");
        return "redirect:/web/yards";
    }

    @GetMapping("/delete/{id}")
    public String deleteYard(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            yardRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Pátio com ID " + id + " foi deletado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message_error", "Erro ao deletar pátio.");
        }
        return "redirect:/web/yards";
    }
}