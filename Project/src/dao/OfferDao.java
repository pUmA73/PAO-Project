package dao;

import models.Offer;

import java.util.List;
import java.util.ArrayList;

public class OfferDao {

    private static List<Offer> offers = new ArrayList<>();

    // Find offers based on Id
    public Offer read(String offerId) {
        if(!offers.isEmpty()) {
            for(Offer o : offers) {
                if(o.getOfferId().equals(offerId)) {
                    return o;
                }
            }
        }

        return null;
    }

    // Find offers based on User
    public Offer read(String firstName, String lastName) {
        if(!offers.isEmpty()) {
            for(Offer o : offers) {
                if(o.getBuyer().getLastName().equals(lastName) &&
                        o.getBuyer().getFirstName().equals(firstName)) {
                    return o;
                }
            }
        }
        return null;
    }

    public void delete(Offer offer) {offers.remove(offer);}

    public void create(Offer offer) {offers.add(offer);}

}
