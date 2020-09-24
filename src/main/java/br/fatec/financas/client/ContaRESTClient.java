package br.fatec.financas.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.fatec.financas.model.Conta;

public class ContaRESTClient {
	public static final String REST_WEBSERVICE_URL = "http://localhost:8080/financas-rest-2020/api/";
	public static final String REST_CONTA_URL = "contas/";

	private Response response;

	public List<Conta> findAll() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(REST_WEBSERVICE_URL + REST_CONTA_URL);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		this.response = invocationBuilder.get();
		List<Conta> contas = this.response.readEntity(new GenericType<List<Conta>>() {
		});
		client.close();
		return contas;
	}

	public Conta find(Long numero) {
		this.response = ClientBuilder.newClient().target(REST_WEBSERVICE_URL + REST_CONTA_URL + numero)
				.request(MediaType.APPLICATION_JSON).get();
		Conta conta = this.response.readEntity(Conta.class);
		return conta;
	}

	public Conta create(Conta obj) {
		this.response = ClientBuilder.newClient().target(REST_WEBSERVICE_URL + REST_CONTA_URL).queryParam("conta", obj)
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(obj, MediaType.APPLICATION_JSON));
		Conta conta = this.response.readEntity(Conta.class);
		return conta;
	}

	public Conta edit(Conta obj) {
		this.response = ClientBuilder.newClient().target(REST_WEBSERVICE_URL + REST_CONTA_URL).queryParam("conta", obj)
				.request(MediaType.APPLICATION_JSON).put(Entity.entity(obj, MediaType.APPLICATION_JSON));
		Conta conta = this.response.readEntity(Conta.class);
		return conta;
	}

	public boolean delete(Long numero) {
		this.response = ClientBuilder.newClient().target(REST_WEBSERVICE_URL + REST_CONTA_URL + numero)
				.request(MediaType.APPLICATION_JSON).delete();
		return this.response.getStatus() == Response.Status.OK.getStatusCode();
	}

}
