package it.polimi.se2018.utils;

import it.polimi.se2018.model.events.messages.*;

public interface ProjectObserver {

    void update(Message message);

    void update(ChooseSchemaMessage chooseSchemaMessage);

    void update(ComebackSocketMessage comebackSocketMessage);

    void update(CreatePlayerMessage createPlayerMessage);

    void update(DemandSchemaCardMessage message);

    void update(ErrorMessage errorMessage);

    void update(NewRoundMessage newRoundMessage);

    void update(SelectedSchemaMessage selectedSchemaMessage);

    void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage);

    void update(SuccessCreatePlayerMessage successCreatePlayerMessage);

    void update(SuccessMoveMessage successMoveMessage);

    void update(UpdateTurnMessage updateTurnMessage);

    void update(GameInitializationMessage gameInitializationMessage);

}
