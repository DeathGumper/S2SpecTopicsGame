
from pydantic import BaseModel
from typing import Optional
from models.creature import Creature

class Player(BaseModel):
    name: str
    id: str
    creatures: list[Optional[Creature]]
    activeCreatureIndex: int
    ready: bool
    owner: bool