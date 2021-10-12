package org.com.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

import javax.persistence.TemporalType;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * Modelo de Classe tipificando o objeto principal. Esta classe baseia-se em
 * Anotações JPA para Formar a tabela no Banco de Dados através do Hibernate.
 * Além das declarações de vatiaveis e anotações de tipos há somente a
 * declaração de construtor vazio (necessária para o hibernate), e os
 * Geters&Seters (também necessários para o hibernate)
 *
 * @author Elias Melo
 */
@Entity
public class Acervo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "descricao", unique = false, nullable = false, length = 250)
    private String descricao;

    @Column(name = "ano_pub", unique = false, nullable = false, length = 10)
    private int ano_pub;

    @Column(name = "autor", unique = false, nullable = false, length = 100)
    private String autor;

    @Column(name = "data_incluso", unique = false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Date data_incluso;

    @Column(name = "data_alterado", unique = false, nullable = true, columnDefinition = "DATETIME DEFAULT NULL")
    @Generated(GenerationTime.INSERT)
    private Date data_alterado;

    @Column(name = "data_excluido", unique = false, nullable = true, columnDefinition = "DATETIME DEFAULT NULL")
    @Generated(GenerationTime.INSERT)
    private Date data_excluido;

    @Column(name = "tipo", unique = false, nullable = false, length = 30)
    private String tipo;

    public Acervo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getAno_pub() {
        return ano_pub;
    }

    public void setAno_pub(int ano_pub) {
        this.ano_pub = ano_pub;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getData_incluso() {
        return data_incluso;
    }

    public void setData_incluso(Date data_incluso) {
        this.data_incluso = data_incluso;
    }

    public Date getData_alterado() {
        return data_alterado;
    }

    public void setData_alterado(Date data_alterado) {
        this.data_alterado = data_alterado;
    }

    public Date getData_excluido() {
        return data_excluido;
    }

    public void setData_excluido(Date data_excluido) {
        this.data_excluido = data_excluido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
