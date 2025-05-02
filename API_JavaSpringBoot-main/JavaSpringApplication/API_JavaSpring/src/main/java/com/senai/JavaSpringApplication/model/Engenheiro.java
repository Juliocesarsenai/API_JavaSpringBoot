package com.senai.JavaSpringApplication.model;

import com.senai.JavaSpringApplication.enums.Sexo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("ENGENHEIRO")
public class Engenheiro extends Funcionario {

    private String crea;

    public Engenheiro() {
    }

    public Engenheiro(Long id, 
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") 
        @Email(message = "Email inválido") String email,
        @NotBlank(message = "Senha é obrigatória") 
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres") String senha,
        Double salario, 
        Sexo sexo, 
        String crea) {
        super(id, nome, email, senha, salario, sexo);
        this.crea = crea;
    }

    public String getCrea() {
        return crea;
    }

    public void setCrea(String crea) {
        this.crea = crea;
    }

    @Override
    public String toString() {
        return "Engenheiro \nid:" + getId() + "nome:" + getNome() + "email:" + getEmail() + 
               ", salario:" + getSalario() + "sexo:" + getSexo() + "crea:" + crea;
    }
}
