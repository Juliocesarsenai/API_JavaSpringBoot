package com.senai.JavaSpringApplication.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.JavaSpringApplication.model.Funcionario;
import com.senai.JavaSpringApplication.security.JwtUtil;
import com.senai.JavaSpringApplication.enums.Sexo;
import com.senai.JavaSpringApplication.service.FuncionarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(FuncionarioService funcionarioService, BCryptPasswordEncoder passwordEncoder) {
        this.funcionarioService = funcionarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
public ResponseEntity<?> registerFuncionario(@RequestBody Map<String, String> request) {
    String nome = request.get("nome");
    String email = request.get("email");
    String senha = request.get("senha");             // senha pura
    // não criptografe aqui!
    String sexoStr = request.get("sexo");
    Double salario = Double.parseDouble(request.get("salario"));
    String tipoFuncionario = request.get("tipoFuncionario");
    String registro = request.get("registro");

    Sexo sexo = Sexo.valueOf(sexoStr.toUpperCase());
    Funcionario funcionario = funcionarioService.registrarFuncionario(
        nome, email, senha, salario, sexo, tipoFuncionario, registro
    );

    return ResponseEntity.ok(Map.of(
        "mensagem", "Funcionário registrado com sucesso!",
        "funcionario", funcionario
    ));
}

   

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String nomeEnviado = request.get("nome");
        String senhaEnviada = request.get("senha");
    
        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Nome enviado no login: " + nomeEnviado);
        System.out.println("Senha enviada no login: " + senhaEnviada);
    
        Optional<Funcionario> funcionario = funcionarioService.buscarPorNome(nomeEnviado);
    
        if (funcionario.isPresent()) {
            System.out.println("Funcionario encontrado no banco: " + funcionario.get().getNome());
            System.out.println("Senha no banco (criptografada): " + funcionario.get().getSenha());
    
            boolean senhaConfere = passwordEncoder.matches(senhaEnviada, funcionario.get().getSenha());
            System.out.println("Senha confere? " + senhaConfere);
    
            if (senhaConfere) {
                String token = JwtUtil.generateToken(funcionario.get().getNome());
                System.out.println("Token gerado: " + token);
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                System.out.println("Senha não confere.");
            }
        } else {
            System.out.println("Nenhum funcionário encontrado com esse nome.");
        }
    
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
    

    @GetMapping("/funcionario/perfil")
    public ResponseEntity<?> perfil() {
        return ResponseEntity.ok("Acesso Permitido!");
    }
}
