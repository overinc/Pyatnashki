package ru.overprof.pyatnashki;

public class PyatnashkiStrings {

	public static String strPlay;
	public static String strSettings;
	public static String strRecords;
	public static String strRealistikPyatn;
	public static String strQuickPyatn;
	public static String strShowHelpPosition;
	public static String strWithoutHelp;
	public static String strSteps;
	public static String strTime;
	
	public PyatnashkiStrings(String lang) {
		if (lang == "ru") {
			strPlay = "Играем";
			strSettings = "Настроить";
			strRecords = "Рекорды";
			strRealistikPyatn = "Реалистичные пятнашки";
			strQuickPyatn  = "Быстрые пятнашки";
			strShowHelpPosition = "Подсказывать позиции";
			strWithoutHelp = "Без подсказок";
			strSteps = "Ходы";
			strTime = "Время";
		} 
		else if (lang == "en") {
			strPlay = "Go";
			strSettings = "??? - nastroit";
			strRecords = "Records";
			strRealistikPyatn = "Realistik game";
			strQuickPyatn  = "Quick game";
			strShowHelpPosition = "Help positions";
			strWithoutHelp = "Without help";
			strSteps = "Steps";
			strTime = "Time";
		}
	}
}
