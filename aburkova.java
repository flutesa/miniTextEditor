/**���� � ���-221
 * ��������: ����������� �������, ��� ����������� ���������� � �������� �� �����.
�������� ����� �������, ������� ������ ��������� ������:
1. ��� ������ ���������� ������������ ������������� ���������� ����(��������� ������
JFrame), ������� �������� ���� ����� (��������� ������ JTextField), ���� ������
��������� ���������� (��������� ������ JTextArea) � ����� ��������� ������
(���������� ������ JButton): ������ ������������� �����, ������ ������� ���� ����� �
������ ������ �� ����������.
2. ����� ��� ������ ���������� ����������� ��������� (������� ����������������) ��������� ����
3. ������������ ����� � ���� ����� ������ ������������ ������, �������� ��� ����, � ����� 
��������� ����������
4. ��������� ��� ������� �� ������ ������������� ����� ��������� ���������� ����
����� � ���������� ��� � �������� ���� ��� �������, ��� ����� ������ (��� �����
������� � ���������) � ������ ����� �� ����������. ���� ����� ������ � ����� ��� ����,
�� ��������� ������� ��������� �� ���� � ���� ������ ���������.
5. ��� ������� �� ������ ������ �� ���������� ��������� ��������� ���� ������

Author: Burkova A.S.
Date: 20 january 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class aburkova {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				makeGUI(); // ����� ������ ��������� �������� (�.1)

			}
		});
	}

	private static void makeGUI() {
		// ������� ����
		JFrame frame = new JFrame("Burkova Alexandra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 700));
		// ������������� ���������� ����
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private static void addComponents(Container pane) {
		// �������� ������������ ��������� � ����
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.insets = new Insets(10, 20, 10, 20);

		final JTextField tField = new JTextField("������� �����", 300);
		tField.setEditable(true);
		c.gridwidth = 3;
		c.ipady = 30;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(tField, c);

		final JTextArea tArea = new JTextArea("");
		tArea.setEditable(false);
		c.gridwidth = 3;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(tArea, c);

		final JButton bEnter = new JButton("������");
		c.gridwidth = 1;
		c.ipady = 15;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(bEnter, c);

		final JButton bClear = new JButton("��������");
		c.gridx = 1;
		c.gridy = 2;
		pane.add(bClear, c);

		final JButton bExit = new JButton("�����");
		c.gridx = 2;
		c.gridy = 2;
		pane.add(bExit, c);

		// ���� ������ ����������� txt ����� (�. 2)
		final JTextArea tTXT = new JTextArea(read());
		tTXT.setEditable(false);
		c.gridwidth = 3;
		c.ipady = 400;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(tTXT, c);

		// � ������ �������� ������ ������, ������ ��� ��������� ����������� ����� � ����
		JScrollPane scrollPane = new JScrollPane(tTXT);
		pane.add(scrollPane, c);

		// ��������� ������� � ��������� ����
		tField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				tArea.setText("����� �������� ����� � ����");
			}

			public void focusLost(FocusEvent arg0) {
				tArea.setText("����� � ���� ���� �� �������������");
			}
		});
		
		// ��������� ����� ����� � ���� ��� ��� ��������
		tField.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				tField.setText("");
			}
			public void mouseEntered(MouseEvent arg0) { }
			public void mouseExited(MouseEvent arg0) { }
			public void mousePressed(MouseEvent arg0) { }
			public void mouseReleased(MouseEvent arg0) { }
		});
		
		// ��������� �������� ������ ������ ��� �����������
		tField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				tArea.setText("��������� ����� � ����...");
			}
			public void keyReleased(KeyEvent arg0) {
				tArea.setText("��������� ����� � ����...");
			}
			public void keyTyped(KeyEvent arg0) {
				tArea.setText("��������� ����� � ����...");
			}
		});

		// ��������� ������� �� ������ (�.3)
		bEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyboardImput = (String) tField.getText();

				// �������� �� ������� ������ ������ � �����
				if (!checkText(keyboardImput, read())) {
					write(keyboardImput);
					tTXT.setText(read());
					tArea.setText("C����� ��������� � ����!");
				} else {
					tArea.setText("����� ������ � ����� ��� ����!");
				}
			}
		});

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tField.setText("");
				tArea.setText("���� �������!");
			}
		});

		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tArea.setText("������ �� ����������...");
				System.exit(0); // ����� �� ���������� �� ����������
								// ������������ (�.4)
			}
		});
	}

	public static String read() { // ������ ������ �� �����
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(
					"src/asadov.txt").getAbsoluteFile()));
			try {
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	public static void write(String text) { // ���������� ������ � ����� �����
		try {
			FileWriter out = new FileWriter(
					new File("src/asadov.txt").getAbsoluteFile(), true);
			try {
				out.append("\n");
				out.append(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean checkText(String subString, String string) {
		// ��������� ��������� ������ �� ������� � ��������� ����� (�.4)
		if (string.length() < subString.length())
			return false;

		int patternHash = 0;
		int currentHash = 0;

		for (int i = 0; i < subString.length(); i++) {
			patternHash += subString.charAt(i);
			currentHash += string.charAt(i);
		}

		int end = string.length() - subString.length() + 1;
		for (int i = 0; i < end; i++) {
			if (patternHash == currentHash)
				if (string.regionMatches(i, subString, 0, subString.length()))
					return true;

			currentHash -= string.charAt(i);
			if (i != end - 1)
				currentHash += string.charAt(i + subString.length());
		}
		return false;
	}

}
