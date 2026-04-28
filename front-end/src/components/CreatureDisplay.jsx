import { gameController } from '../controllers/GameController'

import '../styles/CreatureDisplayStyles.css'

function CreatureDisplay({ creatureData,  turn, image }) {
    var percentage = (creatureData.stats.health / creatureData.stats.maxHealth) * 100;
    return (
        <div className={turn ? "creature-card glow-border" : "creature-card"}>
            <h2 className="creature-name">{creatureData.name}</h2>
            {image && <img src={image} alt={creatureData.name} className="creature-image" />}
            <div className="creature-details">
                <p>Health: {creatureData.stats.health}/{creatureData.stats.maxHealth}</p>
                <div className="status-effect-container">
                    {creatureData.stats.stun > 0 && <div className="status-effect stun-effect">{creatureData.stats.stun}</div>}
                    {creatureData.stats.burn > 0 && <div className="status-effect fire-effect">{creatureData.stats.burn}</div>}
                    {creatureData.stats.poison > 0 && <div className="status-effect poison-effect">{creatureData.stats.poison}</div>}
                </div>
            </div>
        </div>
    )
}

export default CreatureDisplay