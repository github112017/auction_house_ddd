package com.codesai.auction_house.infrastructure;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;

import java.util.HashMap;

public class ActionFactory {

    private static final AuctionRepository auctionRepository = new AuctionRepository() {
        private HashMap<String, Auction> auctions = new HashMap<>();
        @Override
        public void save(Auction auction) {
            auctions.put(auction.id, auction);
        }
    };

    public static CreateAuctionAction createAuctionAction() {
        return new CreateAuctionAction(auctionRepository);
    }
}
