import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import { socketService } from '../services/socketService';
import { jwtDecode } from 'jwt-decode';

function ProfilePage() {
  const [isSearching, setIsSearching] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    socketService.connect(() => {
      console.log("WebSocket подключен, готов к подписке.");
    });

    return () => {
      socketService.disconnect();
    };
  }, []); 

  const handleStartSearch = async () => {
    setIsSearching(true);
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        alert("Ошибка: нет токена. Пожалуйста, войдите заново.");
        setIsSearching(false);
        return;
      }

      const userData = jwtDecode(token);
      const userName = userData.sub; 

      if (!userName) {
        alert("Ошибка: не удалось определить ID пользователя из токена.");
        console.error("Содержимое токена:", userData);
        setIsSearching(false);
        return;
      }
      
      const topic = `/topic/user/${userName}/match-found`;
      console.log(`[ОТЛАДКА] Подписываемся на личный канал: ${topic}`);

      socketService.subscribe(topic, (matchData) => {
        console.log('[ОТЛАДКА] ✅ ПОЛУЧЕНО СООБЩЕНИЕ О МАТЧЕ!', matchData);
        setIsSearching(false);

        if (!matchData.matchId || !matchData.startingCellNum) {
            console.error("[ОТЛАДКА] В полученных данных не хватает matchId или startingCell.id", matchData);
            alert("Ошибка: сервер прислал неполные данные о матче.");
            return;
        }

        localStorage.setItem('matchId', matchData.matchId);
        localStorage.setItem('currentCellNum', matchData.startingCellNum);
        localStorage.setItem('enemies', JSON.stringify(matchData.enemies));
        
        console.log(`[ОТЛАДКА] Переходим на страницу /match/${matchData.matchId}`);
        navigate(`/match/${matchData.matchId}`);
      });

      await axios.post('/match/search');

    } catch (err) {
      console.error("Ошибка при поиске матча:", err);
      alert("❌ Не удалось начать поиск матча");
      setIsSearching(false);
    }
  };

  return (
    <div className="profile-container">
      <h1>Профиль игрока</h1>
      <p>Готов начать исследование?</p>
      <button onClick={handleStartSearch} disabled={isSearching}>
        {isSearching ? '⏳ Идет поиск матча...' : '🎮 Найти игру'}
      </button>
    </div>
  );
}

export default ProfilePage;