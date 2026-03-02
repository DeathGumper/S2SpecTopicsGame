from dataclasses import dataclass
from pydantic import BaseModel
from typing import List, Optional

from models.battle import Battle
from models.lobbySettings import LobbySettings
from models.player import Player

from enum import Enum

class LobbyStage(str, Enum):
    LOBBY = "LOBBY"
    BUYSTAGE = "BUYSTAGE"
    BATTLESTAGE = "BATTLESTAGE"

class LobbyState(BaseModel):
    name: str
    id: str
    players: list[Player] = None
    battles: Optional[List[Battle]] = None
    stage: LobbyStage
    stageTimer: float
    lobbySettings: LobbySettings
