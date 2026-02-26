"""
Docstring for frontend.code.managers.gameManager

Manager for calling game related services based on server events.

Ex: Calling Service due to server saying enemy creature attacked.
"""
import pygame
from controllers.gameController import gameController
from controllers.lobbyController import lobbyController
from services.sceneService import SceneService


class GameManager:

    # Services
    sceneService = None

    def __init__(self):

        # Service declarations
        self.sceneService = SceneService()

        # Temp of random buttons
        # self.uiService.makeButton("Make creature", lambda: self.spawnCreature("fireslime"), position=(200, 200))
        # self.uiService.makeButton("Make lobby", lambda: self.lobby_controller.createLobby(input("id? "), input("name? ")), position=(400, 300))

    # Temporary
    def spawnCreature(self, creature_type):
        creature = self.game_controller.spawnCreature(creature_type)

    def update(self):
        self.sceneService.update()

    def render(self, surface: pygame.Surface):
        self.sceneService.render(surface)