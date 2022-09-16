package com.darnet.agenda.controller;

import com.darnet.agenda.model.Contacto;
import com.darnet.agenda.model.Inversion;
import com.darnet.agenda.repository.ContactoRepository;
import com.darnet.agenda.repository.InversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/inversions")
public class InversionController {

    @Autowired
    private InversionRepository inversionRepository;

    @GetMapping
    public String index(Model model) {
        List<Inversion> inversions = inversionRepository.findAll();
        model.addAttribute("contactos", inversions);
        return "inversions";
    }

    @GetMapping("/nuevoinversion")
    String nuevo(Model model) {
        model.addAttribute("inversion", new Inversion());
        return "nuevoinversion";
    }

    @PostMapping("/nuevoinversion")
    String crear(@Validated Inversion inversion, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inversion", inversion);
            return "nuevoinversion";
        }

        inversion.setFecha(LocalDate.now());
        inversionRepository.save(inversion);

        redirectAttributes.addFlashAttribute("msgExito", "La inversion fue creada correctamente");
        return "redirect:/";
    }
//
//    @GetMapping("/{id}/editar")
//    String editar(@PathVariable Integer id, Model model) {
//        Contacto contacto = contactoRepository.getById(id);
//        model.addAttribute("contacto", contacto);
//        return "nuevo";
//    }
//
//    @PostMapping("/{id}/editar")
//    String actualizar(
//            @PathVariable Integer id,
//            @Validated Contacto contacto,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes,
//            Model model)
//    {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("contacto", contacto);
//            return "nuevo";
//        }
//
//        Contacto contactoFromDB = contactoRepository.getById(id);
//        contactoFromDB.setNombre(contacto.getNombre());
//        contactoFromDB.setFechaNacimiento(contacto.getFechaNacimiento());
//        contactoFromDB.setCelular(contacto.getCelular());
//        contactoFromDB.setEmail(contacto.getEmail());
//
//        contactoRepository.save(contactoFromDB);
//
//        redirectAttributes.addFlashAttribute("msgExito", "EL contacto fue actualizado correctamente");
//        return "redirect:/";
//    }
//
//    @PostMapping("/{id}/delete")
//    String delete(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
//        Contacto contacto = contactoRepository.getById(id);
//        String Nombre = contacto.getNombre();
//        contactoRepository.delete(contacto);
//
//        redirectAttributes.addFlashAttribute("msgExito", "EL contacto " + Nombre + " ha sido eliminado");
//        return "redirect:/";
//    }
}
