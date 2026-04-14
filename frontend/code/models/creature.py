from dataclasses import dataclass

from models.stats import Stats


@dataclass
class Creature:
    name: str
    stats: Stats
    totalSpeed: float

    @classmethod
    def from_dict(cls, d):
        return cls(
            name=d['name'],
            stats=Stats.from_dict(d['stats']),
            totalSpeed=d['totalSpeed'],
        )
