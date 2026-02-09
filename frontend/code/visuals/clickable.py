import pygame

class Clickable:
    clickType = "up" # Default is "up"; when the mouse is released. "down" would be when the mouse is pressed down.
    def __init__(self, rect: pygame.Rect, action: callable):
        self.rect = rect
        self.action = action
        self.hover = False
        self.clicked = False

    def check_click(self):
        mouse_pos = pygame.mouse.get_pos()
        if self.hover == True and not pygame.mouse.get_pressed()[0] and self.clicked == True: # If we were hovering and the mouse button is released, reset clicked state
            self.clicked = False
            self.click() # Call the click method to execute the action
        self.hover = False
        if self.rect.collidepoint(mouse_pos):
            self.hover = True
            if pygame.mouse.get_pressed()[0]: # Left mouse button
                self.clicked = True
                if self.clickType == "down":
                    self.click()

    def click(self):
        self.action()