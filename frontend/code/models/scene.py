import pygame
from typing import Callable

from services.uiService import UIService
from services.inputBoxService import InputBoxService

class Scene:
    name = "scene"
    buttons = None
    inputBoxHandler = None
    gameObjects = None

    def __init__(self, name: str):
        self.name = name
        self.hasSetup = False
        self.buttons = []
        self.inputBoxHandler = InputBoxService()
        self.gameObjects = []
        pass
    
    def checkInput(self):
        for button in self.buttons:
            button.checkClick()

        self.inputBoxHandler.handleInputBoxes()

    def getObjectByName(self):
        pass

    # Button that has an action associated with it ex: navigation between scenes
    def makeActionButton(self, label: str, action, position: tuple, size: tuple=None, name: str=None):
        self.buttons.append(UIService.makeButton(label, action, position, size, name))
    
    def makeInputBox(self, name: str, position: tuple, size: tuple, color: tuple=None, defaultText: str=None):
        self.inputBoxHandler.addNew(name, position, size, color, defaultText)
    
    def makeGameObject(self, name, position, size, color, nameLabel: bool=False):
        self.gameObjects.append(UIService.makeGameObject(name, position, size, color, nameLabel))

    def getGameObjectByName(self, name):
        for go in self.gameObjects:
            if go.name == name:
                return go
        
        print("unable to find gameobject by name " + name)
        return None
    
    def getButtonByName(self, name):
        for button in self.buttons:
            if button.name == name:
                return button
            
        return None

    def getInputBoxByName(self, name):
        inputBox = self.inputBoxHandler.getInputBoxByName(name)
        return inputBox

    def render(self, surface: pygame.Surface):
        for button in self.buttons:
            button.render(surface)
        self.inputBoxHandler.render(surface)

        for gameObject in self.gameObjects:
            gameObject.render(surface)