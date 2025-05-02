package com.senai.JavaSpringApplication.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.senai.JavaSpringApplication.model.Funcionario;
import com.senai.JavaSpringApplication.repository.FuncionarioRepository;

@Service
public class FuncionarioDetailsService implements UserDetailsService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioDetailsService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByNome(nome)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));
        return User .builder()
                .username(funcionario.getNome())
                .password(funcionario.getSenha())
                .roles("User")
                .build();
    }
}
