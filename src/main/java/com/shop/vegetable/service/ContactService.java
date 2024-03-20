package com.shop.vegetable.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.shop.vegetable.entity.Contact;
import com.shop.vegetable.entity.Type;

public interface ContactService {
    Optional<Contact> findById(long id);
	
	Contact save(Contact lh);
	
	int countByStatus(String status);

    List<Contact> findAll();

    int update(String reply, Date repDate, Long id);
}
