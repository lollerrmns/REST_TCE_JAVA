/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author NOTT
 */
//import org.hibernate.annotations.OptimisticLockType;
@Entity
//@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL)
/*@Table(name = "Employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "EMAIL") })*/
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_incluso", unique = false, nullable = false, length = 10, columnDefinition = "DATETIME(10) DEFAULT CURRENT_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private Date data_incluso;

    @Column(name = "data_alterado", unique = false, nullable = true, length = 10,columnDefinition = "DATETIME(10) DEFAULT NULL")
    private Date data_alterado;

    @Column(name = "data_excluido", unique = false, nullable = true, length = 10,columnDefinition = "DATETIME(10) DEFAULT NULL")
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