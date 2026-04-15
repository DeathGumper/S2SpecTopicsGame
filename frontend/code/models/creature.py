from dataclasses import dataclass

from models.stats import Stats


@dataclass
class Creature:
    name: str
    stats: Stats
    totalSpeed: float
    abilities: object = None # dictionary of abilities, display name: id

    @classmethod
    def from_dict(cls, d):
        return cls(
            name=d['name'],
            stats=Stats.from_dict(d['stats']),
            abilities=d.get('abilities') or {},
            totalSpeed=d['totalSpeed'],
        )
