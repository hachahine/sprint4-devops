package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.entity.Employee;
import br.com.mottu.challange.domain.repository.EmployeeRepository;
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
@RequestMapping("/web/employees")
public class EmployeeWebController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping
    public String listEmployees(@PageableDefault(size = 10, sort = "name") Pageable pageable, Model model) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        model.addAttribute("employeePage", employeePage);
        return "employees/list";
    }

    private void loadFormDependencies(Model model) {
        model.addAttribute("allUnits", unitRepository.findAll());
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Cadastrar Novo Funcionário");
        loadFormDependencies(model);
        return "employees/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do funcionário inválido: " + id));
        model.addAttribute("employee", employee);
        model.addAttribute("pageTitle", "Editar Funcionário (ID: " + id + ")");
        loadFormDependencies(model);
        return "employees/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        employeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("message", "Funcionário salvo com sucesso!");
        return "redirect:/web/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Funcionário com ID " + id + " foi deletado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message_error", "Erro ao deletar funcionário.");
        }
        return "redirect:/web/employees";
    }
}