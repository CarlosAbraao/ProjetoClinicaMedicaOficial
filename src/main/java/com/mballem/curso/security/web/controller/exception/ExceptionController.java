package com.mballem.curso.security.web.controller.exception;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
// regras para anotações


@ControllerAdvice
public class ExceptionController {

    // CRIANDO EXCEÇOES PARA ERROS
    // CRIANDO METODOS PARA CAPTURAR EXCEÇOS


    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView usuarioNaoEncontradoExcepption(UsernameNotFoundException ex){


        ModelAndView model = new ModelAndView("error");

        model.addObject("status",404);
        model.addObject("error","Operação não pode ser realizada");
        model.addObject("message",ex.getMessage());



        return model;
    }

    }






