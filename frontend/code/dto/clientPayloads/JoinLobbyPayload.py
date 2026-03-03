from dataclasses import dataclass
from pydantic import BaseModel

@dataclass
class JoinLobbyPayload:
    playerName: str
    lobbyId: str