"""
Docstring for frontend.code.managers.gameManager

Manager for calling game related services based on server events.

Ex: Calling Service due to server saying enemy creature attacked.
"""
import pygame
from controllers.gameController import gameController
from services.uiService import UIService


class gameManager:
    # Controllers
    game_controller = None
    lobby_controller = None

    # Services
    uiService = None

    def __init__(self, game_controller, lobby_controller):
        # Controller declarations
        self.game_controller = game_controller
        self.lobby_controller = lobby_controller

        # Service declarations
        self.uiService = UIService()

        player = None

        # Temp of random buttons
        self.uiService.makeButton("Make creature", lambda: self.spawnCreature("fireslime"), position=(200, 200))
        self.uiService.makeButton("Make lobby", lambda: self.lobby_controller.createLobby(input("id? "), input("name? ")), position=(400, 300))
        self.uiService.makeButton("Join lobby", lambda: self.lobby_controller.joinLobby(input("id? "), input("name? ")), position=(400, 400))

    def spawnCreature(self, creature_type):
        creature = self.game_controller.spawnCreature(creature_type)

    def loop(self):
        self.uiService.checkClicks()

    def render(self, surface: pygame.Surface):
        self.uiService.render(surface)
