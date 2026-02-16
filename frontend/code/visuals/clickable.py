import pygame

class Clickable:
    clickType = "up" # Default is "up"; when the mouse is released. "down" would be when the mouse is pressed down.
    def __init__(self, rect: pygame.Rect, action: callable):
        self.rect = rect
        self.action = action
        self.hover = False
        self.clicked = False
        self.wasClickedOnLastClick = False

    def checkClick(self):
        mouse_pos = pygame.mouse.get_pos()
        if self.hover == True and not pygame.mouse.get_pressed()[0] and self.clicked == True: # If we were hovering and the mouse button is released, reset clicked state
            self.clicked = False
            if self.clickType == "up":
                self.click() # Call the click method to execute the action
        
        # If clicking
        if pygame.mouse.get_pressed()[0]:
            self.wasClickedOnLastClick = False

        self.hover = False
        if self.rect.collidepoint(mouse_pos):
            self.hover = True
            if pygame.mouse.get_pressed()[0]: # Left mouse button
                self.clicked = True
                self.wasClickedOnLastClick = True
                if self.clickType == "down":
                    self.click()

    def click(self):
        self.action()