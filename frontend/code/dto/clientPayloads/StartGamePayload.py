from dataclasses import dataclass
from pydantic import BaseModel

@dataclass
class StartGamePayload:
    lobbyId: str

    