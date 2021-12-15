package proyecto3;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyecto3.PaqueteEnvio;
import proyecto3.Servidor;

import javax.swing.JTextArea;
import java.awt.FlowLayout;
public class Servidor extends JFrame implements Runnable {
	private JPanel contentPane;
	private JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor frame = new Servidor();
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
	public Servidor() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(0, 0, 386, 415);
		textArea.setEditable(false);
		contentPane.add(textArea);
		Thread mihilo = new Thread(this);
		mihilo.start();
		
	}

	@Override
	public void run() {
		try {
			ServerSocket servidor = new ServerSocket(3000);
			
			String nick, ip, mensaje;
			
			PaqueteEnvio paqueterecibido;
			
			
			Socket misocket = servidor.accept();
			
			ObjectInputStream paquetedatos = new ObjectInputStream(misocket.getInputStream());
	
				paqueterecibido = (PaqueteEnvio) paquetedatos.readObject();
				
				nick = paqueterecibido.getNick();
				ip = paqueterecibido.getIp();
				mensaje = paqueterecibido.getMensaje();
		    
		    textArea.append("\n"+nick+": "+mensaje+" para "+ip);
		    Socket enviaDestinatario = new Socket(ip, 9090);
		    ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
		    paqueteReenvio.writeObject(paqueterecibido);
		   paqueteReenvio.close();
		    enviaDestinatario.close();
		    misocket.close();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
