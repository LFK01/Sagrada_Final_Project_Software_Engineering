package it.polimi.se2018.utils;

import it.polimi.se2018.model.events.messages.ToolCardActivationMessage;
import it.polimi.se2018.model.events.messages.*;
import it.polimi.se2018.model.events.messages.ChooseDiceMessage;
import it.polimi.se2018.model.events.messages.NoActionMessage;
import it.polimi.se2018.model.events.messages.ChooseToolCardMessage;

public interface ProjectObserver {

    void update(ChooseDiceMessage chooseDiceMessage);

    void update(ChooseSchemaMessage chooseSchemaMessage);

    void update(ComebackMessage comebackMessage);

    void update(CreatePlayerMessage createPlayerMessage);

    void update(DiePlacementMessage diePlacementMessage);

    void update(ErrorMessage errorMessage);

    void update(NoActionMessage noActionMessage);

    void update(RequestMessage requestMessage);

    void update(SelectedSchemaMessage selectedSchemaMessage);

    void update(SendGameboardMessage sendGameboardMessage);

    void update(ShowPrivateObjectiveCardsMessage showPrivateObjectiveCardsMessage);

    void update(SuccessCreatePlayerMessage successCreatePlayerMessage);

    void update(ToolCardActivationMessage toolCardActivationMessage);

    void update(ToolCardErrorMessage toolCardErrorMessage);

    void update(ChooseToolCardMessage chooseToolCardMessage);

    void update(SendWinnerMessage sendWinnerMessage);

    @Override
    String toString();
}
