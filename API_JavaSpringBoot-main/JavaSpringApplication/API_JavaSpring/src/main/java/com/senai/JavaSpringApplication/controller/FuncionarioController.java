package com.senai.JavaSpringApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.JavaSpringApplication.model.Funcionario;
import com.senai.JavaSpringApplication.service.FuncionarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<Funcionario> listarFuncionarios(){
        return funcionarioService.listarFuncionarios();
    }

    @GetMapping("/{id}")
    public Optional<Funcionario> buscarPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id);
    }

    @PostMapping 
    public Funcionario salvarFuncionario(@Valid @RequestBody Funcionario funcionario) {
        return funcionarioService.salvarFuncionarios(funcionario);
    }

    @PutMapping("/{id}")
    public Funcionario atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario){
        funcionario.setId(id);
        return funcionarioService.atualizar(funcionario);
    }

    @DeleteMapping("/{id}")
    public void deletarFuncionarios(@PathVariable Long id) {
        funcionarioService.deletarFuncionarios(id);
    }

    

}
