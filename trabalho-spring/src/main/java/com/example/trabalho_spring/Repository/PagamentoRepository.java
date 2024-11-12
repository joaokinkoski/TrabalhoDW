package com.example.trabalho_spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trabalho_spring.Model.Pagamento;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByCodJogador(Long codJogador);
    List<Pagamento> findByMes(int mes);
    List<Pagamento> findByAno(int ano);
}
