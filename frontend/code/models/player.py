
from pydantic import BaseModel
from typing import Optional
from models.creature import Creature

class Player(BaseModel):
    name: str
    id: str #UUID from backend
    creatures: list[Optional[Creature]]
    ready: bool
    owner: bool