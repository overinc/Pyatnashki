package ru.overprof.pyatnashki;

import java.util.Random;
import java.util.Timer;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;

public class GameScene extends Scene {
	
	public static final int COUNTER = 4;
	public static final Validate LEFTUPPERPOINTOFPLITKI = new Validate();
	public static final int WIDHTOFPLITKAANDDISTANSE = 70;
	
	int steps = 0;
	
	public GameScene(int pLayerCount) {
		super(pLayerCount);		

		this.setBackground(new ColorBackground(0.01023f, 0.4867f, 0.2170f));		
		
		final ChangeableText cisla = new ChangeableText(500, 50, PyatnashkiActivity.mFont, "", 50);
		final ChangeableText time = new ChangeableText(500, 150, PyatnashkiActivity.mFont, "", 50);
		
		LEFTUPPERPOINTOFPLITKI.x = PyatnashkiActivity.CAMERA_WIDTH / 2 - WIDHTOFPLITKAANDDISTANSE * 2;
		LEFTUPPERPOINTOFPLITKI.y = PyatnashkiActivity.CAMERA_HEIGHT / 2 - WIDHTOFPLITKAANDDISTANSE * 2;
		
		int startRnd = 0;
		boolean[] rndMas = new boolean[15];
		
		Validate startPos;
				
		final Plitka[] setOfTiles = new Plitka[COUNTER*COUNTER-1];
				
		for (int i = 0; i < COUNTER*COUNTER-1; i++){				
			Random r = new Random();
			boolean b = false;
			
			while (!b){
				startRnd = r.nextInt(15);
				if (!rndMas[startRnd]){					
					rndMas[startRnd] = true;
					b = true;
				}
			}
			startPos = indexToPos(startRnd);
			
			final boolean touch = true;
			 
			setOfTiles[i] = new Plitka( startPos.x, startPos.y, PyatnashkiActivity.mYaTextureRegion, i + 1, PyatnashkiActivity.mFont){		
				/*@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
					//if (touch){
						this.setPosition(this.getX(), pSceneTouchEvent.getY() - this.getHeight() / 2);
						return true;*/
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					this.setPosition(this.getX(), pSceneTouchEvent.getY() - this.getHeight() / 2);
					return true;
						
				
						
						
					//}
					//else
				/*	if (pSceneTouchEvent.isActionDown()){
						Validate val = IsEmptyNear(setOfTiles, this.positionX, this.positionY);							
						if (val.is){							
							this.setPosition(LEFTUPPERPOINTOFPLITKI.x + val.x * WIDHTOFPLITKAANDDISTANSE, LEFTUPPERPOINTOFPLITKI.y + val.y * WIDHTOFPLITKAANDDISTANSE);
							this.positionX = val.x;
							this.positionY = val.y;
							this.renewRightPos();
							boolean win = true;
							for (int i = 0; i < COUNTER * COUNTER - 1; i++)
								if (!setOfTiles[i].isRightPos)
									win = false;
							steps++;
							String s;
							s = String.format("%01d",steps);		
							//Text number = new Text(0,0,mFont,s);
							cisla.setText(s);
							if (win)
								cisla.setText("URAAAAAAAAAAAA");
						}
						return true;
					}*/
					//return false;
				}

			};
			//setOfTiles[i].setId(i+1);
			
			//setOfTiles[i].attachChild(cisla);
			this.getLastChild().attachChild(setOfTiles[i]);
			this.registerTouchArea(setOfTiles[i]);
		}	
		
		final Sprite button = new Sprite(400, 50, PyatnashkiActivity.mAlexeyTextureRegion)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
				if(pSceneTouchEvent.isActionMove())
				this.setPosition(this.getX(), pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
				
				//setOfTiles[1].setPosition(500,300);
			/*	if(pSceneTouchEvent.isActionDown()){
				Random rnd = new Random();
				int r = rnd.nextInt(7);
				String s;
				s = String.format("%02d",r);
				cisla.setText(s);
				}
				return true;*/
				
			}

		};
		
/*		this.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				time.setText("Seconds elapsed: " + PyatnashkiActivity.mEngine.getSecondsElapsedTotal());
			}
		}));*/
		
		/*Timer t = new Timer();
		t.*/
		
		
		/*this.registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
		        @Override
		        public void onTimePassed(final TimerHandler pTimerHandler) {	        	
		        	time.setText("" + pTimerHandler.getTimerSecondsElapsed());
		        }
		}));*/
		
		this.getLastChild().attachChild(cisla);
		this.getLastChild().attachChild(time);
		
		this.getLastChild().attachChild(button);
		this.registerTouchArea(button);
		this.setTouchAreaBindingEnabled(true);
		
		Rectangle backItem = new Rectangle(50, 450, 200, 50)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowMainMenu();
				return false;
			}
		};
		
		backItem.setColor(123, 35, 154);
		attachChild(backItem);
		registerTouchArea(backItem);
	}
	
	public void Show() {
		
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {
		
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public Validate IsEmptyNear(Plitka[] set, int posx, int posy){
		boolean[][] temp = new boolean[COUNTER][COUNTER]; 
		int k=0;
		int l=0;
		for (int i = 0; i < COUNTER*COUNTER-1; i++){
			k = set[i].positionX;
			l = set[i].positionY;
			temp[k][l] = true;
		}
		for (int i = 0; i < COUNTER; i++)
			for (int j = 0; j < COUNTER; j++)
				if (!temp[i][j]){
					k = i;
					l = j;
					break;
				}
		Validate val = new Validate();				
		if (((posx-k >= -1 && posx - k <= 1) && (posy-l == 0)) || ((posx-k == 0) && (posy - l >= -1 && posy - l <= 1))){
			val.is = true;
			val.x = k;
			val.y = l;
			return val;
		}
		else{
			val.is = false;
			return val;
		}
	}
	
	public static Validate indexToPos(int ind)
	{
		Validate pos = new Validate();
		pos.x = ind % COUNTER;
		pos.y = (ind - (pos.x))/COUNTER;
		return pos;
	}	

}
