package ufrn.eaj.filipe.lojanba.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idProduto;
    Date deleted;
    String imageUri;
    String nomeProduto;
    String timeJogador;
    float preco;
    String tamanho;
    String indicadoPara;

}
