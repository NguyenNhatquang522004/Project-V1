package com.example.my_app.modules.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.Mapper.Order.OrderMapper;
import com.example.my_app.Repository.Order.OrderProductsRepository;
import com.example.my_app.Repository.Order.OrderRepository;
import com.example.my_app.Repository.Products.ProductRepository;
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

    public OrderServices(
            OrderRepository orderRepository,
            OrderProductsRepository orderProductsRepository, Helper helper, ProductRepository productRepository,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;

        this.orderProductsRepository = orderProductsRepository;
        this.helper = helper;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public ResponedGlobal handleAddNew(RequestOrderItem request, User user) throws Exception {
        try {
            Optional<Products> searchProducts = helper.handleFindOne(productRepository, request.getProducts_id());
            if (searchProducts.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("1").messages("không tìm thấy Products").data("").build();
            }
            Optional<Products_Supports> searchSupport = helper.handleFindList(
                    searchProducts.get().getProducts_support(), Products_Supports::getId, request.getSupport_id());
            if (searchSupport.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("1").messages("không tìm thấy supports").data("").build();
            }
            Optional<Products_Support_Attribute> searchAttribute = helper.handleFindList(
                    searchSupport.get().getProducts_Supports_Products_Support_Attribute(),
                    Products_Support_Attribute::getId, request.getAttribute_id());
            if (searchAttribute.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("1").messages("không tìm thấy attribute").data("").build();
            }
            if (searchAttribute.get().getQuantity() < request.getQuantity()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("số lượng không đủ").data("").build();
            }
            Optional<Order> searchOrder = user.getUser_order().stream().filter(order -> order.isActive() == false)
                    .findFirst();
            if (searchOrder.isEmpty()) {
                Order order = new Order();
                order.setActive(false);
                Order_Products order_Products = new Order_Products();
                order_Products.setColor(searchSupport.get().getColor());
                order_Products.setQuantity(request.getQuantity());
                order_Products.setOrder_id(order);

                searchAttribute.get().getProducts_order().add(order_Products);
                order.getOrder_products().add(order_Products);
                order.setOrder_User(user);
                order.setTotalAmount(searchAttribute.get().getCostPrice() * request.getQuantity());
                orderRepository.saveAndFlush(order);
                return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
            }
            Order_Products order_Products = new Order_Products();
            order_Products.setColor(searchSupport.get().getColor());
            order_Products.setQuantity(request.getQuantity());
            order_Products.setOrder_id(searchOrder.get());
            searchAttribute.get().getProducts_order().add(order_Products);
            searchOrder.get().getOrder_products().add(order_Products);
            searchOrder.get().setOrder_User(user);
            searchOrder.get().setTotalAmount(searchAttribute.get().getCostPrice() * request.getQuantity());
            orderRepository.saveAndFlush(searchOrder.get());
            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }

    @Transactional
    public ResponedGlobal handleDeleteItem(RequestDeleteItem request, User user) throws Exception {
        try {
            Optional<Order> searchOrder = helper.handleFindList(user.getUser_order(), Order::getId,
                    request.getOrder_id());
            if (searchOrder.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy order").data("").build();
            }
            Optional<Order_Products> searchOrderProducts = helper.handleFindList(searchOrder.get().getOrder_products(),
                    Order_Products::getId,
                    request.getOrder_products_id());

            if (searchOrderProducts.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy order_products").data("").build();

            }
            searchOrderProducts.get().getAttribute_id().getProducts_order().remove(searchOrderProducts.get());

            searchOrderProducts.get().getOrder_id().getOrder_products().remove(searchOrderProducts.get());
            searchOrder.get().getOrder_User().getUser_order().remove(searchOrder.get());
            orderRepository.delete(searchOrder.get());
            return ResponedGlobal.builder().code("1").messages("không tìm thấy attribute").data("").build();

        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();

        }
    }

    public ResponedGlobal handleOrderRender(User user) throws Exception {
        try {
            List<shoppingcartResponed> lShoppingcartResponeds = new ArrayList<>();
            Optional<Order> searchOrder = user.getUser_order().stream().filter(order -> order.isActive() == false)
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
                item.setTitle(value.getAttribute_id().getProducts_Supports_id().getProducts_id().getTitle());
                item.setAttribute_id(value.getAttribute_id().getId());
                item.setSupports_id(value.getAttribute_id().getProducts_Supports_id().getProducts_id().getId());
                lShoppingcartResponeds.add(item);
            }
            return ResponedGlobal.builder().code("1").messages("không tìm thấy attribute").data(lShoppingcartResponeds)
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
            searchOrder.get()
                    .setTotalAmount(searchOrder.get().getTotalAmount() * (1 - requestMethod.getDiscount() / 100));
            searchOrder.get().setReceivedAmount(requestMethod.getReceivedAmount());
            orderRepository.save(searchOrder.get());

            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
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
            searchOrderProducts.get().setQuantity(request.getQuantity());
            int totalamount = searchOrderProducts.get().getAttribute_id().getCostPrice() * request.getQuantity();
            searchOrderProducts.get().getOrder_id().setTotalAmount(totalamount);
            orderProductsRepository.save(searchOrderProducts.get());
            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }
}
