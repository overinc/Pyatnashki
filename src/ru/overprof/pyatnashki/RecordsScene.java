package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;

public class RecordsScene extends Scene {
	
	public static int stepsRecordCount;
	public static int timeRecordCount;

	public RecordsScene(int pLayerCount) {
		super(pLayerCount);
		
		this.setBackground(new ColorBackground(0.7385f, 0.3453f, 0.2375f));
		
		Update();

		
	}
	
	public void Update() {		
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
