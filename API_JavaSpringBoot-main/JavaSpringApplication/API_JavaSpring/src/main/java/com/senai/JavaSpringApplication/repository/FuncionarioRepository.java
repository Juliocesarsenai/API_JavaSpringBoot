package com.senai.JavaSpringApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senai.JavaSpringApplication.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository <Funcionario,Long>{
    Optional<Funcionario> findByNome(String nome);

}
