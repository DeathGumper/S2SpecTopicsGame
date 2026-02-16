"""
Docstring for frontend.code.main

Main entry point for frontend application.
Will initialize managers.
Will begin the main application loop.
"""

# Imports
import pygame
import pygame.time

from controllers.gameController import GameController
from controllers.lobbyController import LobbyController
from managers.gameManager import GameManager
from managers.lobbyManager import LobbyManager

from utils.CONSTANTS import WINDOWED_SIZE

# Init pygame
pygame.init()

def toggleFullscreen(window: pygame.Surface):
    # Toggle the fullscreen state
    if window.get_flags() & pygame.FULLSCREEN:
        window = pygame.display.set_mode(WINDOWED_SIZE)
    else:
        window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)

    return window

def main():

    # Manager declarations
    gameManager = GameManager()
    lobbyManager = LobbyManager()

    # Window declaration
    # window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)
    window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)
    print("App Started")

    # Clock
    clock = pygame.time.Clock()
    
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
                # Send the key pressed info to the inputboxservice (Temporary)
                """ I DO NOT LIKE THIS SOLUTION, this is the only solution to the problem i can figure out, i need to try to understand it more """
                gameManager.sceneService.currentScene.inputBoxHandler.keyPressed(event.key)
                print(pygame.key.name(event.key))
                if event.key == pygame.K_f:
                    window = toggleFullscreen(window)
                if event.key == pygame.K_ESCAPE:
                    # Escape key = Quit pygame
                    pygame.quit()
                    return

                
        # Loops
        gameManager.loop()
                
        # Render game visuals
        gameManager.render(window)

        pygame.display.flip() # Update the display
                
        clock.tick(30)
main()