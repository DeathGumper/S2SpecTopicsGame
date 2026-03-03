from dataclasses import dataclass
from pydantic import BaseModel

@dataclass
class ReadyUpPayload:
    lobbyId: str