package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // @Query(value = "select DATE_FORMAT(dh.orderDate, '%m') as month, "
    // + " DATE_FORMAT(dh.orderDate, '%Y') as year, sum(dh.totalPrice) as total "
    // + " from Order dh"
    // // + " where dh.id = ct.donHang.id and dh.trangThaiDonHang ='Hoàn thành'"
    // + " group by DATE_FORMAT(dh.orderDate, '%Y%m')"
    // + " order by year asc" )
    @Query("SELECT MONTH(o.orderDate) AS month, YEAR(o.orderDate) AS year, SUM(o.totalPrice) AS total " +
            "FROM Order o " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate)"+
            "ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    public List<Object> getOrderByMonthvsYear();

}
