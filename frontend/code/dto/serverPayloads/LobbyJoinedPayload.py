from dataclasses import dataclass
from pydantic import BaseModel

from models.lobbyState import LobbyState

class LobbyJoinedPayload(BaseModel):
    lobbyState: LobbyState
    playerId: str