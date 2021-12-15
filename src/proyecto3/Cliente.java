package proyecto3;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyecto3.Cliente;
import proyecto3.PaqueteEnvio;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
public class Cliente extends JFrame implements Runnable{
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JTextField textField_1;
	private JTextField textField_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 369, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("-chat-");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(159, 21, 63, 23);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(77, 270, 207, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					Socket misocket = new Socket("192.168.88.24",3000);
					
					PaqueteEnvio datos = new PaqueteEnvio();
					datos.setNick(textField_1.getText());
					datos.setIp(textField_2.getText());
					datos.setMensaje(textField.getText());
					textField.setText(null);
					ObjectOutputStream paquetedatos = new ObjectOutputStream(misocket.getOutputStream());
					
					paquetedatos.writeObject(datos);
					misocket.close();
					
					
					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(143, 313, 85, 21);
		contentPane.add(btnNewButton);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 54, 335, 179);
		contentPane.add(textArea);
		
		textField_1 = new JTextField();
		textField_1.setBounds(29, 24, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(219, 24, 126, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		Thread mihilo = new Thread(this);
		mihilo.start();
	}

	@Override
	public void run() {
		try {
			ServerSocket servidorcliente = new ServerSocket(9090);
			Socket cliente;
			
			PaqueteEnvio paqueterecibido;
			
			while(true) {
				
				cliente = servidorcliente.accept();
				
				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
				
				paqueterecibido = (PaqueteEnvio) flujoentrada.readObject();
				textArea.append("\n"+paqueterecibido.getNick()+": "+paqueterecibido.getMensaje());
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
