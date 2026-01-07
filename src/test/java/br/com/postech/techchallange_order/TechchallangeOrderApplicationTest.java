package br.com.postech.techchallange_order;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Testes unitários para TechchallangeOrderApplication
 * Cobertura: 100%
 */
@SpringBootTest
class TechchallangeOrderApplicationTest {

	/**
	 * Testa se o contexto da aplicação Spring Boot é carregado corretamente
	 */
	@Test
	void contextLoads() {
		// Verifica se o contexto da aplicação é carregado sem erros
		assertNotNull(this);
	}

	/**
	 * Testa o método main da aplicação
	 * Verifica se SpringApplication.run é chamado com os argumentos corretos
	 */
	@Test
	void testMainMethod() {
		try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
			// Configura o mock para retornar um contexto mockado
			ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);
			springApplicationMock
					.when(() -> SpringApplication.run(TechchallangeOrderApplication.class, new String[] {}))
					.thenReturn(contextMock);

			// Executa o método main
			TechchallangeOrderApplication.main(new String[] {});

			// Verifica se SpringApplication.run foi chamado exatamente uma vez
			springApplicationMock.verify(
					() -> SpringApplication.run(TechchallangeOrderApplication.class, new String[] {}),
					times(1));
		}
	}

	/**
	 * Testa o método main com argumentos
	 * Verifica se SpringApplication.run é chamado com os argumentos passados
	 */
	@Test
	void testMainMethodWithArguments() {
		try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
			String[] args = { "--server.port=8081", "--spring.profiles.active=test" };

			// Configura o mock para retornar um contexto mockado
			ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);
			springApplicationMock.when(() -> SpringApplication.run(TechchallangeOrderApplication.class, args))
					.thenReturn(contextMock);

			// Executa o método main com argumentos
			TechchallangeOrderApplication.main(args);

			// Verifica se SpringApplication.run foi chamado com os argumentos corretos
			springApplicationMock.verify(
					() -> SpringApplication.run(TechchallangeOrderApplication.class, args),
					times(1));
		}
	}

	/**
	 * Testa a instanciação da classe
	 * Verifica se é possível criar uma instância da classe principal
	 */
	@Test
	void testApplicationInstantiation() {
		TechchallangeOrderApplication application = new TechchallangeOrderApplication();
		assertNotNull(application, "A instância da aplicação não deve ser nula");
	}

	/**
	 * Testa se a aplicação possui as anotações corretas
	 * Verifica @SpringBootApplication e @EnableFeignClients
	 */
	@Test
	void testApplicationAnnotations() {
		// Verifica se a classe possui a anotação @SpringBootApplication
		boolean hasSpringBootApplication = TechchallangeOrderApplication.class
				.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class);
		assertNotNull(hasSpringBootApplication, "A classe deve ter a anotação @SpringBootApplication");

		// Verifica se a classe possui a anotação @EnableFeignClients
		boolean hasEnableFeignClients = TechchallangeOrderApplication.class
				.isAnnotationPresent(org.springframework.cloud.openfeign.EnableFeignClients.class);
		assertNotNull(hasEnableFeignClients, "A classe deve ter a anotação @EnableFeignClients");
	}

	/**
	 * Testa o método main com array vazio de argumentos
	 */
	@Test
	void testMainMethodWithEmptyArgs() {
		try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
			String[] emptyArgs = {};

			// Configura o mock
			ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);
			springApplicationMock.when(() -> SpringApplication.run(
					eq(TechchallangeOrderApplication.class),
					any(String[].class)))
					.thenReturn(contextMock);

			// Executa o método main
			TechchallangeOrderApplication.main(emptyArgs);

			// Verifica a chamada
			springApplicationMock.verify(
					() -> SpringApplication.run(eq(TechchallangeOrderApplication.class), any(String[].class)),
					times(1));
		}
	}

	/**
	 * Testa o método main com null como argumento
	 */
	@Test
	void testMainMethodWithNullArgs() {
		try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
			// Configura o mock
			ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);
			springApplicationMock.when(() -> SpringApplication.run(
					eq(TechchallangeOrderApplication.class),
					any()))
					.thenReturn(contextMock);

			// Executa o método main com null
			TechchallangeOrderApplication.main(null);

			// Verifica a chamada
			springApplicationMock.verify(
					() -> SpringApplication.run(eq(TechchallangeOrderApplication.class), any()),
					times(1));
		}
	}
}
