from dataclasses import dataclass
from models.player import Player


@dataclass
class Battle:
    id: str
    player1: Player  # Player object but i dont wanna import the class, just extra
    player2: Player  # Player object but i dont wanna import the class, just extra
    state: str

    @classmethod
    def from_dict(cls, d):
        return cls(
            id=d['id'],
            player1=Player.from_dict(d['player1']),
            player2=Player.from_dict(d['player2']),
            state=d['state'],
        )
