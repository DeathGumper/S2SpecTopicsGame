from dataclasses import dataclass

@dataclass
class Creature:
    name: str
    stats: object
    totalSpeed: float