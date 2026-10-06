package ressourcerest;
import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("options")

public class restOption {
    public static OptionBusiness optB=new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  Response getAllOption(@QueryParam("domaine") String D,@QueryParam("code") int id) {
        List <Option>l = new ArrayList<Option>();
        if (D == null && id == -1) {
            l = optB.getListeOptions();
        } else if (D != null && id == -1) {
            l = optB.getOptionsByDomaine(D);
        }
        else {
            l = Collections.singletonList(optB.getOptionByCode(id));
        }
        return Response.status(200).entity(l).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option op){
        if (optB.addOption(op)){
            return Response.status(200).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int id){
        if(optB.deleteOption(id)){
            return Response.status(204).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int id,Option op){
            if (optB.updateOption(id, op)) {
                return Response.status(200).build();
            } else {
                return Response.status(404).build();
            }
    }
}