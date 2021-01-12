package dev.johnnyleek.engine.util;

/**
 * A custom logger class to log messages of different verbosity levels.
 * 
 * These messages contain ANSI prefixes in order to change the color of
 * the different logging levels (there will be the ability to disable this in a future release)
 * 
 * The verbosity can be set by setting the "verbosity" variable: "Logger.verbosity = Verbosity.DEBUG"
 * 
 * Any object can be logged, but objects are best represented if they have a toString() method.
 * 
 * Objects can be logged as follows: Logger.error("error message");
 * 
 * @author Johnny Leek
 * @verion 1.0
 *
 */
public class Logger {

	public static Verbosity verbosity = Verbosity.CRITICAL;
	
	// Colours taken from: https://stackoverflow.com/a/5762502
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	/**
	 * Prints error message (prints on all verbosities)
	 * @param message: Error message to print
	 */
	public static void error(Object message) {
		System.out.println(String.format("%s[ERROR]: %s", ANSI_RED, message.toString()));
	}
	
	/**
	 * Prints info message (only prints on INFO verbosity or lower)
	 * @param message: Info message to print
	 */
	public static void info(Object message) {
		System.out.println(String.format("%s[INFO]:%s %s", ANSI_CYAN, ANSI_RESET, message.toString()));
	}
	
	/**
	 * Prints warning message (only prints on WARNING verbosity or lower)
	 * @param message: Warning message to print
	 */
	public static void warn(Object message) {
		System.out.println(String.format("%s[WARN]:%s %s", ANSI_YELLOW, ANSI_RESET, message.toString()));
	}
	
	/**
	 * Prints critical message (only prints on CRITICAL verbosity or lower)
	 * @param message: Critical message to print
	 */
	public static void critical(Object message) {
		System.out.println(String.format("%s[CRITICAL]:%s %s", ANSI_RED, ANSI_RESET, message.toString()));
	}
	
	/**
	 * Prints debug message
	 * @param message: Debug message to print
	 */
	public static void debug(Object message) {
		System.out.println(String.format("%s[DEBUG]:%s %s", ANSI_BLUE, ANSI_RESET, message.toString()));
	}
	
}
