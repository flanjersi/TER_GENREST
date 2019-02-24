package fr.amu.ter_genrest.controllers.utils;

import javax.json.Json;

public class Utils {
	public static String makeErrorMessage(int code, String message) {
		return Json.createObjectBuilder()
				.add("error", message)
				.add("code", code)
				.build()
				.toString();
	}
}
