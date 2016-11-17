package br.com.mj.creditminer.bot;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.mj.creditminer.dto.CsvDTO;
import br.com.mj.creditminer.processing.Cache;
import br.com.mj.creditminer.processing.HTMLJsoup;
import br.com.mj.creditminer.processing.WriteFileCSV;
import br.com.mj.creditminer.setupselenium.SetupSelenium;
import br.com.mj.creditminer.util.Util;

public class Bot {

	private final static String URL_INICIAL_CONSIGNUM = "http://sc.consignum.com.br/wmc-sc/login/selecao_parceiro.faces";
	private final static String URL_HISTORICO = "http://sc.consignum.com.br/wmc-sc/pages/consultas/historico/pesquisa_colaborador.faces";
	private final static String URL_BYPASS = "http://sc.consignum.com.br/wmc-sc/pages/consultas/disponibilidade_margem/visualiza_margem_colaborador.faces";
	private static String mensagemDoStatus;
	private static HTMLJsoup instanceHTMLJsoup;

	/**
	 * Método que redireciona para a url passada como parâmetro
	 * 
	 * @param url
	 */
	private static void goTo(String url) {
		SetupSelenium.getInstance().getWebDriver().get(url);
	}

	/**
	 * Método que acessa a primeira página e clica no link para chamar a tela de
	 * login
	 */
	public static void clickLinkAcessoLogin() {

		try {
			goTo(URL_INICIAL_CONSIGNUM);

			Util.pause(1000);
			WebElement element = SetupSelenium.getInstance().getWait()
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_1088422203_1:j_id_jsp_1088422203_8:tbody_element']/tr/td[2]/a")));

			element.click();
			Util.pause(1000);
		} catch (Exception e) {
			clickLinkAcessoLogin();
		}

	}

	/**
	 * Método que navega pelo site e busca a imagem do captcha.
	 * 
	 * @return String linkImagem
	 * 
	 */
	public static String getLinkImagemCaptcha() {

		StringBuilder linkImagem = new StringBuilder();

		try {

			WebElement imgElement = SetupSelenium.getInstance().getWebDriver().findElement(By.xpath(".//*[@id='recaptcha_challenge_image']"));

			linkImagem.append(imgElement.getAttribute("src"));

		} catch (Exception e) {
			System.out.println("Erro ao obter link imagem captcha. " + e.getMessage());
		}
		return linkImagem.toString();
	}

	/**
	 * Método que pega os elementos da página de login que representam os campos
	 * login, password, campo de resposta do captcha e o botão de entrar,
	 * adiciona os valores digitados em nossa página de login e se loga no site
	 * do consignum
	 */
	public static void insereCredenciais() {
		WebElement inputUsuario = null;
		WebElement inputPassword = null;
		WebElement inputCaptcha = null;
		WebElement btnEntrar = null;
		WebElement verificaSeEstaLogado = null;
		String usuario = null;
		String senha = null;
		String captcha = null;
		try {

			inputUsuario = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_1179747809_21']")));
			inputPassword = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("j_id_jsp_1179747809_23")));
			inputCaptcha = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='recaptcha_response_field']")));
			btnEntrar = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_1179747809_27']")));

			// System.out.println("Digite o usuario: ");
			// usuario = new Scanner(System.in).next();
			// inputUsuario.sendKeys(usuario);
			//
			//
			// System.out.println("Digite a senha: ");
			// senha = new Scanner(System.in).next();
			// inputPassword.sendKeys(senha);

			inputUsuario.sendKeys("SC01R02385");
			inputPassword.sendKeys("joaonunes03");

			System.out.println("Digite o captcha: ");
			captcha = new Scanner(System.in).next();
			inputCaptcha.sendKeys(captcha);

			btnEntrar.click();

			verificaSeEstaLogado = new WebDriverWait(SetupSelenium.getInstance().getWebDriver(), 5).until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//*[contains(./@id, 'j_id_jsp_252844863_0pc3')]")));

			if (verificaSeEstaLogado != null) {
				System.out.println("Logado com sucesso!!!");
			}

		} catch (Exception e) {
			System.out.println("Erro ao logar-se, feche a janela e tente novamente...");

		}

	}

	/**
	 * Método que contém o laço para percorrer todos os cpfs do arquivo
	 * 
	 * @param list
	 */
	public static void processaCpfs(List<CsvDTO> list) {

		long start = System.currentTimeMillis();
		mensagemDoStatus = "Iniciando...";
		int qtdResultados = 0;
		int contador = 0;
		int total = list.size();

		try {

			goTo(URL_HISTORICO);

			for (int i = 0; i < list.size(); i++) {

				String cpf = StringUtils.leftPad(list.get(i).getCpf(), 11, "0");

				try {
					pesquisaCPF(cpf);
					qtdResultados = getQtdResultados(cpf);

					contador++;

					System.out.println("cpf: " + cpf);
					System.out.println("matrículas encontradas: " + qtdResultados);

					setMapJsoup(cpf, qtdResultados);
					System.out.println("Status: " + contador + "/" + total);

				} catch (Exception e) {
					pesquisaCPF(cpf);

				}
			}
		} catch (Exception e) {
			System.out.println("Erro ao percorrer CPFs: " + e.getMessage());
		} finally {
			if (Cache.clientesDTOCache != null) {
				WriteFileCSV.createCsvFile(Cache.clientesDTOCache, Util.getProperty("prop.diretorio.cache") + "test1.csv");
			}
		}

		long end = System.currentTimeMillis();
		long totalTempoCpfs = Util.calculaTempoExecucao(start, end);
		System.out.println("tempo processamento cpfs: " + totalTempoCpfs);
		
	}

	/**
	 * Método que pega os elementos inputCpf e btnPesquisar, insere o cpf no
	 * inputCpf e faz a pesquisa.
	 * 
	 * @param String
	 *            cpf
	 */
	private static void pesquisaCPF(String cpf) {
		WebElement inputCpf = null;
		WebElement btnPesquisar = null;
		try {
			inputCpf = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_248910084_1:j_id_jsp_248910084_14']")));
			btnPesquisar = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_248910084_1:j_id_jsp_248910084_15']")));

			inputCpf.clear();
			inputCpf.sendKeys(cpf);
			btnPesquisar.click();

			Util.pause(1000);
		} catch (Exception e) {
			goTo(URL_HISTORICO);
			pesquisaCPF(cpf);
		}

	}

	/**
	 * Método que retorna a quantidade de resultados da pesquisa
	 * 
	 * @return int qtdResultados
	 */
	private static int getQtdResultados(String cpf) {
		int qtdResultados = 0;

		String linha = new WebDriverWait(SetupSelenium.getInstance().getWebDriver(), 4).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='j_id_jsp_248910084_1:tabelaListaCol:tbody_element']/tr/td[1]"))).getText();

		if (linha.equals("") || linha == null) {
			return qtdResultados;
		} else {
			qtdResultados = SetupSelenium.getInstance().getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(./@id, 'j_id_jsp_248910084_23')]"))).size();

		}

		return qtdResultados;
	}

	/**
	 * Método que adiciona os dados pesquisados ao Map do Jsoup
	 * 
	 * @param cpf
	 * @param qtdResultados
	 */
	private static void setMapJsoup(String cpf, int qtdResultados) {

		long start = System.currentTimeMillis();
		WebElement linkNome = null;
		
		for (int i = 0; i < qtdResultados; i++) {

			linkNome = SetupSelenium.getInstance().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("j_id_jsp_248910084_1:tabelaListaCol:" + i + ":j_id_jsp_248910084_23")));
			linkNome.click();

			getInstanceHTMLJsoup().createObjectRecordHTML(SetupSelenium.getInstance().getWebDriver().getPageSource(), cpf + "-" + i);
			goTo(URL_BYPASS);
			getInstanceHTMLJsoup().createObjectRecordHTML(SetupSelenium.getInstance().getWebDriver().getPageSource(), cpf + "-" + i + "-margem");
			goTo(URL_HISTORICO);

		}

		long end = System.currentTimeMillis();
		long totalTempoMatriculas = Util.calculaTempoExecucao(start, end);
		System.out.println("tempo processamento matrículas: " + totalTempoMatriculas);
	}

	/**
	 * Singleton Jsoup
	 * 
	 * @return
	 */
	public static HTMLJsoup getInstanceHTMLJsoup() {
		if (instanceHTMLJsoup == null) {
			instanceHTMLJsoup = new HTMLJsoup();
		}
		return instanceHTMLJsoup;
	}

}
