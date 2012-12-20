package ru.overprof.pyatnashki;

import java.lang.reflect.WildcardType;
import java.util.Random;
import java.util.Timer;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.SharedPreferences.Editor;

import ru.overprof.pyatnashki.MainState.GAMESTATUS;


public class GameScene extends Scene {
	
	public static final int COUNTER = 4;
	public static final Validate LeftUpperAreaPoint = new Validate();
	public static final float Multiplexor = (float) 1.5;
	public static final int WidthPlitkaWithDistanse = (int) (PyatnashkiActivity.mYaTextureRegion.getWidth() / 2 * Multiplexor + 10);
	
	public static Boolean REALITY;
	public static Boolean HELPING;
	
	int touchedMovingPlitkaNumber = -1;
	float extremumRatio = 1 * WidthPlitkaWithDistanse / 2;
	
	int steps = 0;
	ChangeableText time;
	int seconds = 0;
	public boolean startActions_ = false;
	public boolean gamePaused_ = false;
	
	final ChangeableText counterOfSteps;
	public static final Plitka[] setOfTiles = new Plitka[COUNTER*COUNTER-1];
	Sprite restartButton_;
	Sprite winWindow_;
	
	public GameScene(int pLayerCount) {
		super(pLayerCount);		
		
		this.setBackground(new SpriteBackground(PyatnashkiActivity.mGameBackground));
		
		LeftUpperAreaPoint.x = PyatnashkiActivity.CAMERA_WIDTH / 2 - WidthPlitkaWithDistanse * 2 + 13/*?*/;
		LeftUpperAreaPoint.y = PyatnashkiActivity.CAMERA_HEIGHT / 2 - WidthPlitkaWithDistanse * 2 + 13/*?*/;

		// СЧЕТЧИКИ		
		
		Rectangle bottomPanelLeft = new Rectangle(0, PyatnashkiActivity.CAMERA_HEIGHT - MainMenuScene.bottomPanelHeight_, LeftUpperAreaPoint.x - 25, MainMenuScene.bottomPanelHeight_);
		bottomPanelLeft.setColor(0, 0, 0,(float) 0.7);
		attachChild(bottomPanelLeft);
		
		Rectangle bottomPanelRight = new Rectangle(LeftUpperAreaPoint.x + WidthPlitkaWithDistanse * 4 - 18, PyatnashkiActivity.CAMERA_HEIGHT - MainMenuScene.bottomPanelHeight_, PyatnashkiActivity.CAMERA_WIDTH - LeftUpperAreaPoint.x - WidthPlitkaWithDistanse * 4 - 18,  MainMenuScene.bottomPanelHeight_);
		bottomPanelRight.setColor(0, 0, 0,(float) 0.7);
		attachChild(bottomPanelRight);
		
		Rectangle bottomPanelCenter = new Rectangle(bottomPanelLeft.getWidth(), PyatnashkiActivity.CAMERA_HEIGHT - MainMenuScene.bottomPanelHeight_ / 2 + 5, bottomPanelRight.getX() - bottomPanelLeft.getWidth(), MainMenuScene.bottomPanelHeight_ / 2 -5 );
		bottomPanelCenter.setColor(0, 0, 0,(float) 0.7);
		attachChild(bottomPanelCenter);
		
		
		counterOfSteps = new ChangeableText(64, PyatnashkiActivity.CAMERA_HEIGHT - 50, PyatnashkiActivity.mFont, "0", 50);
		attachChild(counterOfSteps);

		time = new ChangeableText(PyatnashkiActivity.CAMERA_WIDTH - 90, PyatnashkiActivity.CAMERA_HEIGHT - 50, PyatnashkiActivity.mFont, "00:00", 50);
		
		this.registerUpdateHandler(new TimerHandler(1, true, 
				new ITimerCallback() {			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {				
				if (MainState.gameStatus_ == GAMESTATUS.GamePlayingStatus && startActions_ && !gamePaused_) {					
					
					time.setText(MainMenuScene.convertSecondsToTime(seconds));
					seconds++;
				}				
			}
		}));
		
		attachChild(time);
		setTouchAreaBindingEnabled(true);
		
		// ИГРОВОЕ ПОЛЕ
		
		initAndOrSortSetOfTiles();
		
		// РЕСТАРТ КНОПКА
		
		restartButton_ = new Sprite(PyatnashkiActivity.CAMERA_WIDTH - PyatnashkiActivity.mRestartTextureRegion.getWidth() - 32, PyatnashkiActivity.CAMERA_HEIGHT / 2 - PyatnashkiActivity.mRestartTextureRegion.getHeight() / 2, PyatnashkiActivity.mRestartTextureRegion)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					initAndOrSortSetOfTiles();
					counterOfSteps.setText("0");
					steps = 0;
					time.setText("00:00");
					seconds = 0;
					startActions_ = false;
					return true;
				} else
					return false;				
			}
		};
		
		restartButton_.setScale((float)1.5);
		attachChild(restartButton_);
		registerTouchArea(restartButton_);		
		
	}
	
	public void Show() {
		if (winWindow_ != null)
		{			
			unregisterTouchArea(winWindow_);
			detachChild(winWindow_);			
			winWindow_ = null;
			
			initAndOrSortSetOfTiles();
			counterOfSteps.setText("0");
			steps = 0;
			time.setText("00:00");
			seconds = 0;
			startActions_ = false;
			
			for (int i = 0; i < COUNTER*COUNTER-1; i++)
			{
				registerTouchArea(setOfTiles[i]);
			}
			registerTouchArea(restartButton_);
		}
		
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide() {
		
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public void initAndOrSortSetOfTiles() {
		
		boolean sort = false;
		if (setOfTiles[0] != null) sort = true;
		
		int startRnd = 0;
		boolean[] rndMas = new boolean[15];		
		Validate startPos = null;
				
		for (int i = 0; i < COUNTER*COUNTER-1; i++) {			
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
			
			if (sort) { // сортируем готовый массив
				setOfTiles[i].setPosition(LeftUpperAreaPoint.x + startPos.x * WidthPlitkaWithDistanse, LeftUpperAreaPoint.y + startPos.y * WidthPlitkaWithDistanse);
				setOfTiles[i].positionX = startPos.x;
				setOfTiles[i].positionY = startPos.y;
				setOfTiles[i].renewRightPos();
				setOfTiles[i].revalidateIt();
			} else // создаем массив с нуля		
			{ 	
				TiledTextureRegion plitkaTextureRegion = PyatnashkiActivity.mYaTextureRegion.clone();			

				setOfTiles[i] = new Plitka( startPos.x, startPos.y, plitkaTextureRegion, i + 1, PyatnashkiActivity.mFont){		
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {						
						Validate val = IsEmptyNear(setOfTiles, this.positionX, this.positionY);							
						if (val.is) {							
							if (REALITY) { // Плавное перемещение								
								if (val.x == this.positionX) { // Вертикальное смещение
									float startCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse;
									float extremumCoord;								
									float touchCoord = pSceneTouchEvent.getY() - this.getHeight() / 2;
									if (this.positionY < val.y) { // Смещение вниз
										extremumCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse + extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										
										if (pSceneTouchEvent.isActionMove()) {											
											if (touchCoord >= startCoord && touchCoord <= startCoord + WidthPlitkaWithDistanse) {
												this.setPosition(this.getX(), touchCoord);								
											}
										}										
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (touchCoord < extremumCoord) 
												this.setPosition(this.getX(), startCoord);
											else {
												this.setPosition(this.getX(), LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
												this.positionY = val.y;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}										
									} else { // Смещение вверх
										extremumCoord = LeftUpperAreaPoint.y + this.positionY * WidthPlitkaWithDistanse - extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord < startCoord && touchCoord > startCoord - WidthPlitkaWithDistanse)
												this.setPosition(this.getX(), touchCoord);
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord < startCoord && */touchCoord > extremumCoord)
												this.setPosition(this.getX(), startCoord);
											else/* if (touchCoord <= extremumCoord) */{
												this.setPosition(this.getX(), LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
												this.positionY = val.y;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
								}
								else { // Горизонтальное смещение
									float startCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse;
									float extremumCoord;								
									float touchCoord = pSceneTouchEvent.getX() - this.getWidth() / 2;
									if (this.positionX < val.x) { // Смещение вправо
										extremumCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse + extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord >= startCoord && touchCoord <= startCoord + WidthPlitkaWithDistanse)
												this.setPosition(touchCoord, this.getY());
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord > startCoord && */touchCoord < extremumCoord)
												this.setPosition(startCoord, this.getY());
											else /*if (touchCoord >= extremumCoord) */{
												this.setPosition(startCoord + WidthPlitkaWithDistanse, this.getY());
												this.positionX = val.x;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
									else { // Смещение влево
										extremumCoord = LeftUpperAreaPoint.x + this.positionX * WidthPlitkaWithDistanse - extremumRatio;
										if (pSceneTouchEvent.isActionDown()) {
											if (touchedMovingPlitkaNumber == -1)
												touchedMovingPlitkaNumber = id;
										}
										if (touchedMovingPlitkaNumber != id) {
											return false;
										}
										if (pSceneTouchEvent.isActionMove()) {
											if (touchCoord < startCoord && touchCoord > startCoord - WidthPlitkaWithDistanse)
												this.setPosition(touchCoord, this.getY());
										}
										if (pSceneTouchEvent.isActionUp()) {
											touchedMovingPlitkaNumber = -1;
											if (!startActions_)
												startActions_ = true;
											if (/*touchCoord < startCoord && */touchCoord > extremumCoord)
												this.setPosition(startCoord, this.getY());
											else /*if (touchCoord <= extremumCoord)*/ {
												this.setPosition(startCoord - WidthPlitkaWithDistanse, this.getY());
												this.positionX = val.x;
												this.renewRightPos();
												steps++;
												String s;
												s = String.format("%01d",steps);
												counterOfSteps.setText(s);
												CheckWin();
											}
										}
									}
								}

							}
							else { // Мгновенное перемещение
								if (pSceneTouchEvent.isActionDown()){													
									if (val.is){	
										if (!startActions_)
											startActions_ = true;
										this.setPosition(LeftUpperAreaPoint.x + val.x * WidthPlitkaWithDistanse, LeftUpperAreaPoint.y + val.y * WidthPlitkaWithDistanse);
										this.positionX = val.x;
										this.positionY = val.y;
										this.renewRightPos();
										steps++;
										String s;
										s = String.format("%01d",steps);
										counterOfSteps.setText(s);
										CheckWin();
									}
								}
							}	
							return true;
						}
						return false;
					}

				};
				setOfTiles[i].setScale(Multiplexor);
				attachChild(setOfTiles[i]);
				registerTouchArea(setOfTiles[i]);
			}
		}
		
		verifyTrueStartPos();		
	}
	
	private void verifyTrueStartPos() {
		int inversCount = 0;
		
		for (int i = 0; i < COUNTER * COUNTER - 1; i++)			
			for (int j = 0; j < i; j++) {
				int val1 = posToIndex(setOfTiles[j].positionX, setOfTiles[j].positionY);
				int val2 = posToIndex(setOfTiles[i].positionX, setOfTiles[i].positionY);
				if (val1 > val2)
					inversCount++;
			}
		inversCount += 4;			
		 
		if (inversCount % 2 == 0)
			return;
		else
			initAndOrSortSetOfTiles();
		
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
	
	private void CheckWin() {
		boolean win = true;
		for (int i = 0; i < COUNTER * COUNTER - 1; i++)
			if (!setOfTiles[i].isRightPos)
				win = false;	
		
		if (win) {			
			startActions_ = false;
			//counterOfSteps.setText("URAAAAAAAAAAAA");
			
			Editor editor = PyatnashkiActivity.mSettings.edit();
			if (steps < RecordsScene.stepsRecordCount || RecordsScene.stepsRecordCount == 0) {
				RecordsScene.stepsRecordCount = steps;
				editor.putInt(PyatnashkiActivity.APP_RECORDS_STEPS, steps);	
			}
			if (seconds < RecordsScene.timeRecordCount || RecordsScene.timeRecordCount == 0) {
				RecordsScene.timeRecordCount = seconds;
				editor.putInt(PyatnashkiActivity.APP_RECORDS_TIME, seconds);
			}
			editor.commit();			
			
			MainState.mainMenuScene_.UpdateRecordsControls();
			
			for (int i = 0; i < COUNTER*COUNTER-1; i++)
			{
				this.unregisterTouchArea(setOfTiles[i]);
			}
			this.unregisterTouchArea(restartButton_);
			
			winWindow_ = new Sprite(PyatnashkiActivity.CAMERA_WIDTH / 2 - PyatnashkiActivity.mWinTextureRegion.getWidth() / 2, PyatnashkiActivity.CAMERA_HEIGHT / 2 - PyatnashkiActivity.mWinTextureRegion.getHeight() / 2, PyatnashkiActivity.mWinTextureRegion)
			{
				@Override				
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
				{
					if (pSceneTouchEvent.isActionDown()) {						
						MainState.ShowMainMenu();
						return true;
					}
					else {
						return false;
					}
				}
			};						

			this.attachChild(winWindow_);
			this.registerTouchArea(winWindow_);
			
		}
	}
	
	public Validate indexToPos(int ind)
	{
		Validate pos = new Validate();
		pos.x = ind % COUNTER;
		pos.y = (ind - (pos.x))/COUNTER;
		return pos;
	}	
	
	private	int posToIndex(int x, int y)
	{	
		return (y * COUNTER + x);
	}	

}
