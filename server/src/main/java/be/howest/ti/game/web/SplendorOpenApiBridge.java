package be.howest.ti.game.web;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.service.SplendorService;
import be.howest.ti.game.logic.service.SplendorServiceImpl;
import be.howest.ti.game.logic.utils.CardUtils;
import be.howest.ti.game.web.tokens.PlainTextTokens;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.request.*;
import be.howest.ti.game.web.views.response.*;

import java.util.*;
import java.util.function.Supplier;

public class SplendorOpenApiBridge extends OpenApiBridge { // NOSONAR this is not a monster class, it is a bridge :-)

    private final Supplier<SplendorService> serviceFactory;

    public SplendorOpenApiBridge() {
        this(SplendorServiceImpl::new, new PlainTextTokens());
    }

    // Factory needed to differentiate between group-tokens, can be simplified with a single service in the student version.
    SplendorOpenApiBridge(Supplier<SplendorService> serviceFactory, TokenManager tokenManager) {
        installPlayerTokenManager(tokenManager);
        this.serviceFactory = serviceFactory;
    }

    private final Map<String, SplendorService> services = new HashMap<>();

    private SplendorService getService(ContextBasedRequestView request) {
        return services.computeIfAbsent(request.getGroupSecret().toString(),
                k -> serviceFactory.get());
    }

    //region Generic Checks
    private static String ensureUrlSafe(String type, String txt) {
        if (!txt.matches("[a-zA-Z0-9]+")) { // letters and digits only or throw
            throw new IllegalArgumentException(type + " should be alphanumeric because it can be used in the url");
        }
        return txt;
    }
    //end region

    //region General operations
    /**
     *
     * Finished Endpoints
     * */
    @Operation("get-info")
    public GetInfoResponse getInfo(GetInfoRequest request) {
        return new GetInfoResponse();
    }

    @Operation("get-gems")
    public GetGemsResponse getGems(GetGemsRequest request) {
        return new GetGemsResponse();
    }

    @Operation("get-nobles")
    public GetNoblesResponse getNobles(GetNoblesRequest request) {
        return new GetNoblesResponse();
    }

    @Operation("get-developments")
    public GetDevelopmentsResponse getDevelopments(GetDevelopmentsRequest request) {
        return new GetDevelopmentsResponse();
    }

    //endregion

    //region Game Management operations
    /**
     * Finished Endpoints
     * */
    @Operation("get-games")
    public GetGamesResponse getGames(GetGamesRequest request) {
        SplendorService service = getService(request);

        List<Game> games = new ArrayList<>();
        try {
            if (request.getStarted()) {
                games = service.getStartedGames();
            } else if (!request.getStarted()) {
                games = service.getNonStartedGames();
            }
        } catch (NullPointerException e) {
            games = service.getAllGames();
        }

        return new GetGamesResponse(games);
    }

    @Operation("create-game")
    public CreateGameResponse createGame(CreateGameRequest request) {
        SplendorService service = getService(request);

        Game game;
        if (request.getGameName() == null) {
            game = service.createGame(
                    request.getNumberOfPlayers(),
                    new Player(request.getPlayerName())
            );
        } else {
            game = service.createGame(
                    request.getGameName(),
                    request.getNumberOfPlayers(),
                    new Player(request.getPlayerName())
            );
        }

        return new CreateGameResponse(game, request.getPlayerName());
    }

    @Operation("delete-games")
    public DeleteGamesResponse deleteGames(DeleteGamesRequest request) {
        SplendorService service = getService(request);

        List<Game> deletedGames = service.deleteAllGames();

        return new DeleteGamesResponse(deletedGames);
    }

    @Operation("get-game-details")
    public GetGameDetailsResponse getGameDetails(GetGameDetailsRequest request) {
        SplendorService service = getService(request);

        Game game = service.getGameByID(request.getGameID());

        return new GetGameDetailsResponse(game);
    }

    @Operation("join-game")
    public JoinGameResponse joinGame(JoinGameRequest request) {
        SplendorService service = getService(request);
        Game game = service.getGameByID(request.getGameId());

        if (game != null) {
            game.joinGame(request.getPlayerName());
        }

        return new JoinGameResponse(request.getGameId(), request.getPlayerName());
    }

    //endregion

    //region Game Action operations
    @Operation("update-tokens")
    public UpdateTokensResponse updateTokens(UpdateTokensRequest request) {
        return new UpdateTokensResponse();
    }

    @Operation("buy-development")
    public BuyDevelopmentResponse buyDevelopment(BuyDevelopmentRequest request) {
        SplendorService service = getService(request);

        Game game = service.buyDevelopment(
                request.getGameId(),
                request.getPlayerName(),
                request.getDevelopment(),
                false,
                request.getPayment()
        );

        return new BuyDevelopmentResponse(game);
    }

    @Operation("reserve-development")
    public NotYetImplementedResponse reserveDevelopment(BaseSplendorRequest request) {
        return new NotYetImplementedResponse("reserve-development");
    }

    @Operation("buy-reserved-development")
    public BuyReservedDevelopmentResponse buyReserveDevelopment(BuyReservedDevelopmentRequest request) {
        SplendorService service = getService(request);

        Game game = service.buyDevelopment(
                request.getGameId(),
                request.getPlayerName(),
                request.getDevelopment(),
                true,
                request.getPayment()
        );

        return new BuyReservedDevelopmentResponse(game);
    }

    @Operation("choose-noble")
    public ChooseNobleResponse chooseNoble(ChooseNobleRequest request) {
        SplendorService service = getService(request);

        Noble noble = service.chooseNoble(
                request.getPlayerName(),
                request.getGameID(),
                request.getNoble()
        );

        return new ChooseNobleResponse(noble);
    }
    //endregion


}
