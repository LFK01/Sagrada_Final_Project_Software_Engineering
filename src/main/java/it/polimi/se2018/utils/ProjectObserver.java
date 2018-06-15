package it.polimi.se2018.utils;

import it.polimi.se2018.model.events.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.moves.ChooseDiceMove;
import it.polimi.se2018.model.events.moves.NoActionMove;
import it.polimi.se2018.model.events.moves.UseToolCardMove;

public interface ProjectObserver {

    void update(Message message);

    void update(ChooseDiceMove chooseDiceMove);

    void update(ChooseSchemaMessage chooseSchemaMessage);

    void update(ComebackMessage comebackMessage);

    void update(ComebackSocketMessage comebackSocketMessage);

    void update(CreatePlayerMessage createPlayerMessage);

    void update(DiePlacementMessage diePlacementMessage);

    void update(ErrorMessage errorMessage);

    void update(GameInitializationMessage gameInitializationMessage);

    void update(NewRoundMessage newRoundMessage);

    void update(NoActionMove noActionMove);

    void update(RequestMessage requestMessage);

    void update(SelectedSchemaMessage selectedSchemaMessage);

    void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage);

    void update(SuccessMessage successMessage);

    void update(SuccessCreatePlayerMessage successCreatePlayerMessage);

    void update(SuccessMoveMessage successMoveMessage);

    void update(ToolCardActivationMessage toolCardActivationMessage);

    void update(ToolCardErrorMessage toolCardErrorMessage);

    void update(UpdateTurnMessage updateTurnMessage);

    void update(UseToolCardMove useToolCardMove);
}
