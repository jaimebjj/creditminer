package br.com.mj.creditminer.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.mj.creditminer.controller.LoginFormCnt;
import br.com.mj.creditminer.controller.PrincipalFormCnt;
import javax.swing.JProgressBar;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JDesktopPane desktopPane;
	private JLabel lblbancologado;
	private JLabel lblNomeArquivoUpload;
	private JLabel lblNomeDiretorioDestino;
	private JLabel lblStatus;
	private JButton btnSelecionar;
	private JButton btnDiretorioDestino;
	private JButton btnIniciar;
	private JProgressBar progressBar;

	public PrincipalView(LoginFormCnt loginFormCnt, final PrincipalFormCnt principalFormCnt) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logotipo.png"));

		desktopPane = new JDesktopPane();
		desktopPane.setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 180);
	    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();  
	    int componentWidth = getWidth();  
	    int componentHeight = getHeight();  
	    setBounds((screenSize.width-componentWidth)/2, (screenSize.height-componentHeight)/2, componentWidth, componentHeight); 
	    
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null,
				Color.DARK_GRAY, Color.GREEN, Color.CYAN));
		setContentPane(desktopPane);

		btnDiretorioDestino = new JButton("Diret�rio Destino");
		btnIniciar = new JButton("Iniciar Captura");
		
		btnSelecionar = new JButton("Upload Arquivo");
		btnSelecionar.setForeground(SystemColor.DARK_GRAY);
		btnSelecionar.setFont(new Font("Agency FB", Font.BOLD, 12));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();  
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(  
			        "CSV", "csv");  
			    chooser.setFileFilter(filter); 
			    int returnVal = chooser.showOpenDialog(null); 
			    if(returnVal == JFileChooser.APPROVE_OPTION) { 
			    	
			    	File file = chooser.getSelectedFile();
			    	principalFormCnt.setFileUpload(file);
			       	lblNomeArquivoUpload.setText(file.getName());
			       	if (principalFormCnt.getFileDestino() != null){
			       		lblNomeDiretorioDestino.setText(principalFormCnt.getFileDestino().getAbsolutePath());
			       	}
			       	btnDiretorioDestino.setEnabled(true);
			    } 
			}
		});
		btnSelecionar.setBounds(10, 40, 156, 23);
		desktopPane.add(btnSelecionar);
		
		lblNomeArquivoUpload = new JLabel();
		lblNomeArquivoUpload.setBounds(185, 40, 597, 23);
		desktopPane.add(lblNomeArquivoUpload);
		
		btnDiretorioDestino.setEnabled(false);
		btnDiretorioDestino.setForeground(SystemColor.DARK_GRAY);
		btnDiretorioDestino.setFont(new Font("Agency FB", Font.BOLD, 12));
		btnDiretorioDestino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int res = fc.showOpenDialog(null);
                if(res == JFileChooser.APPROVE_OPTION){
                    File diretorio = fc.getSelectedFile();
                    principalFormCnt.setFileDestino(diretorio);
                    lblNomeDiretorioDestino.setText(principalFormCnt.getFileDestino().getAbsolutePath());
                    btnIniciar.setEnabled(true);
                }

			}
		});
		btnDiretorioDestino.setBounds(10, 70, 156, 23);
		desktopPane.add(btnDiretorioDestino);	
		
		lblNomeDiretorioDestino = new JLabel();
		lblNomeDiretorioDestino.setBounds(185, 70, 597, 23);
		desktopPane.add(lblNomeDiretorioDestino);
		
		btnIniciar.setEnabled(false);
		btnIniciar.setForeground(SystemColor.DARK_GRAY);
		btnIniciar.setFont(new Font("Agency FB", Font.BOLD, 12));
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					principalFormCnt.iniciarProcesso();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});		
		btnIniciar.setBounds(10, 100, 156, 23);
		desktopPane.add(btnIniciar);
		
		lblStatus = new JLabel();
		lblStatus.setBounds(185, 130, 597, 23);
		desktopPane.add(lblStatus);		
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(-125, -111, 1366, 692);
		lblLogo.setIcon(new ImageIcon("img/mdc_logo.PNG"));
		desktopPane.add(lblLogo);
		
		lblbancologado = new JLabel("lblBancoLogado");
		lblbancologado.setBounds(10, 10, 568, 26);
		lblbancologado.setText("Voc� est� logado no "+ loginFormCnt.getCredentialsEnum().getDescricao());
		desktopPane.add(lblbancologado);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(185, 100, 597, 23);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		desktopPane.add(progressBar);
		
	}

	public static void controlaJanela(JInternalFrame janela) {
		try {
			janela.setVisible(true);
			desktopPane.add(janela);
			desktopPane.getDesktopManager().activateFrame(janela);

			// centraliza no desktopPane
			int lDesk = desktopPane.getWidth();
			int aDesk = desktopPane.getHeight();
			int lIFrame = janela.getWidth();
			int aIFrame = janela.getHeight();

			janela.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2 - aIFrame / 2);

		} catch (Exception e) {
			System.out.println("Erro ao criar janela " + e.getMessage());
		}

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	public void setLblStatus(JLabel lblStatus) {
		this.lblStatus = lblStatus;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
