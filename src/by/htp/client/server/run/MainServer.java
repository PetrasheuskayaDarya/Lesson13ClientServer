package by.htp.client.server.run;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class MainServer {

	public static void main(String[] args) throws IOException {

		// создание сервера и получение данных от клиента
		ServerSocket serverSocket = new ServerSocket(9696);
		try {
			System.out.println("Server started");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected");

			InputStream is = clientSocket.getInputStream();

			byte[] data = new byte[1024];
			is.read(data);
			System.out.println("Data recieved");
			String recived = new String(data);
			char[] str = recived.toCharArray();
			int k = 0;
			char symbol = '!';
			if (str.length > 2) {
				k = Character.getNumericValue(str[0]);
				symbol = str[1];
			} else {
				System.out.println("Array of data is empty");
			}
			byte[] editedBytes = (readFromFileandEdit(k, symbol).getBytes());
			clientSocket.getOutputStream().write(editedBytes);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}
	}

	// считывание текста из файла и его редактирование
	public static String readFromFileandEdit(int k, char symbol) {
		// создание и проверка файла
		File f = new File("source/textOriginal.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String finishText = "";
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String finishLine = null;
			while ((finishLine = br.readLine()) != null) {
				finishText = finishText.concat(Entity.editorline(finishLine, k, symbol) + "\n");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return finishText;
	}
}