package br.com.mj.creditminer.main;

import br.com.mj.creditminer.bot.Bot;
import br.com.mj.creditminer.controller.LoginFormCnt;

public class Principal {

	public static void main(String[] args) {

		Bot.clickLinkAcessoLogin();

		//Bot.insereCredenciais();
		
		new LoginFormCnt();

//		List<CsvDTO> list = null;
//		try {
//			list = Util.parseCsvFileToBeans(CsvDTO.class, "cpf.csv");
//			Bot.processaCpfs(list);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}
