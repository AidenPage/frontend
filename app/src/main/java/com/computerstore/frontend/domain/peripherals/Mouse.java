package com.computerstore.frontend.domain.peripherals;

/**
 * Created by Aiden on 2016/10/23.
 */
public class Mouse
{
    private long _id;
    private String name;
    private String price;

    public Mouse(Builder builder) {
        this._id = builder._id;
        this.name = builder.name;
        this.price = builder.price;
    }

    public Mouse()
    {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    @Override
    public int hashCode() {
        return (int) (_id ^ (_id >>> 32));
    }

    public static class Builder
    {
        private long _id;
        private String name;
        private String price;

        public Builder id(long value)
        {
            this._id=value;
            return this;
        }

        public Builder name(String value)
        {
            this.name = value;
            return this;
        }

        public Builder price(String value)
        {
            this.price = value;
            return this;
        }

        public Builder copy(Mouse value)
        {
            this._id = value._id;
            this.name = value.name;
            this.price = value.price;
            return this;
        }

        public Mouse build()
        {
            return new Mouse(this);
        }
    }
}
