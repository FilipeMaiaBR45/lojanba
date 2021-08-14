package ufrn.eaj.filipe.lojanba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.eaj.filipe.lojanba.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
