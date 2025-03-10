package com.example.my_app.Modules_Admin.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.Order.OrderDTO;
import com.example.my_app.Enum.StatusOrderStatusHistory;
import com.example.my_app.Mapper.Order.OrderMapper;
import com.example.my_app.Modules_Admin.Order.Request.RequestBuyItem;
import com.example.my_app.Modules_Admin.Order.Request.RequestDeleteItem;
import com.example.my_app.Modules_Admin.Order.Request.RequestOrderItem;
import com.example.my_app.Modules_Admin.Order.Request.RequestUpdateQuantityItem;
import com.example.my_app.Repository.Helper.Helper;
import com.example.my_app.Repository.Order.OrderBillRepository;
import com.example.my_app.Repository.Order.OrderPaymentRepository;
import com.example.my_app.Repository.Order.OrderPreRepository;
import com.example.my_app.Repository.Order.OrderProductsRepository;
import com.example.my_app.Repository.Order.OrderRepository;
import com.example.my_app.Repository.Order.OrderStatusHistoryRepository;
import com.example.my_app.Repository.Order.OrderWaybillRepository;
import com.example.my_app.Repository.Products.ProductRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.Order.Order;
import com.example.my_app.model.Order.OrderStatusHistory;
import com.example.my_app.model.Order.Order_Bill;
import com.example.my_app.model.Order.Order_Payment;
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
    private final OrderMapper orderMapper;

    public OrderServices(OrderBillRepository orderBillRepository,
            OrderWaybillRepository orderWaybillRepository,
            OrderRepository orderRepository,
            OrderPreRepository orderPreRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderPaymentRepository orderPaymentRepository,
            OrderProductsRepository orderProductsRepository, Helper helper, ProductRepository productRepository,
            OrderMapper orderMapper) {
        this.orderBillRepository = orderBillRepository;
        this.orderWaybillRepository = orderWaybillRepository;
        this.orderRepository = orderRepository;
        this.orderPreRepository = orderPreRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.helper = helper;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public ResponedGlobal handleAddNew(RequestOrderItem request, User User) throws Exception {
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
            Order order = new Order();
            Order_Products order_Products = new Order_Products();
            order_Products.setColor(searchSupport.get().getColor());
            order_Products.setQuantity(request.getQuantity());
            searchProducts.get().getProducts_order().add(order_Products);
            searchSupport.get().getProducts_order().add(order_Products);
            searchAttribute.get().getProducts_order().add(order_Products);
            order.getOrder_products().add(order_Products);
            order.setOrder_User(User);
            order.setTotalAmount(searchAttribute.get().getCostPrice() * request.getQuantity());
            orderRepository.saveAndFlush(order);
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
            searchOrderProducts.get().getProducts_id().getProducts_order().remove(searchOrderProducts.get());
            searchOrderProducts.get().getSupports_id().getProducts_order().remove(searchOrderProducts.get());
            searchOrderProducts.get().getOrder_id().getOrder_products().remove(searchOrderProducts.get());
            searchOrder.get().getOrder_Payment_id().getOrder_payment().remove(searchOrder.get());
            searchOrder.get().getOrder_User().getUser_order().remove(searchOrder.get());
            orderRepository.delete(searchOrder.get());
            return ResponedGlobal.builder().code("1").messages("không tìm thấy attribute").data("").build();

        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();

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

    @Transactional
    public ResponedGlobal handleBuy(List<OrderDTO> request, User user, RequestBuyItem requestMethod) throws Exception {
        try {
            Optional<Order_Payment> searchPayment = orderPaymentRepository
                    .findByStatusPaymentMethod(requestMethod.getPaymentMethod());
            if (searchPayment.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().code("0").messages("không tìm thấy phương thức thanh toán").data("")
                        .build();
            }
            Order_Bill order_Bill = new Order_Bill();
            user.getUser_order().forEach(val -> {
                Optional<OrderDTO> match = request.stream().filter(p -> p.getId().equals(val.getId())).findFirst();
                Order_Products searchOrderProducts = val.getOrder_products().iterator().next();
                val.setOrder_Payment_id(searchPayment.get());
                if (match.isPresent()) {
                    orderMapper.UpdateEntity(val, match.get());
                    val.setOrders_bill(order_Bill);
                    order_Bill.setTime(LocalDateTime.now());
                    order_Bill.setDiscount(requestMethod.getDiscount());
                    order_Bill.setProfitCode(requestMethod.getProfitCode());
                    int discountAmount = (searchOrderProducts.getOrder_id().getTotalAmount()
                            * requestMethod.getDiscount()) / 100;
                    int finalAmount = searchOrderProducts.getOrder_id().getTotalAmount() - discountAmount;
                    int finalAmount1 = order_Bill.getTotalAmount() + finalAmount;
                    order_Bill.setTotalAmount(finalAmount1);
                    order_Bill.setReceivedAmount(requestMethod.getReceivedAmount());
                }
            });
            OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
            orderStatusHistory.setOrder_id(order_Bill);
            orderStatusHistory.setUser_id(user);
            orderStatusHistory.setStatus(StatusOrderStatusHistory.bill);
            user.getUser_orderHistory().add(orderStatusHistory);
            orderBillRepository.saveAndFlush(order_Bill);
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

    public ResponedGlobal handleOrderWayBill() throws Exception {
        try {

            return ResponedGlobal.builder().code("1").messages("thành công").data("").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().code("0").messages("lỗi").data(e).build();
        }
    }
}
