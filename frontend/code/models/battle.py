from pydantic import BaseModel


class Battle(BaseModel):
    id: str
    player1: object # Player object but i dont wanna import the class, just extra
    player2: object # Player object but i dont wanna import the class, just extra

    state: str