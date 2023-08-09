package pro04;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Menu frame = new Menu();
	private String records = "";

	/**
	 * Launch the application.
	 */
	public static void menu() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setLocationRelativeTo(null);
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 534);
		contentPane = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/Pic/settings.jpg")); // Adding
																												// picture
																												// to
																												// the
																												// background
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("START"); // start the game
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings Settings = new Settings();
				frame.setVisible(false);
				Settings.setLocationRelativeTo(null);
				Settings.setVisible(true);
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 153));
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
		btnNewButton.setBounds(43, 323, 168, 113);
		contentPane.add(btnNewButton);

		JButton btnScores = new JButton("SCORES"); // see the records
		btnScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * read from the file
				 */
				Scanner scanner = new Scanner(System.in);
				try {

					scanner = new Scanner(new File("Records.txt"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				while (scanner.hasNextLine()) {
					String[] split = scanner.nextLine().split(" ");

					records += split[0] + " " + split[1] + "\n";
				}
				JOptionPane.showInternalMessageDialog(contentPane, records, "", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnScores.setForeground(new Color(255, 255, 153));
		btnScores.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
		btnScores.setBackground(new Color(0, 0, 51));
		btnScores.setBounds(251, 323, 168, 113);
		contentPane.add(btnScores);

		JButton btnExit = new JButton("EXIT"); // close the menu
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setForeground(new Color(255, 255, 153));
		btnExit.setFont(new Font("Segoe UI Black", Font.BOLD, 23));
		btnExit.setBackground(new Color(0, 0, 51));
		btnExit.setBounds(462, 323, 168, 113);
		contentPane.add(btnExit);
	}

}
