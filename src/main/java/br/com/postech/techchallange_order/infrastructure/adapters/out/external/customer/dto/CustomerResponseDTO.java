package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

	private String id;

	@JsonProperty("clienteId")
	private String clienteId;

	@JsonProperty("nomeCliente")
	private String nomeCliente;

	@JsonProperty("emailCliente")
	private String emailCliente;

	@JsonProperty("cpfCliente")
	private String cpfCliente;

	private String telefone;

	private Endereco endereco;

	private Boolean ativo;

	@JsonProperty("dataCadastro")
	private LocalDateTime dataCadastro;

	@JsonProperty("dataUltimaAtualizacao")
	private LocalDateTime dataUltimaAtualizacao;

	private Integer versao;

	private Object metadata;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Endereco {
		private String rua;
		private String numero;
		private String complemento;
		private String bairro;
		private String cidade;
		private String estado;
		private String cep;
	}
}
