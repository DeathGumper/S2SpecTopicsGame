from pydantic import BaseModel

from models.stats import Stats


class Creature(BaseModel):
    name: str
    stats: Stats
    totalSpeed: float