function HealthBar({ creatureData }) {
    const percentage = (creatureData.stats.health / creatureData.stats.maxHealth) * 100;
    return (
        <div style={{ width: '100%', backgroundColor: '#e0e0df', borderRadius: '5px' }}>
            <div style={{
                width: `${percentage}%`,
                backgroundColor: percentage > 20 ? '#76ff03' : '#ff1744', // Color change on low health
                height: '20px',
                borderRadius: 'inherit',
                transition: 'width 0.3s ease-in-out' // Smooth transitions
            }} />
        </div>
    )
}

export default HealthBar;