package unit.bussines.actions;

import com.codesai.auction_house.business.actions.RetrieveAuctionAction;
import com.codesai.auction_house.business.actions.commands.RetrieveAuctionCommand;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static helpers.builder.AuctionBuilder.anAuction;
import static matchers.AuctionAssert.assertThatAuction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetrieveAuctionActionShould {

    AuctionRepository repository = mock(AuctionRepository.class);
    RetrieveAuctionAction action = new RetrieveAuctionAction(this.repository);

    @Test
    public void
    retrieve_an_auction_by_its_id_when_exists() {
        var expectedAuction = anAuction().build();
        when(this.repository.retrieveById(expectedAuction.id)).thenReturn(Optional.of(expectedAuction));

        var actualAuction = action.execute(new RetrieveAuctionCommand(expectedAuction.id));

        assertThat(actualAuction.isPresent()).isTrue();
        assertThatAuction(actualAuction.get()).isEqualTo(expectedAuction);
    }
    @Test
    public void
    not_retrieve_an_auction_by_its_id_when_it_does_not_exists() {
        when(this.repository.retrieveById(any())).thenReturn(Optional.empty());

        var actualAuction = action.execute(new RetrieveAuctionCommand("Non existing auction"));

        assertThat(actualAuction.isPresent()).isFalse();
    }

}