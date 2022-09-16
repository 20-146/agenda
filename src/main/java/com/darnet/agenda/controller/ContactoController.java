package com.darnet.agenda.controller;

import com.darnet.agenda.model.Contacto;
import com.darnet.agenda.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ContactoController {
    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping
    public String index(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String busqueda,
            Model model)
    {
        Page<Contacto> contactoPage;
        if (busqueda != null && busqueda.trim().length() > 0 ) {
            contactoPage = contactoRepository.findByNombreContaining(busqueda, pageable);
        } else {
            contactoPage = contactoRepository.findAll(pageable);
        }

        model.addAttribute("contactosPage", contactoPage);
        return "index";
    }

    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    String crear(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }

        contacto.setFechaRegistro(LocalDateTime.now());
        contactoRepository.save(contacto);

        redirectAttributes.addFlashAttribute("msgExito", "EL contacto fue salvado correctamente");
        return "redirect:/";
    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model) {
        Contacto contacto = contactoRepository.getById(id);
        model.addAttribute("contacto", contacto);
        return "nuevo";
    }

    @PostMapping("/{id}/editar")
    String actualizar(
            @PathVariable Integer id,
            @Validated Contacto contacto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }

        Contacto contactoFromDB = contactoRepository.getById(id);
        contactoFromDB.setNombre(contacto.getNombre());
        contactoFromDB.setFechaNacimiento(contacto.getFechaNacimiento());
        contactoFromDB.setCelular(contacto.getCelular());
        contactoFromDB.setEmail(contacto.getEmail());

        contactoRepository.save(contactoFromDB);

        redirectAttributes.addFlashAttribute("msgExito", "EL contacto fue actualizado correctamente");
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
        Contacto contacto = contactoRepository.getById(id);
        String Nombre = contacto.getNombre();
        contactoRepository.delete(contacto);

        redirectAttributes.addFlashAttribute("msgExito", "EL contacto " + Nombre + " ha sido eliminado");
        return "redirect:/";
    }

}

