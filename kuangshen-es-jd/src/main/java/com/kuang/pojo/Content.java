package com.kuang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.io.Serializable;

// 信息实体封装类：封装图书书名，价格等
@Data
@NoArgsConstructor
public class Content implements Serializable {
    private String name;
    private String price;
    private String img;

    //这里可继续添加属性
    public Content(String name, String price, String img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }
}
