import websockets
import asyncio

import json

from dto.clientMessage import ClientMessage
from dto.serverMessage import ServerMessage
from dto.serverPayloads.LobbyJoinedPayload import LobbyJoinedPayload
from dto.serverPayloads.BuyStageStartedPayload import BuyStageStartedPayload
from dto.serverPayloads.BattlesStartedPayload import BattlesStartedPayload
from dto.serverPayloads.ResultsStageStartedPayload import ResultsStageStartedPayload
from models.lobbyState import LobbyState
from dataclasses import asdict
from models.currentLobbyStateHandler import CurrentLobbyStateHandler
from models.currentBattleStateHandler import CurrentBattleStateHandle
from services.BattleService import BattleService

class WebsocketConnection:
    def __init__(self):
        self.websocket = None
        self.recvTask = None
        self.latestUpdate = None
        self.playerId = None

    async def sendMessage(self, message: ClientMessage):
        if not self.websocket:
            print("❌ WebSocket not connected.")
            return

        try:
            await self.websocket.send(json.dumps(asdict(message)))
            print(f"✅ Sent message: {message}")

        except Exception as e:
            print(f"❌ Failed to send message: {e}")

    async def connectWebsocket(self):
        uri = "ws://localhost:8080/websocket"
        try:
            # The async with statement handles connection and cleanup automatically
            self.websocket = await websockets.connect(uri)
            self.recvTask = asyncio.create_task(self.websocket.recv())

            print("✅ Connected to server websocket for game updates.")

        except ConnectionRefusedError:
            print("❌ Could not connect to server. Is the server running?")
        except Exception as e:
            print(f"An error occurred: {e}")

    async def update(self):
        if not self.recvTask:
            return

        # ONLY continue if message is ready
        if not self.recvTask.done():
            return

        # If the task finished, check if it errored
        if self.recvTask.exception():
            print("❌ Recv error:", self.recvTask.exception())
            return

        try:
            message = self.recvTask.result()
            jsonMessage = json.loads(message)
            serverMessage = ServerMessage(**jsonMessage)


            if (serverMessage.type == "WEBSOCKET_CONNECTED"):
                """The only thing in the payload should be success message"""
                print(serverMessage.payload)

            elif (serverMessage.type == "LOBBY_JOINED"):
                """Here the payload has the lobbyState and the playerId"""
                payload = LobbyJoinedPayload.model_validate(serverMessage.payload)
                lobbyState = LobbyState.model_validate(payload.lobbyState)
                self.playerId = payload.playerId

                CurrentLobbyStateHandler.lobbyState = lobbyState

            elif (serverMessage.type == "BUYSTAGE_STARTED"):
                """Here the only thing in the payload is the lobbyState"""
                payload = BuyStageStartedPayload.model_validate(serverMessage.payload)
                CurrentLobbyStateHandler.lobbyState = LobbyState.model_validate(payload.lobbyState)

            elif (serverMessage.type == "BATTLES_STARTED"):
                """The payload will have the lobbyState and the battle this client is in"""
                payload = BattlesStartedPayload.model_validate(serverMessage.payload)
                print(payload)
                CurrentLobbyStateHandler.lobbyState = LobbyState.model_validate(payload.lobbyState)
                CurrentBattleStateHandle.battle = BattleService.getBattleById(CurrentLobbyStateHandler.lobbyState.battles, payload.battleId)

            elif (serverMessage.type == "RESULTSSTAGE_STARTED"):
                """This just has the lobbyState"""
                payload = ResultsStageStartedPayload.model_validate(serverMessage.payload)
                CurrentLobbyStateHandler.lobbyState = LobbyState.model_validate(payload.lobbyState)

            else:
                print("Type " + serverMessage.type + " was not recognized.")

            self.latestUpdate = message

            # Start listening again
            self.recvTask = asyncio.create_task(self.websocket.recv())

        except websockets.ConnectionClosed:
            print("⚠️ WebSocket closed.")
            self.recvTask = asyncio.create_task(self.websocket.recv())
            return

        except Exception as e:
            print("Unexpected error:", e)
            self.recvTask = asyncio.create_task(self.websocket.recv())
            return
        


websocketConnection = WebsocketConnection()