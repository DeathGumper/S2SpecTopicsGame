"""
Docstring for frontend.code.managers.lobbyManager

Manager for calling server related services based on lobby events.

Ex: Player pressed "Join Lobby" button.
"""
import pygame
from controllers.lobbyController import lobbyController

class lobbyManager:
    lobby_controller = None
    game_controller = None
    
    def __init__(self, game_controller, lobby_controller):
        self.game_controller = game_controller
        self.lobby_controller = lobby_controller