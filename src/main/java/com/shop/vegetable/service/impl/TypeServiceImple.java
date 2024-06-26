package com.shop.vegetable.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;



import com.shop.vegetable.dto.TypeDto;

import com.shop.vegetable.entity.Type;
import com.shop.vegetable.exception.NotFoundException;
import com.shop.vegetable.repository.TypeRepository;
import com.shop.vegetable.service.TypeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeServiceImple implements TypeService {
    private final TypeRepository courseRepository;


    @Override
    public List<Type> findAll() {
        // TODO Auto-generated method stub
        return courseRepository.findAll();
    }

     @Override
    public Type save(TypeDto Productdto) {
        Type Product= new Type();
        Product.setName(Productdto.getName());
        return courseRepository.save(Product);
    }
    

    @Override
    public Optional<Type> findById(Long id) {
        // TODO Auto-generated method stub
        return courseRepository.findById(id);
    }

    // @Override
    // public void update(Long id,Type course) {
    //     // Type courseUpdate = courseRepository.getReferenceById(course.getId());
    //     // courseUpdate.setName(course.getName());
    //     // Type type=courseRepository.findById(id).get();
    // //     Type type=new Type();
    // //     type.setId(id);
    // //     type.setName(course.getName());
    // //     // Type typeUpdate=courseRepository.save(type);
    // //     // return typeUpdate;
    // //     Optional<Type> optionalMe = courseRepository.findById(id);
    // //    if (optionalMe.isPresent()) {      
    // //      courseRepository.save(type);
    // //    }
    // //    else {
    // //     throw new EntityNotFoundException();
    // //   } 

    //   Type category = courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category With Id: " + id));
    //   category.setName(course.getName());
    //   courseRepository.save(category);
    // }

    

    @Override
    public void delete(Long id) {
        Type course= courseRepository.getReferenceById(id);
       courseRepository.delete(course);
    }

    @Override
    public List<Type> findCourses(String keyword) {
        return courseRepository.findByName(keyword);
    }

    @Override
    public Type update(Type course) {
        return courseRepository.save(course);
    }

}
