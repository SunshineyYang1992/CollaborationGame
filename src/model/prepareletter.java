package model;

import java.util.Random;

public class prepareletter {
	public static String[] color = { "yellow", "red", "blue", "black", "grey",
			"purple", "brown", "pink", "white", "orange", "golden" };
	public static String[] colormean = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ",
			"��ɫ", "�ۺ�ɫ", "��ɫ", "��ɫ", "��ɫ" };
	public static String[] animal = { "cat", "bull", "lion", "bird", "mouse",
			"tiger", "whale", "zebra", "rabbit", "cattle", "dophin" };
	public static String[] animalmean = { "小猫", "公牛", "狮子", "小鸟", "老鼠", "老虎",
			 "鲸鱼", "斑马", "兔子", "野牛","海豚" };
	public static String[] number = { "one", "two", "four", "five", 
		"seven", "eight", "fifty", "sixty", "three","eleven","twelve" };
	public static String[] numbermean = { "1", "2", "4", "5", "7",
			"8", "50", "60", "3","11","12" };
	public static String[] country = { "arab", "fiji", "iran", "mali", 
		"china", "norway", "japan", "india", "canada","france","greece" };
	public static String[] countrymean = { "阿拉伯", "斐济", "伊朗", "马里共和国", 
		"中国","挪威", "日本", "印度", "加拿大","法国","希腊" };
	static Random random = new Random();
	public static int what = 0;
	public static int whatclass = 0;
	public static int nandu = 0;

	public static int getWhatclass() {
		return whatclass;
	}

	public static void setWhatclass(int whatclass1) {
		whatclass = whatclass1;
	}

	public static String getonecolor() {
		if (whatclass == 0)
			return country[what];
		else if (whatclass == 1)
			return animal[what];
		else
			return number[what];
	}

	public static String getonemean() {
		if (whatclass == 0)
			return countrymean[what];
		else if (whatclass == 1)
			return animalmean[what];
		else
			return number[what];
	}

	public static void random() {
		if(nandu==1)
		{
		what = random.nextInt(4);
		}
		else if(nandu==2)
		{
			what = random.nextInt(4)+4;	
		}else
		{
			what = random.nextInt(3)+8;	
		}
	}
}
