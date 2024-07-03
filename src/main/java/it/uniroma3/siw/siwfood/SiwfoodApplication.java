package it.uniroma3.siw.siwfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@SpringBootApplication
public class SiwfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiwfoodApplication.class, args);
    }
}
