"""
Docstring for frontend.code.managers.gameManager

Manager for calling game related services based on server events.

Ex: Calling Service due to server saying enemy creature attacked.
"""
import pygame
from controllers.actionController import actionController
from controllers.gameController import gameController
from controllers.lobbyController import lobbyController
from models.battle import Battle
from models.currentLobbyStateHandler import CurrentLobbyStateHandler
from models.player import Player
from services.sceneService import SceneService
from visuals.gameObject import GameObject


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
        player = None
        opponent = None
        battle = None

        if (lobbyState == None):
            return
        
        readyCounter: int = 0
        playerCount: int = 0
        for p in lobbyState.players:
            playerCount += 1
            if p.ready:
                readyCounter += 1
            if p.id == CurrentLobbyStateHandler.playerId:
                player = p

        for b in lobbyState.battles:
            if b.player1.id == player.id:
                opponent = b.player2
                battle = b
            elif b.player2.id == player.id:
                opponent = b.player1
                battle = b
        
        if (lobbyState.stage == "LOBBY"):
            if (self.sceneService.currentScene.name != "lobby"):
                self.sceneService.switchScene("lobby")

            readyCounterButton = self.sceneService.currentScene.getButtonByName("readyCounterButton")
            if (readyCounterButton != None):
                readyCounterButton.setLabel(str(readyCounter) + "/" + str(playerCount))
        
        if (lobbyState.stage == "BUYSTAGE"):
            if (self.sceneService.currentScene.name != "buystage"):
                self.sceneService.switchScene("buystage")
            
            if (player != None):
            
                for i, creature in enumerate(player.creatures):
                    # TODO: display the actual creature sprite
                    creatureGo: GameObject = self.sceneService.currentScene.getGameObjectByName("Creature" + str(i+1))

                    if creature != None:
                        # Temp
                        sprite = pygame.Surface((75, 75))
                        sprite.fill((0, 255, 0))

                        creatureGo.setSprite(sprite)
        
        if (lobbyState.stage == "BATTLESTAGE"):
            if (self.sceneService.currentScene.name != "battlestage"):
                self.sceneService.switchScene("battlestage")
                
            self.updateBattleStage(player, opponent)

        if (lobbyState.stage == "RESULTSSTAGE"):
            if (self.sceneService.currentScene.name != "resultsstage"):
                self.sceneService.switchScene("resultsstage")

    def updateBattleStage(self, player: Player, opponent: Player):
        currentScene = self.sceneService.currentScene
        currentScene.getGameObjectByName("friendlycreature").setNewText(player.creatures[player.activeCreatureIndex].name + "\nHP: " + str(player.creatures[player.activeCreatureIndex].stats.health))
        currentScene.getGameObjectByName("enemyCreature").setNewText(opponent.creatures[opponent.activeCreatureIndex].name + "\nHP: " + str(opponent.creatures[opponent.activeCreatureIndex].stats.health))

        actionController.setActions(list(player.creatures[player.activeCreatureIndex].abilities.values()))

    def render(self, surface: pygame.Surface):
        self.sceneService.render(surface)