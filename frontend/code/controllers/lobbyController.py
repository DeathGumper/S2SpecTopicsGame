"""
Docstring for frontend.code.controllers.lobbyController

Controller for communicating with the server regarding lobby actions. 

Ex: Joining/Creating a lobby
"""
from dto.clientPayloads.CreateLobbyPayload import CreateLobbyPayload
from dto.clientPayloads.JoinLobbyPayload import JoinLobbyPayload
from utils.CONSTANTS import SERVER_URL
from models.player import Player
from controllers.websocketConnection import websocketConnection
from dto.clientMessage import ClientMessage
import json

class LobbyController:
    async def createLobby(self, playerName: str, lobbyId: str):
        print(playerName + " : " + lobbyId)
        await websocketConnection.sendMessage(ClientMessage(type="CREATE_LOBBY", payload=CreateLobbyPayload(playerName, lobbyId)))

    async def joinLobby(self, playerName: str, lobbyId: str):
        await websocketConnection.sendMessage(ClientMessage(type="JOIN_LOBBY", payload=JoinLobbyPayload(playerName, lobbyId)))
            
lobbyController = LobbyController()

# async def createLobby(self, playerName: str, lobbyId: str):
    #     try:
    #         with urllib.request.urlopen(SERVER_URL + "/lobby/create/" + lobbyId + "/" + playerName) as response:
    #             if 200 <= response.code < 300:
    #                 dataDict = json.loads(response.read().decode('utf-8'))
    #                 lobbyingDto = from_dict(data_class=LobbyingDto, data=dataDict)
    #                 self.lobbyState = lobbyingDto.lobbyState
    #                 self.player = lobbyingDto.player

    #                 print(lobbyingDto)
    #                 print(self.lobbyState)
    #                 print(self.player)


    #                 await gameController.connectWebsocket() # Connect to the server websocket to listen for game updates
    #                 return True
    #             return response.code
    #     except urllib.error.HTTPError as e:
    #         print(f"HTTP Error: Status code {e.code} - {e.reason}")
    #         return False
    #     except Exception as e: 
    #         print(str(e))
    #         return False
    
    # async def joinLobby(self, playerName: str, lobbyId: str):
    #     try:
    #         with urllib.request.urlopen(SERVER_URL + "/lobby/join/" + lobbyId + "/" + playerName) as response:
    #             if 200 <= response.code < 300:
    #                 dataDict = json.loads(response.read().decode('utf-8'))
    #                 lobbyingDto = from_dict(data_class=LobbyingDto, data=dataDict)
    #                 self.lobbyState = lobbyingDto.lobbyState
    #                 self.player = lobbyingDto.player

    #                 print(lobbyingDto)
    #                 print(self.lobbyState)
    #                 print(self.player)
    #                 return True
    #             return response.code
    #     except urllib.error.HTTPError as e:
    #         print(f"HTTP Error: Status code {e.code} - {e.reason}")
    #         return False
    #     except Exception as e:
    #         print(str(e))
    #         return False