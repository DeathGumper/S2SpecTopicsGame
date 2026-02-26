"""
Docstring for frontend.code.main

Main entry point for frontend application.
Will initialize managers.
Will begin the main application loop.
"""

# Imports
import asyncio

import pygame
import pygame.time
import random

from controllers.gameController import gameController
from controllers.lobbyController import lobbyController
from managers.gameManager import GameManager
from managers.lobbyManager import LobbyManager

from utils.CONSTANTS import WINDOWED_SIZE, MENU_BACKGROUND_COLOR

# Init pygame
pygame.init()

def toggleFullscreen(window: pygame.Surface):
    # Toggle the fullscreen state
    if window.get_flags() & pygame.FULLSCREEN:
        window = pygame.display.set_mode(WINDOWED_SIZE)
    else:
        window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)

    return window

async def main():

    # Manager declarations
    gameManager = GameManager()
    lobbyManager = LobbyManager()

    bgColor = MENU_BACKGROUND_COLOR

    # Window declaration
    # window = pygame.display.set_mode((0, 0), pygame.FULLSCREEN)
    window = pygame.display.set_mode(WINDOWED_SIZE)

    # Clock
    clock = pygame.time.Clock()

    await gameController.connectWebsocket() # Connect to the server websocket to listen for game updates
    
    while True:
        window.fill(bgColor) # Clear the window with a background

        await gameController.update() # Check for updates from the server

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
                if event.key == pygame.K_p:
                    await gameController.sayHi()
                if event.key == pygame.K_F11:
                    window = toggleFullscreen(window)
                if event.key == pygame.K_EQUALS:
                    bgColor = (random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))
                if event.key == pygame.K_ESCAPE:
                    # Escape key = Quit pygame
                    pygame.quit()
                    return

        print(gameController.latestUpdate)
                
        # Loops
        gameManager.update()
                
        # Render game visuals
        gameManager.render(window)

        pygame.display.flip() # Update the display
                
        clock.tick(30)
        await asyncio.sleep(0) # Yield control to allow other tasks to run (like listening for server updates)

asyncio.run(main())