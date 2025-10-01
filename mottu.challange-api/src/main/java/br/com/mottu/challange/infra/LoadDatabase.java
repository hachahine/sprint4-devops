package br.com.mottu.challange.infra; // Ou qualquer outro pacote

import br.com.mottu.challange.domain.entity.User;
import br.com.mottu.challange.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByLogin("guilherme.santos") == null) {
            String rawPassword = "123456";
            String encodedPassword = passwordEncoder.encode(rawPassword);

            System.out.println("----------------------------------------------------");
            System.out.println("Senha: " + rawPassword);
            System.out.println("Senha Criptografada: " + encodedPassword);
            System.out.println("----------------------------------------------------");

            User newUser = new User(null, "guilherme.santos", encodedPassword);
            userRepository.save(newUser);
            System.out.println("Usuário criado com sucesso!");
        }
    }
}