from pydantic import BaseModel

class Stats(BaseModel):
    maxHealth: float
    health: float
    strength: float
    defense: float
    dexterity: float
    speed: float
    accuracy: float