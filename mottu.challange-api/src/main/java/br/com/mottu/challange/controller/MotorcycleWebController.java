package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.entity.Motorcycle;
import br.com.mottu.challange.domain.repository.DeviceRepository;
import br.com.mottu.challange.domain.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/motorcycles")
public class MotorcycleWebController {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public String listMotorcycles(@PageableDefault(size = 10, sort = "id") Pageable pageable, Model model) {
        Page<Motorcycle> motorcyclePage = motorcycleRepository.findAll(pageable);
        model.addAttribute("motorcyclePage", motorcyclePage);
        return "motorcycles/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("motorcycle", new Motorcycle());
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("pageTitle", "Cadastrar Nova Moto");
        return "motorcycles/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID da moto inválido: " + id));
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("pageTitle", "Editar Moto (ID: " + id + ")");
        return "motorcycles/form";
    }

    @PostMapping("/save")
    public String saveMotorcycle(@ModelAttribute Motorcycle motorcycle, RedirectAttributes redirectAttributes) {
        motorcycleRepository.save(motorcycle);
        redirectAttributes.addFlashAttribute("message", "Moto salva com sucesso!");
        return "redirect:/web/motorcycles";
    }

    @GetMapping("/delete/{id}")
    public String deleteMotorcycle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            motorcycleRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Moto com ID " + id + " foi deletada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message_error", "Erro ao deletar moto.");
        }
        return "redirect:/web/motorcycles";
    }
}