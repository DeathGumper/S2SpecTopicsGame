from dataclasses import dataclass

from models.lobbyState import LobbyState


@dataclass
class ResultsStageStartedPayload:
    lobbyState: LobbyState
