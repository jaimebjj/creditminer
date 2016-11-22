package br.com.mj.creditminer.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import br.com.mj.creditminer.controller.LoginFormCnt;

public class PrincipalView extends JFrame {

	private JPanel contentPane;
	private JList list;
	private JMenuBar menuBar;
	private JMenu mnCadastro;
	private static JDesktopPane desktopPane;
	private JLabel lblLblbancologado;

	public PrincipalView(LoginFormCnt loginFormCnt) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logotipo.png"));

		desktopPane = new JDesktopPane();

		setExtendedState(NORMAL);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1936, 1058);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null,
				Color.DARK_GRAY, Color.GREEN, Color.CYAN));
		setContentPane(desktopPane);

		list = new JList();
		list.setBounds(-10008, -10030, 0, 0);

		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1566, 28);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.WHITE);

		mnCadastro = new JMenu("Cadastros");
		mnCadastro.setForeground(Color.GRAY);
		mnCadastro.setFont(new Font("Agency FB", Font.BOLD, 18));
		menuBar.add(mnCadastro);
		
		desktopPane.setLayout(null);
		desktopPane.add(list);
		desktopPane.add(menuBar);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(-33, -48, 1366, 692);
		lblLogo.setIcon(new ImageIcon("img/mdc_logo.PNG"));
		desktopPane.add(lblLogo);
		
		lblLblbancologado = new JLabel("lblBancoLogado");
		lblLblbancologado.setBounds(10, 46, 655, 14);
		lblLblbancologado.setText("Você está logado no: "+ loginFormCnt.getCredentialsEnum().getDescricao());
		desktopPane.add(lblLblbancologado);
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
}
