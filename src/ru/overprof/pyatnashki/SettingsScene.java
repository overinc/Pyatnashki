package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;

import android.content.SharedPreferences.Editor;

public class SettingsScene extends Scene {

	public SettingsScene(int pLayerCount) {
		super(pLayerCount);	
		
		
		
	}
	
	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {		
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public static void SaveSettings()
	{
		Editor editor = PyatnashkiActivity.mSettings.edit();
		editor.putBoolean(PyatnashkiActivity.PREF_TYPE_GAME, GameScene.REALITY);
		editor.putBoolean(PyatnashkiActivity.PREF_HELP_GAME, GameScene.HELPING);
		editor.commit();
		for (int i=0; i < GameScene.COUNTER * GameScene.COUNTER - 1; i++)
			GameScene.setOfTiles[i].revalidateIt();
		
	}

}
