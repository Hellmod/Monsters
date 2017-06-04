package testy;

import java.net.*;
import java.io.*;

public class URLExample
{
	public static void main(String[] args) throws Exception
	{
		URL url = new URL("https://www.google.pl/search?q=kot&client=firefox-b&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiso5bc7fnMAhUFVBQKHfr-Bt4Q_AUIBygB&biw=1280&bih=891");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String s;
		while ((s = in.readLine()) != null)
			System.out.println(s);
		in.close();
	}
}
