package ressourcerest;
import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("etudiants")
public class restEtudiant {
    public static EtudiantBusiness etd = new EtudiantBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiants(@QueryParam("id") String id) {
        List<Etudiant> l = new ArrayList<Etudiant>();
        if (id != null) {
            l = Collections.singletonList(etd.getEtudiantByIdentifiant(id));
        }
        else {
            l = etd.getAllEtudiants();
        }
        return Response.status(200).entity(l).build();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantByCode(@PathParam("code") int code) {
        List<Etudiant> l = new ArrayList<Etudiant>();
        OptionBusiness o = new OptionBusiness();
        Option op = new Option(o.getOptionByCode(code));
        if (op.getCodeOption()!=-1) {
            l = etd.getEtudiantsByOption(op);
            return Response.status(200).entity(l).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if(etd.deleteEtudiant(id)){
            return Response.status(204).build();
        }
        else  {
            return Response.status(404).build();
        }
    }

}
