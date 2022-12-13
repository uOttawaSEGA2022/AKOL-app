package com.example.akolapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private String recipeName;
    private String allergens;
    private String cuisineType;
    private String description;
    private String ingredients;
    private String mealType;
    private String price;
    private String id;
    private String chefName;
    public Recipe(String recipeName, String allergens, String cuisineType, String description, String ingredients, String mealType, String price,String id,String chefName) {
        this.recipeName = recipeName;
        this.allergens = allergens;
        this.cuisineType = cuisineType;
        this.description = description;
        this.ingredients = ingredients;
        this.mealType = mealType;
        this.price = price;
        this.id = id;
        this.chefName = chefName;

    }

    protected Recipe(Parcel in) {
        recipeName = in.readString();
        allergens = in.readString();
        cuisineType = in.readString();
        description = in.readString();
        ingredients = in.readString();
        mealType = in.readString();
        price = in.readString();
        id = in.readString();
        chefName = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(recipeName);
        parcel.writeString(allergens);
        parcel.writeString(cuisineType);
        parcel.writeString(description);
        parcel.writeString(ingredients);
        parcel.writeString(mealType);
        parcel.writeString(price);
        parcel.writeString(id);
        parcel.writeString(chefName);

    }
}
