import pygame

from visuals.button import Button

class UIService:
    def __init__(self):
        pass

    @staticmethod
    def makeButton(label, action, position=(0, 0), size=None):
        button = Button(label, action, position, size)
        return button