package Principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import Biometrics.CFingerPrint;
import util.conexao;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;

public class cadealt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInformeONome;
	private JTextField nome;
	private JTextField txtInformeONvel;
	private JTextField acesso;
	private JTextField txtCadastrarDigital;
	private JButton btnNewButton;
	private JTextField txtInformeOSeu;
	private JTextField user;
	private JTextField txtInformeSuaSenha;
	private JPasswordField password;
	public String digitalLida;
	public String caminho;
	private CFingerPrint m_finger1 = new CFingerPrint();
	private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
	private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cadealt frame = new cadealt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public cadealt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 310);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		contentPane.add(panel);

		txtInformeONome = new JTextField();
		txtInformeONome.setForeground(Color.WHITE);
		txtInformeONome.setBackground(Color.DARK_GRAY);
		txtInformeONome.setEditable(false);
		txtInformeONome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtInformeONome.setHorizontalAlignment(SwingConstants.CENTER);
		txtInformeONome.setText("Informe o nome da pessoa");
		txtInformeONome.setBounds(10, 47, 194, 20);
		panel.add(txtInformeONome);
		txtInformeONome.setColumns(10);

		nome = new JTextField();
		nome.setColumns(10);
		nome.setBounds(214, 47, 430, 20);
		panel.add(nome);

		txtInformeONvel = new JTextField();
		txtInformeONvel.setForeground(Color.WHITE);
		txtInformeONvel.setBackground(Color.DARK_GRAY);
		txtInformeONvel.setText("Informe o nível de acesso");
		txtInformeONvel.setEditable(false);
		txtInformeONvel.setHorizontalAlignment(SwingConstants.CENTER);
		txtInformeONvel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtInformeONvel.setColumns(10);
		txtInformeONvel.setBounds(10, 78, 194, 20);
		panel.add(txtInformeONvel);

		acesso = new JTextField();
		acesso.setColumns(10);
		acesso.setBounds(214, 78, 430, 20);
		panel.add(acesso);

		txtCadastrarDigital = new JTextField();
		txtCadastrarDigital.setBackground(Color.DARK_GRAY);
		txtCadastrarDigital.setForeground(Color.WHITE);
		txtCadastrarDigital.setEditable(false);
		txtCadastrarDigital.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCadastrarDigital.setHorizontalAlignment(SwingConstants.CENTER);
		txtCadastrarDigital.setText("Cadastro / Exclusão de digital");
		txtCadastrarDigital.setColumns(10);
		txtCadastrarDigital.setBounds(10, 11, 634, 20);
		panel.add(txtCadastrarDigital);

		btnNewButton = new JButton("Selecionar digital");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser sel = new JFileChooser("C:\\DG");
				sel.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivo de imagem .tif","tif");
				sel.setFileFilter(filtro);
				sel.setDialogTitle("Selecione a digital");
				sel.setVisible(true);
				File file = new File("");

				int resposta = sel.showOpenDialog(new JDialog());

				if (resposta == JFileChooser.APPROVE_OPTION) {
					file = sel.getSelectedFile();
					caminho =  file.getAbsolutePath();
					try
					{        
						m_bimage1=ImageIO.read(new File(file.getAbsolutePath())) ;
						m_finger1.setFingerPrintImage(m_bimage1) ;
						finger1=m_finger1.getFingerPrintTemplate();
						m_bimage1 = m_finger1.getFingerPrintImageDetail();
						digitalLida = m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1);
						System.out.println(digitalLida);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog (null,ex.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
					}
				}
			}});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setBounds(10, 109, 634, 23);
		panel.add(btnNewButton);

		txtInformeOSeu = new JTextField();
		txtInformeOSeu.setText("Informe o seu usuário");
		txtInformeOSeu.setHorizontalAlignment(SwingConstants.CENTER);
		txtInformeOSeu.setForeground(Color.WHITE);
		txtInformeOSeu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtInformeOSeu.setEditable(false);
		txtInformeOSeu.setColumns(10);
		txtInformeOSeu.setBackground(Color.DARK_GRAY);
		txtInformeOSeu.setBounds(10, 143, 194, 20);
		panel.add(txtInformeOSeu);

		user = new JTextField();
		user.setColumns(10);
		user.setBounds(214, 143, 430, 20);
		panel.add(user);

		txtInformeSuaSenha = new JTextField();
		txtInformeSuaSenha.setText("Informe sua senha");
		txtInformeSuaSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtInformeSuaSenha.setForeground(Color.WHITE);
		txtInformeSuaSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtInformeSuaSenha.setEditable(false);
		txtInformeSuaSenha.setColumns(10);
		txtInformeSuaSenha.setBackground(Color.DARK_GRAY);
		txtInformeSuaSenha.setBounds(10, 174, 194, 20);
		panel.add(txtInformeSuaSenha);

		password = new JPasswordField();
		password.setBounds(214, 174, 430, 20);
		panel.add(password);

		JButton btnExcluirDigital = new JButton("Excluir digital");
		btnExcluirDigital.setForeground(Color.WHITE);
		btnExcluirDigital.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExcluirDigital.setBackground(Color.DARK_GRAY);
		btnExcluirDigital.setBounds(10, 205, 316, 55);
		panel.add(btnExcluirDigital);

		JButton btnNewButton_1_1 = new JButton("Cadastar digital");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao dao = new conexao();

				if (digitalLida == null || nome.getText() == null || acesso.getText() == null) {
					JOptionPane.showMessageDialog(null, "Dados incorretos / faltantes");
				}
				else {
					try {
						dao.writeDataBase(nome.getText(), Integer.parseInt(acesso.getText()), digitalLida);
						JOptionPane.showMessageDialog(null, "Usuário registrado");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1_1.setBounds(336, 205, 308, 55);
		panel.add(btnNewButton_1_1);
	}
}
