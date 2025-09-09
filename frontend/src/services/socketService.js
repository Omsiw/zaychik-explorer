import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SERVER_URL = 'http://localhost:8080/ws';

const SERVER_URL_WS = 'ws://localhost:8080/ws'

class SocketService {
    stompClient;
    subscriptions = new Map();

    connect(onConnectedCallback) {
        if (this.stompClient && this.stompClient.active) {
            console.log('Уже подключены.');
            if (onConnectedCallback) onConnectedCallback();
            return;
        }

        const token = localStorage.getItem('token');
        if (!token) {
            console.error("Нет токена для WebSocket подключения.");
            return;
        }

        this.stompClient = new Client({
            // --- НАЧАЛО ИЗМЕНЕНИЙ ---
            // Указываем прямой URL для чистого WebSocket
            brokerURL: SERVER_URL_WS,
            // webSocketFactory больше не нужен
            // --- КОНЕЦ ИЗМЕНЕНИЙ ---

            // Передаем заголовки для аутентификации
            connectHeaders: {
                Authorization: `Bearer ${token}`,
            },

            // Включаем подробное логирование для отладки
            debug: (str) => {
                console.log('STOMP DEBUG: ' + str);
            },
            
            reconnectDelay: 5000,
        });

        // // Создаем новый клиент с полной конфигурацией
        // this.stompClient = new Client({
        //     // Вместо прямого URL, мы предоставляем функцию,
        //     // которая возвращает SockJS-совместимый объект.
        //     webSocketFactory: () => new SockJS(SERVER_URL),

        //     // Передаем заголовки для аутентификации
        //     connectHeaders: {
        //         Authorization: `Bearer ${token}`,
        //     },

        //     // Включаем подробное логирование для отладки
        //     debug: (str) => {
        //         console.log('STOMP DEBUG: ' + str);
        //     },

        //     // Настройки для поддержания соединения
        //     reconnectDelay: 5000,
        // });

        // Коллбэк после успешного подключения
        this.stompClient.onConnect = (frame) => {
            console.log('✅ WebSocket подключение установлено: ' + frame);
            if (onConnectedCallback) {
                onConnectedCallback();
            }
        };

        // Коллбэк при ошибке на уровне STOMP (теперь мы увидим больше деталей)
        this.stompClient.onStompError = (frame) => {
            console.error('❌ Ошибка STOMP протокола: ' + frame.headers['message']);
            console.error('Детали: ' + frame.body);
        };
        
        // Активируем клиент. Он попытается подключиться.
        this.stompClient.activate();
    }

    disconnect() {
        if (this.stompClient && this.stompClient.active) {
            this.stompClient.deactivate();
            console.log('🔌 WebSocket соединение разорвано.');
            this.subscriptions.clear();
        }
    }

    subscribe(topic, onMessageReceived) {
        if (!this.stompClient || !this.stompClient.active) {
            console.error("Невозможно подписаться, клиент не подключен.");
            return;
        }
        
        const subscription = this.stompClient.subscribe(topic, (message) => {
            onMessageReceived(JSON.parse(message.body));
        });
        
        this.subscriptions.set(topic, subscription);
        console.log(`👂 Подписались на канал: ${topic}`);
    }

    // Метод sendMessage теперь называется publish в новой библиотеке
    sendMessage(destination, body) {
        if (this.stompClient && this.stompClient.active) {
            this.stompClient.publish({
                destination: destination,
                body: JSON.stringify(body)
            });
        } else {
            console.error("Невозможно отправить сообщение, клиент не подключен.");
        }
    }
}

export const socketService = new SocketService();