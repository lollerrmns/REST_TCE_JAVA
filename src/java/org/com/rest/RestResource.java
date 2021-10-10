package org.com.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.com.controle.DAO;
import org.com.modelos.Acervo;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * REST Web Service
 *
 * @author Elias Melo
 */
@Path("rest")
public class RestResource {

    @Context
    private UriInfo context;

    /**
     * Nova Instância de RestResource
     */
    public RestResource() {
    }

    /**
     * Retrieves representation of an instance of org.com.rest.RestResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String ler() {
        List<Acervo> lst = DAO.listaAcervo();
        String lol = "";
        for (Acervo acervo : lst) {
            lol += acervo.getDescricao();
        }
        return lol;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String gravar(
            @FormParam("id") int id,
            @FormParam("descricao") String descricao,
            @FormParam("autor") String autor,
            @FormParam("ano_pub") String ano_pub,
            @FormParam("data_alterado") String data_alterado,
            @FormParam("data_incluso") String data_incluso
    ) {
        Acervo a = new Acervo();
        a.setId(id);
        a.setDescricao(descricao);
        a.setAutor(autor);
        a.setAno_pub(ano_pub);
        a.setData_alterado(data_alterado);
        a.setData_incluso(data_incluso);

        DAO.gravarItem(a);
        return "OK";
    }

    /**
     * PUT method for updating or creating an instance of RestResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String alterar(
            @FormParam("id") int id,
            @FormParam("descricao") String descricao,
            @FormParam("autor") String autor,
            @FormParam("ano_pub") String ano_pub,
            @FormParam("data_alterado") String data_alterado,
            @FormParam("data_incluso") String data_incluso
    ) {
        Acervo a = new Acervo();
        a.setId(id);
        a.setDescricao(descricao);
        a.setAutor(autor);
        a.setAno_pub(ano_pub);
        a.setData_alterado(data_alterado);
        a.setData_incluso(data_incluso);
        
        DAO.atualizarItem(a);
        return "ok";
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String remover(
            @FormParam("id") int id
    ) {
        System.out.println("LOL"+id);
        DAO.removerItem(id);
        return "ok";
    }
}
