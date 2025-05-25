package com.timo.spring.repositories;

import com.timo.spring.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    @Modifying
    @Query(value = "UPDATE book SET person_id=:p_id WHERE id=:b_id", nativeQuery = true)
    void setOwner(@Param("b_id") int bookId, @Param("p_id") Integer ownerId);

    List<Book> findByTitleLikeIgnoreCase(String title);
}
