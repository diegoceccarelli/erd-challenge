/**
 *  Copyright 2014 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Jun 3, 2014
 */
public class OffsetToLinePointConverter {

	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	StringBuffer sb = new StringBuffer();

	public OffsetToLinePointConverter() {

	}

	private void load(Reader reader) throws IOException {

		int c;

		int index = 0;
		int codepoint = 0;
		while ((c = reader.read()) != -1) {
			map.put(index, codepoint);
			codepoint += Character.charCount(codepoint);
			sb.append((char) c);
			index++;
		}

	}

	public OffsetToLinePointConverter loadFile(String file) throws IOException {
		return loadFile(new File(file));

	}

	public OffsetToLinePointConverter loadFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName("UTF-8")));
		load(reader);
		return this;
	}

	public OffsetToLinePointConverter loadString(String string)
			throws IOException {
		load(new StringReader(string));
		return this;
	}

	public String getText() {
		return sb.toString();
	}

	public int getCodepoint(int offset) {
		return map.get(offset);
	}

	public static void main(String[] args) throws IOException {
		int index = 0;
		OffsetToLinePointConverter conv = new OffsetToLinePointConverter()
				.loadFile("/tmp/test");
		String text = conv.getText();
		while ((index = text.indexOf("JAPAN", index + 1)) != -1) {
			System.out.println(conv.getCodepoint(index));
		}
	}
}
