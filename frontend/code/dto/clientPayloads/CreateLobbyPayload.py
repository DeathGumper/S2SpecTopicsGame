from dataclasses import dataclass

@dataclass
class CreateLobbyPayload:
    playerName: str
    lobbyId: str