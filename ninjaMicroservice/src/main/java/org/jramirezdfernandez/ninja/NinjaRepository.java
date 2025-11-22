package org.jramirezdfernandez.ninja;

import org.jramirezdfernandez.ninja.Ninja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  NinjaRepository extends JpaRepository<Ninja, Long> {

    @Query("SELECT n FROM Ninja n LEFT JOIN FETCH n.aldea")
    List<Ninja> findAllWithAldea();

}
