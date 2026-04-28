from dataclasses import dataclass

from models.lobbyState import LobbyState


@dataclass
class LobbyJoinedPayload:
    lobbyState: LobbyState
    playerId: str
