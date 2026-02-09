"""
Docstring for frontend.code.main

Main entry point for frontend application.
Will initialize managers.
Will begin the main application loop.
"""

# Imports
import pygame

from controllers.gameController import gameController
from controllers.lobbyController import lobbyController
from managers.gameManager import gameManager
from managers.lobbyManager import lobbyManager

# Init pygame
pygame.init()

def main():
    # Controller declarations
    lobby_controller = lobbyController()
    game_controller = gameController(lobby_controller)

    # Manager declarations
    game_manager = gameManager(game_controller, lobby_controller)
    lobby_manager = lobbyManager(game_controller, lobby_controller)

    # Window declaration
    # window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)
    window = pygame.display.set_mode((800, 600))
    print("App Started")
    
    while True:
        window.fill((255, 255, 255)) # Clear the window with a white background

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
                
        # Loops
        game_manager.loop()
                
        # Render game visuals
        game_manager.render(window)

        pygame.display.flip() # Update the display
                

main()