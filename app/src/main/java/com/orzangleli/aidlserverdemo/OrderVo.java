package com.orzangleli.aidlserverdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>description：
 * <p>===============================
 * <p>creator：lixiancheng
 * <p>create time：2018/3/27 上午10:48
 * <p>===============================
 * <p>reasons for modification：
 * <p>Modifier：
 * <p>Modify time：
 * <p>@version
 */

public class OrderVo implements Parcelable {
    // 订单id
    private String id;
    // 商品名称
    private String name;
    // 订单金额
    private String price;
    // 折扣金额
    private String discount;

    public OrderVo(String id, String name, String price, String discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    // getter and setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.discount);
    }

    protected OrderVo(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.discount = in.readString();
    }

    public static final Creator<OrderVo> CREATOR = new Creator<OrderVo>() {
        @Override
        public OrderVo createFromParcel(Parcel source) {
            return new OrderVo(source);
        }

        @Override
        public OrderVo[] newArray(int size) {
            return new OrderVo[size];
        }
    };

}
