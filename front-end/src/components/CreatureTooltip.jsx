import '../styles/CreatureTooltipStyles.css'
// IMPORTANT!
// THIS IS UNUSED, I turned this off beacuse it was causing too many bugs and was unnecessary.


// A little dictionary of sorts to get the info based on the attack.
function describeAction(actionStr) {
    const [action, value] = actionStr.split('-')
    switch (action) {
        case 'DamageEnemy': return `Deals damage  (power: ${value})`
        case 'Heal':        return `Restores ${value} HP`
        case 'Stun':        return `Applies ${value} stun stacks`
        case 'Ignite':      return `Applies ${value} burn stacks`
        case 'Poison':      return `Applies ${value} poison stacks`
        default:            return actionStr
    }
}

function parseAbility(actionString) {
    if (!actionString) return []
    return actionString.split('|').map(describeAction)
}


// React class.
function CreatureTooltip({ creature }) {
    // Get the abilities and stats
    var abilities = creature.abilities
    var stats = creature.stats
    // If no abilites, then return
    if (!abilities || Object.keys(abilities).length === 0) return null

    return (
        <div className="creature-tooltip">
            <h3 className="tooltip-heading">Abilities</h3>
            <div className="tooltip-abilities">
                {Object.entries(abilities).map(([name, actionString]) => (
                    <div key={name} className="tooltip-ability">
                        <span className="ability-name">{name}</span>
                        <ul className="ability-effects">
                            {parseAbility(actionString).map((desc, i) => (
                                <li key={i}>{desc}</li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
            <h3 className="tooltip-heading">
                Stats
            </h3>
        </div>
    )
}

export default CreatureTooltip
