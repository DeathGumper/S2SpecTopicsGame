import pygame
from typing import Callable

from services.uiService import UIService

class Scene:
    name = "scene"
    buttons = None
    inputBoxes = None
    objects = None
    hasSetup = False

    def __init__(self, name: str):
        self.name = name
        self.buttons = []
        self.inputBoxes = []
        self.objects = []
        pass
    
    def checkInput(self):
        for button in self.buttons:
            button.check_click()

    # Button that has an action associated with it ex: navigation between scenes
    def makeActionButton(self, label: str, action, position: tuple, size: tuple=None):
        self.buttons.append(UIService.makeButton(label, action, position, size))

    def render(self, surface: pygame.Surface):
        for button in self.buttons:
            button.render(surface)
        for inputBox in self.inputBoxes:
            inputBox.render(surface)