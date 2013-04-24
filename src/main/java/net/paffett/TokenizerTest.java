package net.paffett;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenizerTest {

	private Pattern tokenPattern;

	public TokenizerTest() {
		tokenPattern = Pattern.compile("\\{([^}]+)\\}");
	}

	public String replaceTokens(String text, Map<String, String> valuesByKey) {
		StringBuilder output = new StringBuilder();
		Matcher tokenMatcher = tokenPattern.matcher(text);

		int cursor = 0;
		while (tokenMatcher.find()) {
			// A token is defined as a sequence of the format "{...}".
			// A key is defined as the content between the brackets.
			int tokenStart = tokenMatcher.start();
			int tokenEnd = tokenMatcher.end();
			int keyStart = tokenMatcher.start(1);
			int keyEnd = tokenMatcher.end(1);

			output.append(text.substring(cursor, tokenStart));

			String token = text.substring(tokenStart, tokenEnd);
			String key = text.substring(keyStart, keyEnd);

			if (valuesByKey.containsKey(key)) {
				String value = valuesByKey.get(key);
				output.append(value);
			} else {
				output.append(token);
			}

			cursor = tokenEnd;
		}
		output.append(text.substring(cursor));

		return output.toString();
	}
	
	public String replaceTokens(String text, List<String> values) {
		StringBuilder output = new StringBuilder();
		Matcher tokenMatcher = tokenPattern.matcher(text);

		int cursor = 0;
		while (tokenMatcher.find()) {
			// A token is defined as a sequence of the format "{...}".
			// A key is defined as the content between the brackets.
			int tokenStart = tokenMatcher.start();
			int tokenEnd = tokenMatcher.end();
			int keyStart = tokenMatcher.start(1);
			int keyEnd = tokenMatcher.end(1);

			output.append(text.substring(cursor, tokenStart));

			String token = text.substring(tokenStart, tokenEnd);
			String key = text.substring(keyStart, keyEnd);
			
			Integer index = Integer.parseInt(key);

			if (values.size() >= index) {
				String value = values.get(index);
				output.append(value);
			} else {
				output.append(token);
			}

			cursor = tokenEnd;
		}
		output.append(text.substring(cursor));

		return output.toString();
	}

	public static void main(String args[]) {
		TokenizerTest me = new TokenizerTest();	
		String queryNamed = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = {param}";
		String queryPosition = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = {0}";
		Map<String, String> paramMap = new HashMap<String, String>();
		List<String> paramList = new ArrayList<String>();
		
		
		paramMap.put("param", "HoochieMama");
		paramList.add("HoochieMama");
		
		System.out.println(queryNamed);
		System.out.println(me.replaceTokens(queryNamed, paramMap));
		System.out.println(queryPosition);
		System.out.println(me.replaceTokens(queryPosition, paramList));	
	}

}
