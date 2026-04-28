import { useState, useEffect } from 'react'

function StageTimer({ serverTime }) {
    const [time, setTime] = useState(serverTime)

    // Correct to server time whenever an update arrives
    useEffect(() => {
        setTime(serverTime)
    }, [serverTime])

    // Count down using actual elapsed time so drift doesn't accumulate
    useEffect(() => {
        let lastTick = Date.now()
        const interval = setInterval(() => {
            const now = Date.now()
            const elapsed = (now - lastTick) / 1000
            lastTick = now
            setTime(t => Math.max(0, t - elapsed))
        }, 100)
        return () => clearInterval(interval)
    }, [])

    return (
        <div className="stage-timer">
            <h2>{Math.ceil(time)}</h2>
        </div>
    )
}

export default StageTimer
