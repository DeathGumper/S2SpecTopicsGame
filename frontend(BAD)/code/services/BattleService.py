from models.battle import Battle

class BattleService:

    @staticmethod
    def getBattleById(battles: list[Battle], id: str) -> Battle:
        for battle in battles:
            if battle.id == id:
                return battle
        
        return None