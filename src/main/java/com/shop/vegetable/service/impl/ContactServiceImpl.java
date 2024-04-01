package com.shop.vegetable.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.vegetable.entity.Contact;
import com.shop.vegetable.repository.ContactRepository;
import com.shop.vegetable.service.ContactService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Override
    public int countByStatus(String status) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Optional<Contact> findById(long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact save(Contact lh) {
      return contactRepository.save(lh);
    }

    @Override
    public List<Contact> findAll() {
       return contactRepository.findAll();
    }

    @Override
    public int update(String reply, Date repDate, Long id) {
        return contactRepository.updateUserSetStatusForName(reply, repDate, id);
    }

    @Override
    public Contact updateAll(Contact contact) {
        // TODO Auto-generated method stub
        return contactRepository.save(contact);
    }
    
}
