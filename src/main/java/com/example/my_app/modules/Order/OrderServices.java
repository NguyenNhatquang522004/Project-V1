package com.example.my_app.modules.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.Mapper.Order.OrderMapper;
import com.example.my_app.Repository.Order.OrderProductsRepository;
import com.example.my_app.Repository.Order.OrderRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.custom.Helper.Helper;
import com.example.my_app.model.Order.Order;

import com.example.my_app.model.Order.Order_Products;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Order.Request.RequestBuyItem;
import com.example.my_app.modules.Order.Request.RequestDeleteItem;
import com.example.my_app.modules.Order.Request.RequestOrderItem;
import com.example.my_app.modules.Order.Request.RequestUpdateQuantityItem;
import com.example.my_app.modules.Order.Responed.shoppingcartResponed;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final Helper helper;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Autowired
    public OrderServices(
            OrderRepository orderRepository,
            OrderProductsRepository orderProductsRepository, Helper helper, ProductRepository productRepository,
            OrderMapper orderMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.helper = helper;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponedGlobal handleAddNew(RequestOrderItem request) throws Exception {
        try {
            Optional<User> searchUser = helper.handlefind(request.getUser_id(), userRepository::findById);
            if (searchUser.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy user").data("").build();
            }
            Optional<Products> searchProducts = helper.handleFindOne(productRepository, request.getProducts_id());
            if (searchProducts.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy Products").data("").build();
            }
            Optional<Products_Supports> searchSupport = helper.handleFindList(
                    searchProducts.get().getProducts_support(), Products_Supports::getId, request.getSupport_id());
            if (searchSupport.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy supports").data("").build();
            }
            Optional<Products_Support_Attribute> searchAttribute = helper.handleFindList(
                    searchSupport.get().getProducts_Supports_Products_Support_Attribute(),
                    Products_Support_Attribute::getId, request.getAttribute_id());
            if (searchAttribute.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy attribute").data("").build();
            }
            if (searchAttribute.get().getQuantity() < request.getQuantity()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("số lượng không đủ").data("").build();
            }
            Optional<Order> searchOrder = searchUser.get().getUser_order().stream()
                    .filter(order -> order.isActive() == false)
                    .findFirst();

            if (searchOrder.isEmpty()) {
                Order order = new Order();
                order.setActive(false);
                Order_Products order_Products = new Order_Products();
                order_Products.setColor(searchSupport.get().getColor());
                order_Products.setQuantity(request.getQuantity());
                order_Products.setOrder_id(order);
                order_Products.setPrice(searchAttribute.get().getSellingPrice());
                order_Products.setAttribute_id(searchAttribute.get());
                searchAttribute.get().getProducts_order().add(order_Products);
                order.getOrder_products().add(order_Products);
                order.setOrder_User(searchUser.get());
                order.setTotalAmount(0 + (searchAttribute.get().getSellingPrice() * request.getQuantity()));
                searchUser.get().getUser_order().add(order);
                orderRepository.save(order);
                return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
            }
            Optional<Order_Products> searchOrderProducts = searchOrder.get().getOrder_products().stream()
                    .filter(p -> p.getAttribute_id().getId().equals(request.getAttribute_id())).findFirst();
            if (searchOrderProducts.isEmpty()) {
                Order_Products order_Products = new Order_Products();
                order_Products.setColor(searchSupport.get().getColor());
                order_Products.setQuantity(request.getQuantity());
                order_Products.setOrder_id(searchOrder.get());
                order_Products.setAttribute_id(searchAttribute.get());
                order_Products.setPrice(searchAttribute.get().getSellingPrice());
                searchAttribute.get().getProducts_order().add(order_Products);
                searchOrder.get().getOrder_products().add(order_Products);
                searchOrder.get().setOrder_User(searchUser.get());
                searchOrder.get().setTotalAmount(searchOrder.get().getTotalAmount()
                        + (searchAttribute.get().getSellingPrice() * request.getQuantity()));
                searchUser.get().getUser_order().add(searchOrder.get());
                orderProductsRepository.save(order_Products);
                return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
            }
            searchOrder.get().setTotalAmount(searchOrder.get().getTotalAmount()
                    - (searchOrderProducts.get().getPrice() * searchOrderProducts.get().getQuantity()));
            searchOrderProducts.get().setQuantity(request.getQuantity());
            searchOrder.get().setTotalAmount(searchOrder.get().getTotalAmount()
                    + (searchAttribute.get().getSellingPrice() * request.getQuantity()));
            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }

    @Transactional
    public ResponedGlobal handleDeleteItem(RequestDeleteItem request) throws Exception {
        try {
            Optional<User> searchUser = userRepository.findAll().stream().findFirst();
            if (searchUser.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy user").data("").build();
            }
            Optional<Order_Products> searchOrderProducts = helper.handlefind(request.getOrder_products_id(),
                    orderProductsRepository::findById);

            if (searchOrderProducts.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy order_products").data("").build();

            }
            searchOrderProducts.get().getAttribute_id().getProducts_order().remove(searchOrderProducts.get());
            searchOrderProducts.get().getOrder_id().getOrder_products().remove(searchOrderProducts.get());
            orderProductsRepository.delete(searchOrderProducts.get());
            return ResponedGlobal.builder().code("1").messages("không tìm thấy attribute").data("").build();

        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();

        }
    }

    @Transactional
    public ResponedGlobal handleOrderRender(UUID id) throws Exception {
        try {
            Optional<User> searchUser = helper.handlefind(id, userRepository::findById);
            if (searchUser.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy user").data("").build();
            }
            List<shoppingcartResponed> lShoppingcartResponeds = new ArrayList<>();
            Optional<Order> searchOrder = searchUser.get().getUser_order().stream()
                    .filter(order -> order.isActive() == false)
                    .findFirst();
            if (searchOrder.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("lỗi").data("").build();
            }
            for (Order_Products value : searchOrder.get().getOrder_products()) {
                shoppingcartResponed item = new shoppingcartResponed();
                item.setOrder_id(value.getOrder_id().getId());
                item.setOrder_product_id(value.getId());
                item.setQuantity(value.getQuantity());
                item.setType(value.getColor());
                item.setPrice(value.getPrice());
                item.setUrl(value.getAttribute_id().getProducts_Supports_id().getUrl());
                item.setTitle(value.getAttribute_id().getProducts_Supports_id().getProducts_id().getTitle());
                item.setAttribute_id(value.getAttribute_id().getId());
                item.setSupports_id(value.getAttribute_id().getProducts_Supports_id().getProducts_id().getId());
                lShoppingcartResponeds.add(item);
            }
            return ResponedGlobal.builder().code("1").messages("").data(lShoppingcartResponeds)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }

    @Transactional
    public ResponedGlobal handleBuy(RequestBuyItem requestMethod) throws Exception {
        try {

            Optional<Order> searchOrder = helper.handlefind(requestMethod.getOrder_id(), orderRepository::findById)
                    .stream().findFirst();
            if (searchOrder.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy order ").data("").build();
            }
            if (searchOrder.get().isActive() == true) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("lỗi").data("").build();
            }

            searchOrder.get().setActive(true);
            searchOrder.get().setCity(requestMethod.getCity());
            searchOrder.get().setCountry(requestMethod.getCountry());
            searchOrder.get().setNotes(requestMethod.getNotes());
            searchOrder.get().setWard(requestMethod.getWard());
            searchOrder.get().setProvince(requestMethod.getProvince());

            if (requestMethod.getType().equals("1")) {
                searchOrder.get().setReceivedAmount(requestMethod.getReceivedAmount());
            } else {
                searchOrder.get().setReceivedAmount(0);
            }
            for (Order_Products value : searchOrder.get().getOrder_products()) {
                value.getAttribute_id().getProducts_Supports_id().getProducts_id().setTotalBUY(
                        value.getAttribute_id().getProducts_Supports_id().getProducts_id().getTotalBUY() + 1);
            }
            orderRepository.save(searchOrder.get());

            return ResponedGlobal.builder().code("1").messages("thành công").data(searchOrder.get().getId().toString()).build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }

    @Transactional
    public ResponedGlobal handleUpdateQuantity(RequestUpdateQuantityItem request) throws Exception {
        try {

            Optional<Order_Products> searchOrderProducts = helper.handleFindOne(orderProductsRepository,
                    request.getOrder_id());
            if (searchOrderProducts.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy order_products").data("").build();
            }
            searchOrderProducts.get().getOrder_id()
                    .setTotalAmount(searchOrderProducts.get().getOrder_id().getTotalAmount()
                            - (searchOrderProducts.get().getPrice() * searchOrderProducts.get().getQuantity()));
            searchOrderProducts.get().setQuantity(request.getQuantity());
            searchOrderProducts.get().getOrder_id()
                    .setTotalAmount(searchOrderProducts.get().getOrder_id().getTotalAmount()
                            + (searchOrderProducts.get().getPrice() * searchOrderProducts.get().getQuantity()));
            orderProductsRepository.save(searchOrderProducts.get());
            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }
}
