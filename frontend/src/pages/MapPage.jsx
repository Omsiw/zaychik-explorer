import React, { useEffect, useState } from 'react';
import axios from '../api/axios';

const CAPTURE_TIMES = {
  'Горы': 15,
  'Лес': 12,
  'Вода': 17,
  'Пески': 13,
  'Поле': 10,
};

const TYPE_ICONS = {
  'Горы': '/src/assets/icons/1.png',
  'Лес': '/src/assets/icons/2.png',
  'Вода': '/src/assets/icons/3.png',
  'Пески': '/src/assets/icons/4.png',
  'Поле': '/src/assets/icons/5.png',
};

function MapPage() {
  const matchId = parseInt(localStorage.getItem('matchId'));
  const startCellId = parseInt(localStorage.getItem('startCellId'));

  const [currentCellId, setCurrentCellId] = useState(null);
  const [adjacentCells, setAdjacentCells] = useState([]);
  const [capturing, setCapturing] = useState(false);
  const [timer, setTimer] = useState(0);
  const [gameTime, setGameTime] = useState(600);
  const [takenCells, setTakenCells] = useState([]);
  const [cellMap, setCellMap] = useState({});
  const [gameOver, setGameOver] = useState(false);

  useEffect(() => {
    takeStartCell();

    const gameInterval = setInterval(() => {
      setGameTime(prev => {
        if (prev <= 1) {
          clearInterval(gameInterval);
          setGameOver(true);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(gameInterval);
  }, []);

  const takeStartCell = async () => {
    try {
      const response = await axios.post('/action/take', {
        cellId: startCellId,
        matchId,
      });

      if (response.data.success) {
        const { cellId, adjacentCells } = response.data;
        setCurrentCellId(cellId);
        setTakenCells([cellId]);
        updateMap(adjacentCells);
      } else {
        alert(response.data.message);
      }
    } catch (err) {
      console.error("Ошибка при старте:", err);
    }
  };

  const updateMap = (adjCells) => {
    const map = {};
    adjCells.forEach(cell => {
      map[cell.cellNum] = cell;
    });
    setCellMap(prev => ({ ...prev, ...map }));
    setAdjacentCells(adjCells);
  };

  const handleMove = async (cell) => {
    if (capturing || gameTime <= 0) return;

    const type = cell.cellType.type;
    const captureTime = CAPTURE_TIMES[type] || 10;

    setCapturing(true);
    setTimer(captureTime);

    const countdown = setInterval(() => {
      setTimer(prev => {
        if (prev <= 1) {
          clearInterval(countdown);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    setTimeout(async () => {
      try {
        const response = await axios.post('/action/take', {
          cellId: cell.id,
          matchId,
        });

        if (response.data.success) {
          const { cellId, adjacentCells } = response.data;
          setCurrentCellId(cellId);
          setTakenCells(prev => [...new Set([...prev, cellId])]);
          updateMap(adjacentCells);
        } else {
          alert(response.data.message);
        }
      } catch (err) {
        alert("❌ Ошибка при захвате клетки");
      } finally {
        setCapturing(false);
        setTimer(0);
      }
    }, captureTime * 1000);
  };

  const renderCell = (index) => {
    const cell = cellMap[index];
    const isTaken = takenCells.includes(index);
    const isCurrent = currentCellId === index;

    const classes = ['cell'];
    if (isTaken) classes.push('taken');
    if (isCurrent) classes.push('current');

    return (
      <div
        key={index}
        className={classes.join(' ')}
        onClick={() => !capturing && cell && handleMove(cell)}
        style={{ cursor: cell && !capturing ? 'pointer' : 'default' }}
      >
        {cell && (
          <img
            src={TYPE_ICONS[cell.cellType.type]}
            alt={cell.cellType.type}
            style={{ width: 30, height: 30 }}
          />
        )}
        {isCurrent && (
          <img
            src="/src/assets/images/bunny.png"
            alt="Заяц"
            className="bunny"
          />
        )}
      </div>
    );
  };

  const capturedPercent = Math.round((takenCells.length / 100) * 100);

  return (
    <div className="map-container">
      <h2>Карта матча</h2>

      <p>⏱ Осталось: {Math.floor(gameTime / 60)}:{(gameTime % 60).toString().padStart(2, '0')}</p>

      <div style={{ margin: '10px 0', width: '300px', background: '#eee', borderRadius: '6px' }}>
        <div style={{
          width: `${capturedPercent}%`,
          height: '10px',
          backgroundColor: '#6ba5e9',
          borderRadius: '6px'
        }}></div>
      </div>
      <p>🌍 Исследовано клеток: {takenCells.length} / 100 ({capturedPercent}%)</p>

      <div className="grid-10x10">
        {Array.from({ length: 100 }, (_, i) => renderCell(i))}
      </div>

      {capturing && (
        <div className="capture-timer">⚔️ Захват клетки... Осталось {timer} сек</div>
      )}

      {gameOver && (
        <div style={{ marginTop: 20, padding: 10, backgroundColor: '#fff3cd', border: '1px solid #ffecb5', borderRadius: 8 }}>
          🎉 Игра завершена! Ты захватил {takenCells.length} клеток ({capturedPercent}% карты)!
        </div>
      )}
    </div>
  );
}

export default MapPage;
