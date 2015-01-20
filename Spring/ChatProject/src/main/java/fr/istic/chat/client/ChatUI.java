package fr.istic.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatUI {

	private Command unregister;
	private Command postMessage;
	private IChatUser user;

	private String title;

	private JFrame window;

	private JTextArea txtOutput;

	private JTextField txtMessage;

	private JButton btnSend;

	protected String message;

	public ChatUI() {
		title		= "Logiciel de discussion en ligne";
		window 		= new JFrame(this.title);
		txtOutput 	= new JTextArea();
		txtMessage 	= new JTextField();
		btnSend 	= new JButton("Envoyer");
	}

	/**
	 * Gets the message.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the user.
	 * @return
	 */
	public IChatUser getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * @param user
	 */
	public void setUser(IChatUser user) {
		this.user = user;
	}

	/**
	 * Gets the unregister command.
	 * @return
	 */
	public Command getUnregister() {
		return unregister;
	}

	/**
	 * Sets the unregister command.
	 * @param unregister
	 */
	public void setUnregister(Command unregister) {
		this.unregister = unregister;
	}

	/**
	 * Gets the postMessage command.
	 * @return
	 */
	public Command getPostMessage() {
		return postMessage;
	}

	/**
	 * Sets the postMessage command.
	 * @param postMessage
	 */
	public void setPostMessage(Command postMessage) {
		this.postMessage = postMessage;
	}

	/**
	 * Creates the window.
	 */
	public void window_create() {
		JPanel panel = (JPanel) this.window.getContentPane();
		JScrollPane sclPane = new JScrollPane(txtOutput);
		panel.add(sclPane, BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(this.txtMessage, BorderLayout.CENTER);
		southPanel.add(this.btnSend, BorderLayout.EAST);
		panel.add(southPanel, BorderLayout.SOUTH);

		// Gestion des évènements
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				window_windowClosing(e);
			}
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSend_actionPerformed(e);
			}
		});
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				if (event.getKeyChar() == '\n')
					btnSend_actionPerformed(null);
			}
		});

		// Initialisation des attributs
		this.txtOutput.setBackground(new Color(220, 220, 220));
		this.txtOutput.setEditable(false);
		this.window.setSize(500, 400);
		this.window.setVisible(true);
		this.txtMessage.requestFocus();
	}

	/**
	 * Method called when the window is closed.
	 * @param e
	 */
	public void window_windowClosing(WindowEvent e) {
		try {
			unregister.execute();
		} catch (Exception exc) {
			System.err.println("Desinscription impossible");
		}
		System.exit(-1);
	}

	/**
	 * Method called when the btnSend is clicked.
	 * @param e
	 */
	public void btnSend_actionPerformed(ActionEvent e) {
		try {
			message = this.txtMessage.getText();
			postMessage.execute();
		} catch (Exception exception) {
			System.err.println("Envoie message impossible");
			exception.printStackTrace();
		}
		this.txtMessage.setText("");
		this.txtMessage.requestFocus();
	}

	/**
	 * Displays the message in the chatBox.
	 * @param message
	 */
	public void displayMessage(String message) {
		this.txtOutput.append(message + "\n");
		this.txtOutput.moveCaretPosition(this.txtOutput.getText().length());
	}

	/**
	 * Displays a dialog to prompt the user's 'pseudo'.
	 * @return
	 */
	public String requestPseudo() {
		String pseudo = JOptionPane.showInputDialog(
				this.window, "Entrez votre pseudo : ",
				this.title,  JOptionPane.OK_OPTION
				);
		if (pseudo == null) System.exit(0);
		return pseudo;
	}
}
