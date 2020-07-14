package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.generic.Money;

import java.time.LocalDate;

public class CreateAuctionCommand {
    public final String itemName;
    public final String description;
    public final Money initialBidAmount;
    public final Money conquerPriceAmount;
    public final LocalDate expirationDay;
    public final String ownerId;

    public CreateAuctionCommand(String itemName, String description, double initialBidAmount, double conquerPriceAmount, LocalDate expirationDay, String ownerId) {
        this.itemName = itemName;
        this.description = description;
        this.initialBidAmount = Money.of(initialBidAmount);
        this.conquerPriceAmount = Money.of(conquerPriceAmount);
        this.expirationDay = expirationDay;
        this.ownerId = ownerId;
    }
}
