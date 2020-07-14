package com.codesai.auction_house.infrastructure.api;

import com.codesai.auction_house.business.actions.CreateAuctionCommand;
import com.codesai.auction_house.infrastructure.ActionFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.eclipse.jetty.http.HttpStatus.*;

public class AuctionAPI {
    public static Object createAuction(Request request, Response response) throws JSONException {
        try {
            var command = parseAuctionCommand(request);
            var auctionId = ActionFactory.createAuctionAction().execute(command);
            response.status(CREATED_201);
            response.header("Location", request.url() + "/auction/" + auctionId);
        } catch (JsonSyntaxException e) {
            response.status(BAD_REQUEST_400);
            return "The auction body is not well formed.";
        } catch (RuntimeException e) {
            response.status(UNPROCESSABLE_ENTITY_422);
            response.type("application/json");
            return new JSONObject()
                            .put("name", e.getClass().getSimpleName())
                            .put("description", e.getMessage())
                            .toString();
        }
        return "";
    }

    private static CreateAuctionCommand parseAuctionCommand(Request request) {
        try {
            var json = new Gson().fromJson(request.body(), JsonObject.class);
            return new CreateAuctionCommand(
                json.get("item").getAsJsonObject().get("name").getAsString(),
                json.get("item").getAsJsonObject().get("description").getAsString(),
                json.get("initial_bid").getAsDouble(),
                json.get("conquer_price").getAsDouble(),
                LocalDate.parse(json.get("expiration_date").getAsString()),
                json.get("owner_id").getAsString());
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }

    }
}
