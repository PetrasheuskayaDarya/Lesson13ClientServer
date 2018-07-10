package by.htp.client.server.run;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
	/*
	 * Сервер запрашивает у пользователя номер изменяемого символа и символ, на
	 * который требуется выполнить замену. Клиент отправляет введённые данные на
	 * сервер. Сервер обращается к файлу (файл на сервере, в файле набор
	 * предложений), берёт из файла предложение и выполняет замену каждого К-ого
	 * символа в слове на новый (который получил от клиента). Изменённое предложение
	 * сервер отправляет клиенту и клиент записывает результат в свой файл
	 */

	public static void main(String[] args) {
		String s = enterData();
		byte[] data = connectWithServerOutputInput(s);
		getNewFileandWrite(data);

	}

	// считывание вводиимых данных пользователя
	public static String enterData() {
		String out, s, s1;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter number of symbol in a word, which you want change: ");
		while (true) {
			s = in.next();
			if (s.length() > 1) {
				System.out.println("You entered incorrect number, please repeat Enter");
				System.out.println("Number must be [0-9]");
				s = in.nextLine();
			} else
				break;
		}
		System.out.println("Enter symbol, which you want see: ");
		while (true) {
			s1 = in.next();
			if (s1.length() > 1) {
				System.out.println("You entered incorrect Symbol, please repeat Enter");
				System.out.println("Symbol must be one!");
				s1 = in.nextLine();
			} else
				break;
		}
		out = s + s1;
		in.close();
		return out;
	}

	// подключение к серверу и отпрвака данных
	public static byte[] connectWithServerOutputInput(String text) {
		byte[] data = new byte[1024];
		try {
			Socket clientSocket = new Socket("localhost", 9696);
			byte[] bytes = (text.getBytes());
			clientSocket.getOutputStream().write(bytes);
			System.out.println("Data seted!");
			InputStream is = clientSocket.getInputStream();
			is.read(data);
			System.out.println("Edited text recieved");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	// получение отредоктированного текста и запись его в файл
	public static void getNewFileandWrite(byte[] data) {
	
		File f = new File("source/textEdited.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String recived = new String(data);
		try (FileOutputStream os = new FileOutputStream(f)) {
			os.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(recived);

	}

}