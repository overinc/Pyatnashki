package ru.overprof.pyatnashki;

public class PyatnashkiStrings {

	public static String strPlay;
	
	public PyatnashkiStrings(String lang) {
		if (lang == "ru") {
			strPlay = "������";
		} 
		else if (lang == "en") {
			strPlay = "Go";
		}
	}
}
