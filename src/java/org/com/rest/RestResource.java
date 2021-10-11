package org.com.rest;

import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
//        List<Acervo> lst = DAO.listaAcervo("");
//        String lol = "";
//        for (Acervo acervo : lst) {
//            lol += acervo.getDescricao();
//        }
        return "";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{txt}")
    public String listar(@PathParam("txt") String txt) {
        System.out.println("LOL - " + txt);
        List<Acervo> result = new ArrayList<Acervo>();

        result = DAO.listaAcervo(txt);

        if (result == null) {
            throw new WebApplicationException(404);
        }

        return new Gson().toJson(result);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response gravar(
            @FormParam("descricao_Inc") String descricao,
            @FormParam("autor_Inc") String autor,
            @FormParam("ano_pub_Inc") int ano_pub,
            @FormParam("tipo_inc") String tipo
    ) {
        try {
            Acervo a = new Acervo();
            a.setDescricao(descricao);
            a.setAutor(autor);
            a.setAno_pub(ano_pub);
            a.setData_incluso(new Date());

            DAO.gravarItem(a);
        } catch (Exception ex) {
            return Response
                    .status(400)
                    .build();

        }

        return Response
                .ok()
                .build();

    }

    /**
     * PUT method for updating or creating an instance of RestResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response alterar(
            @FormParam("id") int id,
            @FormParam("descricao") String descricao,
            @FormParam("autor") String autor,
            @FormParam("ano_pub") int ano_pub,
            @FormParam("data_alterado") String data_alterado,
            @FormParam("data_incluso") String data_incluso
    ) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        try {
            Acervo a = new Acervo();
            a.setId(id);
            a.setDescricao(descricao);
            a.setAutor(autor);
            a.setAno_pub(ano_pub);
            a.setData_alterado(sdf.parse(data_alterado));
            a.setData_incluso(sdf.parse(data_incluso));
            DAO.atualizarItem(a);
        } catch (Exception ex) {
            return Response
                    .status(400)
                    .build();

        }

        return Response
                .ok()
                .build();
    }

    @DELETE
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String remover( //@FormParam("id") int id
            ) {
        System.out.println("WOWOWOW");
        //DAO.removerItem(id);
        return "ok";
    }
}
