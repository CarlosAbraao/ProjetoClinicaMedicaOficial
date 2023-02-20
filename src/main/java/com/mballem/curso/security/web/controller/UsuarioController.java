package com.mballem.curso.security.web.controller;


import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@Controller
@RequestMapping("u")
public class UsuarioController {

@Autowired
private UsuarioService service;

    //ABRIR CADASTRO DE USUARIOS
    // QUEM PODE CADASTRar uM USUARIO (MEDICO/ADMIN/PACIENTE)

    @GetMapping("/novo/cadastro/usuario")
    public String cadastroPorAdminParaAdminMedicoPaciente(Usuario usuario){
        return "usuario/cadastro";
    }

    //ABRIR LISTA DE USUARIOS DA DATATABLES

    @GetMapping("/lista")
    public String listarUsuarios(Usuario usuario){


        return "usuario/lista";
    }


    @GetMapping("/datatables/server/usuarios")
    public ResponseEntity<?> listarUsuariosDataTables(HttpServletRequest request){

        return ResponseEntity.ok(service.buscarTodos(request));




    }

}
