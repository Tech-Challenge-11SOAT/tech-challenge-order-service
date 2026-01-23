package br.com.postech.techchallange_order.bdd.hooks;

import br.com.postech.techchallange_order.bdd.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CucumberHooks {

	private final TestContext testContext;

	@Before
	public void beforeScenario(Scenario scenario) {
		log.info("========================================");
		log.info("Iniciando cenário: {}", scenario.getName());
		log.info("========================================");
		testContext.reset();
	}

	@After
	public void afterScenario(Scenario scenario) {
		log.info("========================================");
		log.info("Finalizando cenário: {} - Status: {}",
				scenario.getName(), scenario.getStatus());
		log.info("========================================");

		if (scenario.isFailed()) {
			log.error("Cenário falhou: {}", scenario.getName());
			if (testContext.getLastException() != null) {
				log.error("Exceção capturada:", testContext.getLastException());
			}
		}
	}
}
