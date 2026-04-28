import pygame

from utils.TestingBool import TestingBool

pygame.font.init()

SERVER_URL = 'wss://s2project.azurewebsites.net' # URL of the server for API calls.
FONT = pygame.font.Font(None, 36) # Default font for rendering text in the game.

WINDOWED_SIZE = (800, 600)

DEFAULT_BUTTON_COLOR = (200, 200, 200)

MENU_BACKGROUND_COLOR = (255, 255, 255)

TESTING = TestingBool(False)