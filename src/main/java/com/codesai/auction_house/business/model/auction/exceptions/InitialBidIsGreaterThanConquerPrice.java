package com.codesai.auction_house.business.model.auction.exceptions;

import com.codesai.auction_house.business.model.generic.Money;

import java.util.Locale;

public class InitialBidIsGreaterThanConquerPrice extends RuntimeException {
    public InitialBidIsGreaterThanConquerPrice(Money initialBid, Money conquerPrice) {
        super(String.format(Locale.UK, "initial cannot be greater %.2f than conquer price %.2f", initialBid.amount, conquerPrice.amount));
    }
}
