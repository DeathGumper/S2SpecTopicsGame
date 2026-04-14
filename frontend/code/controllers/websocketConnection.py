import json
import sys

from dto.clientMessage import ClientMessage
from models.lobbyState import LobbyState
from dataclasses import asdict
from models.currentLobbyStateHandler import CurrentLobbyStateHandler
from models.currentBattleStateHandler import CurrentBattleStateHandle
from services.BattleService import BattleService

from utils.CONSTANTS import SERVER_URL, TESTING

IN_BROWSER = sys.platform == 'emscripten'

if not IN_BROWSER:
    import websockets
    import asyncio


class WebsocketConnection:
    def __init__(self):
        self.websocket = None
        self._queue = []      # used in browser mode
        self._recvTask = None # used in native mode

    async def sendMessage(self, message: ClientMessage):
        if not self.websocket:
            print("WebSocket not connected.")
            return

        try:
            payload = json.dumps(asdict(message))
            if IN_BROWSER:
                self.websocket.send(payload)
            else:
                await self.websocket.send(payload)
            print("Sent message: " + str(message))

        except Exception as e:
            print("Failed to send message: " + str(e))

    async def connectWebsocket(self):
        uri = "ws://localhost:8080/websocket"
        if not TESTING.get():
            uri = SERVER_URL + "/websocket"
        try:
            if IN_BROWSER:
                import platform
                self.websocket = platform.window.WebSocket.new(uri)
                self.websocket.onopen = lambda e: print("Connected to server websocket.")
                self.websocket.onmessage = lambda e: self._queue.append(str(e.data))
                self.websocket.onclose = lambda e: print("WebSocket closed.")
                self.websocket.onerror = lambda e: print("WebSocket error.")
            else:
                self.websocket = await websockets.connect(uri)
                self._recvTask = asyncio.create_task(self.websocket.recv())
                print("Connected to server websocket.")

        except Exception as e:
            print("An error occurred: " + str(e))

    async def update(self):
        if IN_BROWSER:
            if not self._queue:
                return
            message = self._queue.pop(0)
        else:
            if not self._recvTask or not self._recvTask.done():
                return
            if self._recvTask.exception():
                print("Recv error:", self._recvTask.exception())
                self._recvTask = asyncio.create_task(self.websocket.recv())
                return
            message = self._recvTask.result()
            self._recvTask = asyncio.create_task(self.websocket.recv())

        try:
            jsonMessage = json.loads(message)
            msgType = jsonMessage['type']
            payload = jsonMessage.get('payload')

            if msgType == "WEBSOCKET_CONNECTED":
                print(payload)

            elif msgType == "LOBBY_JOINED":
                CurrentLobbyStateHandler.lobbyState = LobbyState.from_dict(payload['lobbyState'])
                CurrentLobbyStateHandler.playerId = payload['playerId']

            elif msgType == "BUYSTAGE_STARTED":
                CurrentLobbyStateHandler.lobbyState = LobbyState.from_dict(payload['lobbyState'])

            elif msgType == "BATTLES_STARTED":
                print(payload)
                CurrentLobbyStateHandler.lobbyState = LobbyState.from_dict(payload['lobbyState'])
                CurrentBattleStateHandle.battle = BattleService.getBattleById(
                    CurrentLobbyStateHandler.lobbyState.battles, payload['battleId']
                )

            elif msgType == "RESULTSSTAGE_STARTED":
                CurrentLobbyStateHandler.lobbyState = LobbyState.from_dict(payload['lobbyState'])

            elif msgType == "UPDATE":
                CurrentLobbyStateHandler.lobbyState = LobbyState.from_dict(payload)
                print("UPDATEEEDDDDD")

            else:
                print("Type " + msgType + " was not recognized.")

        except Exception as e:
            print("Unexpected error: " + str(e))


websocketConnection = WebsocketConnection()
