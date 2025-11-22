package org.jramirezdfernandez.mision;

import org.jramirezdfernandez.mision.Mision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MisionRepository extends JpaRepository<Mision, Long> {
}
