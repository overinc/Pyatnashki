package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;

public class SettingsScene extends Scene {

	public SettingsScene(int pLayerCount) {
		super(pLayerCount);
		
		this.setBackground(new ColorBackground(0.4942f, 0.4987f, 0.5555f));
		
		final String settingsItem1Text1 = "Реалистичные пятнашки";
		final String settingsItem1Text2 = "Быстрые пятнашки";
		
		final ChangeableText changeableTextControl1 = new ChangeableText(0, 0, PyatnashkiActivity.menuFont, settingsItem1Text1);
		Rectangle settingsItem1 = new Rectangle(0, 50, changeableTextControl1.getWidth(), changeableTextControl1.getHeight()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (PyatnashkiActivity.mainState_.gameScene_.REALITY) {
						changeableTextControl1.setText(settingsItem1Text2);
						this.setWidth(changeableTextControl1.getWidth());
						PyatnashkiActivity.mainState_.gameScene_.REALITY = false;
					}
					else {
						changeableTextControl1.setText(settingsItem1Text1);
						this.setWidth(changeableTextControl1.getWidth());
						PyatnashkiActivity.mainState_.gameScene_.REALITY = true;
					}
				}
				return true;
			}
		};
		settingsItem1.setColor(20, 100, 200);
		settingsItem1.attachChild(changeableTextControl1);
		
		this.attachChild(settingsItem1);
		this.registerTouchArea(settingsItem1);
		
		String s = "Подсказывать позиции";
		Text settingsItem2 = new Text(0, 200, PyatnashkiActivity.menuFont, s);
		this.attachChild(settingsItem2);
		
		
	}
	
	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {		
		setVisible(false);
		setIgnoreUpdate(true);
	}

}
