
package com.example.inmoapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyResponseOne {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("rows")
    @Expose
    private Rows rows;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PropertyResponseOne() {
    }

    /**
     * 
     * @param count
     * @param rows
     */
    public PropertyResponseOne(Integer count, Rows rows) {
        super();
        this.count = count;
        this.rows = rows;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Rows getRows() {
        return rows;
    }

    public void setRows(Rows rows) {
        this.rows = rows;
    }

}
