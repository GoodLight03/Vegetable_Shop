package com.shop.vegetable.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
@Modifying
@Query("update Contact u set u.reply = :reply, u.dayReply = :dayReply, u.status='Đã trả lời ' where u.id = :id")
int updateUserSetStatusForName(@Param("reply") String reply, 
  @Param("dayReply") Date dayReply,@Param("id") Long id);

}
