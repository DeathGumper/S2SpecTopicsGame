from dataclasses import dataclass
from models.creature import Creature

@dataclass
class Player:
    name: str
    id: str #UUID from backend
    creatures: list