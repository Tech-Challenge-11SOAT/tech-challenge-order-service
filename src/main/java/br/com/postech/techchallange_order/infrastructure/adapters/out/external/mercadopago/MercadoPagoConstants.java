package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.util.List;

/**
 * Constantes para integração com MercadoPago
 */
public final class MercadoPagoConstants {

	private MercadoPagoConstants() {
	}

	public static final String MERCADO_PAGO_FIRST_NAME = "APRO";
	public static final String MERCADO_PAGO_TEST_EMAIL = "test@testuser.com";

	public static final String MERCADO_PAGO_PAYMENT_METHOD_ID = "pix";
	public static final String MERCADO_PAGO_PAYMENT_METHOD_TYPE = "bank_transfer";

	public static final List<String> PAYMENTS_METHODS = List.of(
			MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID);
}
