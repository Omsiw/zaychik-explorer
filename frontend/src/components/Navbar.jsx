// src/components/Navbar.jsx
import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <ul>
        <li><Link to="/">🏠 Главная</Link></li>
        <li><Link to="/login">🔐 Вход</Link></li>
        <li><Link to="/register">📝 Регистрация</Link></li>
        <li><Link to="/profile">👤 Профиль</Link></li>
        <li><Link to="/map">🗺️ Карта</Link></li>
      </ul>
    </nav>
  );
}

export default Navbar;
