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

class GameController:
        
    async def startGame(self):
        await websocketConnection.sendMessage(ClientMessage(type="START_GAME", payload=StartGamePayload(CurrentLobbyStateHandler.lobbyState.id)))

    async def readyUp(self):
        print("Ready up called")
        await websocketConnection.sendMessage(ClientMessage(type="READY_UP", payload=ReadyUpPayload(CurrentLobbyStateHandler.lobbyState.id)))

    async def endBattleStage(self):
        await websocketConnection.sendMessage(ClientMessage(type="END_BATTLES", payload=CurrentLobbyStateHandler.lobbyState.id))

    async def buyCreature(self):
        await websocketConnection.sendMessage(ClientMessage(type="BUY_RANDOM_CREATURE", payload=None))

    async def callAction(self, action: str):
        await websocketConnection.sendMessage(ClientMessage(type="CALL_ACTION", payload=ActionPayload(action)))

gameController = GameController()
        

         
        # try:
        #     with urllib.request.urlopen(SERVER_URL + "/lobby/startGame/" + lobbyController.lobbyState.id + "/" + lobbyController.player.id) as response:
        #         if 200 <= response.code < 300:
        #             responseLoaded = json.loads(response.read().decode('utf-8'))
        #             print(responseLoaded)
        #             return responseLoaded
        #         return response.code
        # except urllib.error.HTTPError as e:
        #     print(f"HTTP Error: Status code {e.code} - {e.reason}")
        #     return False
        # except Exception as e:
        #     print(str(e))
        #     return False