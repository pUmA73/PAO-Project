package daoServices;

import dao.OfferDao;
import models.Offer;

public class OfferRepositoryService {

    private OfferDao offerDao;

    public OfferRepositoryService() {this.offerDao = new OfferDao();}

    public Offer getOfferById(String offerId) {
        Offer offer = offerDao.read(offerId);
        if(offer != null) {
            System.out.println(offer);
        } else {
            System.out.println("No offer has been found!");
        }

        return offer;
    }

    public Offer getOfferByUser(String make, String model) {
        Offer offer = offerDao.read(make, model);
        if(offer != null) {
            System.out.println(offer);
        } else {
            System.out.println("No offer has been found!");
        }

        return offer;
    }

    public void removeOffer(String offerId) {
        Offer offer = offerDao.read(offerId);
        if(offerId == null) {
            System.out.println("No auction has been found!");
        } else {
            offerDao.delete(offer);
            System.out.println("Removed " + offer);
        }
    }

    public void addOffer(Offer offer) {
        if(offer != null) {
            offerDao.create(offer);
        }
    }

}
