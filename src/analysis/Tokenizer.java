package analysis;

import java.io.*;

public class Tokenizer {
	FileReader fr = null;
	int c = 0;
	int line = 1;

	public Tokenizer(String filename) {
		this(new File(filename));
	}

	public Tokenizer(File file) {
		try {
			fr = new FileReader(file);
			c = fr.read();
			while (c != -1 && Character.isWhitespace(c) && !isSeparator(c) )
				advance();
		} catch (IOException exc) {
			System.err.println("IOException: " + exc.getMessage());
		}
	}

	public boolean hasMoreTokens() {
		return (c != -1);
	}

	public boolean isSeparator(int c) {
		return c == ',' || c == '\n' || c == '\r' || c=='\t' || c==' '; // Character.isWhitespace(c);
	}

	void advance() {
		try {
			c = fr.read();
		} catch (IOException exc) {
			System.err.println("IOException: " + exc.getMessage());
		}
		if (c == '\n')
			line++;
	}

	public String nextToken() {
		StringWriter sr = new StringWriter();
		while (c != -1 && !isSeparator(c)) {
			sr.write(c);
			advance();
		}
		while (c != -1 && isSeparator(c))
			advance();
		return sr.toString();
	}

	public int nextInt() {
		return Integer.valueOf(nextToken()).intValue();
	}

	public long nextLong() {
		return Long.valueOf(nextToken()).longValue();
	}

	public float nextFloat() {
		return Float.valueOf(nextToken()).floatValue();
	}

	public double nextDouble() {
		try {
			return Double.valueOf(nextToken()).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public String nextString() {
		StringWriter sr = new StringWriter();
		advance(); // "
		while (c != -1 && c != '"') {
			sr.write(c);
			advance();
		}
		advance(); // "
		while (c != -1 && Character.isWhitespace(c))
			advance();
		return sr.toString();
	}

	public void skipTokens(int n) {
		for (int i = 0; i < n; i++)
			nextToken();
	}

	public void skipLine() {
		skipLines(1);
	}

	public void skipLines(int n) {
		for (int i = 0; i < n; i++) {
			while (c != -1 && c != '\n' && c != '\r')
				advance();
			if (c != -1) {
				advance();
				//advance();
			}
		}
	}

	public void skipUntil(String s) {
		while (hasMoreTokens() && !nextToken().equals(s))
			;
	}

	public void skipUntilBlankLine() {
		String s = "start";
		while (c != -1 && !s.equals("")) {
			s = "";
			while (c != -1 && c != '\n' && c != '\r') {
				s += c;
				advance();
			}
			if (c != -1) {
				advance();
				advance();
			}
		}
		// int lastLine = line;
		// while (hasMoreTokens() && lastLine>=line-1)
		// {
		// lastLine = line;
		// nextToken();
		// }
	}
}