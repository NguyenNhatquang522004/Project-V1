package com.example.my_app.modules.ProductsDetail.Responed;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CartResponed {
    String url;
    String title;
    List<String> codeColor;
    int maxprice;
    int minprice;
}
