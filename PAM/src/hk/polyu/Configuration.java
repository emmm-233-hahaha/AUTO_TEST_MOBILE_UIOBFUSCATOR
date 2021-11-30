package hk.polyu;

import org.jdom2.Namespace;

public class Configuration {
	
	public static String apkPath = "C:\\Users\\j'x'x'x'n\\Downloads\\apk\\F-Droid.apk";
	
	public static String apkDirectory = "C:\\Users\\j'x'x'x'n\\Downloads\\apk";
	public static String apkOutputDirectory = "C:\\Users\\j'x'x'x'n\\Downloads\\apk_op";
	
	public static String platformPath = "C:\\Users\\j'x'x'x'n\\AppData\\Local\\Android\\Sdk\\platforms";
	
	public static String ANDROID_NAMESPACE_PREFIX = "android";
	public static String ANDROID_NAMESPACE_URI = "http://schemas.android.com/apk/res/android";
	public static Namespace ANDROID_NAMESPACE = Namespace.getNamespace(ANDROID_NAMESPACE_PREFIX, ANDROID_NAMESPACE_URI);
	
	public static boolean useAAPT2 = false;
}
