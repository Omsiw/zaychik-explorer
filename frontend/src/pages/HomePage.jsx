import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';


function HomePage() {
  return (
    <div className="home-page">
      <div className="bunny-logo">
        <img src="http://localhost:3000/src/assets/images/logo.png" alt="Big Bunny" />
      </div>
      <h1 className="home-title">Zaychik.KZ</h1>
      <div className="home-buttons">
        <Link to="/login"><button>Войти</button></Link>
        <Link to="/register"><button>Регистрация</button></Link>
      </div>
    </div>
  );
}

export default HomePage;
