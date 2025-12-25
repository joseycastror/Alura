package com.alura.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.LiteraluraRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LiteraluraRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.MuestraElMenu();
		/* 
		var consumoApi = new RequestAPI();
		var convertirDatos = new ConvertirDatos();
		var json = consumoApi.obtenerDatos("https://gutendex.com/books/?search=pride");
		
		DatosConsulta datos = convertirDatos.obtenerDatos(json, DatosConsulta.class);
		System.out.println(datos);
		System.out.println(datos.getClass());
		*/

		/*
		Requisitos para correr el programa:
		- Configurar las variables locales conforme a application properties
		// https://www.aluracursos.com/blog/como-configurar-variables-de-entorno-en-windows-linux-y-macos
		- Tener una base de datos ya creada para acumular los datos que corresponda con la variable local de BD
		*/
	}
}
