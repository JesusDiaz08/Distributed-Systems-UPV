package chat;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Dimension;

public class ChatUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextArea jTextAreaConversacion = null;
	private JScrollPane jScrollPaneConversacion = null;
	private JLabel jLabelUser = null;
	private JTextField jTextFieldNombreUsuario = null;
	private JLabel jLabelDurable = null;
	private JCheckBox jCheckBoxDurable = null;
	private JLabel jLabelNombreConversacion = null;
	private JTextField jTextFieldNombreConversacion = null;
	private JLabel jLabelTiempoDeVida = null;
	private JTextField jTextFieldTiempoDeVida = null;
	private JLabel jLabelms = null;
	private JButton jButtonBorrar = null;
	private JTextField jTextFieldMensaje = null;
	private JTextField jTextFieldTopic = null;
	private JLabel jLabelTopic = null;
    //  @jve:decl-index=0:
	
	private ChatManager cm=null;
	private boolean conectado=false;
	private JLabel jLabelNombreDurable = null;
	private JTextField jTextFieldNombreDurable = null;
	private JButton jButtonConectar = null; 

	public void nuevoMensaje(String men){
		jTextAreaConversacion.append(men);
		int tam=jTextAreaConversacion.getDocument().getLength();
		jTextAreaConversacion.select(tam,tam);
	}
	
	/**
	 * This method initializes jTextAreaConversacion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaConversacion() {
		if (jTextAreaConversacion == null) {
			jTextAreaConversacion = new JTextArea();
			jTextAreaConversacion.setEditable(false);
			jTextAreaConversacion.setLineWrap(true);
			jTextAreaConversacion.setEnabled(true);
			//jTextAreaConversacion.setBackground(Color.white);
			jTextAreaConversacion.setForeground(Color.black);
			jTextAreaConversacion.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextAreaConversacion.setWrapStyleWord(true);
			//jTextAreaConversacion.setText("Hola");
			//jTextAreaConversacion.append("\n"+"Adios");
		}
		return jTextAreaConversacion;
	}

	/**
	 * This method initializes jScrollPaneConversacion	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneConversacion() {
		if (jScrollPaneConversacion == null) {
			jScrollPaneConversacion = new JScrollPane();
			jScrollPaneConversacion.setBounds(new Rectangle(0, 157, 326, 219));
			jScrollPaneConversacion.setViewportView(getJTextAreaConversacion());
			jScrollPaneConversacion.setBorder(new javax.swing.border.TitledBorder(null, "Conversación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102)));
		}
		return jScrollPaneConversacion;
	}

	/**
	 * This method initializes jTextFieldNombreUsuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNombreUsuario() {
		if (jTextFieldNombreUsuario == null) {
			jTextFieldNombreUsuario = new JTextField();
			jTextFieldNombreUsuario.setBounds(new Rectangle(109, 63, 217, 23));
			jTextFieldNombreUsuario.setText("anonimo");
		}
		return jTextFieldNombreUsuario;
	}

	/**
	 * This method initializes jCheckBoxDurable	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxDurable() {
		if (jCheckBoxDurable == null) {
			jCheckBoxDurable = new JCheckBox();
			jCheckBoxDurable.setBounds(new Rectangle(307, 36, 21, 21));
			jCheckBoxDurable.setSelected(false);
			jCheckBoxDurable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// cambiando el estado del checkbox
					if (jCheckBoxDurable.isSelected()) {
						jTextFieldNombreDurable.setEnabled(true);
					}else {
						jTextFieldNombreDurable.setEnabled(false);
					}

				}
			});
		}
		return jCheckBoxDurable;
	}

	/**
	 * This method initializes jTextFieldNombreConversacion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNombreConversacion() {
		if (jTextFieldNombreConversacion == null) {
			jTextFieldNombreConversacion = new JTextField();
			jTextFieldNombreConversacion.setBounds(new Rectangle(151, 94, 178, 22));
			jTextFieldNombreConversacion.setText("laboratorio");
		}
		return jTextFieldNombreConversacion;
	}

	/**
	 * This method initializes jTextFieldTiempoDeVida	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTiempoDeVida() {
		if (jTextFieldTiempoDeVida == null) {
			jTextFieldTiempoDeVida = new JTextField();
			jTextFieldTiempoDeVida.setBounds(new Rectangle(118, 125, 103, 23));
			jTextFieldTiempoDeVida.setText("60000");
		}
		return jTextFieldTiempoDeVida;
	}

	/**
	 * This method initializes jButtonBorrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonBorrar() {
		if (jButtonBorrar == null) {
			jButtonBorrar = new JButton();
			jButtonBorrar.setBounds(new Rectangle(251, 124, 76, 25));
			jButtonBorrar.setText("Borrar");
			jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()");
					jTextAreaConversacion.setText("");
				}
			});
		}
		return jButtonBorrar;
	}

	/**
	 * This method initializes jTextFieldMensaje	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldMensaje() {
		if (jTextFieldMensaje == null) {
			jTextFieldMensaje = new JTextField();
			jTextFieldMensaje.setBounds(new Rectangle(3, 379, 320, 27));
			jTextFieldMensaje.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {				
					cm.send(jTextFieldMensaje.getText(),
							Integer.parseInt(jTextFieldTiempoDeVida.getText()));
					jTextFieldMensaje.setText("");
				}
			});
		}
		return jTextFieldMensaje;
	}

	/**
	 * This method initializes jTextFieldTopic	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTopic() {
		if (jTextFieldTopic == null) {
			jTextFieldTopic = new JTextField();
			jTextFieldTopic.setBounds(new Rectangle(40, 7, 85, 21));
			jTextFieldTopic.setText("Chat");
		}
		return jTextFieldTopic;
	}
	/**
	 * This method initializes jTextFieldNombreDurable	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNombreDurable() {
		if (jTextFieldNombreDurable == null) {
			jTextFieldNombreDurable = new JTextField();
			jTextFieldNombreDurable.setEnabled(false);
			jTextFieldNombreDurable.setBounds(new Rectangle(118, 35, 130, 21));
		}
		return jTextFieldNombreDurable;
	}

	/**
	 * This method initializes jButtonConectar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonConectar() {
		if (jButtonConectar == null) {
			jButtonConectar = new JButton();
			jButtonConectar.setBounds(new Rectangle(131, 7, 197, 22));
			jButtonConectar.setText("Conectar");
			jButtonConectar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						if(!conectado){
							cm.setParams(jTextFieldTopic.getText(), 
									jTextFieldNombreUsuario.getText(), 
									jTextFieldNombreConversacion.getText(), 
									jCheckBoxDurable.isSelected(), 
									jTextFieldNombreDurable.getText());
									//cm.close();
									cm.open();
									conectado=true;
									jButtonConectar.setText("Desconectar");
									jTextFieldNombreDurable.setEnabled(false);
									jCheckBoxDurable.setEnabled(false);
									jTextFieldTopic.setEnabled(false);
									jTextFieldNombreUsuario.setEnabled(false);
									jTextFieldNombreConversacion.setEnabled(false);
						} else {
							cm.close();
							jButtonConectar.setText("Conectar");
							conectado=false;
							jTextFieldNombreDurable.setEnabled(true);
							jCheckBoxDurable.setEnabled(true);
							jTextFieldTopic.setEnabled(true);
							jTextFieldNombreUsuario.setEnabled(true);
							jTextFieldNombreConversacion.setEnabled(true);
						}
					}
			    });
		}
		return jButtonConectar;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ChatUI thisClass = new ChatUI();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public ChatUI() {
		super();
		initialize();
		this.cm = new ChatManager();
		this.cm.setUI(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(341, 449);
		this.setContentPane(getJContentPane());
		this.setTitle("DyA Chat");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelNombreDurable = new JLabel();
			jLabelNombreDurable.setBounds(new Rectangle(6, 38, 109, 15));
			jLabelNombreDurable.setText("Nombre duradero");
			jLabelTopic = new JLabel();
			jLabelTopic.setBounds(new Rectangle(6, 7, 51, 22));
			jLabelTopic.setText("Topic");
			jLabelms = new JLabel();
			jLabelms.setBounds(new Rectangle(226, 130, 31, 16));
			jLabelms.setText("ms");
			jLabelTiempoDeVida = new JLabel();
			jLabelTiempoDeVida.setBounds(new Rectangle(6, 125, 104, 19));
			jLabelTiempoDeVida.setText(" Vida del mensaje");
			jLabelNombreConversacion = new JLabel();
			jLabelNombreConversacion.setBounds(new Rectangle(6, 96, 133, 16));
			jLabelNombreConversacion.setText("Nombre conversación");
			jLabelDurable = new JLabel();
			jLabelDurable.setBounds(new Rectangle(253, 37, 61, 18));
			jLabelDurable.setText("Duradero");
			jLabelUser = new JLabel();
			jLabelUser.setBounds(new Rectangle(6, 66, 95, 19));
			jLabelUser.setText("Nombre usuario");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPaneConversacion(), null);
			jContentPane.add(jLabelUser, null);
			jContentPane.add(getJTextFieldNombreUsuario(), null);
			jContentPane.add(jLabelDurable, null);
			jContentPane.add(getJCheckBoxDurable(), null);
			jContentPane.add(jLabelNombreConversacion, null);
			jContentPane.add(getJTextFieldNombreConversacion(), null);
			jContentPane.add(jLabelTiempoDeVida, null);
			jContentPane.add(getJTextFieldTiempoDeVida(), null);
			jContentPane.add(jLabelms, null);
			jContentPane.add(getJButtonBorrar(), null);
			jContentPane.add(getJTextFieldMensaje(), null);
			jContentPane.add(getJTextFieldTopic(), null);
			jContentPane.add(jLabelTopic, null);
			jContentPane.add(jLabelNombreDurable, null);
			jContentPane.add(getJTextFieldNombreDurable(), null);
			jContentPane.add(getJButtonConectar(), null);
		}
		return jContentPane;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="7,8"
