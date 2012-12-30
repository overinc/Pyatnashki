package ru.overprof.pyatnashki;

public class PyatnashkiStrings {

	public static String strPlay;
	
	public PyatnashkiStrings(String lang) {
		if (lang == "ru") {
			strPlay = "Играем";
		} 
		else if (lang == "en") {
			strPlay = "Go";
		}
	}
}
