/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.com.modelos.Acervo;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * REST Web Service
 *
 * @author NOTT
 */
@Path("rest")
public class RestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestResource
     */
    public RestResource() {
    }

    /**
     * Retrieves representation of an instance of org.com.rest.RestResource
     * @return an instance of java.lang.String
     */
    @GET
    //@Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        Acervo a = new Acervo();
        a.setId(1);
        a.setDescricao("N1");
        a.setAutor("Eu");
        a.setAno_pub("1997");
        a.setData_alterado("11211");
        a.setData_incluso("11211");
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.getTransaction();
        t.begin();
        s.save(a);
        t.commit();
        s.close();
        return "LOL";
    }

    /**
     * PUT method for updating or creating an instance of RestResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
