from dataclasses import dataclass
from pydantic import BaseModel

from models.lobbyState import LobbyState

class ResultsStageStartedPayload(BaseModel):
    lobbyState: LobbyState