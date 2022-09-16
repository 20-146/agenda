package com.darnet.agenda.repository;

import com.darnet.agenda.model.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    Page<Contacto> findByNombreContaining(String nombre, Pageable pageable);
}
