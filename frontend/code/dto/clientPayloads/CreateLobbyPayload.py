from dataclasses import dataclass
from pydantic import BaseModel

@dataclass
class CreateLobbyPayload:
    playerName: str
    lobbyId: str