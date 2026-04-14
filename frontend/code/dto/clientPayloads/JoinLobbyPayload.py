from dataclasses import dataclass

@dataclass
class JoinLobbyPayload:
    playerName: str
    lobbyId: str