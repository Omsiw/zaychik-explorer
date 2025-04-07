import React from 'react';
import { Link } from 'react-router-dom';


function HomePage() {
  return (
    <div className="home-container">
          <h1 className="home-title">Добро пожаловать в Zaychik Explorer 🐰</h1>
          <div className="home-buttons">
            <Link to="/login"><button className="home-button">Войти</button></Link>
            <Link to="/register"><button className="home-button">Регистрация</button></Link>
          </div>
        </div>
  );
}


export default HomePage;
