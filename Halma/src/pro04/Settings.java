package pro04;


import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Settings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 534);
		contentPane = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/Pic/settings.jpg"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Player 1:");
		lblNewLabel.setForeground(new Color(255, 255, 153));
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 21));
		lblNewLabel.setBounds(60, 64, 138, 72);
		contentPane.add(lblNewLabel);

		JLabel lblBoardSize = new JLabel("Board size:");
		lblBoardSize.setForeground(new Color(255, 255, 153));
		lblBoardSize.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 21));
		lblBoardSize.setBounds(60, 195, 164, 79);
		contentPane.add(lblBoardSize);

		JLabel lblMarbleRow = new JLabel("Marble Row:");
		lblMarbleRow.setForeground(new Color(255, 255, 153));
		lblMarbleRow.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 21));
		lblMarbleRow.setBounds(60, 274, 189, 79);
		contentPane.add(lblMarbleRow);

		textField = new JTextField();
		textField.setBounds(277, 217, 143, 43);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(277, 288, 143, 43);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(277, 83, 143, 43);
		contentPane.add(textField_2);

		JButton btnNewButton = new JButton("BACK"); // getting back to the main menu
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu.menu();
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 153));
		btnNewButton.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 18));
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setBounds(94, 403, 104, 51);
		contentPane.add(btnNewButton);

		JButton btnOk = new JButton("OK"); // starting the game
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name1 = textField_2.getText();
				String name2 = textField_3.getText();
				String bs = textField.getText();
				String mr = textField_1.getText();
				int marble_row = Integer.parseInt(mr);
				int board_size = Integer.parseInt(bs);
				if (board_size % 2 != 0) { // check if the board-size is valid
					JOptionPane.showInternalMessageDialog(contentPane, "Enter an even number for board size", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (marble_row < 1 || marble_row > board_size) { // check if the marble row num is valid
					JOptionPane.showInternalMessageDialog(contentPane, "INVALID MARBLE NUM", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (board_size % 2 == 0 && marble_row > 1 && marble_row < board_size) {
					dispose();
					Game.game(board_size, marble_row, name1, name2); // start the game
				}
			}
		});
		btnOk.setForeground(new Color(255, 255, 153));
		btnOk.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 18));
		btnOk.setBackground(new Color(0, 0, 102));
		btnOk.setBounds(456, 403, 104, 51);
		contentPane.add(btnOk);

		JLabel lblPlayer = new JLabel("Player 2:");
		lblPlayer.setForeground(new Color(255, 255, 153));
		lblPlayer.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 21));
		lblPlayer.setBounds(60, 135, 138, 72);
		contentPane.add(lblPlayer);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(277, 146, 143, 43);
		contentPane.add(textField_3);
	}
}
