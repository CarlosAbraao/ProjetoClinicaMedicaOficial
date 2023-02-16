package com.mballem.curso.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

	// abrir pagina home
	@GetMapping({"/","/home"})
	public String home() {
		return "home";
	}

	@GetMapping({"/login"})
	public String login() {
		return "login";
	}

	// LOGIN INVALIDO -> METODO DE ERRO-LOGIN INICIAL
	/*@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta","erro");
		model.addAttribute("titulo","Credenciais Invalidas!");
		model.addAttribute("texto","Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto","Acesso permitido apenas para cadastros já ativados");
		return "login";
	}*/

	//METODO DE ERRO ALTERADO
	@GetMapping({"/acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status",resp.getStatus());
		model.addAttribute("error","Acesso Negado");
		model.addAttribute("message","Você não tem permissão para acesso a esta area ou ação");

		return "error";
	}
}
