package com.shop.vegetable.service.impl;


import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.vegetable.entity.CartItem;
import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderDetail;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.repository.OrderDetailRepository;
import com.shop.vegetable.repository.OrderRepository;
import com.shop.vegetable.repository.UsersRepository;
import com.shop.vegetable.service.OrderService;
import com.shop.vegetable.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final UsersRepository usersRepository;
    private final ShoppingCartService cartService;

    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUsers(shoppingCart.getUsers());
        order.setTax(2);
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");
        order.setQuantity(shoppingCart.getTotalItems());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            detailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        cartService.deleteCartById(shoppingCart.getId());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll(String username) {
        Users Users = usersRepository.findByUsername(username);
        List<Order> orders = Users.getOrders();
        return orders;
    }

    @Override
    public List<Order> findALlOrders() {
        return orderRepository.findAll();
    }


    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> AllOrderDetail(Long id) {
        return detailRepository.findAllByOrderId(id);
    }

    @Override
    public void exportToExcel(List<Order> objectList, HttpServletResponse response) throws IOException {
        // Tạo một workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một sheet trong workbook
        Sheet sheet = workbook.createSheet("Object Data");

        // Tạo hàng đầu tiên (header) trong sheet
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Customer");
        headerRow.createCell(1).setCellValue("Date Order");
        headerRow.createCell(2).setCellValue("Address");
        headerRow.createCell(3).setCellValue("Quantity");
        headerRow.createCell(4).setCellValue("Payment Method");
        headerRow.createCell(5).setCellValue("Total Price");
        // Thêm các tiêu đề cột khác (Field 3, Field 4, ...)
//kê
        // Ghi dữ liệu của các đối tượng vào sheet
        int rowNum = 1;
        for (Order object : objectList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(object.getUsers().getUsername());
            row.createCell(1).setCellValue(object.getOrderDate());
            row.createCell(2).setCellValue(object.getUsers().getAddress());
            row.createCell(3).setCellValue(object.getQuantity());
            row.createCell(4).setCellValue(object.getPaymentMethod());
            row.createCell(5).setCellValue(object.getTotalPrice());
            // Ghi các giá trị của các trường khác (Field 3, Field 4, ...)
        }

        // Thiết lập các thuộc tính của phản hồi HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Sales_report.xlsx");

        // Ghi workbook vào OutputStream của phản hồi
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Đảm bảo rằng dữ liệu được ghi vào phản hồi
        outputStream.flush();
        outputStream.close();

    }

    @Override
    public List<Object> getOrderByMonthvsYear() {
       return orderRepository.getOrderByMonthvsYear();
    }

    @Override
    public void updateStatus(Long id, String status,Long idShip) {
        Order order=orderRepository.findById(id).get();
        order.setOrderStatus(status);
        order.setIdShip(idShip);
        orderRepository.save(order);
    }

    @Override
    public Order findbyId(Long id) {
       return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAllByShipperId(Long id) {
        return orderRepository.findAllByShipperId(id);
    }

    @Override
    public List<Order> findAllByStatus(Long id, String status) {
       return orderRepository.findAllByStatus(id,status);
    }

    

}
