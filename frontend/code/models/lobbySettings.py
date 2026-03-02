from pydantic import BaseModel

class LobbySettings(BaseModel):
    buyStageTimer: float
    turnTimer: float
    maxPlayers: int