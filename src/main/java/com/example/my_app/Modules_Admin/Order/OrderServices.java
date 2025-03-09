package com.example.my_app.Modules_Admin.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.Modules_Admin.Order.Request.RequestDeleteItem;
import com.example.my_app.Modules_Admin.Order.Request.RequestOrderItem;
import com.example.my_app.Repository.Helper.Helper;
import com.example.my_app.Repository.Order.OrderBillRepository;
import com.example.my_app.Repository.Order.OrderPaymentRepository;
import com.example.my_app.Repository.Order.OrderPreRepository;
import com.example.my_app.Repository.Order.OrderProductsRepository;
import com.example.my_app.Repository.Order.OrderRepository;
import com.example.my_app.Repository.Order.OrderStatusHistoryRepository;
import com.example.my_app.Repository.Order.OrderWaybillRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.model.Order.Order;
import com.example.my_app.model.Order.Order_Products;
import com.example.my_app.model.Product.Products;
import com.example.my_app.model.Product.Products_Support_Attribute;
import com.example.my_app.model.Product.Products_Supports;
import com.example.my_app.model.User.User;

@Service
public class OrderServices {

    private final OrderBillRepository orderBillRepository;
    private final OrderWaybillRepository orderWaybillRepository;
    private final OrderRepository orderRepository;
    private final OrderPreRepository orderPreRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final Helper helper;
    private final ProductRepository productRepository;

    public OrderServices(OrderBillRepository orderBillRepository,
            OrderWaybillRepository orderWaybillRepository,
            OrderRepository orderRepository,
            OrderPreRepository orderPreRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderPaymentRepository orderPaymentRepository,
            OrderProductsRepository orderProductsRepository, Helper helper, ProductRepository productRepository) {
        this.orderBillRepository = orderBillRepository;
        this.orderWaybillRepository = orderWaybillRepository;
        this.orderRepository = orderRepository;
        this.orderPreRepository = orderPreRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.helper = helper;
        this.productRepository = productRepository;
    }

    public boolean handleAddNew(RequestOrderItem request, User User) throws Exception {
        try {
            Optional<Products> searchProducts = helper.handleFindOne(productRepository, request.getProducts_id());
            if (searchProducts.isEmpty()) {
                return false;
            }
            Optional<Products_Supports> searchSupport = helper.handleFindList(
                    searchProducts.get().getProducts_support(), Products_Supports::getId, request.getSupport_id());
            if (searchSupport.isEmpty()) {
                return false;
            }
            Optional<Products_Support_Attribute> searchAttribute = helper.handleFindList(
                    searchSupport.get().getProducts_Supports_Products_Support_Attribute(),
                    Products_Support_Attribute::getId, request.getAttribute_id());
            if (searchSupport.isEmpty()) {
                return false;
            }
            if (searchAttribute.get().getQuantity() < request.getQuantity()) {
                return false;
            }
            Order order = new Order();
            Order_Products order_Products = new Order_Products();
            order_Products.setColor(searchSupport.get().getColor());
            order_Products.setQuantity(request.getQuantity());
            searchProducts.get().getProducts_order().add(order_Products);
            searchSupport.get().getProducts_order().add(order_Products);
            searchAttribute.get().getProducts_order().add(order_Products);
            order.getOrder_products().add(order_Products);
            order.setOrder_User(User);
            orderRepository.saveAndFlush(order);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return true;
        }
    }

    public boolean handleDeleteItem(RequestDeleteItem request, User user) throws Exception {
        try {
            Optional<Order> searchOrder = helper.handleFindList(user.getUser_order(), Order::getId,
                    request.getOrder_id());
            if (searchOrder.isEmpty()) {
                return false;
            }
            Optional<Order_Products> searchOrderProducts = helper.handleFindList(searchOrder.get().getOrder_products(),
                    Order_Products::getId,
                    request.getOrder_products_id());

            if (searchOrderProducts.isEmpty()) {
                return false;

            }

            searchOrderProducts.get().getAttribute_id().getProducts_order().remove(searchOrderProducts);
            searchOrderProducts.get().getProducts_id().getProducts_order().remove(searchOrderProducts);
            searchOrderProducts.get().getSupports_id().getProducts_order().remove(searchOrderProducts);
            searchOrderProducts.get().getOrder_id().getOrder_products().remove(searchOrderProducts);
            searchOrder.get().getOrder_Payment_id().getOrder_payment().remove(searchOrder);
            searchOrder.get().getOrder_User().getUser_order().remove(searchOrder);
            orderRepository.delete(searchOrder.get());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean handleOrderRender(User user) throws Exception {
        try {

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean handleBuy(List<OrderDTO> request, User user) throws Exception {
        try {

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
