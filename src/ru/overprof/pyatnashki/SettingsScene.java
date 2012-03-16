package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;

public class SettingsScene extends Scene {

	public SettingsScene(int pLayerCount) {
		super(pLayerCount);
		
		this.setBackground(new ColorBackground(0.4942f, 0.4987f, 0.5555f));
		
		String s = "Вид настроечки1";
		Text settinsgItem1 = new Text(0, 0, PyatnashkiActivity.menuFont, s);
		this.attachChild(settinsgItem1);
		
		s = "Вид настроечки2";
		Text settinsgItem2 = new Text(0, 50, PyatnashkiActivity.menuFont, s);
		this.attachChild(settinsgItem2);
		
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
