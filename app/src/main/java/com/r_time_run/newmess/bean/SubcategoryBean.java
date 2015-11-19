package com.r_time_run.newmess.bean;

/*
传值的类
 */
public class SubcategoryBean {
    private String mSubCategoryFoodKind;

    private int shopID;

    private int mSubCategoryFoodImageView;

    private String mSubcategoryFoodImage;

    private String mSubCategoryFoodName;

    private String mSubCategoryFoodDeatil;

    private String mSubCategoryFoodPrice;

    public SubcategoryBean(int mSubCategoryFoodImageView, String mSubCategoryFoodKind, int shopID,
                           String mSubCategoryFoodName, String mSubCategoryFoodPrice, String mSubcategoryFoodImage, String mSubCategoryFoodDeatil) {
        super();
        this.shopID=shopID;
        this.mSubCategoryFoodImageView = mSubCategoryFoodImageView;
        this.mSubCategoryFoodName = mSubCategoryFoodName;
        this.mSubCategoryFoodPrice = mSubCategoryFoodPrice;
        this.mSubcategoryFoodImage=mSubcategoryFoodImage;
        this.mSubCategoryFoodKind=mSubCategoryFoodKind;
        this.mSubCategoryFoodDeatil = mSubCategoryFoodDeatil;

    }

    public SubcategoryBean() {
        super();
    }

    public int getmSubCategoryFoodImageView() {
        return mSubCategoryFoodImageView;
    }

    public void setmSubCategoryFoodImageView(int mSubCategoryFoodImageView) {
        this.mSubCategoryFoodImageView = mSubCategoryFoodImageView;
    }

    public String getmSubCategoryFoodName() {
        return mSubCategoryFoodName;
    }

    public void setmSubCategoryFoodName(String mSubCategoryFoodName) {
        this.mSubCategoryFoodName = mSubCategoryFoodName;
    }

    public String getmSubCategoryFoodPrice() {
        return mSubCategoryFoodPrice;
    }

    public void setmSubCategoryFoodPrice(String mSubCategoryFoodPrice) {
        this.mSubCategoryFoodPrice = mSubCategoryFoodPrice;
    }

    public String getmSubCategoryFoodDeatil() {
        return mSubCategoryFoodDeatil;
    }

    public void setmSubCategoryFoodDeatil(String mSubCategoryFoodDeatil) {
        this.mSubCategoryFoodDeatil = mSubCategoryFoodDeatil;
    }

    public void setmSubcategoryFoodImage(String mSubcategoryFoodImage) {
        this.mSubcategoryFoodImage = mSubcategoryFoodImage;
    }

    public String getmSubcategoryFoodImage() {
        return mSubcategoryFoodImage;
    }

    public String mSubCategoryFoodKind() {
        return mSubCategoryFoodKind;
    }

    public void mSubCategoryFoodKind(String mSubCategoryFoodKind) {
        this.mSubCategoryFoodKind = mSubCategoryFoodKind;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}


