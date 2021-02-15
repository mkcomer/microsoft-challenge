package dao;

import models.Borough;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoroughDAO {
    private static final Logger logger = LoggerFactory.getLogger(BoroughDAO.class);

    HibernateUtil hibernateUtil = new HibernateUtil();

    /**
     * Queries the Borough table for Location ID based on Borough Name
     *
     * @param boroughName
     * @throws Exception
     */
    public List<Borough> findBoroughInformationByZone(String boroughName, String zone) throws Exception {
          return createQuery(boroughName, zone);
    }

    public List<Borough> findAllBoroughInformation(String boroughName) throws Exception {
        return createQuery(boroughName, null);
    }

    private static List<Borough> createQuery(String name, String zone) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // create a CriteriaQuery for the EvidenceElementEntity
            CriteriaQuery<Borough> cq = builder.createQuery(Borough.class);
            // define the root table
            Root<Borough> root = cq.from(Borough.class);
            // select * from hello
            cq.select(root);
            // to build a dynamic where we need a collection of WHERE clauses
            List<Predicate> whereClauses = new ArrayList<Predicate>();
            // add to clause if we have a taxi type

            if (name !=null) {
                whereClauses.add(builder.equal(root.get("borough"), name));
            }
            // add to clause if we have a zone specified for location ID
            if (zone != null) {
                logger.info("adding zone to query");
                whereClauses.add(builder.equal(root.get("zone"), zone));
            }

            // build where clause
            if (!whereClauses.isEmpty()) {
                cq.where(builder.and(whereClauses.toArray(new Predicate[whereClauses.size()])));
            }
            Query<Borough> query = session.createQuery(cq);
            logger.info(query.unwrap(org.hibernate.Query.class).getQueryString());
            List<Borough> boroughList = query.list();
            return boroughList;
    }
}
