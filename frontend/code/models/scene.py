import pygame
from typing import Callable

from services.uiService import UIService
from services.inputBoxService import InputBoxService

class Scene:
    name = "scene"
    buttons = None
    inputBoxHandler = None
    gameObjects = None
    hasSetup = False

    def __init__(self, name: str):
        self.name = name
        self.buttons = []
        self.inputBoxHandler = InputBoxService()
        self.gameObjects = []
        pass
    
    def checkInput(self):
        for button in self.buttons:
            button.checkClick()

        self.inputBoxHandler.handleInputBoxes()

    # Button that has an action associated with it ex: navigation between scenes
    def makeActionButton(self, label: str, action, position: tuple, size: tuple=None):
        self.buttons.append(UIService.makeButton(label, action, position, size))
    
    def makeInputBox(self, position: tuple, size: tuple, color: tuple=None, defaultText: str=None):
        return self.inputBoxHandler.addNew(position, size, color, defaultText)
    
    def makeGameObject(self, name, position, size, color):
        self.gameObjects.append(UIService.makeGameObject(name, position, size, color))

    def getGameObjectByName(self, name):
        for go in self.gameObjects:
            if go.name == name:
                return go
        
        return None

    def render(self, surface: pygame.Surface):
        for button in self.buttons:
            button.render(surface)
        self.inputBoxHandler.render(surface)

        for gameObject in self.gameObjects:
            gameObject.render(surface)