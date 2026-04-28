from dataclasses import dataclass

from models.lobbyState import LobbyState


@dataclass
class BattlesStartedPayload:
    lobbyState: LobbyState
    battleId: str
