import pygame
import time

from utils.CONSTANTS import DEFAULT_BUTTON_COLOR, FONT
from visuals.clickable import Clickable

class InputBox(Clickable):
    defaultText = "Input"

    def __init__(self, position: tuple, size: tuple, color: tuple=None, defaultText: str=None):
        self.inputText = ""
        if color == None:
            self.color = DEFAULT_BUTTON_COLOR
        else:
            self.color = color
        if defaultText != None:
            self.defaultText = defaultText

        self.lastPress = None
        self.inputText = ""
        
        self.rect = pygame.Rect(position[0], position[1], size[0], size[1])


        super().__init__(self.rect, None)

    def click(self):
        pass

    def removeLetter(self):
        self.inputText = self.inputText[:-1]

    def addLetter(self, letter: str):
        self.inputText += letter

    def checkTypingInput(self):
        keys = pygame.key.get_pressed()
        for key_index, v in enumerate(keys):
            if v:
                print(key_index)
                keyname = pygame.key.name(key_index)
                print(keyname)
                if keyname == "backspace":
                    self.inputText = self.inputText[:-1]
                else:
                    self.inputText += keyname

        """for event in events:
            if event.type == pygame.KEYDOWN:
                char = event.unicode

                print(char)

                if event.key == pygame.K_BACKSPACE:
                    self.inputText = self.inputText[:-1]
                elif char:
                    self.inputText += char"""

    def render(self, surface: pygame.Surface):
        if self.hover:
            tempColor = (self.color[0] * .8, self.color[1] * .8, self.color[2] * .8) # Darker color on hover
        else:
            tempColor = self.color

        if self.clicked:
            tempColor = (self.color[0] * .6, self.color[1] * .6, self.color[2] * .6) # Even darker color when clicked
            
        text = FONT.render(self.inputText if self.inputText != "" else self.defaultText, True, (0, 0, 0) if self.inputText != "" else (50, 50, 50))
        # Draw the button and text
        pygame.draw.rect(surface, tempColor, self.rect, border_radius=5) # Draw the button background with rounded corners
        surface.blit(text, text.get_rect(center=self.rect.center)) # Draw the button label centered on the button