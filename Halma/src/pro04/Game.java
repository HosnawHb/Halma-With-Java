package pro04;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton[][] t;
	private int bs, marble, whitemove = 0, blackmove = 1, player = 1, k, l, whitewin = 0, blackwin = 0;
	private BufferedImage black, white;
	private ImageIcon imageicon, imageicon2; // imageicon = black marble // imageucon2 = white marble
	private boolean select = true;
	private String p1, p2;
	private Border bored = BorderFactory.createLineBorder(Color.cyan,7);
	private Border boredselected = BorderFactory.createLineBorder(Color.red,7);

	public static void game(int board_size, int mr, String player1, String player2) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                  
					Game frame = new Game(board_size, mr, player1, player2); // starting the game
              
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Game(int board_size, int mr, String player1, String player2) {
		t = new JButton[board_size][board_size];
		bs = board_size;
		marble = mr;
		p1 = player1;
		p2 = player2;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1013, 705);
		contentPane = new JPanel();
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 766, 668);
		contentPane.add(panel);
		JButton btnNewButton = new JButton("Menu"); // getting back to the menu
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Menu.menu();
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(Color.white);
		btnNewButton.setFont(new Font("Snap ITC", Font.BOLD, 17));
		btnNewButton.setBounds(823, 113, 116, 84);
		contentPane.add(btnNewButton);

		JButton btnRestart = new JButton("Restart"); // restarting the same game
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Game.game(board_size, marble, p1, p2);
			}
		});
		btnRestart.setForeground(new Color(0, 0, 0));
		btnRestart.setBackground(Color.white);
		btnRestart.setFont(new Font("Snap ITC", Font.BOLD, 17));
		btnRestart.setBounds(823, 244, 116, 84);
		contentPane.add(btnRestart);
		if (board_size > 0) {
			panel.setLayout(new GridLayout(board_size, board_size, 0, 0));
		}

		int colour = 1; // designing the board
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				t[i][j] = new JButton("");
				if (colour % 2 != 0) {
					t[i][j].setBackground(new Color(51, 0, 0));

				} else {
					t[i][j].setBackground(new Color(255, 204, 102));

				}

				t[i][j].setBounds(0, 0, 100, 100);
				colour++;
				panel.add(t[i][j]);
				t[i][j].addActionListener(this);
				if (j == board_size - 1) {
					if (i % 2 == 0) {
						colour = 2;
					} else {
						colour = 1;
					}
				}
			}
		}
		for (int i = 0, k = 0; i < mr; i++, k++) { // placing black marbles
			for (int j = 0; j < mr - k; j++) {
				try {
					black = ImageIO.read(getClass().getResource("/Pic/black.png"));
					imageicon = new ImageIcon(black.getScaledInstance(100, -1, Image.SCALE_SMOOTH));
					t[i][j].setIcon(imageicon);
					t[i][j].setForeground(Color.black);
				} catch (Exception ex) {
					System.out.println(ex);
				}

			}
		}
		for (int i = board_size - mr, k = 1; i < board_size; i++, k++) { // placing white marbles
			for (int j = board_size - k; j < board_size; j++) {
				try {
					white = ImageIO.read(getClass().getResource("/Pic/white.png"));
					imageicon2 = new ImageIcon(white.getScaledInstance(100, -1, Image.SCALE_SMOOTH));
					t[i][j].setIcon(imageicon2);
					t[i][j].setForeground(Color.white);
				} catch (Exception ex) {
					System.out.println(ex);
				}

			}

		}

		black_move(board_size); // black marbles start the game
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < bs; i++) {
			for (int j = 0; j < bs; j++) {
				if (e.getSource().equals(t[i][j])) {
					if (select) {
						selector(i, j, bs);
						t[i][j].setBorder(boredselected);  
						k = i; // k= i of the selected tile
						l = j; // l = j of the selected tile
						select = false;
					} else {
						move(i, j, k, l); // moving the selected marble
						wincheck(bs, marble, whitemove, blackmove, whitewin, blackwin);
						if (player == 1) {
							white_move(bs);
							whitemove++; // count white's moves
							player = 2;

						} else {
							black_move(bs);
							blackmove++; // count black's moves
							player = 1;
						}

						for (int i1 = 0; i1 < bs; i1++) {
							for (int j1 = 0; j1 < bs; j1++) {
								if (t[i1][j1].getBorder() != null) {
									t[i1][j1].setBorder(null);
								}
							}
						}
						select = true;

					}
				}
			}
		}
	}

	public void white_move(int board_size) { // disabling black marbles
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (t[i][j].getForeground().equals(Color.white)) {
					t[i][j].setEnabled(true);
				} else if (t[i][j].getForeground().equals(Color.black)) {
					t[i][j].setEnabled(false);
					t[i][j].setDisabledIcon(imageicon);
				} else {
					t[i][j].setEnabled(false);
				}
			}
		}
	}

	public void black_move(int board_size) { // disabling white marbles
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (t[i][j].getForeground().equals(Color.black)) {
					t[i][j].setEnabled(true);
				} else if (t[i][j].getForeground().equals(Color.white)) {
					t[i][j].setEnabled(false);
					t[i][j].setDisabledIcon(imageicon2);
				} else {
					t[i][j].setEnabled(false);
				}
			}
		}
	}

	public void selector(int i, int j, int board_size) { // selecting a tile + check validation
		disable(board_size);
		for (int k = 0; k < board_size; k++) {
			for (int l = 0; l < board_size; l++) {
				if (k == i - 1 && l == j - 1 || k == i - 1 && l == j || k == i - 1 && l == j + 1 || k == i && l == j - 1
						|| k == i && l == j + 1 || k == i + 1 && l == j - 1 || k == i + 1 && l == j
						|| k == i + 1 && l == j + 1) {
					if (t[k][l].getForeground() != Color.black && t[k][l].getForeground() != Color.white) {
						t[k][l].setEnabled(true);
						t[k][l].setBorder(bored);
					}
				}
			}
		}

		chainjump(i, j, Color.cyan);

	}

	public void disable(int board_size) { // disabling all the tiles
		for (int k = 0; k < board_size; k++) {
			for (int l = 0; l < board_size; l++) {
				t[k][l].setEnabled(false);
				if (t[k][l].getForeground().equals(Color.black)) {
					t[k][l].setDisabledIcon(imageicon);
				} else if (t[k][l].getForeground().equals(Color.white)) {
					t[k][l].setDisabledIcon(imageicon2);
				}
			}
		}
	}

	public void move(int i, int j, int k, int l) { // moving to a tile
		t[k][l].setIcon(null);
		if (t[k][l].getForeground().equals(Color.black)) {
			try {
				black = ImageIO.read(getClass().getResource("/Pic/black.png"));
				imageicon = new ImageIcon(black.getScaledInstance(100, -1, Image.SCALE_SMOOTH));
				t[i][j].setIcon(imageicon);
				t[i][j].setForeground(Color.black);
				t[k][l].setForeground(null);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

		else if (t[k][l].getForeground().equals(Color.white)) {
			try {
				white = ImageIO.read(getClass().getResource("/Pic/white.png"));
				imageicon2 = new ImageIcon(white.getScaledInstance(100, -1, Image.SCALE_SMOOTH));
				t[i][j].setIcon(imageicon2);
				t[i][j].setForeground(Color.white);
				t[k][l].setForeground(null);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public int marblecounter(int mr) { // count all the marbles
		int marblecount = 0;
		for (int i = 1; i <= mr; i++) {
			marblecount += i;
		}
		return marblecount;
	}

	public void wincheck(int board_size, int mr, int whitemove, int blackmove, int white, int black) {
		int marble = 0;
		marble = marblecounter(mr);
		/*
		 * white wins
		 */
		white = 0;
		black = 0;
		for (int i = 0, k = 0; i < mr; i++, k++) {
			for (int j = 0; j < mr - k; j++) {
				if (t[i][j].getForeground().equals(Color.white)) {
					white++;
				}
			}
		}
		/*
		 * black wins
		 */
		for (int i = board_size - mr, k = 1; i < board_size; i++, k++) {
			for (int j = board_size - k; j < board_size; j++) {
				if (t[i][j].getForeground().equals(Color.black)) {
					black++;
				}

			}
		}
		if (white == marble) {
			JOptionPane.showInternalMessageDialog(contentPane, p2 + "  WINS! \n", "", JOptionPane.INFORMATION_MESSAGE);
			records(p2, whitemove);
			disable(bs);
			dispose();
		} else if (black == marble) {
			JOptionPane.showInternalMessageDialog(contentPane, p1 + "  WINS! \n", "", JOptionPane.INFORMATION_MESSAGE);
			records(p1, blackmove);
			disable(bs);
			dispose();
		}
	}

	public void records(String winner, int movement) { // writing records to the file
		try {
			FileWriter writer = new FileWriter("Records.txt", true);
			writer.write(winner + " " + movement+"\n");
			writer.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void chainjump(int i, int j, Color c) { // jumping over other marbles
		if (t[i][j].getBorder() != bored) { // not to check the checked tiles
			t[i][j].setBorder(bored);
			for (int k = 0; k < bs; k++) {
				for (int l = 0; l < bs; l++) {
					if (k == i - 1 && l == j - 1 || k == i - 1 && l == j || k == i - 1 && l == j + 1
							|| k == i && l == j - 1 || k == i && l == j + 1 || k == i + 1 && l == j - 1
							|| k == i + 1 && l == j || k == i + 1 && l == j + 1) {
						if (t[k][l].getForeground().equals(Color.white)
								|| t[k][l].getForeground().equals(Color.black)) {
							if ((k == i - 1 && l == j - 1) && (k - 1 >= 0) && (l - 1 >= 0)
									&& (t[k - 1][l - 1].getForeground() != Color.black
											&& t[k - 1][l - 1].getForeground() != Color.white)
									&& (c != Color.pink)) {
								t[k - 1][l - 1].setEnabled(true);
								chainjump(i - 2, j - 2, Color.red);
							}
							if ((k == i - 1 && l == j) && (k - 1 >= 0) && (t[k - 1][l].getForeground() != Color.black
									&& t[k - 1][l].getForeground() != Color.white) && (c != Color.orange)) {
								t[k - 1][l].setEnabled(true);
								chainjump(i - 2, j, Color.green);

							}

							if ((k == i - 1 && l == j + 1) && (k - 1 >= 0) && (l + 1 < bs)
									&& (t[k - 1][l + 1].getForeground() != Color.black
											&& t[k - 1][l + 1].getForeground() != Color.white)
									&& (c != Color.darkGray)) {
								t[k - 1][l + 1].setEnabled(true);
								chainjump(i - 2, j + 2, Color.white);

							}

							if ((k == i && l == j - 1) && (l - 1 >= 0) && (t[k][l - 1].getForeground() != Color.black
									&& t[k][l - 1].getForeground() != Color.white) && (c != Color.blue)) {
								t[k][l - 1].setEnabled(true);
								chainjump(i, j - 2, Color.black);

							}

							if ((k == i && l == j + 1) && (l + 1 < bs) && (t[k][l + 1].getForeground() != Color.black
									&& t[k][l + 1].getForeground() != Color.white) && (c != Color.black)) {
								t[k][l + 1].setEnabled(true);
								chainjump(i, j + 2, Color.blue);

							}
							if ((k == i + 1 && l == j - 1) && (k + 1 < bs) && (l - 1 >= 0)
									&& (t[k + 1][l - 1].getForeground() != Color.black
											&& t[k + 1][l - 1].getForeground() != Color.white)
									&& (c != Color.white)) {
								t[k + 1][l - 1].setEnabled(true);
								chainjump(i + 2, j - 2, Color.darkGray);
							}

							if ((k == i + 1 && l == j) && (k + 1 < bs) && (t[k + 1][l].getForeground() != Color.black
									&& t[k + 1][l].getForeground() != Color.white) && (c != Color.green)) {
								t[k + 1][l].setEnabled(true);
								chainjump(i + 2, j, Color.orange);
							}
							if ((k == i + 1 && l == j + 1) && (k + 1 < bs) && (l + 1 < bs)
									&& (t[k + 1][l + 1].getForeground() != Color.black
											&& t[k + 1][l + 1].getForeground() != Color.white)
									&& (c != Color.red)) {
								t[k + 1][l + 1].setEnabled(true);
								chainjump(i + 2, j + 2, Color.pink);

							}
						}

					}
				}
			}

		}
	}

}
