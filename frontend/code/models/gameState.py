"""
Docstring for frontend.code.models.gameState

Core state of the game. Will hold all relevant information regarding the current state of the game; turn, players in current battle, etc. 
This will basically only be constructed by the server
"""

class gameState:
    turn = None
    players = None
    creatureOrder = None