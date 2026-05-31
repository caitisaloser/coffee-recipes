package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppData {
	
	public static final String APP_DIR = getAppDirectory();
	
	private static final String FILE_PATH = APP_DIR + File.separator + "recipes.json";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public ArrayList<RecipeInfo> recipes;
	
	public AppData() {
		recipes = loadRecipes();
	}
	
	private static String getAppDirectory() {
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");
		String appName = "Cait Hope Apps" + File.separator + "Coffee Recipes";
		
		if (os.contains("win")) {
			return System.getenv("APPDATA") + File.separator + appName;
		} else if (os.contains("mac")) {
			return userHome + File.separator + "Library" + File.separator + "Application Support" + File.separator + appName;
		} else {
			return userHome + File.separator + "." + appName;
		}
	}
	
	public ArrayList<RecipeInfo> loadRecipes() {		
		File file = new File(FILE_PATH);
		
		if (!file.exists()) {
			return new ArrayList<>();
		}

		try (Reader reader = new FileReader(FILE_PATH)) {
			Type listType = new TypeToken<ArrayList<RecipeInfo>>(){}.getType();
			ArrayList<RecipeInfo> data = gson.fromJson(reader, listType);
			
			return data == null ? new ArrayList<>() : data;
			
		} catch (Exception e) {
			System.err.println("Error loading data: " + e.getMessage());
			return new ArrayList<>();
		}
	}
	
	public static void saveRecipes(ArrayList<RecipeInfo> recipes) {
		File directory = new File(FILE_PATH).getParentFile();
		
		if (directory != null && !directory.exists()) {
			directory.mkdirs(); 
		}

		try (Writer writer = new FileWriter(FILE_PATH)) {
			gson.toJson(recipes, writer);
		} catch (IOException e) {
			System.err.println("Error saving data: " + e.getMessage());
		}
	}
}