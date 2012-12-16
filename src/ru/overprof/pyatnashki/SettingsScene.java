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
		
		this.setBackground(new ColorBackground(0.4942f, 0.4987f, 0.5555f));
		
		final String settingsItem1Text1 = PyatnashkiStrings.strRealistikPyatn;
		final String settingsItem1Text2 = PyatnashkiStrings.strQuickPyatn;		
		
		String settingsItem1Text;
		
		if (GameScene.REALITY)
			settingsItem1Text = settingsItem1Text1;
		else
			settingsItem1Text = settingsItem1Text2;
		
		final ChangeableText changeableTextControl1 = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, settingsItem1Text);
		Rectangle settingsItem1 = new Rectangle(0, 50, changeableTextControl1.getWidth(), changeableTextControl1.getHeight()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (GameScene.REALITY/*PyatnashkiActivity.mainState_.gameScene_.REALITY*/) {
						changeableTextControl1.setText(settingsItem1Text2);
						this.setWidth(changeableTextControl1.getWidth());
						GameScene.REALITY = false;
					}
					else {
						changeableTextControl1.setText(settingsItem1Text1);
						this.setWidth(changeableTextControl1.getWidth());
						GameScene.REALITY = true;
					}
				}
				return true;
			}
		};
		settingsItem1.setColor(20, 100, 200);
		settingsItem1.attachChild(changeableTextControl1);
		
		this.attachChild(settingsItem1);
		this.registerTouchArea(settingsItem1);		
		
		final String settingsItem2Text1 = PyatnashkiStrings.strShowHelpPosition;
		final String settingsItem2Text2 = PyatnashkiStrings.strWithoutHelp;
		String settingsItem2Text;
		if (GameScene.HELPING)
			settingsItem2Text = settingsItem2Text1;
		else
			settingsItem2Text = settingsItem2Text2;
		final ChangeableText changeableTextControl2 = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, settingsItem2Text);
		Rectangle settingsItem2 = new Rectangle(0, 150, changeableTextControl2.getWidth(), changeableTextControl2.getHeight()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (GameScene.HELPING) {
						changeableTextControl2.setText(settingsItem2Text2);
						this.setWidth(changeableTextControl2.getWidth());
						GameScene.HELPING = false;
					}
					else {
						changeableTextControl2.setText(settingsItem2Text1);
						this.setWidth(changeableTextControl2.getWidth());
						GameScene.HELPING = true;
					}
				}
				return true;
			}
		};
		settingsItem2.setColor(20, 100, 200);
		settingsItem2.attachChild(changeableTextControl2);
		
		this.attachChild(settingsItem2);
		this.registerTouchArea(settingsItem2);
		
		
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
