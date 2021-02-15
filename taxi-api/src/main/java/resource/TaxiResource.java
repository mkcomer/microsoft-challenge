package resource;

import dao.BoroughDAO;
import dao.TaxiDAO;
import models.Borough;
import models.Taxi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("taxi")
public class TaxiResource {

    private static final Logger logger = LoggerFactory.getLogger(TaxiResource.class);
    private static final TaxiDAO taxiDAO = new TaxiDAO();
    private static final BoroughDAO boroughDAO = new BoroughDAO();

    /**
     * Returns taxi history for all entries which contain the
     * desired parameters.
     *
     * @param type  - GREEN, YELLOW, FOR-HIRE-VEHICLE
     * @param startLocation - Pick Up Borough
     * @param endLocation - Drop Off Borough
     * @param startTime - desired start time of ride
     * @param endTime   - desired end time of ride
     * @return List<Taxi> taxiData
     * @throws Exception
     */
    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxiHistory(@QueryParam("taxiType") String type, @QueryParam("startLocation") String startLocation, @QueryParam("endLocation") String endLocation, @QueryParam("startTime") String startTime,
                                   @QueryParam("endTime") String endTime) {
        logger.info("/history/?taxitype={}&startLocation={}&endLocation={}&startTime={}&endTime={}", type, startLocation, endLocation, startTime, endTime);

        List<Taxi> taxiList = null;
            try {
                taxiList = taxiDAO.findByParameters(type, startLocation, endLocation, startTime, endTime);
            } catch (Exception e) {
                //Log and return exception from querying DB
                logger.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
            }

        if (null==taxiList || taxiList.isEmpty()){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error querying database - null/empty response").build();
        }

        logger.info("Number of taxi data returned is [{}]", taxiList.size());
        return Response.ok(taxiList.toString()).build();
        }

    /**
     * Returns locationID to pull historic data given borough name and desired zone
     *
     * @param borough - GREEN, YELLOW, FOR-HIRE-VEHICLE
     * @param zone - desired zone
     * @return List<Borough> boroughData
     * @throws Exception
     */

    @GET
    @Path("/locationId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoroughInformation(@QueryParam("borough") String borough, @QueryParam("zone") String zone) {

        logger.info("/locationId/?borough={}&zone={}", borough, zone);

        List<Borough> returnList = null;
        try {
            returnList = boroughDAO.findBoroughInformationByZone(borough, zone);
        } catch (Exception e) {
            logger.error("Exception thrown querying DB: {}", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }

        if (null==returnList || returnList.isEmpty()){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error querying database - null/empty response").build();
        }
        return Response.ok(returnList.toString()).build();
    }
}
