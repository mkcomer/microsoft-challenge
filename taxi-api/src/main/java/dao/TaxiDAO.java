package dao;

import models.Taxi;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxiDAO {

    private static final Logger logger = LoggerFactory.getLogger(TaxiDAO.class);
    HibernateUtil hibernateUtil = new HibernateUtil();

    /**
     * Queries the Taxi table for all entries which contain the
     * desired parameters.
     *
     * @param taxiType  - GREEN, YELLOW, FOR-HIRE-VEHICLE
     * @param pickUpId  - Pick Up Borough
     * @param dropOffId - Drop Off Borough
     * @param startTime - desired start time of ride
     * @param endTime   - desired end time of ride
     * @return List<Taxi> taxiData
     * @throws Exception
     */
    public List<Taxi> findByParameters(String taxiType, String pickUpId, String dropOffId, String startTime, String endTime) throws Exception {
        return createQuery(taxiType, pickUpId, dropOffId, startTime, endTime);
    }


    /**
     * Retrieves taxi history from the database based on set parameters
     *
     * @param taxiType  - GREEN, YELLOW, FOR-HIRE-VEHICLE
     * @param pickUpId  - Pick Up Borough
     * @param dropOffId - Drop Off Borough
     * @param startTime - desired start time of ride
     * @param endTime   - desired end time of ride
     * @return List<Taxi> taxiData
     * @throws Exception A generic exception.
     */

    public static List<Taxi> createQuery(String taxiType, String pickUpId, String dropOffId, String startTime, String endTime) throws Exception {
        Transaction transaction = null;
        List<Taxi> taxiList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // create a CriteriaQuery for the EvidenceElementEntity
            CriteriaQuery<Taxi> cq = builder.createQuery(Taxi.class);
            // define the root table
            Root<Taxi> root = cq.from(Taxi.class);
            // select * from hello
            cq.select(root);
            // to build a dynamic where we need a collection of WHERE clauses
            List<Predicate> whereClauses = new ArrayList<Predicate>();
            // add to clause if we have a taxi type
            if (taxiType != null) {
                whereClauses.add(builder.equal(root.get("taxiType"), taxiType));
            }
            // add to clause if we have a pick up location
            if (pickUpId != null) {
                whereClauses.add(builder.equal(root.get("puLocationid"), pickUpId));
            }

            // add to clause if we have a drop off location
            if (dropOffId != null) {
                whereClauses.add(builder.equal(root.get("doLocationId"), dropOffId));
            }
            // add to clause if we have start and end dates for rides
            // returns all rides that fall between start and end time
            if (startTime != null && endTime != null) {
                whereClauses.add(builder.greaterThanOrEqualTo(root.get("pickupTime"), startTime));
                whereClauses.add(builder.lessThanOrEqualTo(root.get("dropoffTime"), endTime));
            }

            // build where clause
            if (!whereClauses.isEmpty()) {
                cq.where(builder.and(whereClauses.toArray(new Predicate[whereClauses.size()])));
            }
            Query<Taxi> query = session.createQuery(cq);
            System.out.println(query.unwrap(org.hibernate.Query.class).getQueryString());
            taxiList = query.list();
            return taxiList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e.toString());
            return taxiList;
        }
    }
}
