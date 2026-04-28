from dataclasses import dataclass


@dataclass
class LobbySettings:
    buyStageTimer: float
    turnTimer: float
    maxPlayers: int

    @classmethod
    def from_dict(cls, d):
        return cls(
            buyStageTimer=d['buyStageTimer'],
            turnTimer=d['turnTimer'],
            maxPlayers=d['maxPlayers'],
        )
