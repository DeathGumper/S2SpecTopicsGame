import pygame
from visuals.clickable import Clickable
from utils.CONSTANTS import FONT, DEFAULT_BUTTON_COLOR

"""

Docstring for frontend.code.visuals.button

Button class that inherits from Clickable. Represents a clickable button in the game UI.

"""

class GameObject:
    def __init__(self, name: str, position: tuple, sprite: pygame.Surface):
        self.name = name
        self.sprite = sprite

        self.rect = pygame.Rect(position, self.sprite.get_size())
        
    # Dynamic changing of position.
    def setPosition(self, position: tuple):
        self.rect.center = position
    
    # Dynamic changing of action.
    def setAction(self, action: callable):
        self.action = action

    def render(self, surface: pygame.Surface):
        surface.blit(self.sprite, self.rect)