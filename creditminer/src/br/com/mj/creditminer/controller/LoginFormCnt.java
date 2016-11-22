package br.com.mj.creditminer.controller;

import br.com.mj.creditminer.bot.Bot;
import br.com.mj.creditminer.enumerator.CredentialsEnum;
import br.com.mj.creditminer.view.LoginFormView;

public class LoginFormCnt {
	
	private LoginFormView view;
	private CredentialsEnum credentialsEnum;
	
	public LoginFormCnt() {
		Bot.clickLinkAcessoLogin();
		view = new LoginFormView(this);
		view.setVisible(true);
		
		view.getComboBanco().addItem("Selecione um banco");
		for (CredentialsEnum credentialsEnum : CredentialsEnum.values()) {
			view.getComboBanco().addItem(credentialsEnum.getDescricao());
		}
	}
	
	public void Logar() throws Exception{
		String captcha = "";
		if (view.getComboBanco().getSelectedIndex() != 0 && view.getComboBanco().getSelectedIndex() != -1) {
			credentialsEnum = CredentialsEnum.buscarPorDescricao(view.getComboBanco().getSelectedItem().toString());
		} else {
			throw new Exception("Selecione um banco");
		}
		if (!view.getTextCaptha().getText().equals("")){
			captcha = view.getTextCaptha().getText();  
		} else {
			throw new Exception("Digite o captcha");
		}
		
		boolean isLogado = Bot.insereCredenciais(credentialsEnum, captcha);
		if (!isLogado){
			throw new Exception("Erro ao logar-se, feche a janela e tente novamente...");
		}
	}
	
	public CredentialsEnum getCredentialsEnum() {
		return credentialsEnum;
	}
	
	public void setCredentialsEnum(CredentialsEnum credentialsEnum) {
		this.credentialsEnum = credentialsEnum;
	}

}
