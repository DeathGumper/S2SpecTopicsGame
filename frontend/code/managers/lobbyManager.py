"""
Docstring for frontend.code.managers.lobbyManager

Manager for calling server related services based on lobby events.

Ex: Player pressed "Join Lobby" button.
"""
import pygame
from controllers.lobbyController import lobbyController

class lobbyManager:
    lobby_controller = None
    def __init__(self):
        self.lobby_controller = lobbyController()