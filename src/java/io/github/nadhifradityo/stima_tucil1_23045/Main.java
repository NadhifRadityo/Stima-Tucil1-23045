package io.github.nadhifradityo.stima_tucil1_23045;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

	protected static boolean isAwtHeadless() {
		try {
			Class<?> klass = Class.forName("java.awt.GraphicsEnvironment");
			Method method = klass.getDeclaredMethod("isHeadless");
			return (Boolean) method.invoke(null);
		} catch(Exception ignore) {
			return true;
		}
	}

	public static void main(String... args) throws Exception {
		var headless = isAwtHeadless();
		var preferGui = args.length > 0 && args[0].equals("gui");
		var preferCli = args.length > 0 && args[0].equals("cli");
		var modifiedArgs = preferGui || preferCli ? Arrays.copyOfRange(args, 1, args.length) : args;
		if(preferGui && headless) {
			System.err.println("Cannot run GUI mode in headless environment");
			System.exit(1);
			return;
		}
		if(preferGui || (args.length == 0 && !headless))
			AppGUI.main(modifiedArgs);
		else
			AppCLI.main(modifiedArgs);
	}
}
