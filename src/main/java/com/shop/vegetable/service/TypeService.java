package com.shop.vegetable.service;

import java.util.List;
import java.util.Optional;

import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Type;

public interface TypeService {
    Type save(TypeDto coursedto);

    List<Type> findAll();

    Optional<Type> findById(Long id);

    Type update(Type course);

    void delete(Long id);

    List<Type> findCourses(String keyword);

}
