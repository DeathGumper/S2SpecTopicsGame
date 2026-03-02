from pydantic import BaseModel

class Stats(BaseModel):
    health: float
    strength: float
    dexterity: float
    speed: float