package it.polimi.se2018.utils;

import it.polimi.se2018.controller.exceptions.InvalidCellPositionException;
import it.polimi.se2018.controller.exceptions.InvalidDraftPoolPosException;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;

public interface ProjectObserver {

    void update(Message message);

    void update(ChooseSchemaMessage chooseSchemaMessage);

    void update(ComebackSocketMessage comebackSocketMessage);

    void update(CreatePlayerMessage createPlayerMessage);

    void update(ErrorMessage errorMessage);

    void update(GameInitializationMessage gameInitializationMessage);

    void update(NewRoundMessage newRoundMessage);

    void update(SelectedSchemaMessage selectedSchemaMessage);

    void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage);

    void update(SuccessCreatePlayerMessage successCreatePlayerMessage);

    void update(SuccessMoveMessage successMoveMessage);

    void update(UpdateTurnMessage updateTurnMessage);

    void update(ChooseDiceMove chooseDiceMove) throws InvalidCellPositionException,InvalidDraftPoolPosException;

    void update(NoActionMove noActionMove);



}
