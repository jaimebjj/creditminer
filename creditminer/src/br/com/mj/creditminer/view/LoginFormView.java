package br.com.mj.creditminer.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.mj.creditminer.bot.Bot;
import br.com.mj.creditminer.controller.LoginFormCnt;
import br.com.mj.creditminer.util.ImageUtils;
import br.com.mj.creditminer.util.JPanelImage;

public class LoginFormView extends JFrame {

	private MediaTracker tracker;
	private JPanel contentPane;
	private JLabel lblBanco; 
	private JComboBox comboBanco;
	private JPanelImage panelCaptcha; 
	private JLabel lblCapctha; 
	private JTextField textCaptha;
	private JButton btnlogin;
	private JButton btnSair;
	
	private PrincipalView principalView;

	public LoginFormView(final LoginFormCnt controller) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/8271_64x64.png"));
		
	    tracker = new MediaTracker(this);
		
		setBounds(100, 100, 539, 293);
	    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();  
	    int componentWidth = getWidth();  
	    int componentHeight = getHeight();  
	    setBounds((screenSize.width-componentWidth)/2, (screenSize.height-componentHeight)/2, componentWidth, componentHeight);  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new TitledBorder(null, "Dados de Login", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblBanco = new JLabel("Banco:");
		lblBanco.setForeground(Color.GRAY);
		lblBanco.setFont(new Font("Agency FB", Font.BOLD, 15));
		lblBanco.setBounds(23, 31, 72, 20);
		contentPane.add(lblBanco);
		
		comboBanco = new JComboBox();
		comboBanco.setBackground(SystemColor.inactiveCaption);
		comboBanco.setBounds(113, 31, 400, 20);
		contentPane.add(comboBanco);
		
		panelCaptcha = new JPanelImage();
		FlowLayout flowLayout = (FlowLayout) panelCaptcha.getLayout();
		panelCaptcha.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCaptcha.setBackground(Color.WHITE);
		panelCaptcha.setBounds(113, 63, 400, 87);
		contentPane.add(panelCaptcha);	
		
		String urlCaptcha = Bot.getLinkImagemCaptcha();
		try {
			URL url = new URL(urlCaptcha);
			Image image = ImageIO.read(url);
			tracker.addImage(image, 0);
			tracker.waitForAll();
			panelCaptcha.setImage(ImageUtils.imageToBufferedImage(image, image.getWidth(this), image.getHeight(this)));
			panelCaptcha.repaint();
		} catch (Exception e) {
			System.out.println("Erro ao carregar Imagem: " + e.getMessage());
		}
	         
		lblCapctha = new JLabel("Captcha:");
		lblCapctha.setForeground(Color.GRAY);
		lblCapctha.setFont(new Font("AgencygetPanelCaptcha() FB", Font.BOLD, 15));
		lblCapctha.setBounds(23, 161, 92, 20);
		contentPane.add(lblCapctha);		
		
		textCaptha = new JTextField();
		textCaptha.setBackground(SystemColor.inactiveCaption);
		textCaptha.setBounds(113, 162, 400, 19);
		contentPane.add(textCaptha);
		textCaptha.setColumns(10);		
		
		btnlogin = new JButton("Entrar");
		btnlogin.setIcon(new ImageIcon("img/7484_32x32.png"));
		btnlogin.setForeground(SystemColor.windowBorder);
		btnlogin.setFont(new Font("Agency FB", Font.BOLD, 15));
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.Logar();
					dispose();
					principalView = new PrincipalView(controller);
					principalView.setVisible(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnlogin.setBounds(246, 197, 128, 46);
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
		btnSair.setBounds(385, 197, 128, 46);
		contentPane.add(btnSair);
	}
	
	public JComboBox getComboBanco() {
		return comboBanco;
	}
	
	public void setComboBanco(JComboBox comboBanco) {
		this.comboBanco = comboBanco;
	}
	
	public JPanelImage getPanelCaptcha() {
		return panelCaptcha;
	}
	
	public void setPanelCaptcha(JPanelImage panelCaptcha) {
		this.panelCaptcha = panelCaptcha;
	}
	
	public JTextField getTextCaptha() {
		return textCaptha;
	}
	
	public void setTextCaptha(JTextField textCaptha) {
		this.textCaptha = textCaptha;
	}
}
