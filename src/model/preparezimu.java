package model;

import java.util.Random;

public class  preparezimu {
	public static String[] color={"yellow","red","blue","black"};
	public static String[] mean={"黄色","红色","蓝色","黑色"};
	static Random random=new Random();
	public static int what=0;
	public static String getonecolor(){
		return color[what];
	}
	public static String getonemean(){
		return color[what];
	}
	public static void random(){
		what=random.nextInt(color.length);
	}
}
