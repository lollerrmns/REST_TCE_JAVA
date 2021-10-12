package org.com.rest;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.com.controle.DAO;
import org.com.modelos.Acervo;

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
     * Construtor Vazio de RestResource
     */
    public RestResource() {
    }

    /**
     * Método GET - Este método busca um objeto de acervo pelo ID
     *
     * @param id - Recebe o ID através da URL
     * @return JSON - retorna um objeto referente a um item de acervo
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

    /**
     * Método GET - Este método busca uma lista de objetos de Acervo por
     * qualquer um dos itens [Descrição, Ano de publicação, Autor, Tipo, Data de
     * inclusão]
     *
     * @param txt - Recebe o ID através da URL
     * @return JSON - retorna um objeto referente a um item de acervo
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{txt}")
    public String listar(@PathParam("txt") String txt) {
        List<Acervo> result = new ArrayList<Acervo>();
        result = DAO.listaAcervo(txt);
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return new Gson().toJson(result);
    }

    /**
     * Método POST - Este método grava um novo registro
     *
     * @param descricao - Recebido através do Form
     * @param autor - Recebido através do Form
     * @param ano_pub - Recebido através do Form
     * @param tipo - Recebido através do Form
     * @return Response - Neste caso para evitar a página em branco, a resposta
     * é um redirecionamento para a página de pesquisa
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
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
                .ok("<html><body><script>window.location.replace('../#intro');</script></body></html>")
                .build();
    }

    /**
     * Método PUT - Este método Atualiza um registro existente. Este método tb
     * remove a data de exclusão e retorna o registro ao metodo listar()
     *
     * @param json -
     * {"id":"x","ano_pub":"x","autor":"x","data_incluso":"x","data_alterado":"x","data_excluido:":"x","descricao":"x","tipo":"x"}
     * @return Response - a resposta é um simples Status 200(OK) | 400(ERRO)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alterar(String json) {
        SimpleDateFormat dateFormato = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        Acervo a = new Acervo();
        try {
            String[] token = json.split("\",\"");
            for (String tok : token) {
                if (tok.startsWith("{\"")) {
                    tok = tok.replace("{\"", "");
                } else if (tok.endsWith("\"}")) {
                    tok = tok.replace("\"}", "");
                }

                String[] tt = tok.split("\":\"");
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

    /**
     * Método GET - Este método remove os arquivos de forma lógica ele
     * simplesmente adiciona uma data de exclusão, o que faz com que ele não
     * seja incluido no método listar(), a forma de mostrar os registros
     * excluidos é adicionar um asterisco ao final da String de pesquisa
     *
     * @param id - Recebe o ID através da URL
     * @return JSON - retorna um objeto referente a um item de acervo
     */
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) {
        try {
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

    /**
     * Este método foi criado para compatibilizar a conversão dos meses em SQL e
     * java.util.Date
     *
     * @param data - Data a ser Convertida [Oct ]
     * @return JSON - retorna um objeto referente a um item de acervo
     */
    public String ajustarData(String data) {
        if (data.contains("Feb")) {
            data = data.replace("Feb", "Fev");
        } else if (data.contains("Apr")) {
            data = data.replace("Apr", "Abr");
        } else if (data.contains("May")) {
            data = data.replace("May", "Mai");
        } else if (data.contains("Aug")) {
            data = data.replace("Aug", "Ago");
        } else if (data.contains("Sep")) {
            data = data.replace("Sep", "Set");
        } else if (data.contains("Oct")) {
            data = data.replace("Oct", "out");
        } else if (data.contains("Dec")) {
            data = data.replace("Dec", "Dez");
        }

        return data;
    }
}
