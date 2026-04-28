import pygame
import copy
from visuals.visual import Visual
from utils.CONSTANTS import FONT, DEFAULT_BUTTON_COLOR

class GameObject(Visual):
    def __init__(self, name: str, position: tuple, sprite: pygame.Surface, nameLabel: bool):
        self.name = name
        self.sprite = sprite
        self.text = None

        if nameLabel:
            self.setNewText(name)

        self.rect = pygame.Rect(position, self.sprite.get_size())

    def __deepcopy__(self, memo):
        # 1. Create a new instance of the class
        cls = self.__class__
        result = cls.__new__(cls)
        memo[id(self)] = result

        # 2. Manually copy each attribute
        for key, value in self.__dict__.items():
            if isinstance(value, pygame.Surface):
                # Use Pygame's native copy for surfaces
                setattr(result, key, value.copy())
            else:
                # Use standard deepcopy for everything else
                setattr(result, key, copy.deepcopy(value, memo))
        
        return result

    # Dynamic changing of position.
    def setPosition(self, position: tuple):
        self.rect.center = position

    def setSprite(self, sprite: pygame.Surface):
        self.sprite = sprite

    def setNewText(self, text: str):
        self.text = FONT.render(text, True, (0, 0, 0))
    
    # Dynamic changing of action.
    def setAction(self, action: callable):
        self.action = action

    def render(self, surface: pygame.Surface):
        surface.blit(self.sprite, self.rect)
        if (self.text):
            surface.blit(self.text, self.text.get_rect(center=(self.rect.centerx, self.rect.centery - 80)))