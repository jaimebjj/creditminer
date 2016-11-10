package br.com.mj.creditminer.main;

import java.io.IOException;
import java.util.List;

import br.com.mj.creditminer.bot.Bot;
import br.com.mj.creditminer.dto.CsvDTO;
import br.com.mj.creditminer.util.Util;

public class Principal {

	public static void main(String[] args) {
		
		Bot.clickLinkAcessoLogin();

		String urlCaptcha = Bot.getLinkImagemCaptcha();

		System.out.println("urlCaptcha: " + urlCaptcha);

		Bot.insereCredenciais();

		List<CsvDTO> list = null;
		try {
			list = Util.parseCsvFileToBeans(CsvDTO.class, "cpf.csv");
			Bot.processaCpfs(list);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
