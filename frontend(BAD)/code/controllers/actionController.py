"""
Docstring for frontend.code.controllers.gameController

Controller for communicating with the server regarding game actions. 

Ex: Player attack enemy creature.
"""
import pygame
from dto.clientMessage import ClientMessage
from dto.clientPayloads.StartGamePayload import StartGamePayload
from dto.clientPayloads.ReadyUpPayload import ReadyUpPayload
from dto.clientPayloads.ActionPayload import ActionPayload
from models.creature import Creature
from utils.CONSTANTS import SERVER_URL
from controllers.lobbyController import lobbyController
from controllers.websocketConnection import websocketConnection
from models.currentLobbyStateHandler import CurrentLobbyStateHandler

import json

class ActionController:
    def __init__(self):
        self.actions = []

    def setActions(self, actions: list[str]):
        self.actions = actions
        print("Set actions: " + str(actions))
        
    async def callActionByIndex(self, index: int):
        print("Actions: " + str(self.actions))
        if (index >= len(self.actions)):
            print("Invalid action index.")
            return
        
        print("Calling action: " + self.actions[index])
        await websocketConnection.sendMessage(ClientMessage(type="CALL_ACTION", payload=ActionPayload(self.actions[index])))

actionController = ActionController()
        