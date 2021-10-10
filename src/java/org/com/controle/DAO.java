package org.com.controle;

import java.util.ArrayList;
import java.util.List;
import org.com.modelos.Acervo;
import org.com.rest.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Elias Melo
 */
public class DAO {

    static Session s;
    static Transaction t;

    public static List<Acervo> listaAcervo() {
        List<Acervo> lst = new ArrayList<>();
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        lst = s.createSQLQuery("select * from acervo WHERE data_excluido IS NULL").addEntity(Acervo.class).list();
        t.commit();
        s.close();
        return lst;
    }

    public static Acervo encontarItem(int id) {
        Acervo a = new Acervo();
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        a = (Acervo) s.createSQLQuery("select * from acervo WHERE data_excluido IS NULL").addEntity(Acervo.class).list().get(0);
        t.commit();
        s.close();
        return a;
    }

    public static void gravarItem(Acervo a) {
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        s.save(a);
        t.commit();
        s.close();
    }

    public static void atualizarItem(Acervo a) {
        s = HibernateUtil.getSessionFactory().openSession();
        t = s.getTransaction();
        t.begin();
        s.update(a);
        t.commit();
        s.close();
    }

    public static void removerItem(int id) {
        Acervo a = encontarItem(id);
        a.setData_excluido("LOL");
        atualizarItem(a);
    }
}
