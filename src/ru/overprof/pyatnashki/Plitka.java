package ru.overprof.pyatnashki;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.HorizontalAlign;

import ru.overprof.pyatnashki.MainState.GAMESTATUS;

import android.sax.StartElementListener;


public class Plitka extends Sprite{

	private final static int COUNTER = GameScene.COUNTER;
	private final static Validate LeftUpperAreaPoint = GameScene.LeftUpperAreaPoint;
	public static final int WidthPlitkaWithDistanse = GameScene.WidthPlitkaWithDistanse;
	
	private short id;
	public boolean isRightPos;
	public int positionX;
	public int positionY;
	private Text number;
	private ChangeableText zeroone;

	public Plitka(final float pX, final float pY, final TextureRegion pTextureRegion, int pId,Font pFont) {		
		super(LeftUpperAreaPoint.x + pX * WidthPlitkaWithDistanse, LeftUpperAreaPoint.y + pY * WidthPlitkaWithDistanse, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion);
		positionX = (int) pX;
		positionY = (int) pY;
		id = (short) pId;
		
		String s1;
		s1 = String.format("0");	
		zeroone = new ChangeableText(35,30,pFont,s1);
		this.attachChild(zeroone);
		
		renewRightPos();
		
		String s;
		s = String.format("%01d",id);		
		number = new Text(12,10,pFont,s);
		this.attachChild(number);
		
		int x;
		if (isRightPos)
			x = 1;
		else 
			x = 0;

		
	}

	public int getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
	public void renewRightPos() {
		if ((id - 1) == (positionX + positionY * 4))
			isRightPos = true;
		else
			isRightPos = false;
		int x;
		if (isRightPos)
			x = 1;
		else 
			x = 0;
		String s;
		s = String.format("%01d",x);
		zeroone.setText(s);
	}
	

	
	

}
