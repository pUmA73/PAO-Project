package daoServices;

import dao.OfferDao;
import models.Offer;

import java.sql.SQLException;

public class OfferRepositoryService {
    private final OfferDao offerDao = OfferDao.getInstance();

    public OfferRepositoryService() throws SQLException {}

    public Offer getOffer(int offerId) throws SQLException {
        Offer offer = offerDao.read(offerId);
        if(offer != null) {
            System.out.println(offer);
        } else {
            System.out.println("No offer found!");
        }

        return offer;
    }

    public void removeOffer(int offerId) throws SQLException {
        Offer offer = getOffer(offerId);
        if(offer == null) return;

        offerDao.delete(offer);
        System.out.println("Removed: " + offer);
    }

    public void addOffer(Offer offer) throws SQLException {
        if(offer != null) {
            offerDao.add(offer);
        }
    }

    public void updateOffer(Offer offer) throws SQLException {
        if(offer != null) {
            offerDao.update(offer);
            System.out.println("Updated: " + offer);
        }
    }
}
