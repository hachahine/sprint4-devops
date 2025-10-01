package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.entity.Device;
import br.com.mottu.challange.domain.entity.StatusColor;
import br.com.mottu.challange.domain.repository.DeviceRepository;
import br.com.mottu.challange.domain.repository.MotorcycleRepository;
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
@RequestMapping("/web/devices")
public class DeviceWebController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @GetMapping
    public String listDevices(@PageableDefault(size = 10, sort = "code") Pageable pageable, Model model) {
        Page<Device> devicePage = deviceRepository.findAll(pageable);
        model.addAttribute("devicePage", devicePage);
        return "devices/list";
    }

    private void loadFormDependencies(Model model) {
        model.addAttribute("allYards", yardRepository.findAll());
        model.addAttribute("allMotorcycles", motorcycleRepository.findAll());
        model.addAttribute("allStatusColors", StatusColor.values());
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("pageTitle", "Cadastrar Novo Dispositivo");
        loadFormDependencies(model);
        return "devices/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do dispositivo inválido: " + id));
        model.addAttribute("device", device);
        model.addAttribute("pageTitle", "Editar Dispositivo (ID: " + id + ")");
        loadFormDependencies(model);
        return "devices/form";
    }

    @PostMapping("/save")
    public String saveDevice(@ModelAttribute Device device, RedirectAttributes redirectAttributes) {
        deviceRepository.save(device);
        redirectAttributes.addFlashAttribute("message", "Dispositivo salvo com sucesso!");
        return "redirect:/web/devices";
    }

    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            deviceRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Dispositivo com ID " + id + " foi deletado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message_error", "Erro ao deletar dispositivo.");
        }
        return "redirect:/web/devices";
    }
}