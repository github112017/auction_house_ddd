package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.UUID;

public class Auction {
    public final String id;
    public final String name;
    public final String description;
    public final Money initialBid;
    public final Money conquerPrice;
    public final LocalDate expirationDay;
    public final String ownerId;

    private Auction(String name, String description, Money initialBid, Money conquerPrice, LocalDate expirationDay, String ownerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.initialBid = initialBid;
        this.conquerPrice = conquerPrice;
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }

    public static Auction anAuction(String name, String description, Money initialBid, Money conquerPrice, LocalDate expirationDay, String ownerId) {
        if (initialBid.isGreaterThan(conquerPrice)) throw new InitialBidIsGreaterThanConquerPrice(initialBid, conquerPrice);
        return new Auction(name, description, initialBid, conquerPrice, expirationDay, ownerId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
