package Principal;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import Biometrics.CFingerPrint;
import util.conexao;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.lang.Exception;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class CEntityForm extends JFrame {

	private static final long serialVersionUID = 1L;

	class BJPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public BufferedImage bi;

		public BJPanel() {
			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent m) {
					JOptionPane.showMessageDialog(null,
							"(" + Integer.toString(m.getPoint().x) + ";" + Integer.toString(m.getPoint().y) + ")",
							"Point", JOptionPane.PLAIN_MESSAGE);
				}
			});
		}

		public BJPanel(BufferedImage bi) {
			this.bi = bi;
			setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
		}

		public void setBufferedImage(BufferedImage bi) {
			this.bi = bi;
			setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
			this.repaint();
		}

		public void paintComponent(Graphics g) {
			g.drawImage(bi, 0, 0, this);
		}
	}
	private JTextField jTextField1 = new JTextField();
	private JPanel grade = new JPanel();

	private CFingerPrint m_finger1 = new CFingerPrint();
	private BJPanel m_panel12 = new BJPanel();
	private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
	private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
	private final JPanel panel = new JPanel();
	private JTextField txtCoordenadasDasMinuncias;
	private JTextField txtNvelDeAcesso;
	private JTextField txtN;
	public String caminho;
	private JTextField digitalNoBanco;

	public CEntityForm() {
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(null);
		grade.setForeground(Color.WHITE);
		grade.setBounds(0, 0, 984, 377);
		grade.setLayout(null);
		jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		jTextField1.setBounds(342, 51, 632, 29);
		jTextField1.setEditable(false);
		grade.add(jTextField1);

		getContentPane().setLayout(null);
		this.getContentPane().add(grade);
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(10, 11, 322, 352);
		grade.add(panel);
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		digitalNoBanco = new JTextField();
		digitalNoBanco.setHorizontalAlignment(SwingConstants.CENTER);
		digitalNoBanco.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		digitalNoBanco.setEditable(false);
		digitalNoBanco.setBounds(342, 294, 632, 29);
		grade.add(digitalNoBanco);

		JButton btnNewButton = new JButton("Selecionar digital para leitura");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
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
					System.out.println(caminho);}

				try
				{        
					m_bimage1=ImageIO.read(new File(file.getAbsolutePath())) ;
					m_panel12.setBufferedImage(m_bimage1);

					//Send image for skeletinization
					m_finger1.setFingerPrintImage(m_bimage1) ;
					finger1=m_finger1.getFingerPrintTemplate();

					//See what skeletinized image looks like
					m_bimage1 = m_finger1.getFingerPrintImageDetail();
					m_panel12.setBufferedImage(m_bimage1);
					jTextField1.setText(m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1));
					panel.setVisible(false);
					panel.setVisible(true);
					panel.add(m_panel12);

					conexao dao = new conexao();
					String aux = dao.readDataBase("'"+m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1)+"'");
					System.out.println("\n\n" + aux + "\n\n");

					if (aux != null && aux != "Sem informações") {
						digitalNoBanco.setText("Digital encontrada!");
						txtN.setText(aux);
					}
					else {
						digitalNoBanco.setText("Digital não encontrada!");
						txtN.setText("Sem informações");
					}

				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog (null,ex.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
				}

			}
		});
		btnNewButton.setBounds(342, 196, 632, 87);
		grade.add(btnNewButton);

		txtCoordenadasDasMinuncias = new JTextField();
		txtCoordenadasDasMinuncias.setEditable(false);
		txtCoordenadasDasMinuncias.setHorizontalAlignment(SwingConstants.CENTER);
		txtCoordenadasDasMinuncias.setText("Coordenadas das minuncias");
		txtCoordenadasDasMinuncias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtCoordenadasDasMinuncias.setBounds(342, 11, 632, 29);
		grade.add(txtCoordenadasDasMinuncias);

		txtNvelDeAcesso = new JTextField();
		txtNvelDeAcesso.setText("Nível de acesso:");
		txtNvelDeAcesso.setHorizontalAlignment(SwingConstants.CENTER);
		txtNvelDeAcesso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtNvelDeAcesso.setBounds(342, 334, 400, 29);
		grade.add(txtNvelDeAcesso);

		txtN = new JTextField();
		txtN.setForeground(Color.RED);
		txtN.setText("N");
		txtN.setHorizontalAlignment(SwingConstants.CENTER);
		txtN.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtN.setBounds(752, 334, 222, 29);
		grade.add(txtN);

		JButton btnAlterarADigital = new JButton("Cadastrar ou remover digital");
		btnAlterarADigital.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlterarADigital.setBounds(342, 91, 632, 94);
		grade.add(btnAlterarADigital);

		this.setTitle("Entity");
		this.setSize(new Dimension(1000, 416));
	}}
