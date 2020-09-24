package br.fatec.financas.control;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.fatec.financas.client.ContaRESTClient;
import br.fatec.financas.model.Conta;

@ManagedBean
@SessionScoped
public class ContaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Conta conta;
	private List<Conta> contas;
	private final ContaRESTClient restClient = new ContaRESTClient();

	public ContaBean() {
		super();
		contas = restClient.findAll();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public String pagPrincipal() {
		contas = restClient.findAll();
		return "/index";
	}

	public String pagConta() {
		this.conta = new Conta();
		return "/pag_conta";
	}

	public String gravar() {
		restClient.create(conta);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("Conta criada com sucesso!");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		context.addMessage(null, msg);
		conta = new Conta();
		return null;
	}

	public String excluir(Conta c) {
		if (!restClient.delete(c.getNumero())) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("Não foi possível excluir a conta " + c.getNumero());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		} else {
			contas.remove(c);
		}
		return null;
	}
}