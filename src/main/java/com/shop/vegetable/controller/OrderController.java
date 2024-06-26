package com.shop.vegetable.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.vegetable.config.vppay.VNPayService;
import com.shop.vegetable.dto.UserDto;
import com.shop.vegetable.entity.Order;
import com.shop.vegetable.entity.OrderDetail;
import com.shop.vegetable.entity.Product;
import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.ShoppingCart;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.exception.RecordNotFoundException;
import com.shop.vegetable.service.OrderService;
import com.shop.vegetable.service.ShoppingCartService;
import com.shop.vegetable.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final UserService usersService;
    private final OrderService orderService;

    private final ShoppingCartService cartService;
    private final UserService userService;

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }

            Users Users = usersService.findByUsername(principal.getName());
            // if (Users.getAddress() == null || Users.getCity() == null ||
            // Users.getPhoneNumber() == null) {
            // model.addAttribute("information", "You need update your information before
            // check out");
            // model.addAttribute("Users", Users);
            // model.addAttribute("title", "Profile");
            // model.addAttribute("page", "Profile");
            // return "Users-information";
            // } else {

            ShoppingCart cart = usersService.findByUsername(principal.getName()).getCart();
            model.addAttribute("Users", Users);
            model.addAttribute("title", "Check-Out");
            model.addAttribute("page", "Check-Out");
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("grandTotal", cart.getTotalPrice());
            return "client/checkout";

        }
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }
            Users Users = usersService.findByUsername(principal.getName());
            List<Order> orderList = Users.getOrders();
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            model.addAttribute("currentPages", "order");
            return "client/order";
        }
    }

    @GetMapping("/ordersAD")
    public String getOrdersAD(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }
            List<Order> orderList = orderService.findALlOrders();
            double totalPrice = 0.0;
            for (Order ordd : orderList) {
                totalPrice += ordd.getTotalPrice();
            }

            int quantitys = 0;
            for (Order ordd : orderList) {
                quantitys += ordd.getQuantity();
            }
            model.addAttribute("quantitys", quantitys);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            model.addAttribute("size", orderList.size());
            model.addAttribute("currentPages", "orders");
            return "admin/ordersAD";
        }
    }

    @RequestMapping(value = "/cancel-order", method = { RequestMethod.PUT, RequestMethod.GET })
    public String cancelOrder(Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/orders";
    }

    @RequestMapping(value = "/add-order", method = { RequestMethod.POST })
    public String createOrder(Principal principal,
            Model model,
            HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Users Users = usersService.findByUsername(principal.getName());
            ShoppingCart cart = Users.getCart();
            // Order order = orderService.save(cart);
            // session.removeAttribute("totalItems");
            // model.addAttribute("order", order);
            // model.addAttribute("title", "Order Detail");
            // model.addAttribute("page", "Order Detail");
            // attributes.addFlashAttribute("success", "You have placed your order
            // successfully, thank you!");
            // return "redirect:/orders";
            String infor = cart.getId().toString();
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder((int) cart.getTotalPrice(), infor, baseUrl);
            return "redirect:" + vnpayUrl;
        }
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session,
            RedirectAttributes attributes) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            ShoppingCart cart = cartService.FindById(Long.parseLong(orderInfo));
            Order order = orderService.save(cart);
            session.removeAttribute("totalItems");
            return "client/ordersuccess";
        }
        return "client/orderfail";
    }

    @GetMapping("/order-detail")
    public String dt(Long id, Model model, Principal principal, Authentication authentication) {
        if (principal != null) {
            model.addAttribute("namelogin", principal.getName());
            Users users = userService.findByUsername(principal.getName());
            List<Role> roles = users.getRoles();
            if (roles.size() == 1) {
                model.addAttribute("rolelogin", roles.get(0).getName());
            }

        }
        List<Order> orders;
        if (principal.getName().equals("Admin")) {
            orders = orderService.findALlOrders();
        } else {
            orders = orderService.findAll(principal.getName());
        }

        Order order = null;

        for (Order order2 : orders) {
            if (order2.getId().equals(id)) {
                order = order2;
            }
        }
        if (order == null) {
            throw new RecordNotFoundException();
        }

        model.addAttribute("order", order);

        List<OrderDetail> orderDetails = orderService.AllOrderDetail(order.getId());

        model.addAttribute("orderproduct", orderDetails);

        return "client/orderdetail";
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) {
        try {
            // Tạo danh sách đối tượng cần xuất ra Excel
            List<Order> objectList = orderService.findALlOrders();

            // Xuất danh sách đối tượng vào tệp Excel
            orderService.exportToExcel(objectList, response);
        } catch (IOException e) {
            // Xử lý lỗi nếu cần
        }
    }
}
