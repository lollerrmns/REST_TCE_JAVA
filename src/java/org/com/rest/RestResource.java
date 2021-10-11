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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ler/{id}")
    public String ler(@PathParam("id") int id) {
        Acervo a = new Acervo();
        try {
            a = DAO.encontarItem(id);

        } catch (Exception ex) {
            return "ERROR";
        }
        return new Gson().toJson(a);
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
            a.setTipo(tipo);

            DAO.gravarItem(a);
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * @param json representation for the resource
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(String json) {
        SimpleDateFormat dateFormato = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        Acervo a = new Acervo();
        //Acervo a = new Gson().fromJson(json, Acervo.class);
        try {
            String[] token = json.split("\",\"");
            for (String tok : token) {
                if (tok.startsWith("{\"")) {
                    tok = tok.replace("{\"", "");
                } else if (tok.endsWith("\"}")) {
                    tok = tok.replace("\"}", "");
                }

                String[] tt = tok.split("\":\"");
                System.out.println("01 : " + tt[0]);
                System.out.println("02 : " + tt[1]);
                if (tt[1].equals("undefined") || tt[1].equals("")) {
                    tt[1] = "";
                }
                switch (tt[0]) {
                    case "id":
                        a.setId(Integer.parseInt(tt[1]));
                        break;
                    case "descricao":
                        a.setDescricao(tt[1]);
                        break;
                    case "ano_pub":
                        a.setAno_pub(Integer.parseInt(tt[1]));
                        break;
                    case "autor":
                        a.setAutor(tt[1]);
                        break;
                    case "data_incluso":
                        if (tt[1].equals("undefined") || tt[1].equals("")) {
                            a.setData_incluso(new Date());
                        } else {
                            a.setData_incluso(dateFormato.parse(ajustarData(tt[1])));
                        }
                        break;
                    case "data_alterado":
                        a.setData_alterado(new Date());
                        break;

                    case "tipo":
                        a.setTipo(tt[1]);
                        break;

                }
            }
            DAO.atualizarItem(a);
        } catch (Exception ex) {
            ex.printStackTrace();
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
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) {
        try {
            System.out.println("WOWOWOW");
            
            DAO.removerItem(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response
                    .status(400)
                    .build();

        }
        return Response
                .ok()
                .build();
    }

    public String ajustarData(String data) {
        if (data.contains("Oct")) {
            data = data.replace("Oct", "out");
        }

        return data;
    }
}
