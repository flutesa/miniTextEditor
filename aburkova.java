/**Тест № ВПТ-221
 * Описание: Контрольное задание, для определения готовности к обучению на курсе.
Напишите набор классов, который решает следующую задачу:
1. При старте приложения пользователю предъявляется диалоговое окно(экземпляр класса
JFrame), которое содержит поле ввода (экземпляр класса JTextField), поле вывода
сообщений приложения (экземпляр класса JTextArea) и набор следующих кнопок
(экземпляры класса JButton): кнопку подтверждения ввода, кнопку очистки поля ввода и
кнопку выхода из приложения.
2. Также при старте приложения открывается некоторый (заранее предопределенный) текстовый файл
3. Пользователь может в поле ввода ввести произвольную строку, очистить это поле, а также 
завершить приложение
4. Программа при нажатии на кнопку подтверждения ввода считывает содержимое поля
ввода и записывает его в открытый файл при условии, что такая строка (без учета
разницы в регистрах) в данном файле не содержится. Если такая строка в файле уже есть,
то программа выводит сообщение об этом в поле вывода сообщений.
5. При нажатии на кнопку выхода из приложения программа завершает свою работу

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
				makeGUI(); // вызов метода наведения марафета (п.1)

			}
		});
	}

	private static void makeGUI() {
		// создаём окно
		JFrame frame = new JFrame("Burkova Alexandra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 700));
		// устанавливаем компоненты окна
		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private static void addComponents(Container pane) {
		// описание расположения элементов в окне
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.insets = new Insets(10, 20, 10, 20);

		final JTextField tField = new JTextField("‚ведите текст", 300);
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

		final JButton bEnter = new JButton("Ввести");
		c.gridwidth = 1;
		c.ipady = 15;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(bEnter, c);

		final JButton bClear = new JButton("Очистить");
		c.gridx = 1;
		c.gridy = 2;
		pane.add(bClear, c);

		final JButton bExit = new JButton("Выйти");
		c.gridx = 2;
		c.gridy = 2;
		pane.add(bExit, c);

		// поле вывода содержимого txt файла (п. 2)
		final JTextArea tTXT = new JTextArea(read());
		tTXT.setEditable(false);
		c.gridwidth = 3;
		c.ipady = 400;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(tTXT, c);

		// в случае большого объёма текста, скролл для прокрутки содержимого файла в поле
		JScrollPane scrollPane = new JScrollPane(tTXT);
		pane.add(scrollPane, c);

		// обработка событий в текстовом поле
		tField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				tArea.setText("Можно добавить текст в поле");
			}

			public void focusLost(FocusEvent arg0) {
				tArea.setText("Текст в поле пока не редактируется");
			}
		});
		
		// обработка клика мышки в поле для его очищения
		tField.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				tField.setText("");
			}
			public void mouseEntered(MouseEvent arg0) { }
			public void mouseExited(MouseEvent arg0) { }
			public void mousePressed(MouseEvent arg0) { }
			public void mouseReleased(MouseEvent arg0) { }
		});
		
		// обработка процесса набора текста для уведомления
		tField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				tArea.setText("Добавляем текст в поле...");
			}
			public void keyReleased(KeyEvent arg0) {
				tArea.setText("Добавляем текст в поле...");
			}
			public void keyTyped(KeyEvent arg0) {
				tArea.setText("Добавляем текст в поле...");
			}
		});

		// обработка событий от кнопок (п.3)
		bEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyboardImput = (String) tField.getText();

				// проверка на наличие данной строки в файле
				if (!checkText(keyboardImput, read())) {
					write(keyboardImput);
					tTXT.setText(read());
					tArea.setText("Cтрока добавлена в файл!");
				} else {
					tArea.setText("Такая строка в файле уже есть!");
				}
			}
		});

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tField.setText("");
				tArea.setText("Поле очищено!");
			}
		});

		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tArea.setText("выхожу из приложения...");
				System.exit(0); // выход из приложения по требованию
								// пользователя (п.4)
			}
		});
	}

	public static String read() { // читаем данные из файла
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

	public static void write(String text) { // записываем данные в конец файла
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
		// проверяем введённую строку на наличие в текстовом файле (п.4)
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
