from dataclasses import dataclass

from models.lobbySettings import LobbySettings

@dataclass
class LobbyState:
    name: str
    id: str
    owner: object # Player object but i dont wanna import the class, just extra clutter
    players: list
    gameState: str
    stageTimer: float
    lobbySettings: LobbySettings