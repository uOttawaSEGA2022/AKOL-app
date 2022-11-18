package com.example.akolapp;

public class Recipe {
    private String recipeName;
    private String allergens;
    private String cuisineType;
    private String description;
    private String ingredients;
    private String mealType;
    private String price;
    public Recipe(String recipeName, String allergens, String cuisineType, String description, String ingredients, String mealType, String price) {
        this.recipeName = recipeName;
        this.allergens = allergens;
        this.cuisineType = cuisineType;
        this.description = description;
        this.ingredients = ingredients;
        this.mealType = mealType;
        this.price = price;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
