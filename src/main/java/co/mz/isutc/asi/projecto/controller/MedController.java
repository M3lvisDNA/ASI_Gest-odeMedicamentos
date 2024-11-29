package co.mz.isutc.asi.projecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import co.mz.isutc.asi.projecto.model.Medicamentos;
import co.mz.isutc.asi.projecto.repository.MedRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/farmacia")
public class MedController {
    private final MedRepository medRepository;

    public MedController(MedRepository medRepository){
        this.medRepository = medRepository;
    }
    //lista todos os dados salvos na base de dados
    @GetMapping
    public String listMed(Model model){
        model.addAttribute("farmacia", medRepository.findAll());
        return "farmacia/list";
    }
    //abri a pagina onde se cria ou se adiciona o novo produto a base de dados
    @GetMapping("/new")
    public String medCreateForm(Model model){
        model.addAttribute("medicamentos", new Medicamentos());
        return "farmacia/registar";
    }

    @PostMapping
    public String createMed(@ModelAttribute("medicamentos") Medicamentos medicamentos) {
        medRepository.save(medicamentos);
        return "redirect:/farmacia";
    }
    //Edita os dados, os dados sao tirados da base de dados usando como referencia o ID
    @GetMapping("/edit/{id}")
    public String medShowForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("medicamentos", medRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O ID invalido:" +id)));
        return "farmacia/edit";
    }
    @PostMapping("/update/{id}")
    public String updateMed(@PathVariable("id") Long id, @ModelAttribute("medicamentos") Medicamentos medicamentos){
        medicamentos.setId(id);
        medRepository.save(medicamentos);
        return "redirect:/farmacia";
    }
    //deleta o produto, ele deleta indo procurar o produto usando como referencia o ID depois o elimina da base de dados
    @GetMapping("/delete/{id}")
    public String deleteMed(@PathVariable("id") Long id) {
        medRepository.deleteById(id);
        return "redirect:/farmacia";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

}
