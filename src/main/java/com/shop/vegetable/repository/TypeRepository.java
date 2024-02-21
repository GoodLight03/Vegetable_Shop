package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    @Query("select p from Type p")
    List<Type> findAll();

    @Query(value = "update Type set name = ?1 where id=?2")
    Type update(String name, Long id);

    // Optional<Course> findById(Long id );
    @Query("select p from Type p where p.name like %?1%")
    List<Type> findByName(String name);
}
