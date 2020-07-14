package com.codesai.auction_house.business.actions;

import com.codesai.auction_house.business.model.auction.AuctionRepository;

import static com.codesai.auction_house.business.model.auction.Auction.anAuction;

public class CreateAuctionAction {
    private final AuctionRepository repository;

    public CreateAuctionAction(AuctionRepository repository) {
        this.repository = repository;
    }

    public String execute(CreateAuctionCommand command) {
        var auction = anAuction(
                command.itemName,
                command.description,
                command.initialBidAmount,
                command.conquerPriceAmount,
                command.expirationDay,
                command.ownerId
        );
        repository.save(auction);
        return auction.id;
    }
}
