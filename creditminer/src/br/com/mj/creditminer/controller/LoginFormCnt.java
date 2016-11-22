package br.com.mj.creditminer.controller;

import br.com.mj.creditminer.enumerator.CredentialsEnum;
import br.com.mj.creditminer.view.LoginFormView;

public class LoginFormCnt {
	
	private LoginFormView view;
	
	public LoginFormCnt() {
		view = new LoginFormView(this);
		view.setVisible(true);
		
		for (CredentialsEnum credentialsEnum : CredentialsEnum.values()) {
			view.getComboBanco().addItem(credentialsEnum.getLogin());
		}
	}
	
	public boolean Logar(){
		
		return true;
	}

}
