from dataclasses import dataclass

from models.creature import Creature


@dataclass
class Player:
    name: str
    id: str
    creatures: list
    activeCreatureIndex: int
    ready: bool
    owner: bool

    @classmethod
    def from_dict(cls, d):
        return cls(
            name=d['name'],
            id=d['id'],
            creatures=[Creature.from_dict(c) if c else None for c in d['creatures']],
            activeCreatureIndex=d['activeCreatureIndex'],
            ready=d['ready'],
            owner=d['owner'],
        )
