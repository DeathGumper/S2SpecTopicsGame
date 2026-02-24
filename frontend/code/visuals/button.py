import pygame
from visuals.clickable import Clickable
from utils.CONSTANTS import FONT, DEFAULT_BUTTON_COLOR

"""

Docstring for frontend.code.visuals.button

Button class that inherits from Clickable. Represents a clickable button in the game UI.

"""

class Button(Clickable):
    text = None
    rect = None

    def __init__(self, label: str, action: callable, position: tuple=(0, 0), size: tuple=None, color: tuple=DEFAULT_BUTTON_COLOR, asyncAction: bool=False):
        # Text on the button
        self.label = label
        self.color = color
        self.setupText()
        if size == None:
            size = (self.text.get_width() + 10, self.text.get_height() + 10) # Default size based on text size

        self.rect = pygame.Rect(position[0], position[1], size[0], size[1]) # Default button size

        super().__init__(self.rect, action, asyncAction=asyncAction) # Call the base Clickable constructor with the button's rect and action

    def click(self):
        super().click() # Call the base click method to execute the action

    # Initialize the text.
    def setupText(self):
        self.text = FONT.render(self.label, True, (0, 0, 0)) # Render the button label as text

    # Dynamic changing of position.
    def setPosition(self, position: tuple):
        self.rect.center = position
    
    # Dynamic changing of action.
    def setAction(self, action: callable):
        self.action = action

    def render(self, surface: pygame.Surface):
        if self.hover:
            tempColor = (self.color[0] * .8, self.color[1] * .8, self.color[2] * .8) # Darker color on hover
        else:
            tempColor = self.color

        if self.clicked:
            tempColor = (self.color[0] * .6, self.color[1] * .6, self.color[2] * .6) # Even darker color when clicked
            
        # Draw the button and text
        pygame.draw.rect(surface, tempColor, self.rect, border_radius=5) # Draw the button background with rounded corners
        surface.blit(self.text, self.text.get_rect(center=self.rect.center)) # Draw the button label centered on the button
