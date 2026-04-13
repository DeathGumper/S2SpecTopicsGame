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
        lobbyScene = Scene("lobby") # Scene showing all players in lobby and settings for lobby if you created it
        buyStageScene = Scene("buystage") # Buying new creatures and gear
        battleStageScene = Scene("battlestage") # Battling opponent
        resultsStageScene = Scene("resultsstage") # End of battle results

        # Scene storage
        self.scenes.extend([mainMenuScene, lobbyingScene, lobbyScene, buyStageScene, battleStageScene, resultsStageScene])

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
        lobbyingScene.makeInputBox("nameInput", (400, 100), (100, 40), defaultText="Name?")

        # Id input box
        lobbyingScene.makeInputBox("idInput", (400, 200), (100, 40), defaultText="Id?")
        
        # Join Lobby and then go to lobby scene if successful
        lobbyingScene.makeActionButton("Join", lambda: asyncio.create_task(lobbyController.joinLobby(
            lobbyingScene.getInputBoxByName("nameInput").getInputText(), 
            lobbyingScene.getInputBoxByName("idInput").getInputText())), (100, 100))

        # Create Lobby and then go to lobby scene if successful
        lobbyingScene.makeActionButton("Create", lambda: asyncio.create_task(lobbyController.createLobby(
            lobbyingScene.getInputBoxByName("nameInput").getInputText(), 
            lobbyingScene.getInputBoxByName("idInput").getInputText())), (100, 200))

    def lobbySetup(self):
        # Get the lobby scene
        lobbyScene = self.getSceneByName("lobby")

        # Start Game and then go to buy stage
        lobbyScene.makeActionButton("Start Game!", lambda: asyncio.create_task(gameController.startGame()), (100, 100))

        # lobbyScene.makeActionButton("0/0", lambda: print("Hi"), (500, 100), (40, 40), "readyCounterButton")

    def buystageSetup(self):
        buystageScene = self.getSceneByName("buystage")

        # temporary
        # Make a button that displays its the buystage
        buystageScene.makeActionButton("Buy Stage!", lambda: print("You happen to be in the buy stage rn"), (10, 10))

        buystageScene.makeActionButton("Ready Up!", lambda: asyncio.create_task(gameController.readyUp()), (100, 100))

        # temporary
        # Buys a random creature and puts it in your creature list
        buystageScene.makeActionButton("Buy Creature", lambda: asyncio.create_task(gameController.buyCreature()), (100, 200))

        for i in range(1, 6):
            buystageScene.makeGameObject("Creature" + str(i), (75 + (100 * (i-1)), 400), (75, 75), (200, 200, 0), True)

    def battlestageSetup(self):
        battlestageScene = self.getSceneByName("battlestage")

        battlestageScene.makeGameObject("friendlycreature", (500, 300), (200, 200), (0, 0, 255))

        battlestageScene.makeGameObject("enemyCreature", (100, 100), (100, 100), (255, 0, 0))

        battlestageScene.makeActionButton("Battle Stage!", lambda: print("You happen to be in the battle stage rn"), (10, 10))

        battlestageScene.makeActionButton("End Battles!", lambda: asyncio.create_task(gameController.endBattleStage()), (100, 400))

        battlestageScene.makeActionButton("Kill Enemy Creature", lambda: asyncio.create_task(gameController.callAction("KillCreature")), (400, 400))

    def resultsstageSetup(self):
        resultsstageScene = self.getSceneByName("resultsstage")

        resultsstageScene.makeActionButton("Results Stage!", lambda: print("You happen to be in the results stage rn"), (10, 10))

    # Get scene by name (name is lowercase scene name without "scene" ex: BuyStageScene -> "buystage")
    def getSceneByName(self, name: str) -> Scene:
        for scene in self.scenes:
            if scene.name == name:
                return scene

        return None

    # Switch to different scene by name
    def switchScene(self, newSceneName: str):
        newScene = self.getSceneByName(newSceneName)

        if newScene == None:
            print("failed to find scene for " + newSceneName)
            return
        
        if not newScene.hasSetup:
            setupFunc = getattr(self, newSceneName + "Setup", None)
            if setupFunc:
                setupFunc()
                newScene.hasSetup = True
            else:
                print("failed to find setup function for " + newSceneName)

        self.currentScene = newScene

    # Called every frame
    def update(self):
        self.currentScene.checkInput()

    # Called every frame
    def render(self, surface: pygame.Surface):
        self.currentScene.render(surface)