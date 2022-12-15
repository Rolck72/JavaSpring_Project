package com.example.myproject.repositories;

import com.example.myproject.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface imageRepository extends JpaRepository<Image, Integer> {


}
