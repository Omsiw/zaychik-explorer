import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { socketService } from '../services/socketService';
import { jwtDecode } from 'jwt-decode';
import axios from '../api/axios'; 
import { takeCellAction, moveToAction } from '../services/matchService'; 
import bunnyImage from '../assets/images/bunny.png';
import enemyImage from '../assets/images/enemy.png';

const TYPE_ICONS = {
    'Равнина': '/src/assets/icons/5.png',
    'Лес': '/src/assets/icons/2.png',
    'Горы': '/src/assets/icons/1.png',
    'Болото': '/src/assets/icons/4.png',
    'Вода': '/src/assets/icons/3.png',
};

function MapPage() {
    const { matchId } = useParams();
    const [myUserId, setMyUserId] = useState(null);
    const [myPosition, setMyPosition] = useState(null);
    const [timer, setTimer] = useState(0);
    const [cellMap, setCellMap] = useState({});
  const [capturing, setCapturing] = useState(false);
    const [enemies, setEnemies] = useState([])
    const [takenCells, setTakenCells] = useState({});
    const [isLoading, setIsLoading] = useState(false);

    //обновления карты
    const getMap = useCallback(async () => {
        try {
            const obj = JSON.parse(localStorage.getItem('enemies'))
            console.log(obj)
            setEnemies(obj)

            const mapResponse = await axios.get(`/matches/${matchId}/cells`);
            const cells = mapResponse.data;
            console.log(cells)
        
            const takenCellsResponse = await axios.get('/action/taken');
            const takenCells = takenCellsResponse.data;

            const newMap = {};
            cells.forEach(cell => {
                newMap[cell.cellNum] = cell;
            });

            const storageCellNum = parseInt(localStorage.getItem('currentCellNum'), 10);
            setCellMap(newMap);
            setTakenCells(takenCells);
            setMyPosition(storageCellNum);

        } catch (error) {
            console.error("Ошибка при загрузке карты:", error);
        }
    }, [matchId]);

    // WebSocket
    useEffect(() => {
        try {
            const token = localStorage.getItem('token');
            const userData = jwtDecode(token);
            setMyUserId(parseInt(userData.userId, 10));
        } catch (e) { console.error("Ошибка токена:", e); }

        getMap();

        const handleGameEvent = (event) => {
            console.log("Получено игровое событие:", event);
            var lastEnemies = localStorage.getItem("enemies")
            console.log(lastEnemies)
            if (event.eventType === "MOVE") {
                const updatedEnemies = enemies.map(enemy => {
                    if (enemy.userId === event.userId) {
                        return { ...enemy, cellNum: event.cellNum };
                    }
                    return enemy;
                });

                console.log("Старый массив врагов:", enemies);
                console.log("Новый, обновленный массив:", updatedEnemies);
                setEnemies(updatedEnemies)

                localStorage.setItem("enemies", JSON.stringify(updatedEnemies));
                console.log(enemies)
            }
            getMap(); 
        };

        socketService.connect(() => {
            socketService.subscribe(`/topic/match/${matchId}`, handleGameEvent);
        });

        if (myUserId) {
            getMap();
        }

        return () => socketService.disconnect();
    }, [myUserId, matchId, getMap]);

    // Логика клика
    const handleCellClick = async (clickedCell) => {
        if (isLoading || !myPosition) return;
        //const captureTime = clickedCell.cellType.movementCost * 10;

        setIsLoading(true);
        
        // setCapturing(true); 
        // setTimer(captureTime);
        
        // const countdown = setInterval(() => {
        //     setTimer(prev => {
        //         if (prev <= 1) {
        //         clearInterval(countdown);
        //         return 0;
        //         }
        //         return prev - 1;
        //     });
        // }, 1000);
        
        try {
            const currentCell = cellMap[myPosition];

            if (clickedCell.cellNum === myPosition) {
                await takeCellAction(matchId, clickedCell.id);
            } else {
                const result = await moveToAction(matchId, clickedCell.cellNum);
                console.log(result);
                localStorage.setItem('currentCellNum', result.cellNum);
            }
        } catch (error) {
            console.error("Ошибка при выполнении действия:", error);
            alert(error.response?.data?.message || "Произошла ошибка");
        } finally {
            setIsLoading(false);
        }
    };

    // Отрисовка клетки
    const renderCell = (index) => {
        const cell = cellMap[index];
        if (!cell) return <div key={index} className="cell empty"></div>;

        const userId = takenCells.takeByEnemy;
        let cellClass = 'cell';
        if (takenCells?.takenByUser?.some(c => c.id === cell.id)) {
            cellClass = "taken-by-me";
        } else if (takenCells?.takenByEnemy?.some(c => c.id === cell.id)) {
            cellClass = "taken-by-enemy";
        }
        const isEnemyHere = enemies.some(enemy => enemy.cellNum === index);
        
        return (
            <div 
                key={index} 
                className={cellClass} 
                onClick={() => handleCellClick(cell)}
                style={{ cursor: 'pointer' }}
            >
                <img src={TYPE_ICONS[cell.cellType.type]} alt={cell.cellType.type} className="terrain-icon" />
                {myPosition === index && (
                    <img src={bunnyImage} alt="Заяц" className="bunny-icon" />
                )}
                {isEnemyHere && (
                    <img src={enemyImage} alt="Враг" className="bunny-icon enemy" />
                )}
            </div>
        );
    };

    return (
        <div className="map-container">
            <h2>Карта матча #{matchId}</h2>
            <div className="grid-10x10">
                {Object.keys(cellMap).length > 0
                    ? Array.from({ length: 100 }, (_, i) => renderCell(i))
                    : <p>Загрузка карты...</p>
                }
            </div>
            {capturing && (
                <div className="capture-timer">⚔️ Захват клетки... Осталось {timer} сек</div>
            )}
        </div>
    );
}

export default MapPage;