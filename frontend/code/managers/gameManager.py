"""
Docstring for frontend.code.managers.gameManager

Manager for calling game related services based on server events.

Ex: Calling Service due to server saying enemy creature attacked.
"""
import pygame
from controllers.gameController import gameController
from controllers.lobbyController import lobbyController
from models.currentLobbyStateHandler import CurrentLobbyStateHandler
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

    def update(self):
        self.sceneService.update()

        lobbyState = CurrentLobbyStateHandler.lobbyState    
            
        if (lobbyState == None):
            return
        
        print(lobbyState.stage)
        
        if (lobbyState.stage == "LOBBY"):
            if (self.sceneService.currentScene.name != "lobby"):
                self.sceneService.switchScene("lobby")
        
        if (lobbyState.stage == "BUYSTAGE"):
            print("buy stage time")
            if (self.sceneService.currentScene.name != "buystage"):
                self.sceneService.switchScene("buystage")
        
        if (lobbyState.stage == "BATTLESTAGE"):
            if (self.sceneService.currentScene.name != "battlestage"):
                self.sceneService.switchScene("battlestage")

    def render(self, surface: pygame.Surface):
        self.sceneService.render(surface)