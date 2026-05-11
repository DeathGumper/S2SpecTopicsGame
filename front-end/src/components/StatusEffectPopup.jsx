import '../styles/StatusEffectPopupStyles.css'

function StatusEffectPopup({ statusName, message }) {
    return (
        <div className="popup">
            <h1>
                { statusName }
            </h1>
            <p>
                { message }
            </p>
        </div>
    )
}

export default StatusEffectPopup
