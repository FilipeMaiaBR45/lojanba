package ufrn.eaj.filipe.lojanba.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufrn.eaj.filipe.lojanba.model.Produto;
import ufrn.eaj.filipe.lojanba.repository.ProdutoRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProdutoService {

    ProdutoRepository repository;

    @Autowired
    public void setRepository(ProdutoRepository repository){
        this.repository = repository;
    }

    public List<Produto> findAll(){
        return repository.findAll();
    }

    public Produto findById(Long id){
        return repository.getById(id);
    }

    public void save(Produto p){
        repository.save(p);
    }


    public void deletarProduto(Long id){
        Date data = new Date();
        Produto produto = repository.getById(id);
        produto.setDeleted(data);

        repository.save(produto);
    }

   // public void atualizarProduto(){
       //repository.
   // }

}
