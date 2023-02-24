package com.mballem.curso.security.web.controller;


import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;

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


    // SALVAR UM CADASTRO DE USUARIO

    @PostMapping("/cadastro/salvar")
    public String salvarUsuarios(Usuario usuario, RedirectAttributes attr){

        List<Perfil> perfis = usuario.getPerfis();

        if (perfis.size() > 2 ||

                perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L))) ||
                perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {

            attr.addFlashAttribute("falha", "Paciente não pode ser Amin e/ou Médioco. ");
            attr.addFlashAttribute("usuario", usuario);

        }   else {
            // CASO  HAJA DUPLICAÇÃO DE USUARIO MSTRA UMA EXCESSAO
            try {
                service.savarUsuario(usuario);
                attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
            } catch (DataIntegrityViolationException ex){
                attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
            }

        }
        return "redirect:/u/novo/cadastro/usuario";
    }


}
