package hk.polyu.repackage;

import java.util.ArrayList;
import java.util.Arrays;

import hk.polyu.util.BashRunner;

public class SignApk {
	
	private static String KEYSTORE_PATH = "";
	private static String KEYSTORE_NAME = "";
	private static String KEYSTORE_PASSWORD = "";
	private static String KEY_PASSWORD = "";
	
	private static String AAPT_PATH = "";
	
	public static void jarsigner(String appPath){
		ArrayList<String> commands = new ArrayList<String>();
		String[] commonCmd = { "/bin/sh", "-c" };
		commands.addAll(Arrays.asList(commonCmd));
		commands.add(String.format("%s %s %s %s %s %s %s %s %s %s %s %s %s %s",
					 "jarsigner", "-verbose", "-sigalg", "SHA1withRSA", "-digestalg", "SHA1",
					 "-keystore", KEYSTORE_PATH, appPath, KEYSTORE_NAME, "-storepass", KEYSTORE_PASSWORD, "-keypass", KEY_PASSWORD));
		
		BashRunner bash = new BashRunner(commands, false);
		bash.run();
	}
	
	public static void zipalign(String srcAppPath, String tgtAppPath){
		ArrayList<String> commands = new ArrayList<String>();
		String[] commonCmd = { "/bin/sh", "-c" };
		commands.addAll(Arrays.asList(commonCmd));
		commands.add(String.format("%s %s %s %s %s",
					 AAPT_PATH + "/zipalign", "-v", "4", srcAppPath, tgtAppPath));
				
		BashRunner bash = new BashRunner(commands, false);
		bash.run();
	}

}
