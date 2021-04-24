package com.example.mygym;

public class menuItem
{
    private int menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private String menuItemType;
    private String menuItemImage;
    private int menuItemFats;
    private int menuItemProtein;
    private int menuItemCalories;
    private int menuItemCarbohydrates;
    private int menuItemSugar;
    private double menuItemPrice;
    private int menuPreparation;
    private int menuItemAvailable;
    private int selected = 0;

    public menuItem(int menuItemId, String menuItemName, String menuItemDescription, String menuItemType,
                    String menuItemImage, int menuItemFats, int menuItemProtein, int menuItemCarbohydrates,
                    int menuItemSugar, double menuItemPrice, int menuItemCalories,
                    int menuPreparation, int menuItemAvailable) {
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemDescription = menuItemDescription;
        this.menuItemType = menuItemType;
        this.menuItemImage = menuItemImage;
        this.menuItemFats = menuItemFats;
        this.menuItemProtein = menuItemProtein;
        this.menuItemCarbohydrates = menuItemCarbohydrates;
        this.menuItemSugar = menuItemSugar;
        this.menuItemPrice = menuItemPrice;
        this.menuItemCalories = menuItemCalories;
        this.menuPreparation = menuPreparation;
        this.menuItemAvailable = menuItemAvailable;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public void setMenuItemDescription(String menuItemDescription) {
        this.menuItemDescription = menuItemDescription;
    }

    public String getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(String menuItemType) {
        this.menuItemType = menuItemType;
    }

    public String getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(String menuItemImage) {
        this.menuItemImage = menuItemImage;
    }

    public int getMenuItemFats() {
        return menuItemFats;
    }

    public void setMenuItemFats(int menuItemFats) {
        this.menuItemFats = menuItemFats;
    }

    public int getMenuItemProtein() {
        return menuItemProtein;
    }

    public void setMenuItemProtein(int menuItemProtein) {
        this.menuItemProtein = menuItemProtein;
    }

    public int getMenuItemCarbohydrates() {
        return menuItemCarbohydrates;
    }

    public void setMenuItemCarbohydrates(int menuItemCarbohydrates) {
        this.menuItemCarbohydrates = menuItemCarbohydrates;
    }

    public int getMenuItemSugar() {
        return menuItemSugar;
    }

    public void setMenuItemSugar(int menuItemSugar) {
        this.menuItemSugar = menuItemSugar;
    }

    public double getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(int menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public int getMenuItemAvailable() {
        return menuItemAvailable;
    }

    public void setMenuItemAvailable(int menuItemAvailable) {
        this.menuItemAvailable = menuItemAvailable;
    }

    public int getMenuItemCalories() {
        return menuItemCalories;
    }

    public void setMenuItemCalories(int menuItemCalories) {
        this.menuItemCalories = menuItemCalories;
    }

    public int getMenuPreparation() {
        return menuPreparation;
    }

    public void setMenuPreparation(int menuPreparation) {
        this.menuPreparation = menuPreparation;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}