"""
Docstring for frontend.code.main

Main entry point for frontend application.
Will initialize managers.
Will begin the main application loop.
"""

# Imports
import pygame

from managers.gameManager import gameManager
from managers.lobbyManager import lobbyManager

# Init pygame
pygame.init()

def main():
    # Manager declarations
    game_manager = gameManager()
    lobby_manager = lobbyManager()

    # Window declaration
    window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)
    
    while True:
        # Event handling
        for event in pygame.event.get():
            # Exit conditions
            if event.type == pygame.QUIT:
                # Quit pygame
                pygame.quit()
                return
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    # Escape key = Quit pygame
                    pygame.quit()
                    return
        

main()