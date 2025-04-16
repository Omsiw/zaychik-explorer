import { Link } from "react-router-dom";
import logo from "../assets/images/logo.png";

function Navbar() {
  const matchId = localStorage.getItem('matchId');

  return (
    <div className="navbar">
      <div className="navbar-left">
        <img src={logo} alt="Logo" className="navbar-logo" />
        <span className="navbar-brand">Zaychik.KZ</span>
      </div>
      <div className="navbar-links">
        <Link to="/profile">Мой заяц</Link>
        {matchId && <Link to={`/match/${matchId}`}>Карта</Link>}
      </div>
    </div>
  );
}

export default Navbar;
