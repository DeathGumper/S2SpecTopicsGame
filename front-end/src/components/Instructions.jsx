import { useState, useEffect } from 'react'
import { ALLSCENES } from '../helpers/constants'
import '../styles/InstructionsStyles.css'

const slides = [
    {
        title: 'The Lobby',
        body: 'Create a new lobby or join one using a Lobby ID. Enter your username and wait for other players to join. The host can start the game once everyone is ready. You can see all players in the player list on the left.'
    },
    {
        title: 'Buy Stage',
        body: 'You have money! Each creature costs money so spend your money wisely. Press Ready Up when you are ready!'
    },
    {
        title: 'Your Creatures',
        body: 'Each creature has a unique name and a set of stats: Health, Strength, Defense, Dexterity, Speed, and Accuracy. Speed determines turn order — faster creatures act first. Your team is shown at the bottom of the screen during the Buy Stage.'
    },
    {
        title: 'Creature Stats',
        body: 'Health — how much damage a creature can take before fainting. Strength — how hard it hits. Defense — how much damage it absorbs. Dexterity — affects dodge chance. Accuracy — chance to land hits. Speed — determines who goes first each turn.'
    },
    {
        title: 'Status Effects',
        body: 'Creatures can be afflicted with status effects during battle. Burn (🔴) deals damage at the start of each creatures turn. Stun (⚪) has a random chance, based on how many stacks, to skip your turn at the start of it. Poison (🟢) amplifies incoming damage. Status badges appear on the creature card when active.'
    },
    {
        title: 'Battle Stage',
        icon: '⚡',
        body: 'Battles are one-on-one and turn-based. When it\'s your turn, pick an ability from the panel at the bottom. Each creature has unique abilities. Watch the health bars — green means healthy, red means critical. A glowing border shows whose turn it is.'
    },
    {
        title: 'Lives & Elimination',
        body: 'Every player starts with a set number of lives. When all of your active creature\'s health reaches zero you lose the battle and a life. Lose all your lives and you are eliminated from the game. Lives remaining are shown in the Buy Stage.'
    },
    {
        title: 'How to Win',
        body: 'After each round of battles, surviving players move on to buy more creatures and fight again. The last player with lives remaining wins the game. Build a well-rounded team, pick your abilities wisely, and outlast everyone else. Good luck!'
    }
]

function Instructions({ setScene }) {
    const [current, setCurrent] = useState(0)
    const [direction, setDirection] = useState(null)
    const [animating, setAnimating] = useState(false)
    const [displayed, setDisplayed] = useState(0)

    const navigate = (dir) => {
        if (animating) return
        const next = current + dir
        if (next < 0 || next >= slides.length) return

        setDirection(dir)
        setAnimating(true)

        setTimeout(() => {
            setCurrent(next)
            setDisplayed(next)
            setDirection(null)
            setAnimating(false)
        }, 300)
    }

    const slide = slides[displayed]

    return (
        <div id="instructions">
            <button className="back-button" onClick={() => setScene(ALLSCENES.MAINMENU)}>
                <span className="back-arrow">←</span>
                <span className="back-text">Back</span>
            </button>

            <h1 className="title">How to Play</h1>

            <div id="instructions-container">
                <button
                    className="nav-arrow left-arrow menu-button glow-text-hover"
                    onClick={() => navigate(-1)}
                    disabled={current === 0}
                >
                    ‹
                </button>

                <div
                    id="slide-window"
                >
                    <div
                        className={`slide ${animating ? (direction > 0 ? 'exit-left' : 'exit-right') : 'enter'}`}
                    >
                        <h2 className="slide-title">{slide.title}</h2>
                        <p className="slide-body">{slide.body}</p>
                    </div>
                </div>

                <button
                    className="nav-arrow right-arrow menu-button glow-text-hover"
                    onClick={() => navigate(1)}
                    disabled={current === slides.length - 1}
                >
                    ›
                </button>
            </div>

            <div id="slide-dots">
                {slides.map((_, i) => (
                    <span
                        key={i}
                        className={`dot ${i === current ? 'dot-active' : ''}`}
                        onClick={() => {
                            if (!animating && i !== current) {
                                navigate(i > current ? 1 : -1)
                            }
                        }}
                    />
                ))}
            </div>

            <p id="slide-counter">{current + 1} / {slides.length}</p>
        </div>
    )
}

export default Instructions
