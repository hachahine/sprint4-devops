package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.entity.Unit;
import br.com.mottu.challange.domain.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/units")
public class UnitWebController {

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping
    public String listUnits(@PageableDefault(size = 10, sort = "name") Pageable pageable, Model model) {
        Page<Unit> unitPage = unitRepository.findAll(pageable);
        model.addAttribute("unitPage", unitPage);
        return "units/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("unit", new Unit());
        model.addAttribute("pageTitle", "Cadastrar Nova Unidade");
        return "units/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da unidade inválido: " + id));
        model.addAttribute("unit", unit);
        model.addAttribute("pageTitle", "Editar Unidade (ID: " + id + ")");
        return "units/form";
    }

    @PostMapping("/save")
    public String saveUnit(@ModelAttribute Unit unit, RedirectAttributes redirectAttributes) {
        unitRepository.save(unit);
        redirectAttributes.addFlashAttribute("message", "Unidade salva com sucesso!");
        return "redirect:/web/units";
    }

    @GetMapping("/delete/{id}")
    public String deleteUnit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            unitRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Unidade com ID " + id + " foi deletada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message_error", "Erro ao deletar unidade.");
        }
        return "redirect:/web/units";
    }
}