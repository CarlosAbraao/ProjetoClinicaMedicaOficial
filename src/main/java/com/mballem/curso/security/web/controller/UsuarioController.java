package com.mballem.curso.security.web.controller;


import com.mballem.curso.security.domain.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("u")
public class UsuarioController {


    //ABRIR CADASTRO DE USUARIOS
    // QUEM PODE CADASTRar uM USUARIO (MEDICO/ADMIN/PACIENTE)

    @GetMapping("/novo/cadastro/usuario")
    public String cadastroPorAdminParaAdminMedicoPaciente(Usuario usuario){
        return "usuario/cadastro";
    }



}