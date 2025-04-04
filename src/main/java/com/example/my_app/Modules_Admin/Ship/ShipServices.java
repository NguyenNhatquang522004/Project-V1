package com.example.my_app.Modules_Admin.Ship;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.my_app.DTO.ship.ShipStatusHistoryDTO;
import com.example.my_app.DTO.ship.ShipmentDTO;
import com.example.my_app.DTO.ship.ShipmentInfoDTO;
import com.example.my_app.Enum.StatusPaymentMethod;
import com.example.my_app.Mapper.Ship.ShipStatusHistoryMapper;
import com.example.my_app.Mapper.Ship.ShipmentInfoMapper;
import com.example.my_app.Mapper.Ship.ShipmentMapper;
import com.example.my_app.Repository.Helper.Helper;
import com.example.my_app.Repository.Ship.ShipStatusHistoryRepository;
import com.example.my_app.Repository.Ship.ShipmentInfoRepository;
import com.example.my_app.Repository.Ship.ShipmentRepository;
import com.example.my_app.common.ResponedGlobal;
import com.example.my_app.model.ship.ShipStatusHistory;
import com.example.my_app.model.ship.Shipment;
import com.example.my_app.model.ship.ShipmentInfo;
import jakarta.transaction.Transactional;
@Service
public class ShipServices {
    private final ShipmentInfoRepository shipment_InfoRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipStatusHistoryRepository ship_StatusHistoryRepository;
    private final ShipmentInfoMapper shipmentInfoMapper;
    private final ShipmentMapper shipmentMapper;
    private final ShipStatusHistoryMapper shipStatusHistoryMapper;
    private final Helper helper;
    public ShipServices(ShipmentInfoRepository shipment_InfoRepository,
            ShipmentRepository shipmentRepository,
            ShipStatusHistoryRepository ship_StatusHistoryRepository, ShipmentInfoMapper shipmentInfoMapper,
            ShipmentMapper shipmentMapper, ShipStatusHistoryMapper shipStatusHistoryMapper, Helper helper) {
        this.shipment_InfoRepository = shipment_InfoRepository;
        this.shipmentRepository = shipmentRepository;
        this.ship_StatusHistoryRepository = ship_StatusHistoryRepository;
        this.shipmentMapper = shipmentMapper;
        this.shipStatusHistoryMapper = shipStatusHistoryMapper;
        this.shipmentInfoMapper = shipmentInfoMapper;
        this.helper = helper;
    }
    @Transactional
    public ResponedGlobal handleAddShipment(ShipmentDTO request, UUID employee_id) throws Exception {
        try {
            Shipment shipment = shipmentMapper.toEntity(request);
            shipmentRepository.save(shipment);
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }
    @Transactional
    public ResponedGlobal handleDeleteShipment(UUID shipment_id) throws Exception {
        try {
            shipmentRepository.deleteById(shipment_id);
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }
    @Transactional
    public ResponedGlobal handleFindOneShipmentInfo(UUID id) throws Exception {
        try {
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }
    @Transactional
    public ResponedGlobal handleAddShipmentInfo(ShipmentInfoDTO request) throws Exception {
        try {
            Optional<Shipment> searcShipment = helper.handleFindOne(shipmentRepository,
                    request.getShipment_id().getId());

            if (searcShipment.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").messages("không tìm thấy shipment ").code("0").build();
            }

            ShipmentInfo shipmentInfo = shipmentInfoMapper.toEntity(request);
            shipmentInfo.setShipment_id(searcShipment.get());
            searcShipment.get().setShipment_Info(shipmentInfo);
            shipment_InfoRepository.saveAndFlush(shipmentInfo);
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }
    @Transactional
    public ResponedGlobal handleUpdateShipmentInfo(ShipmentInfoDTO request, ShipStatusHistoryDTO requestUpdate)
            throws Exception {
        try {
            Optional<ShipmentInfo> searcShipmentInfo = helper.handleFindOne(shipment_InfoRepository,
                    request.getShipment_id().getId());
            if (searcShipmentInfo.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").messages("không tìm thấy shipment ").code("0").build();
            }
            if (!searcShipmentInfo.get().getId().toString().equals(requestUpdate.getId().toString())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").messages("không tìm thấy shipment ").code("0").build();
            }
            Optional<ShipStatusHistory> searcHistory = helper.handleFindOne(ship_StatusHistoryRepository,
                    requestUpdate.getId());
            if (searcHistory.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponedGlobal.builder().data("").messages("không tìm thấy shipment ").code("0").build();
            }
            searcShipmentInfo.get().setTotalOrders(searcShipmentInfo.get().getTotalOrders() + 1);
            searcHistory.get().getShipStatusHistory_WayBill().getOrder_Order_Bill().getOrders_Bill_Order()
                    .forEach(p -> {
                        if (p.getOrder_Payment_id().getStatusPaymentMethod() == StatusPaymentMethod.CASH) {
                            searcShipmentInfo.get()
                                    .setCodAmount(searcShipmentInfo.get().getCodAmount() + p.getTotalAmount());
                        }
                    });
            searcShipmentInfo.get().setTotalShippingFee(
                    searcShipmentInfo.get().getTotalShippingFee() + requestUpdate.getShippingFee());

            searcShipmentInfo.get().setOutstandingCod(
                    searcShipmentInfo.get().getCodAmount() - searcShipmentInfo.get().getCodSuccess());
            searcShipmentInfo.get().setOutstandingShippingFee(
                    searcShipmentInfo.get().getOutstandingShippingFee() + request.getOutstandingShippingFee());
            shipment_InfoRepository.saveAndFlush(searcShipmentInfo.get());
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }

    @Transactional
    public ResponedGlobal handleAddShipHistory(ShipStatusHistoryDTO request) throws Exception {
        try {
            
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }

    @Transactional
    public ResponedGlobal handleUpdateShipHistory() throws Exception {
        try {
            return ResponedGlobal.builder().data("").messages("thành công ").code("1").build();
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponedGlobal.builder().data(e).messages("lỗi ").code("0").build();
        }
    }
}
