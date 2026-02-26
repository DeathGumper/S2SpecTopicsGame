import pygame

from visuals.button import Button
from visuals.inputBox import InputBox

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