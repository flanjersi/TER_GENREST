package fr.amu.terGENREST.controllers.utils;

public class Utils {
	public static String makeErrorMessage(int code, String message) {
		return "{ error: " + message + ", code : " + code + "}";
	}
}
