package com.iam.conllaboration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import model.CustomDialog;
import model.prepareletter;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.DelayModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.EaseBounceOut;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.KeyEvent;

public class MainActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	// 游戏主摄像头
	private Camera mCamera;
	// 消息处理handler
	private Handler mHandler;
	// android存储相关，存储设置参数
	private SharedPreferences audioOptions;
	private SharedPreferences.Editor audioEditor;
	// 母场景
	protected Scene mMenuScene;
	// 主界面场景
	protected Scene mainScene;
	// 游戏界面背景图
	private Sprite backimg1;
	// 单词纹理相关
	private Texture wordTexture;
	private TextureRegion wordTextureRegion[] = new TextureRegion[26];
	// 游戏界面中间三个按钮纹理
	private Texture gobackTexture;
	private TextureRegion gobackTextureRegion;
	private Texture playsoundTexture;
	private TextureRegion playsoundTextureRegion;
	private Texture refreshTexture;
	private TextureRegion refreshTextureRegion;
	// 主界面纹理
	private Texture mainbackTexture;
	private TextureRegion mainbackTextureRegion;
	private Texture mainbuttonTexture;
	private TextureRegion mainbutton1TextureRegion;
	private TextureRegion mainbutton2TextureRegion;
	private TextureRegion mainbutton3TextureRegion;
	// 设置界面纹理
	private Texture settingbackTexture;
	private TextureRegion settingbackTextureRegion;
	private Texture settingbuttonTexture;
	private TextureRegion settingnullTextureRegion;
	private TextureRegion settingslowTextureRegion;
	private TextureRegion settingfastTextureRegion;
	private TextureRegion settingsmallTextureRegion;
	private TextureRegion settingnormalTextureRegion;
	private TextureRegion settinglargeTextureRegion;
	private TextureRegion settingoffTextureRegion;
	private TextureRegion settingonTextureRegion;
	private TextureRegion settingexitTextureRegion;
	private Texture settingnumberTexture;
	private TextureRegion settingthreeTextureRegion;
	private TextureRegion settingfiveTextureRegion;
	private TextureRegion settingeightTextureRegion;
	private Texture maingamebackTexture;
	private TextureRegion maingamebackTextureRegion;
	private Texture gamewhereTexture;
	private TextureRegion gamewhereTextureRegion;
	// 选择词汇界面按钮纹理
	private Texture choosemainTexture;
	private TextureRegion choosecolorTextureRegion;
	private TextureRegion chooseanimalTextureRegion;
	private TextureRegion choosenumberTextureRegion;

	private Texture nanduTexture;
	private TextureRegion nandu1TextureRegion;
	private TextureRegion nandu2TextureRegion;
	private TextureRegion nandu3TextureRegion;

	private Texture countryTexture;
	private TextureRegion countryTextureRegion[] = new TextureRegion[11];
	private Texture numberTexture;
	private TextureRegion numberTextureRegion[] = new TextureRegion[11];
	private Texture animalTexture;
	private TextureRegion animalTextureRegion[] = new TextureRegion[11];
	// 主界面按钮
	private Sprite mainbutton1;
	private Sprite mainbutton2;
	private Sprite mainbutton3;
	// 随机数
	Random random = new Random();
	// 游戏界面老人和小孩精灵，和目标位置精灵
	Sprite leftobb[] = new Sprite[6];
	Sprite rightobb[] = new Sprite[6];
	Sprite leftr[] = new Sprite[6];
	Sprite rightr[] = new Sprite[6];

	// 主界面三个按钮的sprite
	Sprite goback;
	Sprite playmusic;
	Sprite refresh;
	//
	Sprite easy1;
	Sprite easy2;
	Sprite easy3;
	// 主界面背景
	Sprite mainback;
	// 游戏界面中间的线
	Line midline;
	// 游戏界面老人和小孩的行动提示
	private ChangeableText kidtext;
	private ChangeableText oldtext;
	// 本次的单词
	String precolor;
	// 小孩打乱后位置数组
	int randomarray[];
	// 大人打乱后位置数组
	int randomarray2[];
	// 纪录每一个是否已经填充
	Boolean[] isfill = new Boolean[6];
	// 游戏界面的场景
	protected Scene mStaticMenuScene;
	// 设置界面场景
	protected Scene msetupScene;
	// loading字体
	private Font mmFont;
	protected Texture mmFontTexture;

	private Font mfFont;
	protected Texture mfFontTexture;
	// 完成数字体
	private Font mscoreFont;
	protected Texture mscoreFontTexture;
	// loading界面场景
	private Scene splashScene;
	// 设置界面场景
	protected Scene mOptionsMenuScene;
	// 表示该哪个走
	private boolean who = true;
	// 纪录本组该走第几个
	private int step = 0;
	private int kidHeiW = 20;// 小孩第一个目标块距离顶端距离
	private int oldHeiW = 20;// 老人第一个目标块距离顶端距离
	private int movebottoml = 50;// 移动块距离低端距离?
	private int movetopl = 30;// 移动块距离顶端距离?
	private int kidfontsize = 80;// 小孩字体的大小
	private int oldfontsize = 80;// 老人字体的大小
	private int delaynumber = 1;// 学习时间
	// 老人和小孩动画
	private float kidan = 0;
	private float oldan = 0;
	// 游戏界面老人和小孩的完成数文本
	ChangeableText myscoretextkid;
	ChangeableText myscoretextold;
	// 游戏界面老人和小孩的翻译文本
	Sprite kidtranslation;
	ChangeableText oldtranslation;
	private int myscore = -1;
	private int iseasy = 1;
	// 声音
	Sound[] lettersound = new Sound[26];
	Sound[] colorsound = new Sound[prepareletter.color.length];
	Sound[] animalsound = new Sound[prepareletter.animal.length];
	Sound[] numbersound = new Sound[prepareletter.number.length];
	Sound passsound;
	Sound errorsound;
	// 显示设置界面音效字体
	ChangeableText soundtext;
	// 选择词库场景
	Scene choosescene;

	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// 打开应用私有存储区
		audioOptions = getSharedPreferences("audio", MODE_PRIVATE);
		audioEditor = audioOptions.edit();
		// 音效是否打开
		if (!audioOptions.contains("effectsOn")) {
			audioEditor.putBoolean("effectsOn", true);
			audioEditor.commit();
		}
		// 小孩字体大小
		if (!audioOptions.contains("kidfontsize")) {
			audioEditor.putInt("kidfontsize", 1);
			audioEditor.commit();
		}
		// 老人字体大小
		if (!audioOptions.contains("oldfontsize")) {
			audioEditor.putInt("oldfontsize", 1);
			audioEditor.commit();
		}
		// 小孩动画快慢
		if (!audioOptions.contains("kidanimation")) {
			audioEditor.putInt("kidanimation", 1);
			audioEditor.commit();
		}
		// 老人动画快慢
		if (!audioOptions.contains("oldanimation")) {
			audioEditor.putInt("oldanimation", 1);
			audioEditor.commit();
		}
		// 是否语音提示
		if (!audioOptions.contains("isvoice")) {
			audioEditor.putBoolean("isvoice", true);
			audioEditor.commit();
		}
		// 单词学习时间1,2,3分别对应3,5,8秒
		if (!audioOptions.contains("denumber")) {
			audioEditor.putInt("denumber", 1);
			audioEditor.commit();
		}
		// 难度设置1,2,3分别对应一中南秒
		if (!audioOptions.contains("easy")) {
			audioEditor.putInt("easy", 1);
			audioEditor.commit();
		}

		// TODO Auto-generated method stub
		// 返回一个engine设置为横屏，屏幕设配方案为铺满屏幕，设置有声音
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new FillResolutionPolicy(), this.mCamera).setNeedsSound(true)
				.setNeedsMusic(true));
	}

	// 初始资源载入
	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		// 载入字体资源
		this.mmFontTexture = new Texture(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mmFont = FontFactory.createFromAsset(mmFontTexture, this,
				"fonts/pod.ttf", 40, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(mmFontTexture);
		this.mEngine.getFontManager().loadFont(mmFont);
		// 预缓存loading字体
		this.mmFont.prepareLettes("loading".toCharArray());

		// 载入字体资源
		this.mfFontTexture = new Texture(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mfFont = FontFactory.createFromAsset(mfFontTexture, this,
				"fonts/pod.ttf", 40, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(mfFontTexture);
		this.mEngine.getFontManager().loadFont(mfFont);

		// 载入完成数字体资源
		this.mscoreFontTexture = new Texture(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mscoreFont = FontFactory.createFromAsset(mscoreFontTexture, this,
				"fonts/pod.ttf", 25, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(mscoreFontTexture);
		this.mEngine.getFontManager().loadFont(mscoreFont);
		// 预缓存0123456789字体
		this.mmFont.prepareLettes("0123456789".toCharArray());
	}

	@SuppressLint("NewApi")
	@Override
	// 载入场景
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mMenuScene = new Scene(1);
		initSplashScene();
		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						loadResources();
						loadScenes();
						MainActivity.this.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								splashScene.detachSelf();
							}
						});
						mMenuScene.setChildScene(mainScene);
						mEngine.setScene(mMenuScene);
					}
				}));
		return mMenuScene;
	}

	// 载入资源
	protected void loadScenes() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mainScene = new Scene(2);
		mainback = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				mainbackTextureRegion);
		// back1 = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// back1.setColor(1, 1, 1);
		mainScene.getLastChild().attachChild(mainback);
		mainbutton1 = new Sprite(270, 80, 260, 90, mainbutton1TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									creatChooseScene();
									mMenuScene.clearChildScene();
									mMenuScene.setChildScene(choosescene);
									mEngine.setScene(mMenuScene);
								}

								
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		mainbutton2 = new Sprite(270, 180, 260, 90, mainbutton2TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatsetupScene();
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(msetupScene);
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		mainbutton3 = new Sprite(270, 280, 260, 90, mainbutton3TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					showCheckDialog("exit?");
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		mainScene.registerTouchArea(mainbutton1);
		mainScene.registerTouchArea(mainbutton2);
		mainScene.registerTouchArea(mainbutton3);
		mainScene.getLastChild().attachChild(mainbutton1);
		mainScene.getLastChild().attachChild(mainbutton2);
		mainScene.getLastChild().attachChild(mainbutton3);
		// this.createStaticMenuScene();
	}

	Sprite oner1;
	Sprite oner2;
	Sprite oner3;
	Sprite otherr1;
	Sprite otherr2;
	Sprite otherr3;
	Sprite onear1;
	Sprite onear2;
	Sprite onear3;
	Sprite otherar1;
	Sprite otherar2;
	Sprite otherar3;
	Sprite soundsetting1;
	Sprite soundsetting2;
	Sprite voicesetting1;
	Sprite voicesetting2;
	Sprite three;
	Sprite five;
	Sprite eight;

	// 设置界面
	public void creatsetupScene() {
		msetupScene = new Scene(2);
		Sprite setupback = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				settingbackTextureRegion);
		msetupScene.attachChild(setupback);
		oner1 = new Sprite(220, 20, 150, 50, settingsmallTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									oner1.setAlpha(0.5f);
									oner2.setAlpha(1f);
									oner3.setAlpha(1f);
									audioEditor.putInt("kidfontsize", 1);
									// 换纹理
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		oner2 = new Sprite(420, 20, 150, 50, settingnormalTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									oner1.setAlpha(1f);
									oner2.setAlpha(0.5f);
									oner3.setAlpha(1f);
									audioEditor.putInt("kidfontsize", 2);
									// 换纹理
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		oner3 = new Sprite(600, 20, 150, 50, settinglargeTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									oner1.setAlpha(1f);
									oner2.setAlpha(1f);
									oner3.setAlpha(0.5f);
									audioEditor.putInt("kidfontsize", 3);
									// 换纹理
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};

		otherr1 = new Sprite(220, 82, 150, 50, settingsmallTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherr1.setAlpha(0.5f);
									otherr2.setAlpha(1f);
									otherr3.setAlpha(1f);
									audioEditor.putInt("oldfontsize", 1);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		otherr2 = new Sprite(420, 82, 150, 50, settingnormalTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherr1.setAlpha(1f);
									otherr2.setAlpha(0.5f);
									otherr3.setAlpha(1f);
									audioEditor.putInt("oldfontsize", 2);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		otherr3 = new Sprite(600, 82, 150, 50, settinglargeTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherr1.setAlpha(1f);
									otherr2.setAlpha(1f);
									otherr3.setAlpha(0.5f);
									audioEditor.putInt("oldfontsize", 3);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		onear1 = new Sprite(220, 144, 150, 50, settingnullTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									onear1.setAlpha(0.5f);
									onear2.setAlpha(1f);
									onear3.setAlpha(1f);
									audioEditor.putInt("kidanimation", 1);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		onear2 = new Sprite(420, 144, 150, 50, settingslowTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									onear1.setAlpha(1f);
									onear2.setAlpha(0.5f);
									onear3.setAlpha(1f);
									audioEditor.putInt("kidanimation", 2);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		onear3 = new Sprite(600, 144, 150, 50, settingfastTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									onear1.setAlpha(1f);
									onear2.setAlpha(1f);
									onear3.setAlpha(0.5f);
									audioEditor.putInt("kidanimation", 3);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		otherar1 = new Sprite(220, 208, 150, 50, settingnullTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherar1.setAlpha(0.5f);
									otherar2.setAlpha(1f);
									otherar3.setAlpha(1f);
									audioEditor.putInt("oldanimation", 1);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		otherar2 = new Sprite(420, 208, 150, 50, settingslowTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherar1.setAlpha(1f);
									otherar2.setAlpha(0.5f);
									otherar3.setAlpha(1f);
									audioEditor.putInt("oldanimation", 2);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		otherar3 = new Sprite(600, 208, 150, 50, settingfastTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO Auto-generated method stub
									otherar1.setAlpha(1f);
									otherar2.setAlpha(1f);
									otherar3.setAlpha(0.5f);
									audioEditor.putInt("oldanimation", 3);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};

		voicesetting1 = new Sprite(220, 274, 150, 50, settingonTextureRegion) {

			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									voicesetting1.setAlpha(0.5f);
									voicesetting2.setAlpha(1f);
									audioEditor.putBoolean("isvoice", true);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));

					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		voicesetting2 = new Sprite(420, 274, 150, 50, settingoffTextureRegion) {

			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									voicesetting1.setAlpha(1f);
									voicesetting2.setAlpha(0.5f);
									audioEditor.putBoolean("isvoice", false);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));

					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};

		soundsetting1 = new Sprite(220, 338, 150, 50, settingonTextureRegion) {

			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									soundsetting1.setAlpha(0.5f);
									soundsetting2.setAlpha(1f);
									audioEditor.putBoolean("effectsOn", true);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		soundsetting2 = new Sprite(420, 338, 150, 50, settingoffTextureRegion) {

			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									soundsetting1.setAlpha(1f);
									soundsetting2.setAlpha(0.5f);
									audioEditor.putBoolean("effectsOn", false);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		Sprite exit = new Sprite(600, 300, 150, 50, settingexitTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(mainScene);
					MainActivity.this.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							msetupScene.detachSelf();
						}
					});
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		three = new Sprite(220, 400, 150, 50, settingthreeTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									three.setAlpha(0.5f);
									five.setAlpha(1f);
									eight.setAlpha(1f);
									audioEditor.putInt("denumber", 1);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));

					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		five = new Sprite(420, 400, 150, 50, settingfiveTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									three.setAlpha(1f);
									five.setAlpha(0.5f);
									eight.setAlpha(1f);
									audioEditor.putInt("denumber", 2);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		eight = new Sprite(600, 400, 150, 50, settingeightTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									three.setAlpha(1f);
									five.setAlpha(1f);
									eight.setAlpha(0.5f);
									audioEditor.putInt("denumber", 3);
									audioEditor.commit();
								}
							}, new ParallelEntityModifier(new AlphaModifier(
									0.1f, 1.0f, 0.0f), new AlphaModifier(0.1f,
									0.0f, 1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		if (audioOptions.getInt("kidfontsize", 1) == 1) {
			// settingsmallTextureRegion.setTexturePosition(0, 100);
			oner1.setAlpha(0.5f);
			oner2.setAlpha(1f);
			oner3.setAlpha(1f);
		} else if (audioOptions.getInt("kidfontsize", 0) == 2) {
			// settingsmallTextureRegion.setTexturePosition(300, 100);
			oner1.setAlpha(1f);
			oner2.setAlpha(0.5f);
			oner3.setAlpha(1f);
		} else {
			// settingsmallTextureRegion.setTexturePosition(600, 100);
			oner1.setAlpha(1f);
			oner2.setAlpha(1f);
			oner3.setAlpha(0.5f);
		}
		if (audioOptions.getInt("oldfontsize", 1) == 1) {
			otherr1.setAlpha(0.5f);
			otherr2.setAlpha(1f);
			otherr3.setAlpha(1f);
		} else if (audioOptions.getInt("oldfontsize", 0) == 2) {
			otherr1.setAlpha(1f);
			otherr2.setAlpha(0.5f);
			otherr3.setAlpha(1f);
		} else {
			otherr1.setAlpha(1f);
			otherr2.setAlpha(1f);
			otherr3.setAlpha(0.5f);
		}
		if (audioOptions.getInt("kidanimation", 1) == 1) {
			onear1.setAlpha(0.5f);
			onear2.setAlpha(1f);
			onear3.setAlpha(1f);
		} else if (audioOptions.getInt("kidanimation", 0) == 2) {
			onear1.setAlpha(1f);
			onear2.setAlpha(0.5f);
			onear3.setAlpha(1f);
		} else {
			onear1.setAlpha(1f);
			onear2.setAlpha(1f);
			onear3.setAlpha(0.5f);
		}
		if (audioOptions.getInt("oldanimation", 1) == 1) {
			otherar1.setAlpha(0.5f);
			otherar2.setAlpha(1f);
			otherar3.setAlpha(1f);
		} else if (audioOptions.getInt("oldanimation", 0) == 2) {
			otherar1.setAlpha(1f);
			otherar2.setAlpha(0.5f);
			otherar3.setAlpha(1f);
		} else {
			otherar1.setAlpha(1f);
			otherar2.setAlpha(1f);
			otherar3.setAlpha(0.5f);
		}
		if (audioOptions.getBoolean("isvoice", true)) {
			voicesetting1.setAlpha(0.5f);
			voicesetting2.setAlpha(1f);
		} else {
			voicesetting1.setAlpha(1f);
			voicesetting2.setAlpha(2f);
		}
		if (audioOptions.getBoolean("effectsOn", true)) {
			soundsetting1.setAlpha(0.5f);
			soundsetting2.setAlpha(1f);
		} else {
			soundsetting1.setAlpha(1f);
			soundsetting2.setAlpha(0.5f);
		}
		if (audioOptions.getInt("denumber", 1) == 1) {
			three.setAlpha(0.5f);
			five.setAlpha(1f);
			eight.setAlpha(1f);
		} else if (audioOptions.getInt("denumber", 0) == 2) {
			three.setAlpha(1f);
			five.setAlpha(0.5f);
			eight.setAlpha(1f);
		} else {
			three.setAlpha(1f);
			five.setAlpha(1f);
			eight.setAlpha(0.5f);
		}
		msetupScene.registerTouchArea(oner1);
		msetupScene.registerTouchArea(oner2);
		msetupScene.registerTouchArea(oner3);
		msetupScene.registerTouchArea(otherr1);
		msetupScene.registerTouchArea(otherr2);
		msetupScene.registerTouchArea(otherr3);
		msetupScene.registerTouchArea(onear1);
		msetupScene.registerTouchArea(onear2);
		msetupScene.registerTouchArea(onear3);
		msetupScene.registerTouchArea(otherar1);
		msetupScene.registerTouchArea(otherar2);
		msetupScene.registerTouchArea(otherar3);
		msetupScene.registerTouchArea(exit);
		msetupScene.registerTouchArea(soundsetting1);
		msetupScene.registerTouchArea(soundsetting2);
		msetupScene.registerTouchArea(voicesetting1);
		msetupScene.registerTouchArea(voicesetting2);
		msetupScene.registerTouchArea(three);
		msetupScene.registerTouchArea(five);
		msetupScene.registerTouchArea(eight);
		msetupScene.attachChild(oner1);
		msetupScene.attachChild(oner2);
		msetupScene.attachChild(oner3);
		msetupScene.attachChild(otherr1);
		msetupScene.attachChild(otherr2);
		msetupScene.attachChild(otherr3);
		msetupScene.attachChild(soundsetting1);
		msetupScene.attachChild(soundsetting2);
		msetupScene.attachChild(onear1);
		msetupScene.attachChild(onear2);
		msetupScene.attachChild(onear3);
		msetupScene.attachChild(otherar1);
		msetupScene.attachChild(otherar2);
		msetupScene.attachChild(otherar3);
		msetupScene.attachChild(voicesetting1);
		msetupScene.attachChild(voicesetting2);
		msetupScene.attachChild(three);
		msetupScene.attachChild(five);
		msetupScene.attachChild(eight);
		msetupScene.attachChild(exit);
	}

	// 游戏界面
	public void creatGameScene(int whatclass) {
		// 类别
		if (whatclass == 0) {
			prepareletter.setWhatclass(0);
		} else if (whatclass == 1) {
			prepareletter.setWhatclass(1);
		} else {
			prepareletter.setWhatclass(2);
		}
		// 难度
		if (audioOptions.getInt("easy", 1) == 1) {
			prepareletter.nandu = 1;
		} else if (audioOptions.getInt("easy", 1) == 2) {
			prepareletter.nandu = 2;
		} else {
			prepareletter.nandu = 3;
		}
		// 根据设置的字体大小设置大小和位置,小孩字体
		if (audioOptions.getInt("kidfontsize", 1) == 1) {
			kidfontsize = 40;
		} else if (audioOptions.getInt("kidfontsize", 1) == 2) {
			kidfontsize = 50;
		} else {
			kidfontsize = 80;
		}
		// 老人字体
		if (audioOptions.getInt("oldfontsize", 1) == 1) {
			oldfontsize = 40;
		} else if (audioOptions.getInt("oldfontsize", 1) == 2) {
			oldfontsize = 50;
		} else {
			oldfontsize = 80;
		}
		// 小孩动画
		if (audioOptions.getInt("kidanimation", 1) == 1) {
			kidan = 0;
		} else if (audioOptions.getInt("kidanimation", 1) == 2) {
			kidan = 1;
		} else {
			kidan = 0.5f;
		}
		// 老人动画
		if (audioOptions.getInt("oldanimation", 1) == 1) {
			oldan = 0;
		} else if (audioOptions.getInt("oldanimation", 1) == 2) {
			oldan = 1;
		} else {
			oldan = 0.5f;
		}
		if (audioOptions.getInt("denumber", 1) == 1) {
			delaynumber = 3;
		} else if (audioOptions.getInt("denumber", 1) == 2) {
			delaynumber = 5;
		} else {
			delaynumber = 7;
		}
		myscore = 0;
		mStaticMenuScene = new Scene(2);
		backimg1 = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				maingamebackTextureRegion);
		mStaticMenuScene.attachChild(backimg1);
		midline = new Line(CAMERA_WIDTH / 2, 0, CAMERA_WIDTH / 2, CAMERA_HEIGHT);
		midline.setColor(0, 0, 0);
		kidtext = new ChangeableText(0, 0, mmFont, "等待", 6);
		oldtext = new ChangeableText(0, 0, mmFont, "行动", 6);
		kidtext.setPosition(200, (CAMERA_HEIGHT - kidtext.getHeight()) / 2);
		oldtext.setPosition(CAMERA_WIDTH - oldtext.getWidth() - 200,
				(CAMERA_HEIGHT - kidtext.getHeight()) / 2);
		kidtext.setRotation(90);
		oldtext.setRotation(-90);
		// 小孩目标区?
		for (int i = 0; i < 6; i++) {
			leftr[i] = new Sprite(0, 0, kidfontsize, kidfontsize,
					gamewhereTextureRegion);
			leftr[i].setVisible(false);
			mStaticMenuScene.attachChild(leftr[i]);
			rightr[i] = new Sprite(0, 0, oldfontsize, oldfontsize,
					gamewhereTextureRegion);
			rightr[i].setVisible(false);
			mStaticMenuScene.attachChild(rightr[i]);
		}
		for (int what = 0; what < 6; what++) {
			leftobb[what] = new Sprite(0, 0, kidfontsize, kidfontsize,
					wordTextureRegion[what]) {
				// 为卡牌绑定监听?
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					if (who == false) {
						switch (pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							// 根据该精灵的userdata(0)（存放着字母的asc码�?）播放相应字�?
							int temp = (Integer) ((ArrayList) this
									.getUserData()).get(0);
							mSoundplay(lettersound[temp], 1);
							// 动画停止
							this.clearEntityModifiers();
							this.setAlpha(1f);
							// 将除了自己以外的�?��字母隐藏
							for (int i = 0; i < precolor.length(); i++) {
								if (((ArrayList) this.getUserData()).get(0)
										.equals(((ArrayList) leftobb[i]
												.getUserData()).get(0))) {
									leftobb[i].setVisible(true);
								} else {
									// 如果第i个已经在正确的位置则不隐�?
									if (!isfill[i]) {
										leftobb[i].setVisible(false);
									}
								}
							}
							/*
							 * Toast.makeText(MainActivity.this,
							 * "card touch down", Toast.LENGTH_SHORT) .show();
							 */
							break;
						case TouchEvent.ACTION_UP:
							boolean biaoji = false;
							for (int j = 0; j < precolor.length(); j++) {
								int left1 = (int) this.getX();
								int right1 = left1 + (int) this.getWidth();
								int top1 = (int) this.getY();
								int bottom = top1 + (int) this.getHeight();
								// 判断该字母与�?��空挡是否有碰触，如果有（判读setUserData是否�?��，如果一致则正确，不�?��提示错误，作出错误处理）如果�?��的都没碰触，�?��原位
								if (iscoss(left1, right1, top1, bottom,
										(int) leftr[j].getX(),
										(int) (leftr[j].getX() + leftr[j]
												.getWidth()),
										(int) leftr[j].getY(),
										(int) (leftr[j].getY() + leftr[j]
												.getHeight()))) {
									// 如果第j个已经被填充，按错误处理
									if (isfill[j]) {
										break;
									}
									// 有触碰?
									if (((ArrayList) this.getUserData()).get(0)
											.equals(leftr[j].getUserData())) {
										/*
										 * // 正确 Toast.makeText(
										 * MainActivity.this, "正确" + this.getX()
										 * + this.getY(),
										 * Toast.LENGTH_SHORT).show();
										 */
										this.setPosition(
												(CAMERA_WIDTH) / 2
														- this.getWidth()
														- movetopl,
												j * CAMERA_HEIGHT
														/ precolor.length()
														+ kidHeiW);
										mStaticMenuScene
												.unregisterTouchArea(this);
										biaoji = true;
										// 将�?人对应的牌放到相应位�?
										rightobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.setAlpha(1);
										rightobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.clearEntityModifiers();
										rightobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.setPosition(
														(CAMERA_WIDTH) / 2
																+ movetopl,
														(precolor.length() - j - 1)
																* CAMERA_HEIGHT
																/ precolor
																		.length()
																+ oldHeiW);
										mStaticMenuScene
												.unregisterTouchArea(rightobb[(Integer) ((ArrayList) this
														.getUserData()).get(1)]);
										// 第i个被填充
										isfill[j] = true;
										kidtext.setText("等待");
										oldtext.setText("行动");
										who = true;
										if (++step >= precolor.length()) {
											mSoundplay(passsound, 0);
											clear();
											refresth(1);
										}
										break;
									} else {
										mSoundplay(errorsound, 0);
										/*
										 * // 错误 Toast.makeText(
										 * MainActivity.this, "错误" + this.getX()
										 * + this.getY(),
										 * Toast.LENGTH_SHORT).show();
										 */
									}
									break;
								}
							}
							if (biaoji == false) {
								// �?��原位
								this.setPosition(
										movebottoml,
										randomarray[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												* CAMERA_HEIGHT
												/ precolor.length() + kidHeiW);
								/*
								 * Toast.makeText( MainActivity.this,
								 * "card touch up" + this.getX() + this.getY(),
								 * Toast.LENGTH_SHORT).show();
								 */
							}
							// 将所有字母显�?
							for (int i = 0; i < precolor.length(); i++) {
								leftobb[i].setVisible(true);
							}
							break;
						case TouchEvent.ACTION_MOVE:
							if (pSceneTouchEvent.getX() < CAMERA_WIDTH / 2) {
								this.setPosition(
										pSceneTouchEvent.getX()
												- this.getWidth() / 2,
										pSceneTouchEvent.getY()
												- this.getHeight() / 2);
							}
							break;
						}
					}
					return true;
				}
			};
			rightobb[what] = new Sprite(0, 0, oldfontsize, oldfontsize,
					wordTextureRegion[what + 6]) {
				// 为卡牌绑定监�?
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					if (who == true) {
						switch (pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							// 根据该精灵的userdata(0)（存放着字母的asc码�?）播放相应字�?
							int temp = (Integer) ((ArrayList) this
									.getUserData()).get(0);
							mSoundplay(lettersound[temp], 1);
							// 动画停止
							this.clearEntityModifiers();
							this.setAlpha(1f);
							// 将除了自己以外的�?��字母隐藏
							for (int i = 0; i < precolor.length(); i++) {
								if (((ArrayList) this.getUserData()).get(0)
										.equals(((ArrayList) rightobb[i]
												.getUserData()).get(0))) {
									rightobb[i].setVisible(true);
								} else {
									// 如果第i个已经在正确的位置则不隐�?
									if (!isfill[i]) {
										rightobb[i].setVisible(false);
									}
								}
							}
							/*
							 * Toast.makeText(MainActivity.this,
							 * "card touch down", Toast.LENGTH_SHORT) .show();
							 */
							break;
						case TouchEvent.ACTION_UP:
							boolean biaoji = false;
							for (int j = 0; j < precolor.length(); j++) {
								int left1 = (int) this.getX();
								int right1 = left1 + (int) this.getWidth();
								int top1 = (int) this.getY();
								int bottom = top1 + (int) this.getHeight();
								// 判断该字母与�?��空挡是否有碰触，如果有（判读setUserData是否�?��，如果一致则正确，不�?��提示错误，作出错误处理）如果�?��的都没碰触，�?��原位
								if (iscoss(left1, right1, top1, bottom,
										(int) rightr[j].getX(),
										(int) (rightr[j].getX() + rightr[j]
												.getWidth()),
										(int) rightr[j].getY(),
										(int) (rightr[j].getY() + rightr[j]
												.getHeight()))) {
									// 如果第j个已经被填充，按错误处理
									if (isfill[j]) {
										break;
									}
									// 有触�?
									if (((ArrayList) this.getUserData()).get(0)
											.equals(rightr[j].getUserData())) {
										// 正确
										/*
										 * Toast.makeText( MainActivity.this,
										 * "正确" + this.getX() + this.getY(),
										 * Toast.LENGTH_SHORT).show();
										 */
										this.setPosition((CAMERA_WIDTH) / 2
												+ movetopl,
												(precolor.length() - 1 - j)
														* CAMERA_HEIGHT
														/ precolor.length()
														+ oldHeiW);
										mStaticMenuScene
												.unregisterTouchArea(this);
										biaoji = true;
										// 将小孩牌对应的牌放到相应位置
										leftobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.setAlpha(1);
										leftobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.clearEntityModifiers();
										leftobb[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												.setPosition(
														(CAMERA_WIDTH)
																/ 2
																- leftobb[(Integer) ((ArrayList) this
																		.getUserData())
																		.get(1)]
																		.getWidth()
																- movetopl,
														j
																* CAMERA_HEIGHT
																/ precolor
																		.length()
																+ kidHeiW);
										mStaticMenuScene
												.unregisterTouchArea(leftobb[(Integer) ((ArrayList) this
														.getUserData()).get(1)]);
										kidtext.setText("行动");
										oldtext.setText("等待");
										isfill[j] = true;
										who = false;
										if (++step >= precolor.length()) {
											mSoundplay(passsound, 0);
											clear();
											refresth(1);
										}
										break;
									} else {
										mSoundplay(errorsound, 0);
										/*
										 * // 错误 Toast.makeText(
										 * MainActivity.this, "错误" + this.getX()
										 * + this.getY(),
										 * Toast.LENGTH_SHORT).show();
										 */
									}
									break;
								}
							}
							if (biaoji == false) {
								// �?��原位
								this.setPosition(
										CAMERA_WIDTH - movebottoml
												- this.getWidth(),
										randomarray2[(Integer) ((ArrayList) this
												.getUserData()).get(1)]
												* CAMERA_HEIGHT
												/ precolor.length() + oldHeiW);
								/*
								 * Toast.makeText( MainActivity.this,
								 * "card touch up" + this.getX() + this.getY(),
								 * Toast.LENGTH_SHORT).show();
								 */
							}
							// 将所有字母显�?
							for (int i = 0; i < precolor.length(); i++) {
								rightobb[i].setVisible(true);
							}
							break;
						case TouchEvent.ACTION_MOVE:
							if (pSceneTouchEvent.getX() > CAMERA_WIDTH / 2) {
								this.setPosition(
										pSceneTouchEvent.getX()
												- this.getWidth() / 2,
										pSceneTouchEvent.getY()
												- this.getHeight() / 2);
							}
							break;
						}
					}
					return true;
				}
			};
			leftobb[what].setVisible(false);
			rightobb[what].setVisible(false);
			mStaticMenuScene.attachChild(leftobb[what]);
			mStaticMenuScene.attachChild(rightobb[what]);
		}
		refresh = new Sprite(375, 55, 50, 50, refreshTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					clear();
					refresth(0);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		playmusic = new Sprite(375, 215, 50, 50, playsoundTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if (prepareletter.getWhatclass() == 0) {
						mSoundplay(colorsound[prepareletter.what], 1);
					} else if (prepareletter.getWhatclass() == 1) {
						mSoundplay(animalsound[prepareletter.what], 1);
					} else {
						mSoundplay(numbersound[prepareletter.what], 1);
					}
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		goback = new Sprite(375, 375, 50, 50, gobackTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatChooseScene();
					mMenuScene.clearChildScene();
					mMenuScene.setChildScene(choosescene);
					MainActivity.this.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							mStaticMenuScene.detachSelf();
						}
					});
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		// 分数
		myscoretextkid = new ChangeableText(0, 0, mscoreFont, "完成数:0", 12);
		myscoretextold = new ChangeableText(0, 0, mscoreFont, "完成数:0", 12);
		myscoretextkid.setPosition(200, 100);
		myscoretextkid.setRotation(90);
		myscoretextold.setPosition(485, 360);
		myscoretextold.setRotation(-90);
		mStaticMenuScene.attachChild(myscoretextkid);
		mStaticMenuScene.attachChild(myscoretextold);
		// 翻译
		if (prepareletter.whatclass == 0) {
			kidtranslation = new Sprite(0, 0, 100, 60, countryTextureRegion[0]);
		} else if (prepareletter.whatclass == 1) {
			kidtranslation = new Sprite(0, 0, 100, 60, animalTextureRegion[0]);
		} else {
			kidtranslation = new Sprite(0, 0, 100, 60, numberTextureRegion[0]);
		}
		kidtranslation.setRotation(90);
		kidtranslation.setPosition(200, 380);
		oldtranslation = new ChangeableText(0, 0, mscoreFont, "红色", 8);
		oldtranslation.setRotation(-90);
		oldtranslation.setPosition(510, 80);

		mStaticMenuScene.registerTouchArea(refresh);
		mStaticMenuScene.registerTouchArea(playmusic);
		mStaticMenuScene.registerTouchArea(goback);
		mStaticMenuScene.attachChild(kidtext);
		mStaticMenuScene.attachChild(oldtext);
		mStaticMenuScene.setTouchAreaBindingEnabled(true);
		mStaticMenuScene.attachChild(midline);
		mStaticMenuScene.attachChild(refresh);
		mStaticMenuScene.attachChild(playmusic);
		mStaticMenuScene.attachChild(goback);
		mStaticMenuScene.attachChild(oldtranslation);
		mStaticMenuScene.attachChild(kidtranslation);
		// 刷新数据
		refresth(0);
	}

	// 纪录完成数
	public void addscore(int i) {
		myscore = myscore + i;
		final Text addscore1 = new Text(0, 0, mfFont, "+" + i,
				HorizontalAlign.CENTER);
		addscore1.setRotation(90);
		addscore1.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						addscore1.setVisible(false);
						myscoretextkid.setText("完成数:" + myscore);
						MainActivity.this.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								addscore1.detachSelf();
							}
						});
					}
				}, new ParallelEntityModifier(new MoveModifier(1f,
						(CAMERA_WIDTH / 4 - addscore1.getWidth() / 2),
						myscoretextkid.getX(), (CAMERA_HEIGHT - addscore1
								.getHeight()) / 2, myscoretextkid.getY()),
						new AlphaModifier(1f, 1, 0)), new ScaleModifier(1f, 1f,
						0)));
		final Text addscore2 = new Text(0, 0, mfFont, "+" + i,
				HorizontalAlign.CENTER);
		addscore2.setRotation(-90);
		addscore2.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						addscore2.setVisible(false);
						myscoretextold.setText("完成数:" + myscore);
						MainActivity.this.runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								addscore2.detachSelf();

							}
						});
					}
				}, new ParallelEntityModifier(new MoveModifier(1f,
						(CAMERA_WIDTH * 3 / 4 - addscore2.getWidth() / 2),
						myscoretextold.getX(), (CAMERA_HEIGHT - addscore2
								.getHeight()) / 2, myscoretextold.getY()),
						new AlphaModifier(1f, 1, 0)), new ScaleModifier(1f, 1f,
						0)));
		mStaticMenuScene.attachChild(addscore1);
		mStaticMenuScene.attachChild(addscore2);

	}

	// 每一次刷新一次
	protected void refresth(int isadd) {
		// 每次完成，完成数加一?
		if (isadd == 1) {
			addscore(1);
		}
		// TODO Auto-generated method stub
		kidtext.setText("等待");
		oldtext.setText("行动");
		who = true;
		// 个数重置
		step = 0;
		// preparezimu.color[0]当局单词
		prepareletter.random();
		precolor = prepareletter.getonecolor();
		if (prepareletter.getWhatclass() == 0) {
			mSoundplay(colorsound[prepareletter.what], 1);
		} else if (prepareletter.getWhatclass() == 1) {
			mSoundplay(animalsound[prepareletter.what], 1);
		} else {
			mSoundplay(numbersound[prepareletter.what], 1);
		}
		// 显示本次翻译
		if (prepareletter.whatclass == 0) {
			countryTextureRegion[0]
					.setTexturePosition(prepareletter.what % 5 * 100,
							(prepareletter.what / 5) * 60);
		} else if (prepareletter.whatclass == 1) {
			animalTextureRegion[0]
					.setTexturePosition(prepareletter.what % 5 * 100,
							(prepareletter.what / 5) * 60);
		} else {
			numberTextureRegion[0]
					.setTexturePosition(prepareletter.what % 5 * 100,
							(prepareletter.what / 5) * 60);
		}

		oldtranslation.setText("" + prepareletter.getonemean());
		// 根据单词个数，调整目标位�?
		setobbpostion(precolor.length());
		// 小孩目标�?
		for (int i = 0; i < precolor.length(); i++) {
			leftr[i].setVisible(true);
			leftr[i].setUserData(((int) precolor.toString().charAt(i) - 97));
			leftr[i].setPosition((CAMERA_WIDTH) / 2 - leftr[i].getWidth()
					- movetopl, i * CAMERA_HEIGHT / precolor.length() + kidHeiW);
			isfill[i] = false;
		}
		// 随机排列数组
		randomarray = new int[precolor.length()];
		randomarray2 = new int[precolor.length()];
		for (int i = 0; i < precolor.length(); i++) {
			randomarray[i] = i;
		}
		// 洗牌程序
		for (int i = 0; i < randomarray.length; i++) {
			swap(randomarray, i, random.nextInt(randomarray.length));
		}
		// 大人跟小孩相�?
		for (int i = 0; i < randomarray.length; i++) {
			randomarray2[i] = randomarray.length - randomarray[i] - 1;
		}
		// 小孩的牌
		for (int i = 0; i < precolor.length(); i++) {
			ArrayList cun = new ArrayList();
			// 字母的asc
			cun.add((int) precolor.toString().charAt(i) - 97);
			// 在本来位置?
			cun.add(i);
			// 换纹理?
			changetextureregion(false, i, (Integer) cun.get(0));
			leftobb[i].setVisible(true);
			leftobb[i].setUserData(cun);
			final int dangqian = i;
			leftobb[i].registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							mStaticMenuScene
									.registerTouchArea(leftobb[dangqian]);
							// 有动�?
							if (kidan != 0) {
								// 播放动画
								leftobb[dangqian]
										.registerEntityModifier(new LoopEntityModifier(
												new SequenceEntityModifier(
														new ParallelEntityModifier(
																new MoveXModifier(
																		kidan,
																		movebottoml,
																		200 - leftobb[dangqian]
																				.getWidth())),
														new ParallelEntityModifier(
																new MoveXModifier(
																		kidan,
																		200 - leftobb[dangqian]
																				.getWidth(),
																		movebottoml)))));
							}
						}
					}, new DelayModifier(delaynumber),
					new ParallelEntityModifier(new MoveModifier(1,
							(CAMERA_WIDTH) / 2 - leftobb[i].getWidth()
									- movetopl, movebottoml, i * CAMERA_HEIGHT
									/ precolor.length() + kidHeiW,
							randomarray[i] * CAMERA_HEIGHT / precolor.length()
									+ kidHeiW))));
			leftobb[i]
					.setPosition((CAMERA_WIDTH) / 2 - leftobb[i].getWidth()
							- movetopl, i * CAMERA_HEIGHT / precolor.length()
							+ kidHeiW);
			leftobb[i].setRotation(90);

		}
		// 老人目标�?
		for (int i = 0; i < precolor.length(); i++) {
			rightr[i].setVisible(true);
			rightr[i].setUserData(((int) precolor.toString().charAt(i) - 97));
			rightr[i].setPosition(
					(CAMERA_WIDTH) / 2 + movetopl,
					(precolor.length() - 1 - i) * CAMERA_HEIGHT
							/ precolor.length() + oldHeiW);
		}
		// 老人的牌
		for (int i = 0; i < precolor.length(); i++) {
			ArrayList cun = new ArrayList();
			// 字母的asc
			cun.add((int) precolor.toString().charAt(i) - 97);
			// 在打乱序列位�?
			cun.add(i);
			changetextureregion(true, i, (Integer) cun.get(0));

			rightobb[i].setUserData(cun);
			rightobb[i].setVisible(true);
			final int dangqian = i;
			rightobb[i].registerEntityModifier(new SequenceEntityModifier(
					new IEntityModifierListener() {
						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							mStaticMenuScene
									.registerTouchArea(rightobb[dangqian]);
							if (oldan != 0) {
								rightobb[dangqian]
										.registerEntityModifier(new LoopEntityModifier(
												new SequenceEntityModifier(
														new ParallelEntityModifier(
																new MoveXModifier(
																		oldan,
																		CAMERA_WIDTH
																				- rightobb[dangqian]
																						.getWidth()
																				- movebottoml,
																		600)),
														new ParallelEntityModifier(
																new MoveXModifier(
																		oldan,
																		600,
																		CAMERA_WIDTH
																				- rightobb[dangqian]
																						.getWidth()
																				- movebottoml)))));
							}
						}
					}, new DelayModifier(delaynumber),
					new ParallelEntityModifier(new MoveModifier(1,
							(CAMERA_WIDTH) / 2 + movetopl, CAMERA_WIDTH
									- rightobb[i].getWidth() - movebottoml,
							(precolor.length() - i - 1) * CAMERA_HEIGHT
									/ precolor.length() + oldHeiW,
							randomarray2[i] * CAMERA_HEIGHT / precolor.length()
									+ oldHeiW))));
			rightobb[i].setPosition(
					(CAMERA_WIDTH) / 2 + movetopl,
					(precolor.length() - i - 1) * CAMERA_HEIGHT
							/ precolor.length() + oldHeiW);
			rightobb[i].setRotation(-90);
		}
	}

	// 设置位置
	private void setobbpostion(int length) {
		// TODO Auto-generated method stub
		switch (length) {
		case 3:
			// 老人小字�?
			if (oldfontsize == 40) {
				oldHeiW = 60;
			}// 老人中字�?
			else if (oldfontsize == 50) {
				oldHeiW = 55;
			}// 老人大字�?
			else {
				oldHeiW = 40;
			}
			// 小孩小字�?
			if (kidfontsize == 40) {
				kidHeiW = 60;
			}// 小孩中字�?
			else if (kidfontsize == 50) {
				kidHeiW = 55;
			}// 小孩大字�?
			else {
				kidHeiW = 40;
			}
			break;
		case 4:
			// 老人小字�?
			if (oldfontsize == 40) {
				oldHeiW = 40;
			}// 老人中字�?
			else if (oldfontsize == 50) {
				oldHeiW = 35;
			}// 老人大字�?
			else {
				oldHeiW = 20;
			}
			// 小孩小字�?
			if (kidfontsize == 40) {
				kidHeiW = 40;
			}// 小孩中字�?
			else if (kidfontsize == 50) {
				kidHeiW = 35;
			}// 小孩大字�?
			else {
				kidHeiW = 20;
			}
			break;
		case 5:
			// 老人小字�?
			if (oldfontsize == 40) {
				oldHeiW = 28;
			}// 老人中字�?
			else if (oldfontsize == 50) {
				oldHeiW = 23;
			}// 老人大字�?
			else {
				oldHeiW = 8;
			}
			// 小孩小字�?
			if (kidfontsize == 40) {
				kidHeiW = 28;
			}// 小孩中字�?
			else if (kidfontsize == 50) {
				kidHeiW = 23;
			}// 小孩大字�?
			else {
				kidHeiW = 8;
			}
			break;
		case 6:
			// 老人小字�?
			if (oldfontsize == 40) {
				oldHeiW = 20;
			}// 老人中字�?
			else if (oldfontsize == 50) {
				oldHeiW = 15;
			}// 老人大字�?
			else {
				oldHeiW = 0;
			}
			// 小孩小字�?
			if (kidfontsize == 40) {
				kidHeiW = 20;
			}// 小孩中字�?
			else if (kidfontsize == 50) {
				kidHeiW = 15;
			}// 小孩大字�?
			else {
				kidHeiW = 0;
			}
			break;

		}

	}

	// 换纹理
	private void changetextureregion(boolean b, int i, Integer integer) {
		// TODO Auto-generated method stub
		// b==true为小�?i表示第几�?integer表示换成�?��
		if (b) {
			wordTextureRegion[i].setTexturePosition((integer % 8) * 128,
					(integer / 8) * 128);
		} else {
			wordTextureRegion[i + 6].setTexturePosition((integer % 8) * 128,
					(integer / 8) * 128);
		}
	}

	// 清除上一轮的单词
	protected void clear() {
		// TODO Auto-generated method stub
		for (int i = 0; i < precolor.length(); i++) {
			rightobb[i].clearEntityModifiers();
			rightobb[i].setVisible(false);
			leftobb[i].setVisible(false);
			leftobb[i].clearEntityModifiers();
			leftr[i].setVisible(false);
			rightr[i].setVisible(false);
		}
	}

	// 交换元素函数
	private void swap(int[] randomarray, int i, int nextInt) {
		// TODO Auto-generated method stub
		int temp = randomarray[i];
		randomarray[i] = randomarray[nextInt];
		randomarray[nextInt] = temp;
	}

	// 碰撞检测
	private boolean iscoss(int left1, int right1, int top1, int bottom1,
			int left2, int right2, int top2, int bottom2) {
		if (right1 < left2)
			return false;
		if (left1 >= right2)
			return false;
		if (bottom1 < top2)
			return false;
		if (top1 >= bottom2)
			return false;
		return true;
	}

	protected void gameInit() {
		// TODO Auto-generated method stub
	}

	public void initSplashScene() {
		splashScene = new Scene(2);
		Text text = new Text(0, 0, mmFont, "载入中");
		text.setPosition((CAMERA_WIDTH - text.getWidth()) / 2,
				(CAMERA_HEIGHT - text.getHeight()) / 2);
		Rectangle loadingback = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		splashScene.getLastChild().attachChild(loadingback);
		splashScene.getLastChild().attachChild(text);
		mMenuScene.setChildScene(splashScene);
	}

	public void loadResources() {
		wordTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		for (int i = 0; i <= 25; i++) {
			wordTextureRegion[i] = TextureRegionFactory.createFromAsset(
					wordTexture, this, "main/zimu_" + (i + 1) + ".png",
					(i % 8) * 128, (i / 8) * 128);
		}
		this.mEngine.getTextureManager().loadTexture(wordTexture);
		gobackTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gobackTextureRegion = TextureRegionFactory.createFromAsset(
				gobackTexture, this, "main/goback.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(gobackTexture);
		playsoundTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		playsoundTextureRegion = TextureRegionFactory.createFromAsset(
				playsoundTexture, this, "main/playsound.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(playsoundTexture);
		refreshTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		refreshTextureRegion = TextureRegionFactory.createFromAsset(
				refreshTexture, this, "main/refresh.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(refreshTexture);

		mainbackTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mainbackTextureRegion = TextureRegionFactory.createFromAsset(
				mainbackTexture, this, "main/mainback.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(mainbackTexture);
		mainbuttonTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mainbutton1TextureRegion = TextureRegionFactory.createFromAsset(
				mainbuttonTexture, this, "main/mainbutton1.png", 0, 0);
		mainbutton2TextureRegion = TextureRegionFactory.createFromAsset(
				mainbuttonTexture, this, "main/mainbutton2.png", 0, 170);
		mainbutton3TextureRegion = TextureRegionFactory.createFromAsset(
				mainbuttonTexture, this, "main/mainbutton3.png", 0, 340);
		this.mEngine.getTextureManager().loadTexture(mainbuttonTexture);

		settingbackTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		settingbackTextureRegion = TextureRegionFactory.createFromAsset(
				settingbackTexture, this, "main/settingback.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(settingbackTexture);
		settingnumberTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		settingthreeTextureRegion = TextureRegionFactory.createFromAsset(
				settingnumberTexture, this, "main/three.png", 0, 0);
		settingfiveTextureRegion = TextureRegionFactory.createFromAsset(
				settingnumberTexture, this, "main/five.png", 300, 0);
		settingeightTextureRegion = TextureRegionFactory.createFromAsset(
				settingnumberTexture, this, "main/eight.png", 600, 0);
		this.mEngine.getTextureManager().loadTexture(settingnumberTexture);
		settingbuttonTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		settingnullTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/animationnull.png", 0, 0);
		settingslowTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/animationslow.png", 300, 0);
		settingfastTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/animationfast.png", 600, 0);
		settingsmallTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/fontsmall.png", 0, 100);
		settingnormalTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/fontnormal.png", 300, 100);
		settinglargeTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/fontlarge.png", 600, 100);
		settingoffTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/off.png", 0, 200);
		settingonTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/on.png", 300, 200);
		settingexitTextureRegion = TextureRegionFactory.createFromAsset(
				settingbuttonTexture, this, "main/back.png", 600, 200);
		this.mEngine.getTextureManager().loadTexture(settingbuttonTexture);

		maingamebackTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		maingamebackTextureRegion = TextureRegionFactory.createFromAsset(
				maingamebackTexture, this, "main/maingameback.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(maingamebackTexture);

		gamewhereTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gamewhereTextureRegion = TextureRegionFactory.createFromAsset(
				gamewhereTexture, this, "main/where.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(gamewhereTexture);

		choosemainTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		choosecolorTextureRegion = TextureRegionFactory.createFromAsset(
				choosemainTexture, this, "main/choosecolor.png", 0, 0);
		chooseanimalTextureRegion = TextureRegionFactory.createFromAsset(
				choosemainTexture, this, "main/chooseanimal.png", 0, 200);
		choosenumberTextureRegion = TextureRegionFactory.createFromAsset(
				choosemainTexture, this, "main/choosenumber.png", 0, 400);
		this.mEngine.getTextureManager().loadTexture(choosemainTexture);

		nanduTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nandu1TextureRegion = TextureRegionFactory.createFromAsset(
				nanduTexture, this, "main/nandu1.png", 0, 0);
		nandu2TextureRegion = TextureRegionFactory.createFromAsset(
				nanduTexture, this, "main/nandu2.png", 0, 200);
		nandu3TextureRegion = TextureRegionFactory.createFromAsset(
				nanduTexture, this, "main/nandu3.png", 0, 400);
		this.mEngine.getTextureManager().loadTexture(nanduTexture);

		countryTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		for (int i = 0; i < countryTextureRegion.length; i++) {
			countryTextureRegion[i] = TextureRegionFactory.createFromAsset(
					countryTexture, this, "main/" + prepareletter.country[i]
							+ ".jpg", i % 5 * 100, (i / 5) * 60);
		}
		this.mEngine.getTextureManager().loadTexture(countryTexture);

		numberTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		for (int i = 0; i < numberTextureRegion.length; i++) {
			numberTextureRegion[i] = TextureRegionFactory.createFromAsset(
					numberTexture, this, "main/" + prepareletter.number[i]
							+ ".jpg", i % 5 * 100, (i / 5) * 60);
		}
		this.mEngine.getTextureManager().loadTexture(numberTexture);

		animalTexture = new Texture(1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		for (int i = 0; i < animalTextureRegion.length; i++) {
			animalTextureRegion[i] = TextureRegionFactory.createFromAsset(
					animalTexture, this, "main/" + prepareletter.animal[i]
							+ ".jpg", i % 5 * 100, (i / 5) * 60);
		}
		this.mEngine.getTextureManager().loadTexture(animalTexture);

		try {
			for (int i = 0; i < lettersound.length; i++) {
				this.lettersound[i] = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(),
						getApplicationContext(), "music/" + (char) (i + 97)
								+ ".mp3");
			}
			for (int i = 0; i < colorsound.length; i++) {
				this.colorsound[i] = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(),
						getApplicationContext(), "music/"
								+ prepareletter.country[i].toString() + ".mp3");
			}
			for (int i = 0; i < animalsound.length; i++) {
				this.animalsound[i] = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(),
						getApplicationContext(), "music/"
								+ prepareletter.animal[i].toString() + ".mp3");
			}
			for (int i = 0; i < numbersound.length; i++) {
				this.numbersound[i] = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(),
						getApplicationContext(), "music/"
								+ prepareletter.number[i].toString() + ".mp3");
			}
			this.passsound = SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), getApplicationContext(),
					"music/pass.mp3");
			this.errorsound = SoundFactory.createSoundFromAsset(
					this.mEngine.getSoundManager(), getApplicationContext(),
					"music/error.mp3");

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPauseGame() {
		// TODO Auto-generated method stub
		super.onPauseGame();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showCheckDialog("退出?");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 退出提示函数
	public void showCheckDialog(String message) {
		CustomDialog dialog;
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(
				MainActivity.this);
		customBuilder
				.setTitle("退出？")
				.setMessage(message)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
						System.exit(0);
					}
				})
				.setNegativeButton("否?", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialog = customBuilder.create();
		dialog.show();

	}

	@Override
	public void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
	}

	private void mSoundplay(Sound mSound, int i) {
		if (i == 1) {
			if (audioOptions.getBoolean("isvoice", true)) {
				mSound.play();
			}
		} else {
			if (audioOptions.getBoolean("effectsOn", true)) {
				mSound.play();
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	private void creatChooseScene() {
		// TODO Auto-generated method stub
		choosescene = new Scene(2);
		Sprite back = new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT,
				mainbackTextureRegion);
		Text backbutton = new Text(0,
				CAMERA_HEIGHT - 50, mfFont, "返回") {
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mMenuScene.clearChildScene();
					mMenuScene
							.setChildScene(mainScene);
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		Text nanduchoose = new Text(20,
				20, mfFont, "难度选择:") ;
		easy1 = new Sprite(220, 20, 120, 50,
				nandu1TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO
									// Auto-generated
									// method stub
									easy1.setAlpha(0.5f);
									easy2.setAlpha(1f);
									easy3.setAlpha(1f);
									audioEditor
											.putInt("easy",
													1);
									// 换纹理
									audioEditor
											.commit();
								}
							},
							new ParallelEntityModifier(
									new AlphaModifier(
											0.1f,
											1.0f,
											0.0f),
									new AlphaModifier(
											0.1f,
											0.0f,
											1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		easy2 = new Sprite(350, 20, 120, 50,
				nandu2TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO
									// Auto-generated
									// method stub
									easy1.setAlpha(1f);
									easy2.setAlpha(0.5f);
									easy3.setAlpha(1f);
									audioEditor
											.putInt("easy",
													2);
									// 换纹理
									audioEditor
											.commit();
								}
							},
							new ParallelEntityModifier(
									new AlphaModifier(
											0.1f,
											1.0f,
											0.0f),
									new AlphaModifier(
											0.1f,
											0.0f,
											1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		easy3 = new Sprite(480, 20, 120, 50,
				nandu3TextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.registerEntityModifier(new SequenceEntityModifier(
							new IEntityModifierListener() {
								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									// TODO
									// Auto-generated
									// method stub
									easy1.setAlpha(1f);
									easy2.setAlpha(1f);
									easy3.setAlpha(0.5f);
									audioEditor
											.putInt("easy",
													3);
									// 换纹理
									audioEditor
											.commit();
								}
							},
							new ParallelEntityModifier(
									new AlphaModifier(
											0.1f,
											1.0f,
											0.0f),
									new AlphaModifier(
											0.1f,
											0.0f,
											1.0f))));
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		if (audioOptions.getInt("easy", 1) == 1) {
			// settingsmallTextureRegion.setTexturePosition(0,
			// 100);
			easy1.setAlpha(0.5f);
			easy2.setAlpha(1f);
			easy3.setAlpha(1f);
		} else if (audioOptions.getInt("easy", 0) == 2) {
			// settingsmallTextureRegion.setTexturePosition(300,
			// 100);
			easy1.setAlpha(1f);
			easy2.setAlpha(0.5f);
			easy3.setAlpha(1f);
		} else {
			// settingsmallTextureRegion.setTexturePosition(600,
			// 100);
			easy1.setAlpha(1f);
			easy2.setAlpha(1f);
			easy3.setAlpha(0.5f);
		}
		final Sprite color = new Sprite(270, 80,
				260, 90, choosecolorTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatGameScene(0);
					mMenuScene.clearChildScene();
					mMenuScene
							.setChildScene(mStaticMenuScene);
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		final Sprite animal = new Sprite(270, 180,
				260, 90, chooseanimalTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatGameScene(1);
					mMenuScene.clearChildScene();
					mMenuScene
							.setChildScene(mStaticMenuScene);
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		final Sprite number = new Sprite(270, 280,
				260, 90, choosenumberTextureRegion) {
			@SuppressLint("NewApi")
			public boolean onAreaTouched(
					TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				switch (pSceneTouchEvent
						.getAction()) {
				case TouchEvent.ACTION_DOWN:
					creatGameScene(2);
					mMenuScene.clearChildScene();
					mMenuScene
							.setChildScene(mStaticMenuScene);
					mEngine.setScene(mMenuScene);
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			}
		};
		color.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated
						// method stub
						choosescene
								.registerTouchArea(color);
					}
				},
				new ParallelEntityModifier(
						new MoveXModifier(
								0.5f,
								-color.getWidth(),
								(CAMERA_WIDTH - color
										.getWidth()) / 2,
								EaseBounceOut
										.getInstance()))));

		animal.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated
						// method stub
						choosescene
								.registerTouchArea(animal);
					}
				},
				new ParallelEntityModifier(
						new MoveXModifier(
								0.5f,
								CAMERA_WIDTH,
								(CAMERA_WIDTH - animal
										.getWidth()) / 2,
								EaseBounceOut
										.getInstance()))));
		number.registerEntityModifier(new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated
						// method stub
						choosescene
								.registerTouchArea(number);
					}
				},
				new ParallelEntityModifier(
						new MoveXModifier(
								0.5f,
								CAMERA_WIDTH,
								(CAMERA_WIDTH - animal
										.getWidth()) / 2,
								EaseBounceOut
										.getInstance()))));
		choosescene.registerTouchArea(backbutton);
		choosescene.registerTouchArea(easy1);
		choosescene.registerTouchArea(easy2);
		choosescene.registerTouchArea(easy3);
		choosescene.attachChild(back);
		choosescene.attachChild(nanduchoose);
		choosescene.attachChild(easy1);
		choosescene.attachChild(easy2);
		choosescene.attachChild(easy3);
		choosescene.attachChild(color);
		choosescene.attachChild(animal);
		choosescene.attachChild(number);
		choosescene.attachChild(backbutton);
	}

}
