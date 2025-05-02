package com.senai.JavaSpringApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.JavaSpringApplication.enums.Sexo;
import com.senai.JavaSpringApplication.model.Engenheiro;
import com.senai.JavaSpringApplication.model.Funcionario;
import com.senai.JavaSpringApplication.model.Medico;
import com.senai.JavaSpringApplication.repository.FuncionarioRepository;

import jakarta.validation.Valid;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Funcionario registrarFuncionario(
    String nome,
    String email,
    String senha,           
    double salario,
    Sexo sexo,
    String tipoFuncionario,
    String registro
) {
   
    String senhaCriptografada = passwordEncoder.encode(senha);
    Funcionario funcionario;
    if ("medico".equalsIgnoreCase(tipoFuncionario)) {
        funcionario = new Medico(null, nome, email, senhaCriptografada, salario, sexo, registro);
    } else if ("engenheiro".equalsIgnoreCase(tipoFuncionario)) {
        funcionario = new Engenheiro(null, nome, email, senhaCriptografada, salario, sexo, registro);
    } else {
        funcionario = new Funcionario(null, nome, email, senhaCriptografada, salario, sexo);
    }
    return funcionarioRepository.save(funcionario);
}


    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario salvarFuncionarios(@Valid Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void deletarFuncionarios(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public Funcionario atualizar(@Valid Funcionario funcionario) {
        Funcionario funcionarioAtualizar = funcionarioRepository.findById(funcionario.getId())
            .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        funcionarioAtualizar.setNome(funcionario.getNome());
        funcionarioAtualizar.setEmail(funcionario.getEmail());
        funcionarioAtualizar.setSenha(funcionario.getSenha());
        funcionarioAtualizar.setSalario(funcionario.getSalario());
        funcionarioAtualizar.setSexo(funcionario.getSexo());

        if (funcionario.getSenha() != null && !funcionario.getSenha().isEmpty()) {
            funcionarioAtualizar.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        }

        if (funcionarioAtualizar instanceof Engenheiro && funcionario instanceof Engenheiro) {
            Engenheiro engenheiroAtualizar = (Engenheiro) funcionarioAtualizar;
            Engenheiro engenheiro = (Engenheiro) funcionario;
            engenheiroAtualizar.setCrea(engenheiro.getCrea());
        } else if (funcionarioAtualizar instanceof Medico && funcionario instanceof Medico) {
            Medico medicoAtualizar = (Medico) funcionarioAtualizar;
            Medico medico = (Medico) funcionario;
            medicoAtualizar.setCrm(medico.getCrm());
        }

        return funcionarioRepository.save(funcionarioAtualizar);
    }

    public Optional<Funcionario> buscarPorNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }
}
