"""
Docstring for dto.lobbyingDto

helps transfer the data from the server to the client, this will hold lobbyState and the Player
"""

from models.lobbyState import LobbyState
from models.player import Player

class LobbyingDto:
    lobbyState: LobbyState = None
    player: Player = None

    def __init__(self, lobbyState: LobbyState, player: Player):
        self.lobbyState = lobbyState
        self.player = player