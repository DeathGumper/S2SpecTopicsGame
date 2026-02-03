"""
Docstring for frontend.code.managers.gameManager

Manager for calling game related services based on server events.

Ex: Calling Service due to server saying enemy creature attacked.
"""
import pygame
from controllers import gameController


class gameManager:
    game_controller = None
    def __init__(self):
        self.game_controller = gameController()
