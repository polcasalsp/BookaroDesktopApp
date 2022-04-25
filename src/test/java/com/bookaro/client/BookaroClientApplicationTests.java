package com.bookaro.client;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookaro.client.Utils.RetrofitClient;
import com.bookaro.client.Utils.Tools;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;


@SpringBootTest
class BookaroClientApplicationTests {

	private DBCallService dbcs;
	private LoginService loginService;
	
	/**
	 * Prova de recuperar la llista de tots els usuaris que
	 * hi ha a la BD amb l'usuari admin.
	 * Retorna codi 200, OK.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void getAllUsersPermissionTestAdmin() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = RetrofitClient.getClient(loginService.getToken()).create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 200;
	}	
	
	/**
	 * Prova de recuperar la llista de tots els usuaris que
	 * hi ha a la BD amb un usuari normal.
	 * Retorna codi 403, FORBIDDEN.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void getAllUsersPermissionTestUser() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("cliente1", "1234");
		dbcs = RetrofitClient.getClient(loginService.getToken()).create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 403;
	}
	
	/**
	 * Aquest test prova el sistema de login i que els rols es 
	 * recuperen correctament del token.
	 * Administrador = ROLE_ADMIN
	 * Usuari = ROLE_USER
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void loginAndRoleTest() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		assert Tools.getRoleFromToken(loginService.getToken()).equals("ROLE_ADMIN");
		loginService.sendUserCredentials("cliente1", "1234");
		assert Tools.getRoleFromToken(loginService.getToken()).equals("ROLE_USER");
	}
	
	/**
	 * Prova el sistema de logout al servidor (blacklist)
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void serverLogout() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		String token = loginService.getToken();
		assert !token.equals("");
		dbcs = RetrofitClient.getClient(token).create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 200;
		dbcs.logout().execute();
		assert dbcs.getUsers().execute().code() == 403;
	}
	
	/**
	 * Prova el sistema de logout al client (esborrar token)
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void clientLogout() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		String token = loginService.getToken();
		dbcs = RetrofitClient.getClient(token).create(DBCallService.class);
		assert !token.equals("");
		loginService.logout();
		token = loginService.getToken();
		assert token.equals("");
	}
}