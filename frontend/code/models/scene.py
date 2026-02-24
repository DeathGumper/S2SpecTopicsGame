import pygame
from typing import Callable

from services.uiService import UIService
from services.inputBoxService import InputBoxService

class Scene:
    name = "scene"
    buttons = None
    inputBoxHandler = None
    objects = None
    hasSetup = False

    def __init__(self, name: str):
        self.name = name
        self.buttons = []
        self.inputBoxHandler = InputBoxService()
        self.objects = []
        pass
    
    def checkInput(self):
        for button in self.buttons:
            button.checkClick()

        self.inputBoxHandler.handleInputBoxes()

    # Button that has an action associated with it ex: navigation between scenes
    def makeActionButton(self, label: str, action, position: tuple, size: tuple=None, asyncAction: bool=False):
        self.buttons.append(UIService.makeButton(label, action, position, size, asyncAction=asyncAction))
    
    def makeInputBox(self, position: tuple, size: tuple, color: tuple=None, defaultText: str=None):
        return self.inputBoxHandler.addNew(position, size, color, defaultText)

    def render(self, surface: pygame.Surface):
        for button in self.buttons:
            button.render(surface)
        self.inputBoxHandler.render(surface)