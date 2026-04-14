from dataclasses import dataclass

from models.battle import Battle
from models.lobbySettings import LobbySettings
from models.player import Player


@dataclass
class LobbyState:
    name: str
    id: str
    players: list
    battles: list
    stage: str
    stageTimer: float
    lobbySettings: LobbySettings

    @classmethod
    def from_dict(cls, d):
        return cls(
            name=d['name'],
            id=d['id'],
            players=[Player.from_dict(p) for p in (d.get('players') or [])],
            battles=[Battle.from_dict(b) for b in (d.get('battles') or [])],
            stage=d['stage'],
            stageTimer=d['stageTimer'],
            lobbySettings=LobbySettings.from_dict(d['lobbySettings']),
        )
