package br.com.mj.creditminer.processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import br.com.mj.creditminer.dto.ClienteDTO;
import br.com.mj.creditminer.dto.SolicitacaoDTO;

/**
 * Classe que gera o csv com os dados processados
 * 
 * @author Marcelo Lopes Nunes</br> bjjsolutions.com.br - 30/06/2016</br> <a
 *         href=malito:lopesnunnes@gmail.com>lopesnunnes@gmail.com</a>
 * 
 */
public class WriteFileCSV {

	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "cpf;matricula;colaborador;secretaria;nascimento;cargo;ultimafolhamovimentada;margem;controleconsignataria;tipo;banco;valorautorizado;parcelas;parcelaspagas";

	public static void createCsvFile(Map<String, ClienteDTO> clientes, File destino) {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(destino.getAbsolutePath(), true);

			fileWriter.append(FILE_HEADER.toString());

			fileWriter.append(NEW_LINE_SEPARATOR);

			for (ClienteDTO clienteDTO : clientes.values()) {

				if (clienteDTO.getSolicitacaes() != null && !clienteDTO.getSolicitacaes().isEmpty()) {
					for (SolicitacaoDTO solicitacaoDTO : clienteDTO.getSolicitacaes()) {
						writeLine(fileWriter, clienteDTO, solicitacaoDTO);
					}
				} else {
					writeLine(fileWriter, clienteDTO, null);
				}

			}

			System.out.println("Arquivo CSV criado com sucesso");

		} catch (Exception e) {
			System.err.println("Erro na cria��o do arquivo");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.err.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
	}

	private static void writeLine(FileWriter fileWriter, ClienteDTO clienteDTO, SolicitacaoDTO solicitacaoDTO) throws IOException {
		String controleConsignataria = "";
		String tipo = "";
		String banco = "";
		String valorAutorizado = "";
		String parcelas = "";
		String pagas = "";

		if (solicitacaoDTO != null) {
			controleConsignataria = solicitacaoDTO.getControleConsignataria() != null ? solicitacaoDTO.getControleConsignataria() : "";
			tipo = solicitacaoDTO.getTipo() != null ? solicitacaoDTO.getTipo() : "";
			banco = solicitacaoDTO.getBanco() != null ? solicitacaoDTO.getBanco() : "";
			valorAutorizado = solicitacaoDTO.getValorAutorizado() != null ? solicitacaoDTO.getValorAutorizado() : "";
			parcelas = solicitacaoDTO.getParcelas() != null ? solicitacaoDTO.getParcelas() : "";
			pagas = solicitacaoDTO.getPagas() != null ? solicitacaoDTO.getPagas() : "";
		}

		fileWriter.append(clienteDTO.getCpf());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getMatricula());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getColaborador());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getSecretaria());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getNascimento());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getCargoFuncao());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getUltimaFolhaMovimentada() != null ? clienteDTO.getUltimaFolhaMovimentada() : "");
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(clienteDTO.getMargem() != null ? clienteDTO.getMargem() : "");
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(controleConsignataria);
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(tipo);
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(banco);
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(valorAutorizado);
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(parcelas);
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(pagas);
		fileWriter.append(NEW_LINE_SEPARATOR);
	}
}
