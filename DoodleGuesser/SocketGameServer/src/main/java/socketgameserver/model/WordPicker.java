package socketgameserver.model;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class WordPicker {

	private WordPicker() {}

	public static String getRandomWord(InputStream stream) {
		String result = null;
		Random rand = new Random();
		int n = 0;
		for(Scanner sc = new Scanner(stream); sc.hasNext(); )
		{
			++n;
			String line = sc.nextLine();
			if(rand.nextInt(n) == 0)
				result = line;
		}

		return result;
	}
}
