package com.webblog.repository;

import com.webblog.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByEmail(String email);
    List<Person> findAllByPersonDeactivated(int number);
}
