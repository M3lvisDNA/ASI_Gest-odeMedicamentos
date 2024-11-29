package co.mz.isutc.asi.projecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.mz.isutc.asi.projecto.model.Medicamentos;

/* Aqui se faz o envio e a recepcao de dados
 * Basicamente faz a interacao com a base de dados
 */
public interface MedRepository extends JpaRepository<Medicamentos, Long> {
    
} 
