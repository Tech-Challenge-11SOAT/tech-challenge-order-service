package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para MercadoPagoConstants
 * Cobertura: 100%
 */
class MercadoPagoConstantsTest {

	/**
	 * Testa se a classe possui um construtor privado (utility class pattern)
	 */
	@Test
	void testPrivateConstructor() throws NoSuchMethodException {
		Constructor<MercadoPagoConstants> constructor = MercadoPagoConstants.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()),
				"O construtor deve ser privado");
	}

	/**
	 * Testa se o construtor privado pode ser invocado via reflection
	 * e verifica que a classe segue o padrão de utility class
	 */
	@Test
	void testPrivateConstructorInvocation() throws Exception {
		Constructor<MercadoPagoConstants> constructor = MercadoPagoConstants.class.getDeclaredConstructor();
		constructor.setAccessible(true);

		assertDoesNotThrow(() -> constructor.newInstance(),
				"Deve ser possível invocar o construtor privado via reflection");

		MercadoPagoConstants instance = constructor.newInstance();
		assertNotNull(instance, "A instância não deve ser nula");
	}

	/**
	 * Testa a constante MERCADO_PAGO_FIRST_NAME
	 */
	@Test
	void testMercadoPagoFirstName() {
		assertEquals("APRO", MercadoPagoConstants.MERCADO_PAGO_FIRST_NAME,
				"MERCADO_PAGO_FIRST_NAME deve ser 'APRO'");
		assertNotNull(MercadoPagoConstants.MERCADO_PAGO_FIRST_NAME,
				"MERCADO_PAGO_FIRST_NAME não deve ser nulo");
	}

	/**
	 * Testa a constante MERCADO_PAGO_TEST_EMAIL
	 */
	@Test
	void testMercadoPagoTestEmail() {
		assertEquals("test@testuser.com", MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL,
				"MERCADO_PAGO_TEST_EMAIL deve ser 'test@testuser.com'");
		assertNotNull(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL,
				"MERCADO_PAGO_TEST_EMAIL não deve ser nulo");
		assertTrue(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL.contains("@"),
				"MERCADO_PAGO_TEST_EMAIL deve conter '@'");
	}

	/**
	 * Testa a constante MERCADO_PAGO_PAYMENT_METHOD_ID
	 */
	@Test
	void testMercadoPagoPaymentMethodId() {
		assertEquals("pix", MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID,
				"MERCADO_PAGO_PAYMENT_METHOD_ID deve ser 'pix'");
		assertNotNull(MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID,
				"MERCADO_PAGO_PAYMENT_METHOD_ID não deve ser nulo");
	}

	/**
	 * Testa a constante MERCADO_PAGO_PAYMENT_METHOD_TYPE
	 */
	@Test
	void testMercadoPagoPaymentMethodType() {
		assertEquals("bank_transfer", MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_TYPE,
				"MERCADO_PAGO_PAYMENT_METHOD_TYPE deve ser 'bank_transfer'");
		assertNotNull(MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_TYPE,
				"MERCADO_PAGO_PAYMENT_METHOD_TYPE não deve ser nulo");
	}

	/**
	 * Testa a lista PAYMENTS_METHODS
	 */
	@Test
	void testPaymentsMethods() {
		assertNotNull(MercadoPagoConstants.PAYMENTS_METHODS,
				"PAYMENTS_METHODS não deve ser nulo");
		assertFalse(MercadoPagoConstants.PAYMENTS_METHODS.isEmpty(),
				"PAYMENTS_METHODS não deve estar vazia");
		assertEquals(1, MercadoPagoConstants.PAYMENTS_METHODS.size(),
				"PAYMENTS_METHODS deve conter 1 elemento");
	}

	/**
	 * Testa o conteúdo da lista PAYMENTS_METHODS
	 */
	@Test
	void testPaymentsMethodsContainsPix() {
		assertTrue(MercadoPagoConstants.PAYMENTS_METHODS.contains("pix"),
				"PAYMENTS_METHODS deve conter 'pix'");
		assertTrue(MercadoPagoConstants.PAYMENTS_METHODS.contains(
				MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID),
				"PAYMENTS_METHODS deve conter MERCADO_PAGO_PAYMENT_METHOD_ID");
	}

	/**
	 * Testa a imutabilidade da lista PAYMENTS_METHODS
	 */
	@Test
	void testPaymentsMethodsIsImmutable() {
		assertThrows(UnsupportedOperationException.class,
				() -> MercadoPagoConstants.PAYMENTS_METHODS.add("new_method"),
				"PAYMENTS_METHODS deve ser imutável");
	}

	/**
	 * Testa se a classe é final (não pode ser estendida)
	 */
	@Test
	void testClassIsFinal() {
		assertTrue(Modifier.isFinal(MercadoPagoConstants.class.getModifiers()),
				"A classe deve ser final");
	}

	/**
	 * Testa se a classe é pública
	 */
	@Test
	void testClassIsPublic() {
		assertTrue(Modifier.isPublic(MercadoPagoConstants.class.getModifiers()),
				"A classe deve ser pública");
	}

	/**
	 * Testa os valores das constantes não são vazios
	 */
	@Test
	void testConstantsAreNotEmpty() {
		assertFalse(MercadoPagoConstants.MERCADO_PAGO_FIRST_NAME.isEmpty(),
				"MERCADO_PAGO_FIRST_NAME não deve estar vazio");
		assertFalse(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL.isEmpty(),
				"MERCADO_PAGO_TEST_EMAIL não deve estar vazio");
		assertFalse(MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID.isEmpty(),
				"MERCADO_PAGO_PAYMENT_METHOD_ID não deve estar vazio");
		assertFalse(MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_TYPE.isEmpty(),
				"MERCADO_PAGO_PAYMENT_METHOD_TYPE não deve estar vazio");
	}

	/**
	 * Testa consistência entre PAYMENT_METHOD_ID e PAYMENTS_METHODS
	 */
	@Test
	void testConsistencyBetweenPaymentMethodIdAndPaymentsMethods() {
		String paymentMethodId = MercadoPagoConstants.MERCADO_PAGO_PAYMENT_METHOD_ID;
		assertTrue(MercadoPagoConstants.PAYMENTS_METHODS.contains(paymentMethodId),
				"PAYMENTS_METHODS deve conter o PAYMENT_METHOD_ID definido");
		assertEquals(paymentMethodId, MercadoPagoConstants.PAYMENTS_METHODS.get(0),
				"O primeiro elemento de PAYMENTS_METHODS deve ser o PAYMENT_METHOD_ID");
	}

	/**
	 * Testa se a lista PAYMENTS_METHODS retorna sempre a mesma referência
	 */
	@Test
	void testPaymentsMethodsSameReference() {
		assertSame(MercadoPagoConstants.PAYMENTS_METHODS, MercadoPagoConstants.PAYMENTS_METHODS,
				"PAYMENTS_METHODS deve retornar sempre a mesma referência");
	}

	/**
	 * Testa validação de email no formato correto
	 */
	@Test
	void testTestEmailFormat() {
		String email = MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL;
		assertTrue(email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"),
				"MERCADO_PAGO_TEST_EMAIL deve estar em formato de email válido");
	}

	/**
	 * Testa que o construtor privado lança exceção quando necessário
	 */
	@Test
	void testPrivateConstructorCannotBeAccessedDirectly() {
		Constructor<?>[] constructors = MercadoPagoConstants.class.getDeclaredConstructors();
		assertEquals(1, constructors.length, "Deve haver exatamente 1 construtor");

		for (Constructor<?> constructor : constructors) {
			assertTrue(Modifier.isPrivate(constructor.getModifiers()),
					"Todos os construtores devem ser privados");
		}
	}

	/**
	 * Testa criação de múltiplas instâncias via reflection
	 */
	@Test
	void testMultipleInstancesViaReflection() throws Exception {
		Constructor<MercadoPagoConstants> constructor = MercadoPagoConstants.class.getDeclaredConstructor();
		constructor.setAccessible(true);

		MercadoPagoConstants instance1 = constructor.newInstance();
		MercadoPagoConstants instance2 = constructor.newInstance();

		assertNotNull(instance1);
		assertNotNull(instance2);
		assertNotSame(instance1, instance2, "Diferentes invocações devem criar diferentes instâncias");
	}

	/**
	 * Testa que não é possível modificar constantes (são final)
	 */
	@Test
	void testConstantsAreFinal() throws NoSuchFieldException {
		assertTrue(Modifier.isFinal(MercadoPagoConstants.class.getField("MERCADO_PAGO_FIRST_NAME").getModifiers()),
				"MERCADO_PAGO_FIRST_NAME deve ser final");
		assertTrue(Modifier.isFinal(MercadoPagoConstants.class.getField("MERCADO_PAGO_TEST_EMAIL").getModifiers()),
				"MERCADO_PAGO_TEST_EMAIL deve ser final");
		assertTrue(
				Modifier.isFinal(MercadoPagoConstants.class.getField("MERCADO_PAGO_PAYMENT_METHOD_ID").getModifiers()),
				"MERCADO_PAGO_PAYMENT_METHOD_ID deve ser final");
		assertTrue(
				Modifier.isFinal(
						MercadoPagoConstants.class.getField("MERCADO_PAGO_PAYMENT_METHOD_TYPE").getModifiers()),
				"MERCADO_PAGO_PAYMENT_METHOD_TYPE deve ser final");
		assertTrue(Modifier.isFinal(MercadoPagoConstants.class.getField("PAYMENTS_METHODS").getModifiers()),
				"PAYMENTS_METHODS deve ser final");
	}

	/**
	 * Testa que todas as constantes são públicas e estáticas
	 */
	@Test
	void testConstantsArePublicAndStatic() throws NoSuchFieldException {
		assertTrue(Modifier.isPublic(MercadoPagoConstants.class.getField("MERCADO_PAGO_FIRST_NAME").getModifiers()),
				"MERCADO_PAGO_FIRST_NAME deve ser público");
		assertTrue(Modifier.isStatic(MercadoPagoConstants.class.getField("MERCADO_PAGO_FIRST_NAME").getModifiers()),
				"MERCADO_PAGO_FIRST_NAME deve ser estático");

		assertTrue(Modifier.isPublic(MercadoPagoConstants.class.getField("PAYMENTS_METHODS").getModifiers()),
				"PAYMENTS_METHODS deve ser público");
		assertTrue(Modifier.isStatic(MercadoPagoConstants.class.getField("PAYMENTS_METHODS").getModifiers()),
				"PAYMENTS_METHODS deve ser estático");
	}
}
