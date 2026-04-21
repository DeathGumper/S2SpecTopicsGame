function TitleScreen({ setScene }) {
    return (
        <div className="title-screen">
            <h1>Title Screen</h1>
            <button onClick={() => setScene(ALLSCENES.MAINMENU)}>Start Game</button>
        </div>
    )
}