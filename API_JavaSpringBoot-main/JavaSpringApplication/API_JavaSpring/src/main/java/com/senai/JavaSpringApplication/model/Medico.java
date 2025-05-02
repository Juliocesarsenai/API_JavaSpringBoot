package com.senai.JavaSpringApplication.model;

import com.senai.JavaSpringApplication.enums.Sexo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("MEDICO")
public class Medico extends Funcionario {

    private String crm;

    public Medico() {
    }

    public Medico(Long id, @NotBlank(message = "Nome é obrigatório") String nome,
                  @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
                  @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres") String senha,
                  double salario, Sexo sexo, String crm) {
        super(id, nome, email, senha, salario, sexo);
        this.crm = crm;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public String toString() {
        return "Medico \nCrm" + crm  + super.toString();
    }
}
