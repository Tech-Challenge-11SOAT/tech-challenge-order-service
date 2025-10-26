package br.com.postech.techchallange_order.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "mercadopago")
@Getter
@Setter
public class MercadoPagoOptionsConfig {

	private Api api = new Api();
	private Options options = new Options();

	@Getter
	@Setter
	public static class Api {
		private String baseUrl;
		private String accessToken;
		private String publicKey;
		private Integer timeout;
	}

	@Getter
	@Setter
	public static class Options {
		private Boolean testMode = true;
		private Boolean integrationActive = false;
	}
}
