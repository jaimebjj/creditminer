package br.com.mj.creditminer.enumerator;

import br.com.mj.creditminer.util.Util;

/**
 * Enum de definição das credenciais do bot
 * 
 * @author Marcelo Lopes Nunes</br> bjjsolutions.com.br - 30/05/2016</br> <a
 *         href=malito:lopesnunnes@gmail.com>lopesnunnes@gmail.com</a>
 * 
 */
public enum CredentialsEnum {

	CREDENTIALS_PARANA_BANCO(Util.getProperty("prop.descricao.parana.banco"), Util.getProperty("prop.login.parana.banco"), Util.getProperty("prop.password.parana.banco")), 
	CREDENTIALS_PAN(Util.getProperty("prop.descricao.pan"), Util.getProperty("prop.login.pan"), Util.getProperty("prop.password.pan")), 
	CREDENTIALS_BMG(Util.getProperty("prop.descricao.bmg"), Util.getProperty("prop.login.bmg"), Util.getProperty("prop.password.bmg")), 
	CREDENTIALS_BOM_SUCESSO(Util.getProperty("prop.descricao.bom.sucesso"), Util.getProperty("prop.login.bom.sucesso"), Util.getProperty("prop.password.bom.sucesso")),
	CREDENTIALS_DAYCOVAL(Util.getProperty("prop.descricao.daycoval"), Util.getProperty("prop.login.daycoval"), Util.getProperty("prop.password.daycoval")), 
	CREDENTIALS_BOM_SAFRA(Util.getProperty("prop.descricao.safra"), Util.getProperty("prop.login.safra"), Util.getProperty("prop.password.safra"));

	private String descricao;
	private String login;
	private String password;

	private CredentialsEnum(String descricao, String login, String password) {
		this.descricao = descricao;
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static CredentialsEnum buscarPorDescricao(String descricao){
		for (CredentialsEnum credentialsEnum : values()){
			if (credentialsEnum.getDescricao().contains(descricao)){
				return credentialsEnum;
			}
		}
		return null;
	}
}