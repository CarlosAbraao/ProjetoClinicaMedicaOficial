package com.mballem.curso.security.web.controller;

import com.mballem.curso.security.domain.Medico;

import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.MedicoService;
import com.mballem.curso.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicoService service;


    //ABRIR PAGINA DE DADOS PESSOAIS DO MEDICO
    @GetMapping({"/dados"})
    public String abrirPorMedico(Medico medico, ModelMap model, @AuthenticationPrincipal User user){

        if(medico.hasNotId()){
            medico = service.buscarPorEmail(user.getUsername());
            model.addAttribute("medico", medico);
        }

        return "medico/cadastro";
    }



    // SALVAR MEDICO
    @PostMapping({"/salvar"})
    public String salvar(Medico medico, RedirectAttributes attr, @AuthenticationPrincipal User user) {
        if (medico.hasNotId() && medico.getUsuario().hasNotId()) {
            Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
            medico.setUsuario(usuario);


        }
        service.salvar(medico);
        attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
        attr.addFlashAttribute("medico", medico);
        return "redirect:/medicos/dados";
    }

    // EDITAR MEDICO
    @PostMapping ({"/editar"})
    public String editar(Medico medico, RedirectAttributes attr){

            service.editar(medico);

        service.salvar(medico);
        attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
        attr.addFlashAttribute("medico", medico);


        return "redirect:/medicos/dados";
    }
}
