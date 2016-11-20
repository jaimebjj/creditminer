package br.com.mj.creditminer.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import br.com.mj.creditminer.controller.LoginFormCnt;

public class LoginFormView extends JFrame {

	private JPanel contentPane;
	private JLabel lblBanco; 
	private JComboBox comboBanco;
	private JButton btnlogin;
	private JButton btnSair;
	
	private PrincipalView principalView;

	public LoginFormView(final LoginFormCnt controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/8271_64x64.png"));
		
		setBounds(100, 100, 448, 194);
	    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();  
	    int componentWidth = getWidth();  
	    int componentHeight = getHeight();  
	    setBounds((screenSize.width-componentWidth)/2, (screenSize.height-componentHeight)/2, componentWidth, componentHeight);  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new TitledBorder(null, "Informe seu Login", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblBanco = new JLabel("Login:");
		lblBanco.setForeground(Color.GRAY);
		lblBanco.setFont(new Font("Agency FB", Font.BOLD, 15));
		lblBanco.setBounds(44, 31, 59, 20);
		contentPane.add(lblBanco);
		
		comboBanco = new JComboBox();
		comboBanco.setBackground(SystemColor.inactiveCaption);
		comboBanco.setBounds(113, 31, 275, 20);
		contentPane.add(comboBanco);
		
		btnlogin = new JButton("Entrar");
		btnlogin.setIcon(new ImageIcon("img/7484_32x32.png"));
		btnlogin.setForeground(SystemColor.windowBorder);
		btnlogin.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean flag = controller.Logar();
				if (flag == false){
					JOptionPane.showMessageDialog(null, "Usuario ou senha inv�lidos");
				}else {
					dispose();
					principalView = new PrincipalView();
					principalView.setVisible(true);
				}
			}
		});
		btnlogin.setBounds(183, 92, 107, 46);
		contentPane.add(btnlogin);
		
		btnSair = new JButton("Sair");
		btnSair.setIcon(new ImageIcon("img/6045_32x32.png"));
		btnSair.setForeground(SystemColor.windowBorder);
		btnSair.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(300, 92, 107, 46);
		contentPane.add(btnSair);
	}
	
}
