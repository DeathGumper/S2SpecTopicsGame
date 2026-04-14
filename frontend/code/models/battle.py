from dataclasses import dataclass


@dataclass
class Battle:
    id: str
    player1: object  # Player object but i dont wanna import the class, just extra
    player2: object  # Player object but i dont wanna import the class, just extra
    state: str

    @classmethod
    def from_dict(cls, d):
        return cls(
            id=d['id'],
            player1=d['player1'],
            player2=d['player2'],
            state=d['state'],
        )
