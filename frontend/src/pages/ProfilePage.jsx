import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../services/axios';

function ProfilePage() {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

const handleStartMatch = async () => {
  try {
    setLoading(true);

    const response = await axios.post('/match/search');
    const data = response.data;

    const matchId = data.matchId || data.matchID || data.match?.id || data.id;
    const startCellId = data.id;
    const cellNum = data.cellNum;
    const cellType = data.cellType;

    if (!matchId || !startCellId || !cellNum || !cellType) {
      alert("❌ Не получены все данные стартовой клетки");
      return;
    }

    localStorage.setItem('matchId', matchId);
    localStorage.setItem('startCellId', startCellId);
    localStorage.setItem('cellNum', cellNum);
    localStorage.setItem('cellType', JSON.stringify(cellType));

    navigate(`/match/${matchId}`);
  } catch (err) {
    console.error("Ошибка при старте матча:", err);
    alert("❌ Не удалось начать матч");
  } finally {
    setLoading(false);
  }
};
  return (
    <div className="profile-container">
      <h1>Профиль игрока</h1>
      <p>Готов начать исследование?</p>
      <button onClick={handleStartMatch} disabled={loading}>
        {loading ? 'Загрузка...' : '🎮 Играть'}
      </button>
    </div>
  );
}

export default ProfilePage;
