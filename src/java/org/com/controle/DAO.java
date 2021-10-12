package org.com.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.com.modelos.Acervo;
import org.com.rest.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Classe de Direct Access Object. O modelo DAO foi empregado visando tentar
 * gerenciar a declaração de Sessions de uma forma simplificada, os métodos são
 * static para que não seja necessário Abrir Sessions constantes através do
 * HibernateUtil
 *
 * @author Elias Melo
 */
public class DAO {

    static Session s;
    static Transaction t;

    /**
     * Método listaAcervo() - Este método busca uma lista de objetos de Acervo
     * por qualquer um dos itens [Descrição, Ano de publicação, Autor, Tipo,
     * Data de inclusão]
     *
     * @param txt - Recebe o token a ser pesquisado caso seja adicionada um (*)
     * ao final do token será incluido também na pesquisa os itens com
     * data_excluido definida, caso contrário estes itens serão ignorados
     * @return List<Acervo> - retorna uma lista de objetos contendo os
     * resultados da pesquisa
     */
    public static List<Acervo> listaAcervo(String txt) {
        String sql = "select * from acervo";
        String tmp = txt;
        if (txt.endsWith("*")) {
            tmp = txt.substring(0, txt.length() - 1);
        }

        if (txt.endsWith("*") && tmp.equals("")) {
            sql += "";
        } else if (txt.endsWith("*") && !tmp.equals("")) {
            sql += " WHERE (descricao LIKE '%" + tmp + "%' OR ano_pub LIKE '%" + tmp + "%' OR autor LIKE '%" + tmp + "%' OR tipo LIKE '%" + tmp + "%' OR data_incluso = '" + tmp + "')";
        } else if (!txt.endsWith("*") && tmp.equals("")) {
            sql += " WHERE data_excluido IS NULL";
        } else if (!txt.endsWith("*") && !tmp.equals("")) {
            sql += " WHERE (descricao LIKE '%" + tmp + "%' OR ano_pub LIKE '%" + tmp + "%' OR autor LIKE '%" + tmp + "%' OR tipo LIKE '%" + tmp + "%' OR data_incluso LIKE '%" + tmp + "%') AND data_excluido IS NULL";
        }

        List<Acervo> lst = new ArrayList<>();
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        lst = s.createSQLQuery(sql).addEntity(Acervo.class).list();
        t.commit();
        s.close();
        return lst;
    }

    /**
     * Método encontarItem - Este método busca um objeto de acervo pelo ID
     *
     * @param id - Recebe o ID
     * @return Acervo - retorna o primeiro objeto com este id
     */
    public static Acervo encontarItem(int id) {
        Acervo a = new Acervo();
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        a = (Acervo) s.createSQLQuery("select * from acervo WHERE id = '" + id + "'").addEntity(Acervo.class).list().get(0);
        t.commit();
        s.close();
        return a;
    }

    /**
     * Método gravarItem - Este método grava um novo item no Banco de Dados
     *
     * @param item - Recebe o objeto de acervo já pré definido
     */
    public static void gravarItem(Acervo item) {
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        s.save(item);
        t.commit();
        s.close();
    }

    /**
     * Método atualizarItem - Este método grava dados atualizados em um item no
     * Banco de Dados
     *
     * @param item - Recebe o objeto de acervo já pré definido
     */
    public static void atualizarItem(Acervo item) {
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        s.update(item);
        t.commit();
        s.close();
    }

    /**
     * Método removerItem - Este método define uma data_excluido para o item.
     * desta forma o titem deixará de ser considerado no método listaAcervo()
     *
     * @param id - Recebe o id referente ao item de acervo a ser removido, neste
     * caso optei por não utilizar a referencia completa do objeto, mas pega-lo
     * diretamente do banco
     */
    public static void removerItem(int id) {
        Acervo a = encontarItem(id);
        a.setData_excluido(new Date());
        atualizarItem(a);
    }
}
