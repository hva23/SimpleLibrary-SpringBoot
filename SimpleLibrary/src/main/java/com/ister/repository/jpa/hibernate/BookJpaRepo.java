package com.ister.repository.jpa.hibernate;

import com.ister.model.Book;
import com.ister.model.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookJpaRepo extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(String id);
    Book findTopByOrderByIdDesc();
    Optional<Book> findByName(String name);
}
