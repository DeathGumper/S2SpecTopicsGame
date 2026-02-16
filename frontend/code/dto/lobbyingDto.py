"""
Docstring for dto.lobbyingDto

helps transfer the data from the server to the client, this will hold lobbyState and the Player
"""

from models.lobbyState import LobbyState
from models.player import Player
from dataclasses import dataclass

@dataclass
class LobbyingDto:
    lobbyState: LobbyState
    player: Player