import pygame
import asyncio
from controllers.gameController import gameController
from models.scene import Scene

from controllers.lobbyController import lobbyController

class SceneService:
    scenes = []
    currentScene = None

    def __init__(self):
        # Scene declarations
        mainMenuScene = Scene("mainmenu") # Main menu
        lobbyingScene = Scene("lobbying") # Choosing wether to join or create a lobby
        buyStageScene = Scene("buystage") # Buying new creatures and gear
        lobbyScene = Scene("lobby") # Scene showing all players in lobby and settings for lobby if you created it
        gameScene = Scene("game") # Battling opponent
        endScene = Scene("end") # End of battle results

        # Scene storage
        self.scenes.extend([mainMenuScene, lobbyingScene, lobbyScene, buyStageScene, gameScene, endScene])

        # Default starting scene
        self.switchScene("mainmenu")

    # Individual Scene Setup
    def mainmenuSetup(self):
        # Get the main menu scene
        mainMenuSceen = self.getSceneByName("mainmenu")

        # Play (this will make the player) and then go to the lobbying menu
        mainMenuSceen.makeActionButton("Play", lambda: self.switchScene("lobbying"), (100, 100))
        # mainMenuSceen.makeActionButton("Say Hi", lambda: asyncio.run(gameController.sayHi()), (400, 100))

    def lobbyingSetup(self):
        # Get the lobbying scene
        lobbyingScene = self.getSceneByName("lobbying")

        # Name input box
        nameInput = lobbyingScene.makeInputBox((400, 100), (100, 40), defaultText="Name?")

        # Id input box
        idInput = lobbyingScene.makeInputBox((400, 200), (100, 40), defaultText="Id?")
        
        # Join Lobby and then go to lobby scene if successful
        lobbyingScene.makeActionButton("Join", lambda: lobbyController.joinLobby(nameInput.inputText, idInput.inputText) and self.switchScene("lobby"), (100, 100))

        # Create Lobby and then go to lobby scene if successful
        lobbyingScene.makeActionButton("Create", lambda: lobbyController.createLobby(nameInput.inputText, idInput.inputText) and self.switchScene("lobby"), (100, 200))

    def lobbySetup(self):
        # Get the lobby scene
        lobbyScene = self.getSceneByName("lobby")

        # Start Game and then go to buy stage
        lobbyScene.makeActionButton("Start Game!", lambda: gameController.readyUp(), (100, 100))

    def buystageSetup(self):
        pass

    # Get scene by name (name is lowercase scene name without "scene" ex: BuyStageScene -> "buystage")
    def getSceneByName(self, name: str) -> Scene:
        for scene in self.scenes:
            if scene.name == name:
                return scene

        return None

    # Switch to different scene by name
    def switchScene(self, newSceneName: str):
        newScene = self.getSceneByName(newSceneName)
        if newScene:
            self.currentScene = newScene
        else:
            print("failed to find scene for " + newSceneName)
            return
        
        if self.currentScene.hasSetup == False:
            # Get the scene setup by name string
            setupFunc = getattr(self, newSceneName + "Setup")
            if setupFunc:
                setupFunc()
            else:
                print("failed to find setup function for " + newSceneName)

        print(self.currentScene.name)

    # Called every frame
    def update(self):
        self.currentScene.checkInput()

    # Called every frame
    def render(self, surface: pygame.Surface):
        self.currentScene.render(surface)