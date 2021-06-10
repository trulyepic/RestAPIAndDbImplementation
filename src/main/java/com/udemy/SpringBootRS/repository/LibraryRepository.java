package com.udemy.SpringBootRS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.SpringBootRS.Controller.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, String>,LibraryRepositoryCustom{

}
