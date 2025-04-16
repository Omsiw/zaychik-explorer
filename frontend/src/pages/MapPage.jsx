import React, { useEffect, useState } from 'react';

// Если понадобится — подключи свой инстанс axios:
// import axios from '../services/axios';

function MapPage() {
  // Стартовая клетка (приходила из /match/search)
  const initial = parseInt(localStorage.getItem('cellNum'), 10) || null;

  const [current, setCurrent] = useState(initial);
  const [taken,  setTaken]   = useState(initial !== null ? [initial] : []);
  const [capturing, setCapturing] = useState(false);
  const [timer, setTimer] = useState(0);

  // Таймер для прогресса захвата
  const startCapture = (cellNum) => {
    const duration = 5; // секунд, например
    setCapturing(true);
    setTimer(duration);

    const iv = setInterval(() => {
      setTimer(t => {
        if (t <= 1) {
          clearInterval(iv);
          setCapturing(false);
          // после завершения:
          setCurrent(cellNum);
          setTaken(prev => Array.from(new Set([...prev, cellNum])));
          return 0;
        }
        return t - 1;
      });
    }, 1000);
  };

  // Обработчик клика
  const handleClick = (idx) => {
    console.log('Clicked cell:', idx);
    // сюда вставь свой axios.post('/action/move'...)
    startCapture(idx);
  };

  // генерация сетки
  const renderCell = (idx) => {
    const isCurr = current === idx;
    const isTaken = taken.includes(idx);

    let className = 'cell';
    if (isTaken) className += ' taken';
    if (isCurr)  className += ' current';

    return (
      <div
        key={idx}
        className={className}
        onClick={() => !capturing && handleClick(idx)}
      >
        { isCurr && <div className="bunny">🐰</div> }
        { /* номер (необязательно) */ }
        <div className="num">{idx}</div>

        { capturing && isCurr && (
          <div className="progress-bar" />
        ) }
      </div>
    );
  };

  return (
    <div className="map-container">
      <h2>Карта матча</h2>
      <div className="grid-10x10">
        {Array.from({ length: 100 }, (_, i) => renderCell(i))}
      </div>
    </div>
  );
}

export default MapPage;
