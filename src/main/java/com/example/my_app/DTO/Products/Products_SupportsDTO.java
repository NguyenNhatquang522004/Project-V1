package com.example.my_app.DTO.Products;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Order.Order_PerOrderDTO;
import com.example.my_app.DTO.Purchasing.Purchase_Transaction_DetailDTO;
import com.example.my_app.DTO.Warehouse.inventory_cardsDTO;
import com.example.my_app.DTO.Warehouse.warehouse_ProductsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Products_SupportsDTO {
  @JsonDeserialize(using = AutoDeserializer.class)
  UUID id;
  String url;
  String color;
  @JsonProperty("isActive")
  boolean isActive;
  ProductsDTO products_id;
  Set<ProductsSupportAttributeDTO> products_Supports_Products_Support_Attribute;
  Set<Purchase_Transaction_DetailDTO> products_Supports_Purchase_Transaction_Detail;
  Set<inventory_cardsDTO> products_Supports_Inventory_Cards;
  Set<warehouse_ProductsDTO> products_Supports_Warehouse_Products;
  Set<Order_PerOrderDTO> products_Supports_Order_PerOrder;

}

// Lấy id của Products (nếu không cần ánh xạ toàn bộ entity)

// Các quan hệ chuyển sang DTO tương ứng
