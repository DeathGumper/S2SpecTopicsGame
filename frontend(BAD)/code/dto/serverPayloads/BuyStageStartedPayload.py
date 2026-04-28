from dataclasses import dataclass

from models.lobbyState import LobbyState


@dataclass
class BuyStageStartedPayload:
    lobbyState: LobbyState
