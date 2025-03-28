package br.univille.projetohotelpracachorro.entity.ensalamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;


import lombok.Builder;
import org.hibernate.type.ListType;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name= "ensalamento")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor


public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "sala", unique = true)
    private String nomeSala;

    @Type(type = "jsonb")
    @Column(name = "horario", columnDefinition = "JSON")
    private List<Horarios> horarios;

}
