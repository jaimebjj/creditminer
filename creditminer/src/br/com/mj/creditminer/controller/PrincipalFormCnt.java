package br.com.mj.creditminer.controller;

import java.io.File;

import br.com.mj.creditminer.view.PrincipalView;

public class PrincipalFormCnt {
	
	private PrincipalView principalView;
	private File fileUpload;
	private File fileDestino;
	
	public PrincipalFormCnt(LoginFormCnt loginFormCnt) {
		principalView = new PrincipalView(loginFormCnt, this);
		principalView.setVisible(true);
	}
	
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	public File getFileUpload() {
		return fileUpload;
	}
	
	public void setFileDestino(File fileDestino) {
		this.fileDestino = fileDestino;
	}
	
	public File getFileDestino() {
		return fileDestino;
	}
}
