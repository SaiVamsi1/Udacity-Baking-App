package com.vamsi.bakingappvamsi.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IngredientModel implements Serializable {

    @SerializedName("ingredient")
    @Expose
    private String Ingredient;
    @SerializedName("quantity")
    @Expose
    private double Quantity;
    @SerializedName("measure")
    @Expose
    private String Measure;

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }
}
