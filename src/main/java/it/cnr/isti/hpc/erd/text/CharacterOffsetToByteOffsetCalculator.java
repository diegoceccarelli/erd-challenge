package it.cnr.isti.hpc.erd.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class CharacterOffsetToByteOffsetCalculator {

	private final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	private final Map<Integer, Integer> imap = new HashMap<Integer, Integer>();

	private final StringBuffer sb = new StringBuffer();

	public CharacterOffsetToByteOffsetCalculator() {

	}

	private void load(Reader reader) throws IOException {

		int c;

		int index = 0;
		int codepoint = 0;
		while ((c = reader.read()) != -1) {
			map.put(index, codepoint);
			imap.put(codepoint, index);
			codepoint += Character.charCount(codepoint);
			sb.append((char) c);
			index++;
		}
		map.put(index, codepoint);
		imap.put(codepoint, index);

	}

	public CharacterOffsetToByteOffsetCalculator loadFile(String file)
			throws IOException {
		return loadFile(new File(file));

	}

	public CharacterOffsetToByteOffsetCalculator loadFile(File file)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName("UTF-8")));
		load(reader);
		return this;
	}

	public CharacterOffsetToByteOffsetCalculator loadString(String string)
			throws IOException {
		load(new StringReader(string));
		return this;
	}

	public String getText() {
		return sb.toString();
	}

	public int getByteOffset(int offset) {
		return map.get(offset);
	}

	public static void main(String[] args) throws IOException {
		int index = 0;
		CharacterOffsetToByteOffsetCalculator conv = new CharacterOffsetToByteOffsetCalculator()
				.loadFile("/tmp/mainbody-00003");
		String text = conv.getText();
		while ((index = text.indexOf("JAPAN", index + 1)) != -1) {
			System.out.println(index + " - " + conv.getByteOffset(index));
		}
	}
}
