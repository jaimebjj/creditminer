package br.com.mj.creditminer.controller;

import br.com.mj.creditminer.view.LoginFormView;

public class LoginFormCnt {
	
	private LoginFormView view;
	
	public LoginFormCnt() {
		view = new LoginFormView(this);
		view.setVisible(true);
	}
	
	public boolean Logar(){
		
		return true;
	}

}
