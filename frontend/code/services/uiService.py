import pygame

from visuals.button import Button
from visuals.inputBox import InputBox
from visuals.gameObject import GameObject

class UIService:
    def __init__(self):
        pass

    @staticmethod
    def makeButton(label, action, position=(0, 0), size=None):
        button = Button(label, action, position, size)
        return button
    
    @staticmethod
    def makeInputBox(position: tuple, size: tuple, color: tuple=None, defaultText: str=None):
        inputBox = InputBox(position, size, color, defaultText)
        return inputBox
    
    @staticmethod
    def makeGameObject(name: str, position=(0, 0), size=(100, 100), color=(255, 0, 0)):
        # TODO: make it not a box at all times
        sprite = pygame.Surface(size)
        sprite.fill(color)

        gameObject = GameObject(name, position, sprite)

        return gameObject