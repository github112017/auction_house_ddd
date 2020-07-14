package auction_house.unit;

import com.codesai.auction_house.business.actions.CreateAuctionAction;
import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.AuctionRepository;
import com.codesai.auction_house.business.model.auction.exceptions.InitialBidIsGreaterThanConquerPrice;
import com.codesai.auction_house.business.model.generic.Money;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.UUID;

import static com.codesai.auction_house.business.model.auction.Auction.anAuction;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class CreateAuctionActionShould {

    final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    final String ANY_DESCRIPTION = "AnyDescription" + UUID.randomUUID();
    final double ANY_INITIAL_BID_AMOUNT = 50.0;
    final double ANY_CONQUER_PRICE_AMOUNT = 100.0;
    final LocalDate ANY_EXPIRATION_DAY = now();
    final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID();

    final AuctionRepository repository = mock(AuctionRepository.class);
    final CreateAuctionAction action = new CreateAuctionAction(repository);
    private final ArgumentCaptor<Auction> captor = ArgumentCaptor.forClass(Auction.class);

    @Test
    public void
    create_an_auction() {
        var expectedAuction = anAuction(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                Money.of(ANY_INITIAL_BID_AMOUNT),
                Money.of(ANY_CONQUER_PRICE_AMOUNT),
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID);

        action.execute(new CreateAuctionCommand(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_INITIAL_BID_AMOUNT,
                ANY_CONQUER_PRICE_AMOUNT,
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID
        ));


        verify(repository, times(1)).save(captor.capture());
        assertDoesNotThrow(() -> UUID.fromString(captor.getValue().id));
        assertThat(captor.getValue().name).isEqualTo(expectedAuction.name);
        assertThat(captor.getValue().description).isEqualTo(expectedAuction.description);
        assertThat(captor.getValue().initialBid).isEqualTo(expectedAuction.initialBid);
        assertThat(captor.getValue().conquerPrice).isEqualTo(expectedAuction.conquerPrice);
        assertThat(captor.getValue().expirationDay).isEqualTo(expectedAuction.expirationDay);
        assertThat(captor.getValue().ownerId).isEqualTo(expectedAuction.ownerId);
    }

    @Test
    public void
    throws_an_exception_when_creating_an_auction_with_initial_price_greater_than_conquer_price() {
        assertThatThrownBy(() -> action.execute(new CreateAuctionCommand(
                ANY_ITEM_NAME,
                ANY_DESCRIPTION,
                ANY_CONQUER_PRICE_AMOUNT + 1,
                ANY_CONQUER_PRICE_AMOUNT,
                ANY_EXPIRATION_DAY,
                ANY_OWNER_ID
        ))).isInstanceOf(InitialBidIsGreaterThanConquerPrice.class);
        verify(repository, times(0)).save(any());
    }

}

