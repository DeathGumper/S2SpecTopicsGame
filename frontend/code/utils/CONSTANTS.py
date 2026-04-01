import pygame

from utils.TestingBool import TestingBool

pygame.font.init()

SERVER_URL = 'wss://s2spectopicsserver-dtg3dac0h6d8b8hf.eastus-01.azurewebsites.net'
FONT = pygame.font.Font(None, 36) # Default font for rendering text in the game.

WINDOWED_SIZE = (800, 600)

DEFAULT_BUTTON_COLOR = (200, 200, 200)

MENU_BACKGROUND_COLOR = (255, 255, 255)

TESTING = TestingBool(False)