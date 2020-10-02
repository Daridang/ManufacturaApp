package lt.manufaktura.app.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
@Entity(tableName = "products")
public class Product implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int productID;

    private String name;
    private String section;
    private double price;
    private String category;
    private String description;
    private String productPicture;
    private String productPictureUrl;
    private String productImage;

    Product(int productID, String name, String section, double price, String category, String description,
            String productPicture, String productPictureUrl, String productImage) {
        this.productID = productID;
        this.name = name;
        this.section = section;
        this.price = price;
        this.category = category;
        this.description = description;
        this.productPicture = productPicture;
        this.productPictureUrl = productPictureUrl;
        this.productImage = productImage;
    }

    @Ignore
    public Product(){}

    protected Product(Parcel in) {
        productID = in.readInt();
        name = in.readString();
        section = in.readString();
        price = in.readDouble();
        category = in.readString();
        description = in.readString();
        productPicture = in.readString();
        productPictureUrl = in.readString();
        productImage = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getProductPictureUrl() {
        return productPictureUrl;
    }

    public void setProductPictureUrl(String productPictureUrl) {
        this.productPictureUrl = productPictureUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productID +
                ", name='" + name + '\'' +
                ", section='" + section + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + productPicture + '\'' +
                ", pictureUrl='" + productPictureUrl + '\'' +
                ", pictureImage='" + productImage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productID);
        dest.writeString(name);
        dest.writeString(section);
        dest.writeDouble(price);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(productPicture);
        dest.writeString(productPictureUrl);
        dest.writeString(productImage);
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
