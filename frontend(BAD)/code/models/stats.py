from dataclasses import dataclass


@dataclass
class Stats:
    maxHealth: float
    health: float
    strength: float
    defense: float
    dexterity: float
    speed: float
    accuracy: float

    @classmethod
    def from_dict(cls, d):
        return cls(
            maxHealth=d['maxHealth'],
            health=d['health'],
            strength=d['strength'],
            defense=d['defense'],
            dexterity=d['dexterity'],
            speed=d['speed'],
            accuracy=d['accuracy'],
        )
