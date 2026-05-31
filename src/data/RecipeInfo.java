package data;

public class RecipeInfo {

    public String name;
    public String ingredients;
    public String instructions;
    public String imagePath;
    
    public RecipeInfo() {}

    public RecipeInfo(String name, String ingredients, String instructions, String imagePath) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return this.name;
    }
}