from dataclasses import dataclass
from pydantic import BaseModel

from models.lobbyState import LobbyState

class BattlesStartedPayload(BaseModel):
    lobbyState: LobbyState
    battleId: str