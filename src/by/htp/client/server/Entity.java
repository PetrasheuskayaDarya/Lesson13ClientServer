package by.htp.client.server;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entity {
	private static int x = 0, y = 0;

	public static String editorline(String text, int k, char symbol) {
		String finishline = "",editedWord="";
		String[] str = text.split(" ");
		for (String e : str) {
			if (e.length() >= k) {
				String firstPartOfword = e.substring(0, k - 1) + symbol;
				editedWord = firstPartOfword.concat((e.substring(k, e.length())));
			} else {
				editedWord = e;
			}
			finishline = finishline.concat(editedWord) + " ";
		}
		return finishline;
	}

	public static StringBuilder editorTextStringBuilder(String text, int k, char symbol) {
		StringBuilder editedText = new StringBuilder();
		Pattern p = Pattern.compile("[ ,.!?\\r\\n\"]");
		Matcher m = p.matcher(text);
		while (m.find()) {
			if (m.end() > 0) {
				x = m.end();
				String word = text.substring(y, x);
				if (word.length() > k) {
					StringBuilder wordChanges = new StringBuilder(word);
					wordChanges.setCharAt(k - 1, symbol);
					editedText.append(wordChanges);
				} else {
					editedText.append(word);
				}
			} else {

				String s41 = text.substring(y, text.length());
				if (s41.length() > k) {
					StringBuilder str1 = new StringBuilder(s41);
					str1.setCharAt(k - 1, symbol);
					editedText.append(str1 + " ");
					break;
				}
			}
			y = x;

		}
		return editedText;

	}

}